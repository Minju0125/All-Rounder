package kr.or.ddit.groupware.board.faq.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.board.faq.service.FaqBoardService;

/**
 * @author 작성자명
 * @since Nov 18, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 18, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre> 
 */
@Controller
@RequestMapping("/faq/{bbsNo}")
public class FaqBoardRemoveController {

	@Inject
	private FaqBoardService service;
	
	@DeleteMapping 
	public String faqboardDelete(@PathVariable int bbsNo) {
		
		String viewName="";
		ServiceResult result = service.removeFaqBoard(bbsNo);
		if(result.equals(ServiceResult.OK)) {
			viewName="redirect:/faq";
		}else {
			viewName="redirect:/faq/{bbsNo}";
		}
		return viewName;
	}
	
	
}
