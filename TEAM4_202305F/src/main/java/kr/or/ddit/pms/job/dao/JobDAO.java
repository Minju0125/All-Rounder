package kr.or.ddit.pms.job.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.pms.ChargerVO;
import kr.or.ddit.vo.pms.IssueVO;
import kr.or.ddit.vo.pms.PAtchVO;
import kr.or.ddit.vo.pms.PLogVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;

/**
 * @author 작성자명
 * @since 2023. 11. 8.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       수정자         수정내용
 * --------     --------    ----------------------
 * 2023. 11. 8. 	김보영         최초작성 의사코드 1차 작성
 * 2023. 11. 11.    김보영         일감차트,참여자
 * 2023. 11. 13.    김보영         일감담당자,일감목록
 * 2023. 11. 15.    김보영         담당자등록,일감등록
 * 2023. 11. 16.    김보영         일감삭제,일감수정
 * 2023. 11. 17.    김보영         내일감t보기
 * 2023. 11. 21.    김보영         일감상세 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */


@Mapper
public interface JobDAO {
	
	/**
	 * 일감 완료일
	 * @param jobSn
	 * @return
	 */
	public int updateCdate(int jobSn);
	
	
	/**
	 * 일감의 담당자목록
	 * @param jVO
	 * @return
	 */
	public List<ChargerVO> chargerList(int jobSn);
	
	
	/**
	 * 하나의 프로젝트 참여자 목록 
	 * @param pMVO
	 * @return
	 */
	public List<PmemberVO> proMemList(PmemberVO pMVO);
	
	
	/**
	 * 일감의 요청 상태 갯수 목록
	 * @param pVO
	 * @return
	 */
	public PjobVO jobCount(PjobVO pVO);
	
	
	
	
	/**
	 * 하나의 프로젝트 일감 목록
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> selectPjobList (PaginationInfo<PjobVO> paging);
	
	
	/**
	 * 사번을 가지고 일감의 작성자를 찾는 쿼리
	 * @param empCd
	 * @return
	 */
	public EmployeeVO selectWriter(@Param("empCd")String empCd);
	
	
	
	/**
	 * 일감등록 시 jobSn 가져오기
	 * @return
	 */
	public int selectJobSn();
	
	/**
	 * 일감등록
	 * @param jVO
	 * @return
	 */
	public int insertJob(PjobVO jVO);
	

	/**
	 * 일감의 담당자 등록
	 * @param jVO
	 * @return
	 */
	public int insertCharger(ChargerVO cVO);

	
	/**
	 * 일감 삭제
	 * @param jVO
	 * @return
	 */
	public int deleteJob(PjobVO jVO);
	
	
	
	/**
	 * 일감삭제시 해당 담당자도 같이 삭제
	 * @param jobSn
	 * @return
	 */
	public int deleteCharger(ChargerVO cVO);
	
	/**
	 * 일감 수정
	 * @param jVO
	 * @return
	 */
	public int updateJob(PjobVO jVO);
	
	/**
	 * 일감 수정시 담당자도 같이 수정
	 * @param cVO
	 * @return
	 */
	public int updateCharger(ChargerVO cVO);
	
	
	/**
	 * 나의 일감 조회
	 * @param cVO
	 * @return
	 */
	public List<PjobVO>myJobList(ChargerVO cVO);
	
	
	/**
	 * 하나의 프로젝트 일감 totalRecord 조회
	 * @param jVO
	 * @return
	 */
	public int selectTotalRecord(PaginationInfo<PjobVO> paging);


	/**
	 * 하나의 프로젝트 상위일감 조회
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> UJobList(@Param("proSn") String proSn , @Param("empCd") String loginEmpCd);


	/**
	 * 현재 들어와있는 하나의 프로젝트 정보
	 * @param proSn
	 * @return
	 */
	public ProjectVO projectInfo(@Param("proSn") String proSn);


	/**
	 * 일감상세보기
	 * @param dJob
	 * @return
	 */
	public PjobVO jobDetail(PjobVO dJob);


	/**
	 * 하위일감목록
	 * @param jobSn
	 * @return
	 */
	public List<PjobVO> rowJList(int jobSn);


	/**
	 * 사번으로 이름 찾기
	 * @param changer
	 */
	public String selectChanger(@Param("changer") String changerCd);
	
	/**
	 * 로그 등록
	 * @param plVO
	 * @return
	 */
	public int insertPLog(PLogVO plVO);


	/**
	 * 하나의 일감의 로그목록
	 * @param jobSn
	 * @return
	 */
	public List<PLogVO> selectLogList(@Param("jobSn") int jobSn);


	/**
	 * 대시보드에서의 일감목록
	 * @param loginEmpCd
	 * @return
	 */
	public List<PjobVO> dashJobList(@Param("empCd") String empCd);


	/**
	 * 프로젝트의 일감참조된 이슈 목록
	 * @param proSn
	 * @return
	 */
	public List<IssueVO> selectRefIssueList(@Param("proSn")String proSn);


	/**
	 * 나의 요청내역
	 * @param loginEmpCd
	 * @return
	 */
	public  List<PjobVO> selectMyReq(@Param("empCd") String loginEmpCd);
	


	
}
