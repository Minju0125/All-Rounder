package kr.or.ddit.pms.project.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import kr.or.ddit.vo.pms.ProjectVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml") 
class ProjectDAOTest {

	@Inject
	private ProjectDAO projectDAO;
	
	@Test
	void testSelectProjectList() {
		projectDAO.selectProjectList("E220401001");
	}

}
