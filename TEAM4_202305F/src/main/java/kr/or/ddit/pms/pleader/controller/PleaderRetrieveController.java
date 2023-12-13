package kr.or.ddit.pms.pleader.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.adproject.service.AdminProjectService;
import kr.or.ddit.pms.job.service.JobService;
import kr.or.ddit.pms.pleader.service.PleaderService;
import kr.or.ddit.pms.project.service.ProjectService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.pms.LeaderCheckVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Nov 29, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 29, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/pleader")
public class PleaderRetrieveController {

	
	@Inject
	private PleaderService service;
	
	/**
	 * 프로젝트 인원 데려오기 
	 */
	@Inject
	private ProjectService pService; 
	
	
	/**
	 * 프로젝트 정보 
	 */
	@Inject
	private JobService jservice;
	
	/**
	 * 프로젝트 인원
	 */
	@Inject
	private AdminProjectService Pmemservice;
	
	
	@GetMapping("{proSn}")
	public String onlypleaderView(
			@PathVariable String proSn
			, Model model
			, Authentication Principal 
			) { 
		  
		String empCd = Principal.getName();
		 
		PjobVO pjobleader = service.pjobAllCount(proSn);
		model.addAttribute("pjobleader", pjobleader); 
		
		List<PjobVO> pjobEveryThing = service.pjobChargerCount(proSn);
		model.addAttribute("pjobEveryThing", pjobEveryThing); 
		
		//프로젝트명 목록
		List<ProjectVO> projname = pService.selectProjectList(empCd);  
		model.addAttribute("projname", projname);
		System.out.println(projname);
		
		
		//프로젝트정보
		ProjectVO pVO = jservice.retrieveProject(proSn);
		model.addAttribute("proInfo", pVO);
		
		
		//프로젝트 인원
		List<PmemberVO> pmemAdminList = Pmemservice.projectPmemberSelectleader(proSn);  
		model.addAttribute("pmemAdminList",pmemAdminList); 
		
		
     		
		return "pleader/pleaderView"; 
	}
	
	
	
	@PostMapping("leadercheck")
	public String LeaderCheckPmem(
			@RequestParam(value = "searchType", required = false) String searchType
			, @RequestParam(value = "searchWord", required = false) String searchWord
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			, @ModelAttribute("simpleCondition") SearchVO simpleCondition
			,@ModelAttribute("pjobVO") PjobVO pjobVO
			,@RequestParam(value = "proSn" , required = true) String proSn
			,@RequestParam(value = "empCd" , required = true) String empCd 
			
			) {
		
		PjobVO jVO = new PjobVO();
		jVO.setEmpCd1(empCd);
		jVO.setProSn(proSn);
		System.out.println("데이터화기이이이이이이인"+jVO);
		
		
		PaginationInfo<PjobVO> paging = new PaginationInfo<>(6,2);  
		paging.setDetailCondition(jVO); 
		
		paging.setSimpleCondition(simpleCondition);
		  
		paging.setCurrentPage(currentPage);
		
		service.retrieveLeaderPmember(paging);
		
		model.addAttribute("paging", paging);
		 
		
		return "jsonView";  
	}
	
	/*
	 {
	    "page": 1,
	    "proSn": "P23006",
	    "empCd": "E220907003",
	    "searchType":"",
	    "searchWord":""
	}
	 */
	@PostMapping("/leadercheckAjax")
	public String LeaderCheckPmemAjax(
			@RequestBody LeaderCheckVO leaderCheckVO, Model model
			) {
		
		log.info("leaderCheckVO : " + leaderCheckVO);
		
		PjobVO jVO = new PjobVO();
		jVO.setEmpCd1(leaderCheckVO.getEmpCd());
		jVO.setProSn(leaderCheckVO.getProSn());
		System.out.println("데이터화기이이이이이이인"+jVO);
		
		
		PaginationInfo<PjobVO> paging = new PaginationInfo<>(6,2);  
		paging.setDetailCondition(jVO); 

		SearchVO simpleCondition = new SearchVO();
		
		simpleCondition.setSearchType(leaderCheckVO.getSearchType());
		simpleCondition.setSearchWord(leaderCheckVO.getSearchWord());
		
		paging.setSimpleCondition(simpleCondition);
		  
		paging.setCurrentPage(leaderCheckVO.getPage());
		
		service.retrieveLeaderPmember(paging);
		
		model.addAttribute("paging", paging);
		 
		
		return "jsonView";  
	}
	
}
