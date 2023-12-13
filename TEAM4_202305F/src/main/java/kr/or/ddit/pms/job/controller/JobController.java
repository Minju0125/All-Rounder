package kr.or.ddit.pms.job.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.pms.job.service.JobService;
import kr.or.ddit.pms.project.service.ProjectService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.pms.ChargerVO;
import kr.or.ddit.vo.pms.IssueVO;
import kr.or.ddit.vo.pms.PAtchVO;
import kr.or.ddit.vo.pms.PLogVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 11.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet  
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 09.    김보영         최초작성
 * 2023. 11. 10.    김보영         일감상태,차트
 * 2023. 11. 11.    김보영         참여자 AG그리드
 * 2023. 11. 14.    김보영         일감목록
 * 2023. 11. 16.    김보영         일감등록 , 삭제
 * 2023. 11. 17.    김보영         나의 일감, 일감상세조회
 * 2023. 11. 26.    김보영         파일처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */


@Slf4j
@Controller
@RequestMapping("/job/{proSn}")
public class JobController {
	
	@Inject
	private JobService service;
	
	@Inject
	private ProjectService pService;
	
	@Value("#{appInfo.jobFiles}")
	private Resource jobFiles;
	
	
	//이슈 목록
	@GetMapping("issueList")
	public String pMemberList(
			Model model
			, @PathVariable String proSn
			) {
		//해당 프로젝트의 이슈참조 일감 내역
		List<IssueVO> issueList = service.retrieveRefIssueList(proSn);
		model.addAttribute("issueList",issueList);
		return "jsonView";
	}
	
	
	@GetMapping("home")
	public String jobHome(
			Authentication principal 
			, Model model
			, @PathVariable String proSn
			) {
	
		model.addAttribute("proSn" ,proSn);
		//로그인한 직원의 정보
		String loginEmpCd = principal.getName();
		
		//요청내역
		List<PjobVO> myRequest = service.retrieveReq(loginEmpCd);
		model.addAttribute("myReq", myRequest);
	
		//일감의 상태
		PjobVO jVO = new PjobVO();
		jVO.setEmpCd1(loginEmpCd);
		jVO.setProSn(proSn);
		PjobVO jobCnt = service.retrieveJobCount(jVO);
		model.addAttribute("jobCnt", jobCnt);
		System.out.println(jobCnt);
		
		
		//프로젝트명 목록
		List<ProjectVO> proj = pService.selectProjectList(loginEmpCd);
		model.addAttribute("proj", proj);
		System.out.println(proj);

		//참여자목록
		PmemberVO pMVO = new PmemberVO();
		pMVO.setProSn(proSn);
		List<PmemberVO> proM = service.retrieveProMember(pMVO);
		
		//담당자인지 확인
		
		String role = "member";
		for(PmemberVO member : proM) {
			if(member.getProLeader().equals("Y")) {
				if(member.getEmpCd().equals(loginEmpCd)) {
					role = "leader";
					break;
				}
			}
		}
		model.addAttribute("role", role);
		model.addAttribute("proM", proM);
		
		//일감목록
		List<PjobVO> job = service.retrieveUJobList(proSn,loginEmpCd);
		model.addAttribute("jobList", job);
		
		
		//프로젝트정보
		ProjectVO pVO = service.retrieveProject(proSn);
		model.addAttribute("proInfo", pVO);
		
	
		return "job/jobHome";
	}
	
	

	@GetMapping("pMemberList")
	public String pMemberList(
			Authentication principal 
			, Model model
			, @PathVariable String proSn
			) {
		
		//프로젝트 참여자 리스트
		PmemberVO pMVO = new PmemberVO();
		//String proSn ="P23001";
		pMVO.setProSn(proSn);
		List<PmemberVO> proM = service.retrieveProMember(pMVO);
		model.addAttribute("proM", proM);
		System.out.println(proM);
		return "jsonView";
	}
	
	//일감리스트
	@GetMapping("jobList")
	public String jobList(
		@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
		, Authentication principal 
		, Model model
		, @PathVariable String proSn
		, @ModelAttribute("simpleCondition") SearchVO simpleCondition
		) {
		
		PjobVO jVO = new PjobVO();
		jVO.setProSn(proSn);
		
		//페이징
		PaginationInfo<PjobVO> paging = new PaginationInfo<>(8, 3);
		paging.setDetailCondition(jVO);	//키워드 검색 조건
		paging.setSimpleCondition(simpleCondition);
		
		paging.setCurrentPage(currentPage);
		service.retrieveJobList(paging);
		
		model.addAttribute("paging", paging);
		 
		log.info("일감목록 성공");
		
		return "jsonView";
	}
	
	//일감등록
	@PostMapping("insert")
	@ResponseBody
	public Map<String, String> jobInsert(
		PjobVO jVO
		, PAtchVO pAVO
		, @PathVariable String proSn
		, @RequestParam(value="tempEmpCd", required = true) String tempEmpCd
		, RedirectAttributes redirectAttributes	
		, Authentication principal 
		) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		//jVO.setJobuSn(jVO.getJobSn());
		int jobSn=service.getJobSn();
		
		String loginEmpCd = principal.getName();
		
		//담당자 세팅
		List<ChargerVO> chargerList = new ArrayList<ChargerVO>();
		for(String empCd : tempEmpCd.split(",")) {
			ChargerVO cVO = new ChargerVO();
			cVO.setEmpCd(empCd);
			chargerList.add(cVO);
		}
		jVO.setChargerList(chargerList);
		
		jVO.setJobWriter(loginEmpCd);
		jVO.setJobSn(jobSn);
		
		ServiceResult result = service.createJob(jVO);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;

		default:
			map.put("success", "N");
			break;
		}
		
		map.put("jobSn" ,String.valueOf(jVO.getJobSn()));
		
		
		return map;
	}
	
	//일감삭제
	@ResponseBody  
	@DeleteMapping("{jobSn}/delete")
	public Map<String, String> jobDelete(
		PjobVO jVO
		, @PathVariable String proSn
		, @PathVariable int jobSn
		, RedirectAttributes redirectAttributes	
		, Authentication principal 
		, @RequestParam(value="tempEmpCd", required = true) String tempEmpCd
		) {
		
		jVO.setEmpCd1(tempEmpCd);
		
		Map<String, String> map = new HashMap<String, String>();
		
		//일감등록
		ServiceResult result = service.removeJob(jVO);
		
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
	
	//일감수정
	@ResponseBody  
	@PutMapping("{jobSn}/update")
	public Map<String, String> jobUpdate(
		Authentication principal 
		, Model model
		, @PathVariable String proSn
		, @PathVariable int jobSn
		, @ModelAttribute("jVO") PjobVO jVO
		, @RequestParam(value="tempEmpCd", required = true) String tempEmpCd
		) {
		
		//로그인한 직원의 정보
		String loginEmpCd = principal.getName();
		jVO.setEmpCd1(loginEmpCd); //로그인한 사람 임시저장
		
		//담당자 세팅
		List<ChargerVO> chargerList = new ArrayList<ChargerVO>();
		for(String empCd : tempEmpCd.split(",")) {
			ChargerVO cVO = new ChargerVO();
			cVO.setEmpCd(empCd);
			chargerList.add(cVO);
		}
		jVO.setChargerList(chargerList);
		
		Map<String, String> map = new HashMap<String, String>();
		
		//일감등록
		ServiceResult result = service.modifyJob(jVO);
		
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
	
	//나의일감
	@GetMapping("myjob")
	public String myJobList(
		Authentication principal 
		, Model model
		, @PathVariable String proSn	
		) {
		model.addAttribute("proSn" ,proSn);
		//로그인한 직원의 정보
		String loginEmpCd = principal.getName();
		
		ChargerVO cVO= new ChargerVO();
		cVO.setEmpCd(loginEmpCd);
		cVO.setProSn(proSn);
		
		List<PjobVO> jVO = service.retrieveMyJob(cVO);
		model.addAttribute("myjob",jVO); 
		return "jsonView";
	}
	
	
	
	@GetMapping("{jobSn}/detail")
	public String jobDetail(
			Authentication principal 
			, Model model
			, @PathVariable String proSn
			, @PathVariable int jobSn
		) {
		
		model.addAttribute("proSn" ,proSn);
		//로그인한 직원의 정보
		String loginEmpCd = principal.getName();
		model.addAttribute("loginEmpCd",loginEmpCd);
		
		
		//프로젝트명 목록
		List<ProjectVO> proj = pService.selectProjectList(loginEmpCd);
		model.addAttribute("proj", proj);
		System.out.println(proj);

		//참여자목록
		PmemberVO pMVO = new PmemberVO();
		pMVO.setProSn(proSn);
		List<PmemberVO> proM = service.retrieveProMember(pMVO);
		
		//팀원인지 리더인지 구분하기 위함
		
		String role = "member";
		for(PmemberVO member : proM) {
			if(member.getProLeader().equals("Y")) {
				if(member.getEmpCd().equals(loginEmpCd)) {
					role = "leader";
					break;
				}
			}
		}
		model.addAttribute("role", role);
		model.addAttribute("proM", proM);
		
		
		//프로젝트정보
		ProjectVO pVO = service.retrieveProject(proSn);
		model.addAttribute("proInfo", pVO);
		
		//일감상세보기
		PjobVO dJob = new PjobVO();
		dJob.setJobSn(jobSn);
		dJob.setProSn(proSn);
		PjobVO dJVO = service.retrievejobDetail(dJob);
		model.addAttribute("detail",dJVO);
		
		List<ChargerVO> cList = service.retrieveCharger(jobSn);
		model.addAttribute("chargerList",cList);
		
		//하위일감 리스트 
		List<PjobVO> LowJList = service.retrieveLowJList(jobSn);
		model.addAttribute("LowJList",LowJList);
		
		//로그목록
		List<PLogVO> logList = service.retrieveLogList(jobSn);
		model.addAttribute("logList",logList);
		
		//파일리스트
		PAtchVO pAVO = new PAtchVO();
		pAVO.setProFileCode(dJVO.getProFileCd());
		List<PAtchVO> fileList = service.retrieveFileList(pAVO);
		model.addAttribute("fileList",fileList);

		//일감목록
		List<PjobVO> job = service.retrieveUJobList(proSn,loginEmpCd);
		model.addAttribute("jobList", job);
				
		
		return "job/jobDetail";
	}
	
	
	//파일삭제
	@DeleteMapping("deleteFile")
	@ResponseBody
	public Map<String, String > deleteFile(
		@RequestBody PAtchVO originPVO
		,Authentication principal 
		){
		String loginEmpCd= principal.getName();
		
		Map<String, String> map = new HashMap<String, String>();
		
		ServiceResult result =  service.removeFile(originPVO,loginEmpCd);
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
	
	
	
	
	
	//일감파일 다운로드
	@RequestMapping("download")
	public ResponseEntity<InputStreamResource> downloadJobFile(
			HttpServletResponse response
			, PAtchVO pAVO
			) throws IOException {
		
		//파일 조회
		PAtchVO jobFile = service.retrieveFile(pAVO);
		File tmpFile = new File(jobFiles.getFile().getAbsolutePath()+"/"+jobFile.getProAtchSnm());
		
		InputStream res = new FileInputStream(tmpFile) {
			@Override
			public void close() throws IOException {
				super.close();
			}
		};

		// 응답 생성
		return ResponseEntity.ok().contentLength(tmpFile.length())
				.contentType(MediaType.parseMediaType(jobFile.getProAtchtype())) // 파일에 맞는 컨텐츠 타입 지정
				.header("Content-Disposition", "attachment;filename="+URLEncoder.encode(jobFile.getProAtchnm(),"UTF-8")) // 파일 이름 설정
				.body(new InputStreamResource(res));
	}
	
}
