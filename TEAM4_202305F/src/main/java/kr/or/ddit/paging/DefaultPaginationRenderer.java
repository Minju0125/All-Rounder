package kr.or.ddit.paging;

import kr.or.ddit.vo.PaginationInfo;

public class DefaultPaginationRenderer implements PaginationRenderer {
	private final String PATTERN = "<a href='javascript:;' onclick='fn_paging(%d);'>%s</a>";

	@Override
	public String renderPagination(PaginationInfo<?> paging) {
		int startPage = paging.getStartPage();
		int endPage = paging.getEndPage();
		int totalPage = paging.getTotalPage();
		
		StringBuffer html = new StringBuffer();
		if(startPage > 1) {
			html.append(
				String.format(PATTERN, startPage-1, "이전")
			);
		}
		
		for(int page = startPage ; page <= endPage; page++) {
			html.append(
				String.format(PATTERN, page, page)
			);
		}
		
		if(endPage < totalPage) {
			html.append(
				String.format(PATTERN, endPage + 1, "다음")
			);
		}
		
		return html.toString();
	}

}
