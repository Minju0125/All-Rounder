package kr.or.ddit.groupware.mailing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import com.sun.mail.handlers.multipart_mixed;

import jakarta.mail.BodyPart;
import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Part;
import jakarta.mail.Session;
import jakarta.mail.Store;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.search.AndTerm;
import jakarta.mail.search.ComparisonTerm;
import jakarta.mail.search.ReceivedDateTerm;
import jakarta.mail.search.SearchTerm;
import kr.or.ddit.vo.groupware.MailAttachVO;
import kr.or.ddit.vo.groupware.MailVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class IMapMailManagerTest {

	private String saveDirectory;

	/**
	 * 첨부파일이 저장될 위치 설정
	 * 
	 * @param dir
	 */
	public void setSaveDirectory(String dir) {
		this.saveDirectory = dir;
	}

	String host = "imap.gmail.com";
	String username = "bagminju046@gmail.com";
	String password = "ffuruyqvtzvbwksc";

	@Test
	public void getMail() {
		MailVO mailVO = new MailVO();

		/* IMAP 속성 설정 */
		Properties properties = new Properties();
		properties.setProperty("mail.store.protocol", "imaps");
		properties.setProperty("mail.imap.host", host);
		properties.setProperty("mail.imap.port", "993");
		setSaveDirectory("D:/02.medias/images/mail");

		try {
			/* 세션 생성 */
			Session session = Session.getDefaultInstance(properties); // 세션 생성

			/* store 생성 및 연결 */
			Store store = session.getStore("imaps"); // 위에 설정한 특정 프로토콜에 대한 Store 객체 받아옴
			store.connect(host, username, password);

			/* Inbox(받은편지함) 폴더 열기 */
			Folder inbox = store.getFolder("INBOX");
			inbox.open(Folder.READ_ONLY);

			/* 메세지 가져오기 */
			Message[] messages = inbox.getMessages();
			for (Message message : messages) {

				System.out.println("-----------------메시지  ----------------------------");
				mailVO.setMailSj(message.getSubject()); // 제목
				mailVO.setMailSender(InternetAddress.toString(message.getFrom())); // 발신자
				mailVO.setMailTrnsmisDt(String.valueOf(message.getSentDate())); // 일시

//				log.info("=============이메일 가져오기============mailVO ==> " + mailVO);

				String messageContent = "";
				/* 첨부파일 처리 */
				String contentType = message.getContentType();
				System.out.println("contentType ==> " + contentType);
				List<MailAttachVO> attachList = new ArrayList<>();
				if(contentType.contains("multipart")) { //contentType이 멀티파트인 경우
					System.out.println("멀티파트 메일");
					//멀티파트 : 본문과 첨부 파일 등이 포함된 이메일의 다양한 부분을 접근
					Multipart multipart = (Multipart)message.getContent();
					int partCount = multipart.getCount(); // multipart 객체에서 몇 개의 부분으로 이루어져 있는지 확인 (=이메일이 몇개의 섹션으로 나뉘어있는지)
					for(int i=0; i<partCount; i++) { //파트의 갯수만큼 반복
						BodyPart part = multipart.getBodyPart(i);
						//bodyPart가 첨부파일이 아닌경우
						//해당 파트의 disposition을 통해 첨부파일인지 아닌지 판별한다 !
						//일반적으로 disposition은 inline 또는 attachment 중 한가지 값을 가짐.
						if(Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())){ //첨부파일인 경우
							//mailVO의 attachList에 넣는다. (가져올때는 DB에 저장하지 않아도 됨!)
							String fileName = part.getFileName();
							System.out.println("첨부파일이름? ==> " + fileName);
							MailAttachVO mailAttachVO = new MailAttachVO();
							mailAttachVO.setMailAttachName(fileName);
							String indexString = String.format("%03d", i + 1);
							mailAttachVO.setMailAttachNo(indexString);
							attachList.add(mailAttachVO);
						}else {// inline 인 경우
							messageContent = part.getContent().toString();
							System.out.println("■■■■■■■>>>>" + messageContent + "<<<■■■■■■■");
						}
						
					}
				}else if(contentType.contains("TEXT/PLAIN") || contentType.contains("TEXT/HTML")){ //contentType이 PLAIN 이나, HTML인 경우
					System.out.println("플레인이나 흐트믈");
					messageContent = message.getContent().toString();
					System.out.println("첨부파일내용==> " + messageContent);
				}else {
					System.out.println("다 아님");
					messageContent = message.getContent().toString();
					System.out.println("첨부파일내용==> " + messageContent);
				}
				
				mailVO.setAttachments(attachList);
				mailVO.setMailCn(messageContent);
				
				System.out.println("========> 최종 mailVO : ==> " + mailVO);
				
				
				
//				List<MailAttachVO> attachList = new ArrayList<>();
//
//				String contentType = message.getContentType();
//				if (contentType.contains("multipart")) {
//					Multipart multiPart = (Multipart) message.getContent();
				
//					int numberOfParts = multiPart.getCount();
				
//					for (int i = 0; i < numberOfParts; i++) {
//						MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(i);
//						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
//
//							// 첨부파일 있을 경우 지정 폴더로 저장 ("D:/02.medias/images/mail")
//							String fileName = part.getFileName();
//							MultipartFile file = (MultipartFile) new File(fileName, saveDirectory);
//							System.out.println(new MailAttachVO(file));
//							
//							attachFiles += fileName + ", ";
//							part.saveFile(saveDirectory + File.separator + fileName);
//						} else {
//							// 메일 내용 저장
//							messageContent = part.getContent().toString();
//						}
//					}
//					// 첨부파일이 있는 경우
//					if (attachFiles.length() > 1) {
//						attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
//					}
//				} else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
//					// 여긴 거의 타지 않음
//					Object content = message.getContent();
//					if (content != null) {
//						messageContent = content.toString();
//					}
//				}
//				System.out.println("messageContent: " + messageContent);
//			}

			}
			inbox.close(false);
			store.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
