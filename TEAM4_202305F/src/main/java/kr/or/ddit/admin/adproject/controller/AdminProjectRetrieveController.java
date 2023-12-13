package kr.or.ddit.admin.adproject.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.admin.adproject.service.AdminProjectService;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.messenger.service.MessengerService;
import kr.or.ddit.vo.ChatParticipantVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;
 
/**
 * @author 작성자명
 * @since Nov 21, 2023 
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 21, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/adminproject") 
public class AdminProjectRetrieveController {

	@Inject
	private AdminProjectService service;
	
	@Inject
	private MessengerService msgService;
	
	 //프로젝트 상세조회
	@GetMapping("{proSn}")
	public String adminProjectView(
			@PathVariable String proSn
			, @RequestParam(value = "searchType", required = false) String searchType
			, @RequestParam(value = "searchWord", required = false) String searchWord
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			, @ModelAttribute("simpleCondition") SearchVO simpleCondition
			) {
		PaginationInfo<PmemberVO> paging = new PaginationInfo<>(7, 2);
		paging.setSimpleCondition(simpleCondition);	//키워드 검색 조건
		 
		paging.setCurrentPage(currentPage);
		paging.setProSn(proSn);
		 log.info("페이징에 프로젝트 번호가 넘어갔을까",paging);
		  
		service.retrieveAdminProject(paging);
		  
		model.addAttribute("paging", paging);
		
		List<PmemberVO> pmemAdminList = service.projectPmemberSelect(proSn); 
		
		model.addAttribute("pmemAdminList",pmemAdminList);  
		 
		return "adminproject/adprojectView";
	}
	
	 
	@GetMapping
	public String adprojects(
			@RequestParam(value = "searchType", required = false) String searchType
			, @RequestParam(value = "searchWord", required = false) String searchWord
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			, @ModelAttribute("simpleCondition") SearchVO simpleCondition
			,@ModelAttribute("pmemVO") PmemberVO pmemVO
		) {
		PaginationInfo<ProjectVO> paging = new PaginationInfo<>(10, 2);
		paging.setSimpleCondition(simpleCondition);	//키워드 검색 조건
		
		// request의 parameter로 받은 page정보를 넘김 → 이때 PaginationInfo의 property는 5개
		paging.setCurrentPage(currentPage);
		
		service.retrieveAdminProjectList(paging);
		
		model.addAttribute("paging", paging);
		
		
		return "adminproject/adprojectList";   
	}
	 	

	@ResponseBody
	@PostMapping
	public String makeAjaxProject(@RequestBody ProjectVO proj) {
		
		log.info("체크:{}",proj); 
		   
	   List<PmemberVO> pMemList=	  proj.getPmemberList();
	     
		service.createAdminProject(proj);
		
		/*
		for (PmemberVO pmemberVO : pMemList) {
			 pmemberVO.setProSn(proj.getProSn());
		}
		*/
	
		service.insertAdProjectPmember(proj); 
		
		List<ChatParticipantVO> chatParticipantList = new ArrayList<ChatParticipantVO>();
		
		for(PmemberVO pMember : pMemList) {
			String pMemberEmpCd = pMember.getEmpCd(); //프로젝트 구성원 한명의 사번
			ChatParticipantVO chatParticipantVO = new ChatParticipantVO(pMemberEmpCd); //프로젝트 구성원 한명의 VO
			chatParticipantList.add(chatParticipantVO); //프로젝트 구성원 list 에 추가
			chatParticipantVO.setChatRoomNm(proj.getProNm());
		}
		log.info("채팅방 생성? ==> " + chatParticipantList);
		msgService.createChatRoom(chatParticipantList, "P");
		
		
		return "ok"; 
	}

	
	@DeleteMapping("{proSn}")   
	@ResponseBody 
	public String adminProjectMemDel(Model model,@RequestBody PmemberVO pmemVO) {
		
		String viewName="";
		ServiceResult result = service.removeAdminProjMem(pmemVO);
		
		if(result.equals(ServiceResult.OK)) {
			
			return "jsonView";
		}else {
			viewName="redirect:/adminproject"; 
		} 
 
		return viewName; 
	}
	
}
