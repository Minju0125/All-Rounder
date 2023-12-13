package kr.or.ddit.vo.pms;

import javax.validation.constraints.NotBlank;

import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 작성자명
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>                
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data 
@EqualsAndHashCode(of="empCd")
public class PmemberVO {

	@NotBlank
	private String empCd;		//사번
	@NotBlank
	private String proSn;		//프로젝트일련번호	
	@NotBlank
	private String proLeader;	//리더여부	
	
	private EmployeeVO emp;
	
	private String pmemCount;


	
	
	
}
