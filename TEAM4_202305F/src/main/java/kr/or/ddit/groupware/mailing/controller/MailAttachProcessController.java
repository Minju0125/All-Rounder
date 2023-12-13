package kr.or.ddit.groupware.mailing.controller;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import kr.or.ddit.groupware.mailing.service.MailingService;
import kr.or.ddit.vo.groupware.MailAttachVO;

@Controller
public class MailAttachProcessController {
	
	@Inject
	private MailingService service;
	
	@Value("#{appInfo.mailFiles}")
	private Resource mailAtchFiles;

	@GetMapping("/mail/{mailCd}/mailAttach/{attNo}")
	public ResponseEntity<Resource> download(@PathVariable String attNo) throws IOException {
		MailAttachVO atch = service.retrieveMailAttach(attNo);
		Resource mailFile = mailAtchFiles.createRelative(atch.getMailAttachSavename());
		if(!mailFile.exists()) { 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 파일이 없음");
		}
		//파일이 존재하는 경우
		HttpHeaders headers = new HttpHeaders();
		ContentDisposition disposition = ContentDisposition.attachment()
											.filename(atch.getMailAttachName(), Charset.defaultCharset())
											.build();
		headers.setContentDisposition(disposition);
		headers.setContentLength(atch.getMailAttachSize());
		headers.setContentType(null);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return ResponseEntity.ok() 
							.headers(headers)
							.body(mailFile); 
	}
}
