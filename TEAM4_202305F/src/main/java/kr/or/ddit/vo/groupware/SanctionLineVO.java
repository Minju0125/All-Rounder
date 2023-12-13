package kr.or.ddit.vo.groupware;

import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.common.BlobController;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * 2023. 11. 22.  전수진       서명이미지 처리추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "sanctnLineNo")
public class SanctionLineVO {

	private String sanctnLineNo;			// 결재라인번호 ; SL231106001
	private String sanctnNo;				// 결재문서번호	; BM+사번+001
	private String sanctner;				// 결재자 ; 사번
	private String sanctnerNm;				// 결재자 명 ; FN_GET_EMP_NAME
	private String sanctnerRankNm;			// 결재자 직급 명 ; FN_GET_EMP_NAME
	private String sanctnerDeptName;		// 결재자 부서 명 ; FN_GET_EMP_NAME
	private String realSanctner;			// 실결재자 ; 사번
	private String realSanctnerNm;			// 실결재자 명 ; FN_GET_EMP_NAME
	private String realSanctnerRankNm;		// 실결재자 직급 명 ; FN_GET_EMP_NAME
	private String realSanctnerDeptName;	// 실결재자 부서 명 ; FN_GET_EMP_NAME
	private Integer sanctnOrdr;				// 결재순서
	private String sanctnOpinion;			// 결재의견
	private String sanctnerEndyn;			// 최종결재자여부
	private String sanctnlineSttus;			// 결재상태
	private String signDate;				// 결재일자
	
	private byte[] signImg;					// 서명이미지
	
	private MultipartFile signImage;		// 서명이미지 처리용
	
	// Has a 관계 (1:1 관계형성)
	private SanctionVO sanction;	// 결재양식
	
	public void setSignImage(MultipartFile signImage) throws IOException {
		if(signImage!=null && signImage.isEmpty()) {
			this.signImage = signImage;
			signImg = signImage.getBytes();
		}
	}
	
	public String getSignImgBase64() {
		if(signImg!=null) {
			return Base64.getEncoder().encodeToString(signImg);
		}else {
			return null;
		}
	}
	
	public String getSignImg() {
		if(signImg!=null) {
//			return Base64.getEncoder().encodeToString(signImg);

			//blob 데이터를 byte로 변환 실시 [필요시 base64 인코딩 >>  data url 생성 가능]
//	        byte arr[] = blobToBytes((Blob) result.get("T_BLOB"));
			
	        //data url 리턴 실시
	        if(this.signImg.length > 0 && this.signImg != null){ //데이터가 들어 있는 경우
	            //바이트를 base64인코딩 실시
	            String base64Encode = BlobController.byteToBase64(this.signImg);
	            base64Encode = "data:image/png;base64," + base64Encode;
	            return base64Encode;
	        }
	        else {
	            return "";
	        }
		}else {
			return "";
		}
	}

	
	
	
	
	
}
