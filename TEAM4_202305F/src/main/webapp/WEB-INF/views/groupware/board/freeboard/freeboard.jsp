<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/freeboard/freeboardList.css">

<div class="card mb-4">
	<div class="card-header d-flex flex-wrap justify-content-between gap-3">
		<div class="card-title mb-0 me-1">
			<h5 class="mb-1">자유게시판</h5>
			<p class="text-muted mb-0">웹진형 게시판</p>
		</div>
		
	</div>
	<div class="card-body">
		<div class="d-flex justify-content-md-end align-items-center gap-3 flex-wrap" id="serachFree">
		</div>
		<div class="row gy-4 mb-4" id="freeboardList">
		
        </div>
        <div class="row gy-4 mb-4" id="freeboardSelect">

        </div>
        <div id="replyFree">

        </div>
        <div id="insertFree">

        </div>
        <div id="searchFree">
			
		</div>
        <div id="freePage">
			
		</div>	
	</div>
</div>

<script src="${pageContext.request.contextPath }/resources/js/app/board/free.js"></script>