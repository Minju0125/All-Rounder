package kr.or.ddit.groupware.mailing.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.MailAttachVO;
import kr.or.ddit.vo.groupware.MailReceptionVO;
import kr.or.ddit.vo.groupware.MailVO;
/**
 * 
 * @author 작성자명
 * @since 2023. 11. 15.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일                          수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 11. 15.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
@Mapper
public interface MailDAO {
	
	
	
	
	// --------------------------------------- 수신메일 목록 조회 ---------------------------------------

	/**
	 * 받은 메일 totalRecord 조회
	 * @param paging
	 * @return 받은 메일 갯수
	 */
	public int selectReceivedTotalRecord(PaginationInfo<MailVO> paging);
	
	/**
	 * 받은 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> selectReceptionMailList(PaginationInfo<MailVO> paging);
	
	// --------------------------------------- 발신메일 목록 조회 ---------------------------------------
	/**
	 * 보낸 메일 totalRecord 조회
	 * @param paging
	 * @return 보낸 메일 갯수
	 */
	public int selectSentTotalRecord(PaginationInfo<MailVO> paging);
	
	/**
	 * 보낸 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> selectSentMailList(PaginationInfo<MailVO> paging);
	
	// --------------------------------------- 휴지통 목록 조회 ---------------------------------------
	/**
	 * 보낸 메일 totalRecord 조회
	 * @param paging
	 * @return 보낸 메일 갯수
	 */
	public int selectTrashTotalRecord(PaginationInfo<MailVO> paging);
	
	/**
	 * 보낸 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> selectTrashMailList(PaginationInfo<MailVO> paging);
	
	
	// --------------------------------------- 중요 메일 목록 조회 ---------------------------------------
	/**
	 * 중요메일 메일 totalRecord 조회
	 * @param paging
	 * @return 중요메일 갯수
	 */
	public int selectImportantTotalRecord(PaginationInfo<MailVO> paging);
	
	/**
	 * 중요 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> selectImportantMailList(PaginationInfo<MailVO> paging);
	
	/**
	 * 신규 메일 작성 시, mail_tb 테이블에 insert
	 * @param mailVO
	 * @return 성공1, 실패0 반환
	 */
	public int insertMailTb(MailVO mailVO);

	/**
	 * 신규 메일 작성 시, mailRecpetion(수신자) 테이블에 insert
	 * @param mailRecptionVO
	 * @return 성공 시, 1이상 반환
	 */
	public int insertMailReception(MailReceptionVO mailRecptionVO);
	
	/**
	 * 신규메일 작성 시, MAIL_ATTACH 테이블에 insert
	 * @param mailVO
	 * @return 성공 시, 1이상 반환
	 */
	public int insertMailAttach(MailAttachVO mailAttachVO);
	
	/**
	 * 첨부파일 번호로 첨부파일 VO 찾기
	 * @param attNo
	 * @return
	 */
	public MailAttachVO selectMailAttach(String attNo);
	
	/**
	 * 메일 코드로 조회
	 * @param mailCd 메일번호
	 * @return
	 */
	public MailVO selectMailDetail(String mailCd);
	
	public MailReceptionVO selectReceptionMail(Map<String,String> paramMap);

	public int updateReceivedMailImportant(HashMap<String, String> paramMap);

	public int updateSendMailImportant(MailVO mailVO);
	
	public MailVO simpleSelectSentMail(String mailCd);

	/**
	 * 특정 메일 코드의 수신자 list 조회
	 * @param mailCd
	 * @return
	 */
	public List<MailReceptionVO> selectReceptionList(String mailCd);

	/**
	 * 보낸 메일 수정 (임시저장여부, 삭제여부)
	 * @param mail
	 * @return
	 */
	public int updateMail(MailVO mail);

	/**
	 * 보낸 메일 수정 (중요여부, 삭제여부, 읽음여부)
	 * @param recpetionMail
	 * @return
	 */
	public int updateReceptionMail(MailReceptionVO recpetionMail);
	
}