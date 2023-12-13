<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ----------  	  ---------  -----------------
* 2023. 12. 6.      김보영        최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>   


<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <!-- Layout container -->
  <div class="layout-container">
    <!-- Content wrapper -->
    <div class="layout-page">
      <!-- Content -->
      <h4 class="py-3 mb-4"><a class="text-muted fw-light " href="/adminproject/${proSn}"><i class=' fw-bold mb-1 bx bx-undo'></i>프로젝트/</a> ${jobList[0].findName2 } </h4>
      <div class="row">
        <div class="col-sm-6 col-lg-3 mb-4">
          <div class="card card-border-shadow-primary h-100">
            <div class="card-body">
              <p class="mb-1">상위일감 수</p>
              <div class="d-flex align-items-center mb-2 pb-1">
                <div class="avatar me-2">
                  <span class="avatar-initial rounded bg-label-primary"><i class="bx bxs-truck"></i></span>
                </div>
                <h4 class="ms-1 mb-0"> <span id="drafting">${cnt.uppercount}</span> 건</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-lg-3 mb-4">
          <div class="card card-border-shadow-danger h-100">
            <div class="card-body">
              <p class="mb-1">하위일감 수</p>
              <div class="d-flex align-items-center mb-2 pb-1">
                <div class="avatar me-2">
                  <span class="avatar-initial rounded bg-label-danger"><i class="bx bx-git-repo-forked"></i></span>
                </div>
                <h4 class="ms-1 mb-0">${cnt.lowcount} 건</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-lg-3 mb-4">
          <div class="card card-border-shadow-warning h-100">
            <div class="card-body">
              <p class="mb-1">결함이슈 수</p>
              <div class="d-flex align-items-center mb-2 pb-1">
                <div class="avatar me-2">
                  <span class="avatar-initial rounded bg-label-warning"><i class="bx bx-error"></i></span>
                </div>
                <h4 class="ms-1 mb-0"> <span id="pending">${cnt.highissue}</span> 건</h4>
              </div>
            </div>
          </div>
        </div>
        <div class="col-sm-6 col-lg-3 mb-4">
          <div class="card card-border-shadow-info h-100">
            <div class="card-body">
              <p class="mb-1">일반이슈 수</p>
              <div class="d-flex align-items-center mb-2 pb-1">
                <div class="avatar me-2">
                  <span class="avatar-initial rounded bg-label-info"><i class="bx bx-time-five"></i></span>
                </div>
                <h4 class="ms-1 mb-0"> <span id="receive">${cnt.nomalissue}</span> 건</h4>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-xl-12 mb-4 order-3">
        <div class="card">
          <div class="card-header d-flex align-items-center justify-content-between">
            <h5 class="card-title m-0 me-2">일감목록</h5>
          </div>
          <div class="card-body">
            <!-- 일감리스트 -->
            <div id="jobList" class="ag-theme-alpine" style="height:500px"></div>

            <!--일감리스트-->
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



<script>var __basePath = './';</script>
<script src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>

<script>

/* 프로젝트 일감목록 AG-GRID */
const gridOptionsMy = {
  // define grid columns
  columnDefs: [
    { field: 'proSn', headerName: '프로젝트번호' , hide: "true"  },
    { field: 'jobSn', headerName: '일감번호'},
    { field: 'jobSj', headerName: '일감명' },
    { field: 'jobRdate', headerName: '작성일'},
    { field: 'writer', headerName: '작성자'},
    { field: 'jobBdate', headerName: '시작일'},
    { field: 'jobEdate', headerName: '마감일'},
    { field: 'jobCdate', headerName: '완료일'},
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
				}else{
					return "<span class='badge bg-label-secondary me-1 fw-bold'>상위</span>";
				}
				
			}  
    },
    {
    	field: 'jobProgrs', headerName: '진행도'	
    		, cellRenderer: function (row) {
    			return "<div class='progress mt-3'><div class='progress-bar progress-bar-striped progress-bar-animated bg-primary' role='progressbar' style='width:"+row.data.jobProgrs+"%' aria-valuenow='0' aria-valuemin='0' aria-valuemax='100'></div></div>";
    			}
    	
    }
  ],
  rowHeight: 42,
  defaultColDef: {
    sortable: true,
    resizable: true,
    filter: true,
    width: 110,
  },
  pagination: true,
  paginationAutoPageSize: false,
  paginationPageSize: 8,
  onRowClicked: function (event) {
		console.log(event);
		location.href = "/job/"+event.data.proSn+"/"+event.data.jobSn+"/detail"
	}
};

document.addEventListener('DOMContentLoaded', function () {
  var gridDivMy = document.querySelector("#jobList");
  new agGrid.Grid(gridDivMy, gridOptionsMy);
  

  const httpRequestMy = new XMLHttpRequest();
  httpRequestMy.open('GET', '/adminprojectDetail/${proSn}/jobList');
  httpRequestMy.send();

  httpRequestMy.onreadystatechange = function () {
    if (httpRequestMy.readyState === 4 && httpRequestMy.status === 200) {
      httpResultMy = JSON.parse(httpRequestMy.responseText);

      var json = [];
      for (var i = 0; i < httpResultMy.job.length; i++) {
        var obj = new Object();
        obj.proSn = httpResultMy.job[i].proSn;
        obj.jobSn = httpResultMy.job[i].jobSn;
        obj.jobSj = httpResultMy.job[i].jobSj;
        obj.writer = httpResultMy.job[i].findName;
        obj.jobEdate = httpResultMy.job[i].jobEdate;
        obj.jobRdate = httpResultMy.job[i].jobRdate;
        obj.jobBdate = httpResultMy.job[i].jobBdate;
        obj.jobCdate = httpResultMy.job[i].jobCdate;
        obj.jobPriort = httpResultMy.job[i].jobPriort;
        obj.jobStcd = httpResultMy.job[i].jobStcd;
        obj.jobProgrs = httpResultMy.job[i].jobProgrs;
        json.push(obj);
      }

      gridOptionsMy.api.setRowData(json);
    }
  };
});


</script>