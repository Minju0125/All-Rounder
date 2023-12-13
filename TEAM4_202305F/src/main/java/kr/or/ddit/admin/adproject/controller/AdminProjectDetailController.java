package kr.or.ddit.admin.adproject.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.admin.adproject.service.AdminProjectService;
import kr.or.ddit.pms.job.service.JobService;
import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 12. 6.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 12. 6.      김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Slf4j
@Controller
@RequestMapping("/adminprojectDetail") 	
public class AdminProjectDetailController {
	
	@Inject
	private AdminProjectService aService;
	
	@Inject
	private JobService service;
	
	
	@GetMapping("{proSn}")
	public String adprojectDeatail(@PathVariable String proSn , Model model) {
		
		//일감목록
		List<PjobVO> job = aService.retrieveJobList(proSn);
		model.addAttribute("jobList", job);
		
		//일감 수, 이슈 수
		ChartVO cVO = aService.retrieveCnt(proSn);
		model.addAttribute("cnt",cVO);
		
		model.addAttribute("proSn",proSn);
		
		
		return "adminproject/adprojectDetail";
	}
	
	
	@GetMapping("{proSn}/jobList")
	public String jobList(@PathVariable String proSn , Model model) {
		
		//일감목록
		List<PjobVO> job = aService.retrieveJobList(proSn);
		model.addAttribute("job", job);
		return "jsonView";
	}
	
	
	
}