package kr.or.ddit.groupware.board.notice.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.groupware.board.notice.service.NoticeBoardService;
import kr.or.ddit.validate.grouphint.UpdateGroup;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 11.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 11.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/notice/{bbsNo}/edit")
@SessionAttributes("editboard")
public class NoticeBoardModifyController {

	@Inject
	private NoticeBoardService service;
	
	@GetMapping
	public String editForm(@PathVariable int bbsNo, Model model) {
		if(!model.containsAttribute("editboard")) {
			BoardVO editboard = service.retrieveNoticeBoard(bbsNo);
			model.addAttribute("editboard", editboard);
		}
		
		return "notice/noticeEdit";
	}
	
	@PutMapping
	public String boardUpdate(
			@Validated(UpdateGroup.class) @ModelAttribute("editboard") BoardVO editboard
			, BindingResult errors
			, SessionStatus sessionStatus
			, RedirectAttributes redirectAttributes
			) {
		log.info("===================Notice update method request=================");
		
		String viewName = null;
		if(!errors.hasErrors()) {
			// 검증통과
				service.modifyNoticeBoard(editboard);
				sessionStatus.setComplete();
				viewName = "redirect:/notice/{bbsNo}";
		}else {
			String attrName = BindingResult.MODEL_KEY_PREFIX+"editboard";
			redirectAttributes.addFlashAttribute(attrName, errors);
			viewName = "redirect:/notice/{bbsNo}/edit";	// redirect 후 get 요청
		}
		return viewName;
	}
	
	
}
