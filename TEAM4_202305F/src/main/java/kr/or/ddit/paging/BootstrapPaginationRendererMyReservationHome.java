package kr.or.ddit.paging;

import java.util.Map;

import kr.or.ddit.vo.PaginationInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * MyReservationHome 에서 사용할 BootstrapPaginationRenderer
 * 
 * @author 작성자명
 * @since 2023. 12. 1.
 * @version 1.0
 * @see javax.servlet.http.HttpServlet
 * 
 *      <pre>
 * [[개정이력(Modification Information)]]
 * 수정일             수정자               수정내용
 * --------     --------    ----------------------
 * 2023. 12. 1.      박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 *      </pre>
 */
@Slf4j
public class BootstrapPaginationRendererMyReservationHome implements PaginationRenderer {
	private final String LISTART = "<li class='page-item %s'>";
	private final String NOOPATAG = "<a class='page-link' href='javascript:;'>%s</a>";
	private final String LIEND = "</li>";

	private final String ATAG_VHCLE = "<a class='page-link' href='javascript:;' onclick='fn_pagingV(%d);'>%s</a>";
	private final String ATAG_CONF = "<a class='page-link' href='javascript:;' onclick='fn_pagingC(%d);'>%s</a>";

	@Override
	public String renderPagination(PaginationInfo<?> paging) {
		int startPage = paging.getStartPage();
		int endPage = paging.getEndPage();
		int totalPage = paging.getTotalPage();
		int currentPage = paging.getCurrentPage();
		StringBuffer html = new StringBuffer();
		html.append("<nav aria-label='...'>");
		html.append("<ul class='pagination justify-content-center'>");

		Map<String, Object> variousConditionMap = paging.getVariousCondition();
		String aria = String.valueOf(variousConditionMap.get("aria"));

		if ("conf".equals(aria)) { // 회의실 예약 목록에서 사용할 renderer
			// 이전 구간
			if (startPage > 1) {
				html.append(String.format(LISTART, ""));
				html.append(String.format(ATAG_CONF, startPage - 1, "Previous"));
			} else {
				html.append(String.format(LISTART, "disabled"));
				html.append(String.format(NOOPATAG, "Previous"));
			}
			html.append(LIEND);
			// 페이지 링크
			for (int page = startPage; page <= endPage; page++) {
				if (page == currentPage) {
					html.append(String.format(LISTART, "active"));
					html.append(String.format(NOOPATAG, page));
				} else {
					html.append(String.format(LISTART, ""));
					html.append(String.format(ATAG_CONF, page, page));
				}
				html.append(LIEND);
			}
			// 다음 구간
			if (endPage < totalPage) {
				html.append(String.format(LISTART, ""));
				html.append(String.format(ATAG_CONF, endPage + 1, "Next"));
			} else {
				html.append(String.format(LISTART, "disabled"));
				html.append(String.format(NOOPATAG, "Next"));
			}
			html.append(LIEND);

			html.append("</ul>");
			html.append("</nav>");
		} else { // 차량 예약 목록에서 사용할 renderer
			// 이전 구간
			if (startPage > 1) {
				html.append(String.format(LISTART, ""));
				html.append(String.format(ATAG_VHCLE, startPage - 1, "Previous"));
			} else {
				html.append(String.format(LISTART, "disabled"));
				html.append(String.format(NOOPATAG, "Previous"));
			}
			html.append(LIEND);
			// 페이지 링크
			for (int page = startPage; page <= endPage; page++) {
				if (page == currentPage) {
					html.append(String.format(LISTART, "active"));
					html.append(String.format(NOOPATAG, page));
				} else {
					html.append(String.format(LISTART, ""));
					html.append(String.format(ATAG_VHCLE, page, page));
				}
				html.append(LIEND);
			}
			// 다음 구간
			if (endPage < totalPage) {
				html.append(String.format(LISTART, ""));
				html.append(String.format(ATAG_VHCLE, endPage + 1, "Next"));
			} else {
				html.append(String.format(LISTART, "disabled"));
				html.append(String.format(NOOPATAG, "Next"));
			}
			html.append(LIEND);

			html.append("</ul>");
			html.append("</nav>");
		}
		return html.toString();
	}
}
