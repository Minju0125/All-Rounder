package kr.or.ddit.vo.groupware;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.common.BlobController;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
 * 2023. 11. 20.  전수진       첨부파일 관련 메소드 수정
 * 2023. 11. 27.  전수진       기안자 서명이미지컬럼 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "sanctnNo")
public class SanctionVO {
	
	private int rnum;
	
	private String sanctnNo;			// 결재문서번호 ; DOC20231104001
	private String drafter;				// 기안자 ; 사번
	private String drafterNm;			// 기안자 명 ; 사번
	private String drafterRankNm;		// 기안자 직급명 
	private String drafterDeptName;		// 기안자 부서명 
	private String drafterDeptCd;		// 기안자 부서코드 
	private Integer formNo;				// 양식번호
	@NotBlank
	private String sanctnSj;			// 결재문서 제목
	private String sanctnDate;			// 결재문서 작성일자
	@ToString.Exclude
	private String sanctnSourc;			// 결재문서 내용
	private String sanctnSttus;			// 결재상태
	private String sanctnSttusNm;			// 결재상태명
	private String sanctnRcyer;			// 수신자 ; 사번(업무담당자)
	private String sanctnRcyerRankNm;	// 수신자 직급명 ; 사번(업무담당자)
	private String sanctnRcyerDeptName;	// 수신자 부서명; 사번(업무담당자)
	private String sanctnRcyerNm; 		//수신자 명
	
	private byte[] drafterSignImg;				// 기안자 서명이미지
	private MultipartFile drafterSignImage;		// 기안자 서명이미지 처리용
	
	private MultipartFile[] sanctionFile;
	
	// Has Many관계(1:N 관계형성)
	private List<SanctionAttachVO> attachList;	// 첨부파일 리스트
	private List<SanctionLineVO> lineList;		// 결재라인 리스트
	
	// Has a 관계 (1:1 관계형성)
	private BookmarkVO bookmark;	// 결재라인즐겨찾기
	private SanctionFormVO sanctionForm;	// 결재양식
	private EmployeeVO emp;					// 기안자
	
	
	public void setSanctionFile(MultipartFile[] sanctionFile) {
		if(sanctionFile != null) {
			this.sanctionFile = Arrays.stream(sanctionFile)
									.filter((sf)->!sf.isEmpty())
									.toArray(MultipartFile[]::new);
		}
		if(this.sanctionFile!=null) {
			this.attachList = Arrays.stream(this.sanctionFile)
								.map((sf)->new SanctionAttachVO(sf))
								.collect(Collectors.toList());
		}
	}


	public String getDrafterSignImg() {
		if(drafterSignImg!=null) {
			if(this.drafterSignImg.length > 0 && this.drafterSignImg != null) {
	            String base64Encode = BlobController.byteToBase64(this.drafterSignImg);
	            base64Encode = "data:image/png;base64," + base64Encode;
	            return base64Encode;
			} else {
	            return "";
	        }
		}else {
			return "";
		}
	}
	
	public String getDrafterSignImgBase64() {
		if(drafterSignImg!=null) {
			return Base64.getEncoder().encodeToString(drafterSignImg);
		}else {
			return null;
		}
	}

	public void setDrafterSignImage(MultipartFile drafterSignImage) throws IOException {
		if(drafterSignImage!=null && drafterSignImage.isEmpty()) {
			this.drafterSignImage = drafterSignImage;
			drafterSignImg = drafterSignImage.getBytes();
		}
	}
	
}
