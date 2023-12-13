package kr.or.ddit.groupware.board.notice.controller;

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

import kr.or.ddit.groupware.board.notice.service.NoticeBoardService;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 9.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 9.   전수진       최초작성
 * 2023. 11. 11.   전수진       insert 부분 수정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/notice/new")
@SessionAttributes(names = "boardVO")
public class NoticeBoardCreateController {
	
	@Inject
	private NoticeBoardService service;
	
	@ModelAttribute("boardVO")
	public BoardVO board(Authentication authentication) {
		BoardVO board = new BoardVO();
		board.setEmpCd(authentication.getName());
		return board;
	}
	
	@GetMapping
	public String boardForm(BoardVO boardVO) {
		return "notice/noticeForm";
	}
	
	@PostMapping
	public String boardInsert(
		@Validated @ModelAttribute("boardVO") BoardVO boardVO
		, BindingResult errors
		, RedirectAttributes redirectAttributes
		, SessionStatus sessionStatus
		) {
		String viewName = null;
		
		
		//true : 오류 발생 /  false : 정상 실행
		log.info("errors.hasErrors() : " + errors.hasErrors());
		
		if(!errors.hasErrors()) {
			service.createNoticeBoard(boardVO);
			sessionStatus.setComplete();
			viewName = "redirect:/notice/"+boardVO.getBbsNo();
		} else {
			String fileName = BindingResult.MODEL_KEY_PREFIX+"boardVO";
			redirectAttributes.addFlashAttribute(fileName, errors);
			viewName = "redirect:/notice/new";
		}
		return viewName;
	}

}
