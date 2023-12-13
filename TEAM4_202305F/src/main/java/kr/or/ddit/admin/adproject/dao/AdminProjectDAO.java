package kr.or.ddit.admin.adproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;

/**
 * @author 작성자명
 * @since Nov 21, 2023
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * Nov 21, 2023      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface AdminProjectDAO {

	 
	/**
	 * 관리자의 프로젝트 등록
	 * @param proj
	 * @return
	 */
	public int insertAdminProject(ProjectVO proj);
	
	 
	/**
	 * 프로젝트를 생성한 뒤의 추가적으로 프로젝트 멤버 삽입 
	 * @param proj  
	 * @return
	 */
	public int insertAfterAdProjectPmember(PmemberVO pmemVO); 
	
	/**
	 * 프로젝트를 생성한 뒤의 프로젝트 멤버 삽입 
	 * @param proj  
	 * @return
	 */
	public int insertAdProjectPmember(PmemberVO pmemVO); 
	
	
	/**
	 * 관리자가 프로젝트 멤버를 등록하기 위한 상세 페이지
	 * @param proSn
	 * @return
	 */
	public List<PmemberVO> selectAdminProjectMember(PaginationInfo<PmemberVO> paigng);
	  
	  
	
	/**
	 * 검색조건에 맞는 프로젝트들의 수 조회
	 * @param paging
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<ProjectVO> paging);
	
	
	/**
	 * 검색조건에 맞는 프로젝트의 멤버수 조회 
	 * @param paging
	 * @return
	 */
	public int selectTotalRecordPmember(PaginationInfo<PmemberVO> paging); 
	
	 
	/**
	 * 검색조건에 맞는 공지게시판 리스트 조회
	 * @param paging
	 * @return
	 */
	public List<ProjectVO> selectAdminProjectList(PaginationInfo<ProjectVO> paging);
	
	
	
	/**
	 * 프로젝트 수정
	 * @param proj
	 * @return
	 */
	public int updateAdminProject(ProjectVO proj);
	
	
	/**
	 * 프로젝트 상세멤버 삭제 
	 * @return
	 */
	public int deleteAdminProjectMem(PmemberVO pmemVO);
	
	
	/**
	 * 상위 일감카운트
	 * @param proSn
	 * @return
	 */
	public String upperCountJop(String proSn);
	
	
	/**
	 * 하위 일감 카운트 
	 * @param proSn
	 * @return
	 */
	public String lowCountJob(String proSn);
	
	
	/**
	 *  프로젝트내의 리더를 업데이트하기 위한 기존의 리더 삭제
	 * @param pmemVO
	 * @return
	 */
	public int updateProjectLeaderDel(PmemberVO pmemVO);
	
	
	/**
	 * 프로젝트내의 리더를 업데이트하며 가져올 새로운 리더 업데이트
	 * @param pmemVO
	 * @return
	 */
	public int updateProjectLeaderChange(PmemberVO pmemVO);
	
	
	/**
	 * 프로젝트 멤버 조회
	 * @param pmemVO
	 * @return
	 */
	public List<PmemberVO> projectPmemberSelect(String proSn);
	
	/**
	 * 프로젝트 멤버 조회
	 * @param pmemVO
	 * @return
	 */
	public List<PmemberVO> projectPmemberSelectleader(String proSn);


	/**
	 * 프로젝트일감목록
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> selectJobList(String proSn);


	/**
	 * 프로젝트 일감,이슈 개수
	 * @param proSn
	 * @return
	 */
	public ChartVO selectJICnt(String proSn);
	
	 

	
}
