package kr.or.ddit.pms.issue.service;

import java.util.List;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.pms.IssueVO;
import kr.or.ddit.vo.pms.PjobVO;

/**
 * @author 작성자명
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------    		 --------    ----------------------
 * 2023. 11. 23.      김보영         최초작성
 * 2023. 11. 24.      김보영         조회 
 * 2023. 11. 24.      김보영         칸반보드
 * 2023. 11. 29.      김보영         수정, 삭제
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

public interface IssueService {

	/**
	 * 이슈등록
	 * @param iVO
	 * @return
	 */
	public ServiceResult createIssue(IssueVO iVO);

	/**
	 * 이슈목록조회
	 * @param proSn
	 * @return
	 */
	public List<IssueVO> retrieveIssueList(IssueVO iVO);

	/**
	 * 이슈 수정
	 * @param issueVO
	 * @return
	 */
	public ServiceResult modifyIssue(IssueVO issueVO);

	/**
	 * 하나의 이슈조회
	 * @param issueNo
	 * @return
	 */
	public IssueVO retrieveIssue(int issueNo);

	/**
	 * 왜 일감번호 못 가져와 !ㅜ
	 * 이슈에 참조되어있는 일감번호
	 * @param issueNo
	 * @return
	 */
	public IssueVO retrieveJobSn(int issueNo);

	/**
	 * 이슈 수정
	 * @param iVO
	 * @return
	 */
	public ServiceResult modifyIssueInfo(IssueVO iVO);

	/**
	 * 이슈삭제
	 * @param issueNo
	 * @return
	 */
	public ServiceResult removeIssue(IssueVO iVO);

	/**
	 * @param iVOGen
	 * @return
	 */
	public List<IssueVO> chartValue(IssueVO iVOGen);

	/**
	 * 일감목록
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> retrieveJobList(String proSn);


	

}
