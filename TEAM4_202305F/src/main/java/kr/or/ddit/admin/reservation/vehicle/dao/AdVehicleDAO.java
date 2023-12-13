package kr.or.ddit.admin.reservation.vehicle.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.VehicleVO;
/**
 * 
 * @author 박민주
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 23.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface AdVehicleDAO {

	/**
	 * 예약코드 가져오기
	 * @return
	 */
	List<CommonVO> selectReserveCdList();
	
	/**
	 * 등록된 차량 목록 조회
	 * @return
	 */
	List<VehicleVO> selectVehicleList();
	
	/**
	 * 특정 차량 조회
	 * @param vhcleCd
	 * @return 차량 한대 정보
	 */
	VehicleVO selectVehicle(String vhcleCd);

	/**
	 * 신규 차량 등록
	 * @param vehicle
	 */
	int insertVehicle(VehicleVO vehicle);

	/**
	 * 차량 상세 정보 수정
	 * @param vehicle
	 */
	int updateVehicle(VehicleVO vehicle);

	/**
	 * 차량 삭제
	 * @param vhcleCd
	 */
	int deleteVehicle(String vhcleCd);
	
	/**
	 * 모든 차량의 모든 예약 내역 조회
	 * @return 
	 */
	List<VehicleVO> selectVehicleReservationList();

	/**
	 * 해당 층수의 다음 회의실 코드
	 * @param selectedStair
	 * @return
	 */
	String selectNextConfroomCode(String selectedStair);

}
