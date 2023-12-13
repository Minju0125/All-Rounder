package kr.or.ddit.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author 작성자명
 * @since 2023. 11. 18.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자     	수정내용
 * --------     --------    ----------------------
 * 2023. 11. 10      박민주       최초작성
 * 2023. 11. 18		 박민주		CHAT_ROOM 테이블 수정에 따른 chatRoomYn(채팅방유형번호) 필드 삭제
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of="chatRoomCd")
public class ChatRoomVO {
	@NotBlank
	private String chatRoomCd; //채팅방번호 ex) 23 11 03 0001
	
//	@NotBlank
//	private String chatRoomYn; //채팅방유형번호 ex) 0(일대일), 1(그룹) => 나중에 여기에 넣기? => 비즈니스 로직에서 넣을 수 있을듯!
	
	private List<ChatParticipantVO> partiesList; // has many(1:N 관계)
}
