package kr.or.ddit.admin.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.account.service.AccountService;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.company.org.service.OrgService;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 15.    김보영         최초작성
 * 2023. 11. 18.    김보영         사원목록 출력
 * 2023. 11. 20.    김보영         CUD 진행
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */


@Slf4j
@Controller
@RequestMapping("/account")
public class AccountController {
	
	@Inject
	private OrgService orgService;
	
	@Inject
	private AccountService service;
	
	@Value("#{appInfo.accountFiles}")
	private Resource accountAtchFiles;
	
	@ModelAttribute("EmployeeVO")
	public EmployeeVO employee(Authentication authentication) {
		EmployeeVO emp = new EmployeeVO();
		emp.setEmpCd(authentication.getName());
		return emp;
	}
	
	@PostMapping("insert")
	@ResponseBody
	public Map<String, String> accountInsert(
		@ModelAttribute("employeeVO") EmployeeVO emp
		) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		ServiceResult result =  service.createEmp(emp);
		
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
	
	
	
	
	@PutMapping("delete")
	@ResponseBody
	public Map<String, String> accountDelete(
		@RequestParam(value="empCd" ,required = true) String empCd	
		) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		ServiceResult result =  service.removeEmp(empCd);
		
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
	public String accountHome(Model model) {
		List<DeptVO> deptList = orgService.selectListDept();
		model.addAttribute("deptList", deptList);
		return "account/accountHome";
	}
	
	@ResponseBody  
	@PutMapping("update")
	public Map<String, String> accountUpdate(
		@ModelAttribute("employeeVO") EmployeeVO emp
		){
		Map<String, String> map = new HashMap<String, String>();
		
		ServiceResult result = service.modifyEmp(emp);
		
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
	
	
	
	//직원 목록
	@GetMapping("empList")
	public String empList(
		EmployeeVO eVO
		, Model model
		) {
		List<EmployeeVO> empList = service.retrieveEmpList();
		model.addAttribute("empList", empList);
		log.info("직원목록조회");
		return "jsonView";
	}
	
	@GetMapping("form")
	public String accountForm(
		Model model	
		) {
		
		//부서명
		List<DeptVO> deptList=  service.deptList();
		
		model.addAttribute("deptList",deptList);
		
		//직급
		List<CommonVO> rankList = service.rankList();
		model.addAttribute("rankList",rankList);
	
		return "account/accountForm";
	}
	
	@ResponseBody
	@GetMapping("suprr")
	public List<EmployeeVO> empSuprrList(
		@RequestParam (value="deptCd" , required = true) String deptCd	
		) {
		
		List<EmployeeVO> suprrList =  service.empSuprrList(deptCd);
		return suprrList; 
	}
	
	
	@GetMapping("edit")
	public String accountEdit(
		@RequestParam(value="empCd" , required = true) String empCd
		, Model model	
		) {
		
		//부서명
		List<DeptVO> deptList=  service.deptList();
		
		model.addAttribute("deptList",deptList);
		
		//직급
		List<CommonVO> rankList = service.rankList();
		model.addAttribute("rankList",rankList);
		
		//한명의 직원 정보
		EmployeeVO emp = service.retrieveMember(empCd);
		model.addAttribute("emp",emp);
		
		return "account/accountEdit";
	}

}
