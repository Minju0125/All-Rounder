package kr.or.ddit.company.org.service;

import java.util.List;

import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

public interface OrgService {
	
//	/**
//	 * 조직도
//	 * 직원 전제 조회
//	 * @return
//	 */
//	public List<EmployeeVO> selectPmemListOrg(String proSn); 
	 
	/**
	 * 조직도
	 * 직원 전제 조회
	 * @return
	 */  
	public List<EmployeeVO> selectListOrg(); 
	/**
	 * 조직도
	 * 직원 전제 조회
	 * 오버라이드를 이용한 proSn의 값이 있을때 사용
	 * @return
	 */  
	public List<EmployeeVO> selectListOrg(String proSn);
	
	/**
	 * 조직도
	 * 부서 전체 조회
	 * @return
	 */
	public List<DeptVO> selectListDept();
	
	/**
	 * 조직도
	 * 직원 상세 조회
	 * @param empCd
	 * @return
	 */
	public EmployeeVO selectOrg(String empCd);
	
	/**
	 * 부서
	 * 추가
	 * @param dVO
	 * @return
	 */
	public int insertDept(DeptVO dVO);
	
	/**
	 * 부서
	 * 삭제
	 * @param deptCd
	 * @return
	 */
	public int deleteDept(String deptCd);
}
