package kr.or.ddit.admin.reservation.confroom.service;

import java.util.List;

import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.ConfRoomVO;

public interface AdConfRoomService {

	/**
	 * 등록된 회의실 전체 목록 조회
	 * @return
	 */
	List<ConfRoomVO> retrieveConRoomList();
	
	/**
	 * 신규 회의실 등록
	 * @param confRoom
	 * @return
	 */
	int createConfRoom(ConfRoomVO confRoom);
	
	/**
	 * 특정 회의실 추가
	 * @param confRoom
	 * @return
	 */
	int modifyConfRoom(ConfRoomVO confRoom);

	/**
	 * 특정 회의실 삭제
	 * @param confRoomCd
	 * @return
	 */
	int removeConfRoom(String confRoomCd);
	
	/**
	 * 회의실 예약 전체 목록 조회
	 * @return
	 */
	List<ConfRoomReservationVO> retrieveConfRoomReservationList();
	
	/**
	 * 특정 회의실 상세 조회
	 * @param confRoomCd
	 * @return
	 */
	ConfRoomVO retrieveConfRoomOne(String confRoomCd);
	
	/**
	 * 특정 회의실 예약 내역 조회
	 * @return
	 */
	List<ConfRoomReservationVO> retrieveConfRoomReservationOne();
	
	/**
	 * 회의실 예약 등록
	 * @param reservation
	 * @return
	 */
	int createConfRoomReservation(ConfRoomReservationVO reservation);
	
	/**
	 * 회의실 예약 수정
	 * @param reservation
	 * @return
	 */
	int modifyConfRoomReservation(ConfRoomReservationVO reservation);
	
	/**
	 * 회의실 예약 삭제
	 * @param confReserveCd
	 * @return
	 */
	int removeConfRoomReservation(String confReserveCd);
	
}
