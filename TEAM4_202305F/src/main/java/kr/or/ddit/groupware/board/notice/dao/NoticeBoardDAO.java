package kr.or.ddit.groupware.board.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;

/**
 * @author 전수진
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      전수진       최초작성
 * 2023. 11. 8.      전수진       조회수 증가 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface NoticeBoardDAO {

	/**
	 * 공지게시판 게시글 등록
	 * @param board
	 * @return 등록 성공( >= 1)
	 */
	public int insertNoticeBoard(BoardVO board);
	
	
	/**
	 * 공지게시판 게시글 상세조회
	 * @param bbsNo
	 * @return
	 */
	public BoardVO selectNoticeBoard(@Param("bbsNo") int bbsNo);
	
	/**
	 * 공지게시판 조회수 증가
	 * @param bbsNo
	 * @return
	 */
	public int incrementHit(int bbsNo);
	
	
	/**
	 * 검색조건에 맞는 공지게시판 게시글 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<BoardVO> paging);
	
	
	/**
	 * 검색조건에 맞는 공지게시판 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<BoardVO> selectNoticeBoardList(PaginationInfo<BoardVO> paging);
	
	
	/**
	 * 공지게시판 게시글 수정
	 * @param board
	 * @return 수정 성공( >= 1)
	 */
	public int updateNoticeBoard(BoardVO board);
	
	
	/**
	 * 공지게시판 게시글 삭제
	 * @param bbsNo
	 * @return 삭제 성공( >= 1)
	 */
	public int deleteNoticeBoard(@Param("bbsNo") int bbsNo);
	
	
	/**
	 * 대쉬보드용 필독공지사항리스트 조회
	 * @return
	 */
	public List<BoardVO> selectNoticeForDashBoard();
	
}
