package kr.or.ddit.groupware.webhard.service;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import javax.inject.Inject;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.groupware.webhard.dao.WebHardDAO;
import kr.or.ddit.groupware.webhard.ftp.FTPControl;
import kr.or.ddit.vo.FTPVO;
import kr.or.ddit.vo.groupware.WebHardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebHardServiceImpl implements WebHardService{

	@Inject
	private WebHardDAO dao;
	
	@Override
	public void insertFile(WebHardVO webVO) {
		dbInsertFile(webVO);
		localInsertFile(webVO);
	}

	@Transactional
	public void dbInsertFile(WebHardVO webVO) {
		// db 업로드
		webVO.setWebDate(todayDate());
		
		if(!(webVO.getFile()==null)) {		// 파일일 경우
			String originName=webVO.getFile().getOriginalFilename();
			// .은 정규식에서 특별한 의미를 가져서 올바른 분리를 위해서는 이스케이프(\)를 해야함.
			String[] mime=originName.split("\\.");	//[a], [txt]
			webVO.setWebRnm(originName);
			webVO.setWebTy(mime[mime.length-1]);
			System.out.println("webVO : "+webVO);
			System.out.println("fileName : "+ originName);
		}
		dao.insertFile(webVO);
		// 권한 부여
		dao.insertAuthor(webVO);
	}
	
	private String todayDate() {
		LocalDate today=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedToday = today.format(formatter);
		return formattedToday;
	}
	
	public void localInsertFile(WebHardVO webVO) {
		// 로컬 업로드
		String saveFile = webVO.getUploadPath()+webVO.getWebRnm();	// local에 저장될 이름과 경로
		String ftpFile=webVO.getWebCours()+webVO.getWebSnm();	// ftp에 업로드될 이름과 경로
		FTPVO fVO=new FTPVO();
		File file = new File(saveFile);
		if(webVO.getFile()!=null) {		// 파일일 경우
			MultipartFile insertFile=webVO.getFile();
			try {
				insertFile.transferTo(file);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			ftpFile+="."+webVO.getWebTy();
			fVO.setLocalFile(saveFile);
		}
		// ftp 서버 업로드
		fVO.setFtpFile(ftpFile);
		if(!(webVO.getFile()==null)) {		// 파일일 경우
			FTPControl.ftpFileUpload(fVO);
			file.delete();
		}else {									// 폴더일 경우
			FTPControl.ftpForderUpload(fVO);
		}
	}

	@Override
	public List<WebHardVO> selectListFile(WebHardVO webVO) {
		return dao.selectListFile(webVO);
	}

	@Override
	public ResponseEntity<Resource> download(FTPVO ftpVO) {
		ftpVO.setLocalFile("C:/download/"+ftpVO.getLocalFile());
		return FTPControl.ftpFileDownload(ftpVO);
	}

	@Override
	public void delete(WebHardVO webVO) {
		dbDeleteFile(webVO);
		localDeleteFile(webVO);
	}
	
	@Transactional
	public WebHardVO dbDeleteFile(WebHardVO webVO) {
		log.info("삭제에에에에에 : {}",webVO);
		// db 삭제
		dao.deleteFile(webVO);
		// db 권한 삭제
		dao.dbDeleteAuthor(webVO);
		return webVO;
	}
	
	public void localDeleteFile(WebHardVO webVO) {
		// ftp 서버 삭제
		FTPVO fVO=new FTPVO();
		fVO.setFtpFile(webVO.getWebCours()+webVO.getWebSnm());
		if(webVO.getWebTy()!=null) {	// 파일일 경우
			fVO.setFtpFile(fVO.getFtpFile()+"."+webVO.getWebTy());
			FTPControl.ftpFileDelete(fVO);
		}else {
			System.out.println("폴더 삭제"+webVO);
			FTPControl.ftpFolderDelete(fVO);
		}
	}

	@Override
	public void update(WebHardVO webVO) {
		dao.update(webVO);
	}

	@Override
	public WebHardVO selectFile(WebHardVO webVO) {
		return dao.selectFile(webVO);
	}

}
