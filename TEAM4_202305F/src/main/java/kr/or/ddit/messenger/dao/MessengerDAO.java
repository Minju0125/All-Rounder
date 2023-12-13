package kr.or.ddit.messenger.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ChatParticipantVO;
import kr.or.ddit.vo.ChatRoomVO;

/**
 * @author 작성자명
 * @since 2023. 11. 6.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 6.      박민주       최초작성
 * 2023. 11. 9.		 박민주		신규 채팅방 개설
 * 2023. 11. 18.     박민주       채팅방 목록 출력 메소드 추가
 * 2023. 11. 18.     박민주       채팅방 제목 수정 메소드 추가
 * 2023. 11. 18.     박민주       채팅방 삭제 메소드 추가
 * 2023. 11. 18.     박민주       신규 채팅방 목록 출력 메소드 추가
 * 2023. 11. 22.     박민주		채팅방 제목 조회 메소드 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface MessengerDAO {
	
	/**
	 * 채팅방 목록 출력
	 * @param paging
	 */
	List<ChatParticipantVO> selectChatList(String empCd);

	/**
	 * 신규 채팅방 개설 쿼리를 위한 시퀀스 조회
	 * @return
	 */
	String selectNewChatSeq();
	
	/**
	 * 프로젝트 생성시 부여될 채팅방 번호 조회
	 * @return
	 */
	String selectProjectNewChatSeq();
	
	/**
	 * 신규 채팅방 개설 시, chatParticipant 테이블에 insert
	 * @param chatParticipantVO
	 * @return
	 */
	int insertChatParticipant(ChatParticipantVO chatParticipantVO);
	/**
	 * 채팅방 삭제
	 * 로그인된 계정의 사원에게서만 삭제되어야 한다.
	 * @param chatRoomVO
	 * @return
	 */
	int deleteChatRoom(ChatParticipantVO chatParticipantVO);
	
	/**
	 * 채팅방 이름 수정
	 * @param chatRoomVO
	 * @return
	 */
	int updateChatRoom(ChatParticipantVO chatParticipantVO);

	/**
	 * 채팅방 코드별로 참여자명단(List<ChatParticipantVO>) 반환
	 * @param chatRoomCd
	 * @return 본인의 채팅방 코드별 참여자 명단
	 */
	List<ChatParticipantVO> selectChatRoomParties(String chatRoomCd);

	/**
	 * 2023.11.22 20:30 추가
	 * 채팅방 코드를 통해 ChatParticipantVO 반환
	 * @param chatRoomCd
	 * @return
	 */
	ChatParticipantVO retrieveChatParticipantOne(ChatParticipantVO chatParticipantVO);
}
