package kr.or.ddit.vo.pms;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 작성자명
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 23.      김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Data
@EqualsAndHashCode
public class PLogVO implements Serializable{
	
	@NotBlank
	private String plogNo;
	@NotBlank
	private Integer jobSn;
	@NotBlank
	private String proSn;
	@NotBlank
	private String plogWriter;
	@NotBlank
	private String plogCn;
	private String proFileCd;
	@NotBlank
	private String plogCdate;
	
	

}
