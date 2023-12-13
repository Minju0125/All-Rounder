package kr.or.ddit.login.service;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.groupware.EmployeeVO;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;

public interface FindPasswordProcessService {

	/**
	 * 사용자 인증
	 * 
	 * @param inputData
	 * @return NOTEXIST, INVALIDPASSWORD, OK
	 */
	public ServiceResult authenticate(EmployeeVO inputData);

	/**
	 * 비밀번호 찾기 시 사용되는 인증
	 * 
	 * @param inputData
	 * @return NOTEXIST, OK
	 */
	public ServiceResult authForFindPassword(EmployeeVO inputData);

	/**
	 * 생성된 인증번호로 사원 테이블에 update
	 * @param employeeVO
	 * @return
	 */
	public int updateCertiNum(EmployeeVO employeeVO);

	/**
	 * 문자 발송 메소드
	 * @param 사원휴대폰번호 , 인증번호
	 * @return
	 */
	SingleMessageSentResponse sendMessage(String inputEmpTelno, String crtfNo);

	/**
	 * 사원의 비밀번호를 사원의 생년월일로, 로그인 상태를 N 로 업데이트
	 * @param empCd
	 * @return
	 */
	public int modifyEmpPwAndStatus(String empCd);

	/**
	 * 특정 사원의 비밀번호 변경 (암호화 필요)
	 * @param employee
	 * @return
	 */
	public int modifyEmpPassword(EmployeeVO employee);

}
