package kr.or.ddit.groupware.reservation.vehicle.controller;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.reservation.vehicle.service.AdVehicleService;
import kr.or.ddit.groupware.reservation.vehicle.service.VehicleService;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;
import kr.or.ddit.vo.groupware.VehicleVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 29.    박민주		최초작성

 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Controller
@RequestMapping("/vehicle")
@Slf4j
public class VehicleController {

	@Inject
	private VehicleService service;

	@Inject
	private AdVehicleService adService;

	@GetMapping("/vehicleHome")
	public String vehicleHome(Model model) {
		List<VehicleVO> vehicleList = adService.retrieveVehiclelist(); // 차량목록출력을 위한 list
		List<CommonVO> timeCd = service.retrieveCommonVReserveTimeCdList(); // 예약모달에서 시간대 출력을 위한 lsit

		// 차량 코드별 현재 사용 가능 상태 판단
		for (VehicleVO vehicle : vehicleList) {
			String vehiclCd = vehicle.getVhcleCd();
			List<VehicleReservationVO> reserveList = service.retrieveSimpleReservationList(vehiclCd);
			log.info("reserveList ==> " + reserveList);

			if (reserveList != null) {
				for (VehicleReservationVO reserve : reserveList) {
					String vhcleDate = reserve.getVhcleUseTimeCd();// R_V1 또는 R_V2

					String useTime = "";
					if ("R_V1".equals(vhcleDate)) {
						useTime = "09:00-13:00";
					} else {
						useTime = "14:00-18:00";
					}
					String[] timeRanges = useTime.split("-");
					String timeRangeStart = timeRanges[0];
					String timeRangeEnd = timeRanges[1];
					// 현재 시간 가져오기
					LocalTime currentTime = LocalTime.now();
					LocalTime startTime = LocalTime.parse(timeRangeStart);
					LocalTime endTime = LocalTime.parse(timeRangeEnd);
					boolean isInTimeRange = !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
					// 예약이 현재 시간에 해당하는 시간대에 있으면 "사용중"
					if (isInTimeRange) {
						vehicle.setVhcleFlag("1"); // 사용중
						break;
					}
				}
			}
		}
		model.addAttribute("vehicleList", vehicleList);
		model.addAttribute("timeCd", timeCd);
		return "reservation/vehicleHome";
	}

	/**
	 * 특정 차량 조회 (관리자 컨트롤러에도 존재하나 접근할 수 없음)
	 * 
	 * @param vhcleCd
	 * @return
	 */
	@GetMapping("/oneVehicleInfo/{vhcleCd}")
	@ResponseBody
	public Map<String, Object> oneVehicleInfo(@PathVariable String vhcleCd) {
		VehicleVO vehicleVO = adService.retreiveVehicle(vhcleCd);
		List<VehicleReservationVO> reserveList = service.retrieveSimpleReservationList(vhcleCd);
		log.info("여기 ===> " + vehicleVO);
		Map<String, Object> vMap = new HashMap<String, Object>();
		vMap.put("vehicle", vehicleVO);
		vMap.put("reserveList", reserveList);
		return vMap;
	}

	@GetMapping("/oneVehicleReservation/{vhcleCd}")
	@ResponseBody
	public Map<String, Object> vehicleReservation(@PathVariable String vhcleCd, @RequestParam String startDate,
			@RequestParam String endDate) {

		log.info("startDate ==> " + startDate);
		log.info("endDate ==> " + endDate);

		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("vhcleCd", vhcleCd);
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);

		Map<String, Object> resultMap = new HashMap<>();
		List<VehicleReservationVO> resultList = service.retrieveSpecificVehicleReservationList(paramMap);

		resultMap.put("resultList", resultList);

		return resultMap;
	}

	// 차량예약
	@PostMapping("/reserve")
	@ResponseBody
	public Map<String, String> doPost(@RequestBody VehicleReservationVO reservation) {
		Map<String, String> result = new HashMap<String, String>();
		String success = "Fail";
		if(service.createReservation(reservation)>0) {
			success = "OK";
		}
		result.put("success", success);
		return result;
	}

}
