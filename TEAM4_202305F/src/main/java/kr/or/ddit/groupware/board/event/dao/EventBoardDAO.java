package kr.or.ddit.groupware.board.event.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.EmojiVO;

/**
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 20.      김보영         최초작성
 * 2023. 11. 20.      김보영         등록
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Mapper
public interface EventBoardDAO {

	/**
	 * 경조사 게시글 등록
	 * @param bVO
	 * @return
	 */
	public int insertEventBoard(BoardVO bVO);

	/**
	 * 페이징을 위한 글의 갯수
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<BoardVO> paging);

	/**
	 * 
	 *게시글 목록
	 * @param paging
	 * @return
	 */
	public List<BoardVO> selectEventList(PaginationInfo<BoardVO> paging);

	/**
	 * 게시글 상세
	 * @param bVO
	 * @return
	 */
	public BoardVO selectEventDetail(@Param("bbsNo") int bbsNo);

	/**
	 * 하나의 게시글의 이모지 카운팅
	 * @param bbsNo
	 * @return
	 */
	public BoardVO countEmoji(@Param("bbsNo") int bbsNo);

	/**
	 * 이모지선택 수정
	 * @param eVO
	 * @return
	 */
	public int updateEmoji(EmojiVO eVO);

	/**
	 * 이모지선택 등록
	 * @param eVO
	 * @return
	 */
	public int insertEmoji(EmojiVO eVO);

	/**
	 * 게시글 수정
	 * @param bVO
	 * @return
	 */
	public int updateEvent(BoardVO bVO);

	/**
	 * 게시글 삭제
	 * @param bbsNo
	 * @return
	 */
	public int deleteEvent(@Param("bbsNo") int bbsNo);

	/**
	 * 이모지선택 삭제
	 * @param bbsNo
	 * @return
	 */
	public int deleteEmoji(@Param("bbsNo") int bbsNo);

}
