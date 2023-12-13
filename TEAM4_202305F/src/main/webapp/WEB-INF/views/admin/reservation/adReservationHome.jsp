<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ---------- 		---------  -----------------
* 2023. 12. 01		박민주		최초작성
* Copyright (c) 2023 by DDIT All right reserved
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>

<h4 class="py-3 mb-4"><span class="text-muted fw-light">자원예약관리 / </span> 예약관리</h4>
<div class="layout-wrapper layout-content-navbar">
	<div class="layout-container">
		<!-- Layout container -->
		<div class="layout-page">
			<!-- Content wrapper -->
			<div>
				<div class="row mb-5">
					<!-- 차량 예약 목록 -->
					<div class="col-md-12 col-lg-12">
						<div class="card  mb-3">
							<div class="card-body">
								<h5 class="card-title">
									차량 예약목록
									<button type="button"
										class="btn rounded-pill btn-label-primary"
										onclick="fn_vReservationDelBtn()">삭제</button>
								</h5>
								<hr />
								<div id="vReservationList" class="ag-theme-balham"
									style="height: 500px">
									<!-- agGrid -->
								</div>
							</div>
						</div>
					</div>

					<!-- 회의실 예약 목록 -->
					<div class="col-md-12 col-lg-12">
						<div class="card  mb-3">
							<div class="card-body">
								<h5 class="card-title">
									회의실 예약목록
									<button type="button"
										class="btn rounded-pill btn-label-primary"
										onclick="fn_cReservationDelBtn()">삭제</button>
								</h5>
								<hr />
								<div id="cReservationList" class="ag-theme-balham"
									style="height: 500px">
									<!-- agGrid -->
								</div>
							</div>
						</div>
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

<script>
	var __basePath = './';
</script>
<script
	src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>
<script src="/resources/js/app/admin/reservation/adReservation.js"></script>