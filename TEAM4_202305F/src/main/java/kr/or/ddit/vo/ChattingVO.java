package kr.or.ddit.vo;

import java.util.List;

import lombok.Data;

@Data
public class ChattingVO {
	
	private String chatRoomCd; /* 채팅방 번호 */
	
	private String senderEmpCd; /* 발신자 사번 */
	private String senderEmpName; /* 발신자 이름 */

	private String senderEmpPosition; //ex. 팀장
	private String senderEmpMail;
	private String senderEmpProfileImg;
	private String senderEmpTelno;
	private String senderEmpExtension; /* 내선 번호 */
	private String senderEmpHiredate;

	private String senderDeptName; /* ex. 생산부 */
	private String senderCommonCodeSj; /* ex. 부장 */
	
	private String sendDate;
	private String sendTime;
	private String sendContent;
	
	private List<ChatParticipantVO> parties; /* 해당 채팅방에 참여하는 사람들*/
	
}
