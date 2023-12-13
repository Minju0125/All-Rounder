package kr.or.ddit.groupware.reservation.reserveStatus.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.ReservationStatusVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 30.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일             수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 30.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface ReserveStatusService {

	//vehicleHome 에서 차량 사용상태 표시
	public List<ReservationStatusVO> retrieveVehicleStatus();
	
	//confRoomHome 에서 회의실 사용상태 표시
	public List<ReservationStatusVO> retrieveConfRoomStatus();
	
	//myReservationHome 에서 내 예약 중 차량예약 상태 표시
	public List<Map<String, String>> retrieveMyVehicleReservationStatus(List<VehicleReservationVO> dataList);
	
	//myReservationHome 에서 내 예약 중 차량예약 상태 표시
	public List<Map<String, String>> retrieveMyConfRoomReservationStatus(List<ConfRoomReservationVO> dataList);
	
}