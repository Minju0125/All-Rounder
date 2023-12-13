package kr.or.ddit.groupware.mailing.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.common.exception.BoardNotFoundException;
import kr.or.ddit.groupware.mailing.dao.MailDAO;
import kr.or.ddit.login.dao.LoginDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.MailAttachVO;
import kr.or.ddit.vo.groupware.MailReceptionVO;
import kr.or.ddit.vo.groupware.MailVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      박민주       최초작성
 * 2023. 11. 12.     박민주		SMTP, IMAP을 사용하여 전송, 메일 불러오기 확인
 * 2023. 11. 13.     박민주		어플리케이션 내에서 메일 발송 기능 구현 완료 (파일첨부 기능 빠뜨림)
 * 2023. 11. 14.     박민주		+ 파일 첨부 기능 추가
 * 2023. 11. 14.     박민주       메일 불러오기 구현 중
 * 2023. 12. 02.     박민주       임시메일함, 휴지통, 중요메일함 목록 조회 추가
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Slf4j
@Service
public class MailingServiceImpl implements MailingService {

	@Inject
	private MailDAO dao;

	@Inject
	private LoginDAO loginDao;

	@Override
	@Transactional
	public ServiceResult mailSend(MailVO mailVO) {
		//메일 수신자 처리
		String[] mailReceivers = mailVO.getMailReceivers(); 
		List<MailReceptionVO> mailReceiverList = new ArrayList<MailReceptionVO>();
		for(String mailReceiver : mailReceivers) {
			MailReceptionVO vo = new MailReceptionVO(mailReceiver); //E220101001, E220101002
			mailReceiverList.add(vo);
		}
		mailVO.setMailReceiverList(mailReceiverList);
		//메일 첨부파일 처리
		List<MailAttachVO> attachments = new ArrayList<MailAttachVO>();
		MultipartFile[] files = mailVO.getFiles();
		for(MultipartFile multipartFile : files) {
			MailAttachVO mailAttachVO = new MailAttachVO(multipartFile);
			attachments.add(mailAttachVO);
		}
		mailVO.setAttachments(attachments);
		
		/* 발신자 정보 (security) */
		EmployeeVO employeeVO = new EmployeeVO();
		String senderCd = SecurityContextHolder.getContext().getAuthentication().getName(); // 사원번호
		employeeVO.setEmpCd(senderCd);
		EmployeeVO senderVO = loginDao.selectEmpForAuth(employeeVO);
		log.info("mailSend-> senderVO : " + senderVO);
		//bagminju046@gmail.com
		String senderSecEmail = "bagminju046@gmail.com"; // SMTP를 통한 메일 발송을 위해, 지정해둔 실제 second 메일 주소로 가져오기 !
		try {
			Session session = SMTPService.getMailSession();
			/* 메세지 생성 및 설정 */
			MimeMessage msg = new MimeMessage(session);
			InternetAddress addressFrom = new InternetAddress(senderSecEmail);
			msg.setFrom(addressFrom); // 보내는 사람 설정 (= security 에 인증된 사원의 second mail 주소)
			for (MailReceptionVO receiverVO : mailVO.getMailReceiverList()) { // 받는 사람 설정
				EmployeeVO tempEmployeeVO = new EmployeeVO();
				//mailReceivers=[E220101001, E220101002]
				tempEmployeeVO.setEmpCd(receiverVO.getMrReceiver());
				String receptionSecondMail = loginDao.selectEmpForAuth(tempEmployeeVO).getEmpEmailSecond();
				//msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receptionSecondMail));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(senderSecEmail));
			}
			log.info("메일보낼때 내용? ==> " + mailVO);
			msg.setSubject(mailVO.getMailSj(), "UTF-8"); // 제목
			msg.setSentDate(new Date()); // 날짜
			
//			msg.setText(mailVO.getMailCn(), "UTF-8"); // 내용

			MimeMultipart mParts = new MimeMultipart(); 
			MimeBodyPart htmlParts = new MimeBodyPart(); //html 태그(내용)를 위한 mimeBodyPart
			
			//내용 처리
			htmlParts.setContent(mailVO.getMailCn(), "text/html; charset=utf-8");
			mParts.addBodyPart(htmlParts);
			
			/* 첨부파일 처리 */
			
			for (MailAttachVO attachVO : mailVO.getAttachments()) {
				MimeBodyPart mFilePart = new MimeBodyPart();

				DataSource source = new ByteArrayDataSource(attachVO.getMailFile().getBytes(), attachVO.getMailFile().getContentType());
				System.out.println("파일 이름: " + attachVO.getMailFile().getOriginalFilename());
				// 데이터 핸들러를 사용하여 첨부 파일을 추가
				mFilePart.setDataHandler(new DataHandler(source));
				mFilePart.setFileName(attachVO.getMailFile().getOriginalFilename());
				// MimeMultipart에 첨부 파일 추가
				mParts.addBodyPart(mFilePart);
			}
			msg.setContent(mParts);
			Transport.send(msg);
			return ServiceResult.OK;
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return ServiceResult.FAIL;
		}
	}

	@Transactional
	@Override
	public void createMail(MailVO mailVO) {
		String senderCd = SecurityContextHolder.getContext().getAuthentication().getName(); // 사원번호
		mailVO.setMailSender(senderCd);
		mailVO.setMailCn(mailVO.getMailCn());
		dao.insertMailTb(mailVO);

		List<MailReceptionVO> mailReceptList = mailVO.getMailReceiverList();
		for (MailReceptionVO receptionVO : mailReceptList) {
			dao.insertMailReception(receptionVO);
		}

		//mailAttach 테이블에 insert
		List<MailAttachVO> mailAttachList = mailVO.getAttachments();
		for (MailAttachVO mailAttachVO : mailAttachList) {
			dao.insertMailAttach(mailAttachVO);
		}
	}
	

	//=============================수신메일 목록 조회=============================
	@Override
	public List<MailVO> retrieveRecptionMailList(PaginationInfo<MailVO> paging) {
		int totalRecord = dao.selectReceivedTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<MailVO> dataList = dao.selectReceptionMailList(paging);
		paging.setDataList(dataList);
		return dataList;
	}

	//=============================발신 메일 목록 조회=============================
	@Override
	public PaginationInfo<MailVO> retrieveSentMailList(PaginationInfo<MailVO> paging) {
		int totalRecord = dao.selectSentTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<MailVO> dataList = dao.selectSentMailList(paging);
		paging.setDataList(dataList);
		return paging;
	}
	
	//=============================중요 메일 목록 조회=============================
	@Override
	public List<MailVO> retrieveImpotantMailList(PaginationInfo<MailVO> paging) {
		int totalRecord = dao.selectImportantTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<MailVO> dataList = dao.selectImportantMailList(paging);
		paging.setDataList(dataList);
		return dataList;
	}
	
	//=============================삭제 메일 목록 조회=============================
	@Override
	public List<MailVO> retrieveTrashMailList(PaginationInfo<MailVO> paging) {
		int totalRecord = dao.selectTrashTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<MailVO> dataList = dao.selectTrashMailList(paging);
		paging.setDataList(dataList);
		return dataList;
	}

	//IMAP 프로토콜을 통해 메일 가져오기
	@Override
	public MailVO getMAil() {
		return null;
	}

	@Override
	public MailVO retrieveMailDetail(String mailCd) {
		MailVO mail = dao.selectMailDetail(mailCd);
		return dao.selectMailDetail(mailCd);
	}

	@Override
	public MailAttachVO retrieveMailAttach(String attNo) {
		MailAttachVO atch = dao.selectMailAttach(attNo);
		if (atch == null) {
			throw new BoardNotFoundException(HttpStatus.NOT_FOUND, String.format("%d 해당 파일이 없음.", attNo));
		}
		return atch;
	}

	//-----------------------------------수정-----------------------------------
	@Override
	public int updateMail(MailVO mail) {
		String loginUser = SecurityContextHolder.getContext().getAuthentication().getName(); // 사원번호
		mail.setLoginUser(loginUser);
		return dao.updateMail(mail);
	}

	@Override
	public int updateReceptionMail(MailReceptionVO recpetionMail) {
		String loginUser = SecurityContextHolder.getContext().getAuthentication().getName(); // 사원번호
		recpetionMail.setLoginUser(loginUser);
		return dao.updateReceptionMail(recpetionMail);
	}

	@Override
	public MailReceptionVO retrieveReceptionMail(Map<String,String> paramMap) {
		MailReceptionVO mailReception = dao.selectReceptionMail(paramMap);
		return mailReception;
	}

}