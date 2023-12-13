package kr.or.ddit.admin.attendance.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.attendance.dao.AdminAttendanceDAO;
import kr.or.ddit.vo.groupware.AttendanceVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.VacationVO;

@Service
public class AdminAttendanceServiceImpl implements AdminAttendanceService {

	@Inject
	private AdminAttendanceDAO dao;
	
	@Override
	public List<AttendanceVO> attendanceList(String date) {
		date+="%";
		return dao.attendanceList(date);
	}

	@Override
	public EmployeeVO selectEmp(String empCd) {
		return dao.selectEmp(empCd);
	}

	@Override
	public List<AttendanceVO> adminAttendanceMonth(Map<Object, Object> dataMap) {
		return dao.adminAttendanceMonth(dataMap);
	}

	@Override
	public List<VacationVO> vacationList(Map<Object, Object> dataMap) {
		return dao.vacationList(dataMap);
	}

	@Override
	public List<DeptVO> selectDeptList() {
		return dao.selectDeptList();
	}

	@Override
	public List<Map<String, String>> selectRank() {
		return dao.selectRank();
	}

	@Override
	public List<EmployeeVO> selectDeptEmp(Map<Object, Object> data) {
		return dao.selectDeptEmp(data);
	}

	@Override
	public void timeUpdate(Map<Object, Object> data) {
		dao.timeUpdate(data);
	}

}
