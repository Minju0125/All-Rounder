<%--
* [[개정이력(Modification Information)]]
* 수정일          수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 22. 전수진      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script src="${pageContext.request.contextPath }/resources/js/ckeditor/ckeditor.js"></script>
<style>
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

<div class="card">
	<div class="card-header border-bottom">
		<h4 class="card-title">수신문서</h4>
		<div class="d-flex justify-content-between align-items-center row py-3 gap-3 gap-md-0">
			<div class="col-md-4 user_role"></div>
			<div class="col-md-4 user_plan"></div>
			<div class="col-md-4 user_status"></div>
		</div>
	
		<div class="dt-action-buttons text-xl-end text-lg-start text-md-end text-start d-flex align-items-center justify-content-end flex-md-row flex-column mb-3 mb-md-0">
			<div class="dt-buttons">

			</div>
		</div>
		<br>
	
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
				<tbody class="table-border-bottom-0" id="trBody">
				</tbody>
				<tfoot>
				<tr>
					<td colspan="7">
						<span id="pagingArea"></span>
						<div id="searchUI"  class="row g-3 d-flex justify-content-center">
							<div class="col-auto">
								<select name="formNo" class="form-select">
									<option value>결재양식</option>
									<c:forEach items="${formlist }" var="form">
										<option label="${form.formNm }" value="${form.formNo }" />
									</c:forEach>
								</select>
							</div>
							<div class="col-auto">
								<select name="drafterDeptCd" class="form-select">
									<option value>부서</option>
									<c:forEach items="${deptlist }" var="dept">
										<option label="${dept.deptName }" value="${dept.deptCd }" />
									</c:forEach>
								</select>
							</div>
							<div class="col-auto">
								<input type="text" name="sanctnSj" placeholder="제목" class="form-control"/>
							</div>
							<div class="col-auto">
								<input type="button" value="검색" id="searchBtn" class="btn btn-primary"/>
							</div>
						</div>
					</td>
				</tr>
			</tfoot>
			</table>
		</div> 
	</div>
</div>

<form id = "searchForm" class="boader" action="<c:url value='/sanction/rcyerData' />">
	<input type="hidden" name="formNo" readonly="readonly" placeholder="formNo" />
	<input type="hidden" name="drafterDeptCd" readonly="readonly" placeholder="drafterDeptCd"  />
	<input type="hidden" name="sanctnSj" readonly="readonly" placeholder="sanctnSj"  />
	<input type="hidden" name="page" readonly="readonly" placeholder="page" />
</form>  
<script>

$(searchForm).on("submit", function(event){
	event.preventDefault();
	let cPath = document.body.dataset.contextPath;
	let url = this.action;
	let data = $(this).serialize();
	console.log("url",url);
	console.log("data",data);
	
	$.getJSON(`\${url}?\${data}`)
		.done(function(resp){
			let dataList = resp.paging.dataList;
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
				$(pagingArea).html(resp.paging.pagingHTML);
			} else {
				trTag += `
					<tr>
						<td colspan = '7'>검색 조건에 맞는 리스트 없음</td>
					</tr>
				`;
				$(pagingArea).empty();
			}
			$("#trBody").html(trTag);
		}); 
		
}).submit();

function  fn_paging(page){
	searchForm.page.value = page;
	searchForm.requestSubmit();
	searchForm.page.value = "";
}

$(searchUI).on("click", "#searchBtn", function(event){
	let inputs = $(this).parents("#searchUI").find(":input[name]");
	console.log(inputs);
	$.each(inputs, function(idx, ipt){
		let name = ipt.name;
		let value = $(ipt).val();
		
		
		$(searchForm).find(`:input[name=\${name}]`).val(value);
	});
		$(searchForm).submit();
});


</script>


