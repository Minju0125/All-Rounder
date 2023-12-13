package kr.or.ddit.admin.adproject.controller;

import java.io.Console;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import kr.or.ddit.admin.adproject.service.AdminProjectService;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;
import retrofit2.http.PUT;

/**
 * @author 작성자명
 * @since Nov 25, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 25, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/adminproject") 
@SessionAttributes("projectInfo")
public class AdminProjectModifyController {
	
	@Inject
	private AdminProjectService service;
	
	/**
	 * 프로젝트변경
	 * @param pjVO
	 * @return
	 */
	@PutMapping("projUpdate")  
	@ResponseBody
	public String projectUpdate(@RequestBody ProjectVO pjVO) {
		
		System.out.println("여기에요 여기~~"+pjVO); 
		
		
		service.modifyAdminProject(pjVO);  
		  
		 
		return "ok";
	}
	
	/**
	 * 리더변경
	 * @param pmemVO
	 * @return
	 */
	@PutMapping("pmemUpdate")
	@ResponseBody
	public String pmemberUpdate(@RequestBody PmemberVO pmemVO) {
		
 		
		service.updateProjectLeaderChange(pmemVO); 
		
	return "ok";
	}
	
	
	
	
	

}
