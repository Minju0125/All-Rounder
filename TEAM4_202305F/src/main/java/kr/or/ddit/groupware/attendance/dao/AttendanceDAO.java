package kr.or.ddit.groupware.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.AnnualVO;
import kr.or.ddit.vo.groupware.AttendanceLogVO;
import kr.or.ddit.vo.groupware.AttendanceVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

@Mapper
public interface AttendanceDAO {

	void attendanceInsert(AttendanceLogVO alVO);

	AttendanceVO selectAttendance(String empCd);

	List<AttendanceLogVO> attendanceLogList(AttendanceLogVO alVO);

	void commute(AttendanceVO aVO);
	
	List<Map<Object, Object>> attendanceList();

	List<AttendanceVO> attendanceWeek(AttendanceVO aVO);

	List<AttendanceVO> attendanceWeekly(AttendanceVO aVO);

	int selectYearAnnual(String empCd);

	int selectAwardAnnual(String empCd);

	int selectHalfAnnual(String empCd);

	List<Integer> selectUseAnnaul(String empCd);

	List<AnnualVO> annualList(String formattedToday);

	List<Map<String, String>> annualLeaveUsageRateByRank();

	void updateAttendance(AttendanceVO eVO);

	void insertPaidAnnual(Map<Object, Object> m);

	List<AttendanceVO> attendanceDateList();

	void insertAwardAnnual(Map<Object, Object> m);

	void insertVacation(Map<Object, Object> m);

	void deleteAttDate(Map<Object, Object> m);

	void updateAttStime(Map<Object, Object> m);


}
