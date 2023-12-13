package kr.or.ddit.groupware.attendance.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.groupware.AnnualVO;
import kr.or.ddit.vo.groupware.AttendanceLogVO;
import kr.or.ddit.vo.groupware.AttendanceVO;

public interface AttendanceService {

	void attendanceInsert(AttendanceLogVO alVO);

	AttendanceVO selectAttendance(String empCd);

	List<AttendanceLogVO> attendanceLogList(AttendanceLogVO alVO);

	void commute(AttendanceVO aVO);

	double attendanceWeek(AttendanceVO aVO);

	List<AttendanceVO> attendanceWeekly(AttendanceVO aVO);

	List<Object> annualStatistics(String empCd);

	List<AnnualVO> annualList();

	List<Map<String, String>> annualLeaveUsageRateByRank();

}
