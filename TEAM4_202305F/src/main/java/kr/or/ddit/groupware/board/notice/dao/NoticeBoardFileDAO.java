package kr.or.ddit.groupware.board.notice.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.groupware.BoardFileVO;

/**
 * @author 전수진
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface NoticeBoardFileDAO {
	
	
	/**
	 * 공지사항 첨부파일 등록
	 * @param file
	 * @return 등록 성공( >=1 )
	 */
	public int insertFile(BoardFileVO file);
	
	
	/**
	 * 첨부파일 조회
	 * @param fileCode
	 * @return 존재하지 않으면 Null반환
	 */
	public BoardFileVO selectBoardFile(String fileCode);
	
	
	/**
	 * 첨부파일 다운로드 횟수 조회
	 * @param fileCode
	 * @return
	 */
	public int incrementDowncount(String fileCode);
	
	
	/**
	 * 공지사항 게시글 삭제전 첨부파일 삭제
	 * @param bbsNo
	 * @return 삭제성공 ( >= 1)
	 */
	public int deleteBoardFile(String fileCode);
	
}
