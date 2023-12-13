package kr.or.ddit.groupware.webhard.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import kr.or.ddit.vo.FTPVO;
import kr.or.ddit.vo.groupware.WebHardVO;

public interface WebHardService {

	void insertFile(WebHardVO webVO);

	List<WebHardVO> selectListFile(WebHardVO webVO);

	ResponseEntity<Resource> download(FTPVO ftpVO);

	void delete(WebHardVO webVO);

	void update(WebHardVO webVO);

	WebHardVO selectFile(WebHardVO webVO);

}
