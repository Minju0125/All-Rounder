package kr.or.ddit.vo.groupware;

import java.util.List;

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
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "bkmkNo")
@ToString
public class BookmarkVO {
	
	private String bkmkNo;		// 즐겨찾기번호 ; BM+사번+001
	private String bkmkOwner;	// 소유주 ; 사번
	private String bkmkNm;		// 즐겨찾기이름
	
	//BOOKMARK테이블 : BOOKMARK_DETAIL테이블 = 1 : N
	// Has Many관계(1:N 관계형성)
	private List<BookmarkDetailVO> detailList;	//결재라인즐겨찾기상세리스트
	private List<SanctionVO> sanction;	// 전자결재문서리스트
	
	// Has a 관계 (1:1 관계형성)
	private EmployeeVO emp;			// 소유자
	
}
