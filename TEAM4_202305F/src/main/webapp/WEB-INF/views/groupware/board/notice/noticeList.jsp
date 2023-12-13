<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 11.  전수진      최초작성
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="realUser"/>
</sec:authorize>

<div class="card">
	<div class="card-header border-bottom">
		<h4 class="card-title">공지사항</h4>
		<div class="d-flex justify-content-between align-items-center row py-3 gap-3 gap-md-0">
			<div class="col-md-4 user_role"></div>
			<div class="col-md-4 user_plan"></div>
			<div class="col-md-4 user_status"></div>
		</div>
	
		<div class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-end flex-md-row flex-column mb-3 mb-md-0">
			
			<c:if test="${realUser.username eq 'admin' }">
			<div class="dt-buttons">
				<button class="dt-button add-new btn btn-primary" tabindex="0" aria-controls="DataTables_Table_0" 
						type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasAddUser" 
						onclick="location.href='<c:url value='/notice/new' />'"
						>
					<span>
						<i class="bx bx-plus me-0 me-sm-1"></i>
						<span class="d-none d-sm-inline-block">등록</span>
					</span>
				</button>
			</div>
			</c:if>
		</div>
		<br />
	
		<div class="card-datatable table-responsive">
			<table class="table">
				<thead class="table-light">
					<tr>
						<th>NO</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody class="table-border-bottom-0">
					<c:if test="${empty paging.dataList }">
						<tr>
							<td colspan="5">검색 조건에 맞는 게시글 없음 </td>
						</tr>
					</c:if>
					<c:if test="${not empty paging.dataList }">
						<c:forEach	items="${paging.dataList }" var="notice" varStatus="loopStatus">
							<tr>
								<td>${notice.bbsNo }</td>				
								<td>
									<a href="<c:url value='/notice/${notice.bbsNo}'/>">
						                <c:if test="${loopStatus.index < 3}">
						                	<c:if test="${notice.noiceMustRead eq 'Y' }">
							                    <span class="badge bg-warning">필독</span>
						                	</c:if>
						                </c:if>
						                <c:out value="${notice.bbsSj }" />
						            </a>
								</td>				
								<td>${notice.empCd }</td>
								<td>${notice.bbsRdate }</td>				
								<td>${notice.bbsRdcnt }</td>				
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">
							${paging.pagingHTML }
							<div id ="searchUI" class="row g-3 d-flex justify-content-center">
								<div class="col-auto">
									<form:select path = "simpleCondition.searchType" class="form-select"> 
										<form:option label="전체" value="" />
										<form:option label="제목" value="title" />
									</form:select>
								</div>
								<div class="col-auto">
								<form:input path="simpleCondition.searchWord" placeholder="검색키워드" class="form-control" />
								</div>
								<div class="col-auto">
								<input type="button" value="검색" id="searchBtn" class="btn btn-primary" />
								</div>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div> 
	</div>
</div>
<!-- 히든태그 사용 -->
<form:form modelAttribute="simpleCondition" method="get" id = "searchForm" class = "border" >
	<form:hidden path="searchType" readonly="readonly" placeholder="searchType"/>
	<form:hidden path="searchWord" readonly="readonly" placeholder="searchWord"/>
	<input type="hidden" name="page" readonly="readonly" placeholder="page"/>
</form:form>

<script>
	function fn_paging(page) {
		searchForm.page.value = page;
		searchForm.requestSubmit();
	}
	$(searchUI).on("click", "#searchBtn", function(event){
		let inputs = $(this).parents("#searchUI").find(":input[name]");
		$.each(inputs, function(idx, ipt){
			let name = ipt.name;
			let value = $(ipt).val();
			$(searchForm).find(`:input[name=\${name}]`).val(value);
			$(searchForm).submit();
		});
	});

</script>