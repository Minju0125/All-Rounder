package kr.or.ddit.vo;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 전수진
 * @since 2023. 11. 13.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 13.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "alarmNo")
public class AlarmVO {

//	누가(발신자)
//	누구(수신자)에게
//	어디서(클릭 시 이동할 위치)
//	무얼(내용)을 했는지
//
//	ex) 누가님이 어디서 누구님을 내용했습니다.
//	       '리더'님이 '상위 일감 1번'에서 '담당자 1'님을 '담당자로 편성'하였습니다.	

	private Integer alarmNo; 		//알림번호
	private String alarmSender; 	// 발신자
	private String alarmReceiver;	// 수신자
	private String alarmSource;		// 발신url
	private String alarmContent;	// 내용
	private LocalDateTime alarmDate;// 알림일자
	private String alarmRead;		// 읽음여부 ; N(안 읽음), Y(읽음)
	private String alarmType;		// 알림타입
	private String alarmUrl;		// 이동url ; 알림 클릭 시 이동할 페이지
}
