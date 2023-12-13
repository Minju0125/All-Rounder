package kr.or.ddit.vo.groupware;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.validate.grouphint.DeleteGroup;
import kr.or.ddit.validate.grouphint.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
 * 2023. 11. 24.    전수진      게시판번호를 위한 RNUM 추가
 * 2023. 11. 25.    김보영      경조사 게시판 - 시작일,종료일,구분추가
 * 2023. 12. 04. 	오경석		댓글기능
 * 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "bbsNo")
public class BoardVO implements Serializable{
	
	private int rnum;
	
	@NotNull(groups = {UpdateGroup.class, DeleteGroup.class})
	private int bbsNo;
	@NotBlank
	private String bbsSj;
	@NotBlank
	private String bbsCn;	// 게시물내용
	private LocalDateTime bbsRgsde;	//등록일자, 값을 설정하지 않는 항목은 NotNull이나, NotBlank조건을 주지않음
	private String empCd;
	private int bbsRdcnt;
	private String noiceMustRead;
	private String noticeTmlmt; //마감일자
	private String bbsCategory;	// 게시판 유형  E : 경조사 N:공지사항 Q:qna
	private String eventRdate;
	private String bbsRdate;
	
	private Double bbsEquator;  //경도
	private Double bbsLatitude;  //위도
	
	private String eventSttus; //경조사 구분 1.결혼/출산 2. 사망 3.생일 4. 기타
	private String eventBdate; //경조사 시작일 
	private String eventEdate; //경조사 종료일
	private String eventLoc ; //경조사 장소
	//이모지 카운팅
	private int angryCnt;   //화난거
	private int sadCnt;    //우는거
	private int smileCnt;  //웃는거
	private int impressCnt;  //감동
	private int thumbsCnt;    //박수
	private int selectEmo;
	
	private String empName;
	
	private BoardFileVO freeboardFile;
	private MultipartFile freeboardImgFile;
	
	
	private MultipartFile[] boFile;
	
	
	// 1:N 관계형성
	private List<BoardFileVO> boardFileList;
	
	public void setBoFile(MultipartFile[] boFile) {
		if(boFile != null) {
			this.boFile = Arrays.stream(boFile)
								. filter((f)->!f.isEmpty())
								.toArray(MultipartFile[]::new);
		}
		if(this.boFile!=null) {
			this.boardFileList = Arrays.stream(this.boFile)
								.map((f)->new BoardFileVO(f))
								.collect(Collectors.toList());
		}
		
	}
	
	private List<AnswerVO> anserList;
	

}
