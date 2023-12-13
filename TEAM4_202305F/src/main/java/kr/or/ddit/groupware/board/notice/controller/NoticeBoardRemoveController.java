package kr.or.ddit.groupware.board.notice.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.board.notice.service.NoticeBoardService;

/**
 * @author 전수진
 * @since 2023. 11. 12.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 12.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/notice/{bbsNo}")
public class NoticeBoardRemoveController {

	@Inject
	private NoticeBoardService service;
	
	@DeleteMapping
	public String boardDelete (@PathVariable int bbsNo) {
		
		String viewName="";
		ServiceResult result = service.removeNoticeBoard(bbsNo);
		if(result.equals(ServiceResult.OK)) {
			viewName="redirect:/notice";
			
		}else {
			viewName="redirect:/notice/{bbsNo}";
		}
		return viewName;
	}
		
	
	

}
