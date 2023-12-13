package kr.or.ddit.groupware.sanction.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.groupware.SanctionFormVO;

/**
 * @author 전수진
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 15.  전수진       최초작성
 * 2023. 11. 21.  전수진       DB formNo 데이터타입변환으로 인한 수정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface SanctionFormService {
	
	/**
	 * 결재문서양식Form 등록
	 * @param sanctionFormVO
	 * @return
	 */
	public ServiceResult creatSanctionForm(SanctionFormVO sanctionFormVO);

	/**
	 * 결재문서 양식List을 조회(기안자용)
	 * @return
	 */
	public List<SanctionFormVO> retrieveSanctionFormList();
	
	/**
	 * 결재문서양식 리스트 모두 조회
	 * @return
	 */
	public List<SanctionFormVO> retrieveSanctionFormAllList();
	
	/**
	 * 결재문서양식 수정
	 * @param sanctionForm
	 * @return
	 */
	public int modifySanctionForm(SanctionFormVO sanctionForm);
	
	/**
	 * 결재문서 양식 form조회
	 * @param formNo
	 * @return 
	 */
	public SanctionFormVO retrieveSanctionFormSourc(int formNo);

}
