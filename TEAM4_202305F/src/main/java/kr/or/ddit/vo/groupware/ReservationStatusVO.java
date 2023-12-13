package kr.or.ddit.vo.groupware;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 자원예약에서 자원 또는 예약 코드와 상태를 한곳에 담아다닐 수 있는 VO
 * @author 박민주
 * @since 2023. 11. 30.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 30.   박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "reserveCd")
public class ReservationStatusVO implements Serializable{
	private String reserveCd; /* 예약코드 또는 자원코드 */
	private String status; /* 상태 */
}