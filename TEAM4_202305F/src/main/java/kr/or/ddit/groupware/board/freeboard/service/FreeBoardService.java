package kr.or.ddit.groupware.board.freeboard.service;

import java.io.IOException;
import java.util.List;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.AnswerVO;
import kr.or.ddit.vo.groupware.BoardVO;

public interface FreeBoardService {

	/**
	 * 검색조건에 맞는 공지게시판 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<BoardVO> selectFreeBoardList(PaginationInfo<BoardVO> paging);
		
	/**
	 * 상세 조회
	 * @param bbsNo
	 * @return
	 */
	public BoardVO selectFreeBoard(int bbsNo);
	
	/**
	 * 댓글 작성
	 * @param answerVO
	 * @return
	 */
	public int answerInsert(AnswerVO answerVO);
	
	/**
	 * 대댓글 추가
	 * @param answerVO
	 * @return
	 */
	public int replyInsert(AnswerVO answerVO);
	
	/**
	 * 댓글 삭제
	 * @param answerCode
	 * @return
	 */
	public void answerDelete(AnswerVO answerVO);
	
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
	 * @throws IOException 
	 */
	public int freeboardInsert(BoardVO boardVO) throws IOException;
}
