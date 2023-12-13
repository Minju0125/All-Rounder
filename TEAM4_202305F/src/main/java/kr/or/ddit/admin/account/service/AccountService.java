package kr.or.ddit.admin.account.service;

import java.util.List;

import kr.or.ddit.admin.account.UserNotFoundException;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 15.    김보영         최초작성
 * 2023. 11. 15.    전수진         직원정보 상세조회 추가
 * 2023. 11. 18.    김보영         직원정보 목록조회 추가
 * 2023. 11. 19.    김보영         POI
 * 2023. 11. 19.    김보영         직원정보 CRUD
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */


public interface AccountService {
	
	/**
	 * 마이 페이지 및 사원한명을 조회할때 사용
	 * @param empCd
	 * @return 존재하지 않는 경우, {@link UserNotFoundException} 발생
	 */
	public EmployeeVO retrieveMember(String empCd);

	/**
	 * 직원 목록을 조회할때 사용
	 * @return
	 */
	public List<EmployeeVO> retrieveEmpList();

	/**
	 * 직원등록->POI
	 * @param excelEmployeeVO
	 */
	public ServiceResult insertEmpExcel(EmployeeVO excelEmployeeVO);

	/**
	 * 직원등록
	 * @param emp
	 * @return
	 */
	public ServiceResult createEmp(EmployeeVO emp);

	/**
	 * 직원삭제
	 * @param empCd
	 * @return
	 */
	public ServiceResult removeEmp(String empCd);

	/**
	 * 부서명리스트
	 * @return
	 */
	public List<DeptVO> deptList();

	/**
	 * 직급리스트
	 * @return
	 */
	public List<CommonVO> rankList();

	/**
	 * 부서에따른 직원목록
	 * @param deptCd
	 * @return
	 */
	public List<EmployeeVO> empSuprrList(String deptCd);

	/**
	 * 직원수정
	 * @param emp
	 * @return
	 */
	public ServiceResult modifyEmp(EmployeeVO emp);


	
	
	
}
