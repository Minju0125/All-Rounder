package kr.or.ddit.groupware.sanction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
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
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 15.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/sanction/{formNo}/new")
@SessionAttributes(names = "newSanction")
@RequiredArgsConstructor
public class SanctionDocCreateController {

	private final SanctionFormService formService;
	private final AccountService accountService;
	private final SanctionService service;
	
	@ModelAttribute("newSanction")
	public SanctionVO sanction() {
		return new SanctionVO();
	}
	
	/**
	 * 결재양식 Setting을 위해 Sampleform조회
	 */
	@GetMapping
	public String setSanctionDoc(Authentication authentication, @PathVariable int formNo, Model model) {
		String empCd = authentication.getName();
		EmployeeVO empVo = accountService.retrieveMember(empCd);
		
		SanctionFormVO formSet = formService.retrieveSanctionFormSourc(formNo);
		model.addAttribute("formSet",formSet);
		model.addAttribute("empVo",empVo);
		
		return "sanctiondoc/sanctiondocForm";
	}
	
	@PostMapping
	@ResponseBody
//	public String createSancitionDoc(SanctionVO newSanction)
	public Map<String, String> createSancitionDoc(
			@PathVariable int formNo,
			@RequestPart("sanctionVO") SanctionVO newSanction,			
			@RequestPart("lineList") List<SanctionLineVO> lineList,
			@RequestPart("sanctionFile") MultipartFile[] sanctionFile
	)	
	{
		// 가져와야되는 데이터 기안자, 결재양식번호, 결재문서제목, 결재수신자, 결재문서내용, 첨부파일, 결재선
		//private List<SanctionLineVO> lineList;

		log.info("sanctionVO====================== {}",newSanction);
		log.info("sanctionFile====================== {}",(Object[]) sanctionFile);
		log.info("lineList====================== {}",lineList);
		
		newSanction.setFormNo(formNo);
		newSanction.setLineList(lineList);
		newSanction.setSanctionFile(sanctionFile);
		//log.info("fbi:",newSanction);
		
		ServiceResult result = service.createSanction(newSanction);
		log.info("sanctionVO====================== {}",newSanction);
		String sanctnNo =  newSanction.getSanctnNo();
		
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("msg", "FAIL");
		
		if(result.equals(ServiceResult.OK)) {
			resultMap.put("msg", "OK");
			resultMap.put("sanctnNo", sanctnNo);
		} 
		return resultMap;
		
	}
	
	
}
