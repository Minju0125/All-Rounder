package kr.or.ddit.messenger.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.vo.ChattingVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일             수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 20.     박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
public class ChatHandler extends TextWebSocketHandler{
	
	// 로그인한 전체 session list
	private static List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	// 1:1로 할 경우
	private Map<String, WebSocketSession> userSessionsMap = new HashMap<String, WebSocketSession>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("■■■■■■■■ ChatHandler :  누군가 접속");
		sessions.add(session);
		log.info("## 현재 접속 세션 currentUserName(session) ##" + currentUserName(session));//현재 접속한 사람
		String senderId = currentUserName(session);
		userSessionsMap.put(senderId,session);
	}

	// 클라이언트 소켓과 통신(메세지를 주고받는)하는 핵심 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info("■■■■■■■■ ChatHandler session :"+currentUserName(session));
		
		String uMsg = message.getPayload();
		ObjectMapper obj = new ObjectMapper();
		ChattingVO chattingVO = obj.readValue(uMsg, ChattingVO.class);
		
		System.out.println("메세지 눈으로 확인하기!!!!!!!"+message.getPayload());
		
		String serializeChattingVO =  obj.writeValueAsString(chattingVO);
		TextMessage txtMsg =  new TextMessage(serializeChattingVO);
		
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(txtMsg);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("## ChatHandler ## 누군가 떠남");
		sessions.remove(session);
		userSessionsMap.remove(currentUserName(session),session);
	}
	
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
