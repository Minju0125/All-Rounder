<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ----------  		---------  -----------------
* 2023. 11. 15.     김보영        최초작성
* 2023. 12. 04.     오경석        부서관리
* Copyright (c) 2023 by DDIT All right reserved
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>   


<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
	<!-- Layout container -->
	<div class="layout-container">
		<!-- Content wrapper -->
		<div class="layout-page">
			<!-- Content -->
			<!-- 버튼 -->
			<div class="col-xl-12 col-12" style="text-align: right;">
				<div class="card-body">
					<div class="block-ui-btn demo-inline-spacing">
						<h4 class="m-0 me-2" style="text-align: left;">
							<i class='bx bxs-universal-access'></i>
							직원계정관리</h4>
						<button type="button" class="btn rounded-pill btn-label-primary" onclick="accountForm()">
							<span class="tf-icons bx bxs-user-account me-1"></span>직원등록
						</button>
						<button type="button" class="btn rounded-pill btn-label-primary" data-bs-toggle="modal" data-bs-target="#insertDelete">
							<span class="tf-icons bx bxs-duplicate me-1"></span>부서관리
						</button>
						<button type="button" class="btn rounded-pill btn-label-primary"  data-bs-toggle="modal" data-bs-target="#backDropModal" >
							<span class="tf-icons bx bx-upload me-1"></span>업로드
						</button>
						<button type="button" class="btn rounded-pill btn-label-primary" onclick="excelDownload()">
							<span class="tf-icons bx bx-download me-1"></span>다운로드
						</button>
					</div>
				</div>
			</div>
			<!-- 리스트창 -->
			<div class="col-md-12 col-xl-12 mt-3">
				<div class="card">
					<div class="card-body" > 
						<div id="empList" class="ag-theme-alpine" style="height:730px">
						<!--직원리스트 ag-grid-->
						</div>
					</div>
				</div>
			</div>
			<!-- /Content -->
		</div>
	</div>
</div>

<!-- 업로드모달 -->
<div class="modal fade" id="backDropModal" data-bs-backdrop="static" tabindex="-1" style="display: none;"
  aria-hidden="true">

  <div class="modal-dialog">
    <form action="/account/excel/upload" class="modal-content" method="post"  enctype="multipart/form-data">
      <security:csrfInput/>
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle">업로드</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <hr>
      </div>
      <div class="modal-body">
        <div class="row g-2">
          <div class="col mb-3">
            <label for="dobBackdrop" class="form-label">첨부파일</label>
            <input type="file" class="form-control" name="file" id="file" accept=".xlsx, .xls"/>
          </div>
        </div>
      </div>  
      <div class="modal-footer">
        <button type="button" class="btn btn-label-primary" onclick="location.href='/resources/file/addAccount.xlsx'">양식다운로드</button>
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">Close </button>
        <button type="submit" class="btn btn-primary">Save</button>
      </div>
    </form>
  </div>
</div>



<!-- 부서관리 모달 -->
<div class="modal fade" id="insertDelete" data-bs-backdrop="static" tabindex="-1" style="display: none;"
	aria-hidden="true">

	<div class="modal-dialog">
		<form action="/account/excel/upload" class="modal-content" method="post" enctype="multipart/form-data">
			<security:csrfInput />
			<div class="modal-header">
				<h5 class="modal-title" id="backDropModalTitle">부서 등록 / 삭제</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				<hr>
			</div>
			<div class="modal-body">
				<div class="row g-2">
					<div class="col mb-3">
						<div class="row">
							<label for="dobBackdrop" class="form-label">부서 추가</label>
							<div class="col-md-8">
								<input type="text" class="form-control " name="deptName" id="deptName" />
							</div>
							<div class="col-md-4">
								<button type="button" class="btn btn-primary" id="insertDept">Save</button>
							</div>
						</div>
					</div>
				</div>
				<div class="row g-2">
					<div class="col mb-3">
						<div class="row">
							<label for="dobBackdrop" class="form-label">부서 삭제</label>
							<div class="col-md-8">
								<select id="deptCode" class="form-select">
									<c:forEach items="${deptList }" var="dept">
										<option value="${dept.deptCd }">${dept.deptName }</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-4">
								<button type="button" class="btn btn-danger" id="deleteDept">Delete</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">Close </button>
			</div>
		</form>
	</div>
</div>




<script>var __basePath = './';</script>
<script src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/admin/account/account.js"></script>

<script>
$('#insertDept').on("click", function() {
	let deptName = $("input[name=deptName]").val();
	let url = "/org";
	console.log(deptName)

	$.ajax({
		type: "post",
		url: url,
		/*contentType: "application/json;charset=UTF-8",*/
		dataType: "json",
		data: {
			"deptName": deptName
		}
		,
		success: function(data) {
			console.log("성공");
			location.reload();
		},
		error: function(request, status, error) {
			console.log("code: " + request.status)
			console.log("message: " + request.responseText)
			console.log("error: " + error);
		}
	});
})

$(document).on("click",'#deleteDept', function() {
		
	let deptCd = $('#deptCode').val();
	console.log(deptCd)
	let url = "/org";
		
	$.ajax({
		type: "delete",
		url: url + "?deptCd=" + deptCd,
		contentType: "application/json; charset=UTF-8",
		dataType: "text",
/*			data: deptCd
		,*/
		success: function(resp) {
			console.log(resp)
			location.reload();
			
		},
		error: function(request, status, error) {
			console.log("code: " + request.status)
			console.log("message: " + request.responseText)
			console.log("error: " + error);
		}
	});
})
</script>


