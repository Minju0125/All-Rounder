package kr.or.ddit.groupware.reservation.confroom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.reservation.confroom.service.AdConfRoomService;
import kr.or.ddit.groupware.reservation.confroom.service.ConfRoomService;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.ConfRoomVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일             수정자      수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.    박민주       최초작성
 * 2023. 11. 29.	박민주	   회의실 예약 CRUD 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/confRoom")
public class ConfRoomController {
	/*
		confRoom/confRoomHome (GET) : 회의실 예약 페이지로 이동
		confRoom/reserve/ (GET) : 전체 예약 목록 조회
		confRoom/reserve/${사번?} (GET) : 특정 사번의 예약 목록 조회
		confRoom/reserve/ (POST) : 신규 예약 추가
		confRoom/reserve/${confRoomReservationCd} (DELETE) : 예약 삭제
		confRoom/reserve/ (PUT) : 예약 수정
	*/
	
	@Inject
	AdConfRoomService adService;
	
	@Inject
	ConfRoomService service;
	
	//회의실 예약 페이지로 이동
	@GetMapping("/confRoomHome")
	public String confRoomHome(Model model) {
		List<ConfRoomVO> confRoomList = adService.retrieveConRoomList();
		List<ConfRoomReservationVO> reserveList =  service.retrieveConfRoomReservationList();
		List<CommonVO> commonList = service.retrieveCommonConfTimeCdList();
		
		model.addAttribute("confRoomList", confRoomList); //전체 회의실 목록
		model.addAttribute("reserveList", reserveList); //전체 회의실 예약 목록
		model.addAttribute("commonList", commonList); //공통코드 리스트 목록
		
		return "reservation/confRoomHome";
	}
	
	@PostMapping("/reserve")
	@ResponseBody
	public Map<String, Object> doPost(@RequestBody ConfRoomReservationVO reservation){
		int result = service.createConfRoomReservation(reservation);
		Map<String, Object> resultMap = new HashMap<>();
		String success = "Fail";
		if(result>0) {
			success = "OK";
		}
		resultMap.put("success", success);
		return resultMap;
	}
	
	@GetMapping("/reservedTimeCheck/{confRoomCd}")
	@ResponseBody
	public List<String> reservedTimeCheck(@PathVariable String confRoomCd, @RequestParam String confRoomUseDate){
		List<ConfRoomReservationVO> reserveList =  service.retrieveConfRoomReservationList();
		List<String> reservedTimeList = new ArrayList<String>();
		for(ConfRoomReservationVO reservation : reserveList) {
			if(confRoomCd.equals(reservation.getConfRoomCd()) && confRoomUseDate.equals(reservation.getConfDate())){
				reservedTimeList.add(reservation.getConfTimeCd());
			}
		}
		return reservedTimeList; 
	}
}