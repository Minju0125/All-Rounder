package kr.or.ddit.groupware.mypage.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.groupware.EmployeeVO;

/**
 * @author 작성자명
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

public interface MypageService {

	/**
	 * 마이페이지 조회
	 * @param empCd
	 * @return
	 */
	public EmployeeVO selectMypage(String empCd);
	
	/**
	 * 비밀번호 조회
	 * @param emp
	 * @return
	 */
	public EmployeeVO authenticateBoard(EmployeeVO emp);
	
	/**
	 * 개인정보 수정
	 * @param empVO
	 * @return
	 */
	public ServiceResult updateMypage(EmployeeVO empVO);
}
