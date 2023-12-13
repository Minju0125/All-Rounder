package kr.or.ddit.groupware.mypage.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.mypage.service.MypageService;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Nov 27, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 27, 2023      송석원       최초작성
 * Nov 28, 2023      오경석       기능구현
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/mypage")
public class MyPageController {

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Inject
	private MypageService service;
	
	
	@GetMapping
	public String mypage() {
		return "mypage/mypageView";  
	}
	
	@GetMapping("info")
	@ResponseBody
	public EmployeeVO mypageInfo(Authentication principal) {
		String empCd = principal.getName();
		EmployeeVO mypage = service.selectMypage(empCd);			
		return mypage;
	}
	
	@PostMapping("/pw")
	@ResponseBody
	public EmployeeVO password(@RequestBody EmployeeVO emp, Authentication principal) {
		String empCd = principal.getName();
		emp.setEmpCd(empCd);
		
		log.info("%%%%%%%%%%%%% {}",emp);
		
		emp = service.authenticateBoard(emp);
		
		return emp;
	}
	
	@PutMapping
	@ResponseBody
	public String updateMypage(Authentication principal, EmployeeVO empVO) {
		String empCd = principal.getName();
		empVO.setEmpCd(empCd);
		ServiceResult result = service.updateMypage(empVO);
		String value = null;
		switch (result) {
		case OK:
			value = "Good";
			break;
		default:
			value = "Fail";
			break;
		}
		return value;
	}
	
	
	
	
}
