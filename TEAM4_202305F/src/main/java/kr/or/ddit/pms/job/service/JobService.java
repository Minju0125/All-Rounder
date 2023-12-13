package kr.or.ddit.pms.job.service;

import java.util.List;

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
 * @since 2023. 11. 10.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 10.    김보영         최초작성
 * 2023. 11. 11.    김보영         일감차트,참여자
 * 2023. 11. 13.    김보영         일감담당자,일감목록
 * 2023. 11. 15.    김보영         일감등록
 * 2023. 11. 16.    김보영         일감파일등록,수정,삭제
 * 2023. 11. 17.    김보영         나의일감
 * 2023. 11. 21.    김보영         일감상세
 * 2023. 11. 26.    김보영         파일처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
public interface JobService {
	
	/**
	 * 일감의 상태 카운팅 
	 * @param pVO
	 * @return
	 */
	public PjobVO retrieveJobCount(PjobVO pVO);
	
	
	/**
	 * 프로젝트 참여자
	 * @param pjVO
	 * @return
	 */
	public List<PmemberVO> retrieveProMember(PmemberVO pMVO);
	
	
	/**
	 * 일감 담당자
	 * @param cVO
	 * @return
	 */
	public List<ChargerVO> retrieveCharger(int jobSn);

	
	/**
	 * 프로젝트에 해당하는 모든 일감목록
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> retrieveJobList(PaginationInfo<PjobVO> paging);
	
	
	/**
	 * 일감의 작성자를 찾기
	 * @param empCd
	 * @return
	 */
	public EmployeeVO retrieveWriter(String empCd);
	
	
	/**
	 * 일감등록 ,파일추가
	 * @param jVO
	 */
	public ServiceResult createJob(PjobVO jVO);


	/**
	 * 일감등록시 담당자 함께 추가
	 * @param cVO
	 */
	public void createCharger(ChargerVO cVO);


	/**
	 * 새로운일감의 번호(시퀀스)
	 * @return
	 */
	public int getJobSn();
	
	
	/**
	 * 일감삭제
	 * @param jVO
	 */
	public ServiceResult removeJob(PjobVO jVO);
	
	/**
	 * 일감삭제시 해당 담당자도 같이 삭제
	 * @param jobSn
	 */
	public void removeCharger(ChargerVO cVO);
	
	/**
	 * 일감수정
	 * @param jVO
	 */
	public ServiceResult modifyJob(PjobVO jVO);
	
	
	
	/**
	 * 해당프로젝트의 내 일감보기
	 * @param cVO
	 */
	public List<PjobVO> retrieveMyJob(ChargerVO cVO);
	
	

	/**
	 * 상위일감조회
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> retrieveUJobList(String proSn,String empCd);


	/**
	 * 하나의 프로젝트 정보
	 * @param proSn
	 * @return
	 */
	public ProjectVO retrieveProject(String proSn);


	/**
	 * 일감상세보기 
	 * @param dJob
	 */
	public PjobVO retrievejobDetail(PjobVO dJob);


	/**
	 * 하위일감 목록
	 * @param jobSn
	 * @return
	 */
	public List<PjobVO> retrieveLowJList(int jobSn);


	/**
	 * 하나의 일감의 로그목록
	 * @param jobSn
	 * @return
	 */
	public List<PLogVO> retrieveLogList(int jobSn);


	/**
	 * 파일조회
	 * @param pAVO
	 * @return
	 */
	public PAtchVO retrieveFile(PAtchVO pAVO);


	/**
	 * 파일목록 조회
	 * @param pAVO
	 * @return
	 */
	public List<PAtchVO> retrieveFileList(PAtchVO pAVO);


	/**
	 * 프로젝트의 이슈참조일감목록
	 * @param proSn
	 * @return
	 */
	public List<IssueVO> retrieveRefIssueList(String proSn);


	/**
	 * 나의 요청내역
	 * @param loginEmpCd
	 * @return
	 */
	public List<PjobVO> retrieveReq(String loginEmpCd);


	/**
	 * 파일삭제
	 * @param pVO
	 * @return
	 */
	public ServiceResult removeFile(PAtchVO pVO,String loginEmpCd);


	
	
	

}
