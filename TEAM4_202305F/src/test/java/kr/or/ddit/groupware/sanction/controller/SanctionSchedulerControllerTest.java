package kr.or.ddit.groupware.sanction.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import kr.or.ddit.AbstractRootContextTest;
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
 * 2023. 12. 1.   전수진      최초작성(대결권 스케쥴러처리 테스트)
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
class SanctionSchedulerControllerTest extends AbstractRootContextTest {
	@Inject
	private BookmarkAndProxyDAO dao;
	@Inject
	private SanctionLineDAO lineDAO;

	@Test
	void testModifySanctionByProxy() {
		
		List<SanctionByProxyVO> proxy = dao.selectSanctionProxyList();
		log.info("proxy======================={}",proxy);
		LocalDate today = LocalDate.now();
		for(SanctionByProxyVO vo : proxy) {
			LocalDate endDate = LocalDate.parse(vo.getExtshDate());
			if(today.equals(endDate)) {
				log.info("proxy종료일자일치!!!!!==============={}",vo.getPrxsanctnAlwnc());
				vo.setPrxsanctnYn("N");
				dao.updateSanctionProxy(vo);
				log.info("vo======================={}",vo);
			}
			
		}
	}
	
	@Test
	void modifyChangePrxsanctnCnfer() {
		List<SanctionByProxyVO> proxy = dao.selectSanctionProxyList();
		List<SanctionLineVO> line = lineDAO.selectSanctionLineList();
		log.info("line======================={}",line);
		LocalDate today = LocalDate.now();
		for(SanctionByProxyVO pVo : proxy) {
			LocalDate startDate = LocalDate.parse(pVo.getAlwncDate());
			if(today.equals(startDate)) {
				String alwnc = pVo.getPrxsanctnAlwnc();
				log.info("proxy시작일자일치!!!!!==========부여자{}",alwnc);
				String cnfer = pVo.getPrxsanctnCnfer();
				log.info("proxy시작일자일치!!!!!==========수여자{}",cnfer);
				
				for(SanctionLineVO lVo : line) {
					if(lVo.getSanctner().equals(alwnc)) {
						log.info("proxy시작일자일치!!!!!==========결재자{}",lVo.getSanctner());
						log.info("proxy시작일자일치!!!!!==========실문서번호{}",lVo.getSanctnNo());
						log.info("proxy시작일자일치!!!!!==========라인번호{}",lVo.getSanctnLineNo());
						lVo.setRealSanctner(cnfer);
						lineDAO.updateRealSanctner(lVo);
					}
				}
			}
			
		}
	}

}
