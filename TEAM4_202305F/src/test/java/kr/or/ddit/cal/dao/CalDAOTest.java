package kr.or.ddit.cal.dao;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml")
class CalDAOTest {

	@Inject
	private CalDAO dao;
	
	@Test
	void test() {
		dao.selectList("E221001005");
	}

}
