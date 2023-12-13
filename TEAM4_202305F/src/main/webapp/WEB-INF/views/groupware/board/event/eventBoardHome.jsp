<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2023. 11. 20.      김보영        최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="realUser" />
</security:authorize>

<div class="content-wrapper">
	<!-- Content -->

	<div class="container-xxl flex-grow-1 container-p-y">
		<h4 class="py-3 mb-4"><span class="text-muted fw-light">게시판 /</span> 경조사게시판</h4>
		<div class="row mt-4">
			<!-- Navigation -->
			<div class="col-lg-3 col-md-4 col-12 mb-md-0 mb-3">
				<div class="d-flex justify-content-between flex-column mb-2 mb-md-0">
					<ul class="nav nav-align-left nav-pills flex-column" role="tablist">
						<li class="nav-item" role="presentation">
							<button class="nav-link active" data-bs-toggle="tab" data-bs-target="#payment" aria-selected="true" 
							role="tab" tabindex="-1" onclick="fn_boardList(1)">
								<i class="bx bx-male-female faq-nav-icon me-1"></i>
								<span class="align-middle">결혼/출산</span>
							</button>
						</li>
						<li class="nav-item" role="presentation">
							<button class="nav-link" data-bs-toggle="tab" data-bs-target="#delivery" aria-selected="false" role="tab"
								tabindex="-1" onclick="fn_boardList(2)">
								<i class="bx bxs-bookmark faq-nav-icon me-1"></i>
								<span class="align-middle">사망</span>
							</button>
						</li>
						<li class="nav-item" role="presentation">
							<button class="nav-link" data-bs-toggle="tab" data-bs-target="#cancellation" aria-selected="false"
								role="tab" tabindex="-1" onclick="fn_boardList(3)">
								<i class='bx bxs-party faq-nav-icon me-1'></i>
								<span class="align-middle">생일</span>
							</button>
						</li>
						<li class="nav-item" role="presentation">
							<button class="nav-link " data-bs-toggle="tab" data-bs-target="#orders" aria-selected="false"
								role="tab" tabindex="-1" onclick="fn_boardList(4)">
								<i class="bx bx-cube faq-nav-icon me-1"></i>
								<span class="align-middle">기타</span>
							</button>
						</li>
						<c:if test="${realUser.username  eq 'admin'}">
						<li class="nav-item mt-3">
							<button class="btn btn-warning" onclick="fn_eventForm()">
								<i class="bx bx-pencil faq-nav-icon me-1"></i>
								<span class="align-middle">등록</span>
							</button>
						</li>
						</c:if>
					</ul>
				</div>
			</div>
			<!-- /Navigation -->

			<!-- FAQ's -->
			<div class="col-lg-9 col-md-8 col-12">
				<div class="tab-content py-0">
					<div class="tab-pane fade active show" id="payment" role="tabpanel">
						<div class="d-flex mb-3 gap-3">
							<div>
								<span class="badge bg-label-primary rounded-2">
									<i class="bx bx-male-female bx-md"></i>
								</span>
							</div>
							<div>
								<h4 class="mb-0">
									<span class="align-middle">결혼/출산</span>
								</h4>
								<span>Happy Anniversary!</span>
							</div>
						</div>
						<div  class="card">
							<div class="card-body">
								<div class="mb-3 col-12 mb-0" id="WBArea">
									
								</div>
							</div>
						</div>
					</div>

					<div class="tab-pane fade "  id="delivery" role="tabpanel">
						<div class="d-flex mb-3 gap-3">
							<div>
								<span class="badge bg-label-primary rounded-2">
									<i class="bx bxs-bookmark bx-md"></i>
								</span>
							</div>
							<div>
								<h4 class="mb-0">
									<span class="align-middle">사망</span>
								</h4>
								<span>Pray for the bliss of dead</span>
							</div>
						</div>
						<div  class="card">
							<div class="card-body">
								<div class="mb-3 col-12 mb-0" id="deadArea">
									
								</div>
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="cancellation" role="tabpanel">
						<div class="d-flex mb-3 gap-3">
							<div>
								<span class="badge bg-label-primary rounded-2">
									<i class="bx bxs-party bx-tada bx-md"></i>
								</span>
							</div>
							<div>
								<h4 class="mb-0"><span class="align-middle">생일</span></h4>
								<span>Happy birthday</span>
							</div>
						</div>
						<div  class="card">
							<div class="card-body">
								<div class="mb-3 col-12 mb-0" id="birthArea">
									
								</div>
							</div>
						</div>
					</div>

					<div class="tab-pane fade " id="orders" role="tabpanel">
						<div class="d-flex mb-3 gap-3">
							<div>
								<span class="badge bg-label-primary rounded-2">
									<i class="bx bx-cube bx-md"></i>
								</span>
							</div>
							<div>
								<h4 class="mb-0">
									<span class="align-middle">기타</span>
								</h4>
								<span>Etc</span>
							</div>
						</div>
						<div  class="card">
							<div class="card-body">
								<div class="mb-3 col-12 mb-0" id="etcArea">
									
								</div>
							</div>
						</div>
					</div>
					<!--페이징 버튼 -->
					<div  class=" mt-3">
						<div class="">
							<div class=" col-12 mb-1 mt-1" id="eventPaging" style="text-align: center;">
							</div>
							<form id="searchForm">
								<div id ="searchUI" class="row g-3 d-flex justify-content-center">
									<input type="hidden" name="page" readonly="readonly"/>
									<input type="hidden" name="eventSttus" id="sttusId" value=""/>
									<div class="col-auto">
										<select name="searchType" class="form-select"> 
											<option value="bbsSj" >제목</option>
										</select>
									</div>
									<div class="col-auto">
										<input name="searchWord" placeholder="입력하세요" class="form-control" />
									</div>
									<div class="col-auto">
										<input type="button" value="검색" id="searchBtn" class="btn btn-primary" />
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<!-- /FAQ's -->
		</div>
		<!-- /Contact -->
	</div>
	<!-- / Content -->
</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/board/event.js"></script>