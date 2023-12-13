package kr.or.ddit.groupware.mailing.service;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
/**
 * SMTP 서비스 이용을 위해 필요한 설정 클래스
 * @author 박민주
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 15.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public class SMTPService extends Authenticator {

    @Override
	protected PasswordAuthentication getPasswordAuthentication() {
		/*
		 * protected PasswordAuthentication getPasswordAuthentication(MailVO senderVO) {
		 */
		String userName = "bagminju046@gmail.com";
		String password = "ffuruyqvtzvbwksc";
		return new PasswordAuthentication(userName, password);
	}
	
	protected static Session getMailSession() {
		/* SMTP 및 메일 송신 설정 */
		Properties props = new Properties(); // 이메일 전송에 필요한 설정을 하기 위한 프로퍼티스 객체 생성
		
		props.put("mail.smtp.port", "587"); // google smtp port 번호
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		
		SMTPService senderAuth = new SMTPService();
		Session session = Session.getDefaultInstance(props, senderAuth); // 메일 세션 생성
	
		return session;
	}
}
