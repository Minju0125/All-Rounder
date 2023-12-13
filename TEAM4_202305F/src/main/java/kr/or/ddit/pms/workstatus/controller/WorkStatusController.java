package kr.or.ddit.pms.workstatus.controller;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.pms.workstatus.service.WorkStatusService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/workStatus")
public class WorkStatusController {
	
	@Inject
	private WorkStatusService service;
	
	@GetMapping 
	public String workList(
			Authentication Principal,
		@RequestParam(value = "searchType", required = false) String searchType
		, @RequestParam(value = "searchWord", required = false) String searchWord
		, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
		, Model model
		,@ModelAttribute("simpleCondition") SearchVO simpleCondition
	) {
		String empCd = Principal.getName();
		log.info("==============================="+empCd);
		 
		PaginationInfo<ProjectVO> paging = new PaginationInfo<>(6,3);
		paging.setSimpleCondition(simpleCondition);
		
		paging.setEmpCd(empCd);
		 
		paging.setCurrentPage(currentPage);
		
		service.retrieveWorkStatusList(paging); 
		
		model.addAttribute("paging", paging);
		
		
		return "pms/work/workStatus"; 
		
	}
	

}
