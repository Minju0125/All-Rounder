package kr.or.ddit.pms.project.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.pms.project.service.ProjectService;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 8.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 8.      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/pms/project")
public class ProjectViewController {

	@Inject
	private ProjectService service;
	
	@GetMapping
	public String projectList(Authentication Principal, Model model) {
		String empCd = Principal.getName();
		log.info("==============================="+empCd);
		List<ProjectVO> proj = service.selectProjectList(empCd);
		 
		System.out.println("아아아아아아아아아"+proj);
		  
		model.addAttribute("proj", proj);
 		 
	
	return "pms/project/projectList";
	
	}
	
	@PutMapping
	public String updateProject(@RequestBody ProjectVO  projectVO,
			Authentication Principal, Model model) {
		String empCd = Principal.getName();
		String empCCd = service.selectEmployeeCode(projectVO.getProSn()); 
		System.out.println("프로젝트리더사번"+empCCd);
		System.out.println("로그인한사번"+empCd);
		
		String JACount = service.selectJACompleteCount(projectVO.getProSn()); 
		String JobCCount = service.selectJobCompleteCount(projectVO.getProSn());
		
		System.out.println("put으로 보낸 데이터를 확인해 볼까요~:"+projectVO);
		
		
		
		
		
		if(empCCd.equals(empCd)) {
			
			
			ServiceResult result = service.modifyProjectStt(projectVO); 
			
			model.addAttribute("result", result);
			
			return "jsonView";   
			
			
			
			
		}else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		
		  
		
		
	}
	 
	
	
	
}


