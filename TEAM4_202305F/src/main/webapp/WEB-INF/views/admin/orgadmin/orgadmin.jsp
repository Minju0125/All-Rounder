<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 30.      작성자명      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!-- <!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" /> --> 
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" /> -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" /> -->
<%-- <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/org/organization.css"> --%>

<!-- <!-- Bootstrap CSS --> 
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"> -->


<!-- <!-- Popper.js -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script> -->


<!-- Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<script>
	window.addEventListener("DOMContentLoaded", function(){
		let script = document.createElement("script");
		script.src = "https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js";
		document.body.appendChild(script);
		
		let org = document.createElement("script");
		org.src = "${pageContext.request.contextPath }/resources/js/app/org/organization.js";
		document.body.appendChild(org);
		
	})
</script>
<security:authentication property="principal" var="principal" />
<input type="hidden" id="schEmpCd" name="schEmpCd" value="${principal.realUser.empCd }" />
<body>
<div class="row">
	<div class="plus_wrap">
		<input type="text" id="searchInput" placeholder="검색">
		<a href="#insertModal" class="dropdown-shortcuts-add text-body" data-toggle="modal" data-bs-toggle="tooltip" data-bs-placement="top" aria-label="단축키 추가" data-bs-original-title="추가" _mstaria-label="211718" _msthash="209" _mstvisible="3"><i class="bx bx-sm bx-plus-circle" _mstvisible="4"></i></a>
		<a href="#deleteModal" class="dropdown-shortcuts-add text-body" data-toggle="modal" data-bs-toggle="tooltip" data-bs-placement="top" aria-label="단축키 추가" data-bs-original-title="삭제" _mstaria-label="211718" _msthash="209" _mstvisible="3"><i class="bx bx-sm bx-minus-circle" _mstvisible="4"></i></a>
		
	</div>
	<div id="kt_docs_jstree_basic" class="ksksk">

	</div>
	

	<div id="information" class="card mb-4">
		<div class="card-body"></div>
	</div>
</div>

<div id="insertDelete">

</div>

</body>
<!-- <div id="insertDelete"> -->
<%-- 	<form method="post" id="insertInfo"> --%>
<!-- 		<div id="insertModal" class="modal"> -->
<!-- 		     <div class="modal-dialog"> -->
<!-- 		          <div class="modal-content"> -->
<!-- 		               <div class="modal-header"> -->
<!-- 		                    <h4 class="modal-title">All-Rounder</h4> -->
<!-- 		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> -->
<!-- 		               </div> -->
<!-- 		               <div class="modal-body"> -->
<!-- 		                    <p>추가할 부서명을 입력하세요</p> -->
<!-- 		                    <input type="text" name="deptName" id="deptName"> -->
<%-- 							<security:csrfInput/>	 --%>
<!-- 		               </div> -->
<!-- 		               <div class="modal-footer"> -->
<!-- 		                    <button type="submit" class="btn btn-primary" id="insertDept">추가</button> -->
<!-- 		                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button> -->
<!-- 		               </div> -->
<!-- 		          </div> -->
<!-- 		     </div> -->
<!-- 		</div> -->
<%-- 	</form> --%>
<!-- </div> -->


