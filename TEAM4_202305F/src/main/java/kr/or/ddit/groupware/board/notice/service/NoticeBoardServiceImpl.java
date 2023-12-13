package kr.or.ddit.groupware.board.notice.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.common.exception.BoardNotFoundException;
import kr.or.ddit.groupware.board.notice.dao.NoticeBoardDAO;
import kr.or.ddit.groupware.board.notice.dao.NoticeBoardFileDAO;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.BoardFileVO;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 전수진
 * @since 2023. 11. 7.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일            수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 7.    전수진       최초작성
 * 2023. 11.12.    전수진       상단고정 기본값 설정
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeBoardServiceImpl implements NoticeBoardService {
	
	private final NoticeBoardDAO noticeDAO;
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
	public void createNoticeBoard(BoardVO board) {
		// 공지사항 게시글등록
		board.setBbsCategory("N"); // 공지사항이니까 카테고리 설정

		if(board.getNoiceMustRead()==null || board.getNoiceMustRead().trim().isEmpty()) {
			board.setNoiceMustRead("N");	// 상단고정 체크여부에 따른 기본값설정
		}
		noticeDAO.insertNoticeBoard(board);
		processBoFiles(board);
		
	}

	@Override
	public BoardVO retrieveNoticeBoard(int bbsNo) {
		// 공지사항 게시글 1개 조회
		BoardVO board = noticeDAO.selectNoticeBoard(bbsNo);
		
		if(board==null) // 조회한 글이 없을경우
			throw new BoardNotFoundException(HttpStatus.NOT_FOUND, String.format("%s 해당 게시글이 없음", bbsNo));
		
		noticeDAO.incrementHit(bbsNo);
		
		return board;
	}

	@Override
	public BoardFileVO retrieveBoardFile(String fileCode) {
		BoardFileVO file = fileDAO.selectBoardFile(fileCode);
		if(file==null) 
			throw new BoardNotFoundException(HttpStatus.NOT_FOUND, String.format("%s 해당 파일이 없음", fileCode));
		fileDAO.incrementDowncount(fileCode);
	
		return file;
	}

	@Override
	public List<BoardVO> retrieveNoticeBoardList(PaginationInfo<BoardVO> paging) {
		int totalRecord = noticeDAO.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		List<BoardVO> dataList = noticeDAO.selectNoticeBoardList(paging);
		paging.setDataList(dataList);

		return dataList;
	}

	@Transactional
	@Override
	public void modifyNoticeBoard(BoardVO board) {
		noticeDAO.updateNoticeBoard(board);
		if(board.getNoiceMustRead()==null || board.getNoiceMustRead().trim().isEmpty()) {
			board.setNoiceMustRead("N");	// 상단고정 체크여부에 따른 기본값설정
		}
		processBoFiles(board);
	}
	
	private void processDeleteFile(int bbsNo) {
		BoardVO savedBoard = noticeDAO.selectNoticeBoard(bbsNo);
		if (savedBoard != null && savedBoard.getBoardFileList() != null) {
			savedBoard.getBoardFileList().forEach((f)->{
				String saveName = f.getFileSavename();
				fileDAO.deleteBoardFile(f.getFileCode());
				try {
					FileUtils.deleteQuietly(new File(boFiles.getFile(), saveName));
				} catch(IOException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}
	
	@Transactional
	@Override
	public ServiceResult removeNoticeBoard(int bbsNo) {
		ServiceResult result = null;
		int status = noticeDAO.deleteNoticeBoard(bbsNo);
		if(status > 0 ) {
			processDeleteFile(bbsNo);
			result = ServiceResult.OK;
		} else {
			result = ServiceResult.FAIL;
		}
		return result;
	
	}

}
