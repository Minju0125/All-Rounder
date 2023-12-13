package kr.or.ddit.groupware.sanction.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.common.exception.BoardNotFoundException;
import kr.or.ddit.groupware.sanction.dao.BookmarkAndProxyDAO;
import kr.or.ddit.groupware.sanction.dao.SanctionAttachDAO;
import kr.or.ddit.groupware.sanction.dao.SanctionDAO;
import kr.or.ddit.groupware.sanction.dao.SanctionLineDAO;
import kr.or.ddit.vo.PaginationInfo;
//import kr.or.ddit.vo.ProdVO;
import kr.or.ddit.vo.groupware.SanctionAttachVO;
import kr.or.ddit.vo.groupware.SanctionByProxyVO;
import kr.or.ddit.vo.groupware.SanctionLineVO;
import kr.or.ddit.vo.groupware.SanctionVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 16.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 16.  전수진       최초작성
 * 2023. 11. 21.  전수진       결재문서 첨부파일 관리, 결재라인 추가
 * 2023. 11. 24.  전수진       결재문서 list 구현 추가
 * 2023. 11. 25.  전수진       결재문서 결재진행시 결재라인 update
 * 2023. 11. 27.  전수진       결재문서 기안자 서명이미지 추가, 결재대기문서 list 조회
 * 2023. 11. 29.  전수진       수신문서 list, 부서문서 list 조회
 * 2023. 12. 08.  전수진       관리자 list 조회
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SanctionServiceImpl implements SanctionService {
	
	private final SanctionDAO dao;
	private final SanctionAttachDAO attachDAO;
	private final SanctionLineDAO lineDAO;
	private final BookmarkAndProxyDAO proxyDAO;
	
	@Value("#{appInfo.sanctionFiles}")
	private Resource sanctionFiles;
	
	private void processSanctionFiles(SanctionVO sanctionVO) {
		List<SanctionAttachVO> attachList = sanctionVO.getAttachList();
		if(attachList!=null) {
			attachList.forEach((al)->{
				try {
					al.setSanctnNo(sanctionVO.getSanctnNo());
					attachDAO.insertSanctionAttach(al);
					System.out.println(sanctionFiles.getFile());
					al.saveTo(sanctionFiles.getFile());
				} catch(IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	} 

	@Override
	public List<SanctionVO> retrieveSanctionList(PaginationInfo<SanctionVO> paging) {
		// 검색조건에 맞는 결재문서 리스트 조회(기안자)
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);

		List<SanctionVO> dataList = dao.selectSanctionList(paging);

		paging.setDataList(dataList);
		return dataList;
	}

	@Override
	public SanctionVO retrieveSanction(String sanctnNo) {
		// 결재문서 상세 조회
		SanctionVO sanction = dao.selectSanction(sanctnNo);
		if (sanction==null) 
			throw new BoardNotFoundException(HttpStatus.NOT_FOUND, String.format("%s 해당 게시글이 없음", sanctnNo));
		
		return sanction;
	}

	@Transactional
	@Override
	public ServiceResult createSanction(SanctionVO sanctionVO) {
		// 결재문서 등록(상신) 
		int cnt  = dao.insertSanction(sanctionVO);
		ServiceResult result = null;
		if(cnt > 0) {
			// 결재첨부파일 등록
			processSanctionFiles(sanctionVO);
			
			// 결재라인추가
			List<SanctionLineVO> lineList = sanctionVO.getLineList();
			if(lineList!=null) {
				int size = lineList.size();
				for(SanctionLineVO vo : lineList) {
					vo.setSanctnlineSttus("I");
					vo.setSanctnerEndyn("N");
					if(vo.getSanctnOrdr()==size) {
						vo.setSanctnerEndyn("Y");
					}
					vo.setSanctnNo(sanctionVO.getSanctnNo());
					SanctionByProxyVO proxy = proxyDAO.selectProxyCheck(vo.getSanctner());
					log.info("proxy확인================{}",proxy);
//					proxy확인================SanctionByProxyVO(prxsanctnNo=PRX00001, prxsanctnAlwnc=E220321002, 
//					prxsanctnAlwncNm=null, prxsanctnAlwncRankNm=null, prxsanctnAlwncDeptName=null, 
//					prxsanctnCnfer=E231120030, prxsanctnCnferNm=null, prxsanctnCnferRankNm=null, 
//					prxsanctnCnferDeptName=null, alwncDate=2023-11-29, extshDate=2023-12-02, 
//					alwncReason=연차로 인한 대결권설정, prxsanctnYn=Y, emp=null)
					
					if(proxy==null) {
						vo.setRealSanctner(vo.getSanctner());	// 실결재자 등록 추가
					} else {
						LocalDate today = LocalDate.now();
						LocalDate startDate = LocalDate.parse(proxy.getAlwncDate());
						LocalDate endDate = LocalDate.parse(proxy.getExtshDate()).plusDays(1);
						
						log.info("today확인================{}",today);
						log.info("startDate확인================{}",startDate);
						log.info("endDate확인================{}",endDate);
						if(!today.isBefore(startDate) && today.isBefore(endDate)) {
							// 기간안에 해당하면 대결권수여자를 실결재자로 추가
							vo.setRealSanctner(proxy.getPrxsanctnCnfer());
						} else {
							vo.setRealSanctner(vo.getSanctner());	// 실결재자 등록 추가
						}
					}
					
//					결재자확인================{}E220101002
					log.info("결재자확인================{}",vo.getSanctner());
					
					log.info("vo : "+vo);
					lineDAO.insertSanctionLine(vo);
					log.info("vo : "+vo);
				}
			}
			
			// 서명이미지 업데이트
			Map<String, String> param = new HashMap<String, String>();
			param.put("empCd", sanctionVO.getDrafter());
			param.put("sanctnNo", sanctionVO.getSanctnNo());
			dao.updateDrafterSignImg(param);
			
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Transactional
	@Override
	public ServiceResult modifySanction(SanctionVO sanctionVO) {
		// 결재문서 수정
		ServiceResult result = null;
		SanctionVO sanction = dao.selectSanction(sanctionVO.getSanctnNo());

		if(sanctionVO.getSanctnSttus().equals("0") || sanctionVO.getSanctnSttus().equals("1")) {
			int cnt  = dao.updateSanction(sanctionVO);
			if(cnt>0) {
				List<SanctionAttachVO> attachList = sanctionVO.getAttachList();
				if(attachList!=null && attachList.size() > 0) {
					processDeleteAttach(sanctionVO.getSanctnNo());
					// 결재첨부파일 처리
					processSanctionFiles(sanctionVO);
				}
				
				List<SanctionLineVO> lineList = sanctionVO.getLineList();
				if(lineList!=null && lineList.size()>0) {
					// 결재라인 삭제
					List<SanctionLineVO> savedList = sanction.getLineList();
					for(SanctionLineVO vo : savedList) {
						String sanctnLineNo  = vo.getSanctnLineNo();
						log.info("sanctnLineNo",sanctnLineNo);
						lineDAO.deleteSanctionLine(sanctnLineNo);
					}
					// 결재라인 생성
					int size = lineList.size();
					for(SanctionLineVO vo : lineList) {
						vo.setSanctnlineSttus("I");
						
						vo.setSanctnerEndyn("N");
						if(vo.getSanctnOrdr()==size) {
							vo.setSanctnerEndyn("Y");
						}
						
						vo.setSanctnNo(sanctionVO.getSanctnNo());
						vo.setRealSanctner(vo.getSanctner());	// 실결재자 등록 추가
						log.info("vo : "+vo);
						lineDAO.insertSanctionLine(vo);
					}
				}
				result = ServiceResult.OK;
			} else {
				result = ServiceResult.FAIL;
			}
		} else {
			result = ServiceResult.CANNOTPROCEED;
		} 
		return result;
	}


	@Override
	public SanctionAttachVO retrieveSanctionAttach(int attachNo) {
		// 결재문서 첨부파일 조회
		SanctionAttachVO attach = attachDAO.selectSanctionAttach(attachNo);
		if(attach==null)
			throw new BoardNotFoundException(HttpStatus.NOT_FOUND, String.format("%s 해당 파일이 없음", attachNo));
		
		return attach;
	}
	
	private void processDeleteAttach(String sanctnNo) {
		SanctionVO savedSanction = dao.selectSanction(sanctnNo);
		if(savedSanction != null && savedSanction.getAttachList()!=null) {
			savedSanction.getAttachList().forEach((al)->{
				log.info("savedSanction al{}",al);
				if(al.getAttachSaveNm()!=null) {
					String saveName = al.getAttachSaveNm();
					attachDAO.deleteBoardAttach(al.getAttachNo());
					try {
						FileUtils.deleteQuietly(new File(sanctionFiles.getFile(), saveName));
					} catch(IOException e) {
						throw new RuntimeException(e);
					}
				}
			});
		} 
	}

	@Transactional
	@Override
	public ServiceResult removeSanction(String sanctnNo) {
		// 결재문서 삭제
		ServiceResult result = null;
		SanctionVO sanctionVO = dao.selectSanction(sanctnNo);
		log.info("sanctionVO.getSanctnSttus=============",sanctionVO.getSanctnSttus());
		if(sanctionVO.getSanctnSttus().equals("0") || sanctionVO.getSanctnSttus().equals("1")) {
			
			//결재라인 삭제
			List<SanctionLineVO> lineList = sanctionVO.getLineList();
			if(lineList!=null) {
				for(SanctionLineVO vo : lineList) {
					String sanctnLineNo  = vo.getSanctnLineNo();
					lineDAO.deleteSanctionLine(sanctnLineNo);
				}
			}
			// 첨부파일 삭제
			processDeleteAttach(sanctionVO.getSanctnNo());
			
			int cnt  = dao.deleteSanction(sanctionVO);
			if(cnt>0) {
				result = ServiceResult.OK;
			} else {
				result = ServiceResult.FAIL;
			}
		} else {
			result = ServiceResult.CANNOTPROCEED;
		} 
		return result;
	}

	@Transactional
	@Override
	public ServiceResult modifySanctionLine(SanctionLineVO sanctionline) {

		// 결재자랑 결재문서번호를 가져와서 라인정보를 조회 
		// 라인번호와 실결재자 셋팅
		SanctionLineVO lineVO =  lineDAO.selectSanctionLine(sanctionline);
		sanctionline.setSanctnLineNo(lineVO.getSanctnLineNo());
		sanctionline.setSanctnerEndyn(lineVO.getSanctnerEndyn());
//		서명이미지 처리
		Map<String, String> param = new HashMap<String, String>();
		param.put("empCd", lineVO.getSanctner());
		param.put("sanctnLineNo", lineVO.getSanctnLineNo());
		log.info("sanctnMap==========={}",param);
		
		log.info("ServicelineVO=============={}",lineVO);
		
		log.info("Servicesanctionline=============={}",sanctionline);
		
		
		String sanctnNo = sanctionline.getSanctnNo();
		SanctionVO sanction = dao.selectSanction(sanctnNo);
		
		ServiceResult result = null;

		int cnt = lineDAO.updateSanctionLine(sanctionline);
		
		
		if(cnt > 0) {
//		결재라인VO에서 문서번호를 가져와서 결재문서VO를 조회한다음 sanctnSttus를 수정
			String lineSttus = sanctionline.getSanctnlineSttus();
			
			if(lineSttus.equals("R")) {
				sanction.setSanctnSttus("3");
			} 
			
			if(lineSttus.equals("C") || lineSttus.equals("P")) {
				
			    if(sanctionline.getSanctnerEndyn().equals("Y")) {
			        sanction.setSanctnSttus("4");
			    } else {
			        sanction.setSanctnSttus("2");
			    }
			}
			log.info("Servicesanction=============={}",sanction);
			
			dao.updateSanctionSttus(sanction);
			lineDAO.updateSingImg(param);	//서명이미지 저장용
			
			
			result = ServiceResult.OK;
			
		} else {
			result = ServiceResult.FAIL;
		} 
		return result;
	}

	@Override
	public List<SanctionVO> retrieveSanctnerSanctionList(PaginationInfo<SanctionVO> paging) {
		// 검색조건에 맞는 결재문서 리스트 조회(결재자)
		int totalRecord = dao.selectSanctnerTotalRecord(paging);
		paging.setTotalRecord(totalRecord);

		List<SanctionVO> dataList = dao.selectSanctnerSanctionList(paging);

		paging.setDataList(dataList);
		return dataList;
	}

	@Override
	public List<SanctionVO> retrieveRcyerSanctionList(PaginationInfo<SanctionVO> paging) {
		// 검색조건에 맞는 수신문서 리스트 조회(수신자)
		int totalRecord = dao.selectRcyerTotalRecord(paging);
		paging.setTotalRecord(totalRecord);

		List<SanctionVO> dataList = dao.selectRcyerSanctionList(paging);

		paging.setDataList(dataList);
		return dataList;
	}
	
	@Override
	public List<SanctionVO> retrieveDeptSanctionList(PaginationInfo<SanctionVO> paging) {
		// 검색조건에 맞는 부서문서 리스트 조회
		int totalRecord = dao.selectDeptTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		
		List<SanctionVO> dataList = dao.selectDeptSanctionList(paging);
		
		paging.setDataList(dataList);
		return dataList;
	}

	@Override
	public List<SanctionVO> retrieveAdminSanctionList(PaginationInfo<SanctionVO> paging) {
		int totalRecord = dao.selectAdminTotalRecord(paging);
		paging.setTotalRecord(totalRecord);

		List<SanctionVO> dataList = dao.selectAdminSanctionList(paging);

		paging.setDataList(dataList);
		return dataList;
	}

}
