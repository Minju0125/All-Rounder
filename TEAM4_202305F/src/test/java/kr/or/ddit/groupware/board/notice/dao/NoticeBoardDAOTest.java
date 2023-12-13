package kr.or.ddit.groupware.board.notice.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import kr.or.ddit.AbstractRootContextTest;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class NoticeBoardDAOTest extends AbstractRootContextTest{
	
	@Inject
	private NoticeBoardDAO notice;
	
	@Test
	void testInsertNoticeBoard() {

		BoardVO boardVO = new BoardVO();
		boardVO.setBbsSj("공지사항 제목입니다!");
		boardVO.setBbsCn("공지사항 내용입니다!");
		boardVO.setEmpCd("admin");
		boardVO.setNoiceMustRead("N");
		boardVO.setBbsCategory("N");
		
		log.info("boardVO===============",boardVO);
		
		assertEquals(1, notice.insertNoticeBoard(boardVO));
		
	}
	
	@Test
	void testSelectNoticeBoard() {
		fail("Not yet implemented");
	}

	@Test
	void testSelectTotalRecord() {
		assertEquals(5, notice.selectTotalRecord(null));
	}

	@Test
	void testSelectNoticeBoardList() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateNoticeBoard() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteNoticeBoard() {
		fail("Not yet implemented");
	}

}
