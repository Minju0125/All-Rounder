package kr.or.ddit.admin.reservation.reservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.reservation.reservation.service.AdReservationService;
import kr.or.ddit.admin.reservation.vehicle.service.AdVehicleService;
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
 * 수정일              수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 1.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@RequestMapping("/adReservation")
@Controller
public class AdReservationController {
	
	@Inject
	AdVehicleService adVService;
	
	@Inject
	AdReservationService adRService;
	
	@GetMapping("/adReservationHome")
	public String adReservationHome() {
		return "admin/reservation/adReservationHome";
	}
	
	//차량 예약 리스트
	@GetMapping("/vhcleReservationList")
	@ResponseBody
	public Map<String,List<VehicleReservationVO>> vhcleReservationList(){
		Map<String, List<VehicleReservationVO>> resultMap = new HashMap<>();
		List<VehicleReservationVO> vhcleReservationList = adRService.retrieveReservationListVhcle();
		resultMap.put("vrList", vhcleReservationList);
		return resultMap;
	}
	
	//회의실 예약 리스트
	@GetMapping("/confReservationList")
	@ResponseBody
	public Map<String,List<ConfRoomReservationVO>> confReservationList(){
		Map<String, List<ConfRoomReservationVO>> resultMap = new HashMap<>();
		List<ConfRoomReservationVO> vhcleReservationList = adRService.retrieveReservationListConf();
		resultMap.put("crList", vhcleReservationList);
		return resultMap;
	}
	
	@DeleteMapping("/conf/{reserveCd}")
	@ResponseBody
	public Map<String, String> deleteConfReservation(@PathVariable String reserveCd) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String success ="Fail";
		if(adRService.removeReservationConf(reserveCd)>0) {
			success ="OK";
		}
		resultMap.put("success", success);
		return resultMap;
	}
	
	@DeleteMapping("/vhcle/{reserveCd}")
	@ResponseBody
	public Map<String, String> deleteVhcleReservation(@PathVariable String reserveCd) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String success ="Fail";
		if(adRService.removeReservationVhcle(reserveCd)>0) {
			success ="OK";
		}
		resultMap.put("success", success);
		return resultMap;
	}
}
