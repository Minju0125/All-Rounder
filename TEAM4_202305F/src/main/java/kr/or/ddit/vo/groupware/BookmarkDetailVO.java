package kr.or.ddit.vo.groupware;

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
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Data
@EqualsAndHashCode(of = "bkmkDetailNo")
public class BookmarkDetailVO {

	private Integer bkmkDetailNo;	// 즐겨찾기상세번호 ; 1~
	private String bkmkNo;			// 즐겨찾기번호 ; BM+사번+001
	private Integer sanctnOrdr;		// 결재순서 
	private String sanctner;		// 결재자
	
	// has a (1:1 관계)
    private BookmarkVO bookmark;
}
