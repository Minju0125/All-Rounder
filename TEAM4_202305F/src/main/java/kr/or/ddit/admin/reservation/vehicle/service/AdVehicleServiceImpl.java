package kr.or.ddit.admin.reservation.vehicle.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.reservation.vehicle.dao.AdVehicleDAO;
import kr.or.ddit.vo.groupware.VehicleVO;
/**
 * 
 * @author 작성자명
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일             수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 23.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Service
public class AdVehicleServiceImpl implements AdVehicleService{

	@Inject
	private AdVehicleDAO dao;
	
	@Override
	public List<VehicleVO> retrieveVehiclelist() {
		return dao.selectVehicleList();
	}

	@Override
	public VehicleVO retreiveVehicle(String vhcleCd) {
		return dao.selectVehicle(vhcleCd);
	}
	
	@Override
	public List<VehicleVO> retrieveVehicleReservationList() {
		return dao.selectVehicleReservationList();
	}

	@Override
	public int createVehicle(VehicleVO vehicle) {
		return dao.insertVehicle(vehicle);
	}

	@Override
	public int modifyVehicle(VehicleVO vehicle) {
		return dao.updateVehicle(vehicle);
	}

	@Override
	public int removeVehicle(String vhcleCd) {
		return dao.deleteVehicle(vhcleCd);
	}

	@Override
	public String retrieveNextConfroomCode(String selectedStair) {
		return dao.selectNextConfroomCode(selectedStair);
	}
	
}
