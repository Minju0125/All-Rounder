package kr.or.ddit.groupware.sanction.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.groupware.SanctionLineVO;

/**
 * @author 전수진
 * @since 2023. 11. 17.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 17.   전수진       최초작성
 * 2023. 11. 23.   전수진       결재문서 수정 및 삭제시 결재라인 삭제
 * 2023. 11. 25.   전수진       결재진행시 결재라인 수정
 * 2023. 11. 27.   전수진       결재진행시 결재서명이미지 처리
 * 2023. 12. 01.   전수진       대결권 스케쥴러처리를 위한 list 출력, update 처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface SanctionLineDAO {


	/**
	 * 전자결재문서 생성시 결재라인 등록
	 * @param sanctnLine
	 * @return 등록성공 ( >= 1)
	 */
	public int insertSanctionLine(SanctionLineVO sanctnLine);

	/**
	 * 전자결재문서 수정 및 삭제전 결재라인 삭제
	 * @param sanctnLineNo
	 * @return 삭제성공 ( >= 1)
	 */
	public int deleteSanctionLine(String sanctnLineNo);
	
	
	/**
	 * 결재문서번호와 결재자정보를 넘겨서 결재라인정보를 가져옴
	 * @param sanctnLine
	 * @return
	 */
	public SanctionLineVO selectSanctionLine(SanctionLineVO sanctnLine);
	
	
	/**
	 * 결재진행시 결재라인 수정
	 * @param sanctnLine
	 * @return
	 */
	public int updateSanctionLine(SanctionLineVO sanctnLine);
	
	
	/**
	 * Employee 테이블에서 서명이미지를 가져와서 SanctionLine테이블에 서명이미지를 저장
	 * @param sanctnLine
	 * @return
	 */
	public int updateSingImg(@Param("param") Map<String, String> param);
	
/***************************************대결권 Scheduler 관련***********************************/	
	
	/**
	 * 대결권설정한 결재자의 결재라인변경을 위한 list 출력
	 * @return
	 */
	public List<SanctionLineVO> selectSanctionLineList();
	
	/**
	 * 대결권시작일자에 결재문서가 있을경우 대결권수여자로 변경
	 * @param sanctnLine
	 * @return
	 */
	public int updateRealSanctner(SanctionLineVO sanctnLine);
	
}
