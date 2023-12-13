package kr.or.ddit.groupware.sanction.controller;

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

import kr.or.ddit.groupware.sanction.service.SanctionService;
import kr.or.ddit.vo.groupware.SanctionAttachVO;

/**
 * @author 전수진
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 20.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
public class SanctionAttachProcessController {
	
	@Inject
	private SanctionService service;
	
	@Value("#{appInfo.sanctionFiles}")
	private Resource sanctionFiles;

	@GetMapping("/sanction/{sanctnNo}/sanctionAttach/{attachNo}")
	public ResponseEntity<Resource> download(@PathVariable int attachNo) throws IOException {
		
		SanctionAttachVO attach = service.retrieveSanctionAttach(attachNo); //첨부파일 번호로 첨부파일VO
		
		//createRelative() : 해당 리소스로부터 상대 경로를 이용하여 새로운 리소스 생성
		Resource sanctionFile = sanctionFiles.createRelative(attach.getAttachSaveNm());
		if(!sanctionFile.exists()) { 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 파일이 없음");
		}
		
		//파일이 존재하는 경우
		HttpHeaders headers = new HttpHeaders();
		ContentDisposition disposition = ContentDisposition.attachment()
											.filename(attach.getAttachOriginNm(), Charset.defaultCharset())
											.build();
		//==> 해당 파일 이름을 첨부파일로 다운로드하도록 브라우저에 지시 (getAttFilename으료)
		headers.setContentDisposition(disposition); //여기까지 파일 다운로드 과정
		
		//전체 파일에 대한 데이터 제공
		headers.setContentLength(attach.getAttachSize()); //파일의 크기 내보내줌
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); //바이트 스트림 데이터 받을 때 (8bit=1byte)-일반적으로 이거 세팅함
		//attachment 로 다운로드 할 때는 보통 한가지로 세팅함-> 
		return ResponseEntity.ok() //성공적인 처리 -> ok
							.headers(headers) //header 를 하나의 객체로 모음
							.body(sanctionFile); //파일 한건을 넣음
	}
	
	
}
