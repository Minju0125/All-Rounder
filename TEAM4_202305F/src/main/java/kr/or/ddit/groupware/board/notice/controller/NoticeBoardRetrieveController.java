package kr.or.ddit.groupware.board.notice.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.groupware.board.notice.service.NoticeBoardService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
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
 * 2023. 11. 11.   전수진       최초작성
 * 2023. 11. 12.   전수진       리스트 10개씩으로 수정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
@RequestMapping("/notice")
public class NoticeBoardRetrieveController {
	
	@Inject
	private NoticeBoardService service;
	
	@GetMapping("{bbsNo}")
	public String noticeBoardView(@PathVariable int bbsNo, Model model) {
		BoardVO notice = service.retrieveNoticeBoard(bbsNo);
		model.addAttribute("notice", notice);
		return "notice/noticeView";
		
	}
	
	@GetMapping
	public String noticeList(
		@RequestParam(value = "searchType", required = false) String searchType
		, @RequestParam(value = "searchWord", required = false) String searchWord
		, @RequestParam(value = "page", required = false, defaultValue = "1") int currentPage
		, Model model
		, @ModelAttribute("simpleCondition") SearchVO simpleCondition
	) {
		
		// Controller단에서 PaginationInfo를 생성
		PaginationInfo<BoardVO> paging = new PaginationInfo<>(7, 2);
		paging.setSimpleCondition(simpleCondition);	//키워드 검색 조건
		
		// request의 parameter로 받은 page정보를 넘김 → 이때 PaginationInfo의 property는 5개
		paging.setCurrentPage(currentPage);
		
		service.retrieveNoticeBoardList(paging);	//이단계에서 totalRecode 생성
		
		model.addAttribute("paging", paging);	// 최종모델은 paging이 되어야함.
		
		return "notice/noticeList";
	
	}

}
