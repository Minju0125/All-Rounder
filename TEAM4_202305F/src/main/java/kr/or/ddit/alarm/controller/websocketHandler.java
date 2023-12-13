package kr.or.ddit.alarm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.vo.AlarmVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 13.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 13.  전수진       최초작성
 * 2023. 11. 14.  전수진       로그인한 유저 session 담기도록 수정
 * 2023. 12. 02.  전수진      handleTextMessage 수정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
public class websocketHandler extends TextWebSocketHandler{
	
	// 로그인한 전체 session list
	private static List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1로 할 경우
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("◀◀◀◀ 알림 누군가 접속 ▶▶▶▶");
		sessions.add(session);
		log.info("◀◀◀◀ 알림 접속한 세션 ▶▶▶▶{}",currentUserName(session));//현재 접속한 사람
		String senderId = currentUserName(session);
		userSessionsMap.put(senderId,session);
	}

	// 클라이언트 소켓과 통신(메세지를 주고받는)하는 핵심 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("◀◀◀◀ websocketHandler 세션 ▶▶▶▶{}",currentUserName(session));
		
		String uMsg = message.getPayload();
		ObjectMapper obj = new ObjectMapper();
		AlarmVO alarmVO = obj.readValue(uMsg, AlarmVO.class);
		
		//String uMsg = message.getPayload();
		log.info("◀◀◀◀ 알림 메세지 눈으로 확인하기!!!!!!! ▶▶▶▶{}",message.getPayload());
		
		String serializeAlarmVO=  obj.writeValueAsString(alarmVO);
		TextMessage txtMsg =  new TextMessage(serializeAlarmVO);
		log.info("◀◀◀◀ ObjectMapper 사용 후 txtMsg ▶▶▶▶{}",txtMsg);
		
		for (WebSocketSession webSocketSession : sessions) {
			// 본인제외하고 전체에게 메세지 보내기
			if(session != webSocketSession) {
				webSocketSession.sendMessage(message);
			}
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("◀◀◀◀ 알림 누군가 떠남 ▶▶▶▶");
		sessions.remove(session);
		userSessionsMap.remove(currentUserName(session),session);
	}
	
	// 현재 로그인한 ID를 꺼내오는 로직
	private String currentUserName(WebSocketSession session) {
		Map<String, Object> httpSession = session.getAttributes();
		EmployeeVO loginUser = (EmployeeVO) httpSession.get("login");
		
		if(loginUser == null ) {
			String eid = session.getId();
			return eid;
		}
		String eid = loginUser.getEmpCd();
		return eid;
	}

}
