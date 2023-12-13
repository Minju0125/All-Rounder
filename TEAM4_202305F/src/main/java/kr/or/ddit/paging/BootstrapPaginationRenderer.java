package kr.or.ddit.paging;

import kr.or.ddit.vo.PaginationInfo;

public class BootstrapPaginationRenderer implements PaginationRenderer {
	private final String LISTART = "<li class='page-item %s'>";
	private final String ATAG = "<a class='page-link' href='javascript:;' onclick='fn_paging(%d);'>%s</a>";
	private final String NOOPATAG = "<a class='page-link' href='javascript:;'>%s</a>";
	private final String LIEND = "</li>";

	@Override
	public String renderPagination(PaginationInfo<?> paging) {
		int startPage = paging.getStartPage();
		int endPage = paging.getEndPage();
		int totalPage = paging.getTotalPage();
		int currentPage = paging.getCurrentPage();
		StringBuffer html = new StringBuffer();
		html.append("<nav aria-label='...'>");
		html.append("<ul class='pagination justify-content-center'>");
		// 이전 구간
		if(startPage>1) {
			html.append(String.format(LISTART, ""));
			html.append(String.format(ATAG, startPage-1, "Previous"));
		}else {
			html.append(String.format(LISTART, "disabled"));
			html.append(String.format(NOOPATAG, "Previous"));
		}
		html.append(LIEND);
		// 페이지 링크
		for(int page = startPage ; page <= endPage; page++) {
			if(page==currentPage) {
				html.append(String.format(LISTART, "active"));
				html.append(String.format(NOOPATAG, page));
			}else {
				html.append(String.format(LISTART, ""));
				html.append(String.format(ATAG, page, page));
			}
			
			html.append(LIEND);
		}
		// 다음 구간
		if(endPage<totalPage) {
			html.append(String.format(LISTART, ""));
			html.append(String.format(ATAG, endPage+1, "Next"));
		}else {
			html.append(String.format(LISTART, "disabled"));
			html.append(String.format(NOOPATAG, "Next"));
		}
		html.append(LIEND);
		
		html.append("</ul>");
		html.append("</nav>");
		return html.toString();
	}

}
