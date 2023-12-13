package kr.or.ddit.vo.groupware;

import java.util.List;

import lombok.Data;

@Data
public class AttendanceVO {

	private String attDate;
	private String empCd;
	private String startTime;
	private String endTime;
	private String sTime;
	private String startOfWeek;
	private String endOfWeek;
	
	public void setSTime(String startTime) {
		String hour= startTime.substring(0,2);
		String minute=startTime.substring(2,4);
		String sTime=hour + ":"+minute;
		this.sTime = sTime;
	}
	
	private String empName;
	private String deptName;
	
	private int week;
	
	public List<AttendanceLogVO> alList;
}
