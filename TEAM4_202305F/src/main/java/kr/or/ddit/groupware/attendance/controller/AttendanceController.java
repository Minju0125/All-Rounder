package kr.or.ddit.groupware.attendance.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.groupware.attendance.service.AttendanceService;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.AnnualVO;
import kr.or.ddit.vo.groupware.AttendanceLogVO;
import kr.or.ddit.vo.groupware.AttendanceVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

	@Inject
	private AttendanceService service;
	
	@GetMapping
	public String attendanceUI() {
		return "attendance/attendanceList";
	}
	
	@GetMapping("logList")
	@ResponseBody
	public List<AttendanceLogVO> attendanceLogList(Authentication principal) {
		AttendanceLogVO alVO=new AttendanceLogVO();
		alVO.setEmpCd(principal.getName());
		List<AttendanceLogVO> alList= service.attendanceLogList(alVO);
		return alList;
	}
	
	@GetMapping("work")
	@ResponseBody
	public AttendanceVO selectAttendance(Authentication principal) {
		AttendanceVO aVO= service.selectAttendance(principal.getName());
		return aVO;
	}
	
	@PostMapping
	@ResponseBody
	public AttendanceLogVO attendanceInsert(
			@RequestBody AttendanceLogVO alVO
			, Authentication auth) {
		alVO.setEmpCd(auth.getName());
		service.attendanceInsert(alVO);
		alVO.setLTime(alVO.getAttLogTime());
		return alVO;
	}
	
	//{empCd: 'E220401001'}
	@PostMapping("commute")
	@ResponseBody
	public String commute(@RequestBody AttendanceVO aVO) { 
		//AttendanceVO(attDate=null, empCd=E220401001, ..
		log.info("aVO : " + aVO);
		service.commute(aVO);
		
		return aVO.getEmpCd();
	}
	
	@GetMapping("week")
	@ResponseBody
	public double attendanceWeek(Authentication auth) {
		AttendanceVO aVO=new AttendanceVO();
		aVO.setEmpCd(auth.getName());
		double week=service.attendanceWeek(aVO);
		return week;
	}
	
	@GetMapping("weekly")
	@ResponseBody
	public List<AttendanceVO> attendanceWeekly(Authentication auth
			, @RequestParam(value="week",required = false,defaultValue="0") int week) {
		AttendanceVO aVO=new AttendanceVO();
		aVO.setEmpCd(auth.getName());
		aVO.setWeek(week);
		List<AttendanceVO> aList =service.attendanceWeekly(aVO);
		return aList;
	}

	@GetMapping("annual")
	@ResponseBody
	public List<Object> annualStatistics(Authentication auth) {
		String empCd=auth.getName();
		List<Object> aList =service.annualStatistics(empCd);
		return aList;
	}

	@GetMapping("annualList")
	@ResponseBody
	public List<AnnualVO> annualList() {
		List<AnnualVO> aList =service.annualList();
		return aList;
	}
	
	@GetMapping("annualLeaveUsageByRank")
	@ResponseBody
	public List<Map<String, String>> annualLeaveUsageRateByRank() {
		List<Map<String, String>> a=service.annualLeaveUsageRateByRank();
		log.info("여기야!!! : {}",a);
		return a;
	}
}


















