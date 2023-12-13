package kr.or.ddit.company.org.controller;

import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.Data;

/**
 * @author 작성자명
 * @since 2023. 11. 8.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 8.      오경석       최초작성       org.jsp로 변환을 해서 보내려고 했지만 해당 jsp에서 id와 pid가 내가 컨트롤 할 수 없어 jsp에서 작업함.	
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
public class Org {
	private EmployeeVO adaptee;
	
	private String id;
	private String pid;
	private String 이름;
	private String 부서명;
	private String 사번;
	private String img;
	
	public Org(EmployeeVO adaptee) {
		super();
		this.adaptee = adaptee;
		this.id = adaptee.getDeptCd();
		this.pid = adaptee.getEmpSuprr();
		this.이름 = adaptee.getEmpName();
		this.부서명 = adaptee.getDept().getDeptName();
		this.사번 = adaptee.getEmpCd();
		this.img = adaptee.getEmpProfileImg();
	}
	
}
