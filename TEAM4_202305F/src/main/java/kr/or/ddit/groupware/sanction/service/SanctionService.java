package kr.or.ddit.groupware.sanction.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.SanctionAttachVO;
import kr.or.ddit.vo.groupware.SanctionLineVO;
import kr.or.ddit.vo.groupware.SanctionVO;

/**
 * @author 전수진
 * @since 2023. 11. 16.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 16.  전수진       최초작성
 * 2023. 11. 20.  전수진       결재문서 첨부파일 추가
 * 2023. 11. 24.  전수진       결재문서 리스트 구현 추가
 * 2023. 11. 25.  전수진       결재문서 결재진행시 결재라인 수정
 * 2023. 11. 27.  전수진       결재대기문서 list 조회
 * 2023. 11. 29.  전수진       수신문서 list 조회
 * 2023. 12. 08.  전수진       관리자 list 조회
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface SanctionService {
	
	
	/**
	 * 검색조건에 맞는 결재문서 리스트 조회(기안자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> retrieveSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 결재대기문서 리스트 조회(결재자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> retrieveSanctnerSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 수신문서 리스트 조회(수신자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> retrieveRcyerSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 부서문서 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> retrieveDeptSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 결재문서 리스트 조회(관리자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> retrieveAdminSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 결재문서 상세조회
	 * @param sanctnNo
	 * @return
	 */
	public SanctionVO retrieveSanction(String sanctnNo);
	
	/**
	 * 결재문서 등록(상신)
	 * @param sanctionVO
	 * @return 등록 성공( >= 1 )
	 */
	public ServiceResult createSanction(SanctionVO sanctionVO);
	
	/**
	 * 결재문서 수정, 결재진행이 되지 않았을 경우만 수정가능
	 * @param sanctionVO
	 * @return 수정 성공( >= 1 )
	 */
	public ServiceResult modifySanction(SanctionVO sanctionVO);
	
	/**
	 * 결재문서 삭제, 결재진행이 되지 않았을 경우만 삭제 가능
	 * @param sanctnNo
	 * @return 삭제 성공( >= 1 )
	 */
	public ServiceResult removeSanction(String sanctnNo);
	
	
	/**
	 * 결재문서 첨부파일 조회
	 * @param attachNo
	 * @return 존재하지 않으면 Null반환
	 */
	public SanctionAttachVO retrieveSanctionAttach(int attachNo);


	/**
	 * 결재문서 결재진행시 결재라인 수정
	 * @param sanctionline
	 * @return
	 */
	public ServiceResult modifySanctionLine(SanctionLineVO sanctionline);
	
	
	
}
