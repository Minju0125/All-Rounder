package kr.or.ddit.vo.groupware;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 전수진
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.    전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = {"empCd","bbsNo"})
public class BoardEmojiVO implements Serializable{
	
	@NotBlank
	private String empCd;
	@NotBlank
	private Integer bbsNo;
	private String emojiCd;

}
