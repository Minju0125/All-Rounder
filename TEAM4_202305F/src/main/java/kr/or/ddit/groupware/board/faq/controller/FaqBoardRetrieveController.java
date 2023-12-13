package kr.or.ddit.groupware.board.faq.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.groupware.board.faq.service.FaqBoardService;
import kr.or.ddit.groupware.board.faq.service.FaqBoardServiceImpl;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Nov 15, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 15, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/faq") 
public class FaqBoardRetrieveController {
	
	@Inject
	private FaqBoardService service;
	 
	@GetMapping("{bbsNo}")
	public String faqBoardView(@PathVariable int bbsNo, Model model) {
		BoardVO faq = service.retrieveFaqBoard(bbsNo);
		model.addAttribute("faq", faq);
		
		return "faq/faqView";  
	} 
	
	@GetMapping
	public String faqList( 
		@RequestParam(value = "searchType", required = false) String searchType
		, @RequestParam(value = "searchWord", required = false) String searchWord
		, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
		, Model model
		, @ModelAttribute("simpleCondition") SearchVO simpleCondition
		) {
		//페이징
		PaginationInfo<BoardVO> paging = new PaginationInfo<>(7,2);
		paging.setSimpleCondition(simpleCondition);
		
		paging.setCurrentPage(currentPage); 
		
		service.retrieveFaqBoardList(paging);//
		
		model.addAttribute("paging", paging);
		
		return "faq/faqboardList" ; 
	}
	
	
 
}
