package kr.or.ddit.vo.groupware;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.vo.FileAuthorVO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class WebHardVO {

	private Integer webSnm;
	private String webCours="/";
	private String webTy;
	private String webRnm;
	private MultipartFile file;
	private String uploadPath="D:/ftpLocal/";
	private String webMaker;
	private String webDate;
	
	private String who;
	
	private FileAuthorVO fileAuthor;
}