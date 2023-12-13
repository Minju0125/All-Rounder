package kr.or.ddit.pms.project.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;

/**
 * @author 작성자명
 * @since 2023. 11. 8.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 8.      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface ProjectService {
	
	public List<ProjectVO> selectProjectList(String empCd);

	public ServiceResult modifyProjectStt(ProjectVO proj);
	
	
	
	public String selectEmployeeCode(String proSn); 
	
	
	/**
	 * 프로젝트의 일감의 전체를 카운트 
	 * @param proSn
	 * @return
	 */
	public String selectJACompleteCount(String proSn);
	
	 
	/**
	 * 프로젝트의 완료된 일감을 카운트  
	 * @param proSn
	 * @return
	 */
	public String selectJobCompleteCount(String proSn);
	
	
	public List<ProjectVO> selectCompleteProject();
	
}
