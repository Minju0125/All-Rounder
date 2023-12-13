package kr.or.ddit.pms.memo.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.pms.memo.dao.MemoDAO;
import kr.or.ddit.vo.pms.MemoVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 16.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 16.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
public class MemoServiceImpl implements MemoService {

	@Inject
	private MemoDAO dao;
	
	@Override
	public List<MemoVO> selectListMemo(String memoNo) {
		return dao.selectListMemo(memoNo);
	}

	@Override
	public int insertMemo(MemoVO memoVO) {
		return dao.insertMemo(memoVO);
	}

	@Override
	public int updateMemo(MemoVO memoVO) {
		return dao.updateMemo(memoVO);
	}

	@Override
	public int selectMaxNo() {
		int maxNo = dao.selectMaxNo();
		return maxNo;
	}

	@Override
	public int selectMaxZindex() {
		return dao.selectMaxZindex();
	}

	@Override
	public int deleteMemo(int memoNo) {
		return dao.deleteMemo(memoNo);
	}

	@Override
	public int deleteAll(String empCd) {
		return dao.deleteAll(empCd);
	}


	
}
