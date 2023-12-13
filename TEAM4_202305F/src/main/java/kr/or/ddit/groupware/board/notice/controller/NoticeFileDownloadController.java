package kr.or.ddit.groupware.board.notice.controller;

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


import kr.or.ddit.groupware.board.notice.service.NoticeBoardService;
import kr.or.ddit.vo.groupware.BoardFileVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 11.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 11.   전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@Slf4j
public class NoticeFileDownloadController {

	@Value("#{appInfo.boFiles}")
	private Resource boFiles;
	
	@Inject
	private NoticeBoardService service;
	
	@GetMapping("/notice/{bbsNo}/boFile/{fileCode}")
	public ResponseEntity<Resource> download(@PathVariable String fileCode) throws IOException {
		
		BoardFileVO file = service.retrieveBoardFile(fileCode);
		log.info("boFiles=========================" + boFiles);
		Resource boFile = boFiles.createRelative(file.getFileSavename());
		log.info("File path: " + boFile.getFile().getAbsolutePath());
		if(!boFile.exists()) {
			// 파일없음
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "%s 해당 파일 없음");
		}
		
		HttpHeaders headers = new HttpHeaders();
		ContentDisposition disposition = 
				ContentDisposition.attachment()	
				.filename(file.getFileName(), Charset.defaultCharset())	// 자바8버전에서는 기본이 UTF-8
				.build();
		
		headers.setContentDisposition(disposition);	//생략시 인라인방식 구현(이미지를 그려줌) attachment를 써서 파일을 다운받을수 있게 해줌
		// 파일의 크기가 크다면 쪼개지는 데 이거를 청크라고 함
		headers.setContentLength(file.getFileSize());
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return ResponseEntity.ok()	//라인
				.headers(headers)	//헤더
				.body(boFile);	//바디(스트림카피를 떠서 응답데이터를 내보냄)
		}
	
	
}
