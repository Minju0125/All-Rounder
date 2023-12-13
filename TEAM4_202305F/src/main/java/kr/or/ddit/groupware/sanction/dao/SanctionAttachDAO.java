package kr.or.ddit.groupware.sanction.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.groupware.SanctionAttachVO;

/**
 * @author 전수진
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 20.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface SanctionAttachDAO {

	/**
	 * 결재문서 첨부파일 등록
	 * @param attach
	 * @return 등록성공 ( >= 1)
	 */
	public int insertSanctionAttach(SanctionAttachVO attach);
	
	/**
	 * 결재문서 첨부파일 조회
	 * @param attachNo
	 * @return 존재하지 않으면 Null반환
	 */
	public SanctionAttachVO selectSanctionAttach(int attachNo);
	
	/**
	 * 결재문서 결재문서 삭제전 첨부파일 삭제
	 * @param attachNo
	 * @return 삭제성공 ( >= 1)
	 */
	public int deleteBoardAttach(int attachNo);
	
}
