package kr.or.ddit.security;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 로그인 실패처리를 하기 위한 security failure handler 로그인 실패와 관련된 예외를 캐치
 * 
 * @author 작성자명
 * @since 2023. 12. 5.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일              수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 5.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Slf4j
@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("onAuthenticationFailure에 왔다");
		String errorMessage;
		if (exception instanceof BadCredentialsException) {
			errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMessage = "계정이 존재하지 않습니다. 회원가입 진행 후 로그인 해주세요.";
		} else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
		} else {
			errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
		}

		String encodedErrorMessage = URLEncoder.encode(errorMessage, "UTF-8"); //errorMessage 를 쿼리스트링 형태로 전달했기 때문에 인코딩 필요
		setDefaultFailureUrl("/login?error=true&exception=" + encodedErrorMessage);
		
//		request.setAttribute("errorMessage", errorMessage);
//		HttpSession session = request.getSession();
//		session.setAttribute("errorMessage", errorMessage);
		
		super.onAuthenticationFailure(request, response, exception);
	}
}
