package kr.or.ddit.admin.analysis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 작성자명
 * @since Dec 4, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일         수정자               수정내용
 * --------     --------    ----------------------
 * Dec 4, 2023      송석원      부서지표분석
 * Dec 5, 2023      송석원      직급별 직원 분포도
 * Dec 5, 2023      박민주	    전년 월별 대비 자원의 사용률
 * 2023. 12. 05.    김보영      올해 입퇴사자 , 부서별 근무시간
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper 
public interface AnalysisDAO {
	  
	
	/**
	 * 부서별 인원 지표분석
	 * @return 
	 */
	public List<EmployeeVO> emphavedept();
	
	
	
	/**
	 * 직급별 직원 분포도
	 * @return
	 */
	public List<EmployeeVO> emphaverank();



	/**
	 * 올해 입사자 수
	 * @return
	 */
	public List<ChartVO> selectHireCnt();



	/**
	 * 올해 퇴사자 수
	 * @return
	 */
	public List<ChartVO> selectLeaveCnt();



	/**
	 * 부서별 일 근무시간
	 * @return
	 */
	public List<ChartVO> selectWorkTime();
	

}
