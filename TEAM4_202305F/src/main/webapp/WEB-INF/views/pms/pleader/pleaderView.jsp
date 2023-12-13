<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* Nov 29, 2023      송석원      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>  
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script> 

<style>
    .jobbox-container {
        display: flex;
        flex-wrap: wrap;
        gap: 10px; /* 각 박스 사이의 간격 설정 */
    }

    .jobbox {
        width: calc(50% - 5px); /* 각각의 박스가 50% 너비를 차지하도록 설정하고, 간격을 고려하여 5px를 뺀 값으로 설정 */
        border: 1px solid black; 
        box-sizing: border-box; /* border를 포함하여 크기를 계산하도록 설정 */
        display: flex;
        justify-content: center; /* 내부 컨텐츠를 수평 중앙 정렬 */
        align-items: center; /* 내부 컨텐츠를 수직 중앙 정렬 */
        height: 40px; /* 박스의 높이 설정 */
        font-size:15px;
    }
    
    
</style>
 <h1 class="mb-0" >${proInfo.proNm}</h1>
<h5 class="mb-0">${proInfo.proBdate} ~ ${proInfo.proEdate }</h5><br>
  
         <!-- navbar --> 
        <div class="col-md-7 col-lg-7">
          <ul class="nav nav-pills mb-3 nav-fill" role="tablist">
            <li class="nav-item" role="presentation">
              <a class="nav-link dropdown-toggle" href="javascript:void(0);" data-bs-toggle="dropdown"
                aria-expanded="false" data-trigger="hover">
                <i class='bx bxs-copy-alt'></i>
                　프로젝트</a>
              <div class="dropdown-menu">
            <c:forEach items="${projname}" var="proj">
                <c:if test="${proj.proSttus == 1}">
                    <a class="dropdown-item " href="/pleader/${proj.proSn}">${proj.proNm}</a>
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
              <a class="nav-item nav-link " href="/issue/${proSn}/home"> 
                <i class='bx bx-calendar-exclamation'></i>
                　이슈</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link " href="/pms/gantt/${proSn}">
                <i class='bx bx-chart'></i>
                　간트차트</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link active" href="/pleader/${proSn }"> 
                <i class='bx bxs-doughnut-chart'></i>
                　리더통계</a>
            </li>
          </ul>
        </div>
 <div>
<script>


const swData = [];
</script>
 <c:forEach	items="${pjobEveryThing }" var="progress">
<%--  ${progress.aaCnt }     --%>
 <c:forEach	items="${progress.chargerList }" var="chargerName">
<script>

var oneData = {
  quarter: "${chargerName.emp.empName }",
  진행: "${progress.aaCnt }"-0,
  요청: "${progress.bbCnt }"-0,
  피드백: "${progress.ccCnt }"-0,
  보류: "${progress.ddCnt }"-0, 
  완료: "${progress.eeCnt }"-0,
} 
swData.push(oneData);
console.log("check",swData);
</script>
 </c:forEach> 
 </c:forEach> 
 </div>

<div class="row mb-5">
    <div class="col-md-3 col-lg-3" style="width: 30%;">
        <div class="card mb-3">
            <div class="card-body">
                <h5 class="card-title"><i class='bx bxs-doughnut-chart bx-md mb-1'></i>전체 일감 현황</h5>
                <div class="jobbox-container">
                    <div class="jobbox">전체업무:	${pjobleader.totalCount}</div>
                    <div class="jobbox">진행:	${pjobleader.aaCnt}</div>
                    <div class="jobbox">요청:	${pjobleader.bbCnt}</div>
                    <div class="jobbox">피드백:	${pjobleader.ccCnt}</div>
                    <div class="jobbox">오류:	${pjobleader.ddCnt}</div>
                    <div class="jobbox">완료:	${pjobleader.eeCnt}</div>  
                </div>
            </div>
        </div>
    </div>


		<div class="col-md-3 col-lg-3">
			<div class="card mb-3">
				<div class="card-body">
					<h5 class="card-title"><i class='bx bxs-doughnut-chart bx-md mb-1'></i>참여자 일감 현황</h5> 
					<div>
						<canvas id="leaderChart" style="height:400px"></canvas> 
					</div>
				</div>
			</div>
		</div>
		
		 <div id="myChart" style="height: 380px; width: 500px; " class="card"></div> 

        <script>
          console.log("데이터 체크:",swData);
            (function () {
                const appLocation = '';

                window.__basePath = appLocation;
            })();

          function getData() {
              return swData;
             
          }
        </script> 
        <script src="https://cdn.jsdelivr.net/npm/ag-charts-community@9.0.0/dist/umd/ag-charts-community.js?t=1701270438976"></script>
        <script src="${pageContext.request.contextPath}/resources/js/app/pleader/pleadermainchart.js"></script>   
  		
</div>
  
<div class="row mb-5" >  <input type="hidden" id="hiddenproSn" value="${proSn }" /> 
<select  class="mb-2 form-select" id="memListleader"  style="width: 192px; height: 35px; margin-left: 12px;">
   	<c:forEach items="${pmemAdminList }" var="projmem">
       	<option value="${projmem.emp.empCd}">  
           	${projmem.emp.empName } (${projmem.emp.empCd})
           	<c:if test="${projmem.emp != null}">
			    <c:if test="${projmem.emp.empLoginFlag eq 'L'}">
					<span style="color: red;">(퇴사자)</span>  
				</c:if>
			</c:if>
    		</option>  
    </c:forEach>  
</select>      
<div class="card-datatable table-responsive" style="width: 1292px;  height: 320px;">   
			<table class="table">
			<colgroup>
                <col width="40%" />
                <col width="15%" />
                <col width="15%" /> 
                <col width="15%" /> 
                <col width="15%" />
                </colgroup>
				<thead class="table-light">
					<tr>
						<th>일감명</th> 
						<th >상태</th> 
						<th>진행도</th>
						<th>마감일</th>
						<th>중요도</th>
					</tr>
				</thead> 
				<tbody id ="bepaging" class="table-border-bottom-0">

							
				</tbody>   
			</table>
		</div>
		<div> 
		<table class="table"> 
				<tfoot id="betfoot">
				
				
				</tfoot>
		</table>
		</div> 
</div>
<form action="<c:url value='/pleader/leadercheck'/>" id="searchForm" > 
	<input type="hidden" name="empCd" />
	<input type="hidden" name="proSn"/>
	<input type="hidden" name="page"/>
	<input type="hidden" name="searchType"/>
	<input type="hidden" name="searchWord"/> 
</form>
  
  
   
  
  
<script>
$(function(){
	sessionStorage.setItem("proSn","${proInfo.proSn}");
	fn_paging(1); 
});

function fn_paging(page){
	bebepaging(page,sessionStorage.getItem("proSn"),sessionStorage.getItem("empCd"));
}

$(document).on("click","#searchBtn",function(){
	bebepaging(1,sessionStorage.getItem("proSn"),sessionStorage.getItem("empCd"));
});

let bebepaging = function(page,proSn,empCd){   
	/*
	proSn : P23006
	 empCd : E220801001
	*/
	
	sessionStorage.setItem("proSn",proSn);
	sessionStorage.setItem("empCd",empCd);
	
	$("#page").val(page);
	$("#proSn").val(proSn);
	$("#empCd").val(empCd);
	let searchType = $("#searchType").val();
	let searchWord = $("#searchWord").val();
	
	 let formData = $("#searchForm").serialize();  
	 
	 let data = {
			 "page":page,
			 "proSn":proSn,
			 "empCd":empCd,
			 "searchType":searchType,
			 "searchWord":searchWord
	 };
	 
	 console.log("data : ", data);
    
	 
	$.ajax({
	    type:"post", 
	    url: '/pleader/leadercheckAjax', 
	    contentType:"application/json;charset=utf-8",
	    dataType: "json",
	    data:JSON.stringify(data),  
	    beforeSend:function(xhr){
            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
         },
	    success: function(resp) { 
	        let tag="";
	        let pagingleader = resp.paging.dataList;
	        console.log("asdfghj=====",resp);
	        
	        if(pagingleader?.length >0){
	        	$.each(pagingleader, function(i, v){
	        		
	        		
			        tag += ` 
			        <tr>
			        	<td>\${v.jobSj}</td>
			        	<td>`;
			        	
			        	if(v.jobStcd==1){ 
			        		tag += `<span class="badge bg-label-primary me-1">진행</span>`;
			        	}
			        	else if(v.jobStcd==2){ 
			        		tag += `<span class="badge bg-label-success me-1">요청</span>`;
			        	}
			        	else if(v.jobStcd==3){ 
			        		tag += `<span class="badge bg-label-danger me-1">피드백</span>`;
			        	}
			        	else if(v.jobStcd==4){ 
			        		tag += `<span class="badge bg-label-warning me-1">보류</span>`;
			        	}
			        	else if(v.jobStcd==5){ 
			        		tag += `<span class="badge bg-label-info me-1">완료</span>`;
			        	}
			        	else {   
			        		tag += `<span class="badge bg-label-info	 me-1">상위</span>`; 
			        	}
			        
			        	tag +=`
			        	</td>
			        	<td>\${v.jobProgrs}%</td>  
			        	<td>\${v.jobEdate}</td>
			        	
			        	<td>`;
			        	if(`\${v.jobPriort}`==1){
			        		tag += `<span class="badge bg-label-danger me-1">긴급</span>`;
			        	}
			        	else if(`\${v.jobPriort}`==2){
			        		tag += `<span class="badge bg-label-warning me-1">높음</span>`;
			        	}
			        	else if(`\${v.jobPriort}`==3){
			        		tag += `<span class="badge bg-label-primary me-1">중간</span>`;
			        	}
			        	else if(`\${v.jobPriort}`==4){  
			        		tag += `<span class="badge bg-label-success me-1">낮음</span>`;
			        	}
			        	
			        	tag+= `
			        	</td>
			         	
			        
			        	</tr>
			        `; 
	        	})
	        $("#bepaging").html(tag);
	        	
	        	tag = `
	        	 					<tr> 
	        	 						<td colspan="5" >   
	        							\${resp.paging.pagingHTML } 
	        							<form id="searchForm">
	        		                     <div id ="searchUI" class="row g-3 d-flex justify-content-center">
	        		                        <input type="hidden" id="page" name="page" readonly="readonly"/>
	        		                        <input type="hidden" id="proSn" name="proSn" value="" readonly="readonly"/>
	        		                        <input type="hidden" id="empCd" name="empCd" value="" readonly="readonly"/>
	        		                        <div class="col-auto">
	        		                           <select  id="searchType" name="searchType" class="form-select"> 
	        		                              <option value="title" >일감명</option>
	        		                           </select>
	        		                        </div>
	        		                        <div class="col-auto">
	        		                           <input id="searchWord" name="searchWord" placeholder="입력하세요" class="form-control" />
	        		                        </div>
	        		                        <div class="col-auto">
	        		                           <input type="button" value="검색" id="searchBtn" class="btn btn-primary" />
	        		                        </div>
	        		                     </div>
	        		                  </form> 
	        	 						</td> 
	        	 					</tr> 
	        	`; 
	        	 $("#betfoot").html(tag); 
	        } else{
	        	tag =`
	        		<tr> 
					<td colspan="5" style="text-align:center;">일감이 없습니다. </td>
					</tr>
	        	
	        	`;
	        	
	        	$("#bepaging").html(tag);
	        }
	        
	     
	    	 
	        console.log("성공");  
	      ;  
	    },
	    error: function(xhr, status, error) {
	    
	    	
	    	console.log(xhr.status); 
	    }
	});//end ajax
}
 
$("#memListleader").on("change",function(){
	 var proSn = $('#hiddenproSn').val();
	 var empCd = $('#memListleader').val(); 
	
	 //proSn : P23006
	 console.log("proSn : " + proSn);
	 //empCd : E220801001
	 console.log("empCd : " + empCd);
	 
	bebepaging(1,proSn,empCd);
});


   






/*chart.js  */
document.addEventListener("DOMContentLoaded", function () {

  const ctx = document.querySelector('#leaderChart');
  const data = {
    labels: [
      '진행',
      '요청',
      '피드백',
      '보류',
      '완료'
    ],   
    datasets: [{
      label: '일감개수',
      data: [${ pjobleader.aaCnt }, ${ pjobleader.bbCnt }, ${ pjobleader.ccCnt }, ${ pjobleader.ddCnt }, ${ pjobleader.eeCnt }], 
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
  
  const leaderChart = new Chart(ctx, {
      type: 'doughnut', 
      data: data
    });
  })


</script>
