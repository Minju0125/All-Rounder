package kr.or.ddit.admin.attendance.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.groupware.AttendanceVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.VacationVO;

public interface AdminAttendanceService {

	List<AttendanceVO> attendanceList(String date);

	EmployeeVO selectEmp(String empCd);

	List<AttendanceVO> adminAttendanceMonth(Map<Object, Object> dataMap);

	List<VacationVO> vacationList(Map<Object, Object> dataMap);

	List<DeptVO> selectDeptList();

	List<Map<String, String>> selectRank();

	List<EmployeeVO> selectDeptEmp(Map<Object, Object> data);

	void timeUpdate(Map<Object, Object> data);

}
