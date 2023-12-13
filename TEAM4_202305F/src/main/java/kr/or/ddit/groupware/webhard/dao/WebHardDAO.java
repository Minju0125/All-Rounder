package kr.or.ddit.groupware.webhard.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.groupware.WebHardVO;

@Mapper
public interface WebHardDAO {

	void insertFile(WebHardVO webVO);

	List<WebHardVO> selectListFile(WebHardVO webVO);

	void insertAuthor(WebHardVO webVO);

	void deleteFile(WebHardVO webVO);

	WebHardVO selectFile(WebHardVO webVO);

	void update(WebHardVO webVO);

	void dbDeleteAuthor(WebHardVO webVO);

}
