package kr.or.ddit.groupware.board.event.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.EmojiVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------    	 	--------    ----------------------
 * 2023. 11. 20.     김보영         최초작성
 * 2023. 11. 25.     김보영         등록
 * 2023. 11. 27.     김보영         목록
 * 2023. 11. 28.     김보영         이모지 처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface EventBoardService {

	/**
	 * 경조사 게시판 등록
	 * @param bVO
	 * @return
	 */
	public ServiceResult createEventBoard(BoardVO bVO);

	/**
	 * 목록 페이징 
	 * @param paging
	 * @return
	 */
	public List<BoardVO> retrieveEventList(PaginationInfo<BoardVO> paging);

	/**
	 * 하나의 게시글 상세보기
	 * @param bVO
	 */
	public BoardVO retrieveEventDetail(int bbsNo);

	/**
	 * 하나의 게시글 이모지 처리
	 * @param bbsNo
	 * @return
	 */
	public BoardVO countEmoji(int bbsNo);

	/**
	 * 이모지 선택 변경
	 * (최초 클릭시에는 등록)
	 * @param eVO
	 * @return
	 */
	public ServiceResult modifyEmoji(EmojiVO eVO);

	/**
	 * 게시글 수정
	 * @param bVO
	 * @return
	 */
	public ServiceResult modifyEventBoard(BoardVO bVO);

	/**
	 * 게시글 삭제
	 * @param bbsNo
	 * @return
	 */
	public ServiceResult removeEventBoard(int bbsNo);

	

	

}
