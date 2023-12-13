package kr.or.ddit.cal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

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

import kr.or.ddit.cal.service.CalService;
import kr.or.ddit.vo.CalCheckVO;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.pms.CalendarVO;
import kr.or.ddit.vo.pms.ProjectVO;

/**
 * @author 작성자명
 * @since 2023. 11. 14.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 14.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/cal")
public class CalController {
	
	@Inject
	private CalService service;
	
	/**
	 * 범위 select 조회
	 * @return
	 */
	@ModelAttribute("listType")
	public List<CommonVO> listType() {
		return service.selectListType();
	}
	
	/**
	 * 페이지 이동
	 * @return
	 */
	@GetMapping()
	public String test(Authentication principal, Model model) {
		String empCd = principal.getName();	
		if(empCd.equals("admin")) {
			return "admin/caladmin/caladmin";			
		}else {			
			List<ProjectVO> proList = service.selectProjectList(empCd);
			model.addAttribute("proList", proList);
			return "pms/calendar/calendar";
		}
	}
	
	/**
	 * 해당 사원 관련 일정 조회
	 * @param principal
	 * @param model
	 * @return
	 */
	@GetMapping(value="/list")
	@ResponseBody
	public List<Cal> calendar(Authentication principal, @RequestParam(name = "searchCd", required = false) String searchCd) {
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + searchCd);
		String empCd = principal.getName();	
		
		String C = null;
		String P = null;
		String D = null;
		String I = null;
		
		if(searchCd != null && searchCd.length() != 0) {
			for(int i=0; i<searchCd.length(); i++) {
				String checkBox = searchCd.substring(i, i+1);
				if(checkBox.equals("C")) {
					C = checkBox;
				}else if(checkBox.equals("P")) {
					P = checkBox;
				}else if(checkBox.equals("D")) {
					D = checkBox;
				}else if(checkBox.equals("I")) {
					I = checkBox;
				}
			}
		}
		CalCheckVO checkVO = new CalCheckVO();
		
		checkVO.setC(C);
		checkVO.setP(P);
		checkVO.setD(D);
		checkVO.setI(I);
		
		
		checkVO.setEmpCd(empCd);

		System.out.println("%%%%%%%%%%%%%%%%%"+checkVO);
		checkVO= service.selectList(checkVO);
		
//		CalendarVO vo = new CalendarVO();
//		vo.setEmpCd(empCd);
//		List<CalendarVO> empCal = service.selectList(empCd);
//		'E221001005'	
		List<Cal> calList = checkVO.getCalList().stream().map(Cal::new)
								.collect(Collectors.toList());
		
		
		return calList;
	}
	
	
	/**
	 * 옵션 선택 한 후 원하는 scheduleCd 코드 생성. MAX 값 구하기.
	 * @param schType
	 * @param model
	 * @return
	 */
	@GetMapping(value="/code")
	public String codeType(@RequestParam(name = "schType", required = false) String schType, Model model) {
		String code = service.selectType(schType);
		model.addAttribute("code", code);
		return "jsonView";
	}
	
	/**
	 * 일정 추가
	 * @param calVO
	 * @return
	 */
	@PostMapping
	public String insertCal(@RequestBody CalendarVO calVO) {
		System.out.println("★★★★"+calVO);
		service.insertCal(calVO);
		return "jsonView";
	}
	
	@GetMapping(value = "/detail")
	public String detailCal(@RequestParam String scheduleCd, Model model) {
		CalendarVO detailCal = service.detailCal(scheduleCd);				
		model.addAttribute("detailCal", detailCal);		
		return "jsonView";
	}
	
	@PutMapping()
	public String updateCal(@RequestBody CalendarVO calVO) {
		service.updateCal(calVO);		
		return "jsonView";
	}
	
	@DeleteMapping()
	public String deleteCal(@RequestBody String scheduleCd) {
		service.deleteCal(scheduleCd);
		return "jsonView";
	}
	
	@PutMapping("/drag")
	@ResponseBody
	public String dragUpdate(@RequestBody CalendarVO calVO) {
		
		System.out.println("※※※※※※"+calVO);
		service.dragUpdate(calVO);
		return "jsonView";
	}
	
	
}
