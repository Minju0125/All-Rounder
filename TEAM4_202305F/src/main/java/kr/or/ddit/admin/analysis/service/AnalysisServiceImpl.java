package kr.or.ddit.admin.analysis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.analysis.dao.AnalysisDAO;
import kr.or.ddit.admin.reservation.reservation.dao.AdReservationDAO;
import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since Dec 4, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일          수정자               수정내용
 * --------     --------    ----------------------
 * Dec 4, 2023     송석원       부서 인원 지표분석
 * Dec 5, 2023     송석원       직급별 직원 분포도
 * Dec 5, 2023     박민주	    전년 월별 대비 자원의 사용률
 * 2023. 12. 05.   김보영        올해 입퇴사자, 부서별 근무시간
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisServiceImpl implements AnalysisService {
	
	private final AnalysisDAO analysisDAO;
	private final AdReservationDAO adReservationDAO;

	/**
	 *부서별 인원 지표분석
	 */
	@Override
	public List<EmployeeVO> emphavedept() {
		return analysisDAO.emphavedept();
	}



	/**
	 *직급별 직원 분포도
	 */
	@Override
	public List<EmployeeVO> emphaverank() {
		return analysisDAO.emphaverank();
	}

	/**
	 * 차량의 작년 예약내역
	 */
	@Override
	public List<VehicleReservationVO> retrieveVLastReservationHistory() {
		return adReservationDAO.selectVLastReservationHistory();
	} 
	/**
	 * 차량의 올해 예약내역
	 */
	@Override
	public List<VehicleReservationVO> retrieveVThisReservationHistory() {
		return adReservationDAO.selectVThisReservationHistory();
	} 
	
	/**
	 * 회의실의 작년 예약내역
	 */
	@Override
	public List<ConfRoomReservationVO> retrieveCLastReservationHistory() {
		return adReservationDAO.selectCLastReservationHistory();
	} 
	
	/**
	 * 회의실의 올해 예약내역
	 */
	@Override
	public List<ConfRoomReservationVO> retrieveCThisReservationHistory() {
		return adReservationDAO.selectCThisReservationHistory();
	} 
	
	/**
	 * 입,퇴사자
	 */
	@Override
	public List<ChartVO> retrieveHireCnt() {
		return analysisDAO.selectHireCnt();
	}

	@Override
	public List<ChartVO> retrieveleaveCnt() {
		return analysisDAO.selectLeaveCnt();
	}

	
	/**
	 * 부서별 일 근무시간
	 */
	@Override
	public List<ChartVO> retrieveWorkTime() {
		return analysisDAO.selectWorkTime();
	} 

	
	

}
