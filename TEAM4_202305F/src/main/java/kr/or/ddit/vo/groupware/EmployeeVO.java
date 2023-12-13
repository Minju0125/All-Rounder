package kr.or.ddit.vo.groupware;

import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.validate.grouphint.DeleteGroup;
import kr.or.ddit.validate.grouphint.InsertGroup;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 작성자명
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      박민주       1차 수정 (lombok annotation 추가)
 * 2023. 11. 8.      송석원       2차 수정(프로젝트vo연결)
 * 2023. 11. 9.      오경석       3차 수정(has a 관계 추가)
 * 2023. 11. 14.     전수진       4차 수정(has many 관계 추가, 전자결재관련 vo 연결)
 * 2023. 11. 20.     김보영       5차 수정 직원생성시 필요한 변수추가, (이미지처리)
 * 2023. 11. 27.     송석원       6차 수정 프로젝트 인원추가를 위한 변수추가
 * 2023. 12. 04.     김보영       7차 직원퇴사일 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@EqualsAndHashCode(of = "empCd")
@Data
public class EmployeeVO implements Serializable {

	@NotBlank(groups = { Default.class, DeleteGroup.class })
	private String empCd;
	@NotBlank(groups = { Default.class, DeleteGroup.class }) // 탈퇴시 이중인증 위해
	@Size(min = 8, max = 12, groups = { Default.class, DeleteGroup.class })
	private String empPw;
	@NotBlank(groups = InsertGroup.class) // 가입시만 검증을 표현
	@Size(max = 50, groups = InsertGroup.class)
	private String empName; // 수정 대상 아님

	//마이페이지의 수정가능 목록 : 프로필사진,비밀번호,내선전화,휴대전화,주소
	@NotBlank
	@Size(max = 5, min = 5)
	@ToString.Exclude
	private transient String empZip;
	@NotBlank
	@ToString.Exclude
	private transient String empAdres;
	@NotBlank
	@ToString.Exclude
	private transient String empAdresDetail;
	@NotBlank
	private String empRank;
	@NotBlank
	private String empPosition;
	@NotBlank
	private String deptCd;
	@NotBlank
	@Email
	private String empMail;
	private String empProfileImg;
	private byte[] empSignImg;
	@NotBlank
	@Pattern(regexp = "010-\\d{3,4}-\\d{4}")
	private String empTelno;
	private String empCrtfcNo;
	private String empExtension;
	@Email
	private String empEmailSecond;
	
	@NotBlank
	private String empLoginFlag;  //L(퇴사)
	private String empQr;

	private String empBirth;
	private transient String empSsn;
	private String empHiredate;
	private String empLeavedate;
	
	@NotBlank
	private String empSuprr;
	
	private String empRole;

	//주민번호조합
	private String empSsn1;
	private String empSsn2;
	
	private String proSn;//프로젝트번호 
	
	// 부서명 직급명
	private String deptName;
	private String rankName;
	
	
	//부서인원 카운트
	private String deptCount;
	//직급별 인원 카운트
	private String rankCount;
	
	
	// Has a관계(1:1 관계형성)
	private DeptVO dept;		// 부서
	private CommonVO common;	// 공통코드
	
	// Has Many관계(1:N 관계형성)
	private List<ProjectVO> projectList; 		// PMS 프로젝트 리스트
	private List<MailVO> mailList;  			// 메일 리스트
	private List<BookmarkVO> bookmarkList;		// 즐겨찾기 리스트
	private List<SanctionAttachVO> attachList;	// 첨부파일 리스트
	private List<SanctionLineVO> lineList;		// 결재라인 리스트
	
	public String getRoleSetting() {
		return "admin".equals(empCd) ? "ROLE_ADMIN" : "ROLE_USER";
	}
	
//	public String getEmpRole(){
//		return "admin".equals(empCd) ? "ROLE_ADMIN" : "ROLE_USER";
//	}
	
	//이미지처리
	private MultipartFile empSignImage;
	private MultipartFile empQrImage;
	private MultipartFile empProfileImage;
	
	public void setEmpSignImage(MultipartFile empSignImage) throws IOException {
		if(empSignImage!=null && !empSignImage.isEmpty()) {
			this.empSignImage = empSignImage;
			empSignImg = empSignImage.getBytes();
		}
	}

	public String getEmpSignImgBase64() {
		if(empSignImg!=null)
			return Base64.getEncoder().encodeToString(empSignImg);
		else
			return null;
	}
	
	
}
