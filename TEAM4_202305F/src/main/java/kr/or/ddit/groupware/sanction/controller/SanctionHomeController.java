package kr.or.ddit.groupware.sanction.controller;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.or.ddit.groupware.sanction.dao.SanctionDAO;
import kr.or.ddit.groupware.sanction.service.BookmarkAndProxyService;
import kr.or.ddit.vo.groupware.SanctionByProxyVO;

@Controller
public class SanctionHomeController {
	
	@Inject
	private BookmarkAndProxyService service;
	
	@Inject
	private SanctionDAO dao;
	
	
	@GetMapping("/sanctionhome")
	public String retrieveProxyCheck (Authentication authentication, Model model) {
		String loginCd = authentication.getName();
		SanctionByProxyVO proxy = service.retrieveProxyCheck(loginCd);
		SanctionByProxyVO receiveProxy = service.retrieveProxyRecivedCheck(loginCd);
		
		
		int rejectNo = dao.selectRejectRecode(loginCd);
		
		model.addAttribute("proxy",proxy);
		model.addAttribute("receiveProxy",receiveProxy);
		model.addAttribute("rejectNo",rejectNo);
		
		return "sanctionhome/sanctionIndex";
	}

}
