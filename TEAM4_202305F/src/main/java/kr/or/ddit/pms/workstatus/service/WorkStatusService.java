package kr.or.ddit.pms.workstatus.service;

import java.util.List;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;

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
public interface WorkStatusService {
	
	/**
	 * 검색조건에 맞는 내 전체일감 리스트 조회(검색 및 페이징 지원)
	 * @param paging
	 * @return
	 */  
	public List<ProjectVO> retrieveWorkStatusList(PaginationInfo<ProjectVO>paging); 

}
