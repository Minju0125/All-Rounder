package kr.or.ddit.pms.pleader.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;

/**
 * @author 작성자명
 * @since Nov 29, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 29, 2023     송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface PleaderDAO {
	
	
	/**
	 * 해당 프로젝트의 모든 일감 현황 조회
	 * @param proSn
	 * @return
	 */
	public PjobVO pjobAllCount(String proSn);
	
	
	/**
	 * 프로젝트에서 참여자가 가지고 있는 일감에 대하여 일감현황 조회
	 * @param pjob
	 * @return
	 */ 
	public List<PjobVO> pjobChargerCount(String proSn); 
	
	
	/**
	 * 검색조건에 맞는 프로젝트들의 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<PjobVO> paging);
	
	      
 	
	/**
	 * 리더가 멤버들의 일감목록을 확인하기 위함
	 * @param paging
	 * @return
	 */ 
	public List<PjobVO> selectLeaderPmember(PaginationInfo<PjobVO> paging);
 
}
