package kr.or.ddit.groupware.reservation.vehicle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import javax.inject.Inject;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import kr.or.ddit.groupware.reservation.vehicle.dao.VehicleDAO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

@ContextConfiguration
class VehicleServiceImplTest {

	@Inject
	private VehicleDAO dao;

	@Test
	void testCreateReservation() {
		VehicleReservationVO reservation = new VehicleReservationVO();
		reservation.setVhcleReserveCd(null);
		reservation.setVhcleReserveEmpCd("E200203001");
		reservation.setVhcleReservePw("1234");
		reservation.setVhcleReservePur("고객사 미팅");
		String randomDateString = RandomDate2022();
		reservation.setVhcleUseDate(randomDateString);
		reservation.setVhcleUseTimeCd("R_V2");
		reservation.setVhcleCd("210507V104");
		dao.insertVehicleReservation(reservation);
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
