package kr.or.ddit.vo.groupware;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 전수진
 * @since 2023. 11. 14.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 14.  전수진       최초작성
 * 2023. 11. 20.  전수진       MultipartFile 처리, save 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "attachNo")
@NoArgsConstructor
public class SanctionAttachVO implements Serializable{

	private MultipartFile sanctionFile;
	
	public SanctionAttachVO(MultipartFile sanctionFile) {
		super();
		this.sanctionFile = sanctionFile;
		this.attachOriginNm = sanctionFile.getOriginalFilename();
		this.attachMime = sanctionFile. getContentType();
		this.attachSize = sanctionFile.getSize();
		this.attachFancysize = FileUtils.byteCountToDisplaySize(attachSize);
		this.attachSaveNm = UUID.randomUUID().toString();
	}
	
	private Integer attachNo;		// 결재첨부파일번호 1 ~
	private String sanctnNo;		// 결재문서번호 ; DOC20231104001
	private String attachOriginNm;	// 파일원본이름
	private String attachSaveNm;	// 파일저장이름
	private String attachMime;		// 파일유형
	private long attachSize;		// 파일크기
	private String attachFancysize;	// 팬시크기
	
	// Has a 관계 (1:1 관계형성)
	private SanctionVO sanction;	// 결재문서
	
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(sanctionFile != null) {
			sanctionFile.transferTo(new File(saveFolder, attachSaveNm));
		}
	}
}
