package kr.or.ddit.messenger.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.login.dao.LoginDAO;
import kr.or.ddit.messenger.dao.MessengerDAO;
import kr.or.ddit.vo.ChatParticipantVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 6.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일      	 수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 6.   박민주     최초작성
 * 2023. 11. 18.  박민주	   채팅방관련 CRUD 메소드 추가
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Service
@Slf4j
public class MessengerServiceImpl implements MessengerService {

	@Inject
	private MessengerDAO dao;
	
	@Inject
	private LoginDAO loginDao;

	@Override
	public List<ChatParticipantVO> retrieveChatRoomList(){
		String senderCd = SecurityContextHolder.getContext().getAuthentication().getName(); // 사원번호
		return dao.selectChatList(senderCd);
	}

	@Override
	public ChatParticipantVO retrieveChatParticipantOne(ChatParticipantVO chatParticipantVO) {
		String senderCd = SecurityContextHolder.getContext().getAuthentication().getName(); // 사원번호
		chatParticipantVO.setChatEmpCd(senderCd);
		return dao.retrieveChatParticipantOne(chatParticipantVO);
	}

	//채팅방 추가
	@Override
	@Transactional
	public void createChatRoom(List<ChatParticipantVO> chatParticipantList, String chatType) {
		String chatRoomCd = "";
		if("G".equals(chatType)) { //일반 채팅방
			chatRoomCd = dao.selectNewChatSeq();
		}else { //프로젝트 채팅방
			chatRoomCd = dao.selectProjectNewChatSeq();
		}
		for(ChatParticipantVO chatParticipant : chatParticipantList) {
			chatParticipant.setChatRoomCd(chatRoomCd);
			dao.insertChatParticipant(chatParticipant);
		}
	}

	@Override
	public int modifyChatRoom(ChatParticipantVO chatParticipantVO) {
		return dao.updateChatRoom(chatParticipantVO);
	}

	@Override
	public int removeChatRoom(ChatParticipantVO chatParticipantVO) {
		return dao.deleteChatRoom(chatParticipantVO);
	}

	/* 채팅방 이름 부여를 위해 추가(11.22) */
	@Override
	public List<ChatParticipantVO> retrieveChatParticipant(String chatRoomCd) {
		return dao.selectChatRoomParties(chatRoomCd);
	}


}