package kr.or.ddit.cal.controller;

import kr.or.ddit.vo.pms.CalendarVO;
import lombok.Data;

/**
 * @author 작성자명
 * @since 2023. 11. 14.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 14.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
public class Cal {
//	private CalendarVO adaptee;
	
	private String calCd;
	private String title;
	private String start;
	private String end;
	private String backgroundColor;
	private String textColor;
	private String type;
	private boolean allDay;
	
	
	public Cal(CalendarVO adaptee) {
		super();
//		this.adaptee = adaptee;
		this.calCd = adaptee.getScheduleCd();
		this.title = adaptee.getScheduleSj();
		this.start = adaptee.getScheduleBgnDt();
		this.end = adaptee.getScheduleEndDt();
		this.backgroundColor = adaptee.getScheduleBcolor();
		this.textColor = adaptee.getScheduleFcolor();
		this.type = adaptee.getScheduleCd().substring(0,1);
		
		String day = adaptee.getScheduleDayYn();		
		if(day.equals("N")) {
			this.allDay = false;
		}else {
			this.allDay = true;
		}
		
	}


	@Override
	public String toString() {
		return "Cal [calCd=" + calCd + ", title=" + title + ", start=" + start + ", end=" + end + ", backgroundColor="
				+ backgroundColor + ", textColor=" + textColor + ", type=" + type + ", allDay=" + allDay + "]";
	}
	
	
	
	
	
}
