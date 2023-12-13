package kr.or.ddit.alarm.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.AlarmVO;

/**
 * @author 전수진
 * @since 2023. 12. 1.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 1.   전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface AlarmService {
	
	/**
	 * 결재상신 알림등록
	 * @param alarm
	 * @return
	 */
	public ServiceResult createSanctionAlarm(AlarmVO alarm);
	
	/**
	 * 로그인한 직원의 알림list 출력
	 * @param receiver
	 * @return
	 */
	public List<AlarmVO> retrieveAlarmList(String receiver);
	
	/**
	 * 알림 읽음 표시여부 수정
	 * @param receiver
	 * @return
	 */
	public int modifyReadAlarm(String receiver);
	
	/**
	 * 알림지우기
	 * @param receiver
	 * @return
	 */
	public int removeReadAlarms(String receiver);
	

}
