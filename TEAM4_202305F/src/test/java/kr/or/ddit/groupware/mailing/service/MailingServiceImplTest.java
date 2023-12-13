package kr.or.ddit.groupware.mailing.service;

import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import kr.or.ddit.vo.groupware.MailReceptionVO;
import kr.or.ddit.vo.groupware.MailVO;

class MailingServiceImplTest {

//	@Test
	void testCreateMail() {
		fail("Not yet implemented");
	}

//	@Test
	void testRetrieveMailList() {
		fail("Not yet implemented");
	}
	
//	@Test
	void testMailSend() {
		/* SMTP 를 이용하여, 실제로 메일을 전송함 */

		MailVO mailVO = new MailVO();
		mailVO.setMailCd("2311100006");
		mailVO.setMailSj("테스트 히히히");
		mailVO.setMailCn("제발랍라발발");
		LocalDateTime sentDateTime = LocalDateTime.now();
		mailVO.setMailTrnsmisDt(sentDateTime.toString());
		mailVO.setMailSender("bagminju046@gmail.com");
		mailVO.setMailSaveYn("N");
		mailVO.setMailDeleteFlag("0");
		mailVO.setMrImpoYn("N");
		List<MailReceptionVO> rList = new ArrayList<>();
		
		MailReceptionVO recVO = new MailReceptionVO("2311100006");
		recVO.setMrReceiver("minju.park.kim@gmail.com");
		recVO.setMailCd("2311100006");
		
		MailReceptionVO recVO2 = new MailReceptionVO("2311100006");
		recVO2.setMrReceiver("bagminju047@gmail.com");
		recVO2.setMailCd("2311100006");
//		
		rList.add(recVO);
		rList.add(recVO2);
		mailVO.setMailReceiverList(rList);
		
		/* 발송 시간 기록용 */
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDaeTime = sentDateTime.format(formatter);

		Properties props = new Properties(); // 이메일 전송에 필요한 설정을 하기 위한 프로퍼티스 객체 생성

		/* SMTP 관련 설정 */
		/*
		 * 구글 G-mail > smtp.gmail.com / 465 / TLS 네이트 메일 nate > smtp.mail.nate.com / 465
		 * / SSL 다음 한메일 hanmail > smtp.daum.net / 465 / SSL 네이버 메일 naver >
		 * dsmtp.naver.com / 465 / SSL 아웃룩 핫메일 hotmail > smtp-mail.outlook.com / 587 /
		 * TLS
		 */
		try {
			props.put("mail.smtp.port", "587"); // google smtp port 번호
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.put("mail.smtp.debug", "true");
			props.put("mail.smtp.auth", "true");

			/* 인증정보 */
			SMTPService senderAuth = new SMTPService();
			Session session = Session.getDefaultInstance(props, senderAuth);

			/* 메세지 생성 */
			MimeMessage msg = new MimeMessage(session);

			String mailSender = mailVO.getMailSender(); // 발신자 실제메일
			String mailSubject = mailVO.getMailSj(); // 메일 제목
			String mailContent = mailVO.getMailCn(); // 메일 내용

			for (MailReceptionVO receiverVO : mailVO.getMailReceiverList()) {
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverVO.getMrReceiver())); // ?? 타입?
			}

			// 보내는 사람 메일주소
//        InternetAddress addressFrom = new InternetAddress("bagminju046@gmail.com");
			InternetAddress addressFrom = new InternetAddress(mailSender);
			msg.setFrom(addressFrom);

//			InternetAddress addressTo = new InternetAddress("minju.park.kim@gmail.com");
//        msg.addRecipient(Message.RecipientType.TO, addressTo);  //??  타입?
//        msg.setSubject("제목", "UTF-8");            
			msg.setSubject(mailSubject, "UTF-8");
//        msg.setText("안녕하세요 테스트 메일입니다.", "UTF-8"); 
			msg.setText(mailContent, "UTF-8");
			Transport.send(msg);

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
}
