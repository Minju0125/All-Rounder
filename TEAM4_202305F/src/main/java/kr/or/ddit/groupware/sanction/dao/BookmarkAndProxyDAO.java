package kr.or.ddit.groupware.sanction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
 * 2023. 11. 18.  전수진       즐겨찾기 리스트, 상세보기 구현 
 * 2023. 11. 28.  전수진       대결권 설정구현
 * 2023. 11. 28.  전수진       대결권 해제구현
 * 2023. 12. 01.  전수진       대결권 스케쥴러관련 list 출력
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface BookmarkAndProxyDAO {
	
	/**
	 * 소유자의 즐겨찾기 리스트를 출력
	 * @return
	 */
	public List<BookmarkVO> selectBookmarkList(String bkmkOwner);
	
	/**
	 * 즐겨찾기 등록
	 * @param bookmark
	 * @return
	 */
	public int insertBookmark(BookmarkVO bookmark);
	
	/**
	 * 즐겨찾기 삭제
	 * @param bkmkNo
	 * @return
	 */
	public int deleteBookmark(String bkmkNo);
	
	/**
	 * 즐겨찾기 번호로 결재자리스트 조회
	 * @param bkmkNo
	 * @return
	 */
	public List<BookmarkDetailVO> selectBookmarkDetailList(String bkmkNo);
	
	/**
	 * 즐겨찾기 결재자리스트 등록
	 * @param detail
	 * @return
	 */
	public int insertBookmarkDetail(BookmarkDetailVO detail);
	
	/**
	 * 즐겨찾기리스트 삭제
	 * @param datail
	 * @return
	 */
	public int deleteBookmarkDetail(int bkmkDetailNo);
	
/****************************************대결권관련******************************************/
	/**
	 * 대결권 설정
	 * @param proxy
	 * @return
	 */
	public int insertSanctionProxy(SanctionByProxyVO proxy);
	
	/**
	 * 대결권 부여자의 대결권 설정확인
	 * @param proxy 
	 * @return
	 */
	public SanctionByProxyVO selectPrxsanctnAlwncSetting(SanctionByProxyVO proxy);
	
	
	/**
	 * 대결권 수여자의 대결권 설정확인
	 * @param prxsanctnCnfer
	 * @return
	 */
	public SanctionByProxyVO selectProxyRecivedCheck(String prxsanctnCnfer);
	
	
	/**
	 * 대결권 설정가능여부 체크
	 * @param proxy
	 * @return
	 */
	public SanctionByProxyVO selectProxyCheck(String prxsanctnAlwnc);
	
	/**
	 * 대결권 해제
	 * @param proxy
	 * @return
	 */
	public int updateSanctionProxy(SanctionByProxyVO proxy);
	
	/**
	 * 대결권번호로 대결권정보 확인
	 * @param prxsanctnNo
	 * @return
	 */
	public SanctionByProxyVO selectProxyOne(String prxsanctnNo);
	
/***************************************대결권 Scheduler 관련***********************************/
	
	/**
	 * 업데이트해야하는 대결권리스트를 출력
	 * @return
	 */
	public List<SanctionByProxyVO> selectSanctionProxyList();
	

}
