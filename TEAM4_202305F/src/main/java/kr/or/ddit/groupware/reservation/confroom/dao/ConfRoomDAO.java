package kr.or.ddit.groupware.reservation.confroom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

@Mapper
public interface ConfRoomDAO {
	
	//전체 회의실 예약 목록 조회
	List<ConfRoomReservationVO> selectConfRoomReservationList();
	
	//회의실 예약 등록
	int insertConfRoomReservation(ConfRoomReservationVO reservation);
	
	//회의실 예약 수정
	int updateConfRoomReervation(ConfRoomReservationVO reservation);
	
	//회의실 예약 삭제
	int deleteConfRoomReservation(String reservationCd);
	
	//공통코드의 회의실 사용시간 코드 목록 조회
	List<CommonVO> selectCommonConfTimeCdList();

	//사원한명의 회의실 예약 내역 조회
	List<ConfRoomReservationVO> selectMyConfRoomReservationList(PaginationInfo<ConfRoomReservationVO> paging);

	int selectTotalRecord(PaginationInfo<ConfRoomReservationVO> paging);
	
	/**
	 * 특정 회의실의 오늘 예약 목록 조회
	 * @param confRoomCd
	 * @return
	 */
	List<ConfRoomReservationVO> selectTodayConfRoomReservationList(String confRoomCd);

	String selectReservationPasswordConfRoom(String reserveCd);
	
	
}
