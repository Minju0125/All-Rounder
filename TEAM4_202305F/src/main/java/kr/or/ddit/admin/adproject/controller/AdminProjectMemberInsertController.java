package kr.or.ddit.admin.adproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.admin.adproject.service.AdminProjectService;
import kr.or.ddit.messenger.service.MessengerService;
import kr.or.ddit.vo.ChatParticipantVO;
import kr.or.ddit.vo.pms.PmemberVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/adminpmember") 	 
public class AdminProjectMemberInsertController {
	 
	@Inject
	private AdminProjectService service;
	
	
	@Transactional
	@ResponseBody
	@PostMapping
	public String makeAjaxProjectPmem(@RequestBody ProjectVO proj) {
		  
		log.info("체크:{}",proj); 
		   
	   List<PmemberVO> pMemList=	proj.getPmemberList();
	   
	   service.insertAfterAdProjectPmember(proj); 
		
//		for (PmemberVO pmemberVO : pMemList) {
//			 pmemberVO.setProSn(proj.getProSn());
//		}
		  
	  
		return "ok"; 
	}
	
 	
}
