package kr.or.ddit.admin.attendance.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.attendance.service.AdminAttendanceService;
import kr.or.ddit.vo.groupware.AttendanceVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.VacationVO;

@Controller
@RequestMapping("/adminAttendance")
public class AdminAttendanceController {

	@Inject
	private AdminAttendanceService service;
	
	@GetMapping
	public String adminAttendanceUI() {
		return "adminAttendance/adminAttendanceList";
	}
	
	@GetMapping("month")
	public String monthAttendanceUI(Model model,
			@RequestParam("empCd") String empCd
			, @RequestParam("date") String date) {
		EmployeeVO eVO= service.selectEmp(empCd);
		model.addAttribute("emp",eVO);
		
		List<DeptVO> dList= service.selectDeptList();
		model.addAttribute("dept",dList);
		
		List<Map<String, String>> rankList=service.selectRank();
		model.addAttribute("rankList",rankList);

		String year=date.substring(0,4);
		String month=date.substring(4);
		model.addAttribute("year",year);
		model.addAttribute("month",month);
		
		return "adminAttendance/monthAttendanceList";
	}
	
	@GetMapping("list")
	@ResponseBody
	public List<AttendanceVO> adminAttendanceList(@RequestParam("date") String date){
		System.out.println("asdasdasdas"+date);
		List<AttendanceVO> aList= service.attendanceList(date);
		return aList;
	}
	
	@PostMapping("monthList")
	@ResponseBody
	public List<AttendanceVO> adminAttendanceMonth(@RequestBody Map<Object,Object> dataMap){
		List<AttendanceVO> aList= service.adminAttendanceMonth(dataMap);
		return aList;
	}
	
	@PostMapping("vacationList")
	@ResponseBody
	public List<VacationVO> vacationList(@RequestBody Map<Object,Object> dataMap){
		List<VacationVO> vList= service.vacationList(dataMap);
		return vList;
	}
	
	@PostMapping("deptEmp")
	@ResponseBody
	public List<EmployeeVO> selectDeptEmp(@RequestBody Map<Object,Object> data) {
		List<EmployeeVO> eList=service.selectDeptEmp(data);
		return eList;
	}
	
	@PostMapping("timeUpdate")
	@ResponseBody
	public void timeUpdate(@RequestBody Map<Object,Object> data) {
		service.timeUpdate(data);
	}
}
