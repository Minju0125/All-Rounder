package kr.or.ddit.vo.groupware;

import lombok.Data;
import lombok.ToString;
import lombok.Value;

@Data
@ToString
public class AttendanceLogVO {

	private String attLog;
	private String attLogTime;
	private String attDate;
	private String empCd;
	private String lTime;
}
