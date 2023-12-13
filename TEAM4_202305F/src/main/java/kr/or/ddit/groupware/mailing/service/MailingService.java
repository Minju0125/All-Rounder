package kr.or.ddit.groupware.mailing.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.PaginationInfo;
import kr.or.ddit.vo.groupware.MailAttachVO;
import kr.or.ddit.vo.groupware.MailReceptionVO;
import kr.or.ddit.vo.groupware.MailVO;


public interface MailingService {

	/**
	 * 수신 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> retrieveRecptionMailList(PaginationInfo<MailVO> paging);
	
	/**
	 * 발신 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public PaginationInfo<MailVO> retrieveSentMailList(PaginationInfo<MailVO> paging);
	
	/**
	 * 중요 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> retrieveImpotantMailList(PaginationInfo<MailVO> paging);
	
	/**
	 * 삭제 메일 목록 조회
	 * @param paging
	 * @return
	 */
	public List<MailVO> retrieveTrashMailList(PaginationInfo<MailVO> paging);
	
	/**
	 * 메일코드로 받은메일 조회
	 * @param Map<String,String> paramMap
	 * @return
	 */
	public MailReceptionVO retrieveReceptionMail(Map<String,String> paramMap);
	
	/**
	 * 메일 번호를 통해 메일 상세 정보 불러오기
	 * @param mailCd
	 * @return
	 */
	public MailVO retrieveMailDetail(String mailCd);
	
	/**
	 * 보낸 메일함에서 특정 메일 수정
	 * @param mail
	 * @return
	 */
	public int updateMail(MailVO mail);
	
	/**
	 * 받은 메일함에서 특정 메일 수정
	 * @param recpetionMail
	 * @return
	 */
	public int updateReceptionMail(MailReceptionVO recpetionMail);
	
	
	/**
	 * SMTP 서버를 이용한 메일 전송 메소드
	 */
	public ServiceResult mailSend(MailVO mailVO);

	/**
	 * 메일관련 테이블에 insert 하는 메소드
	 * MAIL_ATTACH, MAIL_RECEPTION, MAIL_TB에 insert 하는 메소드
	 * @param mailVO
	 */
	public void createMail(MailVO mailVO);
	
	

	/**
	 * IMAP 서버를 이용하여 메일 불러오기
	 * @return
	 */
	public MailVO getMAil();



	/**
	 * 첨부파일 번호로 첨부파일 한건 조회
	 * @param attchNo
	 * @return
	 */
	public MailAttachVO retrieveMailAttach(String attchNo);


}
