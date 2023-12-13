package kr.or.ddit.admin.reservation.confroom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.reservation.confroom.service.AdConfRoomService;
import kr.or.ddit.vo.groupware.ConfRoomVO;
import kr.or.ddit.vo.groupware.VehicleVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일      수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.      박민주       최초작성
 * 2023. 11. 29.      박민주		 회의실 CRUD 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/adConfRoom")
public class AdConfRoomController {
	
/*
		 	/adConfRoom (GET) : 회의실 목록 조회
		 	/adConfRoom (POST) : 회의실 추가
		 	/adConfRoom/${confCd} (PUT) : 회의실 수정
		 	/adConfRoom/${confCd} (DELETE) : 회의실 삭제
		 	
		 	/adConfRoom/reserve/${confCd} (GET) : 특정 회의실 예약 조회
		 	/adConfRoom/reserve/ (POST) : 회의실 예약 등록
		 	/adConfRoom/reserve/${confCd} (PUT) : 특정 회의실 예약 수정
		 	/adConfRoom/reserve/${confCd} (DELETE) : 특정 회의실 예약 삭제

 */
	@Inject
	AdConfRoomService service;
	
	@GetMapping
	@ResponseBody
	public Map<String, Object> doGet() {
		Map<String, Object> resultMap = new HashMap<>();
		List<ConfRoomVO> confRoomList =  service.retrieveConRoomList(); //등록된 전체 회의실 목록 출력
		resultMap.put("confRoomList", confRoomList);
		return resultMap;
	}
	
	@PostMapping
	@ResponseBody
	public String doPost(@RequestBody ConfRoomVO confRoom){
		int result = service.createConfRoom(confRoom);
		String succeess = "FAIL";
		if(result>0) {
			succeess = "OK";
		}
		return succeess;
	}
	
	@DeleteMapping("{confRoomCd}")
	@ResponseBody
	public String doDelete (@PathVariable String confRoomCd) {
		String suceess = "FAIL";
		if(service.removeConfRoom(confRoomCd)>0) {
			suceess = "OK";
		}
		return suceess;
	}
	
	@PutMapping("{confRoomCd}")
	@ResponseBody
	public String doPut(@RequestBody ConfRoomVO confroom) {
		String res= "FAIL";
		if(service.modifyConfRoom(confroom)>0) {
			res = "OK";
		}
		return res;
	}
}