package kr.or.ddit.groupware.gdash.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.account.service.AccountService;
import kr.or.ddit.groupware.board.notice.dao.NoticeBoardDAO;
import kr.or.ddit.groupware.board.notice.service.NoticeBoardService;
import kr.or.ddit.groupware.gdash.service.DashBoardService;
import kr.or.ddit.pms.job.service.JobService;
import kr.or.ddit.pms.project.service.ProjectService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.pms.ChargerVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;

/**
 * @author 작성자명
 * @since 2023. 11. 11.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 11.     김보영         최초작성
 * 2023. 11. 22.     김보영         내일감연결
 * 2023. 12. 06.     전수진         필독게시판 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/dashBoard")
public class DashBoardController {
	
	@Inject
	private DashBoardService service;
	
	@Inject
	private ProjectService pService;
	
	private AccountService aService;
	
	@Inject
	private JobService jService;
	
	@Inject
	private NoticeBoardDAO nDao;
	
	@ModelAttribute("notice")
	public List<BoardVO> boardList(){
		return nDao.selectNoticeForDashBoard();
	}
	

	@GetMapping("{empCd}")
	public String DashBoardHome(
			Authentication principal
			, Model model
			, @PathVariable String empCd
		) {
		//로그인한 직원의 정보
		String loginEmpCd = principal.getName();
		
		EmployeeVO empVO=new EmployeeVO();
		empVO.setEmpCd(empCd);
		empVO = service.selectEmpForAuth(empVO);
		String name=empVO.getEmpName();
		model.addAttribute("emp",empVO);
		
		//프로젝트명 목록
		List<ProjectVO> proj = pService.selectProjectList(loginEmpCd);
		model.addAttribute("proj", proj);
		
		return "dashBoard/dashBoardHome";
	}
	
	
	//나의일감
	@ResponseBody
	@GetMapping("myjob")
	public Map<String, List<PjobVO>> myJobList(
		Authentication principal 
		) {
		//로그인한 직원의 정보
		String loginEmpCd = principal.getName();
		Map<String, List<PjobVO>> map = new HashMap<String, List<PjobVO>>();
		
		List<PjobVO> jVO = service.dashboardMyJob(loginEmpCd);
		
		map.put("myjob",jVO);
		return map;
	}
	
	
}
