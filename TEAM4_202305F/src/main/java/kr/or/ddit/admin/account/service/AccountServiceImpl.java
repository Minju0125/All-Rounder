package kr.or.ddit.admin.account.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.admin.account.UserNotFoundException;
import kr.or.ddit.admin.account.dao.AccountDAO;
import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.groupware.DeptVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 15.    김보영         최초작성
 * 2023. 11. 15.    전수진         직원정보 상세조회 추가
 * 2023. 11. 18.    김보영         직원정보 목록조회 추가
 * 2023. 11. 19.    김보영         직원정보 CRUD
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
	
	
	@Value("#{appInfo.accountImagesUrl}")
	private String accountImagesUrl;
	
	@Value("#{appInfo.accountImagesUrl}")
	private Resource accountImages;
	
	@Inject
	private PasswordEncoder encoder;
	
	
	private final AccountDAO dao;



	@Override
	public EmployeeVO retrieveMember(String empCd) {
		EmployeeVO employee = dao.selectEmployee(empCd);
		if(employee==null)
			throw new UserNotFoundException(empCd);
		return employee;
	}


	@Override
	public List<EmployeeVO> retrieveEmpList() {
		return dao.selectEmpList();
	}


	@Override
	public ServiceResult insertEmpExcel(EmployeeVO excelEmployeeVO) {
		
		String poiPW =  excelEmployeeVO.getEmpBirth().substring(2, 8);
		
		//신규비밀번호통일
		excelEmployeeVO.setEmpPw(encoder.encode(poiPW));
		
		//민주 이메일로 
		excelEmployeeVO.setEmpEmailSecond("bagminju046@gmail.com");
		
		int rowcnt = dao.insertEmpExcel(excelEmployeeVO);
		
		ServiceResult result = null;
		
		if(rowcnt >=1) {
			result = ServiceResult.OK;
			log.info("포이성공");
		}else {
			result = ServiceResult.FAIL;
			log.info("포이실패");
		}
		return result;
	}

	

	@Override
	public ServiceResult createEmp(EmployeeVO emp) {
		
		String originPw = emp.getEmpPw();
		emp.setEmpPw(encoder.encode(originPw));
		System.out.println("암호화댓니?"+emp.getEmpPw());
		
		//날짜 변환
		String HData = emp.getEmpHiredate();
		HData = HData.replace(String.valueOf("-"),"");
		emp.setEmpHiredate(HData);
		
		
		//생년월일 변환
		String getBirth[] = emp.getEmpSsn().split("-");
		String birth = getBirth[0];
		String birthYear = getBirth[1];
	    emp.setEmpBirth((birthYear.startsWith("1") || birthYear.startsWith("2")? "19" : "20")+birth);
	    
	    //민주 이메일로 
	    emp.setEmpEmailSecond("bagminju046@gmail.com");
	
		try {
			//프로필 파일
			if(!emp.getEmpProfileImage().isEmpty()) {
				String filename = UUID.randomUUID().toString();
				File saveFile = new File(accountImages.getFile(), filename);
				emp.getEmpProfileImage().transferTo(saveFile);
				emp.setEmpProfileImg(accountImagesUrl+"/"+filename);
			}
			
			//Qr파일
			if(!emp.getEmpQrImage().isEmpty()) {
				String filename = UUID.randomUUID().toString();
				File saveFile = new File(accountImages.getFile(), filename);
				emp.getEmpQrImage().transferTo(saveFile);
				emp.setEmpQr(accountImagesUrl+"/"+filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int rowcnt = dao.insertEmp(emp);
		
		ServiceResult result = null;
		
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}


	@Override
	public ServiceResult removeEmp(String empCd) {
		
		int rowcnt =dao.deleteEmp(empCd);
		ServiceResult result = null;
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}


	@Override
	public List<DeptVO> deptList() {
		return dao.selectDeptList();
	}


	@Override
	public List<CommonVO> rankList() {
		return dao.selectRankList();
	}


	@Override
	public List<EmployeeVO> empSuprrList(String deptCd) {
		return dao.selectSuprrList(deptCd);
	}


	@Override
	public ServiceResult modifyEmp(EmployeeVO emp) {
		
		String originPw = emp.getEmpPw();
		emp.setEmpPw(encoder.encode(originPw));
		System.out.println("암호화댓니?"+emp.getEmpPw());
		
		//날짜 변환
		String HData = emp.getEmpHiredate();
		HData = HData.replace(String.valueOf("-"),"");
		emp.setEmpHiredate(HData);
		
		
		//생년월일 변환
		String getBirth[] = emp.getEmpSsn().split("-");
		String birth = getBirth[0];
		String birthYear = getBirth[1];
	    emp.setEmpBirth((birthYear.startsWith("1") || birthYear.startsWith("2")? "19" : "20")+birth);
	    
	    
		
		try {
			//프로필 파일
			if(!emp.getEmpProfileImage().isEmpty()) {
				String filename = UUID.randomUUID().toString();
				File saveFile = new File(accountImages.getFile(), filename);
				emp.getEmpProfileImage().transferTo(saveFile);
				emp.setEmpProfileImg(accountImagesUrl+"/"+filename);
			}
			
			//Qr파일
			if(!emp.getEmpQrImage().isEmpty()) {
				String filename = UUID.randomUUID().toString();
				File saveFile = new File(accountImages.getFile(), filename);
				emp.getEmpQrImage().transferTo(saveFile);
				emp.setEmpQr(accountImagesUrl+"/"+filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int rowcnt = dao.updateEmp(emp);
		
		ServiceResult result = null;
		
		if(rowcnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		return result;
	}


	
	

}
