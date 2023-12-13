package kr.or.ddit.groupware.sanction.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.websocket.server.PathParam;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.sanction.dao.BookmarkAndProxyDAO;
import kr.or.ddit.groupware.sanction.service.BookmarkAndProxyService;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.BookmarkDetailVO;
import kr.or.ddit.vo.groupware.BookmarkVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.SanctionByProxyVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 17.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 17.  전수진     최초작성
 * 2023. 11. 28.  전수진     대결권 설정구현
 * 2023. 11. 29.  전수진     대결권 설정 조건 추가
 * 2023. 11. 30.  전수진     대결권 해제구현
 * 2023. 11. 30.  전수진     즐겨찾기 삭제구현
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Controller
public class BookmarkAndProxyController {
	@Inject
	private BookmarkAndProxyService service;
	
	@ResponseBody
	@PostMapping("/bookmark/new")
	public String createBookmark(@RequestBody BookmarkVO bookmark 
			, Authentication authentication
			) {
	
		String empCd = authentication.getName();
		bookmark.setBkmkOwner(empCd);
		
		ServiceResult result = service.createBookmark(bookmark);
		
		String msg = "FAIL";
		
		if(result.equals(ServiceResult.OK)) {
			msg = "OK";
		} 
		return msg;
		
	}
	
	@GetMapping("/bookmark/list")
	public String bookmarkList(Authentication authentication, Model model) {
		String empCd = authentication.getName();
		List<BookmarkVO> bookmarkList = service.retrieveBookmarkList(empCd);
		model.addAttribute("bookmarkList",bookmarkList);
		return "jsonView";
	}

	
	@GetMapping("/bookmark/detail")
	public String bookmarkDetailList(@RequestParam String bkmkNo, Model model) {
		log.info("bkmkNo======================"+bkmkNo);
		List<BookmarkDetailVO> detailList = service.retrieveBookmarkDetailList(bkmkNo);
		model.addAttribute("detailList", detailList);
		log.info("detailList======================"+detailList);
		
		return "jsonView";
	}
	
	@DeleteMapping("/bookmark/{bkmkNo}")
	@ResponseBody
	public String removeBookmark(@PathVariable String bkmkNo) {
		log.info("bkmkNo======={}",bkmkNo);
		// 즐겨찾기 삭제
		ServiceResult result = service.removeBookmark(bkmkNo);
		
		String msg = "";
		if(result.equals(ServiceResult.OK)) {
			msg = "OK";
		} else {
			msg = "FAIL";
		}
		
		return msg;
		
	}
	
/*==================================대결권==================================*/
	
	
	@PostMapping("/proxy/new")
	@ResponseBody
	public Map<String, Object> createSanctionProxy(
			@RequestBody SanctionByProxyVO proxy
			, Authentication authentication
			) {
		String empCd = authentication.getName();
		proxy.setPrxsanctnAlwnc(empCd);
		/*
		 SanctionByProxyVO(prxsanctnNo=null, prxsanctnAlwnc=E220321002, prxsanctnAlwncNm=null, prxsanctnAlwncRankNm=null, prxsanctnAlwncDeptName=null
		 , prxsanctnCnfer=E231120030, prxsanctnCnferNm=null, prxsanctnCnferRankNm=null, prxsanctnCnferDeptName=null, alwncDate=2023-11-29, extshDate=2023-12-01
		 , alwncReason=test, prxsanctnYn=null, emp=null)
		 */
		log.info("proxy : " + proxy);
		
		ServiceResult result = service.createSanctionProxy(proxy);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(result.equals(ServiceResult.OK)) {
			SanctionByProxyVO proxyVO = service.retrieveProxyCheck(empCd);
			resultMap.put("msg", "OK");
			resultMap.put("proxy", proxyVO);
			
		} else if(result.equals(ServiceResult.PKDUPLICATED)) {
			resultMap.put("msg", "PKDUPLICATED");
		} else {
			resultMap.put("msg", "FAIL");
		}
		return resultMap;
	}
	
	@PutMapping("/proxy/{prxsanctnNo}")
	@ResponseBody
	public String removeSanctionProxy(@PathVariable String prxsanctnNo) {
		// 대결권해제
		String msg = "";
		SanctionByProxyVO proxy = new SanctionByProxyVO();
		proxy.setPrxsanctnNo(prxsanctnNo);
		ServiceResult result = service.modifySanctionProxy(proxy);
		
		if(result.equals(ServiceResult.OK)) {
			msg = "OK";
		} else {
			msg = "FAIL";
		}
		
		return msg;
	}
	
}
