package kr.or.ddit.groupware.sanction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
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
 * 2023. 11. 24.  전수진       결재문서 list 조회
 * 2023. 11. 26.  전수진       결재진행시 결재상태 변경
 * 2023. 11. 27.  전수진       결재대기문서 list 조회
 * 2023. 11. 29.  전수진       수신문서 list, 부서문서 list 조회
 * 2023. 12. 08.  전수진       관리자 list 조회
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface SanctionDAO {
	
	
	/**
	 * 검색조건에 맞는 결재문서 레코드수(기안자)
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<SanctionVO> paging);
	
	
	/**
	 * 검색조건에 맞는 결재문서 리스트 조회(기안자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> selectSanctionList(PaginationInfo<SanctionVO> paging);
	
	
	/**
	 * 검색조건에 맞는 결재대기문서 레코드수(결재자)
	 * @param paging
	 * @return
	 */
	public int selectSanctnerTotalRecord(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 결재대기문서 리스트 조회(결재자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> selectSanctnerSanctionList(PaginationInfo<SanctionVO> paging);
	
	
	/**
	 * 검색조건에 맞는 수신문서 리스트 조회(수신자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> selectRcyerSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 수신문서 레코드수(수신자)
	 * @param paging
	 * @return
	 */
	public int selectRcyerTotalRecord(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 부서문서 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> selectDeptSanctionList(PaginationInfo<SanctionVO> paging);
	
	/**
	 * 검색조건에 맞는 부서문서 레코드수
	 * @param paging
	 * @return
	 */
	public int selectDeptTotalRecord(PaginationInfo<SanctionVO> paging);
	
	
	/**
	 * 검색조건에 맞는 결재문서 레코드수(관리자)
	 * @param paging
	 * @return
	 */
	public int selectAdminTotalRecord(PaginationInfo<SanctionVO> paging);
	
	
	/**
	 * 검색조건에 맞는 결재문서 리스트 조회(관리자)
	 * @param paging
	 * @return
	 */
	public List<SanctionVO> selectAdminSanctionList(PaginationInfo<SanctionVO> paging);
	
	
	/**
	 * 결재문서 상세조회
	 * @param sanctnNo
	 * @return
	 */
	public SanctionVO selectSanction(String sanctnNo);
	
	/**
	 * 결재문서 등록(상신)
	 * @param sanctionVO
	 * @return 등록 성공( >= 1 )
	 */
	public int insertSanction(SanctionVO sanctionVO);
	
	/**
	 * 결재문서 수정, 결재진행이 되지 않았을 경우만 수정가능
	 * @param sanctionVO
	 * @return 수정 성공( >= 1 )
	 */
	public int updateSanction(SanctionVO sanctionVO);
	
	/**
	 * 결재문서 삭제, 결재진행이 되지 않았을 경우만 삭제 가능
	 * @param sanctnNo
	 * @return 삭제 성공( >= 1 )
	 */
	public int deleteSanction(SanctionVO sanctionVO);
	
	
	/**
	 * 결재진행시 결재문서의 상태 수정
	 * @param sanctnNo
	 * @return 수정 성공( >= 1 )
	 */
	public int updateSanctionSttus(SanctionVO sanctionVO);
	
	/**
	 * Employee 테이블에서 서명이미지를 가져와서 Sanction테이블에 서명이미지를 저장
	 * @param sanctnLine
	 * @return
	 */
	public int updateDrafterSignImg(@Param("param") Map<String, String> param);
	
	
	public int selectRejectRecode(String drafter);
	
}
