<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 15.  전수진      최초작성
* 2023. 11. 16.  전수진      결재선 조직도 추가
* 2023. 11. 18.  전수진      결재라인즐겨찾기 ajax 추가
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script src="${pageContext.request.contextPath }/resources/js/ckeditor/ckeditor.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>

<style>
	.card-header {
		text-align: center;
		font-weight: 800;
		background-color: #fff;
	}
	.first {
		height: 450px;
	}
	table {
	    border-collapse: collapse;
	    height: 30px;
	    width: 100%;
	}
	td {
	    border-color: black;
	    border-style: solid;
	    border-width: 1px;
	    height: 30px;
	}
	.title-cell {
	    background-color: #e2e2e2;
	    text-align: center;
	    width: 15%;
	}
	
	strong span {
	    font-size: 11px;
	}
	.error {
	    color: red;
	}

	.drop {
		display: inline-block !important;
	}
	.justify-content-right {
    	justify-content: right !important;
	}
	.bookmark {
	    width: 50px;
	    height: 38.95px;
	}
	#dropdownMenu li {
	    display: flex;
	    align-items: center;
	}
	
	#bookmark {
		width: 160px;
	}
	.alertArea{
		margin-top : 10%;
	}
	.modalUl{
		padding-top : 10px;
	}
	
		
</style>
<div class="content-wrapper">
<!-- Content -->
	<div class="container-xxl flex-grow-1 container-p-y">
		<div class="mb-3">
		<button class="btn btn-sm btn-outline-primary" id="approvalBtn">결재상신</button>
		<!-- 
			시간날때 구현
		<button class="btn btn-sm btn-outline-secondary" id="proxyBtn">임시저장</button>
		 -->
		<button class="btn btn-sm btn-outline-secondary" onclick="location.href = '/sanctionform'" id="listBtn">목록</button>
		</div>
		<div class="row">
		<!-- User Content -->
			<div class="col-xl-8 col-lg-7 col-md-7 order-1 order-md-0">
				<div class="card mb-4">
					<div class="card-body">
						<h2 class="card-header">${formSet.formNm }</h2>
							<div class="table-responsive mb-3">
								<form:form id="sanctionForm" enctype="multipart/form-data" modelAttribute="newSanction" >
								<table>
									<tbody>
										<tr>
											<td class="title-cell">
												<strong><span>제목(필수*)</span></strong>
											</td>
											<td>
												<form:input type="text" path="sanctnSj" style="width: 100%"  required="true" />
												<form:errors path="sanctnSj" element="span" cssClass="error" />
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>작성일자</span></strong>
											</td>
											<td>
												<input type="text"	name="sanctnDate" id="sanctnDate" style="width: 100%" placeholder="자동생성" readonly/>
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>작성부서</span></strong>
											</td>
											<td>
												<input type="text" id="drafterDept" style="width: 100%" value="${empVo.dept.deptName }" readonly/>
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>작성자</span></strong>
											</td>
											<td>
												<input type="text"	name="drafterView" id="drafterView" style="width: 100%"	value="${empVo.empName }" readonly/>
												<input type="hidden" name="drafter" id="drafter" style="width: 100%" value="${empVo.empCd }" readonly/>
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>수신자</span></strong>
											</td>
											<td>
												<select class="select2 select-email-contacts form-select" name="sanctnRcyer" id="sanctnRcyer"></select>
											</td>
										</tr>
									</tbody>
								</table>
								<br />
								<div class="mb-3">
									<textarea rows="20"	cols="50" name="sanctnSourc" id="sanctnSourc">${formSet.formSourc }</textarea>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>

			<div class="col-xl-4 col-lg-5 col-md-5 order-0 order-md-1">
				<div class="card mb-4">
					<div class="card-body first">
						<div class="d-flex justify-content-right pt-3">
							<button type="button" class="btn btn-primary bookmark" data-bs-toggle="modal" data-bs-target="#addLine"><i class='bx bx-user-plus'></i></button>

							<div class="btn-group ms-1">
								<button type="button" id="bookmarkList" class="btn btn-primary btn-icon dropdown-toggle hide-arrow" data-bs-toggle="dropdown" aria-expanded="false">
									<i class="bx bx-star"></i>
								</button>
								<ul id="dropdownMenu" class="dropdown-menu dropdown-menu-end"></ul>
							</div>
						</div>
						<h5 class="pb-2 border-bottom mb-4"><i class="tf-icons bx bx-task"></i>결재선</h5>
						<p>${empVo.empName } ${empVo.common.commonCodeSj }</p>
						<form>
							<div class="info-container" id="sanctionLine">
							</div>
						</form>
					</div>
				</div>
				<div class="card mb-4">
					<div class="card-body second">
						<div class="mb-3">
							<label class="form-label">첨부파일</label>
							<input type="file" name="sanctionFile" class=" form-control" form="sanctionForm" id="sanctionFile" multiple />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal 띄우기 -->
<!-- Large Modal -->
<div class="modal fade" id="addLine" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-lg" role="document">
		<div class="modal-content">
			<div class="modal-header pb-2 border-bottom mb-4">
				<h5 class="modal-title" id="exampleModalLabel3">결재선 지정</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			
			<div class="modal-body">
				<div class="row">
					<div class="col mb-3">
						<div id="orgTreeContainer"></div>
						
					</div>
					<div class="col mb-3 ">
						<div class="d-flex justify-content-end mb-4">
						<div class="btn-group">
	                      <button type="button" class="btn btn-primary bookmark" data-bs-toggle="dropdown" aria-expanded="false">
	                      <i class="bx bx-star"></i>
	                      <i class='bx bx-chevron-down' ></i>
	                      </button>
	                      <ul class="dropdown-menu">
	                        <li class="d-flex align-items-center">
		                        <a class="dropdown-item" href="javascript:void(0);">
		                         	<div class="d-flex">
				                        <input type="text" class="form-control" id="bookmark" placeholder="즐겨찾기 이름 입력!" />
				                        <button type="button" class="btn btn-label-secondary" id="saveBtn">저장</button>
			                        </div>
		                        </a>
	                        </li>
	                      </ul>
	                    </div>
	                    
						<button type="button" class="btn btn-primary bookmark ms-1" id="remove"><i class='bx bx-refresh'></i></button>
						
						</div>
						
						<div class="alert d-flex align-items-center bg-label-info mb-0" role="alert">
						  <span class="alert-icon text-info me-2 bg-label-light px-1 rounded-2">
						    <i class="bx bx-info-circle bx-xs"></i>
						  </span>
						  <ul class="modalUl">
							  <li><small>현재의 결재선을 즐겨찾기로 추가하려면 상단의 ☆을 눌러 이름지정 후 등록이 가능합니다!</small></li>
						  </ul>
						</div>
						
						
						
						
						<div class="alert alert-secondary alertArea" role="alert">
							<div id="orgTreeResult"></div>
                        </div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" id="closeBtn" class="btn btn-label-secondary" data-bs-dismiss="modal">닫기</button>
				<button type="button" id="addBtn" class="btn btn-primary" >확인</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function () {
	 	
		CKEDITOR.replace('sanctnSourc');
	});
	
	 var formNo = ${formNo}; //경로변수를 js로 전달하기 위해 선언
</script>
<script src="${pageContext.request.contextPath }/resources/js/app/sanction/sanctionDoc.js"></script>
