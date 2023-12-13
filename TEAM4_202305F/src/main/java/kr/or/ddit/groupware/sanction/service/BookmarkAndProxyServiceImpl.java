package kr.or.ddit.groupware.sanction.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.sanction.dao.BookmarkAndProxyDAO;
import kr.or.ddit.groupware.sanction.dao.SanctionLineDAO;
import kr.or.ddit.vo.groupware.BookmarkDetailVO;
import kr.or.ddit.vo.groupware.BookmarkVO;
import kr.or.ddit.vo.groupware.SanctionByProxyVO;
import kr.or.ddit.vo.groupware.SanctionLineVO;
import lombok.extern.slf4j.Slf4j;

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
 * 2023. 11. 29.  전수진       대결권 Insert 구현 
 * 2023. 11. 30.  전수진       대결권 update 구현 
 * 2023. 12. 04.  전수진       대결권 해제후 결재라인 변경, 즐겨찾기 삭제구현 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
public class BookmarkAndProxyServiceImpl implements BookmarkAndProxyService {
	
	@Inject
	private BookmarkAndProxyDAO dao;
	
	@Inject
	private SanctionLineDAO lineDAO;

	
	@Transactional
	@Override
	public ServiceResult createBookmark(BookmarkVO bookmark) {
		
		// 즐겨찾기 추가
		int cnt = dao.insertBookmark(bookmark);
		ServiceResult result = null;

		if(cnt > 0) {
			List<BookmarkDetailVO> sanctnerList = bookmark.getDetailList();
			if(sanctnerList!=null) {
				int sanctnOrdrCnt = 1;
				
				// 즐겨찾기상세에 즐겨찾기 추가
				for(BookmarkDetailVO vo : sanctnerList) {
					vo.setBkmkNo(bookmark.getBkmkNo());
					vo.setSanctnOrdr(sanctnOrdrCnt++);
					
					dao.insertBookmarkDetail(vo);
				}
			}
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Transactional
	@Override
	public ServiceResult removeBookmark(String bkmkNo) {
		// 즐겨찾기 삭제, 디테일도 같이 삭제
		ServiceResult result = null;
		
		List<BookmarkDetailVO> detailList = dao.selectBookmarkDetailList(bkmkNo);
		if(detailList!=null && detailList.size()>0) {
		
			for(BookmarkDetailVO vo : detailList) {
				int bkmkDetailNo = vo.getBkmkDetailNo();
				dao.deleteBookmarkDetail(bkmkDetailNo);
			}
			
			int cnt = dao.deleteBookmark(bkmkNo);
			if(cnt > 0) {
				result = ServiceResult.OK;
			} else {
				result = ServiceResult.FAIL;
			}
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<BookmarkVO> retrieveBookmarkList(String bkmkOwner) {
		// 소유자의 즐겨찾기 리스트 출력
		return dao.selectBookmarkList(bkmkOwner);
	}

	@Override
	public List<BookmarkDetailVO> retrieveBookmarkDetailList(String bkmkNo) {
		// 즐겨찾기 번호로 결재자리스트 조회
		return dao.selectBookmarkDetailList(bkmkNo);
	}

	@Override
	public ServiceResult createSanctionProxy(SanctionByProxyVO proxy) {
		// 대결권 설정 구현
		proxy.setPrxsanctnYn("Y");

		SanctionByProxyVO savedProxy =  dao.selectPrxsanctnAlwncSetting(proxy);
		
		ServiceResult result = null;
		//대결권 등록
		if(savedProxy==null) {
			int cnt = dao.insertSanctionProxy(proxy);
			if(cnt > 0) {
				result = ServiceResult.OK;
			} else {
				result = ServiceResult.FAIL;
			}
		} else {
			result = ServiceResult.PKDUPLICATED;
		}
		
		return result;
	}

	@Override
	public SanctionByProxyVO retrieveProxyCheck(String prxsanctnAlwnc) {
		// 대결권 설정한 정보가 있는지 여부 확인용! 
		// 오늘날짜가 대결권설정기간에 들어있으면 null반환, 없을 경우 vo반환
		return dao.selectProxyCheck(prxsanctnAlwnc);
	}

	@Override
	public ServiceResult modifySanctionProxy(SanctionByProxyVO proxy) {
		// 대결권 해제
		proxy.setPrxsanctnYn("N");
		ServiceResult result = null;
		
		int cnt = dao.updateSanctionProxy(proxy);
//		결재라인되돌리기
		
		log.info("proxy============={}",proxy);
//		SanctionByProxyVO(prxsanctnNo=PRX00008, prxsanctnAlwnc=E220901003, 
//				PRXSANCTNALWNCNM=전수진, PRXSANCTNALWNCRANKNM=대리, 
//				PRXSANCTNALWNCDEPTNAME=개발부, PRXSANCTNCNFER=E210101002, 
//				PRXSANCTNCNFERNM=이기웅, PRXSANCTNCNFERRANKNM=대리, 
//				PRXSANCTNCNFERDEPTNAME=생산부, ALWNCDATE=2023-12-05, 
//				EXTSHDATE=2023-12-13, ALWNCREASON=TEST, PRXSANCTNYN=N,
//				EMP=NULL)
		
		SanctionByProxyVO proxyInfo = dao.selectProxyOne(proxy.getPrxsanctnNo());
		log.info("proxyInfo============={}",proxyInfo);
		List<SanctionLineVO> line = lineDAO.selectSanctionLineList();
		for(SanctionLineVO lVo : line) {
			if(lVo.getRealSanctner().equals(proxyInfo.getPrxsanctnCnfer())){
				lVo.setRealSanctner(proxyInfo.getPrxsanctnAlwnc());
				lineDAO.updateRealSanctner(lVo);
			}
		}
		
		if(cnt > 0) {
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		
		return result;
	}

	@Override
	public SanctionByProxyVO retrieveProxyRecivedCheck(String prxsanctnCnfer) {
		// 대결자수여자 정보확인용!
		// 로그인한 사원이 수여자로 선택되지 않았을경우 Null반환
		return dao.selectProxyRecivedCheck(prxsanctnCnfer);
	}

}
