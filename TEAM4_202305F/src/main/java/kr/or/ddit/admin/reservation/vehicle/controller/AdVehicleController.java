package kr.or.ddit.admin.reservation.vehicle.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.admin.reservation.vehicle.service.AdVehicleService;
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
 * 2023. 11. 24.    박민주		최초작성

 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Controller
@RequestMapping("/adVehicle")
@Slf4j
public class AdVehicleController {
	/*
	 * /vehicle (get) : 전체 차량 조회 /vehicle/V001 (get) : V001 차량정보조회 /vehicle (post) :
	 * 신규차량등록 /vehicle/V001 (put) : V001 차량정보 수정 /vehicle/V001 (delete) : V001 차량 삭제
	 */

	@Inject
	private AdVehicleService service;

	@Value("#{appInfo.vehicleImageUrl}")
	private String vehicleImageUrl;

	@Value("#{appInfo.vehicleImageUrl}")
	private Resource vehicleFile; // 폴더는 리소스로 받아와야함

	// 관리자 차량관리 페이지로 이동
	@GetMapping("/adVehicleHome")
	public String adVehicleHome() {
		return "admin/reservation/adVehicleHome";
	}

	// 전체 차량 내역 조회
	@GetMapping
	@ResponseBody
	public List<VehicleVO> doGet(Model model) {
		List<VehicleVO> vehicleList = service.retrieveVehiclelist();
		return vehicleList;
	}
	
	@GetMapping("/getMaxCount/{selectedStair}")
	@ResponseBody
	public Map<String,String> doGetMaxCount(@PathVariable String selectedStair) {
		Map<String,String> resultMap = new HashMap<>();
		String nextConfroomCd = service.retrieveNextConfroomCode(selectedStair);
		if(nextConfroomCd.length() == 1) {
			nextConfroomCd = selectedStair + "001";
		}
		resultMap.put("nextConfroomCd", nextConfroomCd);
		return resultMap;
	}

	// 차량 상세 조회
	@GetMapping("{vhcleCd}")
	@ResponseBody
	public VehicleVO doGetOne(@PathVariable("vhcleCd") String vhcleCd) {
		List<VehicleVO> vehicleList = service.retrieveVehiclelist();
		VehicleVO vehicleVO = null;
		for (VehicleVO vehicle : vehicleList) {
			String vehicleCdData = vehicle.getVhcleCd();
			if (vehicleCdData.equals(vhcleCd)) {
				vehicleVO = vehicle;
			}
		}
		return vehicleVO;
	}

	//차량 등록
	@PostMapping
	@ResponseBody
	public String doPost(@Validated @RequestPart("vehicleVO") VehicleVO vehicleVO, 
			BindingResult errors, @RequestPart("file") MultipartFile upload, HttpServletRequest req) throws IOException {
		
		log.info("hasErrors? ==> " + errors.hasErrors());
		
		log.info("vehicleVO ===> " + vehicleVO);
		log.info("vehicleVO ===> " + upload.getOriginalFilename());

		String saveName = "";
		String res ="FAIL";
		
		if(!upload.isEmpty()) { //업로드할 파일이 존재하는 경우
			saveName = UUID.randomUUID().toString();
			String url = req.getContextPath() + vehicleImageUrl + "/" + saveName;
			vehicleVO.setVhcleImg(url);
			
			File saveFolder = vehicleFile.getFile();
			File saveFile = new File(saveFolder, saveName);
			
			if(service.createVehicle(vehicleVO)>0){
				upload.transferTo(saveFile);
				res = "OK";
			}
		}else { //업로드할 파일이 존재하지 않는 경우
			//넘어온 데이터를 바로 데이터베이스에 저장
			if(service.createVehicle(vehicleVO)>0) {
				res = "OK";
			}
		}
		return res;
	}

	// 차량 삭제
	@DeleteMapping("{vhcleCd}")
	@ResponseBody
	public String doDelete(@PathVariable String vhcleCd) {
		String res ="FAIL";
		if(service.removeVehicle(vhcleCd)>0) {
			res = "OK";
		}
		return res;
	}
	
	// 차량 상세정보 수정
	@PutMapping("{vhcleCd}")
	@ResponseBody
	public String doPut(@RequestBody VehicleVO vehicle) {
		log.info("vehicleVO ===> "+ vehicle);
		String res= "FAIL";
		if(service.modifyVehicle(vehicle)>0) {
			res = "OK";
		}
		return res;
	}
}
