package kr.or.ddit.vo.pms;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import kr.or.ddit.vo.CommonVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 작성자명
 * @since 2023. 11. 13.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 13.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "scheduleCd")
public class CalendarVO implements Serializable{

	@NotBlank
	private String scheduleCd;
	@NotBlank
	private String scheduleSj;
	@NotBlank
	private String scheduleCn;
	@NotBlank
	private String scheduleBgnDt;
	@NotBlank
	private String scheduleEndDt;
	private String schedulePlace;
	@NotBlank
	private String empCd;
	private String scheduleAlarmYn;
	@NotBlank
	private String scheduleBcolor;
	@NotBlank
	private String scheduleFcolor;
	private String scheduleDayYn;
	private Integer jobSn;
	private String proSn;
	private String scheduleSharer;
	
	private String PROJECTEMPCD;
	
	private String deptName; // 부서명
	
	private String searchCd; // 체크박스 검색 
	
	private List<PjobVO> pjobList;
	

}
