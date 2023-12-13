package kr.or.ddit.company.org.dao;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml")
class OrgDAOTest {

	@Inject
	private OrgDAO dao;
	
	@Test
	void test() {
		dao.selectListOrg();
	}

}
