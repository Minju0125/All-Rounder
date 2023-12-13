package kr.or.ddit.admin.reservation.reservation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

@Mapper
public interface AdReservationDAO {
	List<VehicleReservationVO> selectReservationListVhcle();
	
	List<ConfRoomReservationVO> selectReservationListConf();
	
	int deleteConfReservation(String reservationCd);

	//작년 차량 예약 내역
	List<VehicleReservationVO> selectVLastReservationHistory();
	
	//올해 차량 예약 내역
	List<VehicleReservationVO> selectVThisReservationHistory();
	
	//작년 회의실 예약 내역
	List<ConfRoomReservationVO> selectCLastReservationHistory();
	
	//올해 회의실 예약 내역
	List<ConfRoomReservationVO> selectCThisReservationHistory();
}
