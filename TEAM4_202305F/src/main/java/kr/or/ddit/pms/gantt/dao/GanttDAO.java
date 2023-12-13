package kr.or.ddit.pms.gantt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.ddit.vo.SearchVO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;

@Mapper
public interface GanttDAO {

	
	
	/**
	 * 프로젝트 일감 조회
	 * @return
	 */
	public List<PjobVO> selectPjobList(String proSn);
	
	
	/**
	 * 프로젝트 간트 선택
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> selectGanttchoice(PjobVO pjobVO); 
	
	
}
