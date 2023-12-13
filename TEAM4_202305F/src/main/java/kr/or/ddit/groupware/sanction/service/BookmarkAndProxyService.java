package kr.or.ddit.groupware.sanction.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.groupware.BookmarkDetailVO;
import kr.or.ddit.vo.groupware.BookmarkVO;
import kr.or.ddit.vo.groupware.SanctionByProxyVO;

/**
 * @author 전수진
 * @since 2023. 11. 17.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 17.  전수진       최초작성
 * 2023. 11. 28.  전수진       대결권 설정 구현
 * 2023. 12. 04.  전수진       즐겨찾기 삭제 구현
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface BookmarkAndProxyService {
	
	/**
	 * 소유자의 즐겨찾기 리스트를 출력
	 * @return
	 */
	public List<BookmarkVO> retrieveBookmarkList(String bkmkOwner);
	
	/**
	 * 즐겨찾기 번호로 결재자리스트 조회
	 * @param bkmkNo
	 * @return
	 */
	public List<BookmarkDetailVO> retrieveBookmarkDetailList(String bkmkNo);
	
	
	/**
	 * 즐겨찾기 등록
	 * @param bookmark
	 * @return
	 */
	public ServiceResult createBookmark(BookmarkVO bookmark);
	
	/**
	 * 즐겨찾기 삭제
	 * @param bkmkNo
	 * @return
	 */
	public ServiceResult removeBookmark(String bkmkNo);
	
	/****************************************대결권관련******************************************/
	
	/**
	 * 대결권 설정
	 * @param proxy
	 * @return
	 */
	public ServiceResult createSanctionProxy(SanctionByProxyVO proxy);
	
	
	/**
	 * 대결권 설정가능 여부를 확인
	 * @param prxsanctnAlwnc
	 * @return 대결권설정이 되어있지 않으면 null반환
	 */
	public SanctionByProxyVO retrieveProxyCheck(String prxsanctnAlwnc);
	
	/**
	 * 대결권 수여자의 대결권 설정확인
	 * @param prxsanctnCnfer
	 * @return
	 */
	public SanctionByProxyVO retrieveProxyRecivedCheck(String prxsanctnCnfer);
	
	/**
	 * 대결권 해제
	 * @param proxy
	 * @return
	 */
	public ServiceResult modifySanctionProxy(SanctionByProxyVO proxy);
	

	
}
