package kr.or.ddit.vo.groupware;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author 작성자명
 * @since 2023. 11. 2.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일              수정자           수정내용
 * --------     --------    ----------------------
 * 2023. 11. 2.      작성자명       최초작성
 * 2023. 12. 2.		 박민주		 중요메일 목록 조회를 위한 필드 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "mailCd")
public class MailVO implements Serializable{
	
	@NotBlank
	private String mailCd; //메일코드 ex) M2023110600001
	@NotBlank
	private String mailSj; // 메일제목
	@NotBlank
	private String mailCn; // 메일내용
	@NotBlank
	private String mailTrnsmisDt; // 메일발송일시 2023/01/07(연/월/일) 09:00 (일시)
	
	private String mailSender; //발신자 사번
	private String mailSaveYn; //임시저장여부 Y(임시저장중), N(임시저장아님)
	private String mailDeleteFlag; //삭제여부 0(일반), 1(삭제), 2(영구삭제)
	private String mrImpoYn; //중요여부 Y(중요), N(안중요)
	
	//아래 두개?
	private String[] mailReceivers;
	private List<MailReceptionVO> mailReceiverList;  //1:N 관

	private MultipartFile[] files;
	
	private List<MailAttachVO> attachments; // 1:N
	
	private EmployeeVO senderVO; //1:1 관계
	
	//중요메일 목록 조회를 가져오기 위한 필드 정의
	private String mailReceiver; /* 수신자 사번 */
	private String rImpo;		 /* 수신자지정 중요여부 */
	private String rDelete;		 /* 수신자지정 삭제 여부 */
	private String mrReadYn; 	 /* 수신자지정 읽음 여부 */
	
	private String rDeptName;	 /* 수신자 부서명 */
    private String rEmpName; 	 /* 수신자 이름 */
    private String rEmpRank; 	 /* 수신자 직급명 */
    private String rEmpProfileImg; 	 /* 수신자 프로필 이미지 */
    
    private String sDeptName;	 /* 발신자 부서명 */ 
    private String sEmpName;	 /* 발신자 이름 */
    private String sEmpRank;	 /* 발신자 직급명 */ 
    private String sEmpProfileImg;	 /* 발신자 프로필 이미지 */ 
    private String sImpo;		 /* 발신자지정 중요여부 */
    
    //로그인된 사원의 사번을 담고 다니기 위한 필드 추가 (2023.12.03)
    private String loginUser; /* 로그인 사번*/
}
