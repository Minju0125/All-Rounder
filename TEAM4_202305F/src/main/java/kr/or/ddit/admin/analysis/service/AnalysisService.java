package kr.or.ddit.admin.analysis.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

/**
 * @author 작성자명
 * @since Dec 4, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Dec 4, 2023      송석원       부서 인원 지표분석
 * Dec 5, 2023      송석원       직급별 직원 분포도
 * Dec 5, 2023      박민주	   전년 월별 대비 자원의 사용률
 * 2023. 12. 05.    김보영         올해 입퇴사자 , 부서별 근무시간
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface AnalysisService {
	
	
	/**
	 * 부서별 인원수
	 * @return
	 */
	public List<EmployeeVO> emphavedept();
	
	
	/**
	 * 직급별 직원 분포도 
	 * @return
	 */
	public List<EmployeeVO> emphaverank();


	/**
	 * 차량의 작년 예약내역
	 * 2023.12.05 박민주
	 * @return
	 */
	public List<VehicleReservationVO> retrieveVLastReservationHistory();
	
	
	/**
	 * 차량의 올해 예약내역
	 * 2023.12.05 박민주
	 * @return
	 */
	public List<VehicleReservationVO> retrieveVThisReservationHistory();
	
	/**
	 * 회의실의 작년 예약내역
	 * 2023.12.05 박민주
	 * @return
	 */
	public List<ConfRoomReservationVO> retrieveCLastReservationHistory();
	
	/**
	 * 회의실의 올해 예약내역
	 * 2023.12.05 박민주
	 * @return
	 */
	public List<ConfRoomReservationVO> retrieveCThisReservationHistory();
	

	/**
	 * 올해 입사자 수
	 * @return
	 */
	public List<ChartVO> retrieveHireCnt();



	/**
	 * 올해 퇴사자 수
	 * @return
	 */
	public List<ChartVO> retrieveleaveCnt();



	/**
	 * 부서별 일 근무시간
	 * @return
	 */
	public List<ChartVO> retrieveWorkTime();


}
