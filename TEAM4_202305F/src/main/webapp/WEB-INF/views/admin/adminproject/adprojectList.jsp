<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="form"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
  
<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* Nov 21, 2023      송석원      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%> 
 
 <style>
 	.table:not(.table-dark) th {
    color: #566a7f;
    font-size: 17px;
    }
    .offcanvas, .offcanvas-xxl, .offcanvas-xl, .offcanvas-lg, .offcanvas-md, .offcanvas-sm {
    --bs-offcanvas-width: 900px;
    }
/*      .test{  */
/*      	border: 1px solid black;  */
/*      }  */ 
    
    .wd50 {width:50% !important;}
    .wd100 {width:100% !important;}
    .ft-left{float:left;}
 </style>
  
<div class="card" style="width:100%;">
	<div class="card-header border-bottom">
		<h4 class="card-title">프로젝트 관리</h4>
		<div class="d-flex justify-content-between align-items-center row py-3 gap-3 gap-md-0">
			<div class="col-md-4 user_role"></div>
			<div class="col-md-4 user_plan"></div>
			<div class="col-md-4 user_status"></div> 
		</div>
	
		
		<div class="col-lg-3 col-md-6">
                      <div class="mt-3">
                        <button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasEnd" aria-controls="offcanvasEnd">
                          <span>
						<i class="bx bx-plus me-0 me-sm-1"></i>
						<span class="d-none d-sm-inline-block">프로젝트 생성</span>
					</span>
                        </button>
                        <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasEnd" aria-labelledby="offcanvasEndLabel">
                          <div class="offcanvas-header">
                            <h5 id="offcanvasEndLabel" class="offcanvas-title">프로젝트 생성</h5>
                            <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
                          </div>
                          <div class="offcanvas-body  mx-0 flex-grow-0">
                          
     
		<form  id="byForm" action="/adminproject" method="POST" > 
			<div class="modal-body"  >
				<div class="row">
					<div class="col mb-3 test" style="height: 700px; overflow-y: auto;">
						<div id="orgTreeContainer"></div>
						
					</div>
					<div class="col mb-0 test">
						
						<br />
						 프로젝트명 <br />
						<input type="text" class="form-control" id="proNm" name="proNm"  required/><br>  
						
			
						프로젝트 시작일<br>
						  <input name="proBdate" class="form-control" id="proBdate" name="proBdate" value="" required><br>
						   
						  
						  프로젝트 종료일 <br> 
						  <input name="proEdate" class="form-control" id="proEdate" name="proEdate" value="" required><br> <br>
						 
						<div class="d-flex align-items-center justify-content-between">
        						<h5>참여자 명단</h5>
        						<button type="button" id="remove" class="btn btn-primary">비우기</button>
   						</div>
   						<hr>
   						<div id="ftsz" style="font-size: medium;">처음 선택한 사원이 리더입니다.</div>   
						<div id="orgTreeResult" style="height: 360px; overflow-y: auto;"></div>
					</div>
				</div> 
			</div>  
			                  
                            <button type="submit" class="btn btn-primary mb-2 d-grid w-100"  id="pbtn4">프로젝트 생성</button> 
						 <security:csrfInput/>
						</form>
						
                            <button type="button" class="btn btn-label-secondary d-grid w-100" data-bs-dismiss="offcanvas">
                              Cancel
                            </button>
                          </div>
                        </div>
                      </div>
                    </div>
		<br />
	
		<div class="card-datatable table-responsive" > 
			<table class="table" style="overflow: auto;">
				<thead class="table-light">
					<tr>
						<th>상태</th> 
						<th >프로젝트코드</th> 
						<th >프로젝트명</th> 
						<th>리더사번</th>
						<th>리더명</th>
						<th>팀원수</th>
<!-- 						<th>상위일감수</th> -->
<!-- 						<th>하위일감수</th> -->
						<th>프로젝트 기간</th>
						<th><div style="margin-left: 17px;">수정</div></th> 
					</tr>
				</thead>
				<tbody class="table-border-bottom-0">
					<c:if test="${empty paging.dataList }">
						<tr> 
							<td colspan="6">검색 조건에 맞는 게시글 없음 </td>
						</tr>
					</c:if>
					<c:if test="${not empty paging.dataList }">
						<c:forEach	items="${paging.dataList }" var="adproj">
							<tr>
								<td>
						            <c:choose>
						                <c:when test="${adproj.proSttus eq 1}">진행</c:when>
						                <c:when test="${adproj.proSttus eq 2}">보류</c:when>
						                <c:when test="${adproj.proSttus eq 3}">완료</c:when>
						                <c:otherwise>상태 미정</c:otherwise>
						            </c:choose>
						        </td>
						        <td class="adproj-proSn">${adproj.proSn}</td>
								<td> 
								    <a href="<c:url value='/adminproject/${adproj.proSn}'/>">
								        <c:choose>
								            <c:when test="${fn:length(adproj.proNm) > 8}">
								                ${fn:substring(adproj.proNm, 0, 8)}...
								            </c:when>
								            <c:otherwise>
								                ${adproj.proNm}
								            </c:otherwise>
								        </c:choose>
								    </a>
								</td>  
 		 		
								<c:forEach	items="${adproj.pjobList}" var="pjoblt">
								
								<c:forEach	items="${pjoblt.chargerList}" var="projld">
								<c:forEach	items="${adproj.pmemberList}" var="pmemc">
									<td>	${projld.empCd}</td> 
									<td>	${projld.empLeadername}
									<c:if test="${projld.emp != null}">
									    <c:if test="${projld.emp.empLoginFlag eq 'L'}">
											<span style="color: red;">(퇴사자)</span> 
										</c:if>
									</c:if>   
									</td> 
									
									<td style="padding-left: 37px;">${pmemc.pmemCount }</td> 
<%-- 									<td>${pjoblt.upperJobcount }</td> --%>
<%-- 									<td>${pjoblt.lowerJobcount }</td> --%> 
								</c:forEach>
								</c:forEach>
								</c:forEach>				
								<td>${adproj.proBdate } ~ ${adproj.proEdate}</td>	
								<td>
									<button type="button" class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#smallModalProj">
                         				수정
                        			</button>
								</td>			
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="8"> 
							${paging.pagingHTML }
							<div id ="searchUI" class="row g-3 d-flex justify-content-center">
								<div class="col-auto">
									<form:select path = "simpleCondition.searchType" class="form-select"> 
										<form:option label="전체" value="" />
										<form:option label="제목" value="title" />
									</form:select>
								</div>
								<div class="col-auto">
								<form:input path="simpleCondition.searchWord" placeholder="검색키워드" class="form-control" />
								</div>
								<div class="col-auto">
								<input type="button" value="검색" id="searchBtn" class="btn btn-primary" />
								</div>
							</div>
						</td>
					</tr>
				</tfoot>
			</table>
		</div> 
	</div>
</div>


 


<!-- 모달창--> 
<div class="modal fade" id="smallModalProj" tabindex="-1" 
	style="display: none;" aria-hidden="true">
<form  id="byProj" action="/adminproject/projUpdate" method="PUT" >  
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content"> 
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel2">프로젝트 수정</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div> 
					<div class="col mb-0 wd100">
						<label for="nameSmall" class="form-label">프로젝트명</label> <input 
							type="text" id="projectName" class="form-control"
							placeholder="">
							<input type="hidden" id="projectId" name="proSn" value="">  
					</div>
					
					 
				</div>
				<div class="row g-2 mt-2">
					<div class="col mb-0 wd50 ft-left">
						<label class="form-label" for="emailSmall">시작예정일</label> <input
							 id="PRO_BDATE" name="PRO_BDATE" class="form-control " value="${adproj.proBdate}" > 
							 
					</div>
					<div class="col mb-0 wd50 ft-left">
						<label for="dobSmall" class="form-label">종료예정일</label> <input
							id="PRO_EDATE" name="PRO_EDATE" class="form-control" value="${adproj.proEdate }">
					</div> 
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-label-secondary"
					data-bs-dismiss="modal">닫기</button>
				<button id="smallModalProjEditBtn" type="submit" class="btn btn-primary">수정하기</button> 
			</div> 
		</div>
	</div>
	<security:csrfInput/> 
</form>  
</div>




  



<!-- 히든태그 사용 -->
<form:form modelAttribute="simpleCondition" method="get" id = "searchForm" class = "border" >
	<form:hidden path="searchType" readonly="readonly" placeholder="searchType"/>
	<form:hidden path="searchWord" readonly="readonly" placeholder="searchWord"/>
	<input type="hidden" name="page" readonly="readonly" placeholder="page"/>
</form:form>

<script>


  
$(document).ready(function() {
    let startDate = $('#proBdate');
    let endDate = $('#proEdate');   

    startDate.datepicker({
        format: "yyyy-mm-dd",
        startDate: '+1d',
        autoclose: true,
        showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
        todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false  
        toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
        language: "ko"
    }).on('changeDate', function(selected) {
        let minDate = new Date(selected.date.valueOf());
        endDate.datepicker('setStartDate', minDate);
        if (endDate.val() < startDate.val()) {
            endDate.val('');
        }
    });
 
    endDate.datepicker({
        format: "yyyy-mm-dd",
        startDate: '+1d',
        autoclose: true,
        showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
        todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false  
        toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
        language: "ko"
    }).on('changeDate', function(selected) {
        let maxDate = new Date(selected.date.valueOf());
        startDate.datepicker('setEndDate', maxDate);
        if (startDate.val() > endDate.val()) {
            startDate.val('');
        }
    });
});
$(document).ready(function() {
    let startDate = $('#PRO_BDATE');
    let endDate = $('#PRO_EDATE');

    startDate.datepicker({
        format: "yyyy-mm-dd",
        startDate: '+1d',
        autoclose: true,
        showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
        todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false  
        toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
        language: "ko"
    }).on('changeDate', function(selected) {
        let minDate = new Date(selected.date.valueOf());
        endDate.datepicker('setStartDate', minDate);
        if (endDate.val() < startDate.val()) {
            endDate.val('');
        }
    });
 
    endDate.datepicker({
        format: "yyyy-mm-dd",
        startDate: '+1d',
        autoclose: true,
        showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
        todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false  
        toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
        language: "ko"
    }).on('changeDate', function(selected) {
        let maxDate = new Date(selected.date.valueOf());
        startDate.datepicker('setEndDate', maxDate);
        if (startDate.val() > endDate.val()) {
            startDate.val('');
        }
    });
});



   
 
	//YY.MM.DD 형식의 날짜를 YYYY-MM-DD 형식으로 변환하는 함수
	function formatDateToISO(dateString) {
	    // dateString을 "."을 기준으로 분리하여 배열로 만듭니다.
	    var parts = dateString.split('.');
	    
	    // parts 배열에서 연, 월, 일을 추출합니다.
	    var year = parseInt(parts[0], 10) + 2000; // 년도는 2000을 더해줘야 합니다.
	    var month = parts[1];
	    var day = parts[2];
	
	    // 날짜를 YYYY-MM-DD 형식으로 반환합니다.
	    return year + '-' + month + '-' + day;
	}
	 
	// 클릭 이벤트 핸들러 내에서 날짜 형식 변경하기
	$(document).on('click', '.btn-warning', function() {
	    var projectCode = $(this).closest('tr').find('td:nth-child(2)').text().trim();
	    var projectName = $(this).closest('tr').find('td:nth-child(3)').text().trim();
	    var dateRange = $(this).closest('tr').find('td:nth-child(7)').text().trim();
	 
	    console.log('projectCode:', projectCode);
	    console.log('Project Name:', projectName); 
	    console.log('Date Range:', dateRange);
	
	    // Date Range를 "YY.MM.DD ~ YY.MM.DD"에서 시작날짜와 종료날짜로 분리
	    var dates = dateRange.split(' ~ ');
	    var startDate = formatDateToISO(dates[0]);
	    var endDate = formatDateToISO(dates[1]);
	
	    console.log('Formatted Start Date:', startDate);
	    console.log('Formatted End Date:', endDate);
	
	    // 모달에 데이터 설정
	    $('#projectName').val(projectName);
	    $('#projectId').val(projectCode);   
	    $('#PRO_BDATE').val(startDate); 
	    $('#PRO_EDATE').val(endDate);
	}); 
	


	const byForm = document.querySelector("#byForm");

	byForm.addEventListener("submit",()=>{
		event.preventDefault();

		 const pmemberList = [];
		 const pMems = byForm.querySelectorAll("[name=pMember]");
		 console.log("체크:",pMems); 

		 for(let i=0; i< pMems.length; i++){
			let pMemVO = {
				empCd: pMems[i].value
			}
			pmemberList.push(pMemVO);
		 }

		// 개발모드에서는 항상 필요한 데이타가 보이도록 프로그램
		let projVO = {
		 	proNm:byForm.proNm.value,	
 			proBdate:byForm.proBdate.value,		
 			proEdate:byForm.proEdate.value,	
			pmemberList:pmemberList
		}
         
		console.log("projVO",projVO);
 
		
		$.ajax({
			type:"post",
			url:"/adminproject",
			contentType:"application/json",
			data: JSON.stringify(projVO),
			dataType:"text",
			success:function(resp){
				console.log("체크:",resp);
				Swal.fire({
		            title: '프로젝트가 정상적으로 생성되었습니다.',
		            icon: 'success',
		            showCancelButton: false,
		            confirmButtonColor: '#3085d6',
		            confirmButtonText: '확인'
		        }).then((result) => {
		            if (result.isConfirmed) {
		                location.reload();
		            }
		        }); 
			},
			error:function(xhr){
				console.log("Error:",xhr.status);
			}
		})


	})



	function fn_paging(page) {
		searchForm.page.value = page;
		searchForm.requestSubmit();
	}
	$(searchUI).on("click", "#searchBtn", function(event){
		let inputs = $(this).parents("#searchUI").find(":input[name]");
		$.each(inputs, function(idx, ipt){
			let name = ipt.name;
			let value = $(ipt).val();
			$(searchForm).find(`:input[name=\${name}]`).val(value);
			$(searchForm).submit();
		});
	});
	
	
	 
	
// 	// 저장 버튼 클릭 이벤트 처리
		 const byProj = document.querySelector("#byProj");
		    
		    byProj.addEventListener("submit", () => {
		    	
		    	event.preventDefault();
		
	    // 선택된 프로젝트 정보 가져오기 
	    var newprojectName = $('#projectName').val(); // 변경된 프로젝트 이름
	    var projectCode = $('#projectId').val(); // 프로젝트 ID (예시에서는 모달 내의 hidden 필드로 가정)
	    var newproBdate  = $('#PRO_BDATE').val();
		var newproEdate  = $('#PRO_EDATE').val(); 
	    console.log("데이터 형식{}",newprojectName);  
	    console.log("데이터 형식{}",projectCode); 
	    console.log("데이터 형식{}",newproBdate); 
	    console.log("데이터 형식{}",newproEdate);  
	    
	   function changeTotype(originalDate){
		   return originalDate.split('-').join('');
	   }
	   var realproBdate = changeTotype(newproBdate);
	   var realproEdate = changeTotype(newproEdate);
	   
	   console.log(realproBdate);
	   console.log(realproEdate);
	   
	    	
	    	const proj = [];

			let pjVO = {
					proNm: newprojectName,
		            proSn: projectCode, 
		            proBdate: realproBdate,
		            proEdate: realproEdate	 
			};
			
			console.log("pjVO:{}",pjVO);

	    
	    
	    
	    
	    // 변경된  프로젝트 정보를 proSn를 가지고 서버에 저장 요청을 보냄 
	    $.ajax({
	        type: 'PUT', 
	        url: '/adminproject/projUpdate', 
	        contentType:"application/json",
	        data: JSON.stringify(pjVO), 
	        dataType: 'text',  
	        success: function(response) {
	            console.log('프로젝트 변경 성공');
	            
	            $('#smallModalProjEditBtn').modal('hide');
	            Swal.fire({
		            title: '프로젝트가 정상적으로 수정되었습니다.',
		            icon: 'success', 
		            showCancelButton: false,
		            confirmButtonColor: '#3085d6',
		            confirmButtonText: '확인'
		        }).then((result) => {
		            if (result.isConfirmed) {
		                location.reload();
		            }
		        });
	        }, 
	        error: function(xhr, status, error) {
	            console.error('프로젝트 변경 실패:', error); 
	        }
	    });
	}); 

</script>  
<script src="${pageContext.request.contextPath }/resources/js/app/adproject/addproject.js"></script> 
