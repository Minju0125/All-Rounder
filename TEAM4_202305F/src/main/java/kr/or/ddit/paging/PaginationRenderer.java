package kr.or.ddit.paging;

import kr.or.ddit.vo.PaginationInfo;

public interface PaginationRenderer {
	/**
	 * PaginationInfo 의 프로퍼티(startPage~endPage)에 따라 페이지 링크를 생성.
	 * @param paging
	 * @return
	 */
	public String renderPagination(PaginationInfo<?> paging);
}
