package kr.or.ddit.groupware.sanction.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.sanction.dao.SanctionFormDAO;
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
 * 2023. 12. 08.  전수진       관리자 양식관리 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Service
public class SanctionFormServiceImpl implements SanctionFormService {

	@Inject
	private SanctionFormDAO dao;
	
	@Override
	public ServiceResult creatSanctionForm(SanctionFormVO sanctionFormVO) {
		// 결재문서양식 Form 등록
		ServiceResult result = null;
		sanctionFormVO.setFormUse("N");
		
		String prehtml = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse:collapse; height:30x; width:100%\">\r\n"
				+ "	<tbody>\r\n"
				+ "		<tr>\r\n"
				+ "			<td style=\"background-color:#e2e2e2; border-color:black; border-style:solid; border-width:1px; height:30px; text-align:center; width:15%\"><strong><span style=\"font-size:11px\">제목(필수*)</span></strong></td>\r\n"
				+ "			<td style=\"border-color:black; border-style:solid; border-width:1px; height:30px\">&nbsp;</td>\r\n"
				+ "		</tr>\r\n"
				+ "		<tr>\r\n"
				+ "			<td style=\"background-color:#e2e2e2; border-color:black; border-style:solid; border-width:1px; height:30px; text-align:center; width:15%\"><strong><span style=\"font-size:11px\">작성일자</span></strong></td>\r\n"
				+ "			<td style=\"border-color:black; border-style:solid; border-width:1px; height:30px\">&nbsp;</td>\r\n"
				+ "		</tr>\r\n"
				+ "		<tr>\r\n"
				+ "			<td style=\"background-color:#e2e2e2; border-color:black; border-style:solid; border-width:1px; height:30px; text-align:center; width:15%\"><strong><span style=\"font-size:11px\">작성부서</span></strong></td>\r\n"
				+ "			<td style=\"border-color:black; border-style:solid; border-width:1px; height:30px\">&nbsp;</td>\r\n"
				+ "		</tr>\r\n"
				+ "		<tr>\r\n"
				+ "			<td style=\"background-color:#e2e2e2; border-color:black; border-style:solid; border-width:1px; height:30px; text-align:center; width:15%\"><strong><span style=\"font-size:11px\">작 성 자</span></strong></td>\r\n"
				+ "			<td style=\"border-color:black; border-style:solid; border-width:1px; height:30px\">&nbsp;</td>\r\n"
				+ "		</tr>\r\n"
				+ "		<tr>\r\n"
				+ "			<td style=\"background-color:#e2e2e2; border-color:black; border-style:solid; border-width:1px; height:30px; text-align:center; width:15%\"><strong><span style=\"font-size:11px\">수 신 자</span></strong></td>\r\n"
				+ "			<td style=\"border-color:black; border-style:solid; border-width:1px; height:30px\">&nbsp;</td>\r\n"
				+ "		</tr>\r\n"
				+ "	</tbody>\r\n"
				+ "</table>\r\n"
				+ "<p>&nbsp;</p>\r\n"
				+ sanctionFormVO.getFormSourc();
		
		sanctionFormVO.setFormSample(prehtml);
		
		
		
		int status = dao.insertSanctionForm(sanctionFormVO);
		if(status > 0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<SanctionFormVO> retrieveSanctionFormList() {
		// 결재문서양식 List 조회(기안자용)
		return dao.selectSanctionFormList();
	}

	@Override
	public SanctionFormVO retrieveSanctionFormSourc(int formNo) {
		// 결재문서양식 form 조회
		return dao.selectSanctionFormSourc(formNo);
	}

	@Override
	public List<SanctionFormVO> retrieveSanctionFormAllList() {
		// 전자결재문서양식 List 조회
		return dao.selectSanctionFormAllList();
	}

	@Override
	public int modifySanctionForm(SanctionFormVO sanctionForm) {
		return 0;
	}




	
	
}
