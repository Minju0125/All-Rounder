package kr.or.ddit.pms.issue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.pms.issue.service.IssueService;
import kr.or.ddit.pms.job.service.JobService;
import kr.or.ddit.pms.project.service.ProjectService;
import kr.or.ddit.vo.pms.IssueVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 17.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------    		 --------    ----------------------
 * 2023. 11. 17.      김보영         최초작성
 * 2023. 11. 23.      김보영         이슈등록
 * 2023. 11. 24.      김보영         칸반보드
 * 2023. 11. 29.      김보영         수정, 삭제 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Slf4j
@Controller
@RequestMapping("/issue/{proSn}")
public class IssueController {
	
	
	
	@Inject
	private ProjectService pService;
	
	@Inject
	private IssueService service;
	
	@Inject
	private JobService jService;
	
	
	//칸반보드로 수정하기
	@PutMapping("update")
	public String modifyIssue(
		@RequestBody IssueVO issueVO
		,@PathVariable String proSn
		,Model model
		) {
		
		ServiceResult result = service.modifyIssue(issueVO);
		model.addAttribute("result", result);
		return "jsonView";
	}
	
	//이슈수정
	@ResponseBody
	@PutMapping("updateInfo/{issueNo}")
	public Map<String,String> updateIssueInfo(
		@PathVariable String proSn	
		, @PathVariable int issueNo	
		, IssueVO iVO
		){
		Map<String, String> map = new HashMap<String, String>();
		
		iVO.setIssueNo(issueNo);
		iVO.setProSn(proSn);
		ServiceResult result = service.modifyIssueInfo(iVO);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;

		default:
			map.put("success", "N");
			break;
		}
		return map;
		
	}	
	//이슈삭제
	@ResponseBody
	@DeleteMapping("delete/{issueNo}")
	public Map<String,String> deleteIssue(
			@PathVariable String proSn	
			, @PathVariable int issueNo	
			, IssueVO iVO
			){
		Map<String, String> map = new HashMap<String, String>();
		
		iVO.setIssueNo(issueNo);
		iVO.setProSn(proSn);
		ServiceResult result = service.removeIssue(iVO);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;
			
		default:
			map.put("success", "N");
			break;
		}
		return map;
		
	}	
	
	
	
	
	//이슈상세
	@GetMapping("detail/{issueNo}")
	public String detailIssue(
		IssueVO iVO
		, @PathVariable String proSn
		, @PathVariable int issueNo
		, Model model
		, Authentication principal 
		) {
		
		IssueVO detailIssue = new IssueVO();
		detailIssue.setIssueNo(issueNo);
		detailIssue = service.retrieveIssue(issueNo);
		
		IssueVO findJobSn = new IssueVO();
		findJobSn = service.retrieveJobSn(issueNo);
		model.addAttribute("jonSn", findJobSn);
//		if(findJobSn.getJobSn() != null) {
//			detailIssue.setJobSn(findJobSn.getJobSn());
//		}
		model.addAttribute("detail", detailIssue);
		
		//일감목록
		List<PjobVO> job = service.retrieveJobList(proSn);
		model.addAttribute("jobList", job);
		System.out.println(job);
		
		//차트 값 가져오기 
		//일반
		IssueVO issueChart = new IssueVO();
		issueChart.setProSn(proSn);
		List<IssueVO> chart = service.chartValue(issueChart);
		model.addAttribute("chart",chart);
		
		
		//프로젝트정보
		ProjectVO pVO = jService.retrieveProject(proSn);
		model.addAttribute("proInfo", pVO);
		
		
		//프로젝트명 목록
		String loginEmpCd = principal.getName();
		List<ProjectVO> proj = pService.selectProjectList(loginEmpCd);
		model.addAttribute("proj", proj);
		System.out.println(proj);
		
		//참여자목록
		PmemberVO pMVO = new PmemberVO();
		pMVO.setProSn(proSn);
		List<PmemberVO> proM = jService.retrieveProMember(pMVO);
		
		//팀원인지 리더인지 구분하기 위함
		
		String role = "member";
		for(PmemberVO member : proM) {
			if(member.getProLeader().equals("Y")) {
				if(member.getEmpCd().equals(principal.getName())) {
					role = "leader";
					break;
				}
			}
		}
		model.addAttribute("role", role);

		
		return "issue/issueDetail";
	}
	
	
	@PostMapping("insert")
	@ResponseBody
	public Map<String,String> insertIssue(
		IssueVO iVO
		, @PathVariable String proSn
		, Authentication principal 
		){
		
		Map<String, String> map = new HashMap<String, String>();
		
		String loginEmpCd = principal.getName();
		
		iVO.setEmpCd(loginEmpCd);
		iVO.setProSn(proSn);
		
		//이슈등록
		ServiceResult result =service.createIssue(iVO);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;

		default:
			map.put("success", "N");
			break;
		}
		return map;
	}
	
	
	
	
	
	@GetMapping("home")
	public String issueHome(
		Authentication principal 
		, Model model
		, @PathVariable String proSn
		) {
		model.addAttribute("proSn" ,proSn);
		String empCd = principal.getName();
		
		//프로젝트명 목록
		List<ProjectVO> proj = pService.selectProjectList(empCd);
		model.addAttribute("proj", proj);
		System.out.println(proj);
		
		//일감목록
		List<PjobVO> job = service.retrieveJobList(proSn);
		model.addAttribute("jobList", job);
		System.out.println(job);
		
		//프로젝트정보
		ProjectVO pVO = jService.retrieveProject(proSn);
		model.addAttribute("proInfo", pVO);
		
		//참여자목록
		PmemberVO pMVO = new PmemberVO();
		pMVO.setProSn(proSn);
		List<PmemberVO> proM = jService.retrieveProMember(pMVO);
		
		//팀원인지 리더인지 구분하기 위함
		
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
		
		
		return "issue/issueHome";
	}
	
	//이슈 상태에 따른 이슈목록 조회
	@GetMapping("list")
	public String issueSelect(
		@PathVariable String proSn
		, Model model
		, IssueVO iVO
		){
		
		//Map<String, String> map = new HashMap<String, String>();
		
		iVO.setProSn(proSn);
		
		iVO.setIssueSttus("1");
		List<IssueVO> issueList1 = service.retrieveIssueList(iVO);
		model.addAttribute("issueList1",issueList1);
		
		iVO.setIssueSttus("2");
		List<IssueVO> issueList2 = service.retrieveIssueList(iVO);
		model.addAttribute("issueList2",issueList2);
		
		iVO.setIssueSttus("3");
		List<IssueVO> issueList3 = service.retrieveIssueList(iVO);
		model.addAttribute("issueList3",issueList3);
		
//		if(issueList.size() >0) {
//			map.put("success", "목록조회성공");
//		}else{
//			map.put("success", "목록조회실패");
//		}
		
		return "jsonView";
	}
	

}
