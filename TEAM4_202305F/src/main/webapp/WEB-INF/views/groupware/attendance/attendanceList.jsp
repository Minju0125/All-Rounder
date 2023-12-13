<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %> 
<security:csrfInput/>
<div class="layout-wrapper layout-content-navbar">
	<div class="layout-container">
		<!-- Layout container -->
		<div class="layout-page">

			<!-- Content wrapper -->
			<div>
				<!-- Content -->
				<div class="row g-2">
						<div class="card mb-4" style="width: 68%;display:flex;flex-direction: row;height: 348px;padding-top: 28px; height: 450px;">
							<div class="card-body table-responsive text-nowrap " style="width:75%; float:left; display:inline-block;">
								<!--이부분에서 작업-->
								<div style="display:flex; justify-content: center; align-items: center; width:100%;">
									<a href="javascript:;" class="nav-btn go-prev" style="margin: auto 5px;">◀</a>
									<h4 class="year-month" style="margin: auto 5px;"></h4>
									<a href="javascript:;" class="nav-btn go-next" style="margin: auto 5px;">▶</a>
								</div>
								<table class="table table-borderless" style="width:95%;">
										<tr class="progress">
											<td colspan="8" class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar" style="width: 100%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" id="progressbar">
											</td>
										</tr>
								</table>
								<br>
								<table class="table table-bordered" style="text-align:center; width:95%;">
									<tbody class="table-border-bottom-0" id="workTime">
									</tbody>
								</table>
							</div>
							<div class="table-responsive text-nowrap mt-4 mx-4" style="width:22%; float:right; display:inline-block; position : relative;">
								<div style="width:100%; position: absolute;">
									<p style="text-align:center; font-weight: bold; margin-bottom:0%;">주 근무시간</p>
									<table class="table border-top">
										<tbody id="weekAttTime">
										</tbody>
									</table>
								</div>
								<div style="position : absolute; bottom : 5%; margin-top:20%; width:95%;">
									<p style="text-align:center; font-weight: bold; margin-bottom:0%;">올해 연차 현황</p>
									<table class="table border-top">
<!-- 										<thead> -->
<!-- 											<tr> -->
<!-- 												<td colspan="2" style="font-weight:bold;">올해 연차 현황</td> -->
<!-- 											</tr> -->
<!-- 										</thead> -->
										<tbody id="annualStatus">
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="card mb-4" style="width: 30%; margin-left:2%;">
							<div class="card-body">
								<canvas id="pie-chart" width="100%" height="100%">
								</canvas>
							</div>
						</div>
						<div class="card mb-4" style="width: 38%; margin-right:2%; height:40%; text-align: center;">
							<div class="card-body" style="backgroung-color:white;">
								<!--이부분에서 작업-->
								<button onclick="updLog(this)" id="work" value="N" class="btn btn-primary">근무</button>
								<button onclick="updLog(this)" id="meeting" value="M" class="btn btn-danger">회의</button>
								<button onclick="updLog(this)" id="externalWork" value="E" class="btn btn-info">외근</button>
								<button onclick="updLog(this)" id="businessTrip" value="B" class="btn btn-warning">출장</button>
								<button onclick="updLog(this)" id="outing" value="O" class="btn btn-secondary">외출</button>
							</div>
						</div>
						<div class="card mb-4" id="attendanceStart" style="width: 18%; margin-right:2%; margin-bottom: 0%;">
							<div class="card-body" id="attendanceLog" style="text-align:center;">
								<!--이부분에서 작업-->
							</div>
						</div>
						<div class="card mb-4" style="width: 40%;">
							<div class="card-body">
							<h4 class="card-title mb-0" style="text-align: center; width:100%;">오늘의 연차자</h4>
							<br>
								<!--이부분에서 작업-->
								<table class="table border-top" style="text-align:center;">
									<thead>
										<tr>
											<th>부서</th>
											<th>직급</th>
											<th>이름</th>
										</tr>
									</thead>
									<tbody id="annualList">
									
									</tbody>
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
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
<script src="/resources/js/app/attebdabce/attendanceLog.js"></script>





