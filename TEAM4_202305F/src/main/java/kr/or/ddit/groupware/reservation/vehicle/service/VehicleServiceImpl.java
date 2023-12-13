package kr.or.ddit.groupware.reservation.vehicle.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.ddit.groupware.reservation.vehicle.dao.VehicleDAO;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.ReservationTimeVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;

@Service
public class VehicleServiceImpl implements VehicleService {

	@Inject
	private VehicleDAO dao;

	@Override
	public List<CommonVO> retrieveCommonVReserveTimeCdList() {
		List<CommonVO> timeCdList = dao.selectCommonVReserveTimeCdList();
		return timeCdList;
	}

	@Override
	public List<VehicleReservationVO> retrieveVehicleReservationList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VehicleReservationVO> retrieveMyVehicleReservationList(PaginationInfo<VehicleReservationVO> paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<VehicleReservationVO> dataList = dao.selectMyVehicleReservationList(paging);
		paging.setDataList(dataList);
		return dataList;
	}

	@Override
	public int createReservation(VehicleReservationVO reservation) {
		return dao.insertVehicleReservation(reservation);
	}

	@Override
	public int removeReservation(String vhcleReserveCd) {
		return dao.deleteVehicleReservation(vhcleReserveCd);
	}

	@Override
	public List<VehicleReservationVO> retrieveSpecificVehicleReservationList(Map<String, String> rangeAndCd) {
		return dao.selectOneVehicleReservationList(rangeAndCd);
	}

	@Override
	public List<VehicleReservationVO> retrieveSimpleReservationList(String vhcleCd) {
		return dao.selectSimpleReservationList(vhcleCd);
	}

	@Override
	public String retrieveReservationPasswordVehicle(String reserveCd) {
		return dao.selectReservationPasswordVehicle(reserveCd);
	}
}
