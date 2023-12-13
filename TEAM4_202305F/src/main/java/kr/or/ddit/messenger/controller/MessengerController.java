package kr.or.ddit.messenger.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.messenger.service.MessengerService;
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
 * 수정일       		수정자        수정내용
 * --------     --------    ----------------------
 * 2023. 11. 6.      박민주       최초작성
 * 2023. 11. 18.     박민주	    채팅방 CRUD 메소드 추가
 * 2023. 11. 19.     박민주		웹소켓을 통한 채팅 기능 메소드 추가
 * 2023. 11. 22.     박민주		채팅방 제목 부여를 위한 모델 추가 (getChatList 참고)
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Controller
@RequestMapping("/messenger")
@Slf4j
public class MessengerController {

	@Inject
	private MessengerService service;

	@GetMapping
	@ResponseBody
	public Map<String, Object> getChatList() {
		Map<String, Object> resutlMap = new HashMap<>();
		
		//넘길때 참여자 list 도 같이 넘김
		List<ChatParticipantVO> chatList = service.retrieveChatRoomList();
		
		Map<String, List<ChatParticipantVO>> chatParticipantInfoMap = new HashMap<>();
		for(ChatParticipantVO chatRoom : chatList) {
			String chatRoomCd = chatRoom.getChatRoomCd();
			List<ChatParticipantVO> empInfoList = service.retrieveChatParticipant(chatRoomCd);
			chatParticipantInfoMap.put(chatRoomCd, empInfoList);
		}
		resutlMap.put("chatList", chatList); //채팅방 list
		resutlMap.put("empList", chatParticipantInfoMap); //채팅 참여자 list
		
		return resutlMap;
	}
	
	/**
	 * 특정 채팅방에 참여하는 모든 참여자 명단
	 * @param chatRoomCd
	 * @return
	 */
	@GetMapping("/all/{chatRoomCd}")
	@ResponseBody
	public List<ChatParticipantVO> partiesList(@PathVariable("chatRoomCd") String chatRoomCd){
		return service.retrieveChatParticipant(chatRoomCd);
	}
	
	/**
	 * 2023.11.22 20:20 추가
	 * 채팅방 코드를 입력받아 단순한 ChatParticipantVO 를 반환하는 메소드 (제목을 위한)
	 * @param chatRoomCd
	 * @return ChatParticipantVO
	 */
	@GetMapping("{chatRoomCd}")
	@ResponseBody
	public ChatParticipantVO getParticipantForTitle(@PathVariable("chatRoomCd") String chatRoomCd) {
		ChatParticipantVO chatParticipantVO = new ChatParticipantVO();
		chatParticipantVO.setChatRoomCd(chatRoomCd);
		return service.retrieveChatParticipantOne(chatParticipantVO);
	}
	

	@PostMapping("{chatType}")
	@ResponseBody
	public Map<String, String> doPost(
			@RequestBody List<ChatParticipantVO> chatParticipantList,
			Authentication authentication, 
			@PathVariable("chatType") String chatType
			) {
		String userEmpCd = authentication.getName(); //로그인된 사번
		ChatParticipantVO userChatParticipantVO = new ChatParticipantVO(userEmpCd);
		chatParticipantList.add(userChatParticipantVO);
		log.info("controller에서 chatParticipantList 시큐리티 정보 주입 후 ====> " + chatParticipantList);
		service.createChatRoom(chatParticipantList, chatType);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("success", "성공");
		return resultMap;
	}

	@DeleteMapping("{chatRoomCd}")
	@ResponseBody
	public Map<String, String> doDelete(@PathVariable("chatRoomCd") String chatRoomcd, Authentication authentication) {
		ChatParticipantVO chatParticipantVO = new ChatParticipantVO(chatRoomcd);
		chatParticipantVO.setChatRoomCd(chatRoomcd);
		chatParticipantVO.setChatEmpCd(authentication.getName());
		int result = service.removeChatRoom(chatParticipantVO);
		Map<String, String> resultMap = new HashMap<>();
		if (result > 0) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}

	@PutMapping("{chatRoomCd}")
	@ResponseBody
	public Map<String, String> doPUT(@PathVariable("chatRoomCd") String chatRoomcd, @RequestBody String chatRoomNm,
			Authentication authentication) {
		ChatParticipantVO chatParticipantVO = new ChatParticipantVO(chatRoomcd);
		chatParticipantVO.setChatRoomCd(chatRoomcd);
		chatParticipantVO.setChatRoomNm(chatRoomNm);
		chatParticipantVO.setChatEmpCd(authentication.getName());

		int result = service.modifyChatRoom(chatParticipantVO);
		log.info("chatParticipantVO ==> " + chatParticipantVO);
		Map<String, String> resultMap = new HashMap<>();
		if (result > 0) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		return resultMap;
	}
}
