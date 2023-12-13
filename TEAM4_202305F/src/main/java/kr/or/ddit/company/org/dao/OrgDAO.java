package kr.or.ddit.company.org.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 작성자명
 * @since Nov 27, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 12	, 2023      오경석       최초작성
 * Nov 27	, 2023      송석원       프로젝트 멤버 제약 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface OrgDAO {
	
	
	/**
	 * 조직도
	 * 직원 전제 조회
	 * 파람으로 proSn을 받아 xml에 넘기기 위함값이 없으면 null
	 * @return
	 */
	public List<EmployeeVO> selectListOrg(@Param("proSn") String proSn);
	
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
