package kr.or.ddit.login.controller;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.login.dao.LoginDAO;
import kr.or.ddit.login.service.FindPasswordProcessService;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

/**
 * @author 박민주
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일             수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.     박민주       최초작성
 * 2023. 11. 8.		박민주	  비밀번호 찾기 위한 메소드 추가
 * 2023. 12. 4.     박민주	  비밀번호 찾기 메소드 수정
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */

@Controller
@Slf4j
public class FindPasswordProcessController {

	@Inject
	FindPasswordProcessService fpService;

	@Inject
	private LoginDAO dao;

	@GetMapping("/login")
	public String login(
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception,
			Model model) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "login/loginForm";
	}
	
	@GetMapping("/findPw/findPwForm")
	public String doGetFindPw() {
		return "login/findPw";
	}
	
	@PostMapping("/findPw/findPwProcess")
	@ResponseBody
	public Map<String, String> veryfiyEmpInfo(@RequestBody EmployeeVO inputEmployee) {
		Map<String, String> resultMap = new HashMap<>();
		
		ServiceResult result = fpService.authForFindPassword(inputEmployee);
		String msgResult = "Fail";
		String updateResult = "Fail";
		String messege = null;
		if (result.equals(ServiceResult.OK)) { // 입력받은 값의 직원정보가 존재하는 경우
			String crtfNo = generateRandomCode(); //생성된 랜덤문자 (인증번호)
			log.info("생성된 랜덤인증번호 : " + crtfNo);
			SingleMessageSentResponse resp = fpService.sendMessage(inputEmployee.getEmpTelno(), crtfNo);
//			String resp="하하"; //테스트용~~!!!■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
			if(resp!=null) {
				inputEmployee.setEmpCrtfcNo(crtfNo);
				msgResult = "OK";
				messege = "인증번호를 발송하였습니다.";
				if(fpService.updateCertiNum(inputEmployee)>0) {
					updateResult = "OK";
				}
			}else {
				messege = "문자발송에 실패하였습니다.";
			}
		} else {
			messege = "입력하신 직원 정보와 일치하는 정보를 찾을 수 없습니다.";
		}
		resultMap.put("msgResult", msgResult);
		resultMap.put("updateResult", updateResult);
		resultMap.put("messege", messege);
		return resultMap;
	}
	
	// 랜덤 문자 생성(인증번호)
	public String generateRandomCode() {
		int length = 6; // 6자리 길이로
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // 인증번호 생성에 사용될 문자들
		StringBuilder code = new StringBuilder();
		SecureRandom randomCode = new SecureRandom();
		for (int i = 0; i < length; i++) {
			int randomIndex = randomCode.nextInt(characters.length());
			char randomChar = characters.charAt(randomIndex);
			code.append(randomChar);
		}
		return code.toString();
	}
	
	//사원의 비밀번호를 생년월일로 초기화 & 사원의 로그인 상태를 N로 변경
	@PutMapping("/findPw/findPwProcess/{empCd}")
	@ResponseBody
	public Map<String,String> initializeEmpPwAndStatus(@PathVariable("empCd") String empCd ){
		log.info("컨트롤러로 넘어온 empCd ==> " + empCd);
		Map<String,String> resultMap = new HashMap<>();
		int updateResult = fpService.modifyEmpPwAndStatus(empCd);
		String success = "Fail";
		if(updateResult>0) {
			success = "OK";
		}
		resultMap.put("success", success);
		return resultMap;
	}

	@PostMapping("/findPw/checkCertiNum")
	@ResponseBody
	public Map<String,String> checkCertiNum(@RequestBody EmployeeVO inputEmployee) {
		Map<String, String> resultMap = new HashMap<>();
		EmployeeVO saved = dao.selectEmpForAuth(inputEmployee);
		
		String inputCrifNO = inputEmployee.getEmpCrtfcNo();
		String savedCrifNO = saved.getEmpCrtfcNo();

		String success = "Fail";
		if (inputCrifNO.equals(savedCrifNO)) {
			if(fpService.modifyEmpPwAndStatus(saved.getEmpCd())>0) { //업데이트 성공
				success = "OK";
			}
		}
		resultMap.put("success", success);
		return resultMap;
	}
	
	@GetMapping("/findPw/modifyPasswordForm/{empCd}")
	public String modifyPasswordForm(@PathVariable String empCd, Model model) {
		model.addAttribute("empCd", empCd);
		return "login/modifyPasswordForm";
	}
	
	@PutMapping("/findPw/modifyPasswordProcess")
	@ResponseBody
	public Map<String, String> modifyPasswordProcess(@RequestBody EmployeeVO employee){
		Map<String,String> resultMap = new HashMap<String, String>();
		
		String success = "Fail";
		//if 입력받은 비밀번호가 형식에 맞지 않는다면, return
		if(!isValidPasswordFormat(employee.getEmpPw())) { //형식에 맞지 않으면
			success = "isNotValid";
		}else {
			if(fpService.modifyEmpPassword(employee)>0) {
				success = "OK";
			}
		}
		resultMap.put("success", success);
		return resultMap;
	}
	
	public boolean isValidPasswordFormat(String inputPw) {
		int minLength = 6;
		int maxLength = 15;
		int inputPwLength = inputPw.length();
		return (inputPwLength>=minLength) && (inputPwLength<=maxLength);
	}
}