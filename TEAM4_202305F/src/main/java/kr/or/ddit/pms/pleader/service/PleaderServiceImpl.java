package kr.or.ddit.pms.pleader.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.pms.pleader.dao.PleaderDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Nov 29, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 29, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PleaderServiceImpl implements PleaderService{
	
	private final PleaderDAO pleaderDAO;

	/**
	 *프로젝트의 전체 일감들 개수 조회
	 */
	@Override
	public PjobVO pjobAllCount(String proSn) {
		return pleaderDAO.pjobAllCount(proSn);
	}
 
	/**
	 *프로젝트내 팀원이 맡은 일감의 갯수 조회 
	 */ 
	@Override
	public List<PjobVO> pjobChargerCount(String proSn) {
		return pleaderDAO.pjobChargerCount(proSn);  
	}
  
	@Override     
	public List<PjobVO> retrieveLeaderPmember(PaginationInfo<PjobVO> paging) {
		int totalRecord = pleaderDAO.selectTotalRecord(paging); 
		paging.setTotalRecord(totalRecord);
		List<PjobVO> dataList = pleaderDAO.selectLeaderPmember(paging);
		paging.setDataList(dataList);
		
		return dataList;  
	}

}
