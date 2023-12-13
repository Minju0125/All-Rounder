package kr.or.ddit.vo;

import lombok.Data;

/**
 * @author 작성자명
 * @since 2023. 12. 5.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 12. 5.      김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Data
public class ChartVO {
	
	private int thisMonth;  // 1~12월
	private int hireCnt; // 입사 직원수
	private int leaveCnt; // 퇴사 직원수
	
	private String workTime; //근무시간
	private String deftName; //부서이름
	
	
	private int nomalissue; //일반이슈개수
	private int  highissue; //결함이슈개수
	private int uppercount; //상위일감개수
	private int lowcount; //하위일감개수
	
	
	
}
