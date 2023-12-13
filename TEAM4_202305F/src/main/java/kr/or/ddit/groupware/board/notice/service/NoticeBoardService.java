package kr.or.ddit.groupware.board.notice.service;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.common.exception.BoardNotFoundException;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardFileVO;
import kr.or.ddit.vo.groupware.BoardVO;

/**
 * @author 전수진
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.    전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface NoticeBoardService {

	/**
	 * 공지게시판 게시글 등록, 등록시 문제가 발생한다면 {@link PersistenceException}를 throw 함.
	 * @param board
	 */
	public void createNoticeBoard(BoardVO board);
	
	
	/**
	 * 공지게시판 게시글 상세조회
	 * @param bbsNo
	 * @return 존재하지 않으면 {@link BoardNotFoundException} 발생, 동시에 404 응답전송
	 */
	public BoardVO retrieveNoticeBoard(int bbsNo);
	
	
	/**
	 * 다운로드를 위해 첨부파일 메타데이터 조회
	 * @param fileCode
	 * @return
	 */
	public BoardFileVO retrieveBoardFile(String fileCode);
	
	/**
	 * 검색조건에 맞는 공지게시판 리스트 조회(검색 및 페이징 지원)
	 * @param paging
	 * @return
	 */
	public List<BoardVO> retrieveNoticeBoardList(PaginationInfo<BoardVO> paging);
	
	
	/**
	 * 공지게시판 게시글 수정, 문제가 발생한다면, mybatis는 {@link BoardNotFoundException} throw 함.
	 * @param board
	 */
	public void modifyNoticeBoard(BoardVO board);
	
	
	/**
	 * 공지게시판 게시글 삭제
	 * @param bbsNo
	 */
	public ServiceResult removeNoticeBoard(int bbsNo);
	

}
