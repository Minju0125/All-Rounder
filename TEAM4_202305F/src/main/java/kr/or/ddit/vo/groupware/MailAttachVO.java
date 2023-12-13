package kr.or.ddit.vo.groupware;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of="mailCd")
public class MailAttachVO implements Serializable {
	private MultipartFile mailFile; //어댑티
	
	public MailAttachVO() {};
	
	public MailAttachVO(MultipartFile mailFile) {
		super();
		this.mailFile = mailFile;
		this.mailAttachName = mailFile.getOriginalFilename(); //원본파일명
		this.mailAttachSavename = UUID.randomUUID().toString(); //저장된 파일명
		this.mailAttachMime = mailFile.getContentType();
		this.mailAttachSize = mailFile.getSize();
		this.mailAttachFancysize = FileUtils.byteCountToDisplaySize(mailAttachSize);
	}
	
	private String mailAttachName;
	private String mailAttachSavename;
	private String mailAttachMime;
	private long mailAttachSize;
	private String mailAttachFancysize;
	
	private String mailCd; //메일일련번호 시퀀스로 증가
	private String mailAttachNo; // 파일 첨부 메일 작성시 생성됨
	
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(mailFile!=null) {
			mailFile.transferTo(new File(saveFolder, mailAttachSavename));
		}
	}
}
