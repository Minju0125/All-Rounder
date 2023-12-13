<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 12. 08.  전수진      최초작성
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="${pageContext.request.contextPath }/resources/js/ckeditor/ckeditor.js"></script>

<style>
	.card-header {
		text-align: center;
		font-weight: 800;
	}
	.forminfo {
		padding-top:10px;
	}
</style>

<div class="content-wrapper">
	<!-- Content -->

	<div class="container-xxl flex-grow-1 container-p-y">
		<h4 class="py-3 mb-1">결재양식관리</h4>
		<div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
          <div class="d-flex flex-column justify-content-center">
          </div>
          <div class="d-flex align-content-center flex-wrap gap-3">
            <button class="btn btn-outline-primary" data-bs-toggle="modal" data-bs-target="#formMakeModal">양식추가</button>
          </div>
        </div>
		
		<div class="row">
			<div class="col-xl-4 col-lg-5 col-md-5 order-1 order-md-0">
				<div class="card mb-4">
					<div class="card-body">
						<table class="table table-bordered">
							<thead>
								<tr>
									<td>번호</td>
									<td>양식명</td>
									<td>사용여부</td>
								</tr>
							</thead>
							<tbody id="tbody">
								<c:choose>
									<c:when test="${empty sanctionFormList }">
										<tr>
											<td colspan="2">조회하신 결재양식 목록이 존재하지 않습니다.</td>
										</tr>
									</c:when>
									<c:otherwise>
										<c:forEach items="${sanctionFormList }" var="sanction" varStatus="stat">
											<tr data-no="${sanction.formNo }">
												<td>${stat.count }</td>
												<td>${sanction.formNm }</td>
												<td>
												<c:if test="${sanction.formUse eq 'Y'}">
													<span class="badge rounded-pill bg-label-success">사용중</span>
												</c:if>
												<c:if test="${sanction.formUse eq 'N'}">
													<span class="badge rounded-pill bg-label-danger">미사용중</span>
												</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="col-xl-8 col-lg-7 col-md-7 order-0 order-md-1">
				<div class="card mb-4">
					<div class="card-body" id="info">

<!-- 					<button class="btn btn-primary me-3" onclick="insertFunction()">신규작성</button> -->
					</div>
					<div class="card-header"><h3 id="title"></h3></div>
					<div class="card-body row g-3" id="content">
						
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<!-- Extra Large Modal -->
<div class="modal fade" id="formMakeModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-xl" role="document">
    <div class="modal-content">
      <div class="modal-header pb-2 border-bottom mb-4">
        <h5 class="modal-title" id="exampleModalLabel4">결재 양식 생성</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col mb-3">
            <label for="nameExLarge" class="form-label">양식이름</label>
            <input type="text" id="formNm" class="form-control" placeholder="양식명을 입력하세요!" />
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="nameExLarge" class="form-label">양식설명</label>
            <input type="text" id="formInfo" class="form-control" placeholder="양식에 대한 설명을 간단히 입력하세요!" />
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="nameExLarge" class="form-label">양식소스</label>
            <textarea rows="20" cols="50" name="formSourc" id="formSourc"></textarea>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button class="btn btn-primary" id="insertFormBtn">등록</button>
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">닫기</button>
      </div>
    </div>
  </div>
</div>



<script type="text/javascript">


let formNo;
$(function(){
	var tbody = $("#tbody");
	
	tbody.on("click", "tr", function(){
	    formNo = $(this).data("no");
		
		var data = {
			formNo : formNo	
		}
		
		$.ajax({
			url : "/sanctionform/list",
			type: "post",
			data: JSON.stringify(data),
			dataType: "json",
			contentType: "application/json;charset=utf-8",
			success: function(res){
				$("#title").text(res.formNm);
				$("#content").html(res.formSample);
// 				$("#info").text(res.formInfo);
				$("#info").empty();
	            var alertDiv = $(
	                    '<div class="alert alert-primary d-flex" role="alert">' +
	                        '<span class="badge badge-center rounded-pill bg-primary border-label-primary p-3 me-2"><i class="bx bx-detail fs-6"></i></span>' +
	                        '<div class="d-flex flex-column ps-1">' +
	                            '<h6 class="alert-heading d-flex align-items-center mb-1 forminfo">' + res.formInfo + '</h6>' +
	                        '</div>' +
	                    '</div>'
	                );

	           	$("#info").append(alertDiv);
			}
		});
	});
});

$(function(){
	CKEDITOR.replace('formSourc');
});


$("#insertFormBtn").on("click",function(){
	let formNm = $("#formNm").val();
	let formInfo = $("#formInfo").val();
	let formSourc = CKEDITOR.instances.formSourc.getData(); 
	
    console.log("formNm: ", formNm);
    console.log("formSourc: ", formSourc);
	
	
	let sanctionFormVO ={
		formNm,
		formInfo,
		formSourc
	}
	
	console.log("sanctionFormVO======="+sanctionFormVO);
	
	if(formNm == '') {
		Swal.fire({
			icon: "error",
			title: "결재상신 실패!",
			text: "양식 제목을 입력해주세요!",
			showConfirmButton: false,
			timer: 1500
		});
		return;
	}
	
	$.ajax({
		type : "post",
		url : "/sanctionform/new",
		contentType:"application/json",  // post
		data: JSON.stringify(sanctionFormVO) ,
		dataType:"json",
		success:function(rslt){
			// JSON.parse(rslt)  jQuery가 몰래해줌
			console.log("서버에서 온 값:", rslt);

			if(rslt.msg == "OK"){
				Swal.fire({
					icon: "success",
					title: "양식등록완료!",
					text: "양식 등록이 완료 되었습니다",
					showConfirmButton: false,
					timer: 1500
				});
			
				$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000); 
				
			    setTimeout(function () {
			        location.reload();
			    }, 1000); 
				
				$('#formMakeModal').modal('hide');
				

			}  else {
				Swal.fire({
					icon: "error",
					title: "양식 등록실패!",
					text: "양식 등록에 실패 하였습니다",
					showConfirmButton: false,
					timer: 1500
				});
				$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
				
			}
		},
	    error: function (request, status, error) {
	        console.log("code: " + request.status)
	        console.log("message: " + request.responseText)
	        console.log("error: " + error);
	    }
	});
	
})


</script>