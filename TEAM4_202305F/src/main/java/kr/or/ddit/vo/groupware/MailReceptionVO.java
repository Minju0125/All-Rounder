package kr.or.ddit.vo.groupware;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MailReceptionVO {
	
	//이걸 안쓰면, 데이터 맵퍼 관련 오류가 뜨는데 어디서 어떤 방식으로 만들어줘서 오류가 생기는지 모르겠다..
	public MailReceptionVO(String empCd){
		this.mrReceiver = empCd;
	}
	
	@NotBlank
	private String mrReceiver;
	@NotBlank
	private String mailCd;
	
	private String mrImpoYn;
	private String mailDeleteFlag;
	private String mrReadYn;
	
	private String rDeptName;	 /* 수신자 부서명 */
    private String rEmpName; 	 /* 수신자 이름 */
    private String rEmpRank; 	 /* 수신자 직급명 */
    private String rEmpProfileImg; 	 /* 수신자 프로필 이미지 */
    
    //로그인된 사원의 사번을 담고 다니기 위한 필드 추가 (2023.12.03)
    private String loginUser; /* 로그인 사번*/
}
