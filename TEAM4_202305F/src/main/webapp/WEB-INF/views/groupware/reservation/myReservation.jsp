<%--
* [[개정이력(Modification Information)]]
* 수정일       		수정자        수정내용
* ---------- 		---------  -----------------
* 2023. 11. 30.     박민주        최초작성
* Copyright (c) 2023 by DDIT All right reserved
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<h4 class="py-3 mb-4"><span class="text-muted fw-light">자원예약 /</span> 내 예약보기</h4>
<br>
<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
	<div class="layout-container">
		<!-- Layout container -->
		<div class="layout-page">
			<!-- Content wrapper -->
			<div>
				<!-- Content -->
				<!-- navbar -->

				<hr />
				<div class="row mb-5">

					<!-- 등록된 차량 목록 -->
					<div class="col-md-7 col-lg-7">
						<div class="card  mb-3" style="height: 700px">
							<div class="card-body">
								<h5 class="card-title">차량 예약 내역 목록</h5>
								<hr />
								<div class="card-datatable table-responsive">
									<table class="table" data-aria="vhcle">
										<thead class="table-light">
											<tr>
												<th>사용날짜</th>
												<th>사용시간</th>
												<th>차량모델</th>
												<th>차량번호</th>
												<th>사용목적</th>
												<th>상태</th>
											</tr>
										</thead>
										<tbody id="vhcleTbody" class="table-border-bottom-0">
										</tbody>
									</table>
								</div>
								<div class="fixed-bottom"
									style="background-color: #f8f9fa; padding: 10px; width: 100%; z-index:2; position: absolute; bottom: 0;">
									<table style="margin: auto;">
										<tfoot id="vhcleFooter">
										</tfoot>
									</table>
								</div>
							</div>
						</div>
					</div>

					<!-- 등록된 회의실 목록 -->
					<div class="col-md-5 col-lg-5">
						<div class="card mb-3" style="height: 700px">
							<div class="card-body" style="position: relative;">
								<h5 class="card-title">회의실 예약 내역 목록</h5>
								<hr />
								<div class="card-datatable table-responsive">
									<table class="table" data-aria="conf">
										<thead class="table-light">
											<tr>
												<th>사용날짜</th>
												<th>사용시간</th>
												<th>회의실명</th>
												<th>상태</th>
											</tr>
										</thead>
										<tbody class="table-border-bottom-0" id="confTbody">
											<!-- 여기에 예약 내역이 들어갈 자리 -->
										</tbody>
									</table>
								</div>
								<div class="fixed-bottom"
									style="background-color: #f8f9fa; padding: 10px; width: 100%; position: absolute; z-index:2; bottom: 0;">
									<table style="margin: auto;">
										<tfoot id="confFooter">

										</tfoot>
									</table>
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

<!-- 히든태그 사용 -->
<form:form modelAttribute="simpleCondition" method="get" id="searchForm"
	class="border">
	<input type="hidden" name="page" readonly="readonly" placeholder="page" />
</form:form>

<script>

function makeTbodyConf(dataList){
	let tbodyTagConf = "";
	console.log("dataList ===> " + dataList);
	if(!dataList || dataList.length==0){
		tbodyTagConf += `
				<tr>
					<td colspan="4">예약내역 없음</td>
				</tr>
		`;
	}else{
		for(var idx=0; idx<dataList.length; idx++){
			console.log(dataList[idx]);
			tbodyTagConf += `
				<tr id="\${dataList[idx].confReserveCd}" data-password="\${dataList[idx].confReservePw}">
					<td>\${dataList[idx].confDate}</td>
					<td>\${dataList[idx].confTime}</td>
					<td>\${dataList[idx].confRoom.confRoomNm}</td>
					<td class="cStatus"></td>
				</tr>
			`;
		}
	}
	return tbodyTagConf;
}

function makeTbodyVhcle(dataList){
	let tbodyTagVhcle= "";
	console.log("VdataList ===> " + dataList);
	if(!dataList || dataList.length==0){
		tbodyTagVhcle += `
				<tr>
					<td colspan="6">예약내역 없음</td>
				</tr>
		`;
	}else{
		for(var idx=0; idx<dataList.length; idx++){
			console.log(dataList[idx]);
			tbodyTagVhcle += `
				<tr id="\${dataList[idx].vhcleReserveCd}" data-password="\${dataList[idx].vhcleReservePw}">
					<td>\${dataList[idx].vhcleUseDate}</td>
					<td>\${dataList[idx].vhcleUseTime}</td>
					<td>\${dataList[idx].vhcle.vhcleModel ? dataList[idx].vhcle.vhcleModel : '삭제차량'}</td>
					<td>\${dataList[idx].vhcle.vhcleNo  ? dataList[idx].vhcle.vhcleNo : '삭제차량'}</td>
					<td>\${dataList[idx].vhcleReservePur}</td>
					<td class="vStatus"></td>
				</tr>
			`;
		}
	}
	return tbodyTagVhcle;
} 

//예약상태 표시 (회의실)
function setConfStatus(statusMapList){
	for(var i=0; i<statusMapList.length; i++){
		var ccd = statusMapList[i].confReserveCd;
		var cst = statusMapList[i].status;
		console.log(ccd + ',' + cst);
		console.log($(`tr#\${ccd} td.cStatus`));
		
		let badgeTag = ``;
		if(cst=="사용완료"){
			badgeTag = `
				<span class="badge rounded-pill bg-secondary">사용완료</span>
			`;
		}else if(cst=="사용중"){
			badgeTag = `
				<span class="badge rounded-pill bg-success">사용중</span>
			`;
		}else{ //사용대기
			badgeTag = `
				<span class="badge rounded-pill bg-info">사용대기</span>
			`;
		}
		badgeTag += `
			<span id="\${ccd}" style="cursor:pointer" onclick="reserveDelete('\${ccd}')"><i class='bx bxs-x-circle'></i></span>
		`;
		$(`tr#\${ccd} td.cStatus`).html(badgeTag);
	}
}


function setDataListConf(pageC){
	$.ajax({
		type : "GET",
		url : `/myReservation/getDataListConf/${pageC}`,
		data: { pageC: pageC },
		contentType: "application/json; charset=UTF-8",
		success : function(resp){
			console.log("resp ? ==> " +resp);
			let pagingHTML = $(resp.paging.pagingHTML);
			let dataList = resp.paging.dataList;
			let makeTbodyConfResult = makeTbodyConf(dataList);
						
            $("#confFooter").empty();
            $("#confTbody").empty();
			
			$("#confFooter").append(pagingHTML);
			$("#confTbody").append(makeTbodyConfResult);
			
			setConfStatus(resp.paging.variousCondition.statusMapList);
		},
		error : function(xhr){
			console.log("페이징 실패 ==> " + xhr);
		}
	})
}

//예약 내역 상태 표시 (차량)
function setVhcleStatus(statusMapList){
	for(var i=0; i<statusMapList.length; i++){
		var vcd = statusMapList[i].vhcleReserveCd;
		var vst = statusMapList[i].status;
		console.log(vcd + ',' + vst);
		console.log(statusMapList[i]);
		console.log($(`tr#\${vcd} td.vStatus`));
		
		let badgeTag = ``;
		if(vst=="사용완료"){
			badgeTag = `
				<span class="badge rounded-pill bg-secondary">사용완료</span>
			`;
		}else if(vst=="사용중"){
			badgeTag = `
				<span class="badge rounded-pill bg-success">사용중</span>
			`;
		}else{ //사용대기
			badgeTag = `
				<span class="badge rounded-pill bg-info">사용대기</span>
			`;
		}
		badgeTag += `
			<span id="\${vcd}" style="cursor:pointer" onclick="reserveDelete('\${vcd}')"><i class='bx bxs-x-circle'></i></span>
		`;
		$(`tr#\${vcd} td.vStatus`).html(badgeTag);
	}
}

function reserveDelete(reservationCd) {
	let reserveCd = reservationCd;
    let dataPw = $(`tr[id=\${reservationCd}]`).data('password');
    Swal.fire({
        title: '예약시 입력한 비밀번호를 입력하세요.',
        input: 'password',
        showCancelButton: true,
        cancelButtonColor: '#d33',
        cancelButtonText: '취소',
    }).then((result) => {
    	console.log(result);
    	if(!result.isDismissed==true){ //취소가 아닌 경우
    		if (result.value == dataPw) {
                $.ajax({
                    type: "DELETE",
                    url: `/myReservation/deleteReservation/\${reserveCd}`,
                    contentType: "application/json; charset=UTF-8",
                    success: function (resp) {
                       if(resp.success=='OK'){
                    	   Swal.fire("삭제되었습니다.","","success").then(function(){
	                    	   location.reload();
                    	   });
                       }
                    },
                    error: function (xhr) {
                        Swal.fire("삭제 실패", "", "error");
                    }
                })
            }else{
            	Swal.fire("비밀번호가 틀렸습니다.", "", "error");
            }
    	}
    })
}

function setDataListVhcle(pageV){
	$.ajax({
		type : "GET",		
		url : `/myReservation/getDataListVhcle/${pageV}`,
		data: { pageV: pageV }, 
		contentType: "application/json; charset=UTF-8",
		success : function(resp){
			console.log("resp ? ==> " +resp);
			
			let pagingHTMLV = $(resp.paging.pagingHTML);
			let dataListV = resp.paging.dataList;
			let makeTbodyConfResultV = makeTbodyVhcle(dataListV);
						
            $("#vhcleFooter").empty();
            $("#vhcleTbody").empty();
			
			$("#vhcleFooter").append(pagingHTMLV);
			$("#vhcleTbody").append(makeTbodyConfResultV);

			setVhcleStatus(resp.paging.variousCondition.statusMapList);
			
		},
		error : function(xhr){
			console.log("페이징 실패 ==> " + xhr);
		}
	})
}

$(function(){
	setDataListConf(1);
	setDataListVhcle(1);
})

$(document).on("click", ".page-link", function(e) {
    e.preventDefault();
});

function fn_pagingV(page) {
        setDataListVhcle(page);
}

function fn_pagingC(page) {
        setDataListConf(page);
}
	
</script>
<script>
	var __basePath = './';
</script>
<script
	src="https://cdn.jsdelivr.net/npm/ag-grid-community@30.2.1/dist/ag-grid-community.min.js"></script>
