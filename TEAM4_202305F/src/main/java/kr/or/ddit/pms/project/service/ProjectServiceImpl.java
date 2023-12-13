package kr.or.ddit.pms.project.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.pms.project.dao.ProjectDAO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.RequiredArgsConstructor;

/**
 * @author 작성자명
 * @since 2023. 11. 8.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 8.      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

	private final ProjectDAO projectDAO;
	 


	@Override
	public List<ProjectVO> selectProjectList(String empCd) {
		return projectDAO.selectProjectList(empCd);
		
	}



	
	/**
	 *available 가 true일 때 업데이트가 가능
	 *하단의 상태가 3일 경우에서는 프로젝트의 완료 가능 여부를 판단
	 *판단방법
	 *완료시킬수 없는 프로젝트를 조회 
	 *그리고 현재 프로젝트가 조회된 프로젝트 리스트에 없으면 수정 가능
	 *
	 *
	 */
	@Override
	public ServiceResult modifyProjectStt(ProjectVO proj) {
		boolean available = true;
		if(proj.getProSttus().equals("3")) {
			List<ProjectVO> comProj = selectCompleteProject();
			available = !comProj.contains(proj); 
			 
		}
		int cnt = 0;
		
		if(available) {
			 cnt = projectDAO.updateProjectStt(proj);
		}
			
		
		ServiceResult result = null;
		if(cnt>0) {
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}


 
	@Override
	public String selectEmployeeCode(String proSn) {
		return projectDAO.selectEmployeeCode(proSn);    
	}



	@Override
	public String selectJACompleteCount(String proSn) {
		return projectDAO.selectJACompleteCount(proSn);
	}



	@Override
	public String selectJobCompleteCount(String proSn) {
		return projectDAO.selectJobCompleteCount(proSn);
	}



	@Override
	public List<ProjectVO> selectCompleteProject() {
		 
		return projectDAO.selectCompleteProject();
	}




 


	
	

}
