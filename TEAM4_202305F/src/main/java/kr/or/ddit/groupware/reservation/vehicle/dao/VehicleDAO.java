package kr.or.ddit.groupware.reservation.vehicle.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 25.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일              수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 25.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface VehicleDAO {
	
	//전체 예약내역 조회 (타임테이블 출력)
	//내 예약내역 조회
	//차량예약등록
	//차량예약수정
	//차량예약삭제
	
	/**
	 * 예약시간 코드 조회
	 * 차량 예약 및 수정 모달에서 '예약시간' 항목에 데이터 넣기 위함
	 * @return
	 */
	List<CommonVO> selectCommonVReserveTimeCdList();
	
	List<VehicleReservationVO> selectVehicleReservationList();
	
	/**
	 * 로그인한 사원 본인의 예약 내역 조회
	 * @param paging
	 * @return
	 */
	List<VehicleReservationVO> selectMyVehicleReservationList(PaginationInfo<VehicleReservationVO> paging);
	
	/**
	 * 특정 차량의 모든 예약 내역 조회
	 * @param vhcleCd
	 * @return 
	 */
	List<VehicleReservationVO> selectSimpleReservationList(String vhcleCd);
	
	/**
	 * 특정 차량의 특정기간의 예약 내역 조회
	 * @param dateAndCd
	 * @return
	 */
	List<VehicleReservationVO> selectOneVehicleReservationList(Map<String, String> rangeAndCd);
	
	/**
	 * 차량예약
	 * @param reservation
	 * @return
	 */
	int insertVehicleReservation(VehicleReservationVO reservation);
	
	int updateVehicleReservation(VehicleReservationVO reservation);
	
	int deleteVehicleReservation(String vhcleReserveCd);

	int selectTotalRecord(PaginationInfo<VehicleReservationVO> paging);

	/**
	 * 특정 차량의 오늘 차량 예약 목록 조회
	 * @return
	 */
	List<VehicleReservationVO> selectTodayVehicleReservationList(String vhcleCd);

	String selectReservationPasswordVehicle(String reserveCd);
	
}
