package kr.or.ddit.login.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import kr.or.ddit.security.userdetailes.EmployeeVOWrapper;
import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 박민주
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                 수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      박민주       최초작성
 * 2023. 12.4			박민주		  updateEmpPwAndStatus,updateEmpPw 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Mapper
public interface LoginDAO extends UserDetailsService {
	
	@Override
	default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmployeeVO inputdata = new EmployeeVO();
		inputdata.setEmpCd(username);
		EmployeeVO member = selectEmpForAuth(inputdata);
		if(member==null)
			throw new UsernameNotFoundException(username+" 해당 사용자 없음.");
		return new EmployeeVOWrapper(member);
	}
	/**
	 * 사번을 기반으로 사용자의 기본 정보를 조회
	 * 
	 * @param inputData
	 * @return 검색 결과 객체로 존재하지 않는 경우, null 반환.
	 */
	public EmployeeVO selectEmpForAuth(EmployeeVO inputData);
	
	/**
	 * 사원정보 수정
	 * @param inputData
	 * @return 
	 */
	public int updateEmpCertfNo(EmployeeVO inputData);
	
	/**
	 * 직원 비밀번호를 생년월일로, 상태를 N으로 업데이트
	 * @param employee
	 */
	public int updateEmpPwAndStatus(EmployeeVO employee);
	
	/**
	 * 직원 비밀번호를 입력한 값으로, 상태를 E로 업데이트
	 * @param employee
	 * @return
	 */
	public int updateEmpPw(EmployeeVO employee);
	
}
