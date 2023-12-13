package kr.or.ddit.company.org.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.company.org.dao.OrgDAO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

@Service
public class OrgServiceImpl implements OrgService {

	@Inject
	private OrgDAO dao;
	
//	/**
//	 *프로젝트에서 들어가있는 인원 제외하고 조회 
//	 */
//	@Override
//	public List<EmployeeVO> selectPmemListOrg(String proSn) {
//		return dao.selectPmemListOrg(proSn);  
//	}
	
	@Override
	public List<EmployeeVO> selectListOrg() { 
		return dao.selectListOrg(null); 
	} 
	
	/**
	 *오버라이드를 이용한 proSn의 값이 있을때 사용 
	 */
	@Override
	public List<EmployeeVO> selectListOrg(String proSn) { 
		return dao.selectListOrg(proSn);
	}

	@Override
	public List<DeptVO> selectListDept() {
		return dao.selectListDept();
	}

	@Override
	public EmployeeVO selectOrg(String empCd) {
		return dao.selectOrg(empCd);
	}

	@Override
	public int insertDept(DeptVO dVO) {
		return dao.insertDept(dVO);
	}

	@Override
	public int deleteDept(String deptCd) {
		return dao.deleteDept(deptCd);
	}

	

}
