package kr.or.ddit.security.userdetailes;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import kr.or.ddit.vo.groupware.EmployeeVO;

public class EmployeeVOWrapper extends User{

	private EmployeeVO realUser;
	
	
	public EmployeeVOWrapper(EmployeeVO realUser) {
		super(realUser.getEmpCd(), realUser.getEmpPw(), AuthorityUtils.createAuthorityList(realUser.getRoleSetting()));
		this.realUser = realUser;
	}


	public EmployeeVO getRealUser() {
		return realUser;
	}

	
}
