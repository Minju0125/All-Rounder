package kr.or.ddit.pms.memo.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.pms.memo.service.MemoService;
import kr.or.ddit.vo.pms.MemoVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/memo")
public class MemoController {

	@Inject
	private MemoService service;
	
	@GetMapping
	public String listMemo(Model model, Authentication principal) {
		String empCd = principal.getName();
		int selectMaxNo = service.selectMaxNo();
		log.info("MAXMAXMAXMAXMAX {}",selectMaxNo);
		int selectMaxZindex = service.selectMaxZindex();
		model.addAttribute("selectMaxNo", selectMaxNo);
		model.addAttribute("selectMaxZindex", selectMaxZindex);
		model.addAttribute("empCd", empCd);
		return "pms/memo/memo";
	}
	
	/**
	 * 해당 사원 메모 리스트 조회
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "list")
	@ResponseBody
	public List<MemoVO> selectListMemo(Authentication principal) {
		String empCd = principal.getName();
		List<MemoVO> memoList = service.selectListMemo(empCd);
		return memoList;
	}
	
	/**
	 * 메모 추가
	 * @param principal
	 * @param memoVO
	 * @return
	 */
	@PostMapping
	public String insertMemo(Authentication principal, @RequestBody MemoVO memoVO) {
		String empCd = principal.getName();	
		memoVO.setEmpCd(empCd);
		service.insertMemo(memoVO);
		return "jsonView";
	}
	
	@PutMapping
	@ResponseBody
	public String updateMemo(@RequestBody MemoVO memoVO) {
		int rslt = service.updateMemo(memoVO);
		String msg="NG";
		if(rslt==1) {
			msg="OK";
		}
		return msg;
	}
	
	@DeleteMapping
	public String deleteMemo(@RequestBody int memoNo) {
		service.deleteMemo(memoNo);
		return "jsonView";
	}
	
	@DeleteMapping("/all")
	@ResponseBody
	public void deleteAllMemo(@RequestBody String empCd) {
		System.out.println("★★"+empCd);
		service.deleteAll(empCd);
	}
}



