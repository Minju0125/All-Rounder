<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ---------- 		---------  -----------------
* 2023. 11. 25.     박민주        최초작성
* 2023. 11. 27.     박민주			
* Copyright (c) 2023 by DDIT All right reserved
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<style>
.noneday {
	width: 5%;
}

;
.mon, .tue, .wed, .thu, .fri {
	width: 19%; /* 20%로 설정하면 다섯 열이 동일한 너비를 갖습니다. */
}

thead {
	text-align: center;
}

.clickable {
	cursor: pointer;
}

.bxs-chevron-left, .bxs-chevron-right{
	cursor: pointer;
}

.oneBox {
	padding: 5px 10px 5px 10px;
	border-radius: 15px;
	background-color: #696cff17;
}

.veghicleContainer {
	padding : 15px 20px 15px 20px;
    border-radius: 5px;
}

.vehicleImg{
    width: 350px;
    height: 150px;
    border-radius: 10px;
    margin-top: 10px;
    margin-bottom: 10px;
}


</style>

<h4 class="py-3 mb-4"><span class="text-muted fw-light">자원예약 /</span> 차량예약</h4>
<br>
<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
	<div class="layout-container">
		<!-- Layout container -->
		<div class="layout-page">
			<!-- Content wrapper -->
			<div>
				<div class="row mb-5">

					<div class="col-xl-12 col-12">
						<div class="card  mb-3">
							<div class="card-body">
								<h5 class="card-title">차량 사용 현황<small><a style="margin-left:15px;" href="/myReservation/myReservationHome">| 내 예약내역 보기</a></small>
								</h5>
								<hr />
								<div id="vehicleList" class="container ag-theme-balham">
									<div class="row">
										<c:forEach items="${vehicleList}" var="vehicle"
											varStatus="loop">
												<div class="row veghicleContainer col-md-4 text-center justify-content-center vehicleOne" data-vhcleCd="${vehicle.vhcleCd}"
													style="cursor: pointer">
													<div class="oneBox">
														<div class="col-md-12">
															<img class="vehicleImg" src="${vehicle.vhcleImg}">
														</div>
														<div class="col-md-12 d-flex justify-content-between statusDiv">
														<!-- 상태 출력 예정 !! -->
															<div class="col-md-4"><h5 style="margin-top:8px">${vehicle.vhcleModel}<br><small>(${vehicle.vhcleNo})</small></h5></div>
<%-- 															<c:if test="${vehicle.vhcleFlag eq '1'}"> --%>
<!-- 																<button type="button" id="using" style="height:35px; margin-right:20px" class="btn rounded-pill btn-danger status-flag">사용중</button> -->
<%-- 															</c:if> --%>
<%-- 															<c:if test="${vehicle.vhcleFlag eq '2'}"> --%>
<!-- 																<button type="button" id="stopUsing" style="height:35px; margin-right:20px" class="btn rounded-pill btn-warning status-flag">사용중지</button> -->
<%-- 															</c:if> --%>
<%-- 															<c:if test="${vehicle.vhcleFlag eq '0'}"> --%>
<!-- 																<button type="button" id="available" style="height:35px; margin-right:20px" class="btn rounded-pill btn-success status-flag">사용가능</button> -->
<%-- 															</c:if> --%>
														</div>
													</div>
												</div>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-md-12 col-12" style="text-align: left;">
					    <div class="card-body">
					        <div class="block-ui-btn demo-inline-spacing" style="display: flex; align-items: center;">
					            <span id="beforeIcon"><i class='bx bx-md bxs-chevron-left'></i></span>
					            <h3 class="mb-0"><span class="py-3" id="startDate"></span> - <span class="py-3" id="endDate"></span></h3>
					            <span id="afterIcon"><i class='bx bx-md bxs-chevron-right'></i></span>
					        </div>
					    </div>
					</div>

					<!--일감테이블-->

					<hr class="my-0 mb-3 mt-2">
					<input type="hidden" class="divider-text" data-pro-sn="ㅂㅂ" />
					<div class="table-responsive text-nowrap">
						<table class="table card-table">
							<thead>
								<tr>
									<th class="noneday"></th>
									<th class="mon"></th>
									<th class="tue"></th>
									<th class="wed"></th>
									<th class="thu"></th>
									<th class="fri"></th>
								</tr>
							</thead>
							<tbody id="jobList" class="table-border-bottom-0">
								<tr height="130px">
									<td>09:00-13:00</td>
									<td class="1 R_V1 timetable clickable" style="width: 19%;"></td>
									<td class="2 R_V1 timetable clickable" style="width: 19%;"></td>
									<td class="3 R_V1 timetable clickable" style="width: 19%;"></td>
									<td class="4 R_V1 timetable clickable" style="width: 19%;"></td>
									<td class="5 R_V1 timetable clickable" style="width: 19%;"></td>
								</tr>
								<tr height="130px">
									<td>13:00-18:00</td>
									<td class="1 R_V2 timetable clickable" style="width: 19%;"></td>
									<td class="2 R_V2 timetable clickable" style="width: 19%;"></td>
									<td class="3 R_V2 timetable clickable" style="width: 19%;"></td>
									<td class="4 R_V2 timetable clickable" style="width: 19%;"></td>
									<td class="5 R_V2 timetable clickable" style="width: 19%;"></td>
								</tr>
								<!-- 일감리스트 비동기출력 -->
							</tbody>
							<tfoot id="jobPaging">
							</tfoot>
						</table>
					</div>
				</div>
			</div>
			<!-- /Content -->
		</div>
		<!-- /Content wrapper -->
	</div>
	<!-- /Layout container -->
</div>
<!-- /Layout wrapper -->
<%-- <form method="get" id="searchForm" class="border" >
	<hidden name="searchType" readonly="readonly" placeholder="searchType"/>
	<hidden name="searchWord" readonly="readonly" placeholder="searchWord"/>
	<input type="hidden" name="page" readonly="readonly" placeholder="page"/>
</form> --%>

<!-- 차량 등록 모달 -->
<div class="modal fade" id="backDropModal" data-bs-backdrop="static"
	tabindex="-1" style="display: none;" aria-hidden="true">

	<div class="modal-dialog">
		<form action="/vehicle" id="newVehicleForm" class="modal-content"
			method="POST" enctype="multipart/form-data">
			<security:csrfInput />
			<div class="modal-header">
				<h5 class="modal-title" id="backDropModalTitle">
					<i class='bx bxs-comment-add bx-md'></i> 예약하기
				</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
				<hr>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="card mb-3">
					    <div class="row">
					        <div class="col-md-6">
					            <img class="card-img-top" id="modalImg" alt="Card image cap">
					        </div>
					        <div class="col-md-6">
					            <div class="card-body">
					                <p id="vModel"></p>
					                <p id="vNo"></p>
					            </div>
					        </div>
					    </div>
					</div>
				</div>
				<div class="row">
					<input type="hidden" id="thisVhcleCd">
					<input name="vhcleReserveEmpCd" type="hidden" id="vhcleReserveEmpCd" required />
					<div class="col mb-8">
						<label for=vhcleUseDate class="fw-medium"><i
							class='bx bxs-book-alt bx-sm mb-1'></i> 사용일자 <small>(토,일은 사용불가)</small></label> <input
							name="vhcleUseDate" id="vhcleUseDate" class="form-control"
							placeholder="날짜선택" required />
					</div>
					<div class="col mb-4">
						<label for="vhcleUseTimeCd" class="fw-medium"><i
							class='bx bxs-book-alt bx-sm mb-1'></i> 사용시간</label> 
						<select class="form-select col form-control ui-timepicker-input" name="vhcleUseTimeCd" id="vhcleUseTimeCd" required>
						    <c:forEach items="${timeCd}" var="item">
						        <option class="form-select" value="${item.commonCodeCd}">${item.commonCodeSj}</option>
						    </c:forEach>
						</select>
					</div>
				</div>
				<div class="row">
					<div class="col mb-3">
						<label for="vhcleReservePur" class="fw-medium"><i
							class='bx bxs-book-alt bx-sm mb-1'></i> 사용목적</label> <input
							name="vhcleReservePur" type="text" id="vhcleReservePur"
							class="form-control" placeholder="ex) 외근 (고객사 미팅)" maxlength="40"
							required />
					</div>
				</div>
				<div class="row">	
					<div class="col mb-3">
						<label for="vhcleReservePw" class="fw-medium"><i
							class='bx bxs-book-alt bx-sm mb-1'></i> 취소용 비밀번호</label> <input
							name="vhcleReservePw" type="password" id="vhcleReservePw"
							class="form-control" placeholder="ex) 1234" required
							maxlength="4" />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-label-secondary"
					data-bs-dismiss="modal">Close</button>
				<button type="button" onclick="fn_vReserve()"
					class="btn btn-primary">Save</button>
			</div>
		</form>
	</div>
</div>

<script>
	var __basePath = './';
</script>
<script
	src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>
<script src="/resources/js/app/reservation/vehicle.js"></script>