<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2023. 11. 29.      김보영        최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>


	<div class="row mb-5">
					<div class="col-md-6 col-lg-4">
						<h6 class="mt-2 text-muted">날씨</h6>
						<div class="card mb-4">
							<div class="card-body" id="weather">
								<!--이부분에서 작업-->

							</div>
						</div>
						<h6 class="mt-2 text-muted">프로필</h6>
						<div class="card mb-4">
							<div class="card-body">
								<!--이부분에서 작업-->
							</div>
						</div>
						<h6 class="mt-2 text-muted">근태</h6>
						<div class="card mb-4" style="text-align: center;">
							<div class="card-body">
<!-- 								<button class="btn btn-label-primary" onclick="commute()" id="commute" style="width:70%; height: 50px;"> -->
<!-- 									<span class="tf-icons bx bx-pie-chart-alt me-1"></span> -->
<!-- 								</button> --> 
								 <button class="btn btn-label-primary" onclick="commute()" id="commute"  data-bs-toggle="modal" data-bs-target="#modalCenter" style="width:70%; height: 50px;">
								 	<span class="tf-icons bx bx-pie-chart-alt me-1"></span> 
                         		</button>
                       
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-4">
						<h6 class="mt-2 text-muted">근태현황</h6>
						<div class="card mb-4">
							<div class="card-body" style="justify-content: center;">
								<!--이부분에서 작업-->
								<div class="progress">
                      				<div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" id="progressbar">
                      				</div>
                    			</div>								
								<div class="text-muted d-block" style="text-align: center;">
									<small style="margin: 0 7%; font-size: 15px;">월</small>'
									<small style="margin: 0 7%; font-size: 15px;">화</small>'
									<small style="margin: 0 7%; font-size: 15px;">수</small>'
									<small style="margin: 0 7%; font-size: 15px;">목</small>'
									<small style="margin: 0 7%; font-size: 15px;">금</small>
								</div>
								<div class="text-muted d-block" id="annualList">
								</div>
								<!--/이부분에서 작업-->
							</div>
						</div>
						<h6 class="mt-2 text-muted">내결재</h6>
						<div class="card mb-4">
							<div class="card-body">
								<!--이부분에서 작업-->
							</div>
						</div>
					</div>
					<div class="col-md-6 col-lg-4">
						<h6 class="mt-2 text-muted">공지사항</h6>
						<div class="card mb-4">
							<div class="card-body">
								<!--이부분에서 작업-->
							</div>
						</div>
						<h6 class="mt-2 text-muted">내 일감</h6>
						<div class="card mb-4">
							 <a class="nav-link dropdown-toggle" href="javascript:void(0);" data-bs-toggle="dropdown"
			                    aria-expanded="false" data-trigger="hover">
			                    <i class='bx bxs-copy-alt'></i>
			                    　프로젝트</a>
			                  <div class="dropdown-menu">
			                    <c:forEach items="${proj}" var="proj">
			                      <c:if test="${proj.proSttus == 1}">
			                        <a  class="dropdown-item myproject" onclick="myjobGrid()" data-set-prosn="${proj.proSn}">${proj.proNm}</a>
			                      </c:if>
			                    </c:forEach>
			                  </div>
							<div class="card-body ag-theme-balham" id="myjobList" style="height:465px">
								<!--이부분에서 작업-->
							</div>
						</div>
						<h6 class="mt-2 text-muted">오늘의 연차자</h6>
						<div class="card mb-4">
							<div class="card-body">
								<!--이부분에서 작업-->
							</div>
						</div>
					</div>
				</div>

</body>
</html>