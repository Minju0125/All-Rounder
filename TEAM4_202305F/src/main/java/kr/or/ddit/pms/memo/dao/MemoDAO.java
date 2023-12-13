package kr.or.ddit.pms.memo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.pms.MemoVO;

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
@Mapper
public interface MemoDAO {

	/**
	 * 메모 번호 최대값
	 * @return
	 */
	public int selectMaxNo();
	
	/**
	 * Z-INDEX 번호 최대값
	 * @return
	 */
	public int selectMaxZindex();
	
	/**
	 * 해당 사원 메모 전체 리스트 조회
	 * @param memoNo
	 * @return
	 */
	public List<MemoVO> selectListMemo(String memoNo);
	
	/**
	 * 메모 추가
	 * @param memoVO
	 * @return
	 */
	public int insertMemo(MemoVO memoVO);
	
	/**
	 * 메모 수정
	 * @param memoVO
	 * @return
	 */
	public int updateMemo(MemoVO memoVO);
	
	/**
	 * 메모 삭제
	 * @param memoVO
	 * @return
	 */
	public int deleteMemo(int memoNo);
	
	/**
	 * 메모 전체 삭제
	 * @param empCd
	 * @return
	 */
	public int deleteAll(String empCd);
}
