package kr.or.ddit.groupware.board.faq.service;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.common.exception.BoardNotFoundException;
import kr.or.ddit.groupware.board.faq.dao.FaqBoardDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Nov 15, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 15, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FaqBoardServiceImpl implements FaqBoardService{
	
	private final FaqBoardDAO faqDAO;
	
	
	
	
	/**
	 *faq게시판 글 등록 
	 */
	@Transactional
	@Override
	public void createFaqBoard(BoardVO board) {
		board.setBbsCategory("Q");
		faqDAO.insertFaqBoard(board);
	} 

	@Override
	public BoardVO retrieveFaqBoard(int bbsNo) {
		
		BoardVO board = faqDAO.selectFaqBoard(bbsNo);
		
		if(board==null)
			throw new BoardNotFoundException(HttpStatus.NOT_FOUND, String.format("%d FAQ게시판에 등록된글이 없습니다.", bbsNo));
		
		  
		return board; 
	}

	@Override
	public List<BoardVO> retrieveFaqBoardList(PaginationInfo<BoardVO> paging) {
		int totalRecord = faqDAO.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<BoardVO> dataList = faqDAO.selectFaqBoardList(paging);
		paging.setDataList(dataList);

		return dataList;
	}

	@Transactional
	@Override
	public void modifyFaqBoard(BoardVO board) {
		faqDAO.updateFaqBoard(board);
		
	}
 
	@Transactional
	@Override
	public ServiceResult removeFaqBoard(int bbsNo) {
		ServiceResult result = null;
		
		int status = faqDAO.deleteFaqBoard(bbsNo);
		if(status>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result; 
	}  
	


}
