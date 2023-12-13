package kr.or.ddit.groupware.attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.ddit.admin.attendance.dao.AdminAttendanceDAO;
import kr.or.ddit.groupware.attendance.dao.AttendanceDAO;
import kr.or.ddit.vo.groupware.AttendanceLogVO;
import kr.or.ddit.vo.groupware.AttendanceVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml")
class AttendanceDAOtest {

	@Inject
	AttendanceDAO dao;
	
	@Inject
	AdminAttendanceDAO aDao;
	
	Random rand=new Random();
	
	@Test
	@Disabled
	void listTest() {
		List<Map<Object, Object>> empList= dao.attendanceList();
		List<AttendanceVO> aList = dao.attendanceDateList();
//		for(Map<Object, Object> m:empList) {
//			List<Integer> vacation = new ArrayList<Integer>();
//			for (int i = 0; i < rand.nextInt(16); i++) {
//				int a=rand.nextInt(aList.size());
//				if(vacation.contains(a)) {
//					i-=1;
//				}else {
//					vacation.add(a);
//				}
//	        }
//			
//			int i=0;
//			int randDay=rand.nextInt(aList.size());
//			int a=rand.nextInt(4);
//			for(AttendanceVO aVO:aList) {
//				i++;
//				if(randDay==i) {
//					if(a==0) {	// 포상
//						m.put("AA_GDAY",aVO.getAttDate());
//						dao.insertAwardAnnual(m);
//						a=1;
//					}
//				}
//				
//				if(vacation.contains(i)) {
//					m.put("V_SDAY",aVO.getAttDate());
//					if(rand.nextInt(2)==1) {
//						m.put("V_FLAG","N");
//						int asd=Integer.parseInt(aVO.getAttDate());
//						m.put("V_EDAY",asd+1);
//						m.put("V_WHY","개인사유");
//					}else {
//						m.put("V_FLAG","H");
//						m.put("V_EDAY",null);
//						m.put("V_WHY","병원");
//					}
//					dao.insertVacation(m);
//				}
//			}
//			i=0;
//		}
		
//		for(int i=0;i<6;i++) {
//			log.info("{}",rand.nextInt(2) * 10000+10000);
//			int a=rand.nextInt(10000);
//			String b=a+"";
//			String c=String.format("%06d", b);
//			log.info(c);
//		}
//		for (int i = 0; i < 6; i++) {
//		    int a = rand.nextInt(1000000); // 0부터 999999까지의 값을 반환
//		    log.info("{}",a);
//		    String c = String.format("%06d", a);
//		    log.info("{}",c);
//		}
	}
	
	@Test
//	@Disabled
	void vacationTest() {
		List<Map<Object, Object>> vList= dao.attendanceList();
		for(Map<Object, Object> m:vList) {
			dao.updateAttStime(m);
		}
	}
	
	@Test
	@Disabled
	void insertTest() {
//		List<AttendanceVO> empList= dao.attendanceList();
//		for(int i=0;i<empList.size();i++) {
			
//		}
//		AttendanceLogVO alVO= new AttendanceLogVO();
//		alVO.setEmpCd("E220401001");
//		alVO.setAttLog("M");
//		dao.attendanceInsert(alVO);
		List<Map<Object, Object>> empList= dao.attendanceList();
		List<AttendanceVO> aList = dao.attendanceDateList();
		for(Map<Object, Object> m:empList) {
			List<Integer> vacation = new ArrayList<Integer>();
			for (int i = 0; i < rand.nextInt(16); i++) {
				int a=rand.nextInt(aList.size());
				if(vacation.contains(a)) {
					i-=1;
				}else {
					vacation.add(a);
				}
	        }
			log.info("{}",vacation);
		}
	}

	@Test
	@Disabled
	void dateTest() {
		LocalDate today=LocalDate.now();
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedStart = startOfWeek.format(formatter);
        String formattedEnd = endOfWeek.format(formatter);
        String formattedToday = today.format(formatter);
		log.info("formattedStart {}",formattedStart);
		log.info("formattedEnd {}",formattedEnd);
		log.info("formattedToday {}",formattedToday);
	}

	@Test
	@Disabled
	void timeTest() {
		LocalTime time1=LocalTime.parse("135912", DateTimeFormatter.ofPattern("HHmmss"));
		LocalTime time2=LocalTime.parse("094232", DateTimeFormatter.ofPattern("HHmmss"));
		LocalTime tTime=time1.plusHours(time2.getHour())
                .plusMinutes(time2.getMinute())
                .plusSeconds(time2.getSecond());
		log.info("{}",tTime);
		log.info("{}",time1.compareTo(time2));
		
		long differenceInSeconds = time1.until(time2, ChronoUnit.SECONDS);
		log.info("{}",differenceInSeconds);
		if (differenceInSeconds > 86400) { // 24시간을 초 단위로 나타내는 값인 86400초보다 큰 경우
            System.out.println("24시간을 초과했습니다.");
        } else {
            System.out.println("24시간을 초과하지 않았습니다.");
        }
		
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime dTime1 = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), time1.getHour(), time1.getMinute(), time1.getSecond());
		LocalDateTime tdTime=dTime1.plusHours(time2.getHour())
                .plusMinutes(time2.getMinute())
                .plusSeconds(time2.getSecond());
		int day=tdTime.getDayOfMonth()-dTime1.getDayOfMonth();
		log.info("{}",tdTime);
		log.info("{}",day);
	}

	@Test
	@Disabled
	void sampleTest() {
		// 첫 번째 시간을 생성합니다.
        LocalTime time1 = LocalTime.of(23, 0, 0); // 23시 0분 0초

        // 두 번째 시간을 생성합니다.
        LocalTime time2 = LocalTime.of(2, 0, 0); // 2시 0분 0초

        // 두 시간을 더합니다.
        LocalTime sumTime = time1.plusHours(time2.getHour())
                               .plusMinutes(time2.getMinute())
                               .plusSeconds(time2.getSecond());

        // 시간 간의 차이를 초 단위로 계산합니다.
        long differenceInHours = ChronoUnit.HOURS.between(time1,time2);

        if (differenceInHours > 24) { // 24시간을 초 단위로 나타내는 값인 86400초보다 큰 경우
            System.out.println("24시간을 초과했습니다.");
        } else {
            System.out.println("24시간을 초과하지 않았습니다.");
        }
	}

	@Test
	@Disabled
	void sampleTest2() {
		LocalDate today=LocalDate.now().minusDays(7*-1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedToday = today.format(formatter);
		log.info("logInfo : {}",formattedToday);
	}

	@Test
	@Disabled
	void selectRankTest() {
		List<Map<String, String>> a= aDao.selectRank();
		for(Map<String, String> m:a) {
			for (Map.Entry<String, String> entry : m.entrySet()) {
		        String key = entry.getKey();
		        String value = entry.getValue();
		        System.out.println("Key: " + key + ", Value: " + value);
			}
		}
	}

	@Test
	@Disabled
	void sampleTest3() {
		String a="202312";
		log.info("{}",a.length());
		log.info("{}",a.substring(0,4));
		log.info("{}",a.substring(4));
	}
}






