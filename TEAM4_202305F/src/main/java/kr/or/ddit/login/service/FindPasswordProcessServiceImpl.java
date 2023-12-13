package kr.or.ddit.login.service;

import java.lang.reflect.InvocationTargetException;

import javax.inject.Inject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.login.dao.LoginDAO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Service
@Slf4j
public class FindPasswordProcessServiceImpl implements FindPasswordProcessService {

	@Inject
	private PasswordEncoder encoder;
	
	@Inject
	LoginDAO dao;

	final DefaultMessageService messageService;

	public FindPasswordProcessServiceImpl() {
		this.messageService = NurigoApp.INSTANCE.initialize("NCS9ZQYDTT6C7I6N", "F0UYYWY6GTA2LLUTUQZBPSRVVUYQSA9K",
				"https://api.coolsms.co.kr");
	}

	@Override
	public ServiceResult authenticate(EmployeeVO inputData) {
		EmployeeVO saved = dao.selectEmpForAuth(inputData);
		ServiceResult result = null;

		if (saved != null) {

			// 로그인 시 사용되는 로직 : 비밀번호를 입력받은 경우 (inputData에 비밀번호가 있는 경우)
			if (inputData.getEmpPw() != null) {
				String inputPw = inputData.getEmpPw();
				String savedPw = saved.getEmpPw();
				if (inputPw.equals(savedPw)) {
					try {
						BeanUtils.copyProperties(inputData, saved); // 동일한 properties 를 복사함!
						// 비밀번호가 일치 시, 로그인 성공 OK
						result = ServiceResult.OK;
					} catch (IllegalAccessException | InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				} else {
					// 비밀번호 불일치 시, 로그인 실패 INVALIDPASSWORD
					result = ServiceResult.INVALIDPASSWORD;
				}
			}
		} else {
			// 아예 입력한 사번이 존재하지 않으면 NOTEXIST
			result = ServiceResult.NOTEXIST;
		}
		return result;
	}

	@Override
	public ServiceResult authForFindPassword(EmployeeVO inputData) {
		log.info("inputData ==> " + inputData);
		EmployeeVO saved = dao.selectEmpForAuth(inputData);

		System.out.println("save ==> " + saved);
		ServiceResult result = null;
		if (saved != null) { // 입력받은 사번이 존재하는 경우
			String inputName = inputData.getEmpName();
			String savedName = saved.getEmpName();
			String inputTelno = formattingEmpTelno(inputData.getEmpTelno());
			String savedTelno = saved.getEmpTelno();

			if (inputName.equals(savedName) && inputTelno.equals(savedTelno)) {
				// 입력받은 값들이 모두 일치하는 경우 OK
				result = ServiceResult.OK;
			} else {
				// 입력받은 값들이 일치하지 않는 경우
				result = ServiceResult.INVALIDPASSWORD;
			}
		} else { // 입력받은 사번이 존재하지 않는 경우
			result = ServiceResult.NOTEXIST;
		}
		return result;
	}

	@Override
	public int updateCertiNum(EmployeeVO employeeVO) {
		return dao.updateEmpCertfNo(employeeVO);
	}

	@Override
	public SingleMessageSentResponse sendMessage(String inputEmpTelno, String crtfNo) {
		Message message = new Message();
		
		// 번호 형태 : 하이픈 없이, 01012345678
		message.setFrom("01040322635"); // 발신번호
		message.setTo(inputEmpTelno); // 수신번호 //직원의 번호
		//다시 바꾸기

		String sendText = String.format("[AllRounder] 인증번호는 %s 입니다.", crtfNo);
		message.setText(sendText);

		SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
		log.info("■■■■■■■■■■■■ 발송메세지 : {}", sendText);
		log.info("■■■■■■■■■■■■ 응답????? : {}", response);
		log.info("■■■■■■■■■■■■ 응답코드????? : {}", response.getStatusCode());
		return response;
	}
	
	/**
	 * 숫자로만 입력 받은 전화번호를 형식에 맞게 formatting 하는 메소드
	 * (추가: 2023.12.04 14:17 박민주)
	 * @param inputTelno
	 * @return formattedTelno
	 */
	public String formattingEmpTelno(String inputTelno) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(inputTelno.substring(0,3));
		stringBuffer.append("-");
		stringBuffer.append(inputTelno.substring(3,7));
		stringBuffer.append("-");
		stringBuffer.append(inputTelno.substring(7,11));
		String formattedTelno = stringBuffer.toString();
		return formattedTelno;
	}

	@Override
	public int modifyEmpPwAndStatus(String empCd) {
		EmployeeVO employee = new EmployeeVO();
		employee.setEmpCd(empCd);
		EmployeeVO employeeData = dao.selectEmpForAuth(employee);
		String empBirth = employeeData.getEmpBirth();
		String newPassword= encoder.encode(empBirth);
		log.info("newPassword==>" + newPassword);
		employee.setEmpPw(newPassword);
		return dao.updateEmpPwAndStatus(employee);
	}

	@Override
	public int modifyEmpPassword(EmployeeVO employee) {
		String empPw = encoder.encode(employee.getEmpPw()); 
		employee.setEmpPw(empPw);
		return dao.updateEmpPw(employee);
	}

}
