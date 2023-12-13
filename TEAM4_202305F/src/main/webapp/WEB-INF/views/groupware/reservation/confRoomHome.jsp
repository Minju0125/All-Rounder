<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ---------- 		---------  -----------------
* 2023. 11. 29		박민주		최초작성		
* Copyright (c) 2023 by DDIT All right reserved
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>

<style>
/*회의실 예약 내역 한 칸을 감싸는 wrapper*/
.confRoomWrapper {
	border-radius: 10px;
	margin: auto;
	padding: 10px;
}

.confRoomContainer {
	border: 1px solid #fff;
    border-radius: 20px;
    margin: 4px 0px 4px 0px;
    background-color: #696cff14;
}

#calendar {
	width: 80vw;
	height: 80vh;
}

#yrModal {
	position: fixed;
	width: 100%;
	height: 100%;
	background-color: rgba(50, 150, 150, 0.7);
	display: none;
	z-index: 1000;
}

#cont {
	margin: 50px auto;
	width: 50%;
	height: 70%;
	background-color: darkblue;
	color: yellow;
}
</style>

<h4 class="py-3 mb-4"><span class="text-muted fw-light">자원예약 /</span> 회의실예약</h4>
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
								<h5 class="card-title">회의실 사용 현황<small><a style="margin-left:15px;" href="/myReservation/myReservationHome">| 내 예약내역 보기</a></small>
								
								</h5>
								
								
								<hr />
								<div id="vehicleList" class="container ag-theme-balham">
									<div class="row">
										<c:forEach items="${confRoomList}" var="confRoom"
											varStatus="loop">
											<c:if test="${confRoom.confYn eq 'Y'}">
												<div class="row col-md-3 text-center justify-content-center confRoomWrapper">
													<div class="card-body text-center confRoomContainer" data-confRoomCd="${confRoom.confRoomCd}">
								                      <h4>${confRoom.confRoomNm}</h4>
								                      <h6>수용인원(명) : ${confRoom.confRoomCapacity}</h6>
								                    </div>
												</div>
											</c:if>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</div>

					<hr class="my-0 mb-3 mt-2">
					<input type="hidden" class="divider-text" data-pro-sn="ㅂㅂ" />
				</div>
			</div>
			<!-- /Content -->
		</div>
		<!-- /Content wrapper -->
	</div>
	<!-- /Layout container -->
</div>

<!-- 회의실 예약 모달 -->
<div class="modal fade" id="confRoomReserveModal"
	data-bs-backdrop="static" tabindex="-1" style="display: none;"
	aria-hidden="true">
	<div>
	<input name="confReserveEmpCd" type="hidden" id="confReserveEmpCd" class="form-control"	required />
	
	<div class="modal-dialog modal-sm">
		<form action="/vehicle" id="confRoomForm" class="modal-content"
			method="POST">
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
					<input type="hidden" id="confRoomCd">
					<div class="col mb-3">
						<label for=confRoomUseDate class="fw-medium"><i
							class='bx bxs-book-alt bx-sm mb-1' ></i> 사용일자
							<small>※ 당일예약만 가능함</small>
							</label>
							<input readonly="readonly"
							name="confRoomUseDate" id="confRoomUseDate" class="form-control"
							required />
					</div>
				</div>
				<div class="row">
					<div class="col mb-3">
					<label for="confTimeCd" class="fw-medium"><i
						class='bx bxs-book-alt bx-sm mb-1'></i> 사용시간</label>
					<select class="form-select col form-control ui-timepicker-input"
						id="confTimeCd">
						<c:forEach items="${commonList}" var="common">
							<option value="${common.commonCodeCd}"
								class="form-select">${common.commonCodeSj}</option>
						</c:forEach>
					</select>
					</div>
				</div>
				<div class="row">
					<div class="col mb-3">
						<label for="confRoomReservePw" class="fw-medium"><i
							class='bx bxs-book-alt bx-sm mb-1'></i> 취소용 비밀번호</label> <input
							name="confRoomReservePw" type="password" id="confRoomReservePw"
							class="form-control" placeholder="ex) 1234" required
							maxlength="4" />
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-label-secondary"
					data-bs-dismiss="modal">Close</button>
				<button type="button" onclick="fn_confRoomReserve()"
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
<script src="/resources/js/app/reservation/confRoom.js"></script>