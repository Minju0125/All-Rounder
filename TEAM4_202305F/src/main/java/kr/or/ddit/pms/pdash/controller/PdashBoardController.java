package kr.or.ddit.pms.pdash.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ---------------------- 
 * 2023. 11. 15.     송석원         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/pms/pdash")
public class PdashBoardController { 

	@GetMapping("{empCd}")
	public String PDashBoardHome( 
			Authentication principal
			, Model model
			, @PathVariable String empCd
		) {
	
		model.addAttribute("emp",empCd);
		
		return "pms/pdash/pdashBoard"; 
	}
	
}
