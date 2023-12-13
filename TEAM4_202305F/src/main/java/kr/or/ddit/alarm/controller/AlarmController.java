package kr.or.ddit.alarm.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.alarm.service.AlarmService;
import kr.or.ddit.vo.AlarmVO;

/**
 * @author 전수진
 * @since 2023. 12. 2.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일           수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 2.   전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Controller
@RequestMapping("/alarm")
public class AlarmController {
	
	@Inject
	private AlarmService service;
	
	@GetMapping
	public List<AlarmVO> retrieveAlarmList(Authentication authentication){
		String loginCd = authentication.getName();
		
		return service.retrieveAlarmList(loginCd);
	}
	
	

}
