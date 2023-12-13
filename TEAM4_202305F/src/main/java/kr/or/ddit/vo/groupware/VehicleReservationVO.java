package kr.or.ddit.vo.groupware;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 23.      박민주       최초작성
 * 2023. 11. 27.      박민주		vhcleUseTimeCd 필드 삭제
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Data
@EqualsAndHashCode(of = "vhcleReserveCd")
public class VehicleReservationVO implements Serializable{
	
	private String vhcleReserveCd; /* 차량 예약 코드 */
	@Size(min=4, max=4)
	private String vhcleReservePw; /* 취소용비밀번호 */
	private String vhcleReservePur; /* 목적 */
	private String vhcleUseDate; /* 사용일자 */
	private String vhcleCd; /* 차량코드 */
	
	private String vhcleUseTimeCd; /* 차량 사용시간코드 */
	private String vhcleUseTime; /* 공통코드에 들어있는 실제 시간 */
	
	private String vhcleReserveEmpCd; /* 예약자 사번  */
	private String vhcleReserveEmpNm;/* 예약자명 */
	private String vhcleReserveEmpRankNm;/* 예약자 직급명 */
	private String vhcleReserveEmpDeptNm; /* 예약자 부서명 */
	private String vhcleReserveEmpProfileImg; /* 예약자 프로필사진 */
	
	private VehicleVO vhcle; /* 일대일 : 사용차량정보 */
	
	
}