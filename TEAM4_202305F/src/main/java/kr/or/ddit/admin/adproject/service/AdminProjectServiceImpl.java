package kr.or.ddit.admin.adproject.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.admin.adproject.dao.AdminProjectDAO;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
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
@Slf4j
@Service
@RequiredArgsConstructor 
public class AdminProjectServiceImpl  implements AdminProjectService{
  
	private final AdminProjectDAO adprojDAO;
	
	
	/**
	 *프로젝트 생성
	 */
	@Override
	public ServiceResult createAdminProject(ProjectVO proj) {
		 
			log.info("확인{}",proj); 
			log.info("확인2{}",proj.getProBdate());
		//날짜 변환
				String bData = proj.getProBdate();
				bData = bData.replace(String.valueOf("-"),"");
				proj.setProBdate(bData);
				
				String eData = proj.getProEdate();
				eData = eData.replace(String.valueOf("-"),"");
				proj.setProEdate(eData);
		
		int rowcnt = adprojDAO.insertAdminProject(proj);
		
		ServiceResult result = null;
		
		if(rowcnt >= 1) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}
	
	
	/**
	 *생성한 프로젝트에 멤버 삽입
	 */
	
	@Override
	public ServiceResult insertAdProjectPmember(ProjectVO proj) { 
		//int cnt = adprojDAO.insertAdProjectPmember(proj);  
		ServiceResult result = ServiceResult.FAIL;
		
			List<PmemberVO> lineList = proj.getPmemberList(); 
			
			int cnt =0;
			for(int i=0; i< lineList.size(); i++) {
				PmemberVO vo = lineList.get(i);
				vo.setProSn(proj.getProSn());
				vo.setProLeader("N");
				if(i==0) {
					vo.setProLeader("Y");
				}
				
				cnt += adprojDAO.insertAdProjectPmember(vo);
			}
			
			if(cnt == lineList.size()) {
				result = ServiceResult.OK;
			}
			
		return result; 
	}
	
	/**
	 *추후에 멤버를 추가하는 경우
	 */
	@Override
	public ServiceResult insertAfterAdProjectPmember(ProjectVO proj) {
		ServiceResult result = ServiceResult.FAIL;
		 
		List<PmemberVO> lineList = proj.getPmemberList(); 
		 
		int cnt =0;
		for(int i=0; i< lineList.size(); i++) {
			PmemberVO vo = lineList.get(i);
			vo.setProSn(proj.getProSn());
			vo.setProLeader("N");
			
			cnt += adprojDAO.insertAfterAdProjectPmember(vo);
		} 
		
		if(cnt == lineList.size()) {
			result = ServiceResult.OK;
		}
		 
	return result; 
}
 

	@Override
	public List<PmemberVO> retrieveAdminProject(PaginationInfo<PmemberVO> paging) {
				
		int totalRecord = adprojDAO.selectTotalRecordPmember(paging);
		paging.setTotalRecord(totalRecord);
		List<PmemberVO> dataList = adprojDAO.selectAdminProjectMember(paging);  
		paging.setDataList(dataList);
		
 		
		return dataList; 
	}

	@Override
	public List<ProjectVO> retrieveAdminProjectList(PaginationInfo<ProjectVO> paging) {
		int totalRecord = adprojDAO.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<ProjectVO> dataList = adprojDAO.selectAdminProjectList(paging);
		paging.setDataList(dataList);
		
		return dataList;
	}

	/**
	 *프로젝트 수정 
	 */
	@Override
	public ServiceResult modifyAdminProject(ProjectVO proj) {
		
		int cnt = adprojDAO.updateAdminProject(proj);
		
		ServiceResult result = null;
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		
		return result; 
		   
	}

	/**
	 *상위 카운트
	 */
	@Override
	public String upperCountJop(String proSn) {
		return adprojDAO.upperCountJop(proSn);
	}
	
	/**
	 *하위 카운트 
	 */
	@Override
	public String lowCountJob(String proSn) {
		return adprojDAO.lowCountJob(proSn); 
	}


	/**
	 *프로젝트 상세 멤버삭제
	 */
	@Transactional
	@Override
	public ServiceResult removeAdminProjMem(PmemberVO pmemVO) {
		ServiceResult result = null;
		  
		int status = adprojDAO.deleteAdminProjectMem(pmemVO);  
		if(status>0) {
			result = ServiceResult.OK;
		}else { 
			result = ServiceResult.FAIL;
		}
	return result;  
	}


	@Transactional 
	@Override
	public void updateProjectLeaderChange(PmemberVO pmemVO) {
		adprojDAO.updateProjectLeaderDel(pmemVO);
		adprojDAO.updateProjectLeaderChange(pmemVO);    
	}


	/** 
	 *프로젝트 멤버 조회  
	 */
	@Override
	public List<PmemberVO> projectPmemberSelect(String proSn) {
		
		return adprojDAO.projectPmemberSelect(proSn); 
	}


   
	@Override
	public List<PmemberVO> projectPmemberSelectleader(String proSn) {
		
		return adprojDAO.projectPmemberSelectleader(proSn);
	}


	@Override
	public List<PjobVO> retrieveJobList(String proSn) {
		return adprojDAO.selectJobList(proSn);
	}


	@Override
	public ChartVO retrieveCnt(String proSn) {
		return adprojDAO.selectJICnt(proSn);
	}
	 
	









}
