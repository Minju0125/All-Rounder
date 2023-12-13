package kr.or.ddit.spring;

import static org.junit.jupiter.api.Assertions.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringJUnitWebConfig(locations = "file:src/main/resources/kr/or/ddit/spring/*-context.xml")
class RootContainerLoadTest {

	@Resource(name="dataSource")
	private DataSource dataSource;
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	
	@Test
	void test() {
	     log.info("dataSource : {}",dataSource);
	     log.info("sqlSession : {}",sqlSession);
	}

}
