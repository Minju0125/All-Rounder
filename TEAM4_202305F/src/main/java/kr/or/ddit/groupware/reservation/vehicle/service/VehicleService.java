package kr.or.ddit.groupware.reservation.vehicle.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

public interface VehicleService {
	List<CommonVO> retrieveCommonVReserveTimeCdList();
	
	List<VehicleReservationVO> retrieveVehicleReservationList();
	
	/**
	 * 로그인한 본인의 차량예약내역 조회
	 * @param PaginationInfo
	 * @return
	 */
	List<VehicleReservationVO> retrieveMyVehicleReservationList(PaginationInfo<VehicleReservationVO> paging);
	
	List<VehicleReservationVO> retrieveSpecificVehicleReservationList(Map<String, String> rangeAndCd);

	/**
	 * 특정 차량의 전체 예약 내역 조회
	 * @param vhcleCd
	 * @return
	 */
	List<VehicleReservationVO> retrieveSimpleReservationList(String vhcleCd);
	
	/**
	 * 차량예약 등록
	 * @param reservation
	 * @return
	 */
	int createReservation(VehicleReservationVO reservation);
	
	/**
	 * 예약내역 삭제
	 * @param vhcleReserveCd
	 * @return
	 */
	int removeReservation(String vhcleReserveCd);

	/**
	 * 특정 예약번호의 비밀번호
	 * @param reserveCd
	 * @return
	 */
	String retrieveReservationPasswordVehicle(String reserveCd);

}
