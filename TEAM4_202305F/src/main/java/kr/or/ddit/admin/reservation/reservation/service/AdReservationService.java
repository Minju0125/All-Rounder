package kr.or.ddit.admin.reservation.reservation.service;

import java.util.List;

import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

/**
 * 
 * @author 작성자명
 * @since 2023. 12. 1.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 1.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface AdReservationService {
	
	/**
	 * 모든 예약 내역 조회 (차량)
	 * @return
	 */
	List<VehicleReservationVO> retrieveReservationListVhcle();
	
	/**
	 * 모든 예약 내역 조회 (회의실) 
	 * @return
	 */
	List<ConfRoomReservationVO> retrieveReservationListConf();
	
	public int removeReservationVhcle(String reservationCd);
	
	public int removeReservationConf(String reservationCd);
	
	
}
