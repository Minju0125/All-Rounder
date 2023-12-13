<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ---------- 		---------  -----------------
* 2023. 11. 23.     박민주        차량등록, 조회
* 2023. 11. 24.     박민주        차량수정, 삭제
* Copyright (c) 2023 by DDIT All right reserved
--%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>        

<h4 class="py-3 mb-4"><span class="text-muted fw-light">자원예약관리 / </span>자원관리</h4>
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
      <div>
        <!-- Content -->
        <!-- navbar -->
        
        <div class="col-md-7 col-lg-7">
		</div>
        <div class="row mb-5">
          
          <!-- 등록된 차량 목록 -->
		<div class="col-md-12 col-lg-12">
            <div class="card  mb-3">
              <div class="card-body">
                <h5 class="card-title">
                	차량 목록
				        	<button type="button" class="btn btn-label-primary" data-bs-toggle="modal" data-bs-target="#backDropModal">
	                 	<span>추가</span>
	                </button>
	                <button type="button" class="btn btn-label-primary" onclick="fn_vhcleDelBtn()">삭제</button>
				        </h5>
                <hr/>
                 <div id="vehicleList" class="ag-theme-balham" style="height:500px">
                 	<!-- agGrid -->
                </div>
              </div>
            </div>
          </div>
          
         <!-- 등록된 회의실 목록 -->
		<div class="col-md-12 col-lg-12">
            <div class="card  mb-3">
              <div class="card-body">
                <h5 class="card-title">
                	회의실 목록
				        	<button type="button" class="btn btn-label-primary" data-bs-toggle="modal" data-bs-target="#backDropModal2">
	                 	<span>추가</span>
	                </button>
	                <button type="button" class="btn btn-label-primary" onclick="fn_confRoomDelBtn()">삭제</button>
				        </h5>
                <hr/>
                 <div id="confRoomList" class="ag-theme-balham" style="height:500px">
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
<%-- <form method="get" id="searchForm" class="border" >
	<hidden name="searchType" readonly="readonly" placeholder="searchType"/>
	<hidden name="searchWord" readonly="readonly" placeholder="searchWord"/>
	<input type="hidden" name="page" readonly="readonly" placeholder="page"/>
</form> --%>

<!-- 차량 등록 모달 -->
<div class="modal fade" id="backDropModal" data-bs-backdrop="static" tabindex="-1" style="display: none;"
  aria-hidden="true">

  <div class="modal-dialog">
    <form action="/vehicle" id="newVehicleForm" class="modal-content" method="POST" enctype="multipart/form-data">
      <security:csrfInput/>
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle"><i class='bx bxs-comment-add bx-md'></i> 신규 차량 등록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <hr>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col mb-3">
            <label for="vhcleModel" class="fw-medium"><i class='bx bxs-book-alt bx-sm mb-1' ></i> 차량모델</label>
            <input name="vhcleModel"  type="text" id="vhcleModel" class="form-control" placeholder="ex) 아반떼" required />
          </div>
        </div>
        <div class="row">
          <div class="col-md-8 col-lg-8 mb-3">
            <label for="vhcleNo" class="fw-medium"><i class="bx bxs-book-alt bx-sm mb-1"></i> 차량번호</label>
            <input name="vhcleNo" type="text" id="vhcleNo" class="form-control" placeholder="ex) 305바2023(띄어쓰기 없이)" required>
          </div>
          <div class="col-md-4 col-lg-4 mb-3">
            <label for="vhcleCapacity" class="fw-medium"><i class="bx bxs-book-alt bx-sm mb-1"></i> 수용인원(명)</label>
            <input name="vhcleCapacity" type="number" id="vhcleCapacity" class="form-control" placeholder="6" required>
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="vhcleImgUrlFile" class="fw-medium"><i class='bx bx-file-find bx-sm mb-1'></i> 이미지 업로드</label>
            <input type="file" class="form-control" id="vhcleImgUrlFile" name ="vhcleImgUrlFile" multiple />
            <div id="fileList"></div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">
          Close 
        </button>
        <button type="button" onclick="fn_vehicleInsert()" class="btn btn-primary">Save</button>
      </div>
    </form>
  </div>
</div>

<!-- 회의실 등록 모달 -->
<div class="modal fade" id="backDropModal2" data-bs-backdrop="static" tabindex="-1" style="display: none;"
  aria-hidden="true">
  <div class="modal-dialog">
    <form action="/vehicle" id="newVehicleForm" class="modal-content" method="POST" enctype="multipart/form-data">
      <security:csrfInput/>
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle"><i class='bx bxs-comment-add bx-md'></i> 신규 회의실 등록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <hr>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col-md-12 col-lg-12 mb-3">
            <label for="confRoomNm" class="fw-medium"><i class="bx bxs-book-alt bx-sm mb-1"></i> 회의실명</label>
            <input name="confRoomNm" type="text" id="confRoomNm" class="form-control" required>
          </div>
          <div class="col-md-6 col-lg-6 mb-3">
            <label for="confRoomCd" class="fw-medium"><i class="bx bxs-book-alt bx-sm mb-1"></i> 회의실코드</label>
            <input name="confRoomCd" type="text" id="confRoomCd" class="form-control" placeholder="ex) A202" maxlength="4" required readonly="readonly">
            <select id="selectStair" class="form-control form-control-sm" onchange="changeCodeChar()" style="width:30%">
        		<option selected disabled="disabled">층수선택</option>
        		<option value="A">1층</option>
        		<option value="B">2층</option>
        		<option value="C">3층</option>
        	</select>
          </div>
          <div class="col-md-6 col-lg-6 mb-3">
            <label for="confRoomCapacity" class="fw-medium"><i class="bx bxs-book-alt bx-sm mb-1"></i> 수용인원(명)</label>
            <input name="confRoomCapacity" type="number" id="confRoomCapacity" class="form-control" placeholder="ex) 6" required>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">
          Close 
        </button>
        <button type="button" onclick="fn_confRoomInsert()" class="btn btn-primary">Save</button>
      </div>
    </form>
  </div>
</div>

<script>var __basePath = './';</script>
<script src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>
<script src="/resources/js/app/admin/reservation/adVehicle.js"></script>
<script src="/resources/js/app/admin/reservation/adConfRoom.js"></script>