package kr.or.ddit.groupware.board.event.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.groupware.board.event.dao.EventBoardDAO;
import kr.or.ddit.groupware.board.notice.dao.NoticeBoardFileDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardFileVO;
import kr.or.ddit.vo.groupware.BoardVO;
import kr.or.ddit.vo.groupware.EmojiVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 20.      김보영         최초작성
 * 2023. 11. 25.      김보영         등록
 * 2023. 11. 28.      김보영         이모지 처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class EventBoardServiceImpl implements EventBoardService{
	
	private final EventBoardDAO dao;
	private final NoticeBoardFileDAO fileDAO;
	
	@Value("#{appInfo.boFiles}")
	private Resource boFiles;
	
	private void processBoFiles(BoardVO board) {
		List<BoardFileVO> fileList = board.getBoardFileList();
		if(fileList!=null) {
			fileList.forEach((f)->{
				try {
					f.setBbsNo(board.getBbsNo());
					fileDAO.insertFile(f);
					f.saveTo(boFiles.getFile());
				} catch(IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}
	
	@Transactional
	@Override
	public ServiceResult createEventBoard(BoardVO bVO) {
		
		//ck에디터때문에 생기는 쉼표 제거
		String originCn = bVO.getBbsCn();
		if(originCn != null && originCn.length()>0 && originCn.charAt(0) == ',') {
			originCn =originCn.substring(1);
		}
		bVO.setBbsCn(originCn);
		
		
		int rowcnt = dao.insertEventBoard(bVO);
		processBoFiles(bVO);
		
		ServiceResult result = null;
		
		if(rowcnt >=1) {
			result = ServiceResult.OK;
			log.info("등록성공");
		}else {
			result = ServiceResult.FAIL;
			log.info("등록실패");
		}
		return result;
	}

	@Override
	public List<BoardVO> retrieveEventList(PaginationInfo<BoardVO> paging) {
		
		//총 레코드수 구하기
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		
		//리스트 구하기
		List<BoardVO> dataList = dao.selectEventList(paging);
		return dataList;
	}

	@Override
	public BoardVO retrieveEventDetail(int bbsNo) {
		return dao.selectEventDetail(bbsNo);
	}

	@Override
	public BoardVO countEmoji(int bbsNo) {
		return dao.countEmoji(bbsNo);
	}

	@Override
	public ServiceResult modifyEmoji(EmojiVO eVO) {
		EmojiVO origin = eVO;
		int updateCnt = dao.updateEmoji(eVO);
		
		ServiceResult  result = null;
		
		//만약 처음으로 버튼을 눌렀다면 등록진행
		if(updateCnt < 1) {
			int insertCnt = dao.insertEmoji(origin);
			if(insertCnt >=1) {
				//등록성공
				result = ServiceResult.OK;
			}else {
				result = ServiceResult.FAIL;
			}
		}else {
			//변경성공
			result = ServiceResult.OK;
		}
		return result;
	}

	@Override
	public ServiceResult modifyEventBoard(BoardVO bVO) {
		
		int rowCnt = dao.updateEvent(bVO);
		ServiceResult result = null;
		
		if(rowCnt >=1) {
			result = ServiceResult.OK;
			log.info("수정성공");
		}else {
			result = ServiceResult.FAIL;
			log.info("수정실패");
		}
		return result;
	}

	@Override
	public ServiceResult removeEventBoard(int bbsNo) {
		ServiceResult result = null;
		dao.deleteEmoji(bbsNo);
		
		int rowCnt = dao.deleteEvent(bbsNo);
		if(rowCnt>0) {
			result = ServiceResult.OK;
			log.info("삭제성공");
		}else {
			result = ServiceResult.FAIL;
			log.info("삭제실패");
			
		}
		return result;
	}
}
