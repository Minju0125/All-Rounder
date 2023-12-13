<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2023. 11. 30.      김보영        최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>  

<script>
	const issueData=[];
</script>

<c:forEach items="${chart}" var="cnt">  
	<script>
		var oneData={
				sttus: "${cnt.issueSttus}", a:${cnt.a},b:${cnt.b},c:${cnt.c},d:-${cnt.d},e:-${cnt.e},f:-${cnt.f}
		}
		issueData.push(oneData);
		console.log("check",issueData);
	</script>
</c:forEach> 

<!-- 이슈 현황 -->


<h1 class="mb-0">${proInfo.proNm}</h1>
<h5 class="mb-0">${proInfo.proBdate} ~ ${proInfo.proEdate }</h5><br>
<!-- <img src="/resources/css/bg/bg.jpg" alt="Background Image" class="background-image" > -->
<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <!-- Layout container -->
  <div class="layout-container">
    <!-- Content wrapper -->
    <div class="layout-page">
      <!-- Content -->
      <div>
        <!-- navbar -->
        <div class="col-md-7 col-lg-7">
          <ul class="nav nav-pills mb-3 nav-fill" role="tablist">
            <li class="nav-item" role="presentation">
              <a class="nav-link dropdown-toggle" href="javascript:void(0);" data-bs-toggle="dropdown"
                aria-expanded="false" data-trigger="hover">
                <i class='bx bxs-copy-alt'></i>
                　프로젝트</a>
              <div class="dropdown-menu">
                <c:forEach items="${proj}" var="proj">
                  <c:if test="${proj.proSttus == 1}">
                    <a class="dropdown-item" href="/issue/${proj.proSn}/home">${proj.proNm}</a>
                  </c:if>
                </c:forEach>
              </div>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link " href="/job/${proSn}/home">
                <i class='bx bx-edit'></i>
                　일감</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link active" href="/issue/${detail.proSn}/home">
                <i class='bx bx-calendar-exclamation'></i>
                　이슈</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link" href="/pms/gantt/${proSn}">
                <i class='bx bx-chart'></i>
                　간트차트</a>
            </li>
            <li class="nav-item">
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

        <!-- 이슈디테일 -->

        <div class="row">
          <div class="col-xl-7 col-lg-7 col-md-7">
            <!-- 이슈디테일 -->
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-action-title mb-0"><i class="bx bx-list-ul me-2"></i>이슈 상세</h5>
                <ul class="list-unstyled mb-4 mt-3">
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-user"></i><span class="fw-medium mx-2">이슈구분 : </span>
                    <span>
                      <c:if test="${detail.issueSe eq '1'}">일반이슈</c:if>
                    </span>
                    <span>
                      <c:if test="${detail.issueSe eq '2'}">결함이슈 <i class='bx bx-message-rounded-error bx-tada'
                          style='color:#c62020'></i></c:if>
                    </span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-check"></i><span class="fw-medium mx-2">이슈중요도 : </span>
                    <span class='text-danger'>
                      <c:if test="${detail.issueImp eq '1'}">긴급 <i class='bx bx-dizzy mb-1'></i></c:if>
                    </span>
                    <span class='text-primary'>
                      <c:if test="${detail.issueImp eq '2'}">중간 <i class='bx bx-smile mb-1'></i></c:if>
                    </span>
                    <span class='text-success'>
                      <c:if test="${detail.issueImp eq '3'}">낮음 <i class='bx bx-laugh mb-1'></i></c:if>
                    </span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-star"></i><span class="fw-medium mx-2">이슈상태 : </span>
                    <span>
                      <c:if test="${detail.issueSttus eq '1'}">진행 </c:if>
                    </span>
                    <span>
                      <c:if test="${detail.issueSttus eq '2'}">보류 </c:if>
                    </span>
                    <span>
                      <c:if test="${detail.issueSttus eq '3'}">완료 </c:if>
                    </span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-flag"></i><span class="fw-medium mx-2">이슈기한 : </span>
                    <span>
                      <c:if test="${not empty detail.issueSttus}">${detail.issueSttus}</c:if>
                    </span>
                    <span>
                      <c:if test="${empty detail.issueSttus}">미정</c:if>
                    </span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-detail"></i><span class="fw-medium mx-2">이슈작성자 : </span>
                    <span>${detail.writer}</span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-detail"></i><span class="fw-medium mx-2">이슈작성일 : </span>
                    <span>${detail.issueRdate}</span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-detail"></i><span class="fw-medium mx-2">이슈제목 : </span>
                    <span>${detail.issueSj}</span>
                  </li>
                </ul>
              </div>
            </div>
            <!--/ 이슈디테일 -->
            <div class="card mb-4">
              <div class="card-body">
                <h5 class="card-action-title mb-0"><i class="bx bx-list-ul me-2"></i>이슈내용</h5>
                <p class="mt-3 ">${detail.issueCn}</p>
              </div>
            </div>
            <!-- 참조일감 -->
            <c:if test="${not empty detail.jobSn}">
              <div class="card mb-4">
                <div class="card-body">
                  <h5 class="card-action-title mb-0"><i class="bx bx-list-ul me-2"></i>참조 일감</h5>
                  <ul class="list-unstyled mt-3 mb-0">
                    <li class="d-flex align-items-center mb-3">
                      <i class="bx bx-check"></i><span class="fw-medium mx-2">제목 : </span>
                      <a href="/job/${detail.proSn }/${detail.jobSn }/detail"><span>${detail.refJobName }</span></a>
                    </li>
                  </ul>
                </div>
              </div>
            </c:if>
            <!--/ 참조일감 -->
          </div>
          <div class="col-xl-5 col-lg-5 col-md-5">
            <!-- 차트 -->
            <div class="card card-action mb-4">
              <div class="card-body" style="height: 490px;">
<%--                 <canvas id="issueChart" style="height: 390px;"></canvas> --%>
				<div id="myChart" style="height: 95%"></div>
		        <script>
		            (function () {const appLocation = '';window.__basePath = appLocation;})();
		            function getData() {
		                return issueData;
		            }
		        </script>
              </div>
            </div>
            <!--/ 차트 -->
            <input type="hidden" value="${detail.proSn }" id="proSn" />
            <button class="btn btn-info" type="button" data-bs-toggle="modal"
              data-bs-target="#backDropModal">수정</button>
            <button type="button" class="btn btn-warning me-1" onclick="fn_issueDelete('${detail.issueNo}')">삭제</button>
            <button type="button" class="btn btn-label-secondary me-1"
              onclick="fn_issueHome('${detail.proSn}')">목록</button>
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


<!-- 수정 모달 -->
<!-- Modal -->
<div class="modal fade " id="backDropModal" data-bs-backdrop="static" tabindex="-1" style="display: none;"
  aria-modal="true">
  <div class="modal-dialog">
    <form class="modal-content fv-plugins-bootstrap5 fv-plugins-framework " id="issueForm">
      <security:csrfInput />
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle">이슈수정</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <div class="row g-2" style="text-align: center;">
          <div class=" mb-3 col-md-8 col-lg-8">
            <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
              <button type="button" class="btn-issueSe GenIssue btn btn-outline-secondary
              	<c:if test="${detail.issueSe eq '1'}">active</c:if>" 
             	 onclick="fn_issueSe('1')"
                style="border-bottom-left-radius: 0.375rem;border-top-left-radius: 0.375rem;">
                <i class="tf-icons bx bx-car">일반이슈</i>
              </button>
              <button type="button" class="btn-issueSe bugIssue btn btn-outline-secondary
              <c:if test="${detail.issueSe eq '2'}">active</c:if>" 
              onclick="fn_issueSe('2')">
                <i class="tf-icons bx bx-rocket">결함이슈</i>
              </button>
            </div>
            <div class="input-group has-validation">
              <input type="hidden" id="issueSe" name="issueSe" value="${detail.issueSe }" />
            </div>
          </div>
          <div class=" mb-3 col-md-3 col-lg-3">
            <div class="input-group has-validation">
              <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
                <div class="btn-group" role="group">
                  <select name="issueImp" id="issueImp" class="form-select">
                    <option value="1" <c:if test="${detail.issueImp eq '1'}">selected</c:if> >긴급</option>
                    <option value="2" <c:if test="${detail.issueImp eq '2'}">selected</c:if>>중간</option>
                    <option value="3" <c:if test="${detail.issueImp eq '3'}">selected</c:if>>낮음</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-12 mb-3">
          <div class="row">
            <div class="col-md mb-md-0 mb-2">
              <div class="form-check custom-option custom-option-icon">
                <label class="form-check-label custom-option-content" for="customRadioBuilder">
                  <span class="custom-option-body">
                    <i class="bx bx-building-house"></i>
                    <span class="custom-option-title">진행</span>
                  </span>
                  <input name="issueSttus" class="form-check-input" type="radio" value="1" id="issueSttus" <c:if
                    test="${detail.issueSttus eq '1' }">checked</c:if>>
                </label>
              </div>
            </div>
            <div class="col-md mb-md-0 mb-2">
              <div class="form-check custom-option custom-option-icon checked">
                <label class="form-check-label custom-option-content" for="customRadioOwner">
                  <span class="custom-option-body">
                    <i class="bx bx-crown"></i>
                    <span class="custom-option-title">보류</span>
                  </span>
                  <input name="issueSttus" class="form-check-input" type="radio" value="2" id="issueSttus" <c:if
                    test="${detail.issueSttus eq '2' }">checked</c:if>>
                </label>
              </div>
            </div>
            <div class="col-md mb-md-0 mb-2">
              <div class="form-check custom-option custom-option-icon">
                <label class="form-check-label custom-option-content" for="customRadioBroker">
                  <span class="custom-option-body">
                    <i class="bx bx-briefcase-alt"></i>
                    <span class="custom-option-title">완료</span>
                  </span>
                  <input name="issueSttus" class="form-check-input" type="radio" value="3" id="issueSttus" <c:if
                    test="${detail.issueSttus eq '3' }">checked</c:if>>
                </label>
              </div>
            </div>
          </div>
        </div>


        <div class="row">
          <div class="col mb-3">
            <label for="issueSj" class="fw-medium">이슈명</label>
            <div class="input-group has-validation">
              <input type="text" id="issueSj" name="issueSj" value="${detail.issueSj }" class="form-control">
            </div>

          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium">이슈기한</label>
            <input type="date" id="issueEdate" name="issueEdate" class="form-control" value="${detail.issueEdate}">
          </div>
        </div>
        <div class="row" id="issueJobSnDiv">
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium">일감참조</label>
            <select name="jobSn" id="jobSn" class="form-select" tabindex="0">
              <option value="">(선택)</option>
              <c:forEach items="${jobList}" var="uJob">
                <option value="${uJob.jobSn}" id="insertTempUJob" <c:if test="${detail.jobSn eq uJob.jobSn }">selected
                  </c:if> >${uJob.jobSj}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="issueCn" class="fw-medium">내용</label>
            <div class="input-group has-validation">
              <textarea name="issueCn" id="issueCn" class="form-control" rows="5" cols="10">${detail.issueCn}</textarea>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">
          취소
        </button>
        <button type="button" class="btn btn-primary" onclick="fn_issueUpdate('${detail.issueNo}')">수정</button>
      </div>
    </form>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/ag-charts-enterprise@9.0.0/dist/umd/ag-charts-enterprise.js?t=1701269939106"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/issue/issue.js"></script>
<!-- <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> -->
<script>

//일반이슈 버튼 클릭시 일감명참조 안보이기
$('.GenIssue').on("click", function(){
  if($(this).val() ==""){
    $('#issueJobSnDiv').hide();

  }else {
    $('#issueJobSnDiv').show();
  } 
});

$('.bugIssue').on("click", function(){
  if($(this).val() ==""){
    $('#issueJobSnDiv').show();

  }else {
    $('#issueJobSnDiv').hide();
  } 
});


//--------------------------------------------------------
const data = getData();


const options = {
    container: document.getElementById('myChart'),
    theme: {
        overrides: {
            bar: {
                series: {
                    stroke: 'transparent',
                    strokeWidth: 2,
                    label: {
                        enabled: true,
                        formatter: ({ value }) => Math.abs(value),
                    },
                    formatter: ({ datum, yKey }) => ({
                        fillOpacity: getOpacity((datum[yKey]), yKey, 0.4, 1),
                    }),
                    tooltip: {
                        renderer: ({ datum, xKey, yKey }) => ({
                            content: `${datum[xKey]}: ${(datum[yKey])}`,
                        }),
                    },
                },
            },
        },
    },
    title: {
        text: '이슈현황도',
        spacing: 30
        ,
    },
    footnote: {
        text: '이슈 구분에 따른 이슈상태와 긴급도',
    },
    data,
    series: [
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'sttus',
            yKey: 'a',
            yName: '긴급',
            stacked: true,
        },
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'sttus',
            yKey: 'b',
            yName: '보통',
            stacked: true,
        },
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'sttus',
            yKey: 'c',
            yName: '낮음',
            stacked: true,
        },
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'sttus',
            yKey: 'd',
            yName: '낮음',
            stacked: true,
        },
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'sttus',
            yKey: 'e',
            yName: '보통',
            stacked: true,
        },
        {
            type: 'bar',
            direction: 'horizontal',
            xKey: 'sttus',
            yKey: 'f',
            yName: '긴급',
            stacked: true,
        },
    ],
    axes: [
        {
            type: 'category',
            position: 'left',
            line: {
                enabled: false,
            },
            tick: {
                values: ['진행', '보류','완료'],
            },
            gridLine: {
                enabled: true,
            },
        },
        {
            type: 'number',
            position: 'bottom',
            nice: false,
            min: -10,
            max: 10,
            label: {
                enabled: false,
            },
            tick: {
                values: [0],
                size: 0,
            },
            gridLine: {
                width: 2,
            },
            crossLines: [
                {
                    type: 'range',
                    range: [0, -40],
                    strokeWidth: 0,
                    fillOpacity: 0,
                    label: {
                        text: '일 반 이 슈',
                        position: 'top',
                    },
                },
                {
                    type: 'range',
                    range: [0, 40],
                    strokeWidth: 0,
                    fillOpacity: 0,
                    label: {
                        text: '결 함 이 슈',
                        position: 'top',
                    },
                },
            ],
        },
    ],
    legend: {
        enabled: false,
    },
};

function getOpacity(value, key, minOpacity, maxOpacity) {
    const [min, max] = getDomain(key);
    let alpha = Math.round(((value - min) / (max - min)) * 10) / 10;
    return map(alpha, 0, 1, minOpacity, maxOpacity);
}

function getDomain(key) {
    const min = Math.min(...data.map((d) => Math.abs(d[key])));
    const max = Math.max(...data.map((d) => Math.abs(d[key])));
    return [min, max];
}

const map = (value, start1, end1, start2, end2) => {
    return ((value - start1) / (end1 - start1)) * (end2 - start2) + start2;
};

const chart = agCharts.AgCharts.create(options);





//--------------------------------------------------------
/*chart.js  
// 데이터
const data = {
    labels: ['일반', '결함'],
    datasets: [
        {
            type: 'bar', // 막대 그래프
            label: '진행',
            data: [${gen.d}, ${bug.d}],
            backgroundColor: 'rgba(255, 99, 132, 0.2)',
            borderColor: 'rgb(255, 99, 132)',
            borderWidth: 1,
        },
        {
            type: 'bar', // 막대 그래프
            label: '보류',
            data: [${gen.e}, ${bug.e}],
            backgroundColor: 'rgba(153, 102, 255, 0.2)',
            borderColor: 'rgb(153, 102, 255)',
            borderWidth: 1,
        },
        {
            type: 'bar', // 막대 그래프
            label: '완료',
            data: [${gen.f}, ${bug.f}],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgb(75, 192, 192)',
            borderWidth: 1,
        },
        {
            type: 'line', // 꺽은선 그래프
            label: '긴급',
            data: [${gen.a}, ${bug.a}],
            fill: false,
            borderColor: 'rgba(255, 62, 29, 1)',
            borderWidth: 2,
            yAxisID: 'y2',
        },
        {
            type: 'line', // 꺽은선 그래프
            label: '보통',
            data: [${gen.b}, ${bug.b}],
            fill: false,
            borderColor: 'rgba(105, 108, 255, 1)',
            borderWidth: 2,
            yAxisID: 'y2',
        },
        {
            type: 'line', // 꺽은선 그래프
            label: '낮음',
            data: [${gen.c}, ${bug.c}],
            fill: false,
            borderColor: 'rgba(102, 199, 50, 1)',
            borderWidth: 2,
            yAxisID: 'y2',
        },
    ],
};

// 차트 설정
const options = {
		
    scales: {
        x: {
            stacked: true,
        },
        y: {
            stacked: true,
            beginAtZero: true,
            ticks: {
                stepSize: 5,
            },
            display: true,
            title: {
                display: true,
                text: '개수', 
            },
           
        },
        y2: {
            stacked: false,
            position: 'right',
            beginAtZero: true,
            ticks: {
                stepSize: 5,
            },
            display: true,
            title: {
                display: true,
                text: '중요도', 
            },
            labels: {
                // 레이블을 가로로 표시
                rotation: 120,
            },
        },
    },
    plugins: {
        legend: {
            display: true,
            labels: {
                usePointStyle: true, // 일자선 대신 점으로 표시
            },
            position : 'bottom',
        },
    },
};

// 복합 그래프 생성
const ctx = document.getElementById('issueChart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'bar',
    data: data,
    options: options,
});

*/
</script>
     