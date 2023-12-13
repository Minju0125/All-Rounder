package kr.or.ddit.vo.groupware;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 작성자명
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.  박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of="confReserveCd")
public class ConfRoomReservationVO implements Serializable{
	private String confReserveCd; /* 예약코드 */
	private String confRoomCd; /* 회의실 코드 */
	private String confDate; /* 회의실 예약일 */
	private String confTimeCd; /* 회의실 예약 시간 코드 */
	
	private String confTime ; /* 회의실 사용 시간 (2023-11-30 추가 : 박민주) */
	
	private String confReserveEmpCd; /* 예약자 사번  */
	private String confReservePw; /* 예약용 비밀번호 */
	
	private String confReserveEmpNm;/* 예약자명 */
	private String confReserveEmpRankNm;/* 예약자 직급명 */
	private String confReserveEmpDeptNm; /* 예약자 부서명 */
	private String confReserveEmpProfileImg; /* 예약자 프로필사진 */
	
	private ConfRoomVO confRoom; //1:1 관계
}
