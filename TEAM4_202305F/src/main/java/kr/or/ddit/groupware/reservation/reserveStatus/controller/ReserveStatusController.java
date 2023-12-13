package kr.or.ddit.groupware.reservation.reserveStatus.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.groupware.reservation.reserveStatus.service.ReserveStatusService;
import kr.or.ddit.vo.groupware.ReservationStatusVO;

/**
 * 자원코드, 예약코드를 보내 사용 가능 상태 또는 예약 상태를 판단하는 controller
 * @author 작성자명
 * @since 2023. 11. 30.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 30.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/reserveStatus")
public class ReserveStatusController {
	
	@Inject
	ReserveStatusService statusService;
	
	//vehicleHome 에서 차량 사용상태 표시
	@GetMapping("/vehicleStatus")
	@ResponseBody
	public Map<String, List<ReservationStatusVO>> setVehicleStatus() {
		Map<String, List<ReservationStatusVO>> statusMap = new HashMap<>();
		List<ReservationStatusVO> statusList = statusService.retrieveVehicleStatus();
		statusMap.put("statusList", statusList);
		return statusMap;
	}
	
	//confRoomHome 에서 회의실 사용상태 표시
	@GetMapping("/confRoomStatus")
	@ResponseBody
	public Map<String, List<ReservationStatusVO>> setConfRoomStatus() {
		Map<String, List<ReservationStatusVO>> statusMap = new HashMap<>();
		List<ReservationStatusVO> statusList = statusService.retrieveConfRoomStatus();
		statusMap.put("statusList", statusList);
		return statusMap;
	}
	
	//myReservationHome 에서 내 예약 중 차량예약 상태 표시
	@GetMapping("/myVehicleReserveStatus")
	public List<ReservationStatusVO> setMyVehicleReservationStatus() {
		
		return null;
	}
	
	//myReservationHome 에서 내 예약 중 차량예약 상태 표시
	@GetMapping("/myconfRoomReserveStatus")
	public List<ReservationStatusVO> setMyConfRoomReservationStatus() {
		
		return null;
	}
	
}
