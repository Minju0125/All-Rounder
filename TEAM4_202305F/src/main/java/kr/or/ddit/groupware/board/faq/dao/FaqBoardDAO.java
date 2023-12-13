package kr.or.ddit.groupware.board.faq.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 * Nov 15, 2023     송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface FaqBoardDAO {
	
	/**
	 * FAQ게시판 게시글 등록
	 * @param board
	 * @return 등록 성공( >= 1)
	 */
	public int insertFaqBoard(BoardVO board);
	
	
 	/**
	 * FAQ게시판 게시글 상세조회
	 * @param bbsNo
	 * @return
	 */
	public BoardVO selectFaqBoard(@Param("bbsNo") int bbsNo);
	
//	/**
//	 * FAQ게시판 조회수 증가
//	 * @param bbsNo
//	 * @return
//	 */
//	public int incrementHit(int bbsNo);
	
	
	/**
	 * 검색조건에 맞는 FAQ게시판 게시글 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<BoardVO> paging);
	
	
	/**
	 * 검색조건에 맞는 FAQ게시판 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<BoardVO> selectFaqBoardList(PaginationInfo<BoardVO> paging);
	
	 
	/**
	 * FAQ게시판 게시글 수정
	 * @param board
	 * @return 수정 성공( >= 1)
	 */
	public int updateFaqBoard(BoardVO board);
	
	
	/**
	 * FAQ게시판 게시글 삭제
	 * @param bbsNo
	 * @return 삭제 성공( >= 1)
	 */
	public int deleteFaqBoard(@Param("bbsNo") int bbsNo);
	
}
