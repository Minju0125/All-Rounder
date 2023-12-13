package kr.or.ddit.groupware.reservation.reserveStatus.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.management.timer.TimerNotification;

import org.springframework.stereotype.Service;

import kr.or.ddit.admin.reservation.confroom.dao.AdConfRoomDAO;
import kr.or.ddit.admin.reservation.vehicle.dao.AdVehicleDAO;
import kr.or.ddit.groupware.reservation.confroom.dao.ConfRoomDAO;
import kr.or.ddit.groupware.reservation.vehicle.dao.VehicleDAO;
import kr.or.ddit.vo.groupware.ConfRoomReservationVO;
import kr.or.ddit.vo.groupware.ConfRoomVO;
import kr.or.ddit.vo.groupware.ReservationStatusVO;
import kr.or.ddit.vo.groupware.VehicleReservationVO;
import kr.or.ddit.vo.groupware.VehicleVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReserveStatusServiceImpl implements ReserveStatusService{

	@Inject
	AdVehicleDAO adVehicleDao;
	
	@Inject
	VehicleDAO vehicleDao;
	
	@Inject
	AdConfRoomDAO adConfRoomDao;
	
	@Inject
	ConfRoomDAO confRoomDao;
	
	@Override
	public List<ReservationStatusVO> retrieveVehicleStatus() {
		List<ReservationStatusVO> statusList = new ArrayList<ReservationStatusVO>();
		//차량 전체 내역 조회
		List<VehicleVO> vehicleList =  adVehicleDao.selectVehicleList();
		
		//전체 차량 반복문 돌면서
		for(VehicleVO vehicle : vehicleList) {
			String vhcleCd = vehicle.getVhcleCd(); //차량 코드 얻기
			List<VehicleReservationVO> vehicleReservationList = vehicleDao.selectTodayVehicleReservationList(vhcleCd); //해당차량의 오늘 예약 내역
			String status = "";
			if((!vehicleReservationList.isEmpty())) { //예약내역이 존재한다면
				for(VehicleReservationVO vReserve : vehicleReservationList) {
					String timeRange = vReserve.getVhcleUseTime(); //형식 : 09:00-13:00
					if(timeRangeCheck(timeRange)) {//현재 시간과 비교
						status = "사용중";
					}else{
						status = "예약가능";
					}
				}
			}else { //해당 차량이 오늘 예약내역이 존재하지 않는다면
				status="예약가능";
			}
			ReservationStatusVO reservationStatusVO = new ReservationStatusVO();
			reservationStatusVO.setReserveCd(vhcleCd);
			reservationStatusVO.setStatus(status);
			statusList.add(reservationStatusVO);
		}
		return statusList;
	}
	
	@Override
	public List<ReservationStatusVO> retrieveConfRoomStatus() {
		List<ReservationStatusVO> statusList = new ArrayList<ReservationStatusVO>();
		//전체 회의실 목록 조회
		List<ConfRoomVO> confRoomList =  adConfRoomDao.selectConfRoomList();
		for(ConfRoomVO confRoom : confRoomList) {
			String confRoomCd = confRoom.getConfRoomCd();
			List<ConfRoomReservationVO> confRoomReservationList = confRoomDao.selectTodayConfRoomReservationList(confRoomCd);
			String status = "";
			if(!confRoomReservationList.isEmpty()) {
				for(ConfRoomReservationVO cReserve : confRoomReservationList) {
					String timeRange = cReserve.getConfTime();
					if(timeRangeCheck(timeRange)) {
						status = "사용중";
					}else {
						status = "예약가능";
					}
				}
			}else { //해당 회의실의 오늘 예약 내역이 존재하지 않는다면
				status = "예약가능";
			}
			ReservationStatusVO reservationStatusVO = new ReservationStatusVO();
			reservationStatusVO.setReserveCd(confRoomCd);
			reservationStatusVO.setStatus(status);
			statusList.add(reservationStatusVO);
		}
		return statusList;
	}

	@Override
	public List<Map<String, String>> retrieveMyVehicleReservationStatus(List<VehicleReservationVO> dataList) {
		List<Map<String, String>> statusList = new ArrayList<>();
		for(VehicleReservationVO vrVO : dataList) {
			String status = "";
			String vhcleReserveCd = vrVO.getVhcleReserveCd(); 
			String vhcleUseTime = vrVO.getVhcleUseTime();
			String vhcleUseDate = vrVO.getVhcleUseDate();
			if(dateTimeCheck(vhcleUseDate,vhcleUseTime)) { //이 시간대 안에 존재한다면 사용중
				status = "사용중";
			}else { //endDate 가 현재시간 이후인지 판별
				if(isAfterEndDateTime(vhcleUseDate,vhcleUseTime)) {
					status = "사용완료";
				}else {
					status = "사용대기";
				}
			}
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("vhcleReserveCd", vhcleReserveCd);
			resultMap.put("status", status);
			statusList.add(resultMap);
		}
		return statusList;
	}

	@Override
	public List<Map<String, String>> retrieveMyConfRoomReservationStatus(List<ConfRoomReservationVO> dataList) {
		List<Map<String, String>> statusList = new ArrayList<>();
		for(ConfRoomReservationVO crVO : dataList) {
			String status = "";
			String confReserveCd = crVO.getConfReserveCd();
			String confReserveDate = crVO.getConfDate();
			String confTime = crVO.getConfTime();
			
			if(dateTimeCheck(confReserveDate,confTime)) { //이 시간대 안에 존재한다면 사용중
				status = "사용중";
			}else { //endDate 가 현재시간 이후인지 판별
				if(isAfterEndDateTime(confReserveDate,confTime)) {
					status = "사용완료";
				}else {
					status = "사용대기";
				}
			}
			Map<String, String> resultMap = new HashMap<>();
			resultMap.put("confReserveCd", confReserveCd);
			resultMap.put("status", status);
			statusList.add(resultMap);
		}
		return statusList;
	}
	
	private boolean isAfterEndDateTime(String confReserveDate, String confTime) {
        String[] times = confTime.split("-");
        LocalTime endTime = LocalTime.parse(times[1]);
        
        // 현재 날짜와 시간 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDate confReserveLocalDate = LocalDate.parse(confReserveDate);

        // 입력 받은 날짜와 시간대가 현재보다 이후인지 확인
        boolean result =false;
        LocalDateTime targetDateTime = LocalDateTime.of(confReserveLocalDate, endTime);
        if (currentDateTime.isAfter(targetDateTime)) {
        	result =true;
        } 
        return result;
	}
	
	/**
	 * 날짜, 시간대를 넘겨 받아 현재 시간이 해당 범위 안에 포함되는지 판별
	 */
	private boolean dateTimeCheck(String confReserveDate, String confTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        LocalDate confReserveLocalDate = LocalDate.parse(confReserveDate);

        String[] times = confTime.split("-");
        LocalTime startTime = LocalTime.parse(times[0]);
        LocalTime endTime = LocalTime.parse(times[1]);

        boolean result = false;
        if (currentDateTime.toLocalDate().isEqual(confReserveLocalDate) &&
            currentDateTime.toLocalTime().isAfter(startTime) &&
            currentDateTime.toLocalTime().isBefore(endTime)) {
            log.info("사용중");
            result = true;
        }
		return result;
	}
	
	/**
	 * 문자열 형식의 시간대를 파라미터로
	 * @param timeRange
	 * @return
	 */
	private boolean timeRangeCheck(String timeRange) {
        LocalTime currentTime = LocalTime.now(); //현재시간

        // timeStr 시간대를 '-'을 기준으로 시작 시간과 종료 시간으로 분리
        String[] times = timeRange.split("-");
        String startTimeString = times[0]; //시작시간 (ex.09:00)
        String endTimeString = times[1]; //종료시간 (ex.13:00)

        LocalTime startTime = LocalTime.parse(startTimeString);
        LocalTime endTime = LocalTime.parse(endTimeString);

        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
	}
}
