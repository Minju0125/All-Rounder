<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 15.  송석원     최초작성
* 2023. 11. 17.  송석원     악코디언 기능 수정
* 2023. 11. 18.  송석원     게시판 관리자 기능
* Copyright (c) $2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script src="<c:url value='/resources/js/ckeditor/ckeditor.js'/>"></script>
<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="realUser" />
  </security:authorize>   
 
  
<style>
.que {
	position: relative;
	padding: 14px 0;
	cursor: pointer;
	font-size: 20px;
	border-bottom: 1px solid #dddddd;
}

.que::before {
	display: inline-block;
	content: 'Q';
	font-size: 15px;
	color: rgb(105, 108, 255);
	margin: 0 5px;
}

.que.on>span {
	font-weight: bold;
	color: #006633;
}

.anw {
	font-size: 15px;
	background-color: #f4f4f2;
	padding: 27px 0;
}

.anw::before {
	display: inline-block;
	content: 'A';
	font-size: 14px;
	font-weight: bold;
	color: #666;
	margin: 0 5px;
}

.arrow-wrap {
	position: absolute;
	top: 50%;
	right: 10px;
	transform: translate(0, -50%);
}

.table-responsive {
	overflow-x: hidden;
}

#btCm {
	height: 200px;
}
</style>
 
<div class="card">
	<div class="card-header border-bottom">
		<h4 class="card-title">FAQ게시판</h4>
		<div
			class="d-flex justify-content-between align-items-center row py-3 gap-3 gap-md-0">
			<div class="col-md-4 user_role"></div>
			<div class="col-md-4 user_plan"></div>
			<div class="col-md-4 user_status"></div>
		</div>


<c:if test="${realUser.username   eq 'admin'}">
		<div class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-end flex-md-row flex-column mb-3 mb-md-0">
			<div class="dt-buttons">
				<button class="dt-button add-new btn btn-primary" tabindex="0" aria-controls="DataTables_Table_0" 
						type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasAddUser" 
						onclick="location.href='<c:url value='/faq/new' />'"
						>
					<span>
						<i class="bx bx-plus me-0 me-sm-1"></i>
						<span class="d-none d-sm-inline-block">등록</span>
					</span>
				</button>
			</div>
		</div>
</c:if>		
		
		
		<br />
		<div class="card-datatable table-responsive">
			<table class="table">

				<tbody class="table-border-bottom-0">
					<c:if test="${empty paging.dataList }">
						<tr>
							<td colspan="4">검색 조건에 맞는 게시글 없음</td>
						</tr>
					</c:if>
	<c:if test="${not empty paging.dataList }">
    <div class="col-md">
        <div class="accordion mt-3 accordion-header-primary" id="accordionStyle1">
            <c:forEach items="${paging.dataList}" var="faq" varStatus="loopStatus">
                <div class="accordion-item card">
                    <h2 class="accordion-header">
                       
                        <button type="button" class="accordion-button collapsed que" data-bs-toggle="collapse"
                            data-bs-target="#accordionStyle1-${loopStatus.index + 1}" aria-expanded="false">${faq.bbsSj}</button>
                    </h2> 
                    <div id="accordionStyle1-${loopStatus.index + 1}" class="accordion-collapse collapse <c:if test="${(loopStatus.index + 1) eq '1'}">show</c:if>"
                        data-bs-parent="#accordionStyle1" style=""> 
                        <div class="accordion-body  anw">
                            ${faq.bbsCn}
                            <c:if test="${realUser.username   eq 'admin'}">
                           <div style="text-align: right;">
										<form id="deleteForm${faq.bbsNo}"
											action="<c:url value='/faq/${faq.bbsNo}'/>" method="post">
											<input type="hidden" name="_method" value="DELETE"> <a
												href="javascript:;" onclick="deleteFunction(${faq.bbsNo})"
												class="btn btn-danger">삭제</a>
											<security:csrfInput />
										</form>
										 <a href="<c:url value='/faq/${faq.bbsNo}/edit'/>" class="btn btn-primary me-1 ">수정</a>
									</div>
									</c:if>  
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>


				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">${paging.pagingHTML }
							<div id="searchUI" class="row g-3 d-flex justify-content-center">
								<div class="col-auto">
									<form:select path="simpleCondition.searchType"
										class="form-select">
										<form:option label="전체" value="" />
										<form:option label="제목" value="title" />
									</form:select>
								</div>
								<div class="col-auto">
									<form:input path="simpleCondition.searchWord"
										placeholder="검색키워드" class="form-control" />
								</div>
								<div class="col-auto">
									<input type="button" value="검색" id="searchBtn"
										class="btn btn-primary" />
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
<form:form modelAttribute="simpleCondition" method="get" id="searchForm"
	class="border">
	<form:hidden path="searchType" readonly="readonly"
		placeholder="searchType" />
	<form:hidden path="searchWord" readonly="readonly"
		placeholder="searchWord" />
	<input type="hidden" name="page" readonly="readonly" placeholder="page" />
</form:form>

<script>
 

function deleteFunction(bbsNo) {
	Swal.fire({
	    title: '정말로 삭제하시겠습니까?',
	    icon: 'warning',
	    showCancelButton: true,
	    confirmButtonColor: '#d33',
	    cancelButtonColor: '#3085d6',
	    confirmButtonText: '삭제',
	    cancelButtonText: '취소'
	}).then((result) => {
	    if (result.isConfirmed) {
	        document.getElementById("deleteForm" + bbsNo).submit();
	    }
	});
} 
	// ck에디터
	let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
	let csrf = $("meta[name='_csrf']").attr("content");

	CKEDITOR
			.replace(
					'bbsCn',
					{
						filebrowserImageUploadUrl : `<c:url value='/faq/image?type=image'/>&\${csrfparam}=\${csrf}`
					});
 
	function fn_paging(page) {
		searchForm.page.value = page;
		searchForm.requestSubmit();
	}
	$(searchUI).on("click", "#searchBtn", function(event) {
		let inputs = $(this).parents("#searchUI").find(":input[name]");
		$.each(inputs, function(idx, ipt) {
			let name = ipt.name;
			let value = $(ipt).val();
			$(searchForm).find(`:input[name=\${name}]`).val(value);
			$(searchForm).submit();
		});
	});
</script>