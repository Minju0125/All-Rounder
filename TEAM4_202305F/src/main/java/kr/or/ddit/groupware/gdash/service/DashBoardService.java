package kr.or.ddit.groupware.gdash.service;

import java.util.List;

import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.pms.PjobVO;


public interface DashBoardService {

	EmployeeVO selectEmpForAuth(EmployeeVO empVO);

	/**
	 * 로그인한 사람의 일감 목록
	 * @param loginEmpCd
	 * @return
	 */
	public List<PjobVO> dashboardMyJob(String loginEmpCd);

	

}
