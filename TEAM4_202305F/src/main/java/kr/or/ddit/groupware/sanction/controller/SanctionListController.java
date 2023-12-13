package kr.or.ddit.groupware.sanction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.admin.account.dao.AccountDAO;
import kr.or.ddit.groupware.sanction.dao.SanctionFormDAO;
import kr.or.ddit.groupware.sanction.service.SanctionService;
import kr.or.ddit.paging.BootstrapPaginationRenderer;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.SanctionFormVO;
import kr.or.ddit.vo.groupware.SanctionVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 24.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 24.  전수진       최초작성
 * 2023. 11. 27.  전수진       결재자 결재대기문서 list 생성
 * 2023. 11. 29.  전수진       수신자 수신문서 list, 부서문서 list
 * 2023. 12. 08.  전수진       전체 결재문서 list
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/sanction")
public class SanctionListController {
	
	@Inject
	private SanctionService service;
	@Inject
	private SanctionFormDAO formDAO;
	@Inject
	private AccountDAO accountDAO;
	
	@ModelAttribute("formlist")
	public List<SanctionFormVO> formlist(){
		return formDAO.selectSanctionFormList();
	} 
	
	@ModelAttribute("deptlist")
	public List<DeptVO> deptlist(){
		return accountDAO.selectDeptList();
	} 
	
	@GetMapping("/drafter")
	public String drafterSanctionDocUI() {
		return "sanctiondoc/sanctionDrafterList";
	}
	
	@GetMapping("/drafterData")
	public String drafterSanctionDocList(
			Authentication authentication
			,@ModelAttribute("detailCondition") SanctionVO detailCondition
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			) {
		String drafter = authentication.getName();
		
		// 기안자가 기안한 list 조회
		PaginationInfo<SanctionVO> paging = new PaginationInfo<>(10,3);
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("drafter", drafter);
		paging.setVariousCondition(variousCondition);
		
		paging.setCurrentPage(currentPage);
		paging.setDetailCondition(detailCondition);
		log.info("detailCondition==========={}",detailCondition);
		service.retrieveSanctionList(paging);
		
		paging.setRenderer(new BootstrapPaginationRenderer());
		
		model.addAttribute("paging", paging);
		
		return "jsonView";
	}
	
	@GetMapping("/sanctner")
	public String sanctnerSanctionDocUI() {
		return "sanctiondoc/sanctionSanctnerList";
	}
	
	
	@GetMapping("/sanctnerData")
	public String sanctnerSanctionDocList(
			Authentication authentication
			,@ModelAttribute("detailCondition") SanctionVO detailCondition
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			) {
		String sanctner = authentication.getName();
		
		// 결재자의 결재해야할 list 조회
		PaginationInfo<SanctionVO> paging = new PaginationInfo<>(10,3);
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("sanctner", sanctner);
		paging.setVariousCondition(variousCondition);
		
		paging.setCurrentPage(currentPage);
		paging.setDetailCondition(detailCondition);
		log.info("detailCondition==========={}",detailCondition);
		service.retrieveSanctnerSanctionList(paging);
		
		paging.setRenderer(new BootstrapPaginationRenderer());
		
		model.addAttribute("paging", paging);
		
		return "jsonView";

	}
	
	
	@GetMapping("/rcyer")
	public String rcyerSanctionDocUI() {
		return "sanctiondoc/sanctionRcyerList";
	}
	
	
	@GetMapping("/rcyerData")
	public String rcyerSanctionDocList(
			Authentication authentication
			,@ModelAttribute("detailCondition") SanctionVO detailCondition
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			) {
		String sanctnRcyer = authentication.getName();
		
		// 결재자의 결재해야할 list 조회
		PaginationInfo<SanctionVO> paging = new PaginationInfo<>(10,3);
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("sanctnRcyer", sanctnRcyer);
		paging.setVariousCondition(variousCondition);
		
		paging.setCurrentPage(currentPage);
		paging.setDetailCondition(detailCondition);
		service.retrieveRcyerSanctionList(paging);
		
		paging.setRenderer(new BootstrapPaginationRenderer());
		
		model.addAttribute("paging", paging);
		
		return "jsonView";

	}

	
	@GetMapping("/dept")
	public String deptSanctionDocUI() {
		return "sanctiondoc/sanctionDeptList";
	}
	
	
	@GetMapping("/deptData")
	public String deptSanctionDocList(
			Authentication authentication
			, SanctionVO detailCondition
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			) {
		log.info("deptSanctionDocList->detailCondition : " + detailCondition);
		
		String deptCd = accountDAO.selectEmployee(authentication.getName()).getDeptCd();
		
		// 로그인한 사원의 부서문서 list 조회
		PaginationInfo<SanctionVO> paging = new PaginationInfo<>(10,3);
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("deptCd", deptCd);
		paging.setVariousCondition(variousCondition);
		
		paging.setCurrentPage(currentPage);
		paging.setDetailCondition(detailCondition);
		
		log.info("detailCondition=========={}", detailCondition);
		service.retrieveDeptSanctionList(paging);
		
		paging.setRenderer(new BootstrapPaginationRenderer());
		
		model.addAttribute("paging", paging);
		
		return "jsonView";

	}
	
	@GetMapping("/admin")
	public String adminSanctionDocUI() {
		return "admin/sanction/sanctionAdminList";
	}
	
	@GetMapping("/adminData")
	public String adminSanctionDocList(
			@ModelAttribute("detailCondition") SanctionVO detailCondition
			, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
			, Model model
			) {
		
		PaginationInfo<SanctionVO> paging = new PaginationInfo<>(10,3);
		
		paging.setCurrentPage(currentPage);
		paging.setDetailCondition(detailCondition);
		log.info("detailCondition==========={}",detailCondition);
		service.retrieveAdminSanctionList(paging);
		
		paging.setRenderer(new BootstrapPaginationRenderer());
		
		model.addAttribute("paging", paging);
		
		return "jsonView";
	}

}
