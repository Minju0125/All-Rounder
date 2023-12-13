package kr.or.ddit.vo;

import javax.validation.constraints.NotBlank;

import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="chatRoomCd")
public class ChatParticipantVO {
	
	public ChatParticipantVO() {};
	
	private String chatEmpCd; //참여자사번
	private String chatRoomCd; //채팅방코드
	private String chatRoomNm; //채팅방이름

	private String empName;
	private String deptCd;
	private String deptName;
	private String empRank;
	private String empMail;
	private String empEmailSecond;
	private String empPosition;
	private String commonCodeSj;
	private String empProfileImg;
	
	private EmployeeVO employee; //1:1관계
	
	public ChatParticipantVO(String chatEmpCd) {
		super();
		this.chatEmpCd = chatEmpCd;
		this.employee = new EmployeeVO(); // EmployeeVO 객체 초기화
		employee.setEmpCd(chatEmpCd);
	}

	
}
