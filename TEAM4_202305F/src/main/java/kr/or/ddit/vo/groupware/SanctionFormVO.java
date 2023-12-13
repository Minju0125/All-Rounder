package kr.or.ddit.vo.groupware;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 전수진
 * @since 2023. 11. 14.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 14.  전수진       최초작성
 * 2023. 12. 08.  전수진       관리자용page를 위해 컬럼 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "formNo")
public class SanctionFormVO {

	private Integer formNo;		// 양식번호 ; 1 ~
	private String formNm;		// 양식명
	@ToString.Exclude
	private String formSourc;	// 양식HTML소스
	@ToString.Exclude
	private String formSample;	// 양식Sample HTML소스

	private String formUse;
	private String formInfo;
	
	
	// Has Many관계(1:N 관계형성)
	private List<SanctionVO> sanctionList;	// 전자결재문서 리스트
}
