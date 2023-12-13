package kr.or.ddit.pms.gantt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.or.ddit.pms.gantt.dao.GanttDAO;
import kr.or.ddit.vo.pms.PjobVO;
import kr.or.ddit.vo.pms.ProjectVO;
import lombok.RequiredArgsConstructor;

/**
 * @author 작성자명
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.      송석원       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Service
@RequiredArgsConstructor
public class GanttServiceImpl implements GanttService{
	
	private final GanttDAO ganttDAO;

	@Override
	public List<PjobVO> retrievePjobList(String proSn) {
		return ganttDAO.selectPjobList(proSn);
	}

	@Override
	public List<PjobVO> retrievePjobGanttChoice(PjobVO pjobVO) {
		return ganttDAO.selectGanttchoice(pjobVO); 
	}

	

}
