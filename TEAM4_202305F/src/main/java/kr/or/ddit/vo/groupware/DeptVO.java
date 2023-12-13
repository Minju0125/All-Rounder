package kr.or.ddit.vo.groupware;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       수정자         수정내용
 * --------     --------    ----------------------
 * 2023. 11. 20. 김보영         1차수정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data

@EqualsAndHashCode(of = "deptCd")
public class DeptVO {
	
	@NotBlank
	private String deptCd;
	@NotBlank
	private String deptName;
	@NotBlank
	private String uDeptCd;
	 
	
	
	
}
