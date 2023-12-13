package kr.or.ddit.admin.adproject.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
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
public interface AdminProjectService {
	
	
	/**
	 * 프로젝트 생성
	 * @param proj
	 */
	public ServiceResult createAdminProject(ProjectVO proj);
	
	/**
	 * 프로젝트를 생성한 뒤의 프로젝트 멤버 삽입 
	 * @param proj  
	 * @return
	 */
	public ServiceResult insertAdProjectPmember(ProjectVO proj);   
	
	/**
	 * 프로젝트를 생성한 뒤의 프로젝트 추가적인 멤버 삽입 
	 * @param proj  
	 * @return
	 */
	public ServiceResult insertAfterAdProjectPmember(ProjectVO proj);   
	
	
	/**
	 * 프로젝트 내의팀원조회 
	 * @param proSn
	 * @return
	 */
	public List<PmemberVO> retrieveAdminProject(PaginationInfo<PmemberVO> paging);
	 
	
	
	/**
	 * 검색조건에 맞는 프로젝트 조회 및 페이징
	 * @param paging
	 * @return
	 */
	public List<ProjectVO> retrieveAdminProjectList(PaginationInfo<ProjectVO>paging);
	
	
	
	/**
	 * 프로젝트 수정
	 * @param proj
	 */
	public ServiceResult modifyAdminProject(ProjectVO proj);
	
	
	public ServiceResult removeAdminProjMem(PmemberVO pmemVO);
	
	
	//프로젝트 삭제는 업데이트 형식으로 가야할려나...? 
	
	
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
	 * 프로젝트내의 리더를 업데이트하며 기존의 리더 삭제 후 가져올 새로운 리더 업데이트
	 * @param pmemVO
	 * @return
	 */
	public void updateProjectLeaderChange(PmemberVO pmemVO);
	
	
	
	/**
	 * 해당 프로젝트의 멤버들 조회
	 * @param proSn
	 * @return
	 */
	public List<PmemberVO>projectPmemberSelect(String proSn);
	

	/**
	 * 프로젝트 멤버 조회 리더용
	 * @param proSn
	 * @return
	 */
	public List<PmemberVO>projectPmemberSelectleader(String proSn);

	/**
	 * 일감조회용
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> retrieveJobList(String proSn);

	/**
	 * 일감,이슈개수
	 * @param proSn
	 * @return
	 */
	public ChartVO retrieveCnt(String proSn);

	 
	
	
	
	

}
