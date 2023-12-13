<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ---------- 		---------  -----------------
* 2023. 11. 10.     김보영        최초작성
* 2023. 11. 14.     김보영        일감리스트
* Copyright (c) 2023 by DDIT All right reserved
--%>



<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>        


<h1 class="mb-0">${proInfo.proNm}</h1>
<h5 class="mb-0">${proInfo.proBdate} ~ ${proInfo.proEdate }</h5><br>
<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
      <div>
        <!-- Content -->
        <!-- navbar -->
        <input type="hidden" value="${proInfo.proBdate }"  id="projectBdate"/>
        <input type="hidden" value="${proInfo.proEdate }"  id="projectEdate"/>
        <div class="col-md-7 col-lg-7">
          <ul class="nav nav-pills mb-3 nav-fill" role="tablist">
            <li class="nav-item" role="presentation">
              <a class="nav-link dropdown-toggle" data-bs-toggle="dropdown" href="#" aria-expanded="false"
                data-trigger="hover">
                <i class='bx bxs-copy-alt'></i>
                　프로젝트</a>
              <div class="dropdown-menu">
                <c:forEach items="${proj}" var="proj">
                  <c:if test="${proj.proSttus == 1}">
                    <a class="dropdown-item" href="/job/${proj.proSn}/home">${proj.proNm}</a>
                  </c:if>
                </c:forEach>
              </div>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link active" href="javascript:void(0)">
                <i class='bx bx-edit'></i>
                　일감</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link" href="/issue/${proSn}/home">
                <i class='bx bx-calendar-exclamation'></i>
                　이슈</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link" href="/pms/gantt/${proSn}">
                <i class='bx bx-chart'></i>
                　간트차트</a>
            </li>
            <li class="nav-item" >
	            <c:if test="${role eq 'leader'}">
              <a class="nav-item nav-link " href="/pleader/${proSn }">
                <i class='bx bxs-doughnut-chart'></i>
                　리더통계</a>
	            </c:if>
	            <c:if test="${role ne 'leader'}">
              <a class="nav-item nav-link disabled " href="javascript:void(0)">
                <i class='bx bxs-doughnut-chart'></i>
                　리더통계</a>
	            </c:if>
            </li>
          </ul>
        </div>
        
        <!-- /navbar -->
        <!-- 일감 진행상태 -->
        <div class="card mb-4">
          <div class="card-widget-separator-wrapper">
            <div class="card-body card-widget-separator">
              <div class="row gy-4 gy-sm-1">
                <div class="col-sm-20 col-lg-20" style="width: 20%;">
                  <div class="d-flex justify-content-between align-items-start card-widget-1 border-end pb-3 pb-sm-0">
                    <div>
                      <h3 class="mb-2">${jobCnt.aaCnt}</h3>
                      <p class="mb-0 fw-bold">진행</p>
                    </div>
                    <div class="avatar me-sm-4">
                      <span class="avatar-initial rounded bg-label-primary">
                      	<i class='bx bxs-send bx-sm'></i>
                      </span>
                    </div>
                  </div>
                  <hr class="d-none d-sm-block d-lg-none me-4">
                </div>
                <div class="col-sm-20 col-lg-20" style="width: 20%;">
                  <div class="d-flex justify-content-between align-items-start card-widget-2 border-end pb-3 pb-sm-0">
                    <div>
                      <h3 class="mb-2">${jobCnt.bbCnt}</h3>
                      <p class="mb-0 fw-bold">요청</p>
                    </div>
                    <div class="avatar me-lg-4">
                      <span class="avatar-initial rounded bg-label-success">
                      	<i class='bx bxs-bell bx-sm' ></i>
                      </span>
                    </div>
                  </div>
                  <hr class="d-none d-sm-block d-lg-none">
                </div>
                <div class="col-sm-20 col-lg-20" style="width: 20%;">
                  <div class="d-flex justify-content-between align-items-start card-widget-2 border-end pb-3 pb-sm-0">
                    <div>
                      <h3 class="mb-2">${jobCnt.ccCnt}</h3>
                      <p class="mb-0 fw-bold">피드백</p>
                    </div>
                    <div class="avatar me-lg-4">
                      <span class="avatar-initial rounded bg-label-danger">
                     	 <i class='bx bxs-message-error bx-sm'></i>
                      </span>
                    </div>
                  </div>
                  <hr class="d-none d-sm-block d-lg-none">
                </div>
                <div class="col-sm-20 col-lg-20" style="width: 20%;">
                  <div class="d-flex justify-content-between align-items-start border-end pb-3 pb-sm-0 card-widget-3">
                    <div>
                      <h3 class="mb-2">${jobCnt.ddCnt}</h3>
                      <p class="mb-0 fw-bold">보류</p>
                    </div>
                    <div class="avatar me-sm-4">
                      <span class="avatar-initial rounded bg-label-warning">
                        <i class="bx bxs-hand bx-sm"></i>
                      </span>
                    </div>
                  </div>
                </div>
                <div class="col-sm-20 col-lg-20" style="width: 20%;">
                  <div class="d-flex justify-content-between align-items-start">
                    <div>
                      <h3 class="mb-2">${jobCnt.eeCnt}</h3>
                      <p class="mb-0 fw-bold">완료</p>
                    </div>
                    <div class="avatar">
                      <span class="avatar-initial rounded bg-label-info">
                      	<i class='bx bxs-comment-check bx-sm'></i>
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /일감 진행상태 -->
        
        <div class="row">
            <div class="col-xl-4 col-lg-5 col-md-5">
              <!-- 일감등록 -->
              <div class="card mb-4">
                <div class="card-body bg-gradient-primary" style="border-radius: 7px;">

                  <div class="row g-3 ">
                    <div class="col-5 col-xxl-5 col-xl-12">
                      <button type="button" class="btn  btn-label-primary" data-bs-toggle="modal"
                        data-bs-target="#backDropModal">
                        <span class="tf-icons bx bx-paper-plane me-1"></span>일감등록
                      </button>
                    </div>
                    <div class="col-7 col-xxl-7 col-xl-12" style="text-align: -webkit-center;">
                      <img src="/resources/assets/img/illustrations/lady-with-laptop-light.png" alt=""
                        style="height: 140px;">
                    </div>
                  </div>
                </div>
              </div>
              <!--/ 일감등록 -->
              <!-- 이슈 일감 -->
              <div class="card mb-4">
                <div class="card-body">
                  <h6><i class="bx bx-calendar-exclamation mb-1"></i> 이슈 일감</h6>
                	<div id="issueList" class="ag-theme-alpine" style="height:230px">
                	</div>
                </div>
              </div>
              <!--/ 이슈 일감 -->
            </div>
            <div class="col-xl-8 col-lg-8 col-md-8">
              <!-- 내 일감 -->
              <div class="card card-action mb-4">
                <div class="card-body">
                  <h5 class="card-title"><i class='bx bx-book-heart bx-md mb-1'></i> 내 일감</h5>
                  <div id="myjobList" class="ag-theme-alpine" style="height:425px">
                  </div>
                </div>
                <!--/ 내 일감 -->
              </div>
            </div>
		</div>
        
        
        
        
        

        <div class="row">
          <div class="col-md-4">
            <!--  내 일감 상태 -->
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title"><i class='bx bxs-doughnut-chart bx-md mb-1'></i> 내 일감 상태</h5>
                <div style="height:295px;text-align: -webkit-center;">
                  <canvas id="jobChart" style="height:295px"></canvas>
                </div>
              </div>
            </div>
            <!--/ 내 일감 상태 -->
          </div>
          <div class="col-md-5">
            <!-- 참여자 -->
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title"><i class='bx bxs-face bx-md mb-1'></i> 참여자</h5>
                <div id="pMemberList" class="ag-theme-alpine" style="height:295px">
                </div>
              </div>
              <!--/ 참여자 -->
            </div>
          </div>
          <div class="col-md-3">
            <!-- 요청내역 -->
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-title"><i class='bx bxs-bell bx-sm mb-1' ></i> 요청내역</h5>
                <div id="pMemberList" class="ag-theme-balham table-responsive" style="height:295px">
                	<c:if test="${not empty myReq}">
                 		<c:forEach items="${myReq }" var="req" varStatus="i">
	                	<c:set var="color" value=""/>
	                  	<c:if test="${i.index % 5 eq 0 }">
	                  		<c:set var="color" value="info"/>
	                  	</c:if>
	                  	<c:if test="${i.index % 5 eq 1 }">
	                  		<c:set var="color" value="primary"/>
	                  	</c:if>
	                  	<c:if test="${i.index % 5 eq 2 }">
	                  		<c:set var="color" value="danger"/>
	                  	</c:if>
	                  	<c:if test="${i.index % 5 eq 3 }">
	                  		<c:set var="color" value="warning"/>
	                  	</c:if>
	                  	<c:if test="${i.index % 5 eq 4 }">
	                  		<c:set var="color" value="success"/>
	                  	</c:if>
                		<div class="d-flex align-items-center gap-3" style="scale: 90%;margin-left: -10px;">
	                    	<span class="bg-label-${color } p-2 rounded">
	                      		<i class="bx bx-bulb bx-sm"></i>
	                    	</span>
	                   		<div class="content-right">
	                      	<p class="mb-0">기한: ${req.jobEdate}</p>
	                      	<h5 class="text-${color } mb-3"><a style="text-decoration: none; color: inherit;" href="/job/${proSn}/${req.jobSn}/detail">${req.jobSj}</a></h5>
	                    	</div>
                  		</div>
                  		</c:forEach>
                  	</c:if>
                  	<c:if test="${empty myReq}">
	                   <h5 class="mb-0">요청내역 없음</h5>
                  	</c:if>
                </div>
              </div>
              <!--/ 요청내역 -->
            </div>
          </div>

          

            <!--일감테이블-->
            <div class="card">
              <input type="hidden" class="divider-text" data-pro-sn="${proSn}" />
              <div class="table-responsive text-nowrap card-body">
                <h5 class="card-title"><i class='bx bx-book-heart bx-md mb-1'></i>일감목록</h5>
                <hr>
                <table class="table card-table">
                  <colgroup>
			    	<col width="45%" />
			    	<col width="10%" />
			    	<col width="10%" />
			    	<col width="10%" />
			    	<col width="10%" />
			    	<col width="15%" />
			  	  </colgroup>
                  <thead>
                    <tr>
                      <th>일감명</th>
                      <th>작성자</th>
                      <th>진행도</th>
                      <th>담당자</th>
                      <th>상태</th>
                      <th>마감일</th>
                    </tr>
                  </thead>
                  <tbody id="jobList" class="table-border-bottom-0">
                    <!-- 일감리스트 비동기출력 -->
                  </tbody>
                  <tfoot id="jobPaging">
                  </tfoot>
                </table>
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

<!-- 일감등록 -->
<div class="modal fade" id="backDropModal" data-bs-backdrop="static" tabindex="-1" style="display: none;"
  aria-hidden="true">

  <div class="modal-dialog">
    <form action="/job/${proSn}/insert" id="formJobSettings" class="modal-content fv-plugins-bootstrap5 fv-plugins-framework" method="POST" 
      enctype="multipart/form-data">
      <security:csrfInput />
      <input type="hidden" id="tempProSn" value='<c:out value="${proSn}"/>' />
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle"><i class='bx bxs-comment-add bx-md'></i> 일감등록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        <hr>
      </div>
      <div class="modal-body">
        <div class="row">
          <div class="col mb-3">
            <label class="fw-medium"><i class='bx bxs-book-add bx-sm mb-1'></i> 상위일감 선택</label>
            <select name="jobuSn" class="form-select" tabindex="0" id="insertjobuSn" required>
              <c:if test="${role eq 'leader'}">
                <option value="">(선택)</option>
              </c:if>
              <c:forEach items="${jobList}" var="uJob">
                <option value="${uJob.jobSn}" data-bdate="${uJob.jobBdate}" data-edate="${uJob.jobEdate}" id="selectUjob" >${uJob.jobSj}</option>
              </c:forEach>
            </select>
            <input type="hidden" value="${uJob.jobSn}" id="insertTempUJob" />
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="jobSj" class="fw-medium"><i class='bx bxs-book-alt bx-sm mb-1'></i> 일감명</label>
            <div class="input-group has-validation">
            	<input name="jobSj" type="text" id="jobSj" class="form-control rounded-1" placeholder="일감제목을 입력하세요"/>
          	 </div>
          </div>
        </div>
        <div class="row g-2" style="text-align: center; display:none" id="jobStcdDiv">
        
          <div class="col mb-3">
            <button onclick="fn_jobStcd('1')" type="button"
              class="btn-jobStcd btn rounded-pill btn-label-primary" value="1">진행</button>
          </div>
          <div class="col mb-3">
            <button onclick="fn_jobStcd('2')" type="button"
              class="btn-jobStcd btn rounded-pill btn-label-success" value="2">요청</button>
          </div>
          <div class="col mb-3">
            <button onclick="fn_jobStcd('3')" type="button"
              class="btn-jobStcd btn rounded-pill btn-label-danger"  value="3">피드백</button>
          </div>
          <div class="col mb-3">
            <button onclick="fn_jobStcd('4')" type="button"
              class="btn-jobStcd btn rounded-pill btn-label-warning" value="4">보류</button>
          </div>
          <div class="col mb-3">
            <button onclick="fn_jobStcd('5')" type="button"
              class="btn-jobStcd btn rounded-pill btn-label-info" value="5">완료</button>
          </div>
           <div class="input-group has-validation">
          	<input id="jobStcd" type="hidden" name="jobStcd" value="" />
          </div>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="jobBdate" class="fw-medium"><i class='bx bx-calendar-edit bx-sm mb-1'></i> 시작일</label>
            <div class="input-group has-validation">
            	<input type="date"  name="jobBdate"  id="jobBdate" class="form-control rounded-1" >
          	</div>
          </div>
          <div class="col mb-3">
            <label for="jobEdate" class="fw-medium"><i class='bx bx-calendar-exclamation bx-sm mb-1'></i>
              종료일</label>
            <div class="input-group has-validation">
            	<input type="date" name="jobEdate"  id="jobEdate" class="form-control rounded-1" >
          	</div>
          </div>
        </div>
        <c:if test="${role eq 'leader'}">
        <div id="proPeriod">
        	<label for="nameBackdrop" class="fw-light mb-3 proPeriod">프로젝트 기간 : ${proInfo.proBdate} ~ ${proInfo.proEdate }</label>
        </div>
        </c:if>
        <div id="ujobPeriod">
        	<label for="nameBackdrop" class="fw-light mb-3 ujobPeriod">상위일감 기간 :  <span id="uJobBdate"></span> ~ <span id="uJobEdate"></span></label>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="jobCn" class="fw-medium"><i class='bx bx-edit bx-sm mb-1'></i> 내용</label>
            <div class="input-group has-validation">
            	<textarea name="jobCn" rows="5" cols="20" id="jobCn" class="form-control"></textarea>
          	</div>
          </div>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="dobBackdrop" class="fw-medium"><i class='bx bx-file-find bx-sm mb-1'></i> 첨부파일</label>
            <input type="file" class="form-control" id="insertJobFiles" name="jobFile" multiple />
            <div id="fileList"></div>
          </div>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium d-block"><i class='bx bx-universal-access bx-sm mb-1'></i>
              담당자</label>
            <c:if test="${not empty proM }">
              <c:forEach items="${proM}" var="proM" varStatus="i">
                <div class=" col mx-2 form-check custom-option custom-option-basic mb-3 checked d-inline-block">
                  <label class="form-check-label custom-option-content" for="proM${i.index }">
                    <input class="form-check-input insertchar" type="checkbox" id="proM${i.index }" name="tempEmpCd"
                      value="${proM.emp.empCd}">
                    <span class="custom-option-header pb-0">
                      <span class="fw-medium">${proM.emp.empName}</span>
                    </span>
                  </label>
                </div>
              </c:forEach>
            </c:if>
          </div>
        </div>
        <div class="row g-2" id="jobprogDiv">
          <div class="col mb-3">
            <label for="jobProgrs" class="fw-medium"><i class='bx bx-objects-horizontal-left bx-sm mb-1'></i>
              진행도</label>
            <select name="jobProgrs" class="form-select" tabindex="0" id="jobProgrs">
              <c:forEach begin="0" end="100" step="10" var="percentage" varStatus="loop">
                <option value="${percentage}">${percentage}%</option>
              </c:forEach>
            </select>
          </div>
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium"><i class='bx bx-error-circle bx-sm mb-1'></i> 우선순위</label>
            <select name="jobPriort" class="form-select" tabindex="0" id="roleEx4">
              <option value="1">긴급</option>
              <option value="2">높음</option>
              <option value="3" selected>보통</option>
              <option value="4">낮음</option>
            </select>
            <input type="hidden" value="${proSn }" name="proSn">
            <input type="hidden" value="" name="jobSn">
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-label-warning"  onclick="fillDummyData()">
          시연용
        </button>
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">
          Close
        </button>
        <button type="button" onclick="fn_jobInsert()" class="btn btn-primary">Save</button>
      </div>
    </form>
  </div>
</div>






<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>var __basePath = './';</script>
<script src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/job/job.js"></script>

<script>

function fillDummyData() {
    // 각 입력 태그에 더미값 설정
    document.getElementById('jobSj').value = '일감등록 form validation 기능 구현';
    document.getElementById('jobBdate').value = '2023-12-01';
    document.getElementById('jobEdate').value = '2023-12-10';
    document.getElementById('jobCn').value = '사이트 https://formvalidation.io/ 참고바람';
    document.getElementById('jobProgrs').value = '50';
    document.getElementById('jobPriort').value = '1';
}
	

  //초기화
  if($('#insertjobuSn').val() ==""){
    $('#jobStcdDiv').hide();
    $('#jobStcd').prop("disabled",true);
    $('#ujobPeriod').hide();
  }else {
    $('#jobStcdDiv').show();
    $('#jobStcd').prop("disabled",false);
    $('#ujobPeriod').show();
    $('#uJobBdate').text($(this).find("option:selected").data("bdate"));
    $('#uJobEdate').text($(this).find("option:selected").data("edate"));
  } 
  
  //상위일감 선택시에는 상태버튼 안보이기.
  $('#insertjobuSn').on("change", function(){
    if($(this).val() ==""){
      $('#jobStcdDiv').hide();
      $('#jobStcd').prop("disabled",true);
      $('#ujobPeriod').hide();
      $('#proPeriod').show();
    }else {
      $('#jobStcdDiv').show();
      $('#jobStcd').prop("disabled",false);
      $('#ujobPeriod').show();
      $('#proPeriod').hide();
      $('#uJobBdate').text($(this).find("option:selected").data("bdate"));
      $('#uJobEdate').text($(this).find("option:selected").data("edate"));
    } 
  });

	
	
  /*chart.js  */
  document.addEventListener("DOMContentLoaded", function () {

    const ctx = document.querySelector('#jobChart');
    const data = {
      labels: [
        '진행',
        '요청',
        '피드백',
        '보류',
        '완료'
      ],
      datasets: [{
        label: '내 일감 상태',
        data: [${ jobCnt.aaCnt }, ${ jobCnt.bbCnt }, ${ jobCnt.ccCnt }, ${ jobCnt.ddCnt }, ${ jobCnt.eeCnt }],
        backgroundColor: [
          'rgb(231, 231, 255)',
          'rgb(232, 250, 223)',
          'rgb(255, 224, 219)',
          'rgb(255, 242, 214)',
          'rgb(215, 245, 252)'
        ],
        hoverOffset: 4
      }]
    };

    const jobChart = new Chart(ctx, {
      type: 'doughnut',
      data: data
    });
  })



  /* 참여자 AG-GRID */
  const gridOptions = {
    // define grid columns
    columnDefs: [
      { 
    	  field: 'empProfileImg', headerName: '사진'
  			, cellRenderer: function (row) {
  				if (row.data.empProfileImg != null) {
  					return "<img src='" + row.data.empProfileImg + "' style='height: 80%;'/>";
  				}else{
  					return "<img src='/resources/images/basic.png' style='height: 80%;'/>";
  				}
  			}  
      },
      { field: 'proLeader', headerName: '구분' },
      { field: 'deptName', headerName: '부서' },
      { field: 'empName', headerName: '이름' },
    ],
    rowHeight: 45,
    defaultColDef: {
      sortable: true,
      resizable: true,
      filter: true,
      width: 100,
    },
    pagination: true,
    paginationAutoPageSize: false,
    paginationPageSize: 4
  };

  document.addEventListener('DOMContentLoaded', function () {
    var gridDiv = document.querySelector("#pMemberList");
    new agGrid.Grid(gridDiv, gridOptions);


    const httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', '/job/${proSn}/pMemberList');
    httpRequest.send();

    httpRequest.onreadystatechange = function () {
      if (httpRequest.readyState === 4 && httpRequest.status === 200) {
        httpResult = JSON.parse(httpRequest.responseText);

        var json = [];
        for (var i = 0; i < httpResult.proM.length; i++) {
          var obj = new Object();
          obj.empProfileImg = httpResult.proM[i].emp.empProfileImg;
          obj.proLeader = (httpResult.proM[i].proLeader === 'N') ? '팀원' : '리더';
          obj.deptName = httpResult.proM[i].emp.dept.deptName;
          obj.empName = httpResult.proM[i].emp.empName;
          json.push(obj);
        }

        gridOptions.api.setRowData(json);
      }
    };
  });
  
  
  
  /* 내일감 AG-GRID */
  const gridOptionsMy = {
    // define grid columns
    columnDefs: [
      { 
    	  field: 'jobStcd', headerName: '상태'
  			, cellRenderer: function (row) {
  				if (row.data.jobStcd == "1") {
  					return "<span class='badge bg-label-primary me-1 fw-bold'>진행</span>";
  				}else if(row.data.jobStcd == "2"){
  					return "<span class='badge bg-label-success me-1 fw-bold'>요청</span>";
  				}else if(row.data.jobStcd == "3"){
  					return "<span class='badge bg-label-danger me-1 fw-bold'>피드백</span>";
  				}else if(row.data.jobStcd == "4"){
  					return "<span class='badge bg-label-warning me-1 fw-bold'>보류</span>";
  				}else if(row.data.jobStcd == "5"){
  					return "<span class='badge bg-label-info me-1 fw-bold'>완료</span>";
  				}else if(row.data.jobuSn == null){
  					return "<span class='badge bg-label-secondary me-1 fw-bold'>상위</span>";
  				}else{
  					return "";
  				}
  				
  			}  
      },
      { field: 'jobSn', headerName: '일감번호', hide: "true" },
      { field: 'proSn', headerName: '프로젝트번호', hide: "true" },
      { field: 'jobSj', headerName: '일감명' , width: 170  },
      { field: 'jobBdate', headerName: '시작일' ,width: 170},
      { field: 'jobEdate', headerName: '종료일' , width: 170},
      { field: 'jobPriort', headerName: '우선순위' 
    	  , cellRenderer: function (row) {
    		  if (row.data.jobPriort == "1") {
					return "<span class='text-danger fw-bold'>긴급</span>";
				}else if(row.data.jobPriort == "2"){
					return "<span class='text-warning fw-bold'>높음</span>";
				}else if(row.data.jobPriort == "3"){
					return "<span class='text-primary fw-bold'>중간</span>";
				}else if(row.data.jobPriort == "4"){
					return "<span class='text-success fw-bold'>낮음</span>";
				}
    	  }
      },
      {
      	field: 'jobProgrs', headerName: '진행도'	
      		, cellRenderer: function (row) {
      			return "<div class='progress mt-3'><div class='progress-bar progress-bar-striped progress-bar-animated bg-primary' role='progressbar' style='width:"+row.data.jobProgrs+"%' aria-valuenow='0' aria-valuemin='0' aria-valuemax='100'></div></div>";
      			}
      	
      },
      { field: 'jobCdate', headerName: '완료일' }
    ],
    rowHeight: 45,
    defaultColDef: {
      sortable: true,
      resizable: true,
      filter: true,
      width: 100,
    },
    pagination: true,
    paginationAutoPageSize: false,
    paginationPageSize: 6,
    onRowClicked: function (event) {
		console.log(event);
		location.href = "/job/"+event.data.proSn+"/"+event.data.jobSn+"/detail"
	}
  };

  document.addEventListener('DOMContentLoaded', function () {
    var gridDivMy = document.querySelector("#myjobList");
    new agGrid.Grid(gridDivMy, gridOptionsMy);
    
    let proSn = $('.divider-text').data('pro-sn');

    const httpRequestMy = new XMLHttpRequest();
    httpRequestMy.open('GET', '/job/'+proSn+'/myjob');
    httpRequestMy.send();

    httpRequestMy.onreadystatechange = function () {
      if (httpRequestMy.readyState === 4 && httpRequestMy.status === 200) {
        httpResultMy = JSON.parse(httpRequestMy.responseText);

        var json = [];
        for (var i = 0; i < httpResultMy.myjob.length; i++) {
          var obj = new Object();
          obj.jobSn = httpResultMy.myjob[i].jobSn;
          obj.proSn = httpResultMy.myjob[i].proSn;
          obj.jobSj = httpResultMy.myjob[i].jobSj;
          obj.jobSj = httpResultMy.myjob[i].jobSj;
          obj.jobBdate = httpResultMy.myjob[i].jobBdate;
          obj.jobEdate = httpResultMy.myjob[i].jobEdate;
          obj.jobPriort = httpResultMy.myjob[i].jobPriort;
          obj.jobStcd = httpResultMy.myjob[i].jobStcd;
          obj.jobProgrs = httpResultMy.myjob[i].jobProgrs;
          obj.jobCdate = httpResultMy.myjob[i].jobCdate;
          json.push(obj);
        }

        gridOptionsMy.api.setRowData(json);
      }
    };
  });
  
  
  
  /* 이슈 AG-GRID */
  const gridOptions3 = {
    // define grid columns
    columnDefs: [
      { field: 'issueNo', headerName: '이슈번호', hide: "true" },
      { field: 'proSn', headerName: '프로젝트번호', hide: "true" },
      { field: 'issueSj', headerName: '이슈명' },
      { field: 'writer', headerName: '작성자'  },
      { field: 'refJobName', headerName: '일감명'},
      { field: 'issueImp', headerName: '중요도'  },
    ],
    rowHeight: 30,
    defaultColDef: {
      sortable: true,
      resizable: true,
      filter: true,
      width: 93,
    },
    pagination: true,
    paginationAutoPageSize: false,
    paginationPageSize: 3,
    onRowClicked: function (event) {
		console.log(event);
		location.href = "/issue/"+event.data.proSn+"/detail/"+event.data.issueNo
	}
  };

  document.addEventListener('DOMContentLoaded', function () {
    var gridDiv3 = document.querySelector("#issueList");
    new agGrid.Grid(gridDiv3, gridOptions3);


    const httpRequestIssue = new XMLHttpRequest();
    httpRequestIssue.open('GET', '/job/${proSn}/issueList');
    httpRequestIssue.send();

    httpRequestIssue.onreadystatechange = function () {
      if (httpRequestIssue.readyState === 4 && httpRequestIssue.status === 200) {
        httpResultIssue = JSON.parse(httpRequestIssue.responseText);

        var json = [];
        for (var i = 0; i < httpResultIssue.issueList.length; i++) {
          var obj = new Object();
          obj.issueNo = httpResultIssue.issueList[i].issueNo;
          obj.proSn = httpResultIssue.issueList[i].proSn;
          obj.issueSj = httpResultIssue.issueList[i].issueSj;
          obj.writer = httpResultIssue.issueList[i].writer;
          obj.refJobName = httpResultIssue.issueList[i].refJobName;
          obj.issueImp = httpResultIssue.issueList[i].issueImp;
          json.push(obj);
        }
        gridOptions3.api.setRowData(json);
      }
    };
  });
  
  
  

</script>