<%--
* [[개정이력(Modification Information)]]
* 수정일       	수정자        수정내용
* ----------  	---------  -----------------
* 2023. 12. 1.   김보영        최초작성
* 2023. 12. 4.   송석원        부서 인원 지표분석 
* 2023. 12. 5.   송석원        직급별 직원 분포도 
* 2023. 12. 5.   김보영        올해 입퇴사자, 부서별 근무시간
* 2023. 12. 5.   박민주        전년 월별 대비 자원의 사용률
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>        
<script>
const deptData = [];
const rankData = [];
const hireEmpData = [];
const leaveEmpData = [];
const deftWorkTime= [];
</script>


<!-- 부서별 일 근무시간  -->
<c:forEach items="${workTime}" var="workTime">
    <script>
        var workTimeObj = {
            deftName: "${workTime.deftName}",
            workTime: ${workTime.workTime} 
        };
        deftWorkTime.push(workTimeObj);
        console.log("check보영",deftWorkTime);
    </script>
</c:forEach>

<!-- 입사자 -->
<c:forEach items="${hireCnt}" var="hireCnt">  
    <script>
        var hireEmp ={
            year: "${hireCnt.thisMonth}월",
            cnt: ${hireCnt.hireCnt}
        };
        hireEmpData.push(hireEmp);
    </script>
</c:forEach>

<!-- 퇴사자 -->
<c:forEach items="${leaveCnt}" var="leaveCnt">  
    <script>
        var leaveEmp ={
            year: "${leaveCnt.thisMonth}월",
            cnt: ${leaveCnt.leaveCnt}
        };
        leaveEmpData.push(leaveEmp);
    </script>
</c:forEach>


<c:forEach items="${deptmemCount}" var="deptmemCount">  
<script>
var oneData={
		dept: "${deptmemCount.deptName}", memC: "${deptmemCount.deptCount}"-0	
}
deptData.push(oneData);
console.log("check",deptData);
</script>
</c:forEach> 


<c:forEach items="${rankmemCount}" var="rankmemCount"> 
	<script>
	var twoData={ 
			type: "${rankmemCount.common.commonCodeSj}", count: "${rankmemCount.rankCount}"-0
	}
	rankData.push(twoData);
	console.log("check2",rankData);
	</script>
</c:forEach>     


<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <!-- Layout container -->
  <div class="layout-container">
    <!-- Content wrapper -->
    <div class="layout-page">
      <!-- Content -->
      <div>
        <div class="container-xxl flex-grow-1 container-p-y">
          <div>
            <h4 class="py-3 mb-4 text-end"><i class='bx bx-happy-alt bx-tada mt-n2'></i> 안녕하세요 관리자님</h4>
          </div>
          <div class="row ">
            <div class="col-md-6 col-lg-5">
              <h6 class=" text-muted">부서별 일 평균근무시간</h6>
              <div class="card mb-4">
                <div class="card-body">
                 <div id="deftWorkTime" style="height: 380px; width: 100%;"></div>
                  <script> 
			            (function () {const BY1appLocation = ''; window.__basePath = BY1appLocation; })();
			            function getBY2Data() {
			                const workTimeData = deftWorkTime;
			                return workTimeData;
			            }
			        </script>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg-7">
              <h6 class=" text-muted">직급별 연차 사용률</h6>
              <div class="card mb-4">
                <div class="card-body" style="height: 426px; width: 100%;">
                  <canvas id="ALURBRchart" style="height: 100%;"></canvas>
                </div>
              </div>
            </div>
          </div>
          
          <div class="row">
            <div class="col-md-6 col-lg-4">
              <h6 class=" text-muted">퇴사사유</h6>
              <div class="card mb-4">
                <div class="card-body">

				<div style="width:100%; height:380px;">
			        <canvas id="myChart"></canvas>
			    </div>
			    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
				<script type="text/javascript">
					 const ctx = document.querySelector('#myChart');
	
						//만들위치, 설정값객체
				        new Chart(ctx, {
				            type: 'pie',  // bar, line, pie, doughnut, radar 등등...
				            data: {
				                labels: ['개인사정', '출산결혼', '직원간 불화', '연봉협상', '복리후생'],
				                datasets: [
				                    {
				                        data: [7, 5, 3, 2, 2],
				                        borderWidth: 1,
				                        backgroundColor: ["#9669CB", "#34BFE1", "#5090DC", "#FFA546", "#50A35F"],
				                    }
				                ]
				            },
				            options: {
				                scales: {
				                    y: {
				                        beginAtZero: true
				                    }
				                }
				            }
				        });
				</script>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg-4">
              <h6 class=" text-muted">직급별 직원 분포도</h6>
              <div class="card mb-4">
                <div class="card-body"> 
                  <div id="rankChart" style="height: 380px; width: 100%;"></div>
			        <script>
			            (function () {
			                const appLocation = '';
			
			                window.__basePath = appLocation;
			            })();
			            
			            function getData1(){
			            	return rankData; 
			            }
			        </script> 
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg-4">
              <h6 class=" text-muted">부서별 인원수</h6>
              <div class="card mb-4">
                <div class="card-body"> 
                  <div id="deptChart" style="height: 380px; width: 100%;"></div>  
  
			        <script> 
			            (function () { 
			                const appLocation = ''; 

			                window.__basePath = appLocation; 
			            })();
	            
			            function getData(){ 
			            	return deptData;
			            }
	            
			        </script>
	         
                </div> 
              </div>
            </div>
          </div>
          
          <div class="row">
            <div class="col-md-6 col-lg-7">
              <h6 class=" text-muted">작년과 올해 월별 자원 사용 건수 비교
              	( <a id="confChart" style="cursor:pointer; ">회의실</a> / <a id="vehicleChart" style="cursor:pointer;">차량</a> )
              </h6>
              <div class="card mb-4">
                <div class="card-body" style="height: 430px; width: 100%;">
                 	<canvas id="reservationChart" ></canvas>
                </div>
              </div>
            </div>
            <div class="col-md-6 col-lg-5">
              <h6 class=" text-muted">올해 입퇴사자</h6>
              <div class="card mb-4">
                <div class="card-body">
                 <div id="HLEmpCnt" style="height: 380px;width: 110%; margin-left: -21px;"></div>
                 	<script> 
			            (function () {const BYappLocation = ''; window.__basePath = BYappLocation; })();
			            function getBYData() {
			                const HLEmpData = {
			                    '입사자': hireEmpData,
			                    '퇴사자': leaveEmpData
			                };
			                return HLEmpData;
			            }
			        </script>
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


<!-- 부서별 일 평균 근무시간 -->
<script src="https://cdn.jsdelivr.net/npm/ag-charts-enterprise@9.0.0/dist/umd/ag-charts-enterprise.js?t=1701269939096"></script>

<!-- 입퇴사자 -->
<script src="https://cdn.jsdelivr.net/npm/ag-charts-enterprise@9.0.0/dist/umd/ag-charts-enterprise.js?t=1701269939174"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/admin/analysis/hire_leave_empCnt.js"></script>

<!--  부서 -->
<script src="https://cdn.jsdelivr.net/npm/ag-charts-enterprise@9.0.0/dist/umd/ag-charts-enterprise.js?t=1701269939062"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/admin/analysis/analysisdeptmain.js"></script>
<!-- 직급 -->
<script src="https://cdn.jsdelivr.net/npm/ag-charts-enterprise@9.0.0/dist/umd/ag-charts-enterprise.js?t=1701269939389"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/admin/analysis/rankcountmain.js"></script>  
<!-- 전년 월별 대비 자원의 사용률 -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.4.0/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/admin/analysis/reservationChart.js"></script>

<!-- 직급별 연차 사용률 -->
<script src="${pageContext.request.contextPath}/resources/js/app/admin/analysis/annualLeaveUsageRateByRank.js"></script>    

