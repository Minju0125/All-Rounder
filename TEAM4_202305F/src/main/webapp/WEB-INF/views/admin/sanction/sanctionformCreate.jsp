<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 14.  전수진      최초작성
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script src="${pageContext.request.contextPath }/resources/js/ckeditor/ckeditor.js"></script>
<div class="content-wrapper">
	<!-- Content -->

	<div class="container-xxl flex-grow-1 container-p-y">
		<h4 class="py-3 mb-4">결재 양식 만들기 폼</h4>
		<div class="row">
			<!-- User Content -->
			<div class="col-xl-12 col-lg-7 col-md-7 order-0 order-md-1">
				<!-- Project table -->
				<div class="card mb-4">
					<h5 class="card-header">신규작성</h5>
						<div class="card-body row g-3">
					
					<!-- html 추가될 부분 -->
					<form action="/sanctionform/new" method="post">
						양식명 : <input type="text" name="formNm" value=""/><br/>
						양식 소스:<br/>
						<textarea rows="20" cols="50" name="formSourc" id="formSourc"></textarea>
						<input type="submit" value="등록"/>
						<sec:csrfInput/>
					</form>
					<!-- html 양식 들어갈 부분  -->
					</div>
					
					
				</div>
				<!-- /Project table -->

			</div>
			<!--/ User Content -->
		</div>
	</div>
	<!-- / Content -->
</div>
<script type="text/javascript">
$(function(){
	CKEDITOR.replace('formSourc');
});

</script>