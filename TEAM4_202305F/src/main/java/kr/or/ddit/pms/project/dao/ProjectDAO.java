package kr.or.ddit.pms.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
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
 * 2023. 11. 16.      송석원       제약조건추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface ProjectDAO {
	
	/**
	 * 프로젝트 목록 조회  
	 * @return
	 */
	public List<ProjectVO> selectProjectList(String empCd);
	
	

	
	public int updateProjectStt(ProjectVO proj);
	
	
	/**
	 * 프로젝트 리더 사원번호 
	 * @param proSn
	 * @return
	 */
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
	
	
	/**
	 * 미완료된 프로젝트 번호를 출력
	 * @return
	 */
	public List<ProjectVO> selectCompleteProject();
	

}
