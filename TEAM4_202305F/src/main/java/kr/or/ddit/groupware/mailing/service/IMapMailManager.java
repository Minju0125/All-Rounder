package kr.or.ddit.groupware.mailing.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.InternetAddress;
import kr.or.ddit.vo.groupware.MailVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 11.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 11.      		박민주 			  최초작성
 * 2023. 11. 14.			박민주			  메일메시지를 읽어와서 mailVO 에 할당
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
public class IMapMailManager {

	public List<MailVO> getMailList() {
		MailVO mailVO = new MailVO();
		List<MailVO> mailList = new ArrayList<>();
		
		String host = "imap.gmail.com";
		String username = "bagminju046@gmail.com";
		String password = "ffuruyqvtzvbwksc";

		/* IMAP 속성 설정 */
		Properties properties = new Properties();
		properties.setProperty("mail.store.protocol", "imaps");
		properties.setProperty("mail.imap.host", host);
		properties.setProperty("mail.imap.port", "993");

		try {
			/* 세션 생성 */
			Session session = Session.getDefaultInstance(properties); // 세션 생성

			/* store 생성 및 연결 */
			Store store = session.getStore("imaps"); // 위에 설정한 특정 프로토콜에 대한 Store 객체 받아옴
			store.connect(host, username, password);

			/* Inbox 폴더 열기 */
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			/* 메세지 가져오기 */
			Message[] messages = inbox.getMessages();
			for (Message message : messages) {
				System.out.println("제목: " + message.getSubject());
				System.out.println("발신자: " + InternetAddress.toString(message.getFrom()));
				System.out.println("일시: " + message.getSentDate());
				System.out.println("내용: " + getContent(message));
				System.out.println("---------------------------------------------");
				
				mailVO.setMailSj(message.getSubject()); //제목
				mailVO.setMailSender(InternetAddress.toString(message.getFrom())); //발신자
				mailVO.setMailTrnsmisDt(String.valueOf(message.getSentDate())); //일시
				mailVO.setMailCn(getContent(message)); //내용
				mailList.add(mailVO);
			}

			inbox.close(false);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailList;
	}

	private static String getContent(Part part) throws MessagingException, IOException {
		if (part.isMimeType("text/plain") || part.isMimeType("text/html")) {
			return part.getContent().toString();
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			StringBuilder content = new StringBuilder();
			for (int i = 0; i < multipart.getCount(); i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				content.append(getContent(bodyPart)).append("\n");
			}
			return content.toString();
		} else if (part.getContentType().toLowerCase().contains("application/")
				|| part.getContentType().toLowerCase().contains("image/")
				|| part.getContentType().toLowerCase().contains("audio/")
				|| part.getContentType().toLowerCase().contains("video/")) {
			/* 첨부파일 */
			return "Attachment: " + part.getFileName();
		}
		return "";
	}
}