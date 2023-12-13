package kr.or.ddit.groupware.reservation.confroom.service;

import java.util.List;

import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;

/**
 * 
 * @author 박민주
 * @since 2023. 11. 29.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 29.    박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface ConfRoomService{
	
	//전체 회의실 예약 목록 조회
	List<ConfRoomReservationVO> retrieveConfRoomReservationList();
	
	//회의실 예약 등록
	int createConfRoomReservation(ConfRoomReservationVO reservation);
	
	//회의실 예약 수정
	int modifyConfRoomReservation(ConfRoomReservationVO reservation);
	
	//회의실 예약 삭제
	int removeConfRoomReservation(String reservationCd);
	
	//공통코드의 회의실 사용시간 코드 목록 조회
	List<CommonVO> retrieveCommonConfTimeCdList();

	//사원의 예약내역 (페이징)
	List<ConfRoomReservationVO> retrieveMyConfRoomReservationList(PaginationInfo<ConfRoomReservationVO> paging);

	/**
	 * 특정 예약 번호의 비밀번호
	 * @param reserveCd
	 * @return
	 */
	String retrieveReservationPasswordConfRoom(String reserveCd);

}
