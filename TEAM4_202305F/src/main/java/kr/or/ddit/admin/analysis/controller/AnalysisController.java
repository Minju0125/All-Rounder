package kr.or.ddit.admin.analysis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.account.service.AccountService;
import kr.or.ddit.admin.analysis.service.AnalysisService;
import kr.or.ddit.vo.ChartVO;
import kr.or.ddit.vo.groupware.EmployeeVO;
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
 * 2023. 12. 04.    송석원         부서지표분석
 * Dec 5, 2023      박민주	   전년 월별 대비 자원의 사용률
 * 2023. 12. 05.    김보영         올해 입퇴사자, 부서별 근무시간
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Slf4j
@Controller
@RequestMapping("/analysis")
public class AnalysisController {
	
	@Inject
	private AnalysisService service;
	
	@Inject
	private AccountService aService;
	
	
	
	//지표분석 홈
	
	@GetMapping("home")
	public String analysisHome(
		Model model 	
		) {
		
		//부서별 인원 카운트 
		List<EmployeeVO> deptmemCount = service.emphavedept();
		model.addAttribute("deptmemCount", deptmemCount);
		log.info("부서지표분석을 볼까요?",deptmemCount);
		
		List<EmployeeVO> rankmemCount = service.emphaverank();
		model.addAttribute("rankmemCount", rankmemCount); 
		
		//직원 입사자 수 
		List<ChartVO> hireCnt = service.retrieveHireCnt();
		model.addAttribute("hireCnt", hireCnt);
		
		//직원 퇴사사자 수 
		List<ChartVO> leaveCnt = service.retrieveleaveCnt();
		model.addAttribute("leaveCnt", leaveCnt);
		
		//부서별 일 근무시간
		List<ChartVO> workTime = service.retrieveWorkTime();
		model.addAttribute("workTime",workTime);
		
		return "analysis/analysisHome";
	}
	
	@GetMapping("/monthlyUsageRateReserve/{reserve}")
	@ResponseBody
	public Map<String, Object> getMonthlyUsageRateReserve(@PathVariable String reserve){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if("vehicle".equals(reserve)) { //차량예약
			resultMap.put("reserveLast",service.retrieveVLastReservationHistory());
			resultMap.put("reserveThis",service.retrieveVThisReservationHistory());
		}else { //회의실 예약
			resultMap.put("reserveLast",service.retrieveCLastReservationHistory());
			resultMap.put("reserveThis",service.retrieveCThisReservationHistory());
		}
		return resultMap;
	}
}
