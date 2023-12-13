package kr.or.ddit.vo.pms;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 작성자명
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 23.      김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Data
@EqualsAndHashCode(of="issueNo")
public class IssueVO implements Serializable {
	
	@NotNull
	private Integer issueNo;
	@NotBlank
	private String proSn;
	@NotBlank
	private String issueRdate;
	@NotBlank
	private String issueEdate;
	@NotBlank
	private String issueImp; //1(긴급),2(중간),3(낮음)  중요도
	@NotBlank
	private String issueSttus; //1(진행),2(보류).3(완료)  상태
	@NotBlank
	private String issueSe; // 1(일반),2(결함)
	@NotBlank
	private String issueSj; //제목
	
	private String refJobName; //참조일감명
	private String writer; //작성자이름
	
	private String issueCn;  //내용
	private String empCd;
	private Integer jobSn;
	
	
	
	//차트용
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	private int f;

	
}
