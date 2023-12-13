package kr.or.ddit.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import kr.or.ddit.login.dao.LoginDAO;
import kr.or.ddit.vo.groupware.EmployeeVO;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	@Inject
	private LoginDAO dao;
	
	public CustomAuthenticationSuccessHandler() {
		super();
		setAlwaysUseDefaultTargetUrl(true);
		
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		boolean isAdmin = authentication.getAuthorities()
									.stream()
									.anyMatch(auth->"ROLE_ADMIN".equals(auth.getAuthority()));
		
		//authentication의 login_status 확인메소드 호출
	    String empLoginFlag = checkUserLoginFlag(authentication.getName());
	    
	    String target = null;
		
		// 추후 수정
		if(isAdmin) {
			target = "/analysis/home";
		}else {
			if("N".equals(empLoginFlag)) { //로그인 직후 비밀번호 변경이 필요한 직원인 경우
				//세션을 만료하고 아래 페이지로 이동시켜야함
				new SecurityContextLogoutHandler().logout(request, response, authentication);
				target = "/findPw/modifyPasswordForm/" + authentication.getName();
				response.sendRedirect(target);
			}
			target = "/dashBoard/"+authentication.getName();
		}
		
		
		setDefaultTargetUrl(target);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
	// authentication 의 상태 컬럼을 확인하는 메서드
	private String checkUserLoginFlag(String empCd) {
		EmployeeVO employee = new EmployeeVO();
		employee.setEmpCd(empCd);
		EmployeeVO employeeData = dao.selectEmpForAuth(employee);
		return employeeData.getEmpLoginFlag();
	}
	
}
