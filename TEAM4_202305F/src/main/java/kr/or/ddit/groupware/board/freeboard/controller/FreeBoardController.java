package kr.or.ddit.groupware.board.freeboard.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.ddit.groupware.board.freeboard.dao.FreeBoardDAO;
import kr.or.ddit.groupware.board.freeboard.service.FreeBoardService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.groupware.AnswerVO;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
//@CrossOrigin(origins = {"http://localhost:80"})
@RequestMapping("/free")
public class FreeBoardController {

	@Inject
	private FreeBoardService service;
	
	@Inject
	private FreeBoardDAO dao;
	
	@Value("#{appInfo.freeBoardFile}")
	private Resource freeBoardFile;
	
	@GetMapping
	public String freeBoard() {
		return "groupware/board/freeboard/freeboard";
	}
	
	@GetMapping("/list")
	@ResponseBody
	public PaginationInfo<BoardVO> freeList(
			@ModelAttribute("simpleCondition") SearchVO simpleCondition,
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage) throws Exception {
		log.info("--$$$$-- {}", simpleCondition);
		log.info("--$$$$-- {}", currentPage);
	//	log.info("bVO.스탯확인", bVO.getEventSttus());

		PaginationInfo<BoardVO> paging = new PaginationInfo<BoardVO>(6, 3);
	//	paging.setDetailCondition(bVO);
		paging.setSimpleCondition(simpleCondition);

		paging.setTotalRecord(dao.selectTotalFree(paging));
		paging.setCurrentPage(currentPage);
		
		List<BoardVO> eventList = service.selectFreeBoardList(paging);
		paging.setDataList(eventList);

		log.info("이벤트페이징성공");
		log.info("************* {}", paging);
		return paging;
		// groupware/board/freeboard/freeboard
		// groupware/board/freeboard/freeboarddetail		
	}
	

	@GetMapping("/list2")
	@ResponseBody
	public PaginationInfo<BoardVO> freeList2(
			SearchVO simpleCondition,int currentPage) throws Exception {
		log.info("---------- {}", simpleCondition);

		PaginationInfo<BoardVO> paging = new PaginationInfo<BoardVO>(6, 3);
		paging.setSimpleCondition(simpleCondition);
		paging.setTotalRecord(dao.selectTotalFree(paging));
		paging.setCurrentPage(currentPage);

		List<BoardVO> eventList = service.selectFreeBoardList(paging);
		paging.setDataList(eventList);

		log.info("이벤트페이징성공");

		return paging;
	}

	
	
	@PostMapping("{bbsNo}")
	@ResponseBody
	public BoardVO  selectFreeBoard(@PathVariable("bbsNo") int bbsNO) {	
		log.info("***************** {}",bbsNO);
		return service.selectFreeBoard(bbsNO);
	}
	
	@PostMapping("/answerInsert")
	@ResponseBody
	public void answerInsert(@RequestBody AnswerVO answerVO) {
		log.info("KKKK {}", answerVO);
		service.answerInsert(answerVO);
	}
	
	@PostMapping("/replyInsert")
	@ResponseBody
	public void replyInsert(@RequestBody AnswerVO answerVO) {
		service.replyInsert(answerVO);
	}
	
	@DeleteMapping("/answerDelete")
	@ResponseBody
	public void answerDelete(@RequestBody AnswerVO answerVO) {
		log.info("$$$$$$$$$$$$$$$ {}", answerVO);
		service.answerDelete(answerVO);
	}
	
	@PutMapping("/answerUpdate")
	@ResponseBody
	public String answerUpdate(@RequestBody AnswerVO answerVO) {
		log.info("********{}",answerVO);
		service.answerUpdate(answerVO);
		return "jsonView";
	}
	
	@PostMapping("/freeInsert")
	@ResponseBody
	public String freeboardInsert(BoardVO boardVO, MultipartHttpServletRequest req) throws IOException {
		log.info("123123123123123213 : " + boardVO);
		service.freeboardInsert(boardVO);
		return "jsonView";
	}
}
