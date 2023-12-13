package kr.or.ddit.groupware.board.freeboard.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.groupware.board.freeboard.dao.FreeBoardDAO;
import kr.or.ddit.groupware.board.notice.service.NoticeBoardServiceImpl;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.AnswerVO;
import kr.or.ddit.vo.groupware.BoardFileVO;
import kr.or.ddit.vo.groupware.BoardVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FreeBoardServiceImpl implements FreeBoardService {
	
	@Inject
	private FreeBoardDAO freeDAO;
	
	@Value("#{appInfo.freeBoardFile}")
	private Resource freeBoardFile;
	


	@Override
	public List<BoardVO> selectFreeBoardList(PaginationInfo<BoardVO> paging) {
		log.info("$$$$ {}", paging);
		int totalRecord = freeDAO.selectTotalFree(paging);
		log.info("#### {}", totalRecord);
		paging.setTotalRecord(totalRecord);
		List<BoardVO> dataList = freeDAO.selectFreeBoardList(paging);
		log.info("*** {}", dataList);
		return dataList;
	}

	@Override
	public BoardVO selectFreeBoard(int bbsNo) {
		log.info("%%%%%%%%%%%%%% {}",bbsNo);
		freeDAO.rdcntUpdate(bbsNo);
		return freeDAO.selectFreeBoard(bbsNo);
	}

	@Override
	public int answerInsert(AnswerVO answerVO) {
		String bbsNo = freeDAO.answerMax(answerVO.getBbsNo());
		answerVO.setAnswerCode(bbsNo);
		log.info("★★★ {}",answerVO);
		String newCode = answerVO.getAnswerCode();
		
		String[] arr = newCode.split("\\_");
		System.out.println(arr);
		log.info("&&&&&&&&&&& {}", Arrays.toString(arr));
		if(arr.length == 1) {
			newCode = newCode + "1";
			log.info("|||| {}", newCode);
			answerVO.setAnswerCode(newCode);
		}
		
		return freeDAO.answerInsert(answerVO);
	}

	@Override
	public int replyInsert(AnswerVO answerVO) {
		String replyMax = freeDAO.replyMax(answerVO.getAnswerUpcode());
		log.info("%%%%%%%%%%%%%%%{}",replyMax);
		
		String setReply = null;
		
		int aCount = replyMax.length() - replyMax.replace("_", "").length();
		log.info("))))))) {}",aCount);
		if(aCount == 1) {
			setReply = replyMax + "_1";
		}else {
			setReply = replyMax;
		}		
		answerVO.setAnswerCode(setReply);
		return freeDAO.replyInsert(answerVO);
	}

	@Override
	@Transactional
	public void answerDelete(AnswerVO answerVO) {
		log.info("%%%%%%%%%%%%% {}", answerVO);
		String replyCode = answerVO.getAnswerCode();
		int cnt = freeDAO.replyAllDelete(replyCode);
		if(cnt > 0) {
			freeDAO.answerAllDelete(answerVO);			
		}
		freeDAO.answerAllDelete(answerVO);
	}

	@Override
	public int answerUpdate(AnswerVO answerVO) {
		log.info("######Update {}",answerVO);
		return freeDAO.answerUpdate(answerVO);
	}

	@Override
	public int freeboardInsert(BoardVO boardVO) throws IOException {
		int status = freeDAO.freeboardInsert(boardVO);
		log.info("&&&&&&&&&&&&&&&&& {}",status);
		if(status > 0) {	// 등록 성공
			MultipartFile imgFile = boardVO.getFreeboardImgFile();
			if(imgFile != null && !imgFile.isEmpty()) {
				BoardFileVO fileVO = new BoardFileVO();
				fileVO.setBbsNo(boardVO.getBbsNo());
				fileVO.setFileName(imgFile.getOriginalFilename());
				fileVO.setFileMime(imgFile.getContentType());
				fileVO.setFileSize(imgFile.getSize());
				log.info("^^^^^^^^^^^^^^^^^^^");
				String fileName = uploadFile(imgFile);
				fileVO.setFileFancysize(FileUtils.byteCountToDisplaySize(imgFile.getSize()));
				fileVO.setFileSavename(fileName);
				freeDAO.freeboardInsertFile(fileVO);
			}
		}

		return status;
	}
	
	private String uploadFile(MultipartFile item) throws IOException {
		String fileName = "";
		
		File file = new File(freeBoardFile.getFile().getAbsolutePath());
		if(!file.exists()) {
			file.mkdirs();
		}
		
		UUID uuid = UUID.randomUUID();
		fileName = uuid.toString() + "_" + item.getOriginalFilename();
		String saveLocate = file + "/" + fileName;
		log.info("*****************{}",saveLocate);
		File target = new File(saveLocate);
		
		item.transferTo(target);
		
		return fileName; 
	}

}
