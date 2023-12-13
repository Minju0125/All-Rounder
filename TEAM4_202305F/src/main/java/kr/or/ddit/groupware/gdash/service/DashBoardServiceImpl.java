package kr.or.ddit.groupware.gdash.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.login.dao.LoginDAO;
import kr.or.ddit.pms.job.dao.JobDAO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.pms.PjobVO;

@Service
public class DashBoardServiceImpl implements DashBoardService {

	@Inject
	private LoginDAO dao;
	@Inject
	private JobDAO jDao;
	
	@Override
	public EmployeeVO selectEmpForAuth(EmployeeVO empVO) {
		return dao.selectEmpForAuth(empVO);
	}

	@Override
	public List<PjobVO> dashboardMyJob(String loginEmpCd) {
		return jDao.dashJobList(loginEmpCd);
	}

}
