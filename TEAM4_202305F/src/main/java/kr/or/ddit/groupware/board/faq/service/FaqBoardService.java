package kr.or.ddit.groupware.board.faq.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;

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
public interface FaqBoardService {
	
	/**
	 * faq글 등록
	 * @param board
	 */
	public void createFaqBoard(BoardVO board);
	
	/**
	 * faq글 상세조회
	 * @param bbsNo
	 * @return
	 */
	public BoardVO retrieveFaqBoard(int bbsNo);
	
	/**
	 * faq글 검색&페이징
	 * @param paging
	 * @return
	 */
	public List<BoardVO> retrieveFaqBoardList(PaginationInfo<BoardVO> paging);
	
	/**
	 * faq글 업데이트
	 * @param board
	 */
	public void modifyFaqBoard(BoardVO board); 
	
	/**
	 * faq글 삭제 
	 * @param bbsNo
	 * @return
	 */
	public ServiceResult removeFaqBoard(int bbsNo);

}
