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
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<style>
	.contentArea{
		height: 500px;
		overflow : auto;
	}

</style>

<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="realUser"/>
</sec:authorize>
<div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
	<div class="d-flex flex-column justify-content-center">
		<h4 class="mb-1 mt-3">공지사항 상세</h4>
		<p class="text-muted">Notice Board Detail</p>
	</div>
</div>
<div style="text-align: right;">
    <c:if test="${realUser.username eq 'admin' }">
	    <form id="deleteForm" action="<c:url value='/notice/${bbsNo}'/>" method="post">
	        <input type="hidden" name="_method" value="DELETE">
	        <a href="javascript:;" onclick="deleteFunction()" class="btn btn-danger">삭제</a>
	        <sec:csrfInput/>
	    </form>
    </c:if>
</div>
<div class="card g-3 mt-3">
	<div class="card-body row g-3">
		<div class="d-flex justify-content-between align-items-center flex-wrap mb-2 gap-1">
			<div class="me-1">
			<h5 class="mb-2">제목</h5>
			<p class="mb-0 pt-1">
				<c:out value="${notice.bbsSj }" /> 
			</p>
			</div>
			<div class="d-flex align-items-center">
			<p class="mb-0 pt-1">
				조회수 : ${notice.bbsRdcnt } &nbsp;&nbsp; 등록일자 : ${notice.bbsRdate } &nbsp;&nbsp; 작성자 : ${notice.empCd }
			</p>
			</div>
		</div>
		    
		<div class="card-body">
		
		<c:if test="${realUser.username eq 'admin' }">
		<hr class="my-4">
		
		<div class="d-flex flex-wrap">
			<div class="col-xl-6 col-md-12 col-sm-5 col-12 mb-xl-0 mb-md-4 mb-sm-0 mb-4">
				<p class="mb-1"><i class="bx bx-check-double bx-sm me-2"></i>상단고정여부 : ${notice.noiceMustRead }</p>
			</div>
			<div>
				<p class="text-nowrap"><i class="bx bxs-flag-alt bx-sm me-2"></i>공지종료일자 : <c:if test="${notice.noticeTmlmt eq null }">N</c:if> <c:if test="${notice.noticeTmlmt != null }">${notice.noticeTmlmt}</c:if> </p>
			</div>
		</div>
		</c:if>
		
		<hr class="mb-4 mt-2">
		
		<div class="contentArea">
		<h5>내용</h5>
		<p class="mb-4 content">
		${notice.bbsCn }
		</p>
		</div>
		
		<hr class="mb-4 mt-2">
		
		<h5>첨부파일</h5>
		<p class="mb-4">
			<c:if test="${not empty notice.boardFileList }">
				<c:forEach items="${notice.boardFileList }" var="file">
					<c:url value="/notice/${bbsNo }/boFile/${file.fileCode }" var="downloadUrl" />
					<a href="${downloadUrl }" 
						title="${file.fileFancysize }">${file.fileName }
					</a>
				</c:forEach>
			</c:if>
		</p>
		
		</div>
	</div>
</div>
<br />
<div>
<c:if test="${realUser.username eq 'admin' }">
	<a href="<c:url value='/notice/${bbsNo}/edit'/>" class="btn btn-primary me-1 ">수정</a>
</c:if>
	<a href="<c:url value='/notice'/>" class="btn btn-secondary me-1 " >목록</a>
</div>

<script>
	function deleteFunction(){
		Swal.fire({
			  title: "정말로 삭제하시겠습니까?",
			  icon: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#3085d6",
			  cancelButtonColor: "#d33",
			  cancelButtonText: "취소",
			  confirmButtonText: "확인"
	    }).then((result) => {
	        if (result.isConfirmed) {
				document.getElementById("deleteForm").submit();
			}
	    });
	}
</script>


