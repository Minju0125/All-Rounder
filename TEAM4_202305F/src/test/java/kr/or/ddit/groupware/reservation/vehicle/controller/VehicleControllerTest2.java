package kr.or.ddit.groupware.reservation.vehicle.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import kr.or.ddit.admin.reservation.vehicle.dao.AdVehicleDAO;
import kr.or.ddit.admin.reservation.vehicle.service.AdVehicleService;
import kr.or.ddit.admin.reservation.vehicle.service.AdVehicleServiceImpl;
import kr.or.ddit.vo.groupware.VehicleVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class VehicleControllerTest2 {

	AdVehicleService service = new AdVehicleServiceImpl();
	
	@Test
	void testDoGet() {
		List<VehicleVO> vehicleList =  service.retrieveVehiclelist();
		log.info("vehicleList.size() ===> "+ vehicleList);
		assertEquals(vehicleList, null);
	}

	@Test
	void testDoGetOne() {
		fail("Not yet implemented");
	}

	@Test
	void testDoPost() {
		fail("Not yet implemented");
	}

	@Test
	void testDoPut() {
		fail("Not yet implemented");
	}

	@Test
	void testDoDelete() {
		fail("Not yet implemented");
	}

}
