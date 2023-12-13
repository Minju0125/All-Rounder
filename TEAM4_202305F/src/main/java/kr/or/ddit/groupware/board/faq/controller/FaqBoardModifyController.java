package kr.or.ddit.groupware.board.faq.controller;

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

import kr.or.ddit.groupware.board.faq.service.FaqBoardService;
import kr.or.ddit.validate.grouphint.UpdateGroup;
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
@RequestMapping("/faq/{bbsNo}/edit")
@SessionAttributes("Faqeditboard")  
public class FaqBoardModifyController {

	@Inject
	private FaqBoardService service;
	
	@GetMapping
	public String editForm(@PathVariable int bbsNo, Model model) {
		System.out.println("내 게시파파아아아아아아앙아아"+bbsNo);
		System.out.println("aaa"+!model.containsAttribute("Faqeditboard"));
		if(!model.containsAttribute("Faqeditboard")) {
			BoardVO Faqeditboard = service.retrieveFaqBoard(bbsNo);
			System.out.println("ㅍ아아아아아아아안니ㅓ자ㅟㄴ"+Faqeditboard);
			model.addAttribute("Faqeditboard", Faqeditboard);
		}
	
		return "faq/faqEdit";
	}
	   
	@PutMapping
	public String boardUpdate(
			@Validated(UpdateGroup.class) @ModelAttribute("Faqeditboard") BoardVO Faqeditboard
			, BindingResult errors
			, SessionStatus sessionStatus
			, RedirectAttributes redirectAttributes
			) {
		log.info("===================FAQ update method request=================");
		String viewName = null;
		if(!errors.hasErrors()) {
			// 검증통과
				service.modifyFaqBoard(Faqeditboard); 
				sessionStatus.setComplete();
				viewName = "redirect:/faq";
		}else {
			String attrName = BindingResult.MODEL_KEY_PREFIX+"Faqeditboard"; 
			redirectAttributes.addFlashAttribute(attrName, errors);
			viewName = "redirect:/faq/{bbsNo}/edit";	// redirect 후 get 요청 
		}
		return viewName;
	}
	
	
}
