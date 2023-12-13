package kr.or.ddit.pms.workstatus.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface WorkStatusDAO {

	/**
	 * 검색조건에 맞는 내 일감수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<ProjectVO>paging);
	
	
	/**
	 * 검색조건에 맞는 업무현황의 내가 담당하고 있는일감의 총 조회 
	 * @return
	 */
	public List<ProjectVO> selectWorkStatusList(PaginationInfo<ProjectVO>paging);
	
	
	
	
}
