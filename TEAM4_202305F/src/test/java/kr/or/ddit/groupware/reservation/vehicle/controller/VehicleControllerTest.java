package kr.or.ddit.groupware.reservation.vehicle.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.ddit.groupware.reservation.vehicle.service.VehicleService;
import kr.or.ddit.vo.groupware.VehicleReservationVO;
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml")
class VehicleControllerTest {

	@Inject
	VehicleService vehicleService;
	
	@Test
	void testDoPost() {
		for(int i=0; i<22; i++) {
			VehicleReservationVO reservation = new VehicleReservationVO();
			reservation.setVhcleReserveCd(null);
			reservation.setVhcleReserveEmpCd("E220321001");
			reservation.setVhcleReservePw("1234");
			reservation.setVhcleReservePur("외근");
			String randomDateString = RandomDate2022();
			reservation.setVhcleUseDate(randomDateString);
			reservation.setVhcleUseTimeCd("R_V1");
			reservation.setVhcleCd("230807V106");
			vehicleService.createReservation(reservation);
		}
	}
	private String RandomDate2022() {
		// 2022년 1월 1일부터 2022년 12월 31일까지의 범위에서 랜덤으로 날짜 선택
		LocalDate start = LocalDate.of(2022, Month.JANUARY, 1);
        LocalDate end = LocalDate.of(2022, Month.DECEMBER, 31);

        long startEpochDay = start.toEpochDay();
        long endEpochDay = end.toEpochDay();

        Random random = new Random();
        long randomDay = startEpochDay + random.nextInt((int) (endEpochDay - startEpochDay));

        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        String randomDateString = randomDate.toString();
        return randomDateString;
	}

}
