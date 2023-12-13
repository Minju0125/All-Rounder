package kr.or.ddit.pms.issue.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
 * --------     	--------    ----------------------
 * 2023. 11. 23.      김보영         최초작성
 * 2023. 11. 24.      김보영         목록, 칸반보드
 * 2023. 11. 29.      김보영         수정,삭제
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Mapper
public interface IssueDAO {

	/**
	 * 이슈등록
	 * @param iVO
	 * @return
	 */
	public int insertIssue(IssueVO iVO);

	/**
	 * 이슈목록 조회
	 * @param proSn
	 * @return
	 */
	public List<IssueVO> issueList(IssueVO iVO);

	/**
	 * 이슈수정 
	 * @param issueVO
	 * @return
	 */
	public int updateIssue(IssueVO issueVO);

	/**
	 * 하나의 이슈 조회
	 * @param issueNo
	 * @return
	 */
	public IssueVO selectIssue(@Param("issueNo")int issueNo);

	/**
	 * 일감번호찾기
	 * @param issueNo
	 * @return
	 */
	public IssueVO selectJobSn(@Param("issueNo")int issueNo);

	/**
	 * 이슈 수정하기
	 * @param issueVO
	 * @return
	 */
	public int updateIssueInfo(IssueVO issueVO);

	/**
	 * 이슈 삭제하기
	 * @param iVO
	 * @return
	 */
	public int deleteIssue(IssueVO iVO);

	


	/**
	 * 이슈 구분에 따른 중요도와 이슈 상태 
	 * 차트 개수 구하기
	 * @param iVO
	 * @return
	 */
	public List<IssueVO> chartValue(IssueVO iVO);

	/**
	 * 프로젝트의 일감목록
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> issueJobList(String proSn);

}
