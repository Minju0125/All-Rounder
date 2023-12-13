package kr.or.ddit.pms.gantt.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.ddit.vo.pms.PjobVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml") 
class GanttDAOTest {

	@Inject
	private GanttDAO ganttDAO;
	
	@Test
	void testSelectPjobList() {
		PjobVO pj = new PjobVO();
//		pj.setJobuSn("23");
		List<PjobVO> res = ganttDAO.selectGanttchoice(pj); 
		log.info("{}",res);
	}

}
 