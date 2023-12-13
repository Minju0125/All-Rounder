package kr.or.ddit.pms.gantt.service;

import java.util.List;

import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;

public interface GanttService {
	
	/**
	 *  일감 조회
	 * @return
	 */
	public List<PjobVO> retrievePjobList(String proSn);
	
	
	/**
	 * 간트선택
	 * @param proSn
	 * @return
	 */
	public List<PjobVO> retrievePjobGanttChoice(PjobVO pjobVO);

}
