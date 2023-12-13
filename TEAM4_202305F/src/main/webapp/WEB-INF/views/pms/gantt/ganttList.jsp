<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* $2023. 11. 08      송석원      최초작성
* Copyright (c) $2023 by DDIT All right reserved
 --%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge;chrome=IE8">
<link href="/resources/gantt/css/style.css" type="text/css" rel="stylesheet">
<style type="text/css">
            
           
/*              h2 {  */
/*                  font-size: 1.5em;  */
/*                padding-bottom: 3px;  */
/*                 border-bottom: 1px solid #DDD;  */
/*                 margin-top: 50px;  */
/*                  margin-bottom: 25px;  */
/*              }   */ 
            table th:first-child {
                width: 150px;
            }
            .github-corner:hover .octo-arm {
                animation: octocat-wave 560ms ease-in-out
            }
            @keyframes octocat-wave {
                0%, 100% {
                    transform: rotate(0)
                }
                20%, 60% {
                    transform: rotate(-25deg)
                }
                40%, 80% {
                    transform: rotate(10deg)
                }
            }
            @media (max-width:500px) {
                .github-corner:hover .octo-arm {
                    animation: none
                }
                .github-corner .octo-arm {
                    animation: octocat-wave 560ms ease-in-out
                }
            }
            
            .gantt { 
    			width: 90%;
    			border: 2px solid rgba(0,0,0,0.1);
   			 }
   			 
   			 .row {
    		   --bs-gutter-x: 0rem;
    		}
    		
   			 #myModal{
   			 	position: fixed;
   			 	left:0px; top:0px;
   			 	width:100%;
   			 	height:100%;
   			 	background-color: rgba(50,50,255,0);
   			 	display:none;
   			 	z-index: 100;
   			 }
   			 
   			 #modContent{
   			 	width: 21%;
   			 	background-color: rgb(255,255,255);
   			 	margin: 100px auto;
   			 	border: 1px solid black;
   			 	padding: 30px;
   			 }
   			 
   			 #modal-header{
   			 	border-bottom: 3px solid black;
   			 	margin-bottom: 10px;
   			 	padding-left: 10px;
   			 }
   			 
   			 #modal-content{ 
   			 border: 3px solid black;
   			 padding: 10px;
   			 }
   			 

   			 
   			 
   			 #modal-footer{
			    max-height: calc(100vh - 200px);
			    overflow-y: auto;
			     border: 3px solid black;
			     margin-top: 10px;
			    
			}
			
			.container-p-y:not([class^=pt-]):not([class*=" pt-"]) {
    			padding-top: 0.6rem !important;
			}
			
   			 
			.status-box {
			    display: inline-block;
			    width: 10px;
			    height: 10px;
			    margin-right: 5px; 
			    vertical-align: middle;
			    border: 1px solid #000;  
			} 
   			 
   			 
   			 
        </style>
         
 <div   class="col-lg-4 col-md-6">
                      <div class="mt-3"> 
<!--                         <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBoth" aria-controls="offcanvasBoth"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;"> -->
<!--                           스크롤과 배경화면 모두 활성화 -->
<!--                         </font></font></button> --> 
                        <div  class="offcanvas offcanvas-end" data-bs-scroll="true" tabindex="-1" id="offcanvasBoth" aria-labelledby="offcanvasBothLabel" style="margin-top: 254px; height: 500px;">
                          <div class="offcanvas-header">
                            <h5 id="offcanvasBothLabel" class="offcanvas-title"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;"></font></font></h5>
                            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="닫다"></button>
                          </div>
                          <div  class="offcanvas-body my-auto mx-0 flex-grow-0">
                            <p class="text-center"><font style="vertical-align: inherit;">
                            <font style="vertical-a lign: inherit;"> 
                            
                            <div id="leftmar"> 
                              	<h4>일감등록일:<span id="jobRdate"></span></h4> 
								<h4>담당자: <span id="jobmem"></span></h4>
								<h4>시작일: <span id="jobBdate"></span></h4>
								<h4>종료일: <span id="jobEdate"></span></h4>
								<h4>진행도: <span id="jobProgrses"></span>%</h4>   
                            </div> 
								<div class="progress">
<!--                       <div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar" style="width: $('#jobProgrses span').text() %" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div> -->
                    			</div>   
                    			  
								</font>
<!-- 								<font style="vertical-align: inherit;"> -->
<!-- 									<h3>일감내용</h3>  -->
<!-- 									<h4> <span id="jobCns"></span></h4>  -->
<!--                             	</font> --> 
                            	</font></p>
                             <div class="btnlink">  
<!--                             <button type="button" class="btn btn-primary mb-2 d-grid w-100" ><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">상세페이지 이동</font></font></button> -->
                             </div>
                            <button type="button" class="btn btn-label-secondary d-grid w-100" data-bs-dismiss="offcanvas"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">
                              취소
                            </font></font></button>
                          </div>
                        </div>
                      </div>
                    </div>
        
  


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
        <div class="col-md-7 col-lg-7">
          <ul class="nav nav-pills mb-3 nav-fill" role="tablist">
            <li class="nav-item" role="presentation">
              <a class="nav-link dropdown-toggle" href="javascript:void(0);" data-bs-toggle="dropdown"
                aria-expanded="false" data-trigger="hover">
                <i class='bx bxs-copy-alt'></i>
                　프로젝트</a>
              <div class="dropdown-menu">
                <c:forEach items="${projname}" var="projname">
                  <c:if test="${projname.proSttus == 1}">
                    <a class="dropdown-item" href="/pms/gantt/${projname.proSn}">${projname.proNm}</a> 
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
              <a class="nav-item nav-link active" href="/pms/gantt/${proSn}">
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
        <!-- 내용 -->

		
		<div id="colorinfo" style="margin-left: 61px;"> 
		    <span class="status-box" style="background-color: rgb(231, 231, 255);"></span> :진행
		    <span class="status-box" style="background-color: rgb(232, 250, 223);"></span> :요청
		    <span class="status-box" style="background-color: rgb(255, 224, 219);"></span> :피드백
		    <span class="status-box" style="background-color: rgb(255, 242, 214);"></span> :오류
		    <span class="status-box" style="background-color: rgb(215, 245, 252);"></span> :완료 
		</div> 
  

        
        
        <div class="contain">
 
  
    <c:if test="${not empty proj}">
        <div class="gantt"></div>



        <script src="/resources/gantt/js/jquery.min.js"></script>
        <script src="/resources/gantt/js/jquery.fn.gantt.js"></script>
        <script src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>

        <script> 
        
       

        
        const myModal = document.querySelector('#myModal');
        
       	function fShowModal(data){
       		// this location, call ajax
       		$.ajax({
 		    	   type:"GET",
 		    	   url:`/pms/gantt/ganttChoice?proSn=\${data.proSns}&jobuSn=\${data.jobuSns}&jobSn=\${data.jobSns}`,
//  		    	   contentType:"application/json",
 		    	   dataType:"json",
//  		    	  data: JSON.stringify({proSns:data.proSns, jobuSns:data.jobuSns}), //추가 
 		    	 
 		    	   success:function(data){
 		   				console.log("data : ",data);
 		   			let pjobgantt = data.pjobgantt;
//   		   			 for (var i = 0; i < data.pjobgantt.length; i++) {
//   		   			targetDatas += data.pjobgantt[i].chargerList;   
//   		   			 }
//  		    	      targetDatas = data.pjobgantt[0].chargerList;   
//  		    	      console.log("zzzzz",targetDatas); 
 		    	      let code="";
 		    	      console.log("성공") 
 		    	      for(let y=0;y<pjobgantt.length;y++){
 		    	    	  let targetDatas =pjobgantt[y].chargerList;
 		    	    	  console.log("targetDatas",targetDatas);
// 	 		    	      code += targetDatas.emp.empName+" ";
	 		    	      $.each(targetDatas,function(i,v){
	 		    	      console.log("성공"+i) 
	//  		    	    	  code += v.empCd;
	 		    	      code += v.emp.empName+" ";
	//  		    	      	code += v.empName;
							console.log("v의 값을 확인해 볼까요?"+v)
	 		    	      })
 		    	      }
 		   			 
 		    	      console.log(code)
 		    	      $('#jobmem').html(code);
 		    	      
       				myModal.style.display = 'block';
 		    	      alert("1")
 		    	   },error:function(xhr){ 
 		    	      console.log("실패")
 		    	      alert("2")
 		    	   }
 		    	})   
       	}
       	function fCloseModal(){
       		myModal.style.display = 'none';
       	}
       	
        // HTML 태그 제거 함수
     
        
            $(function () {
                "use strict"; 

                // 프로젝트 이름을 배열에 저장
                var jobSj = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobSj}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>];
                var jobStcd = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobStcd}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>];
                var jobRdates = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobRdate}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>];
                var jobSns = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobSn}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>]; 
                var jobuSns = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobuSn}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>]; 
                var jobProgrses = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobProgrs}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>]; 
                var proSns = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.proSn}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>]; 
//                 var jobCns = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobCn}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>]; 
  

                // 날짜 데이터를 배열로 저장
                var jobBdates = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobBdate}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>];
                var jobEdates = [ <c:forEach items="${proj}" var="proj" varStatus="loop"> '${proj.jobEdate}' <c:if test="${!loop.last}"> , </c:if> </c:forEach>];
							console.log(formattedBDate);
							
// 			 // jobCns 배열 내의 \r\n을 <br>로 교체
// 		    for (var i = 0; i < jobCns.length; i++) {
// 		        jobCns[i] = jobCns[i].replace(/\r\n/g, "<br>");
// 		    }  
  
				
							
							
                // 데이터 변환 및 처리 -> 우리는 20230501형식인데 해당 js는 유닉스 타임 스탬프이기 때문에 형식을 바꿔줬음! 
                var dateObjects = [];
				for (var i = 0; i < jobSj.length; i++) {
				    var formattedBDate = jobBdates[i].replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
				    var formattedEDate = jobEdates[i].replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
				    var fromTimestamp = new Date(formattedBDate).getTime(); // 시작일을 유닉스 타임 스탬프로 변환
				    var toTimestamp = new Date(formattedEDate).getTime();   // 종료일을 유닉스 타임 스탬프로 변환
				     
				    dateObjects.push({
				        name: jobSj[i],
				        desc: " ",
				        values: [{
				            from: fromTimestamp,
				            to: toTimestamp,
				            label: jobSj[i],
				            dataObj: {
				                "jobSj": jobSj[i],
				                "jobRdates": jobRdates[i],
				                "jobBdates": jobBdates[i],
				                "jobEdates": jobEdates[i],
				                "jobSns": jobSns[i],
				                "jobuSns": jobuSns[i],
				                "jobProgrses": jobProgrses[i],
				                "proSns": proSns[i],
// 				                "jobCns": jobCns[i], 
				            },
				            customClass: (jobStcd[i] === "1") ? "ganttViolet" :
				                (jobStcd[i] === "2") ? "ganttGreen" :  
				                (jobStcd[i] === "3") ? "ganttRed" :
				                (jobStcd[i] === "4") ? "ganttStop" : 
				                (jobStcd[i] === "5") ? "ganttFinsh" : ""
				        }]
				    });
				}

				
				let text = "";
				let btnlk= "";
                $(".gantt").gantt({
                    source: dateObjects,
                    scale: "days", 
                    minScale: "hours",
                    navigate: "scroll",
                    maxScale: "months",
                    itemsPerPage:20,
                    scrollToToday: false,
                    useCookie: true,
                    onItemClick: function(data){
                    	fShowModal(data);
                    	$('#offcanvasBoth').offcanvas('show');
                    	console.log("여기에요 여",data);
                    	$("#jobHead").text(data.jobSj);
                    	$("#jobRdate").text(data.jobRdates);
                    	$("#jobBdate").text(data.jobBdates);
                    	$("#jobEdate").text(data.jobEdates);
                    	$("#jobSub").text(data.jobSns);
                    	$("#jobuSub").text(data.jobuSns);
                    	$("#jobProgrses").text(data.jobProgrses);
                    	$("#proSns").text(data.proSns);
//                     	$("#jobCns").text(data.jobCns);  
                    	console.log("jobSj:", data.jobSj);
                    	console.log("jobStcd:", jobStcd); 
                    	console.log("jobBdates:", data.jobBdates);
                    	console.log("jobEdates:", jobEdates);
                    	console.log("jobSub:", jobSns);
                    	console.log("진행도 표시하는곳~",data.jobProgrses);
                    	text = `<div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar"
                    	style="width: \${data.jobProgrses}%" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"></div>`;
						$(".progress").html(text); 
//                     	alert("ㄴㄴ");  
						btnlk=`<button type="button" class="btn btn-primary mb-2 d-grid w-100" onclick="location.href='/job/\${data.proSns}/\${data.jobSns}/detail'"><font style="vertical-align: inherit;">
						<font style="vertical-align: inherit;">상세페이지 이동</font></font></button>`; 
						$(".btnlink").html(btnlk);
                    },
                    onAddClick: function(dt, rowId){
                    	alert("클릭하신 부분에는 일정이 없습니다.");
                    },
                    onRender: function(){
                    	if (window.console && typeof console.log === "function"){
                    		console.log("chart rendered");
                    	}
                    }
                });
                
                 

            });
            
        </script>
    </c:if>
</div>
        
        
        
        

      </div>
      <!-- /Content -->
    </div>
    <!-- /Content wrapper -->
  </div>
  <!-- /Layout container -->
</div>
<!-- /Layout wrapper -->


