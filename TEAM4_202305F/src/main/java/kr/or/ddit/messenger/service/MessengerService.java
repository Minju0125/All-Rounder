package kr.or.ddit.messenger.service;

import java.util.List;

import kr.or.ddit.vo.ChatParticipantVO;
import kr.or.ddit.vo.ChatRoomVO;
import kr.or.ddit.vo.PaginationInfo;

/**
 * @author 작성자명
 * @since 2023. 11. 6.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일         수정자           수정내용
 * --------     --------    ----------------------
 * 2023. 11. 6.      박민주       최초작성
 * 2023. 11. 18.     박민주		채팅방 관련 CRUD 메소드 추가
 * 2023. 11. 22.     박민주		채팅방 이름 부여를 위해 retrieveChatParticipant 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface MessengerService {
	
	/**
	 * 채팅방 목록 조회
	 * @param paging
	 */
	List<ChatParticipantVO> retrieveChatRoomList();
	
	/**
	 * 2023.11.22 20:30 추가
	 * 채팅방 코드를 입력받아 ChatParticipantVO 반환
	 * @param chatRoomCd
	 * @return ChatParticipantVO
	 */
	ChatParticipantVO retrieveChatParticipantOne(ChatParticipantVO chatParticipantVO);
	
	/**
	 * 
	 * @param chatRoomCd
	 * @return 채팅방별 참여자 list
	 */
	List<ChatParticipantVO> retrieveChatParticipant(String chatRoomCd);
	
	/**
	 * 새로운 채팅방 개설
	 * @param chatType 
	 * @param chatRoomVO
	 * @return 성공1, 실패0 반환
	 */
	void createChatRoom(List<ChatParticipantVO> chatParticipantList, String chatType);
	
	/**
	 * 채팅방 수정
	 * 로그인된 사원 본인에게서만 제목 수정
	 * @param chatRoomVO
	 * @return
	 */
	int modifyChatRoom(ChatParticipantVO chatParticipantVO);
	
	/**
	 * 채팅방 삭제
	 * 로그인된 사원 본인에게서만 삭제
	 * @param chatRoomVO
	 * @return
	 */
	int removeChatRoom(ChatParticipantVO chatParticipantVO);

}
