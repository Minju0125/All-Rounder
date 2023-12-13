package kr.or.ddit.pms.issue.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.pms.issue.dao.IssueDAO;
import kr.or.ddit.vo.pms.IssueVO;
import kr.or.ddit.vo.pms.PjobVO;
import lombok.RequiredArgsConstructor;

/**
 * @author 작성자명
 * @since 2023. 11. 23.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일      		수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 23.      김보영         최초작성
 * 2023. 11. 24.      김보영         조회, 칸반보드
 * 2023. 11. 29.      김보영         수정,삭제
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService {

	private final IssueDAO dao;
	
	@Override
	public ServiceResult createIssue(IssueVO iVO) {
		
		
		
		//날짜 변환
		String eData = iVO.getIssueEdate();
		eData = eData.replace(String.valueOf("-"),"");
		iVO.setIssueEdate(eData);
		
		int rowcnt = dao.insertIssue(iVO);
		
		ServiceResult result = null;
		
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<IssueVO> retrieveIssueList(IssueVO iVO) {
		return dao.issueList(iVO);
	}

	@Override
	public ServiceResult modifyIssue(IssueVO issueVO) {
		
		int cnt = dao.updateIssue(issueVO);
		ServiceResult result = null;
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public IssueVO retrieveIssue(int issueNo) {
		return dao.selectIssue(issueNo);
	}

	@Override
	public IssueVO retrieveJobSn(int issueNo) {
		return dao.selectJobSn(issueNo);
	}

	@Override
	public ServiceResult modifyIssueInfo(IssueVO iVO) {
		
		//날짜 변환
		String eData = iVO.getIssueEdate();
		eData = eData.replace(String.valueOf("-"),"");
		iVO.setIssueEdate(eData);
		
		
		int rowcnt = dao.updateIssueInfo(iVO);
		ServiceResult result = null;
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public ServiceResult removeIssue(IssueVO iVO) {
		int rowcnt = dao.deleteIssue(iVO);
		
		ServiceResult result = null;
		if(rowcnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	@Override
	public List<IssueVO> chartValue(IssueVO iVO) {
		return dao.chartValue(iVO);
	}

	@Override
	public List<PjobVO> retrieveJobList(String proSn) {
		return dao.issueJobList(proSn);

	}
}
