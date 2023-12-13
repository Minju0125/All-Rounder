package kr.or.ddit.vo.pms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.vo.groupware.BoardFileVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
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
 * 2023. 11. 7.      송석원       최초작성
 * 2023. 11. 10.	  송석원		수정1      projectVO연결
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of="jobSn")
@ToString
public class PjobVO {

	@NotNull
	private Integer jobSn;		//일감순번
	@NotBlank
	private String proSn;		//프로젝트일련번호	
	private Integer jobuSn;		//상위일감순번	
	@NotBlank
	private String jobSj;		//제목
	@NotBlank
	private String jobWriter;//작성자
	@NotBlank
	private String jobRdate;//작성일
	private String jobStcd;	//일감상태코드
	private String jobPriort;		//우선순위
	@NotBlank
	private String jobBdate;		//일감시작일	
	@NotBlank
	private String jobEdate;		//일감종료예정일	
	@NotBlank
	private String jobCdate;		//일감완료일	
	private String jobCn;			//일감내용	
	private String proFileCd;		//프로젝트파일코드	
	private Integer jobProgrs;	//일감진행도
	@NotBlank
	private String jobScope;		//공개범위	공통고드1(공개)2(비공개)3(리더만공개)
	
	private String empCd1 ;
	private String findName;   //사번으로 찾은 직원의 이름
	private String findName2;   //사번으로 찾은 직원의 이름
	
	
	private String upperJobcount; //상위일감 카운트
	private String lowerJobcount;	//하위 일감 카운트  
	
	
	// 1:N 관계형성
	private List<ChargerVO> chargerList;
	
	private String empProfileImgs; 

	private ProjectVO proj; //has a 관계 
	
	private int rnum;
	
	// 1:N 관계형성
	private List<EmployeeVO> emp;  
	
	private int aaCnt;//진행     	
	private int bbCnt;//요청		
	private int ccCnt;	//피드백	
	private int ddCnt;//보류		
	private int eeCnt;//완료
	private int totalCount;//일감총개수
	
	private MultipartFile[] jobFile;
	
	// 1:N 관계형성
	private List<PAtchVO> jobFileList;
	
	public void setJobFile(MultipartFile[] jobFile) {
		if(jobFile != null) {
			this.jobFile = Arrays.stream(jobFile)
								. filter((f)->!f.isEmpty())
								.toArray(MultipartFile[]::new);
		}
		if(this.jobFile!=null) {
			this.jobFileList = Arrays.stream(this.jobFile)
								.map((f)->new PAtchVO(f))
								.collect(Collectors.toList());
		}
		
	}

	
}
