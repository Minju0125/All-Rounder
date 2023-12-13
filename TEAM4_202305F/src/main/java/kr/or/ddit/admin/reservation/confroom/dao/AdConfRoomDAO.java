package kr.or.ddit.admin.reservation.confroom.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.ConfRoomVO;

@Mapper
public interface AdConfRoomDAO {
	
	/* 쿼리 작성 완료*/////////////////////
	//등록된 회의실 전체 목록 조회
	List<ConfRoomVO> selectConfRoomList();

	//신규 회의실 등록
	int insertConfRoom(ConfRoomVO confRoom);
	
	//특정 회의실 상세 조회
	ConfRoomVO selectConfRoomOne(String confRoomCd);
	
	/* 쿼리 작성 예정*//////////////////////

	//특정 회의실 추가
	int updateConfRoom(ConfRoomVO confRoom);

	//특정 회의실 삭제
	int deleteConfRoom(String confRoomCd);

	//회의실 예약 전체 목록 조회
	List<ConfRoomReservationVO> selectConfRoomReservationList();

	//특정 회의실 예약 내역 조회
	List<ConfRoomReservationVO> selectConfRoomReservationOne();

	//회의실 예약 등록
	int insertConfRoomReservation(ConfRoomReservationVO reservation);

	//회의실 예약 수정
	int updateConfRoomReservation(ConfRoomReservationVO reservation);

	//회의실 예약 삭제
	void deleteConfRoomReservation(String confReserveCd);

}