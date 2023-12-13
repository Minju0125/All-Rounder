package kr.or.ddit.vo;

import java.util.List;

import kr.or.ddit.cal.controller.Cal;
import kr.or.ddit.vo.pms.CalendarVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.Data;

@Data
public class CalCheckVO {
	private String C;	// 입력되어야 할 값
	private String P;
	private String D;
	private String I;
	
	private List<CalendarVO> calList;
	private List<ProjectVO> pList;
	
//	private List<Cal> List;
	
	private String empCd;
}
