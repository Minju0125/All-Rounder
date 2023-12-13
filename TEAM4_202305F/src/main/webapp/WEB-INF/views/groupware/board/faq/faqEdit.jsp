<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* Nov 18, 2023      송석원      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<script src="<c:url value='/resources/js/ckeditor/ckeditor.js'/>"></script>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
	<div class="d-flex flex-column justify-content-center">
		<h4 class="mb-1 mt-3">FAQ 수정</h4>
		<p class="text-muted">Edit a FAQ Board</p>
	</div>
</div>

<div class="card-body">
<form:form enctype="multipart/form-data" modelAttribute="Faqeditboard">
<input type="hidden" name="_method" value="put">
	<div class="mb-3">	
		<label class="form-label" for="bbsSj">제목</label>
		<form:input type="text" path="bbsSj" class="form-control" required="true" placeholder="제목을 입력하세요!" />
		<form:errors path="bbsSj" element="span" cssClass="error" />
		
	</div>			
	<div class="mb-3">	
		<label class="form-label" for="bbsCn">내용</label>
		<form:textarea path="bbsCn" class="form-control" required="true" />
		<form:errors path="bbsCn" element="span" cssClass="error" />
	</div>
	
	<button type="submit" class="btn btn-primary me-1" id="addBtn">등록</button> 
	<button type="button" class="btn btn-label-secondary me-1" onclick="location.href='<c:url value='/faq' />'">목록</button>
</form:form>
</div>
<script>
	let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
 	let csrf = $("meta[name='_csrf']").attr("content");
 	
    CKEDITOR.replace('bbsCn',{
        filebrowserImageUploadUrl:`<c:url value='/faq/image?type=image'/>&\${csrfparam}=\${csrf}`
    });
     
    
</script>