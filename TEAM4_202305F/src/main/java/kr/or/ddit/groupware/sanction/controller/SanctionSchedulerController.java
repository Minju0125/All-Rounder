package kr.or.ddit.groupware.sanction.controller;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import kr.or.ddit.groupware.sanction.dao.BookmarkAndProxyDAO;
import kr.or.ddit.groupware.sanction.dao.SanctionLineDAO;
import kr.or.ddit.vo.groupware.SanctionByProxyVO;
import kr.or.ddit.vo.groupware.SanctionLineVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 12. 1.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 1.   전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Configuration
@EnableScheduling
public class SanctionSchedulerController {
	
	@Inject
	private BookmarkAndProxyDAO dao;
	@Inject
	private SanctionLineDAO lineDAO;
	
	@Scheduled(cron = "0 0 10 * * *") // 매일 10시 0분 0초에 실행
	public void modifySanctionByProxy() {
		
//		상황1. 대결권소멸기간이 도래했을 경우 대결권사용여부를 Y → N
//		   1) proxyList를 불러오는작업
		List<SanctionByProxyVO> proxy = dao.selectSanctionProxyList();
//		   2) 오늘날짜와 종료일자를 비교하는 작업
		LocalDate today = LocalDate.now();
		for(SanctionByProxyVO vo : proxy) {
			LocalDate endDate = LocalDate.parse(vo.getExtshDate());
//		   3) 만약 일치한다면, update 실행하는 작업, 결재라인도 되돌리기
			if(today.equals(endDate)) {
				vo.setPrxsanctnYn("N");
				dao.updateSanctionProxy(vo);
				
				SanctionByProxyVO proxyInfo = dao.selectProxyOne(vo.getPrxsanctnNo());
				List<SanctionLineVO> line = lineDAO.selectSanctionLineList();
				for(SanctionLineVO lVo : line) {
					if(lVo.getRealSanctner().equals(proxyInfo.getPrxsanctnCnfer())){
						lVo.setRealSanctner(proxyInfo.getPrxsanctnAlwnc());
						lineDAO.updateRealSanctner(lVo);
					}
				}
			}
		}
		log.info("---------------->대결권 소멸 업데이트 완료<------------------");
	}
	
	@Scheduled(cron = "0 0 10 * * *") // 매일 10시 0분 0초에 실행
	public void modifyChangePrxsanctnCnfer() {
//		상황2. 대결권설정기간이 시작되었을때 결재문서가 있을 경우 결재자 → 실결재자
//		   1) proxyList와 SanctionLineList를 불러오는작업
		List<SanctionByProxyVO> proxy = dao.selectSanctionProxyList();
		List<SanctionLineVO> line = lineDAO.selectSanctionLineList();
		LocalDate today = LocalDate.now();
		for(SanctionByProxyVO pVo : proxy) {
			LocalDate startDate = LocalDate.parse(pVo.getAlwncDate());
//		   2) 오늘날짜와 시작일자를 비교하는 작업
			if(today.equals(startDate)) {
				String alwnc = pVo.getPrxsanctnAlwnc();
				String cnfer = pVo.getPrxsanctnCnfer();
				
//		   3) 미결처리된 결재라인리스트를 가져와서 날짜가 일치하면, update 실행하는 작업
				for(SanctionLineVO lVo : line) {
					if(lVo.getSanctner().equals(alwnc)) {
						lVo.setRealSanctner(cnfer);
						lineDAO.updateRealSanctner(lVo);
					}
				}
			}
			
		}		
		log.info("---------------->실결재자 업데이트 완료<------------------");
	}

}
