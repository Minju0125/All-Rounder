package kr.or.ddit.pms.job.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ddit.common.enumpkg.ServiceResult;
import kr.or.ddit.vo.pms.PAtchVO;

/**
 * @author 작성자명
 * @since 2023. 11. 16.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet 
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	수정자         수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 16.    김보영         최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */


@Mapper
public interface JobFileDAO {
	
	
	/**
	 * 파일 삭제
	 * @param pVO
	 * @return
	 */
	public int deleteFile(PAtchVO pVO);
	
	/**
	 * 일감의 파일 등록
	 * @param pAVO
	 * @return
	 */
	public int insertJobFile(PAtchVO pAVO);

	/**
	 * 파일조회
	 * @param pAVO
	 * @return
	 */
	public PAtchVO selectFile(PAtchVO pAVO);
	
	
	/**
	 * 파일 리스트 조회
	 * @param pAVO
	 * @return
	 */
	public List<PAtchVO> selectFileList(PAtchVO pAVO);
	
}
