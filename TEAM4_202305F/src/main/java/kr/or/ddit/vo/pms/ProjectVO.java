package kr.or.ddit.vo.pms;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.DeptVO;
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
 * 2023. 11. 22.      송석원       생성일 추가  
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of="proSn")
public class ProjectVO {
	 
	@NotBlank
	private String proSn;			//프로젝트일련번호
	@NotBlank	
	private String proNm;			//프로젝트명
	@NotBlank
	private String proBdate;		//시작일
	@NotBlank
	private String proEdate;		//종료일
	@NotBlank
	private String proSttus;		//프로젝트상태	
	
	private Integer proProgrs;	//프로젝트 진행도	
	
	private String proSdate;		//생성일	
	
	
	private String jobuCount;
	
	private String completedCount; 
	
	
	
	
//	1:N 관계형
	private List<PjobVO> pjobList;
	
	
//	1:N 관계형	 
	private List<PmemberVO> pmemberList;
	
	private EmployeeVO emp; // has a 관계
	
	private CommonVO common;	// has a 관계
	
	

}
