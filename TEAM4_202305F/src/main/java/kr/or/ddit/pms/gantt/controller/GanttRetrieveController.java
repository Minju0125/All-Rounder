package kr.or.ddit.pms.gantt.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.pms.gantt.service.GanttService;
import kr.or.ddit.pms.job.service.JobService;
import kr.or.ddit.pms.pleader.service.PleaderService;
import kr.or.ddit.pms.project.service.ProjectService;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/pms/gantt")
public class GanttRetrieveController {
	 
	
	
	@Inject
	private GanttService service;
	
	@Inject
	private ProjectService pService; 
	
	@Inject
	private PleaderService Plservice;
	
	@Inject
	private JobService jservice; 
	
	@GetMapping("{proSn}")
	public String ganttList(
			Authentication Principal 
			, Model model 
			, @PathVariable String proSn) {
		
		model.addAttribute("proSn" ,proSn);
		//로그인한 직원의 정보
		String empCd = Principal.getName();
		
		PjobVO projVO = new PjobVO();
		projVO.setEmpCd1(empCd);  
		projVO.setProSn(proSn); 
		
		
		List<PjobVO> proj = service.retrievePjobList(proSn); 
		model.addAttribute("proj", proj);
		System.out.println(proj);
		
		
		//프로젝트명 목록
		List<ProjectVO> projname = pService.selectProjectList(empCd);  
		model.addAttribute("projname", projname);
		System.out.println(projname); 
		
		//프로젝트명
		PjobVO pjobleader = Plservice.pjobAllCount(proSn);
		model.addAttribute("pjobleader", pjobleader);
		
		
		//프로젝트정보
		ProjectVO pVO = jservice.retrieveProject(proSn);
		model.addAttribute("proInfo", pVO); 
		
		
		
		//참여자목록
		PmemberVO pMVO = new PmemberVO();
		pMVO.setProSn(proSn);
		List<PmemberVO> proM = jservice.retrieveProMember(pMVO);
		
		//담당자인지 확인
		
		String role = "member";
		for(PmemberVO member : proM) {
			if(member.getProLeader().equals("Y")) {
				if(member.getEmpCd().equals(empCd)) {  
					role = "leader";
					break;
				}
			}
		}
		model.addAttribute("role", role);
		
		
		
		
		
		
		
		return "pms/gantt/ganttList"; 
	}
	
	@GetMapping("/ganttChoice")
	public String ganttChoice(
			@RequestParam String proSn
			,@RequestParam(required = false) Integer jobuSn
			,@RequestParam Integer jobSn
			,Model model) {
		log.info("파라미터1 : {}", proSn);
		log.info("파라미터 2: {}", jobuSn);
		log.info("파라미터 3: {}", jobSn);
		PjobVO pjobVO = new PjobVO(); 
		pjobVO.setProSn(proSn);
		pjobVO.setJobuSn(jobuSn);
		if(jobSn!=null) {
			pjobVO.setJobSn(jobSn); 
		}
		List<PjobVO> list = service.retrievePjobGanttChoice(pjobVO);
		model.addAttribute("pjobgantt", list);
		System.out.println("컨트롤러쪽 오류를 찾아볼까요:"+list);
		
		return "jsonView";
	}
	

}
