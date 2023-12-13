package kr.or.ddit.groupware.reservation.myReservation.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.groupware.reservation.confroom.service.ConfRoomService;
import kr.or.ddit.groupware.reservation.reserveStatus.service.ReserveStatusService;
import kr.or.ddit.groupware.reservation.vehicle.service.VehicleService;
import kr.or.ddit.paging.BootstrapPaginationRendererMyReservationHome;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 29.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 29.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Controller
@Slf4j
@RequestMapping("/myReservation")
public class MyReservationController {

	@Inject
	private VehicleService vService;
	@Inject
	private ConfRoomService cService;
	@Inject
	private ReserveStatusService rsService;

	/**
	 * 내 예약 페이지로 이동
	 * 
	 * @return
	 */
	@GetMapping("/myReservationHome")
	public String myReservationHome() {
		return "reservation/myReservation";
	}
	
	@GetMapping("/getDataListConf") // 회의실 예약 목록 페이징
	@ResponseBody
	public Map<String, PaginationInfo<ConfRoomReservationVO>> getDataListConf(
			Authentication authentication,
			@RequestParam(value = "pageC", required = false, defaultValue = "1") int currentPageC
	){
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("empCd", authentication.getName());
		variousCondition.put("aria", "conf"); //conf에서 발생한 페이지네이션을 표시하기 위한 map
		
		Map<String, PaginationInfo<ConfRoomReservationVO>> map = new HashMap<>();
		PaginationInfo<ConfRoomReservationVO> paging = new PaginationInfo<ConfRoomReservationVO>(7, 5);
		
		paging.setCurrentPage(currentPageC);
		paging.setVariousCondition(variousCondition);
		paging.setRenderer(new BootstrapPaginationRendererMyReservationHome());
		List<ConfRoomReservationVO> dataList = cService.retrieveMyConfRoomReservationList(paging);
		paging.setDataList(dataList);
		
		List<Map<String, String>> statusMapList = rsService.retrieveMyConfRoomReservationStatus(dataList);
		variousCondition.put("statusMapList", statusMapList);
		
		map.put("paging", paging);
		log.info("confRroom 페이징 성공");
		
		return map;
	}
	
	@GetMapping("/getDataListVhcle") //차량 예약 목록 페이징
	@ResponseBody
	public Map<String, PaginationInfo<VehicleReservationVO>> getDataListVhcle(
			Authentication authentication,
			@RequestParam(value = "pageV", required = false, defaultValue = "1") int currentPageV
			){
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("empCd", authentication.getName());
		variousCondition.put("aria", "vhcle"); //vhcle에서 발생한 페이지네이션을 표시하기 위한 map
		
		Map<String, PaginationInfo<VehicleReservationVO>> map = new HashMap<>();
		PaginationInfo<VehicleReservationVO> paging = new PaginationInfo<VehicleReservationVO>(7, 5);
		paging.setCurrentPage(currentPageV);
		paging.setVariousCondition(variousCondition);
		paging.setRenderer(new BootstrapPaginationRendererMyReservationHome());
		List<VehicleReservationVO> dataList = vService.retrieveMyVehicleReservationList(paging);
		paging.setDataList(dataList);
		
		List<Map<String, String>> statusMapList = rsService.retrieveMyVehicleReservationStatus(dataList);
		variousCondition.put("statusMapList", statusMapList);
		
		map.put("paging", paging);
		log.info("vhcle 페이징 성공");
		
		return map;
	}
	
	@DeleteMapping("/deleteReservation/{reserveCd}")
	@ResponseBody
	public Map<String, String> deleteReservation(@PathVariable String reserveCd) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String success ="Fail";
		if(reserveCd.contains("V")) {
			if(vService.removeReservation(reserveCd)>0) {
				success ="OK";
			}
		}else {
			if(cService.removeConfRoomReservation(reserveCd)>0) {
				success ="OK";
			}
		}
		resultMap.put("success", success);
		return resultMap;
	}
	
	@GetMapping("/getReservationPassword/{reserveCd}")
	@ResponseBody
	public Map<String, String> getReservationPassword(@PathVariable String reserveCd){
		Map<String, String> resultMap = new HashMap<String, String>();
		if(reserveCd.contains("V")) {
			String vPassword= vService.retrieveReservationPasswordVehicle(reserveCd);
			if(vPassword!=null) {
				resultMap.put("password", vPassword);
			}
		}else {
			String cPassword= cService.retrieveReservationPasswordConfRoom(reserveCd);
			if(cPassword!=null) {
				resultMap.put("password", cPassword);
			}
		}
		return resultMap;
	}
}
