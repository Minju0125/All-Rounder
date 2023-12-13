package kr.or.ddit.pms.job.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.pms.job.dao.JobDAO;
import kr.or.ddit.pms.job.dao.JobFileDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.EmployeeVO;
import kr.or.ddit.vo.pms.ChargerVO;
import kr.or.ddit.vo.pms.IssueVO;
import kr.or.ddit.vo.pms.PAtchVO;
import kr.or.ddit.vo.pms.PLogVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.RequiredArgsConstructor;

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
 * 2023. 11. 16.    김보영         일감등록, 삭제, 수정
 * 2023. 11. 21.    김보영         일감상세
 * 2023. 11. 26.    김보영         파일처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Service
@RequiredArgsConstructor
public class jobServiceImpl implements JobService{
	
	private final JobDAO dao;
	
	private final JobFileDAO fDao;
	
	
	@Value("#{appInfo.jobFiles}")
	private Resource jobFiles;

	
	
	@Override
	public PjobVO retrieveJobCount(PjobVO pVO) {
		return dao.jobCount(pVO);
	}
	

	@Override
	public List<PmemberVO> retrieveProMember(PmemberVO pMVO) {
		return dao.proMemList(pMVO);
	}


	@Override
	public List<ChargerVO> retrieveCharger(int jobSn) {
		return dao.chargerList(jobSn);
	}


	@Override
	public List<PjobVO> retrieveJobList(PaginationInfo<PjobVO> paging) {
		
		String proSn = paging.getDetailCondition().getProSn();
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<PjobVO> dataList = dao.selectPjobList(paging);
		paging.setDataList(dataList);
		
		return dataList;
	}


	@Override
	public EmployeeVO retrieveWriter(String empCd) {
		return dao.selectWriter(empCd);
	}
	
//	@Transactional
	@Override
	public ServiceResult createJob(PjobVO jVO) {
		
		//날짜 변환
		String bData = jVO.getJobBdate();
		bData = bData.replace(String.valueOf("-"),"");
		jVO.setJobBdate(bData);
		
		String eData = jVO.getJobEdate();
		eData = eData.replace(String.valueOf("-"),"");
		jVO.setJobEdate(eData);
		
		
		if(jVO.getJobuSn() == null) { //상위일감이라면
			jVO.setJobStcd("6"); // 일감 상태를 요청으로만
		}
		
		
		int rowcnt = dao.insertJob(jVO);
		
		for(ChargerVO cVO : jVO.getChargerList()) {
			cVO.setJobSn(jVO.getJobSn());
			cVO.setProSn(jVO.getProSn());
			dao.insertCharger(cVO);
		}
		
		//일감의 파일등록
		try {
			processJobFiles(jVO);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//일감 완료일때 완료일자 넣기
		if(jVO.getJobuSn() != null) {//하위일감일때
			if(jVO.getJobStcd().equals("5")) {
				dao.updateCdate(jVO.getJobSn());
				System.out.println("완료찍혀?");
			}
		}
		
		
		ServiceResult result = null;
		
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
		
	}
	
	private void processJobFiles(PjobVO jVO) {
		List<PAtchVO> fileList = jVO.getJobFileList();
		if(fileList!=null) {
			fileList.forEach((atch)->{
				try {
					atch.setProJobsn(jVO.getJobSn());
					atch.setProFileCode(jVO.getProFileCd());
					fDao.insertJobFile(atch);
					atch.saveTo(jobFiles.getFile());
				}catch (IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}


	@Override
	public void createCharger(ChargerVO cVO) {
		//일감에 대한 담당자 등록
		dao.insertCharger(cVO);
	}


	@Override
	public int getJobSn() {
		return dao.selectJobSn();
	}


	
	@Transactional
	@Override
	public ServiceResult removeJob(PjobVO jVO) {
		int rowcnt = dao.deleteJob(jVO);
		
		ChargerVO cVO = new ChargerVO();
		cVO.setJobSn(jVO.getJobSn());
		cVO.setProSn(jVO.getProSn());
		dao.deleteCharger(cVO);
		
		ServiceResult result = null;
		
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}

	
	public void removeCharger(ChargerVO cVO) {
		dao.deleteCharger(cVO);
	}

	@Transactional
	@Override
	public ServiceResult modifyJob(PjobVO jVO) {
		
		String changerCd = jVO.getEmpCd1();
		//일감수정자이름 가져오기
		String changerNm = dao.selectChanger(changerCd);
		
		
		//로그만들기
		
		//1. 로그작성자등록
		PLogVO plVO = new PLogVO();
		plVO.setPlogWriter(changerNm);
		
		//2. 로그내용 만들기
		plVO.setProSn(jVO.getProSn()); //프로젝트순번
		plVO.setJobSn(jVO.getJobSn()); //일감순번
		
		//수정 전 데이터
		PjobVO beforePjobVO = dao.jobDetail(jVO);
		//수정 후 데이터
		PjobVO afterPjobVO = jVO;
		

		String log = ""; 
		
		//전과 후를 비교해서 기존의 담당자가 삭제되었는지 확인
		for(ChargerVO beforeChargerVO : dao.chargerList(beforePjobVO.getJobSn()) ) {
			boolean changed = false;
			for(ChargerVO afterChargerVO : jVO.getChargerList()) {
				if(beforeChargerVO.getEmpCd().equals(afterChargerVO.getEmpCd())) {
					changed= true;
				}
			}
			
			if(! changed) {
				String chargerName = dao.selectChanger(beforeChargerVO.getEmpCd());
				log += "담당자로 '"+chargerName+"' (이)가 삭제되었습니다.<br>";
			}
			
		}
		
		
		//전과 후를 비교해서 새로운 담당자가 추가되었는지 확인
		for(ChargerVO afterChargerVO : jVO.getChargerList()) {
			boolean changed = false;
			for(ChargerVO beforeChargerVO : dao.chargerList(jVO.getJobSn())) {
				if(afterChargerVO.getEmpCd().equals(beforeChargerVO.getEmpCd())) {
					changed = true;
				}
			}
			
			if(!changed) {
				//바뀐 담당자 이름 찾기 후 로그 담아주기 
				String chargerName = dao.selectChanger(afterChargerVO.getEmpCd());
				log += "담당자로 '"+chargerName+"' (이)가 추가되었습니다.<br>";
			}
		}

		

		
		
		//수정 전 후 데이터 비교
		//시작일 비교
		if(!beforePjobVO.getJobBdate().equals(afterPjobVO.getJobBdate())) {
			log += "시작일이 '"+afterPjobVO.getJobBdate()+"' 로 변경되었습니다.<br>";
		}else if(!beforePjobVO.getJobEdate().equals(afterPjobVO.getJobEdate())) {
			log += "종료일이 '"+afterPjobVO.getJobEdate()+"' 로 변경되었습니다.<br>";
		}else if(!beforePjobVO.getJobSj().equals(afterPjobVO.getJobSj())) {
			log += "일감명이 '"+afterPjobVO.getJobSj()+"' 로 변경되었습니다.<br>";
		}else if(!beforePjobVO.getJobProgrs().equals(afterPjobVO.getJobProgrs())) {
			log += "진행도가 '"+afterPjobVO.getJobProgrs()+"' %로 변경되었습니다.<br>";
		}else if(!beforePjobVO.getJobPriort().equals(afterPjobVO.getJobPriort())) {
			if(afterPjobVO.getJobPriort().equals("1")) {
				log += "우선순위가 '긴급' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobPriort().equals("2")) {
				log += "우선순위가 '높음' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobPriort().equals("3")) {
				log += "우선순위가 '보통' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobPriort().equals("4")) {
				log += "우선순위가 '낮음' 으로 변경되었습니다.<br>";
			}
		}else if ( (beforePjobVO.getJobStcd()!= null) &&(!beforePjobVO.getJobStcd().equals(afterPjobVO.getJobStcd())) ) {
			if(afterPjobVO.getJobStcd().equals("1")) {
				log += "일감상태가 '진행' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobStcd().equals("2")) {
				log += "일감상태가 '요청' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobStcd().equals("3")) {
				log += "일감상태가 '피드백' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobStcd().equals("4")) {
				log += "일감상태가 '보류' 으로 변경되었습니다.<br>";
			}else if(afterPjobVO.getJobStcd().equals("5")) {
				log += "일감상태가 '완료' 으로 변경되었습니다.<br>";
			}	
		}
		
		
		
		jVO.setJobBdate(jVO.getJobBdate().replaceAll("-", ""));
		jVO.setJobEdate(jVO.getJobEdate().replaceAll("-", ""));
		
		//일감 수정
		int rowcnt = dao.updateJob(jVO);
		
		for(PAtchVO file : jVO.getJobFileList()) {
			log += file.getProAtchnm()+" 파일이 추가되었습니다.<br>";
		}
		
		
		//담당자 삭제
		ChargerVO cdVO = new ChargerVO();
		cdVO.setJobSn(jVO.getJobSn());
		cdVO.setProSn(jVO.getProSn());
		dao.deleteCharger(cdVO);
		
		//담당자 추가
		for(ChargerVO cVO : jVO.getChargerList()) {
			cVO.setJobSn(jVO.getJobSn());
			cVO.setProSn(jVO.getProSn());
			dao.insertCharger(cVO);
		}
		
		//일감의 파일등록
		try {
			jVO.setProFileCd(beforePjobVO.getProFileCd());
			processJobFiles(jVO);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//로그 담아주기
		plVO.setPlogCn(log);
		//plog insert
		dao.insertPLog(plVO);
		
		
		//일감 완료일때 완료일자 넣기
		if(jVO.getJobuSn() != null) {//하위일감일때
			if(jVO.getJobStcd().equals("5")) {
				dao.updateCdate(jVO.getJobSn());
				System.out.println("완료찍혀?");
			}
		}
		
		
		ServiceResult result = null;
		
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}


	

	@Override
	public List<PjobVO> retrieveMyJob(ChargerVO cVO) {
		return dao.myJobList(cVO);
	}



	@Override
	public List<PjobVO> retrieveUJobList(String proSn,String loginEmpCd) {
		return dao.UJobList(proSn , loginEmpCd);
	}


	@Override
	public ProjectVO retrieveProject(String proSn) {
		return dao.projectInfo(proSn);
	}


	@Override
	public PjobVO retrievejobDetail(PjobVO dJob) {
		return dao.jobDetail(dJob);
	}


	@Override
	public List<PjobVO> retrieveLowJList(int jobSn) {
		return dao.rowJList(jobSn);
	}


	@Override
	public List<PLogVO> retrieveLogList(int jobSn) {
		return dao.selectLogList(jobSn);
	}


	@Override
	public PAtchVO retrieveFile(PAtchVO pAVO) {
		return fDao.selectFile(pAVO);
	}


	@Override
	public List<PAtchVO> retrieveFileList(PAtchVO pAVO) {
		return fDao.selectFileList(pAVO);
	}


	@Override
	public List<IssueVO> retrieveRefIssueList(String proSn) {
		return dao.selectRefIssueList(proSn);
	}


	@Override
	public List<PjobVO> retrieveReq(String loginEmpCd) {
		return dao.selectMyReq(loginEmpCd);
	}


	@Override
	public ServiceResult removeFile(PAtchVO pVO ,String loginEmpCd) {
		
		ServiceResult result = null;
		
		String log = ""; 
		
		//1. 로그작성자등록
		PLogVO plVO = new PLogVO();
		
		EmployeeVO findName = dao.selectWriter(loginEmpCd);
		
		String empName= findName.getEmpName();
		
		plVO.setPlogWriter(empName);
		plVO.setJobSn(pVO.getProJobsn());
		plVO.setProSn(pVO.getProSn());
		
		PAtchVO newPVO =  fDao.selectFile(pVO);
		String fileNM= newPVO.getProAtchnm();
		
		
		log += fileNM+" 파일이 삭제되었습니다.<br>";
		
		//로그 담아주기
		plVO.setPlogCn(log);
		//plog insert
		dao.insertPLog(plVO);
		
		int rowcnt = fDao.deleteFile(pVO);
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;	
	}


	
	
	
	
	

}
