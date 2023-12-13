package kr.or.ddit.cal.service;

import java.util.List;

import kr.or.ddit.vo.CalCheckVO;
import kr.or.ddit.vo.CommonVO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.pms.CalendarVO;
import kr.or.ddit.vo.pms.ProjectVO;

public interface CalService {
	/**
	 * 일정 전체 조회
	 * @return
	 */
	public CalCheckVO selectList(CalCheckVO checkVO);
	
	/**
	 * 선택 타입 조회
	 * @return
	 */
	public List<CommonVO> selectListType();
	/**
	 * 타입 선택
	 * @param commonCodeCd
	 * @return
	 */
	public String selectType(String commonCodeCd);
	
	/**
	 * 상세 정보 조회
	 * @param scheduleCd
	 * @return
	 */
	public CalendarVO detailCal(String scheduleCd);
	
	/**
	 * 일정 추가
	 * @param calVO
	 * @return
	 */
	public int insertCal(CalendarVO calVO);
	
	/**
	 * 일정 삭제
	 * @param commonCodeCd
	 * @return
	 */
	public int deleteCal(String commonCodeCd);
	
	/**
	 * 일정 수정
	 * @param calVO
	 * @return
	 */
	public int updateCal(CalendarVO calVO);
	
	/**
	 * 검색 조건
	 * @param calVO
	 * @return
	 */
	public List<CalendarVO> searchCal(CalendarVO calVO);
	
	/**
	 * 프로젝트 목록 조회  
	 * @return
	 */
	public List<ProjectVO> selectProjectList(String empCd);
	
	/**
	 * 드래그 수정
	 * @param calVO
	 * @return
	 */
	public int dragUpdate(CalendarVO calVO);
}
