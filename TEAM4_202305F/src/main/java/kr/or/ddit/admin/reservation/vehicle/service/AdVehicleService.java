package kr.or.ddit.admin.reservation.vehicle.service;

import java.util.List;

import kr.or.ddit.vo.groupware.VehicleReservationVO;
import kr.or.ddit.vo.groupware.VehicleVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 23.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface AdVehicleService {

	/**
	 * 차량 목록 조회
	 * @return 
	 */
	public List<VehicleVO> retrieveVehiclelist();
	
	/**
	 * 차량 하나에 대한 정보 조회
	 * @param vhcleCd
	 * @return
	 */
	public VehicleVO retreiveVehicle(String vhcleCd);

	/**
	 * 신규 차량 등록
	 * @param vehicle
	 */
	public int createVehicle(VehicleVO vehicle);
	
	/**
	 * 차량 상세 정보 수정
	 * @param vehicle
	 */
	public int modifyVehicle(VehicleVO vehicle);

	/**
	 * 차량 삭제
	 * @param vhcleCd
	 */
	public int removeVehicle(String vhcleCd);
	
	/**
	 * 모든 차량의 모든 예약 내역 조회
	 * @return
	 */
	public List<VehicleVO> retrieveVehicleReservationList();

	/**
	 * 해당 층수의 다음 회의실 코드
	 * @param selectedStair
	 * @return
	 */
	public String retrieveNextConfroomCode(String selectedStair);
	
}