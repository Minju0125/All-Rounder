package kr.or.ddit.groupware.attendance.service;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.groupware.attendance.dao.AttendanceDAO;
import kr.or.ddit.vo.groupware.AnnualVO;
import kr.or.ddit.vo.groupware.AttendanceLogVO;
import kr.or.ddit.vo.groupware.AttendanceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Inject
	private AttendanceDAO dao;

	@Override
	public void attendanceInsert(AttendanceLogVO alVO) {
		dao.attendanceInsert(alVO);
	}

	@Override
	public AttendanceVO selectAttendance(String empCd) {
		return dao.selectAttendance(empCd);
	}

	@Override
	public List<AttendanceLogVO> attendanceLogList(AttendanceLogVO alVO) {
		return dao.attendanceLogList(alVO);
	}

	@Override
	public void commute(AttendanceVO aVO) {
		dao.commute(aVO);
	}

	@Override
	public double attendanceWeek(AttendanceVO aVO) {
		thisWeekly(aVO);
        List<AttendanceVO> aList=dao.attendanceWeek(aVO);
        int totalWorkOfWeek=0;
		LocalDateTime now = LocalDateTime.now();
        for(AttendanceVO vo:aList) {
        	LocalTime sTime=LocalTime.parse(vo.getStartTime(), DateTimeFormatter.ofPattern("HHmmss"));
    		LocalTime eTime=LocalTime.parse(vo.getEndTime(), DateTimeFormatter.ofPattern("HHmmss"));
    		LocalDateTime sdTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), sTime.getHour(), sTime.getMinute(), sTime.getSecond());
    		LocalDateTime edTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), eTime.getHour(), eTime.getMinute(), eTime.getSecond());
    		int day=0;
    		if(sTime.compareTo(eTime)==1) {
    			day+=1;
    		}
    		LocalDateTime tdTime=edTime.minusHours(sdTime.getHour())
                    .minusMinutes(sdTime.getMinute())
                    .minusSeconds(sdTime.getSecond());
            log.info("tdTime : {}",tdTime);
//    		if(tdTime.getDayOfMonth()-sdTime.getDayOfMonth()==1) {
//    			day+=1;
//    		}
    		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HHmmss");
    		totalWorkOfWeek += Integer.parseInt(tdTime.format(formatter))+day*24;
            log.info("day : {}",day);
            log.info("더하는 시간 : {}",totalWorkOfWeek);
        }
        double totalWorkOfWeekPercent=totalWorkOfWeek/40*0.01;
        log.info("근무 퍼센트 : {}",totalWorkOfWeekPercent);
        log.info("근무시간 : {}",totalWorkOfWeek);
		return totalWorkOfWeekPercent;
	}
	
	public void thisWeekly(AttendanceVO aVO) {
		LocalDate today=LocalDate.now().minusDays(7*aVO.getWeek());
		LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate endOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedStart = startOfWeek.format(formatter);
        String formattedEnd = endOfWeek.format(formatter);
        aVO.setStartOfWeek(formattedStart);
        aVO.setEndOfWeek(formattedEnd);
		
	}

	@Override
	public List<AttendanceVO> attendanceWeekly(AttendanceVO aVO) {
		thisWeekly(aVO);
		List<AttendanceVO> aList=dao.attendanceWeekly(aVO);
		if(aList.size()>0) {
			aList.get(0).setStartOfWeek(aVO.getStartOfWeek());
			aList.get(0).setEndOfWeek(aVO.getEndOfWeek());
		}else {
			aList.add(aVO);
		}
		return aList;
	}

	@Override
	public List<Object> annualStatistics(String empCd) {
		int yearAnnual=dao.selectYearAnnual(empCd);
		int awardAnnual=dao.selectAwardAnnual(empCd);
		double halfAnnual=dao.selectHalfAnnual(empCd)*0.5;
		List<Integer> useAnnual=dao.selectUseAnnaul(empCd);
		double sumAnnual=0;
		for(int a : useAnnual) {
			sumAnnual+=a;
		}
		sumAnnual+=halfAnnual;
		
		List<Object> annualList=new ArrayList<Object>();
		annualList.add(yearAnnual);
		annualList.add(awardAnnual);
		annualList.add(sumAnnual);
		
		return annualList;
	}

	@Override
	public List<AnnualVO> annualList() {
		LocalDate today=LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formattedToday = today.format(formatter);
		return dao.annualList(formattedToday);
	}

	@Override
	public List<Map<String, String>> annualLeaveUsageRateByRank() {
		List<Map<String, String>> aList=dao.annualLeaveUsageRateByRank();
		return aList;
	}
}
