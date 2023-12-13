package kr.or.ddit.groupware.mypage.service;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.mypage.dao.MypageDAO;
import kr.or.ddit.vo.groupware.EmployeeVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 28.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
public class MypageServiceImpl implements MypageService {

	@Value("#{appInfo.accountImagesUrl}")
	private String accountImagesUrl;
	
	@Value("#{appInfo.accountImagesUrl}")
	private Resource accountImages;
	
	@Inject
	private PasswordEncoder encoder;
	
	@Inject
	private MypageDAO dao;
	@Override
	public EmployeeVO selectMypage(String empCd) {
		return dao.selectMypage(empCd);
	}
	@Override
	public ServiceResult updateMypage(EmployeeVO emp) {
		log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@ : {}",emp);
		String password = emp.getEmpPw();
		emp.setEmpPw(encoder.encode(password));
		log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%% :{} ",emp);
		
		
		ServiceResult result = null;
		
		try {
			//프로필 파일
			if(!emp.getEmpProfileImage().isEmpty()) {
				String filename = UUID.randomUUID().toString();
				File saveFile = new File(accountImages.getFile(), filename);
				emp.getEmpProfileImage().transferTo(saveFile);
				emp.setEmpProfileImg(accountImagesUrl+"/"+filename);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		int cnt = dao.updateMypage(emp);
		
		
		
		if(cnt >=1){
			result = ServiceResult.OK;
		}else {
			result = ServiceResult.FAIL;
		}
		
		return result;
	}
	@Override
	public EmployeeVO authenticateBoard(EmployeeVO emp) {
		
		log.info("★★★★★: {}",emp);
		EmployeeVO savedMypage = dao.selectMypage(emp.getEmpCd());
		log.info("#######: {}",savedMypage);
		String encodedPassword = savedMypage.getEmpPw();
		log.info("^^^^^^^^: {}",encodedPassword);
		
		String a = encoder.encode(encodedPassword);		
		log.info("))))))))))))))))) {}",a);
		
		
		String rawPassword = emp.getEmpPw();
		log.info("********: {}",rawPassword);
		if(encoder.matches(rawPassword, encodedPassword)) {
			log.info("같당!");
			return savedMypage;
		}else {
			log.info("다르당");
			return null;
		}
		

	}

}
