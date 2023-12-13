package kr.or.ddit.groupware.board.faq.controller;
 

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import kr.or.ddit.groupware.board.faq.service.FaqBoardService;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Controller
@RequestMapping("/faq/new")
@SessionAttributes(names = "FaqboardVO") 
public class FaqBoardInsertController {
 
	@Inject
	private FaqBoardService service;
	
	
	@ModelAttribute("FaqboardVO")
	public BoardVO board(Authentication authentication) {
		BoardVO board = new BoardVO();
		board.setEmpCd(authentication.getName());
		return board;
	}
	
	@GetMapping
	public String boardForm(BoardVO boardVO) {
		return "faq/faqForm"; 
	}
	
	@PostMapping
	public String faqInsert(
			@Validated @ModelAttribute("faq") BoardVO boardVO
			, BindingResult errors
			, RedirectAttributes redirectAttributes
			, SessionStatus sessionStatus
			) { 
			String viewName = null;
			
			
			//true : 오류 발생 /  false : 정상 실행
			
			if(!errors.hasErrors()) {
				service.createFaqBoard(boardVO);
				sessionStatus.setComplete(); 
				viewName = "redirect:/faq"; 
			} else {
				String fileName = BindingResult.MODEL_KEY_PREFIX+"FaqboardVO";
				redirectAttributes.addFlashAttribute(fileName, errors);
				viewName = "redirect:/faq/new";   
			} 
			return viewName; 
		}
}
