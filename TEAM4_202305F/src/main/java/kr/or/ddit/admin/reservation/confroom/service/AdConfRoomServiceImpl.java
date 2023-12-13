package kr.or.ddit.admin.reservation.confroom.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.reservation.confroom.dao.AdConfRoomDAO;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.ConfRoomVO;

@Service
public class AdConfRoomServiceImpl implements AdConfRoomService{

	@Inject
	AdConfRoomDAO dao;
	
	@Override
	public List<ConfRoomVO> retrieveConRoomList() {
		return dao.selectConfRoomList();
	}

	@Override
	public int createConfRoom(ConfRoomVO confRoom) {
		return dao.insertConfRoom(confRoom);
	}

	@Override
	public int modifyConfRoom(ConfRoomVO confRoom) {
		return dao.updateConfRoom(confRoom);
	}

	@Override
	public int removeConfRoom(String confRoomCd) {
		return dao.deleteConfRoom(confRoomCd);
	}

	@Override
	public List<ConfRoomReservationVO> retrieveConfRoomReservationList() {
		dao.selectConfRoomReservationList();
		return null;
	}

	@Override
	public List<ConfRoomReservationVO> retrieveConfRoomReservationOne() {
		dao.selectConfRoomReservationOne();
		return null;
	}

	@Override
	public int createConfRoomReservation(ConfRoomReservationVO reservation) {
		dao.insertConfRoomReservation(reservation);
		return 0;
	}

	@Override
	public int modifyConfRoomReservation(ConfRoomReservationVO reservation) {
		dao.updateConfRoomReservation(reservation);
		return 0;
	}

	@Override
	public int removeConfRoomReservation(String confReserveCd) {
		dao.deleteConfRoomReservation(confReserveCd);
		return 0;
	}

	@Override
	public ConfRoomVO retrieveConfRoomOne(String confRoomCd) {
		return dao.selectConfRoomOne(confRoomCd);
	}
}
