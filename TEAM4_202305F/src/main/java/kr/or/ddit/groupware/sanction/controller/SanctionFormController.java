package kr.or.ddit.groupware.sanction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.sanction.service.SanctionFormService;
import kr.or.ddit.vo.groupware.SanctionFormVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 15.   전수진       최초작성(관리자 결재문서양식form 등록)
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@RequestMapping("/sanctionform")
@Controller
public class SanctionFormController {
	
	@Inject
	private SanctionFormService service;
	
	
	@GetMapping
	public String sanctionFormList(Model model) {
		List<SanctionFormVO> sanctionFormList = service.retrieveSanctionFormList();
		model.addAttribute("sanctionFormList", sanctionFormList);
		return "sanctionform/sanctionformList";
	}
	
	/**
	 * 클라이언트에서 전송된 JSON데이터를 받아서 JSON으로 응답
	 * 리스트 1건 선택시 SampleForm을 나타냄
	 */
	@ResponseBody
	@PostMapping(value="/list", produces = "application/json;charset=utf-8")
	public ResponseEntity<SanctionFormVO> selectSanctionFormSourc(@RequestBody Map<String, String> param){
		String formNoParam = param.get("formNo");
		int formNo = Integer.parseInt(formNoParam);
		SanctionFormVO sacntionFormVO = service.retrieveSanctionFormSourc(formNo);
		return new ResponseEntity<SanctionFormVO>(sacntionFormVO, HttpStatus.OK);
	}
	
	@GetMapping("/new")
	public String sanctionForm() {
		return "admin/sanction/sanctionformCreate";
	}
	
	@PostMapping("/new")
	@ResponseBody
	public Map<String, String> sanctionFormInsert(@RequestBody SanctionFormVO sanctionFormVO, RedirectAttributes ra) {

		Map<String, String> resultMap = new HashMap<String, String>();
		
		log.info("sanctionFormVO========{}",sanctionFormVO);
		
		ServiceResult result = service.creatSanctionForm(sanctionFormVO);
		if(result.equals(ServiceResult.OK)) {
			resultMap.put("msg", "OK");
		}else {
			resultMap.put("msg", "FAIL");
		}
		return resultMap;
	}
	
	@GetMapping("/listUI")
	public String mamagementSanctionForm(Model model) {
		List<SanctionFormVO> sanctionFormList = service.retrieveSanctionFormAllList();
		model.addAttribute("sanctionFormList", sanctionFormList);
		return "admin/sanction/sanctionformAdminList";
	}
	
	
	

}