package kr.or.ddit.vo.groupware;

import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * 2023. 11. 29.  전수진       대결권부여자, 수여자 이름, 직급명, 부서명 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "prxsanctnNo")
public class SanctionByProxyVO {

	private String prxsanctnNo;				// 대결권번호 ; PRX00001
	private String prxsanctnAlwnc;			// 대결권부여자 ; 사번
	private String prxsanctnAlwncNm;		// 대결권부여자명(함수)
	private String prxsanctnAlwncRankNm;	// 대결권부여자 직급명(함수)
	private String prxsanctnAlwncDeptName;	// 대결권부여자 부서명(함수)
	private String prxsanctnCnfer;			// 대결권수여자 ; 사번
	private String prxsanctnCnferNm;		// 대결권수여자명(함수)
	private String prxsanctnCnferRankNm;	// 대결권수여자 직급명(함수)
	private String prxsanctnCnferDeptName;	// 대결권수여자 부서명(함수)
	private String alwncDate;				// 대결권 부여일자
	private String extshDate;				// 대결권 소멸일자
	private String alwncReason;				// 대결지정사사유
	private String prxsanctnYn;				// 대결권 사용여부
	
	// Has a 관계 (1:1 관계형성)
	private EmployeeVO emp;					// 대결권 부여자
	
}
