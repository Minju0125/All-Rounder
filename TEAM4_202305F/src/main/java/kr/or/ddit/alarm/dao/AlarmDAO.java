package kr.or.ddit.alarm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface AlarmDAO {
	
	/**
	 * 알림등록
	 * @param alarm
	 * @return
	 */
	public int insertAlarm(AlarmVO alarm);
	
	/**
	 * 로그인한 직원의 알림 list 출력
	 * @param receiver
	 * @return
	 */
	public List<AlarmVO> selectAlarmList(String receiver);
	
	
	/**
	 * 읽지않은 알림의 갯수
	 * @param receiver
	 * @return
	 */
	public int countAlarmList(String receiver);
	
	
	/**
	 * 알림 읽음 표시여부 수정
	 * @param receiver
	 * @return
	 */
	public int updateAlarm(String receiver);
	
	/**
	 * 알림 1건 지우기
	 * @param receiver
	 * @return
	 */
	public int deleteAlarm(int alarmNo);
	
	
	/**
	 * 모든 알림 지우기
	 * @param receiver
	 * @return
	 */
	public int deleteAllAlarm(String receiver);
	

}
