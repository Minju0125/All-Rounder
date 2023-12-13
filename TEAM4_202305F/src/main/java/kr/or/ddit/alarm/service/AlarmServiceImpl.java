package kr.or.ddit.alarm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.alarm.dao.AlarmDAO;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.AlarmVO;
import kr.or.ddit.vo.groupware.SanctionLineVO;

/**
 * @author 전수진
 * @since 2023. 12. 2.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 2.   전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Service
public class AlarmServiceImpl implements AlarmService {

	@Inject
	private AlarmDAO dao;
	
	@Override
	public ServiceResult createSanctionAlarm(AlarmVO alarmVO) {
		AlarmVO alarm = new AlarmVO();
		
//	    , ALARM_SENDER 		/* 발신자 */
//	    , ALARM_RECEIVER  	/* 수신자 */
//	    , ALARM_SOURCE 		/* 발신지 */
//	    , ALARM_CONTENT 	/* 내용 */
//	    , ALARM_DATE 		/* 알림날짜 */
//	    , ALARM_READ 		/* 읽음여부 Y(읽음),N(안읽음) */
//	    , ALARM_TYPE 		/* 알림타입 */
//	    , ALARM_URL			/* 이동URL */
		
		
		ServiceResult result = null;
		return result;
	}

	@Override
	public List<AlarmVO> retrieveAlarmList(String receiver) {
		
		List<AlarmVO> list = dao.selectAlarmList(receiver); 
		
		return list;
	}

	@Override
	public int modifyReadAlarm(String receiver) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeReadAlarms(String receiver) {
		// TODO Auto-generated method stub
		return 0;
	}

}
