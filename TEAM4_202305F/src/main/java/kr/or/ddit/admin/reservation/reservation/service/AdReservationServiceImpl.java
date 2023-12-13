package kr.or.ddit.admin.reservation.reservation.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.reservation.reservation.dao.AdReservationDAO;
import kr.or.ddit.groupware.reservation.vehicle.dao.VehicleDAO;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

@Service
public class AdReservationServiceImpl implements AdReservationService{

	@Inject
	private VehicleDAO vDao;
	
	@Inject
	private AdReservationDAO adRDao;
	
	@Override
	public List<VehicleReservationVO> retrieveReservationListVhcle() {
		return adRDao.selectReservationListVhcle();
	}

	@Override
	public List<ConfRoomReservationVO> retrieveReservationListConf() {
		return adRDao.selectReservationListConf();
	}

	@Override
	public int removeReservationVhcle(String reservationCd) {
		return vDao.deleteVehicleReservation(reservationCd);
	}
	
	@Override
	public int removeReservationConf(String reservationCd) {
		return adRDao.deleteConfReservation(reservationCd);
	}
}
