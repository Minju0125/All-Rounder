<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* Nov 22, 2023      송석원      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>    
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script> 

<style>
 .offcanvas, .offcanvas-xxl, .offcanvas-xl, .offcanvas-lg, .offcanvas-md, .offcanvas-sm {
    --bs-offcanvas-width: 600px; 
    }
</style>



<div class="card">
	<div class="card-header border-bottom">
		<h4 class="card-title">프로젝트 멤버관리</h4>
		<div class="col-lg-6 col-md-6">
			<div class="mt-3" style="display: flex; flex-direction: row;">
				<button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasEnd"
					aria-controls="offcanvasEnd">
					<span>
						<i class="bx bx-plus me-0 me-sm-1"></i>
						<span class="d-none d-sm-inline-block">프로젝트 멤버추가</span>
					</span>
				</button>
				<div class="col-auto">
					<button type="button" value="목록" class="btn btn-primary"
					onclick="fn_goback()" style="margin-left: 13px;" >목록</button>
					<button onclick="fn_goDetail()" type="button" class="btn btn-primary mx-2">상세보기</button>
				</div>
				<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasEnd" aria-labelledby="offcanvasEndLabel">
					<div class="offcanvas-header">
						<h5 id="offcanvasEndLabel" class="offcanvas-title">멤버 추가</h5>
						<button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="Close"></button>
					</div>
					<div class="offcanvas-body  mx-0 flex-grow-0">
						<form id="byPmemOrg" action="/adminpmember" method="POST">
							<div class="modal-body">
								<div class="row">
									<div class="col mb-3 test" style="height: 700px; overflow-y: auto;">
										<div id="orgTreeContainer"></div>
									</div>
									<div class="col mb-0 test">
										<br>
										<div class="d-flex align-items-center justify-content-between">
											<h5>추가참여자 명단</h5>
											<button type="button" id="remove" class="btn btn-primary">비우기</button>
										</div>
										<hr>
										<div id="orgTreeResult" style="height: 360px; overflow-y: auto;"></div>
									</div>
								</div>
							</div>
							<button type="submit" class="btn btn-primary mb-2 d-grid w-100" id="pbtn4">멤버 생성</button>
							<security:csrfInput />
						</form>
						<button type="button" class="btn btn-label-secondary d-grid w-100" data-bs-dismiss="offcanvas">
							Cancel
						</button>
					</div>
				</div>
			</div>
		</div>
		<br />

		<div class="card-datatable table-responsive">
			<table class="table">
				<thead class="table-light">
					<tr>
						<th>프로필사진</th>
						<th>사번</th>
						<th>이름</th>
						<th>부서</th>
						<th>이메일</th>
						<th>내선번호</th>
						<th>휴대폰번호</th>
						<th>역할</th>
						<th style="padding-left: 52px;">삭제</th>
					</tr>
				</thead>
				<tbody class="table-border-bottom-0">
					<c:if test="${empty paging.dataList }">
						<tr>
							<td colspan="6">검색 조건에 맞는 게시글 없음 </td>
						</tr>
					</c:if>
					<c:if test="${not empty paging.dataList }">


						<c:forEach items="${paging.dataList }" var="adchar">
							<tr>
								<c:if test="${not empty adchar.emp.empProfileImg }">
									<td><img src='${adchar.emp.empProfileImg }' style='height: 46px; width:46px;' /></td>
								</c:if>
								<c:if test="${empty adchar.emp.empProfileImg }">
									<td><img src='/resources/images/basic.png' style='height: 46px; width:46px;' /></td>
								</c:if>

								<td>${adchar.emp.empCd }</td>
								<td>${adchar.emp.empName }
									<c:if test="${adchar.emp != null}">
										<c:if test="${adchar.emp.empLoginFlag eq 'L'}">
											<span style="color: red;">(퇴사자)</span>
										</c:if>
									</c:if>
								</td>
								<td>${adchar.emp.deptCd }</td>
								<td>${adchar.emp.empMail }</td>
								<td>${adchar.emp.empExtension }</td>
								<td>${adchar.emp.empTelno }</td>

								<td>
									<c:choose>
										<c:when test="${adchar.proLeader eq 'Y'}">리더</c:when>
										<c:when test="${adchar.proLeader eq 'N'}">팀원</c:when>
										<c:otherwise>상태 미정</c:otherwise>
									</c:choose>
								</td>
								<c:choose>
									<c:when test="${adchar.proLeader eq 'N'}">
										<td>
											<div style="text-align: center;">
												<input id="proSn" type="hidden" name="proSn" value="${adchar.proSn}" />
												<input class="empCdad" type="hidden" name="empCd" value="${adchar.emp.empCd }" />
												<a href="javascript:;" onclick="deleteFunction()" class="btn btn-danger">삭제</a>

											</div>
										</td>
									</c:when>
									<c:when test="${adchar.proLeader eq 'Y'}">
										<td style="text-align: center;">

											<button type="button" class="btn btn-secondary" data-bs-toggle="modal"
												data-bs-target="#smallModalLeaderChange">
												변경
											</button>
										</td>
									</c:when>
								</c:choose>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>

				<tfoot>
					<tr>
						<td colspan="9">
							${paging.pagingHTML }
							<div id="searchUI" class="row g-3 d-flex justify-content-center">
								<div class="col-auto">
									<form:select path="simpleCondition.searchType" class="form-select">
										<form:option label="전체" value="" />
										<form:option label="이름" value="title" />
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
<div class="modal fade" id="smallModalLeaderChange" tabindex="-1" style="display: none;" aria-hidden="true">
	<form id="byPmem" action="/adminproject/pmemUpdate" method="PUT">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel2">리더 변경</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div>
						<div class="col mb-0 wd100 mt-2">
							<label for="nameSmall" class="form-label">리더명</label>
							<input type="text" id="leaderName" class="form-control" placeholder="" readonly>
						</div>
						<div class="col mb-0 wd100 mt-2">
							<label for="nameSmall" class="form-label">리더사번코드</label>

							<input type="text" id="leaderNameCd" class="form-control" placeholder="" readonly>
						</div>
						<div class="col mb-0 wd100 mt-2">
							<label for="nameSmall" class="form-label">변경할 리더사원</label><br>
							<select id="changeLeader">
								<c:forEach items="${pmemAdminList }" var="projmem">
									<option value="${projmem.emp.empCd}">
										${projmem.emp.empName } (${projmem.emp.empCd})
									</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-primary">수정하기</button>
				</div>
			</div>
		</div>
		<security:csrfInput />
	</form>
</div>


<!-- 히든태그 사용 -->
<form:form modelAttribute="simpleCondition" method="get" id="searchForm" class="border">
	<form:hidden path="searchType" readonly="readonly" placeholder="searchType" />
	<form:hidden path="searchWord" readonly="readonly" placeholder="searchWord" />
	<input type="hidden" name="page" readonly="readonly" placeholder="page" />
</form:form>

<script>

//상세보기로 가기
function fn_goDetail(){
	
	var proSn = document.querySelector('input[name="proSn"]').value;
	location.href = "/adminprojectDetail/"+proSn;
}

//목록으로 가기 버튼
function fn_goback(){
	
	location.href = "/adminproject";
}


const byPmemOrg = document.querySelector("#byPmemOrg");

byPmemOrg.addEventListener("submit",()=>{
	event.preventDefault(); 
	
	 var proSn = document.querySelector('input[name="proSn"]').value;

	 const pmemberList = []; 
	 const pMems = byPmemOrg.querySelectorAll("[name=pMember]");
	 console.log("체크:",pMems); 
	 console.log("프로젝트 번호 확인!",proSn);

	 for(let i=0; i< pMems.length; i++){
		let pMemVO = {
			empCd: pMems[i].value
		}
		pmemberList.push(pMemVO);
	 }
 
	// 개발모드에서는 항상 필요한 데이타가 보이도록 프로그램
	let projVO = {
		pmemberList:pmemberList,
		proSn:proSn 
	} 
     
	console.log("projVO",projVO);

	$.ajax({
		type:"post",
		url:"/adminpmember", 
		contentType:"application/json",
		data: JSON.stringify(projVO),
		dataType:"text",
		success:function(resp){
			console.log("멤버확인:",resp);
			Swal.fire({
	            title: '멤버가 정상적으로 추가되었습니다.',
	            icon: 'success',
	            showCancelButton: false,
	            confirmButtonColor: '#3085d6',
	            confirmButtonText: '확인'
	        }).then((result) => {
	            if (result.isConfirmed) {
	                location.reload();
	            }
	        });
// 			alert("멤버가 정상적으로 추가되었습니다.");
// 			location.reload();     
		}, 
		error:function(xhr){
			console.log("Error:",xhr.status);
		}
	})


});



$(document).on('click', '.btn-secondary', function() {
    var leaderNameCd = $(this).closest('tr').find('td:nth-child(2)').text().trim();
    var leaderName = $(this).closest('tr').find('td:nth-child(3)').text().trim();
 
    console.log('Leader Name:', leaderName);

    // 모달에 데이터 설정
    $('#leaderName').val(leaderName);
    $('#leaderNameCd').val(leaderNameCd);

    // 현재 리더의 이름과 사번을 select 요소의 기본값으로 설정
    $('#changeLeader').val(leaderNameCd); // 현재 리더의 사번으로 선택
});  
  


function deleteFunction(event) {
    var empCd = event.target.parentElement.querySelector('input[name="empCd"]').value;
    var proSn = event.target.parentElement.querySelector('input[name="proSn"]').value;

    if (confirm("정말로 삭제하시겠습니까?")) {
        let pmemberVO = {
            empCd: empCd,
            proSn: proSn
        } 

        console.log("pmemdel==={}", pmemberVO);

        $.ajax({
            type: "DELETE",
            url: "/adminproject/{proSn}",
            data: JSON.stringify(pmemberVO),
            contentType: 'application/json; charset=utf-8',
            dataType: "text",
            success: function (resp) {
            	Swal.fire({
    	            title: '프로젝트 멤버가 정상적으로 삭제되었습니다.',
    	            icon: 'success',
    	            showCancelButton: false,
    	            confirmButtonColor: '#3085d6',
    	            confirmButtonText: '확인'
    	        }).then((result) => {
    	            if (result.isConfirmed) {
    	                location.reload();
    	            }
    	        }); 
//                 location.reload();
//                 alert("프로젝트 멤버가 정상적으로 삭제되었습니다."); 
            },
            error: (xhr) => {
                console.log(xhr.status);
            }
        })
    } else {
        // 사용자가 취소를 선택한 경우
        return false;
    }
}
 
var deleteButtons = document.querySelectorAll('.btn.btn-danger');
deleteButtons.forEach(function (button) {
    button.addEventListener('click', deleteFunction);
});


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
	
	
	
	
 	// 저장 버튼 클릭 이벤트 처리
	const byPmem = document.querySelector("#byPmem");

byPmem.addEventListener("submit", (event) => {
    event.preventDefault();

    // 선택된 프로젝트 정보 가져오기
    var proSn = document.querySelector('input[name="proSn"]').value;
    var changeLeader = $('#changeLeader').val();
    console.log("데이터 형식{}", proSn);
    console.log("데이터 형식{}", changeLeader);
    

   	
   	const pmem = [];

		let pmemVO = { 
				proSn: proSn,
	            empCd: changeLeader
		};
		
		console.log("pmemVO:{}",pmemVO);

  
   
    
   
   // 변경된  프로젝트 정보를 proSn를 가지고 서버에 저장 요청을 보냄 
   $.ajax({
       type: 'PUT', 
       url: '/adminproject/pmemUpdate',  
       contentType:"application/json",
       data: JSON.stringify(pmemVO),  
       dataType: 'text',  
       success: function(response) {
           console.log('프로젝트리더 변경 성공');
           Swal.fire({
	            title: '프로젝트 리더가 성공적으로 바뀌었습니다.',
	            icon: 'success',
	            showCancelButton: false,
	            confirmButtonColor: '#3085d6',
	            confirmButtonText: '확인'
	        }).then((result) => {
	            if (result.isConfirmed) {
	                location.reload();
	            }
	        }); 
//            alert("프로젝트 리더가 성공적으로 바뀌었습니다.");    
//            $('#smallModalLeaderChange').modal('hide');  
//            location.reload();     
       }, 
       error: function(xhr, status, error) {
           console.error('프로젝트리더 변경 실패:', error); 
       }
   });
}); 

	
	 


	
	 

</script>
<script src="${pageContext.request.contextPath }/resources/js/app/adproject/pmemberOrg.js"></script>     