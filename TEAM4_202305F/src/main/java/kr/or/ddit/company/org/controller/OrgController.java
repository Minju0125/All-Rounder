package kr.or.ddit.company.org.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import kr.or.ddit.company.org.service.OrgService;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
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
 * 2023. 11. 8.      오경석       최초작성       org.jsp에 adaptee로 변환해서 넣으려고 했지만 jsp에서 id와 pid를 내가 컨트롤 할 수 없어 List<EmployeeVO> 로 보내고 jsp에서 조작함.		
 * 2023. 11. 10.      오경석       조직도 가로 완성       		
 * 2023. 11. 27.      송석원       조직도 가로에 프로젝트 멤버 추가시 해당 프로젝트에 들어가있는 인원을 제외한다른 사람들을 출력       		
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j 
@Controller
@RequestMapping("/org")
public class OrgController {

	@Inject
	private OrgService service;
	
	
	/**
	 * 처음 페이지 이동
	 * @param model
	 * @return
	 */
	@GetMapping
	public String orgList(Model model) {
		List<EmployeeVO> list = service.selectListOrg();
		model.addAttribute("list", list);
		return "company/listorg";
	}
	
	
	/**
	 * 조직도
	 * 세로 출력 조회
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/list")
	@ResponseBody
	public Map<String,Object> organicationList() {
		List<EmployeeVO> list = service.selectListOrg();
		List<DeptVO> dept = service.selectListDept();
//		List<Org> orgList = list.stream().map(Org::new)
//					.collect(Collectors.toList());
		
		Map<String,Object> mjData = new HashMap<String, Object>();
		mjData.put("list", list);
		mjData.put("dept", dept);

		return mjData;
	}

	//-----------------------------------------------------------------------------------
		
	
	/**
	 * 조직도 
	 * 페이지 이동
	 * @return
	 */
	@GetMapping({"/organization","/organization/pop"})
	public String organizationList(Authentication principal,  HttpServletRequest req) {
		String admin = principal.getName();
		if(admin.equals("admin")) {
			return "admin/orgadmin/orgadmin";
		}
		String mappinURI = (String)req.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		
		log.info("%%%%%%% {}",mappinURI);
		if(mappinURI.equals("/org/organization/")) {
			log.info("*************************");
			return "company/organization";			
		}else {
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&");
			return "/groupware/company/organization2";
		}
		
//		return "company/organization";
		
	}
	 
	/**
	 * 조직도
	 * 가로 전체 출력
	 * 매핑주소를 하나더 줌을로써다른 다른 매핑주소를타면 프로젝트 참여자 외 인원이보임
	 * pathVariable required에 false를 줘 do로 매핑되면 false 로 널값이 들어가고 그 외는 proSn의 값이 넘어감
	 * @param model
	 * @return
	 */
	@GetMapping(value={"do","do/{proSn}"})
	public String orgTest(@PathVariable(required = false) String proSn, Model model) {
		  

		List<EmployeeVO> list = service.selectListOrg(proSn); 
		List<DeptVO> dept = service.selectListDept();
//		List<Org> orgList = list.stream().map(Org::new)
//					.collect(Collectors.toList());
		
		
		model.addAttribute("list", list);
		model.addAttribute("dept", dept);
		
		
 
		
		return "jsonView";
	}
	
	
//	/**
//	 * 조직도 프로젝트에서 선택한 인원을 제외한 인원출력
//	 * 가로 전체 출력
//	 * @param model
//	 * @return
//	 */
//	@GetMapping(value="/pmem/{proSn}") 
//	@ResponseBody 
//	public List<EmployeeVO> pmemOrg(@PathVariable String proSn, Model model) {  
//		List<EmployeeVO> list = service.selectPmemListOrg(proSn);
//		System.out.println("================================="+proSn);
//		
////		List<DeptVO> dept = service.selectListDept();
//		  
////		model.addAttribute("list", list);
////		model.addAttribute("dept", dept); 
////		
////		System.out.println("리스트값"+list);
////		System.out.println("dept"+dept);  
//		
//		return list;
//	}
	
	/**
	 * 조직도
	 * 부서 추가
	 * @param deptName
	 * @param model
	 * @return
	 */
	@PostMapping
	public String insertDept(@RequestParam("deptName") String deptName) {
		System.out.println(deptName);
		DeptVO dVO=new DeptVO();
		dVO.setDeptName(deptName);
		service.insertDept(dVO);		
		
		
		return "jsonView";
	}
	
	/**
	 * 조직도
	 * 직원 상세 조회
	 * @param empCd
	 * @param model
	 * @return
	 */
	@GetMapping("{empCd}")
	public String selectOrg(@PathVariable("empCd") String empCd, Model model) {
		EmployeeVO emp = service.selectOrg(empCd);
		model.addAttribute("emp", emp);
		return "jsonView";
	}
	
	
	@DeleteMapping
	@ResponseBody
	public String deleteOrg(@RequestParam String deptCd) {
		service.deleteDept(deptCd);
		return "jsonView";
	}
	
	
}
