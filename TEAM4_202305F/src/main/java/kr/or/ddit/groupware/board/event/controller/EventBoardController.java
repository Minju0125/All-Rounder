package kr.or.ddit.groupware.board.event.controller;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.board.event.service.EventBoardService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.EmojiVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.pms.IssueVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 20.      김보영         최초작성
 * 2023. 11. 25.      김보영         등록
 * 2023. 11. 28.      김보영         이모지 처리,수정
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */

@Slf4j
@Controller
@RequestMapping("/event")
public class EventBoardController {
	

	@Value("#{appInfo.boardImagesUrl}")
	private String boardImagesUrl;
	
	@Value("#{appInfo.boardImagesUrl}")
	private Resource boardImages;	//폴더는 리소스로 받아와야함
	
	@Inject
	private EventBoardService service;

	@ModelAttribute("emojiVO")
	public EmojiVO emoji(Authentication authentication) {
		EmojiVO emoji = new EmojiVO();
		emoji.setEmpCd(authentication.getName());
		return emoji;
	}

	@ModelAttribute("boardVO")
	public BoardVO board(Authentication authentication) {
		BoardVO board = new BoardVO();
		board.setEmpCd(authentication.getName());
		return board;
	}
	
	
	@PostMapping("image")	
	public String imageUpload(MultipartFile upload, Model model, HttpServletRequest req) throws IOException {
	
		if(!upload.isEmpty()) {	//파일이 정상적으로 업로드 되고 있다.
			// 로컬의 파일의 url이 필요하므로 webResource형태로 저장이 되야함
			String saveName = UUID.randomUUID().toString();
			File saveFolder =  boardImages.getFile(); //파일로 만들어줘야 저장이 가능
			File saveFile = new File(saveFolder, saveName);
			upload.transferTo(saveFile);	// upload 완료
			
			String url = req.getContextPath()+boardImagesUrl + "/" + saveName;	// 이거 비동기의 응답데이터로 나가야함
			model.addAttribute("uploaded",1);
			model.addAttribute("fileName",upload.getOriginalFilename());	//원본파일명
			model.addAttribute("url",url);
		
		} else {	// 업로드가 되지 않았을때.
			model.addAttribute("uploaded",0);
			model.addAttribute("error", Collections.singletonMap("message", "업로드 된 파일 없음"));	// map의 엔트리가 1개밖에 없을 때 사용
			
		}
		
		return "jsonView";
		
	}

	@PutMapping("emojiUpdate")
	@ResponseBody
	public Map<String, BoardVO> emojiUpdate(
			EmojiVO eVO
		) {
		Map<String, BoardVO> map = new HashMap<String, BoardVO>();

		// 이모지 변경아니면 등록
		service.modifyEmoji(eVO);

		// 이모지 선택 카운트 다시
		BoardVO emojiCnt = new BoardVO();
		emojiCnt = service.countEmoji(eVO.getBbsNo());
		
		map.put("cnt",emojiCnt);
		System.out.println(emojiCnt);
		return map;
	}

	@GetMapping("detail")
	public String eventDetail(
		@RequestParam(value = "bbsNo", required = true) int bbsNo
		, @RequestParam(value = "eventSttus", required = true) String eventSttus
		, Model model
		) {

		// 게시글 상세
		BoardVO eventDetail = new BoardVO();
		eventDetail = service.retrieveEventDetail(bbsNo);
		eventDetail.setEventSttus(eventSttus);
		model.addAttribute("detail", eventDetail);

		// 이모지 선택 카운트
		BoardVO emojiCnt = new BoardVO();
		emojiCnt = service.countEmoji(bbsNo);
		model.addAttribute("cnt", emojiCnt);

		return "event/eventBoardDetail";
	}

	@ResponseBody
	@GetMapping("list")
	public Map<String, PaginationInfo<BoardVO>> eventForm(BoardVO bVO,
			@ModelAttribute("simpleCondition") SearchVO simpleCondition,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) {

		Map<String, PaginationInfo<BoardVO>> map = new HashMap<String, PaginationInfo<BoardVO>>();
		log.info("bVO.스탯확인", bVO.getEventSttus());

		PaginationInfo<BoardVO> paging = new PaginationInfo<BoardVO>(7, 3);
		paging.setDetailCondition(bVO);
		paging.setSimpleCondition(simpleCondition);

		paging.setCurrentPage(currentPage);

		List<BoardVO> eventList = service.retrieveEventList(paging);
		paging.setDataList(eventList);

		map.put("paging", paging);
		log.info("이벤트페이징성공");

		return map;
	}

	@PostMapping("insert")
	@ResponseBody
	public Map<String, String> eventInsert(
			@ModelAttribute("boardVO") BoardVO bVO
			, @RequestParam(value = "bbsCn", required = false) String bbsCn
			) {

		Map<String, String> map = new HashMap<String, String>();

		ServiceResult result = service.createEventBoard(bVO);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;

		default:
			map.put("success", "N");
			break;
		}
		return map;
	}

	@DeleteMapping("delete/{bbsNo}")
	@ResponseBody
	public Map<String, String> eventDelete(
		@PathVariable int bbsNo
		) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		ServiceResult result = service.removeEventBoard(bbsNo);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;
			
		default:
			map.put("success", "N");
			break;
		}
		return map;
	}
	
	
	@PutMapping("update")
	@ResponseBody
	public Map<String, String> eventUpdate(
			@ModelAttribute("boardVO") BoardVO bVO
			, @RequestParam(value = "bbsCn", required = false) String bbsCn
			) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		ServiceResult result = service.modifyEventBoard(bVO);
		
		String bbsNo  =  String.valueOf(bVO.getBbsNo());
		map.put("bbsNo",bbsNo);
		
		String eventSttus = bVO.getEventSttus();
		map.put("eventSttus",eventSttus);
		
		switch (result) {
		case OK:
			map.put("success", "Y");
			break;
			
		default:
			map.put("success", "N");
			break;
		}
		return map;
	}
	
	@GetMapping("home")
	public String eventHome() {
		return "event/eventBoardHome";
	}

	@GetMapping("form")
	public String eventForm() {
		return "event/eventBoardForm";
	}

	@GetMapping("edit")
	public String eventEdit(
		@RequestParam(value = "bbsNo", required = true) int bbsNo
		, Model model
		) {
		
		// 게시글 상세
		BoardVO eventDetail = new BoardVO();
		eventDetail = service.retrieveEventDetail(bbsNo);
		model.addAttribute("detail", eventDetail);
		
		return "event/eventBoardEdit";
	}
	
}
