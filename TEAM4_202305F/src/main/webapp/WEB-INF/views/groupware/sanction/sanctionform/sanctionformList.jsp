<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 14.  전수진      최초작성
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	.card-header {
		text-align: center;
		font-weight: 800;
	}
</style>


<div class="content-wrapper">
	<!-- Content -->

	<div class="container-xxl flex-grow-1 container-p-y">
		<h4 class="py-3 mb-4">결재양식 리스트</h4>
		<div class="row">
			<div class="col-xl-4 col-lg-5 col-md-5 order-1 order-md-0">
				<div class="card mb-4">
					<div class="card-body">
						<table class="table table-bordered">
							<thead>
								<tr>
									<td>번호</td>
									<td>양식명</td>
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
					<div class="card-body">
					<button class="btn btn-primary me-3" onclick="insertFunction()">신규작성</button>
					</div>
					
					<div class="card-header"><h3 id="title"></h3></div>
					<div class="card-body row g-3" id="content">
						
					</div>
				</div>
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
			}
		});
	});
});

function insertFunction(){
	
	if(!formNo){
		Swal.fire({
			icon: "warning",
			title: "양식 미선택!",
			text: "양식을 먼저 선택 하세요.",
			showConfirmButton: false,
			timer: 1500
		});
		
		return;
	}
	location.href=`<c:url value='/sanction/\${formNo}/new'/>`;
}
</script>