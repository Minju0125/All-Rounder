package kr.or.ddit.vo.groupware;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 작성자명
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 28.     김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Data
@EqualsAndHashCode(of = {"empCd","bbsNO"})
public class EmojiVO implements Serializable{
	
	@NotBlank
	private String empCd;
	@NotBlank
	private int bbsNo;
	private String emojiCd;
	

}
