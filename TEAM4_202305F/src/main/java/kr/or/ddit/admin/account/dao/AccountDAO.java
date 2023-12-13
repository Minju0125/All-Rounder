package kr.or.ddit.admin.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 * 2023. 11. 19.    김보영         직원정보 CRUD
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Mapper
public interface AccountDAO {
	
	
	/**
	 * 직원의 목록
	 * @param empVO
	 * @return
	 */
	public List<EmployeeVO> selectEmpList();
	
	/**
	 * 직원 정보 상세 조회
	 * @param empCd
	 * @return 존재하지 않으면, null 반환
	 */
	public EmployeeVO selectEmployee(@Param("empCd") String empCd);

	/**
	 * POI를 이용한 직원 등록 
	 * @param excelEmployeeVO
	 * @return
	 */
	public int insertEmpExcel(EmployeeVO excelEmployeeVO);

	/**
	 * 신규직원생성
	 * @param emp
	 * @return
	 */
	public int insertEmp(EmployeeVO emp);

	/**
	 *  직원삭제
	 * @param empCd
	 */
	public int deleteEmp(@Param("empCd") String empCd);

	/**
	 * 부서에 따른 직원조회
	 * @param deptName
	 * @return
	 */
	public List<EmployeeVO> selectSuprrList (@Param("deptCd") String deptCd);

	
	/**
	 * 직급목록
	 * @return
	 */
	public List<CommonVO> selectRankList();
	
	
	/**
	 * 부서목록
	 * @return dept List
	 */
	public List<DeptVO> selectDeptList();

	/**
	 * 직원정보수정
	 * @param emp
	 * @return
	 */
	public int updateEmp(EmployeeVO emp);
	
	
	
}
