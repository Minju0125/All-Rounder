package kr.or.ddit.vo.groupware;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 전수진
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.    전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "fileCode")
@NoArgsConstructor
public class BoardFileVO implements Serializable {
	
	private MultipartFile boFile;
	
	public BoardFileVO(MultipartFile bofile) {
		super();
		this.boFile = bofile;
		this.fileName = bofile.getOriginalFilename();
		this.fileMime = bofile.getContentType();
		this.fileSize = bofile.getSize();
		this.fileFancysize = FileUtils.byteCountToDisplaySize(fileSize);
		this.fileSavename = UUID.randomUUID().toString();
	}

	@NotBlank
	private String fileCode;
	@NotBlank
	private Integer bbsNo;
	private String fileName;
	private String fileSavename;
	private String fileMime;
	private long fileSize;
	private String fileFancysize;
	private Integer fileDownload;
	
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(boFile != null) {
			boFile.transferTo(new File(saveFolder, fileSavename));
		}
	}
	
}
