package kr.or.ddit.groupware.board.freeboard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.AnswerVO;
import kr.or.ddit.vo.groupware.BoardFileVO;
import kr.or.ddit.vo.groupware.BoardVO;

/**
 * @author 작성자명
 * @since 2023. 11. 30.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 30.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Mapper
public interface FreeBoardDAO {

	/**
	 * 검색조건에 맞는 공지게시판 리스트 조회
	 * 
	 * @param paging
	 * @return
	 */
	public List<BoardVO> selectFreeBoardList(PaginationInfo<BoardVO> paging);

	/**
	 * 검색조건에 맞는 공지게시판 게시글 수 조회
	 * 
	 * @param paging
	 * @return
	 */
	public int selectTotalFree(PaginationInfo<BoardVO> paging);

	/**
	 * 상세 조회
	 * 
	 * @param bbsNo
	 * @return
	 */
	public BoardVO selectFreeBoard(int bbsNo);
	
	/**
	 * 댓글코드 최대값
	 * @param answerCode
	 * @return
	 */
	public String answerMax(int answerCode);

	/**
	 * 댓글 작성
	 * 
	 * @param answerVO
	 * @return
	 */
	public int answerInsert(AnswerVO answerVO);
	
	/**
	 * 대댓글 최대값
	 * @param answerCode
	 * @return
	 */
	public String replyMax(String answerCode);
	
	/**
	 * 대댓글 추가
	 * @param answerVO
	 * @return
	 */
	public int replyInsert(AnswerVO answerVO);
	
	/**
	 * 대댓글 전체 삭제
	 * @param answerUpcode
	 * @return
	 */
	public int replyAllDelete(String answerUpcode);
	
	/**
	 * 댓글 전체 삭제 
	 * @param answerCode
	 * @return
	 */
	public int answerAllDelete(AnswerVO answerVO);
	
	/**
	 * 댓글 수정
	 * @param answerVO
	 * @return
	 */
	public int answerUpdate(AnswerVO answerVO);
	
	
	/**
	 * 자유게시판 등록
	 * @param boardVO
	 * @return
	 */
	public int freeboardInsert(BoardVO boardVO);
	
	/**
	 * 자유게시판 첨부파일 등록
	 * @param file
	 * @return
	 */
	public int freeboardInsertFile(BoardFileVO file);
	
	/**
	 * 조회수 증가
	 * @param bbsNo
	 * @return
	 */
	public int rdcntUpdate(int bbsNo);
}
