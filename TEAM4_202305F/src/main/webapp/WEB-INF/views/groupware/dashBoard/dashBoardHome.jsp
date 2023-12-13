<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/memo/memo.css">
  <script src="/resources/js/app/qrcode/jsQR.js"></script> 
  <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
	2023-11-16	권도윤	근태 수정
	2023-11-22	김보영	내일감연결
	2023-11-28	송석원 	qr코드 인식및데이터 전송 
	2023-12-06	전수진 	내기안문서, 필독게시판 등록
* Copyright (c) ${year} by DDIT All right reserved
 --%>
 <style>
 
 #calendarArea #Wrapper #calendar {
        font-size: 8x; /* 여기에 원하는 글씨 크기를 지정하세요 */
        height : 270px ! important;
        margin-top: -10px !important;
}
 
 .modal-content {
    width: auto;
}
  
/*   #loadingMessage, */
/*   #canvas, */
/*   #output { */
/*     display: none; */
/*   } */
   
/*     .qrbody {  */
/*       font-family: 'Ropa Sans', sans-serif; */
/*       color: #333; */
/*       max-width: 40px; */
/*       margin: 0 auto; */
/*       position: relative; */
/*     } */

    #githubLink {
      position: absolute;
      right: 0;
      top: 12px;
      color: #2D99FF;
    }

    h1 {
      margin: 10px 0;
      font-size: 40px;
    }

    #loadingMessage {
      text-align: center;
      background-color: #eee;
    }
 
    #canvas {
      width: 340px;
    }

    #output {
      margin-top: 20px;
      background: #eee;
      padding: 10px;
      padding-bottom: 0;
    }

    #output div {
      padding-bottom: 10px;
      word-wrap: break-word;
    }

    #noQRFound {
      text-align: center;
    }
    
    
    .background-image {
            width: 100vw;
            height: 100vh;
            object-fit: cover; /* 이미지가 찌그러지지 않도록 설정 */
            position: fixed; /* 이미지를 고정시킴 */
            top: 0;
            left: 0;
            z-index: -1; /* 다른 콘텐츠 위에 나타나지 않도록 함 */
        }

        /* 나머지 콘텐츠의 스타일링 */
        .content {
            padding: 20px;
            color: white;
            text-align: center;
            margin-top: 50vh; /* 화면 세로 중앙 정렬을 위한 설정 */
        }
    .memo{
   	    width: 70%;
    	height: 145px;
    }
    
    
    #drafterTrBody td, #noticeTbody td {
	    font-size: 12px; 
	}
	.width10 {
		width: 10%;
	}
	.width20 {
		width: 20%;
	}
	.width30 {
		width: 30%;
	}
  </style>
  
<sec:csrfInput/> 
<sec:authorize access="isAuthenticated()">
  <sec:authentication property="principal" var="realUser" />
</sec:authorize>

<img src="/resources/assets/img/illustrations/pricing-illustration-dark.png" alt="Background Image" class="background-image" >

<nav class="navbar navbar-expand-lg  mb-2">
  <div class="container-fluid">
    <a class="navbar-brand fw-semibold form-label-lg fs-4">${emp.empName} 님 안녕하세요 <i
        class='fw-semibold fs-4 bx bx-happy-beaming bx-tada mb-1'></i></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-ex-6">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar-ex-6">
      <ul class="navbar-nav ms-lg-auto fs-5 fw-semibold">
        <li class="nav-item">
          <div id="weather"></div>
        </li>
        <li class="nav-item">
          <div id="ondo"></div>
        </li>
        <li class="nav-item">
          <p id="pnIcon" style="margin-top: -12px;"></p>
        </li>
      </ul>
    </div>
  </div>
</nav>


<!-- Layout wrapper -->

<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
      <div>
        <!-- Content -->
        <div class="row">
          <!-- Customer-detail Sidebar -->
          <div class="col-xl-4 col-lg-5 col-md-5 order-1 order-md-0">
            <!-- Customer-detail Card -->
            <div class="card mb-3">
              <div class="card-body">
                <div class="customer-avatar-section">
                  <div class="d-flex align-items-center flex-column">
                    <c:if test="${not empty emp.empProfileImg}">
                      <img class="img-fluid rounded my-3" src="${emp.empProfileImg }" height="110" width="110"
                        alt="User avatar">
                    </c:if>
                    <c:if test="${empty emp.empProfileImg}">
                      <img class="img-fluid rounded my-3" src="/resources/images/basic.png" height="110" width="110"
                        alt="User avatar">
                    </c:if>
                    <div class="customer-info text-center">
                      <h4 class="mb-1">${emp.empName}</h4>
                      <small>사원 ID ${realUser.username}</small>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- /Customer-detail Card -->

            <!-- 출근버튼 -->
            <div class="card mb-3 bg-gradient-primary">
              <div class="card-body">
                <div class="row g-3 ">
                  <div class="col-5 col-xxl-5 col-xl-12">
                    <button class="btn btn-white text-primary w-100 fw-medium shadow-sm" data-bs-target="#modalCenter"
                      data-bs-toggle="modal" onclick="commute()" id="commute">
                      출근
                    </button>
                  </div>
                  <div class="col-7 col-xxl-7 col-xl-12" style="text-align: center;">
                    <img src="/resources/assets/img/illustrations/man-with-laptop-dark.png" alt=""
                      style="height: 120px;">
                  </div>
                </div>
              </div>
            </div>
            <!-- /출근버튼 -->
            <!-- 메모와 연차자 -->
            <div class="card mb-3 ">
              <div class="card-body" style="height:300px">
                <div id="calendarArea" id="calendarArea">
                  <div class=" mt-n7">
						<div id="Wrapper" >
							<div id='calendar'></div>
				 		</div>
					</div>
					<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/app/calendar/calendar.js"></script>
					<script>
						let tag = document.querySelector(".fc-header-toolbar")
						tag.style.display="none";
						
						let Wrapper = document.getElementById("Wrapper")
						
						let link = document.querySelectorAll(".fc-daygrid-week-number");
						for(let i = 0; i<link.length; i++){
							link[i].style.display="none";
						}
						
					</script>
                </div>
              </div>
            </div>
            <div class="card mb-3">
              <div class="card-body" style="height:240px">
                <h5 class="card-title mb-3"><i class=' menu-icon tf-icons bx bx-group mb-1'></i>오늘 연차자</h5>
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
            <!-- 메모와 연차자 -->
          </div>
          <!--  Content -->
          <div class="col-xl-8 col-lg-7 col-md-7 order-0 order-md-1">
            <div class="row text-nowrap">

              <!-- 공지 -->
              <div class="col-md-12 mb-12 mb-3">
                <div class="card h-100 ">
                  <div class="card-body ">
                    <div class="card-info" style="height:188px">
                      <h5 class="card-title mb-3"><i class="menu-icon tf-icons bx bx-clipboard mb-1"></i>공지사항</h5>
                      <div>
						<div class="card-datatable table-responsive">
							<table class="table">
								<thead class="table-light">
									<tr>
										<th>NO</th>
										<th>제목</th>
										<th>작성자</th>
										<th>작성일</th>
										<th>조회수</th>
									</tr>
								</thead>
								<tbody class="table-border-bottom-0" id="noticeTbody">
									<c:if test="${empty notice }">
										<tr>
											<td colspan="5">검색 조건에 맞는 게시글 없음 </td>
										</tr>
									</c:if>
									<c:if test="${not empty notice }">
										<c:forEach	items="${notice }" var="notice" varStatus="loopStatus">
											<tr>
												<td>${notice.bbsNo }</td>				
												<td>
													<a href="<c:url value='/notice/${notice.bbsNo}'/>">
										                <c:if test="${loopStatus.index < 3}">
										                	<c:if test="${notice.noiceMustRead eq 'Y' }">
											                    <span class="badge bg-warning">필독</span>
										                	</c:if>
										                </c:if>
										                ${notice.bbsSj}
										            </a>
												</td>				
												<td>${notice.empCd }</td>
												<td>${notice.bbsRdate }</td>				
												<td>${notice.bbsRdcnt }</td>				
											</tr>
										</c:forEach>
									</c:if>
								</tbody>
								<tfoot>
									<tr>
										<td colspan="5">
										</td>
									</tr>
								</tfoot>
							</table>
						</div> 
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /공지 -->

              <!-- 내결재 -->
              <div class="col-md-12 mb-12 mb-3">
                <div class="card h-100 ">
                  <div class="card-body ">
                    <div class="card-info" style="height:260px">
                      <h5 class="card-title mb-3"><i class="menu-icon tf-icons bx bx-detail mb-1"></i>내 결재</h5>
                      <div>
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
                </div>
              </div>
              <!-- /내결재 -->
              <!-- 일정과 예약내역 -->
              <div class="col-md-6 mb-3">
                <div class="card" style="height:180px">
                  <div class="card-body">
                    <h5 class="card-title mb-3"><i class="menu-icon tf-icons bx bx-edit mb-1"></i>메모</h5>
                    <div id="memO">
                      이부분에 메모
                      <script>
                        $.ajax({
                          type: "GET",
                          url: "/memo/list",
                          dataType: "json",
                          success: function (memoList) {
                            console.log(memoList)
                            // 컨테이너를 찾거나 만듭니다. 여기서는 body를 기준으로 했습니다.
                            let container = document.getElementById("memO");                   
                            let html = "";
                            if (memoList != null) {                  
                              // memoList를 순회하며 각 메모에 대한 <div>를 생성합니다.
                              for(let i=0; i<memoList.length; i++){
                            	  if(memoList[i].memoBkmkYn == 'Y'){
                            		  html = '<div> <h3 class="txtMemo text-truncate">' + memoList[i].memoCn + '</h3> </div>';
                            	  }else{
                            		  html = '<div> <h3 class="txtMemo text-truncate">' + memoList[0].memoCn + '</h3> </div>';
                            	  }
                              }
                              container.innerHTML = html;
                              
 
                            } else {
                              container.innerHTML = "메모가 없습니다";
                            }
                          },
                          error: function (xhr) {
                            alert("2")
                          }
                        });

                      </script>
                    </div>


                  </div>
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <div class="card">
                  <div class="card-body">
                    <h5 class="card-title mb-3"><i class="menu-icon tf-icons bx bxs-user-detail mb-1"></i>근태현황</h5>
                    <div>
                      <div class="progress mb-2">
                        <div class="progress-bar progress-bar-striped progress-bar-animated bg-primary"
                          role="progressbar" style="width: 0%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"
                          id="progressbar">
                        </div>
                      </div>
                      <div class="text-muted d-block mb-3" style="text-align: center;">
                        <small style="margin: 0 7%; font-size: 15px;">월</small>'
                        <small style="margin: 0 7%; font-size: 15px;">화</small>'
                        <small style="margin: 0 7%; font-size: 15px;">수</small>'
                        <small style="margin: 0 7%; font-size: 15px;">목</small>'
                        <small style="margin: 0 7%; font-size: 15px;">금</small>
                      </div>
                      <hr>
                      <div class="text-muted d-block mb-n4 fw-normal " id="annualGauge" style="text-align: center;">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /일정과 예약내역 -->

              <!-- 일감 -->
              <div class="col-md-12 mb-12 mb-3">
                <div class="card h-100 ">
                  <div class="card-body ">
                    <div class="card-info">
                      <h5 class="card-title mb-3"><i class="menu-icon tf-icons bx bx-archive mb-1"></i>내 일감</h5>
                      <div class=" ag-theme-alpine" id="myjobList" style="height:240px">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- /일감 -->
            </div>
            <!-- / customer cards -->
          </div>
          <!--/ Customer Content -->
        </div>
        <!-- /Content -->
      </div>
      <!-- /Content wrapper -->
    </div>
    <!-- /Layout container -->
  </div>
  <!-- /Layout wrapper -->
</div>



<div class="col-lg-4 col-md-6">
  <div class="mt-3">
    <!-- Button trigger modal -->
    <!-- 모달 -->
    <div class="modal fade" id="modalCenter" tabindex="-1" style="display: none;" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <!-- Modal header -->
          <div class="modal-header">
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>  
          <!-- Modal Body -->
          <div class="modal-body">
            <div id="loadingMessage" class="qrbody card mb-4">🎥 비디오 스트림에 액세스할 수 없습니다(웹캠이 활성화되어 있는지 확인하십시오).</div>
            <canvas id="canvas" hidden></canvas>
            <div id="output" hidden>
              <div id="outputMessage">사원의 QR코드를 찍어주세요</div>
              <div hidden><b>Data:</b> <span id="outputData"></span></div>
            </div> 
          </div>
        </div>
      </div>
    </div>
  </div>
</div> 



<script src="/resources/js/app/weather/weather.js"></script>
<script src="/resources/js/app/attebdabce/attendance.js"></script>
<script>var __basePath = './';</script>
<script src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>


<script>

/* 내일감 AG-GRID */
const gridOptionsMy = {
  // define grid columns
  columnDefs: [
    { field: 'jobSn', headerName: '일감번호', hide: "true"},
    { field: 'proSj', headerName: '프로젝트명' , width: 170},
    { field: 'proSn', headerName: '프로젝트번호', hide: "true"},
    { field: 'jobSj', headerName: '일감명' , width: 170 },
    { field: 'jobEdate', headerName: '마감일'},
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
  paginationPageSize: 3,
  onRowClicked: function (event) {
		console.log(event);
		location.href = "/job/"+event.data.proSn+"/"+event.data.jobSn+"/detail"
	}
};

document.addEventListener('DOMContentLoaded', function () {
  var gridDivMy = document.querySelector("#myjobList");
  new agGrid.Grid(gridDivMy, gridOptionsMy);
  

  const httpRequestMy = new XMLHttpRequest();
  httpRequestMy.open('GET', '/dashBoard/myjob');
  httpRequestMy.send();

  httpRequestMy.onreadystatechange = function () {
    if (httpRequestMy.readyState === 4 && httpRequestMy.status === 200) {
      httpResultMy = JSON.parse(httpRequestMy.responseText);

      var json = [];
      for (var i = 0; i < httpResultMy.myjob.length; i++) {
        var obj = new Object();
        obj.jobSn = httpResultMy.myjob[i].jobSn;
        obj.proSj = httpResultMy.myjob[i].findName2;
        obj.proSn = httpResultMy.myjob[i].proSn;
        obj.jobSj = httpResultMy.myjob[i].jobSj;
        obj.jobSj = httpResultMy.myjob[i].jobSj;
        obj.jobEdate = httpResultMy.myjob[i].jobEdate;
        obj.jobPriort = httpResultMy.myjob[i].jobPriort;
        obj.jobStcd = httpResultMy.myjob[i].jobStcd;
        obj.jobProgrs = httpResultMy.myjob[i].jobProgrs;
        json.push(obj);
      }

      gridOptionsMy.api.setRowData(json);
    }
  };
});
 




// qr코드 시작 
  function commute() { 
 
	

var video = document.createElement("video");
var canvasElement = document.getElementById("canvas");
var canvas = canvasElement.getContext("2d");
var loadingMessage = document.getElementById("loadingMessage");
var outputContainer = document.getElementById("output");
var outputMessage = document.getElementById("outputMessage");
var outputData = document.getElementById("outputData");

// QR 코드로부터 읽은 데이터를 저장할 변수
var qrCodeData = ""; // 데이터를 저장할 변수 선언

//qrCodeData 값 업데이트
function updateQRCodeData(data) {
  qrCodeData = data;
}

  function drawLine(begin, end, color) {
    canvas.beginPath();
    canvas.moveTo(begin.x, begin.y);
    canvas.lineTo(end.x, end.y);
    canvas.lineWidth = 4;
    canvas.strokeStyle = color;
    canvas.stroke();
    
 
  }
 
  

  // Use facingMode: environment to attemt to get the front camera on phones
  navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } }).then(function(stream) {
    video.srcObject = stream;
    video.setAttribute("playsinline", true); // required to tell iOS safari we don't want fullscreen
    video.play();
    requestAnimationFrame(tick);
//     qrCodeData = code ? code.data : ""; // 코드에서 데이터를 가져와 변수에 저장 
  
  
  
  });
  function getCurrentTime() {
	  const now = new Date();
	  const hours = String(now.getHours()).padStart(2, '0'); // 시간
	  const minutes = String(now.getMinutes()).padStart(2, '0'); // 분
	  const seconds = String(now.getSeconds()).padStart(2, '0'); // 초
	  
	  const currentTime = hours + ':' + minutes + ':' + seconds;
	  return currentTime;
	}
  const currentTime = getCurrentTime();

  function tick() {
    loadingMessage.innerText = "" 
    if (video.readyState === video.HAVE_ENOUGH_DATA) {
      loadingMessage.hidden = true;
      canvasElement.hidden = false;
      outputContainer.hidden = false;

      canvasElement.height = video.videoHeight;
      canvasElement.width = video.videoWidth;
      canvas.drawImage(video, 0, 0, canvasElement.width, canvasElement.height);
      var imageData = canvas.getImageData(0, 0, canvasElement.width, canvasElement.height);
      var code = jsQR(imageData.data, imageData.width, imageData.height, {
        inversionAttempts: "dontInvert",
      });
      if (code) {
        drawLine(code.location.topLeftCorner, code.location.topRightCorner, "#FF3B58");
        drawLine(code.location.topRightCorner, code.location.bottomRightCorner, "#FF3B58");
        drawLine(code.location.bottomRightCorner, code.location.bottomLeftCorner, "#FF3B58");
        drawLine(code.location.bottomLeftCorner, code.location.topLeftCorner, "#FF3B58");
         
        qrCodeData = code.data; // 코드에서 데이터를 가져와 변수에 저장 
        console.log(qrCodeData); // 데이터를 콘솔에 출력 
        
       
        
        let empCd = qrCodeData; 
           
        let qrsend = {
      		  "empCd":qrCodeData 
        }  
         
      		 console.log("큐알 데이터 확인:",	qrsend);
        Swal.fire({
        	  title: "현재 시간은 " + currentTime + " 입니다.",
        	  showCancelButton: true,
        	  confirmButtonText: '확인',
        	  cancelButtonText: '취소'
        	}).then((result) => {
        	  if (result.isConfirmed) {
        	    console.log("큐알 데이터 확인:", qrsend);
        	    $.ajax({
        	      type: 'POST',
        	      url: '/attendance/commute',
        	      contentType: "application/json;charset=utf-8",
        	      data: JSON.stringify(qrsend), // empCd로 데이터 전송
        	      dataType: 'text',
        	      success: function (response) {
        	        console.log('QR 코드 데이터를 성공적으로 전송했습니다.');
        	        $('#modalCenter').modal('hide');
        	        // myPopup.close();
        	        location.reload();
        	      },
        	      error: function (xhr, status, error) {
        	        // 요청 실패 시의 동작
        	        console.error('데이터 전송에 실패했습니다.');
        	        Swal.fire({
	 			          icon: 'warning',
	 			          title: '실패했습니다 다시 시도해 보세요',
	 			        }); 
        	         
//         	        alert("실패했습니다 다시 시도해 보세요"); 
        	      }
        	    }); //end ajax
        	  } else {
        	    outputMessage.hidden = false;
        	    outputData.parentElement.hidden = true;
        	  }
        	});

         
       
      } else {
        outputMessage.hidden = false;
        outputData.parentElement.hidden = true;
      }
    }
    requestAnimationFrame(tick);
    
    
  }
//   qr코드
  disableCamera();    
} 
$('#modalCenter').on('hidden.bs.modal', function () {
    disableCamera();
}); 
//카메라 비활성화 함수
  function disableCamera() {
    var video = document.querySelector("video");

    if (video && video.srcObject) {
        var stream = video.srcObject;
        var tracks = stream.getTracks();

        tracks.forEach(function(track) {
            track.stop();
        });

        video.srcObject = null;
    }
}

	let cPath = document.body.dataset.contextPath;
	let dUrl = cPath+'/sanction/drafterData';
	
	
	$.getJSON(dUrl)
		.done(function(resp){
			let dataList = resp.paging.dataList.slice(0, 5);
			let total = resp.paging.totalRecord;
			console.log("resp.paging",resp.paging);
			console.log("dataList",dataList);
			console.log("dataList.length",dataList.length);
			let trTag = "";
			if(dataList.length > 0 ){
				$.each(dataList, function(idx, list){
					trTag += "<tr><td>"+list.rnum+"</td><td>"+list.sanctionForm.formNm+"</td>";
					trTag += `<td><a href="\${cPath}/sanction/\${list.sanctnNo}">\${list.sanctnSj}</td>`;
					trTag += "<td>"+list.drafterNm+"</td><td>"+list.drafterDeptName+"</td><td>"+list.sanctnDate+"</td>";
						switch(list.sanctnSttusNm) {
						case '결재대기' :
							trTag += "<td><span class='badge rounded-pill bg-label-secondary'>"+list.sanctnSttusNm+"</span></td></tr>";
							break;
						case '결재진행' :
							trTag += "<td><span class='badge rounded-pill bg-label-info'>"+list.sanctnSttusNm+"</span></td></tr>";
							break;
						case '결재완료' :
							trTag += "<td><span class='badge rounded-pill bg-label-primary'>"+list.sanctnSttusNm+"</span></td></tr>";
							break;
						case '결재반려' :
							trTag += "<td><span class='badge rounded-pill bg-label-danger'>"+list.sanctnSttusNm+"</span></td></tr>";
							break;
						}
					
					console.log("list : "+JSON.stringify(list));
				});
			} else {
				trTag += `
					<tr>
						<td colspan = '7'> 기안된 문서 없음. </td>
					</tr>
				`;
			}
			$("#drafterTrBody").html(trTag);
			
			if(total>0) {
				$("#drafting").text(total);
			} else {
				$("#drafting").text('0');
			}
		}); 


</script>  
