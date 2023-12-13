package kr.or.ddit.groupware.reservation.confroom.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.groupware.reservation.confroom.dao.ConfRoomDAO;
import kr.or.ddit.groupware.reservation.vehicle.dao.VehicleDAO;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;

@Service
public class ConfRoomServiceImpl implements ConfRoomService{

	@Inject
	ConfRoomDAO dao;
	
	@Inject
	VehicleDAO vDao;
	
	

	@Override
	public int createConfRoomReservation(ConfRoomReservationVO reservation) {
		return dao.insertConfRoomReservation(reservation);
	}

	@Override
	public int modifyConfRoomReservation(ConfRoomReservationVO reservation) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int removeConfRoomReservation(String reservationCd) {
		return dao.deleteConfRoomReservation(reservationCd);
	}

	@Override
	public List<CommonVO> retrieveCommonConfTimeCdList() {
		return dao.selectCommonConfTimeCdList();
	}

	@Override
	public List<ConfRoomReservationVO> retrieveMyConfRoomReservationList(PaginationInfo<ConfRoomReservationVO> paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<ConfRoomReservationVO> dataList = dao.selectMyConfRoomReservationList(paging);
		paging.setDataList(dataList);
		return dataList;
	}

	@Override
	public List<ConfRoomReservationVO> retrieveConfRoomReservationList() {
		return dao.selectConfRoomReservationList();
	}

	@Override
	public String retrieveReservationPasswordConfRoom(String reserveCd) {
		return dao.selectReservationPasswordConfRoom(reserveCd);
	}

}
