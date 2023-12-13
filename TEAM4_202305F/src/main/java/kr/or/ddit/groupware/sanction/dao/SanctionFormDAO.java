package kr.or.ddit.groupware.sanction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.groupware.SanctionFormVO;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 15.  전수진       최초작성
 * 2023. 12. 08.  전수진       관리자 관리 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface SanctionFormDAO {
	
	/**
	 * 결재문서양식 등록
	 * @param sanctionForm
	 * @return 등록 성공( >= 1 )
	 */
	public int insertSanctionForm(SanctionFormVO sanctionForm);	// 임시로 양식을 만들때 사용하는 프로세스
	
	/**
	 * 결재문서양식 리스트 조회(기안자용)
	 * @return
	 */
	public List<SanctionFormVO> selectSanctionFormList();
	
	/**
	 * 결재문서양식 리스트 모두 조회
	 * @return
	 */
	public List<SanctionFormVO> selectSanctionFormAllList();
	
	/**
	 * 결재문서양식 수정
	 * @param sanctionForm
	 * @return
	 */
	public int updateSanctionForm(SanctionFormVO sanctionForm);
	
	/**
	 * 결재문서양식 상세조회
	 * @param formNo
	 * @return
	 */
	public SanctionFormVO selectSanctionFormSourc(int formNo);

}
