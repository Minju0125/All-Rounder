package kr.or.ddit.vo.groupware;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 박민주
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.   박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of={"confRoomCd"})
public class ConfRoomVO implements Serializable{
	private String confRoomCd; /*회의실 코드*/
	private String confRoomCapacity; /* 회의실 수용인원 */
	private String confYn; /* 회의실 사용가능 상태 여부 */
	private String confRoomNm; /* 회의실 이름 */
}