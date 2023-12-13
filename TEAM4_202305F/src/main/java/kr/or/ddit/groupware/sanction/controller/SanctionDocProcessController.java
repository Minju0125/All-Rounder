package kr.or.ddit.groupware.sanction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.admin.account.service.AccountService;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.sanction.service.SanctionFormService;
import kr.or.ddit.groupware.sanction.service.SanctionService;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.SanctionFormVO;
import kr.or.ddit.vo.groupware.SanctionLineVO;
import kr.or.ddit.vo.groupware.SanctionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 22.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 22.  전수진       최초작성
 * 2023. 11. 23.  전수진       결재문서 삭제, 수정 구현
 * 2023. 11. 24.  전수진       결재문서 수정후 case resultMap 구현
 * 2023. 11. 25.  전수진       결재문서 결재진행시 결재라인 변경
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/sanction/{sanctnNo}")
@RequiredArgsConstructor
public class SanctionDocProcessController {
	
	private final SanctionFormService formService;
	private final AccountService accountService;
	private final SanctionService service;
	
	/**
	 * 기안된 결재문서 상세조회
	 */
	@GetMapping
	public String sanctionDocView(@PathVariable String sanctnNo, Authentication authentication, Model model) {
		String empCd = authentication.getName();
		EmployeeVO empVo = accountService.retrieveMember(empCd);
		model.addAttribute("empVo",empVo);
		SanctionVO sanction = service.retrieveSanction(sanctnNo);
		log.info("sanctn=============={}",sanction);
		model.addAttribute("sanction",sanction);
		
		return "sanctiondoc/sanctiondocView";
	}
	
	/**
	 * 기안된 결재문서 삭제(결재진행중이 아닐때만 삭제 가능)
	 */
	@DeleteMapping
	@ResponseBody
	public Map<String, String> removeSanctionDoc(@PathVariable String sanctnNo) {
		log.info("삭제!!!!!");
		ServiceResult result = service.removeSanction(sanctnNo);
		
		Map<String, String> resultMap = new HashMap<String, String>();
		
		if(result.equals(ServiceResult.OK)) {
			resultMap.put("msg", "OK");
			
		} else if (result.equals(ServiceResult.CANNOTPROCEED)) {
			// 결재진행상태라 진행못함
			resultMap.put("msg", "CANNOTPROCEED");
		} else {
			resultMap.put("msg", "FAIL");
		}
		return resultMap;
	}
	
	/**
	 * 기안된 결재문서 수정할수 있는 form으로 이동(결재진행중이 아닐때만 수정 가능)
	 */
	@GetMapping("/edit")
	public String editSanctionDocForm(Authentication authentication, @PathVariable String sanctnNo, Model model) {
		String empCd = authentication.getName();
		EmployeeVO empVo = accountService.retrieveMember(empCd);
		
		SanctionVO santion = service.retrieveSanction(sanctnNo);
		int formNo = santion.getFormNo();
		
		SanctionFormVO formSet = formService.retrieveSanctionFormSourc(formNo);
		model.addAttribute("formSet",formSet);
		model.addAttribute("empVo",empVo);
		model.addAttribute("editSanction",santion);
		
		return "sanctiondoc/sanctiondocEdit";
	}
	
	/**
	 * 기안된 결재문서 수정
	 */
	@PutMapping("/edit")
	@ResponseBody
	public Map<String, String> editSanctionDoc(
			@PathVariable String sanctnNo,
			@RequestPart("sanctionVO") SanctionVO editSanction,			
			@RequestPart("lineList") List<SanctionLineVO> lineList,
			@RequestPart("sanctionFile") MultipartFile[] sanctionFile
			) {
		
		editSanction.setLineList(lineList);
		editSanction.setSanctionFile(sanctionFile);
		
		ServiceResult result = service.modifySanction(editSanction);
		String editSanctnNo =  editSanction.getSanctnNo();
		
		Map<String, String> resultMap = new HashMap<String, String>();
		if(result.equals(ServiceResult.OK)) {
			resultMap.put("msg", "OK");
			resultMap.put("sanctnNo", editSanctnNo);
		} else if (result.equals(ServiceResult.CANNOTPROCEED)) {
			// 결재진행상태라 진행못함, view에서 막아뒀지만 비정상접근상황 고려
			resultMap.put("msg", "CANNOTPROCEED");
		} else {
			resultMap.put("msg", "FAIL");
		}
		
		return resultMap;
	}
	
	/**
	 * 기안된 결재문서 수정
	 */
	@PutMapping("/lineEdit")
	@ResponseBody
	public Map<String, Object> modifySanctionLine(
			Authentication authentication,
			@PathVariable String sanctnNo,
			@RequestBody SanctionLineVO sanctionLineVO
			) {
		
		String realSanctner = authentication.getName();
		sanctionLineVO.setRealSanctner(realSanctner);
		sanctionLineVO.setSanctnNo(sanctnNo);
		log.info("sanctionLineVO============{}",sanctionLineVO);
		
		ServiceResult result = service.modifySanctionLine(sanctionLineVO);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("msg", "FAIL");
		
		if(result.equals(ServiceResult.OK)) {
			resultMap.put("msg","OK");
			SanctionVO updateSanction = service.retrieveSanction(sanctnNo);
			resultMap.put("updateSanction", updateSanction);
		}
		
		return resultMap;
		
	}
	
	
}
