package kr.or.ddit.groupware.reservation.confroom.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.ddit.groupware.reservation.confroom.service.ConfRoomService;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml")
class ConfRoomControllerTest {
	
	@Inject
	ConfRoomService service;
	
	@Test
	void testDoPost() {
		for(int i=0; i<100; i++) {
			ConfRoomReservationVO reservation = new ConfRoomReservationVO();
			reservation.setConfReserveEmpCd("E230821001");
			reservation.setConfReservePw("1234");
			reservation.setConfRoomCd("B006");
			reservation.setConfTimeCd("R_C1");
			reservation.setConfDate(RandomDate2022());
			service.createConfRoomReservation(reservation);
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
