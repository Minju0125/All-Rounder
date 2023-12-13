package kr.or.ddit.cal.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.cal.dao.CalDAO;
import kr.or.ddit.vo.CalCheckVO;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.pms.CalendarVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalServiceImpl implements CalService {

	@Inject
	private CalDAO dao;


	@Override
	public List<CommonVO> selectListType() {
		// TODO Auto-generated method stub
		return dao.selectListType();
	}
	
	@Override
	public String selectType(String commonCodeCd) {
		return dao.selectType(commonCodeCd);
	}

	@Override
	public int insertCal(CalendarVO calVO) {
		log.info("결과값 테스트 {}",calVO.getScheduleCd());
		String code = calVO.getScheduleCd();
		String[] arr = code.split("_");
		log.info("TESTESTESTESTE {}", arr.length);
		if(arr.length == 2) {
			code = code + "001";
			calVO.setScheduleCd(code);
		}
		return dao.insertCal(calVO);
	}

	@Override
	public int deleteCal(String commonCodeCd) {
		return dao.deleteCal(commonCodeCd);
	}

	@Override
	public int updateCal(CalendarVO calVO) {
		return dao.updateCal(calVO);
	}

	@Override
	public CalendarVO detailCal(String scheduleCd) {
		return dao.detailCal(scheduleCd);
	}

	@Override
	public List<CalendarVO> searchCal(CalendarVO calVO) {
		return dao.searchCal(calVO);
	}

	@Override
	public List<ProjectVO> selectProjectList(String empCd) {
		return dao.selectProjectList(empCd);
	}


	@Override
	public CalCheckVO selectList(CalCheckVO checkVO) {
		List<ProjectVO> pList= dao.selectProjectList(checkVO.getEmpCd());
		checkVO.setPList(pList);
		List<CalendarVO> cList= dao.selectList(checkVO);
		checkVO.setCalList(cList);
		return checkVO;
	}

	@Override
	public int dragUpdate(CalendarVO calVO) {
		return dao.dragUpdate(calVO);
	}


}
