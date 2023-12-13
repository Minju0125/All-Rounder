<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 28.  전수진      최초작성
* 2023. 12. 04.  전수진      대결권설정 유효성검사, 대결권해제시 내가결재할문서list update
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>

.modalUl{
	padding-top : 15px;
}
#select2-proxy-container {
     border-bottom: 1px solid rgb(105, 108, 255);
}
.text-muted{
    margin-top: 2%;
}


</style>

<div class="content-wrapper">
<!-- Content -->
<div class="container-xxl flex-grow-1 container-p-y">
  <h4 class="py-3 mb-4"><span class="text-muted fw-light">전자결재 /</span> Dashboard</h4>
<div class="d-flex flex-column flex-md-row justify-content-between align-items-start align-items-md-center mb-3">
  <div class="d-flex flex-column justify-content-center">
  </div>
  <div class="d-flex align-content-center flex-wrap gap-2" id="buttonArea">
	<c:if test="${not empty receiveProxy }">
		<small class="text-muted">대결권 설정기간 : ${receiveProxy.alwncDate} 부터 ${receiveProxy.extshDate} 까지 / 대결권 부여자 : [${receiveProxy.prxsanctnAlwncDeptName }] ${receiveProxy.prxsanctnAlwncNm } ${receiveProxy.prxsanctnAlwncRankNm }</small>
	</c:if>
	<c:choose>
		<c:when test="${empty proxy}">
			<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#proxyModal">
			  대결권설정
			</button>
		</c:when>
		<c:otherwise>
				<small class="text-muted">대결권 설정기간 : ${proxy.alwncDate} 부터 ${proxy.extshDate} 까지 / 대결권 수여자 : [${proxy.prxsanctnCnferDeptName }] ${proxy.prxsanctnCnferNm } ${proxy.prxsanctnCnferRankNm }</small>
			<button type="button" class="btn btn-label-primary offProxy">
			  대결권해제
			</button>
		</c:otherwise>
	</c:choose>
  </div>
</div>
<input type="hidden" id="prxsanctnNo" value="${proxy.prxsanctnNo}">
  <!-- Card Border Shadow -->
  <div class="row">
    <div class="col-sm-6 col-lg-3 mb-4">
      <div class="card card-border-shadow-primary h-100">
        <div class="card-body">
          <p class="mb-1">기안</p>
          <div class="d-flex align-items-center mb-2 pb-1">
            <div class="avatar me-2">
              <span class="avatar-initial rounded bg-label-primary"><i class="bx bxs-truck"></i></span>
            </div>
            <h4 class="ms-1 mb-0"> <span id="drafting"></span> 건</h4>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-lg-3 mb-4">
      <div class="card card-border-shadow-danger h-100" >
        <div class="card-body">
          <p class="mb-1">반려</p>
          <div class="d-flex align-items-center mb-2 pb-1">
            <div class="avatar me-2">
              <span class="avatar-initial rounded bg-label-danger"><i class="bx bx-git-repo-forked"></i></span>
            </div>
            <c:if test="${not empty rejectNo}">
	            <h4 class="ms-1 mb-0">${rejectNo}</span> 건</h4>
            </c:if>
            <c:if test="${empty rejectNo}">
	            <h4 class="ms-1 mb-0">0</span> 건</h4>
            </c:if>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-lg-3 mb-4">
      <div class="card card-border-shadow-warning h-100" >
        <div class="card-body">
          <p class="mb-1">결재대기</p>
          <div class="d-flex align-items-center mb-2 pb-1">
            <div class="avatar me-2">
              <span class="avatar-initial rounded bg-label-warning"><i class="bx bx-error"></i></span>
            </div>
            <h4 class="ms-1 mb-0"> <span id="pending"></span> 건</h4>
          </div>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-lg-3 mb-4">
      <div class="card card-border-shadow-info h-100">
        <div class="card-body">
          <p class="mb-1">수신문서</p>
          <div class="d-flex align-items-center mb-2 pb-1">
            <div class="avatar me-2">
              <span class="avatar-initial rounded bg-label-info"><i class="bx bx-time-five"></i></span>
            </div>
            <h4 class="ms-1 mb-0"> <span id="receive"></span> 건</h4>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!--/ Card Border Shadow -->
  <div class="row">
    <!-- On route vehicles Table -->
    <div class="col-12 order-5 mb-4">
      <div class="card">
        <div class="card-header d-flex align-items-center justify-content-between">
          <div class="card-title mb-0">
            <h5 class="m-0 me-2">내가 상신한 문서</h5>
          </div>
        </div>
        <div class="card-datatable table-responsive">
			<table class="table">
				<thead class="table-light">
					<tr>
						<th class="width10">일련번호</th>
						<th class="width20">결재양식</th>
						<th class="width30">제목</th>
						<th class="width10">기안자</th>
						<th class="width10">기안부서</th>
						<th class="width10">작성일자</th>
						<th class="width10">상태</th>
					</tr>
				</thead>
				<tbody class="table-border-bottom-0" id="drafterTrBody">
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7">
						</td>
					</tr>
				</tfoot>
			</table>
        </div>
      </div>
    </div>
  </div>
  <!--/ On route vehicles Table -->
  <!-- On route vehicles Table -->
    <div class="col-12 order-5 mb-4">
      <div class="card">
        <div class="card-header d-flex align-items-center justify-content-between">
          <div class="card-title mb-0">
            <h5 class="m-0 me-2">내가 결재할 문서</h5>
          </div>
        </div>
        <div class="card-datatable table-responsive">
       <table class="table">
				<thead class="table-light">
					<tr>
						<th class="width10">일련번호</th>
						<th class="width20">결재양식</th>
						<th class="width30">제목</th>
						<th class="width10">기안자</th>
						<th class="width10">기안부서</th>
						<th class="width10">작성일자</th>
						<th class="width10">상태</th>
					</tr>
				</thead>
				<tbody class="table-border-bottom-0" id="sanctnerTrBody">
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7">
						</td>
					</tr>
				</tfoot>
			</table>
        </div>
      </div>
    </div>
  </div>
  <!--/ On route vehicles Table -->
</div>
<!-- / Content -->

<!-- Large Modal -->
<div class="modal fade" id="proxyModal" tabindex="-1" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header pb-2 border-bottom mb-4">
        <h5 class="modal-title" id="exampleModalLabel3">대결권 설정</h5>
        <button
          type="button"
          class="btn-close"
          data-bs-dismiss="modal"
          aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col mb-3">
            <label for="nameLarge" class="form-label">대결자 검색</label>
            <select class="select2 select-email-contacts form-select" name="prxsanctnCnfer" id="prxsanctnCnfer"></select>
          </div>
        </div>
        <div class="row g-2 mb-3">
          <div class="col mb-0">
            <label for="dobLarge" class="form-label">대결권 설정 기간</label><br />
            <input id="alwncDate" name="alwncDate" class="form-control mb-4" placeholder="시작일자선택" />
          </div>
          <div class="col mb-0">
            <label for="dobLarge" class="form-label">　</label><br />
            <input id="extshDate" name="extshDate" class="form-control mb-4" placeholder="종료일자선택"/>
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="nameLarge" class="form-label">대결권 지정사유</label>
            <input type="text" id="alwncReason" name="alwncReason" class="form-control mb-4" placeholder="지정사유를 입력해주세요!" />
          </div>
        </div>
        <div class="row">
	        <div class="col mb-3">
				<div class="alert d-flex align-items-center bg-label-info mb-0" role="alert">
				  <span class="alert-icon text-info me-2 bg-label-light px-1 rounded-2">
				    <i class="bx bx-info-circle bx-xs"></i>
				  </span>
				  <ul class="modalUl">
					  <li><small>대결자를 복수로 지정할 수 없습니다.</small></li>
					  <li><small>대결권설정기간은 익일부터 설정이 가능합니다.</small></li>
					  <li><small>대결권설정기간은 2주안으로만 설정이 가능합니다.</small></li>
				  </ul>
				</div>
			</div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">닫기</button>
        <button type="button" class="btn btn-primary" id="saveBtn">저장</button>
      </div>
    </div>
  </div>
</div>

<script src="${pageContext.request.contextPath }resources/js/app/sanction/sanctionHome.js"></script>
