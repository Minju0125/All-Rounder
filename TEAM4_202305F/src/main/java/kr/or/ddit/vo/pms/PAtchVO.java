package kr.or.ddit.vo.pms;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.vo.groupware.BoardFileVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	 수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 15.     김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Data
@EqualsAndHashCode(of = "proFileCode")
public class PAtchVO implements Serializable{
	
	private MultipartFile jobFile;
	
	@NotBlank
	private String proFileCode;		//프로젝트 파일코드
	@NotBlank
	private String proAtchnm;		//파일원본이름
	@NotBlank
	private String proAtchSnm;		//파일저장이름
	@NotBlank
	private String proAtchtype;		//파일유형
	
	private String proSn; //프로젝트번호 임시저장

	private int proJobsn; 
	
	private long proAtchSize;
	
	private String proAtchFancysize; 
	
	
	
	public void saveTo(File saveFolder) throws IllegalStateException, IOException {
		if(jobFile != null) {
			jobFile.transferTo(new File(saveFolder, proAtchSnm));
		}
	}

	public PAtchVO() {};

	public PAtchVO(MultipartFile jobFile) {
		super();
		this.jobFile = jobFile;
		this.proAtchnm = jobFile.getOriginalFilename();
		this.proAtchSnm = UUID.randomUUID().toString();
		this.proAtchtype = jobFile.getContentType();
		this.proAtchSize = jobFile.getSize();
		this.proAtchFancysize = FileUtils.byteCountToDisplaySize(proAtchSize);
	}
	
	

}
