package kr.or.ddit.groupware.mailing.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.mailing.dao.MailDAO;
import kr.or.ddit.groupware.mailing.service.MailingService;
import kr.or.ddit.vo.PaginationInfo;
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
 * 2023. 11. 15.	 박민주		조회 메소드 추가
 * 2023. 12. 02.     박민주		중요메일함 목록 조회 추가
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Slf4j
@Controller
@RequestMapping("/mail")
public class mailingController {

	@Inject
	MailingService service;

	@Inject
	MailDAO dao;

	@GetMapping("/mailForm")
	public String doGet() {
		return "mail/mailForm";
	}

	@Value("#{appInfo.mailFiles}")
	private Resource mailAtchFiles;

	// -------------------- 메일 상세 조회 --------------------
	@GetMapping("/{mailCd}")
	@ResponseBody // AJAX요청 받는 URL
	public MailVO mailDetailView(@PathVariable("mailCd") String mailCd) {
		MailVO mail = service.retrieveMailDetail(mailCd);
		return mail;
	}

	// -------------------- 메일 목록 조회 --------------------
	// 받은 메일함 페이징 처리(inbox)
	@GetMapping("/receptionMail")
	@ResponseBody
	public Map<String, PaginationInfo<MailVO>> selectRecievedMailList(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			Authentication authentication) {

		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("empCd", authentication.getName()); // 로그인된 직원의 사번

		Map<String, PaginationInfo<MailVO>> map = new HashMap<>();
		PaginationInfo<MailVO> paging = new PaginationInfo<MailVO>(8, 5);

		paging.setCurrentPage(currentPage);
		paging.setVariousCondition(variousCondition);
		List<MailVO> dataList = service.retrieveRecptionMailList(paging);
		paging.setDataList(dataList);

		map.put("paging", paging);

		service.retrieveRecptionMailList(paging);
		map.put("paging", paging);

		return map;
	}

	// 보낸 메일함 페이징 처리 (sent)
	@GetMapping("/sentMail")
	@ResponseBody
	public Map<String, PaginationInfo<MailVO>> selectSentMailList(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			Authentication authentication) {
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("empCd", authentication.getName()); // 로그인된 직원의 사번

		Map<String, PaginationInfo<MailVO>> map = new HashMap<>();
		PaginationInfo<MailVO> paging = new PaginationInfo<MailVO>(8, 5);

		paging.setCurrentPage(currentPage);
		paging.setVariousCondition(variousCondition);

		PaginationInfo<MailVO> pagination = service.retrieveSentMailList(paging);
		paging.setDataList(pagination.getDataList());

		for (MailVO mail : pagination.getDataList()) {
			String mailCd = mail.getMailCd();
			List<MailReceptionVO> receptionList = dao.selectReceptionList(mailCd);
			variousCondition.put(mailCd, receptionList);
		}
		map.put("paging", paging);
		paging.setVariousCondition(variousCondition);

		return map;
	}

	// 중요 메일함 페이징 처리 (starred)
	@GetMapping("/starredMail")
	@ResponseBody
	public Map<String, PaginationInfo<MailVO>> selectStarredMailList(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			Authentication authentication) {
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("empCd", authentication.getName()); // 로그인된 직원의 사번

		Map<String, PaginationInfo<MailVO>> map = new HashMap<>();
		PaginationInfo<MailVO> paging = new PaginationInfo<MailVO>(8, 5);

		paging.setCurrentPage(currentPage);
		paging.setVariousCondition(variousCondition);
		List<MailVO> dataList = service.retrieveImpotantMailList(paging);
		paging.setDataList(dataList);

		map.put("paging", paging);
		log.info("중요 메일함 페이징  ===> " + paging);

		return map;
	}
	
	// 삭제 메일 페이징 처리 (delete)
	@GetMapping("/trashMail")
	@ResponseBody
	public Map<String, PaginationInfo<MailVO>> selectTrashMailList(
			@RequestParam(value = "page", required = false, defaultValue = "1") int currentPage,
			Authentication authentication) {
		Map<String, Object> variousCondition = new HashMap<>();
		variousCondition.put("empCd", authentication.getName()); // 로그인된 직원의 사번

		Map<String, PaginationInfo<MailVO>> map = new HashMap<>();
		PaginationInfo<MailVO> paging = new PaginationInfo<MailVO>(8, 5);

		paging.setCurrentPage(currentPage);
		paging.setVariousCondition(variousCondition);
		List<MailVO> dataList = service.retrieveTrashMailList(paging);
		paging.setDataList(dataList);

		map.put("paging", paging);
		log.info("삭제 메일함 페이징  ===> " + paging);

		return map;
	} 

	// --------------------------- 메일 상태 변경 (삭제, 중요처리) ---------------------------
	// 한건의 메일 수정(삭제처리)
	@PutMapping("/delete/{emailCd}")
	@ResponseBody
	public Map<String, String> updateMailDeleteOne(@PathVariable("emailCd") String mailCd,
			@RequestBody Map<String, String> requestBody, Authentication authentication) {
		String targetArea = requestBody.get("targetArea");
		int updateResult = 0;
		if ("sent".equals(targetArea)) { // 받은 메일함
			MailVO mail = service.retrieveMailDetail(mailCd);
			mail.setMailDeleteFlag("1");
			updateResult = service.updateMail(mail);
		} else if ("inbox".equals(targetArea)) { // 보낸 메일함
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("mailCd", mailCd);
			paramMap.put("loginUser", authentication.getName());
			MailReceptionVO receptionMail = service.retrieveReceptionMail(paramMap);
			receptionMail.setMailDeleteFlag("1");
			updateResult = service.updateReceptionMail(receptionMail);
		} else { // (아직까진) 중요메일함 (starred)
		}
		Map<String, String> resultMap = new HashMap<>();
		String success = "Fail";
		if (updateResult > 0) {
			success = "OK";
		}
		resultMap.put("success", success);
		return resultMap;
	}

	// 한건의 메일 수정 (중요표시)
	@PutMapping("/important/{emailCd}")
	@ResponseBody
	public Map<String, String> updateMailImportantOne(@PathVariable("emailCd") String mailCd,
			@RequestBody Map<String, String> requestBody, Authentication authentication) {
		String targetArea = requestBody.get("targetArea");
		int updateResult = 0;
		if ("sent".equals(targetArea)) { // 받은 메일함
			MailVO mail = service.retrieveMailDetail(mailCd);
			String impoYN = mail.getMrImpoYn();
			if ("Y".equals(impoYN)) {
				mail.setMrImpoYn("N");
			} else {
				mail.setMrImpoYn("Y");
			}
			updateResult = service.updateMail(mail);
		} else { // 보낸 메일함
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("mailCd", mailCd);
			paramMap.put("loginUser", authentication.getName());
			MailReceptionVO receptionMail = service.retrieveReceptionMail(paramMap);
			String impoYN = receptionMail.getMrImpoYn();
			if ("Y".equals(impoYN)) {
				receptionMail.setMrImpoYn("N");
			} else {
				receptionMail.setMrImpoYn("Y");
			}
			updateResult = service.updateReceptionMail(receptionMail);
		}
		Map<String, String> resultMap = new HashMap<>();
		String success = "Fail";
		if (updateResult > 0) {
			success = "OK";
		}
		resultMap.put("success", success);
		return resultMap;
	}
	
	// 한건의 메일 수정 (영구삭제)
		@PutMapping("/permanentlyDelete/{emailCd}")
		@ResponseBody
		public Map<String, String> updateMailPermanentlyDeleteOne(@PathVariable("emailCd") String mailCd,
				@RequestBody Map<String, String> requestBody, Authentication authentication) {
			String targetArea = requestBody.get("mailType"); //수신메일인지 발신메일인지
			//이 targetArea 에 따라서 수신메일에서 지울건지 발신메일에서 지울건지 결정
			Map<String, String> resultMap = new HashMap<String, String>();
			int result = 0;
			String success = "Fail";
			if("sent".equals(targetArea)) { //발신메일에서 영구삭제 처리
				MailVO mail = service.retrieveMailDetail(mailCd);
				mail.setMailDeleteFlag("2");
				result = service.updateMail(mail);
				success ="OK";
			}else { //수신메일에서 영구삭제 처리
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("mailCd", mailCd);
				paramMap.put("loginUser", authentication.getName());
				MailReceptionVO receptionMail = service.retrieveReceptionMail(paramMap);
				receptionMail.setMailDeleteFlag("2");
				result = service.updateReceptionMail(receptionMail);
				success ="OK";
			}
			if(result>0) {
				resultMap.put("success", success);
			}
			return resultMap;
		}
		
		// 한건의 메일 수정 (복구)
		@PutMapping("/restoreDelete/{emailCd}")
		@ResponseBody
		public Map<String, String> updateMailRestoreOne(@PathVariable("emailCd") String mailCd,
				@RequestBody Map<String, String> requestBody, Authentication authentication) {
			String targetArea = requestBody.get("mailType"); //수신메일인지 발신메일인지
			//이 targetArea 에 따라서 수신메일에서 지울건지 발신메일에서 지울건지 결정
			Map<String, String> resultMap = new HashMap<String, String>();
			int result = 0;
			String success = "Fail";
			if("sent".equals(targetArea)) { //발신메일에서 영구삭제 처리
				MailVO mail = service.retrieveMailDetail(mailCd);
				mail.setMailDeleteFlag("0");
				result = service.updateMail(mail);
				success ="OK";
			}else { //수신메일에서 영구삭제 처리
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("mailCd", mailCd);
				paramMap.put("loginUser", authentication.getName());
				MailReceptionVO receptionMail = service.retrieveReceptionMail(paramMap);
				receptionMail.setMailDeleteFlag("0");
				result = service.updateReceptionMail(receptionMail);
				success ="OK";
			}
			if(result>0) {
				resultMap.put("success", success);
			}
			return resultMap;
		}

	// 메일 전송
	@PostMapping
	@ResponseBody
	public Map<String, String> processSendMail(MailVO mailVO) {
		String success = "NG";
		String message = "";
		ServiceResult sendMailResult = service.mailSend(mailVO);
		if (sendMailResult.equals(ServiceResult.OK)) {
			success = "OK";
			message = "메일을 발송하였습니다.";
			service.createMail(mailVO);
			List<MailAttachVO> attchList = mailVO.getAttachments();
			if (attchList != null) {
				for (MailAttachVO attch : attchList) {
					try {
						attch.saveTo(mailAtchFiles.getFile());
					} catch (IllegalStateException | IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
		} else {
			message = "메일발송에 실패하였습니다.";
		}
		Map<String, String> mjResult = new HashMap<String, String>();
		mjResult.put("status", success);
		mjResult.put("message", message);
		return mjResult;
	}
}