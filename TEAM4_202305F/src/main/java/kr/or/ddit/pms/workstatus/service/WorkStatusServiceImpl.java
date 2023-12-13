package kr.or.ddit.pms.workstatus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.pms.workstatus.dao.WorkStatusDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Nov 20, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 20, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WorkStatusServiceImpl implements WorkStatusService{
	
	private final WorkStatusDAO workDAO;
	 
	@Override
	public List<ProjectVO> retrieveWorkStatusList(PaginationInfo<ProjectVO> paging) {
		int totalRecord = workDAO.selectTotalRecord(paging); 
		paging.setTotalRecord(totalRecord);
		List<ProjectVO> dataList = workDAO.selectWorkStatusList(paging);
		paging.setDataList(dataList);
		
		return dataList;
	}

}
