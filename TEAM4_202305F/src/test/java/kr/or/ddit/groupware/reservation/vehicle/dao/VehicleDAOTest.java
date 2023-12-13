package kr.or.ddit.groupware.reservation.vehicle.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import kr.or.ddit.vo.groupware.VehicleReservationVO;

class VehicleDAOTest {

	@Test
	void testSelectVehicleReservationList() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectMyVehicleReservationList() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertVehicleReservation() {
		VehicleReservationVO reservation = new VehicleReservationVO();
		reservation.setVhcleCd("231124V041");
		reservation.setVhcleReserveCd("R231128V0001");
		reservation.setVhcleReserveEmpCd("E231121002");
		reservation.setVhcleUseDate("2023-11-25");
		reservation.setVhcleReservePur("외근");
		reservation.setVhcleReservePw("0000");
		
	}
	@Test
	void testUpdateVehicleReservation() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteVehicleReservation() {
		fail("Not yet implemented");
	}

}
