<%--
* [[개정이력(Modification Information)]]
* 수정일          수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 22. 전수진      최초작성
* 2023. 11. 25. 전수진      결재승인, 반려 처리
* 2023. 11. 27. 전수진      결재Table 추가, 서명이미지 처리
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<script src="${pageContext.request.contextPath }/resources/js/ckeditor/ckeditor.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>


<style>
	.card-header {
		text-align: center;
		font-weight: 800;
		background-color: #fff;
		margin-top : 100px;
	}
	.first {
		height: 450px;
	}
	.second {
		height: 300px;
		overflow : auto;
	}
	
	table {
	    border-collapse: collapse;
	    height: 40px;
	    width: 100%;
	}
	td {
	    border-color: black;
	    border-style: solid;
	    border-width: 1px;
	    height: 40px;
	}
	
	.title-cell {
	    background-color: #e2e2e2;
	    text-align: center;
	    width: 15%;
	}
	strong span {
	    font-size: 12px;
	}
	.error {
	    color: red;
	}
	
	#ApprovalOpnion, #RejectedOpnion{
		width: 100%;
	    height: 6.25em;
	    resize: none;
	}
	.tableAreaContainer {
        display: flex;
        justify-content: flex-end;
        margin-top:80px;
    }
	
	.dateArea {
		text-align : center;
		font-size : 11px;
		width : 80px;
	}
	.rankArea {
		text-align : center;
		width : 80px;
	}
	.singArea{
		text-align:center; 
		width : 80px;
		height : 100px;
		
	}
	.tableArea {
		display: flex;
	}

	.signTitle {
		width : 25px;
		background-color: #e2e2e2;
	    text-align: center;
	}
	.signTable1 {
		width:100px;
		margin-right: 5px;
	}
	
	#signTableArea {
		margin-bottom: 20px;
	}
	
	.singAreaInner {
		font-size : 11px;
	}
	
	img {
		width : 50px;
		height : 50px;
	}
</style>

<div class="content-wrapper">
<!-- Content -->
	<div class="container-xxl flex-grow-1 container-p-y">
	<%-- 로그인한 사람에 따라 버튼에 대한 처리 다르게 --%>
		<div id="buttonArea" class="mb-3">
			<sec:authorize access="isAuthenticated()">
				<sec:authentication property="principal" var="principal"/>
				<c:set var="loginCd" value="${principal.realUser.empCd }" />
				<c:set var="drafterCd" value="${sanction.drafter }" />
					<c:if test="${loginCd eq drafterCd}">
					    <c:set value="false" var="btnFlag"/>
						<button class="btn btn-sm btn-outline-primary" onclick="modifyFunction()" id="editBtn">수정</button> 
						<button class="btn btn-sm btn-outline-primary" onclick="deleteFunction()" id="deleteBtn">삭제</button>
						<button class="btn btn-sm btn-outline-secondary" onclick="location.href='/sanction/drafter'" >목록</button>
					</c:if>
					<c:forEach var="sanctnerCd" items="${sanction.lineList }">
						<c:if test="${loginCd eq sanctnerCd.realSanctner && sanctnerCd.sanctnlineSttus eq 'I' }">
							<c:set value="0" var="order"/>
							<c:set value="true" var="btnFlag"/>
							<c:forEach var="sanctnerCd" items="${sanction.lineList }" varStatus="stat">
								<c:if test="${loginCd eq sanctnerCd.realSanctner}">
									<c:set value="${stat.index }" var="order"/>
								</c:if>
							</c:forEach>
							<c:forEach var="sanctnerCd" items="${sanction.lineList }" varStatus="stat">
								<c:choose>
									<c:when test="${order eq 0 }">
										<c:if test="${sanctnerCd.sanctnlineSttus eq 'I' }"><c:set value="true" var="btnFlag"/></c:if>
									</c:when>
									<c:when test="${order eq 1 }">
										<c:if test="${sanction.lineList[0].sanctnlineSttus eq 'I' }"><c:set value="false" var="btnFlag"/></c:if>
									</c:when>
									<c:when test="${order eq 2 }">
										<c:if test="${sanction.lineList[1].sanctnlineSttus eq 'I' }"><c:set value="false" var="btnFlag"/></c:if>
									</c:when>
								</c:choose>
							</c:forEach>
						</c:if>
					</c:forEach>
					
					<c:if test="${btnFlag }">
						<button type="button" class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalApproval" id="approvalBtn">승인</button>
						<button type="button" class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#modalApproval" id="rejectedBtn">반려</button>
					</c:if>
					<c:if test="${loginCd != drafterCd}">
						<button class="btn btn-sm btn-outline-secondary" onClick="history.back()">목록</button>
					</c:if>
					<c:if test="${sanction.sanctnSttus eq 4 }">
						<button class="btn btn-sm btn-outline-secondary" id="pdfBtn">pdf변환</button>
					</c:if>
			</sec:authorize>		
		</div>	
		<div class="row">
		<!-- User Content -->
			<div class="col-xl-8 col-lg-7 col-md-7 order-1 order-md-0">
			<!-- pdf변환영역 시작 -->
			<div id="areaContainer">
				<div class="card mb-4">
					<div class="card-body row g-3">
					<%-- 여기에 승인했을때 결재선테이블 출력예정 --%>
					<div class="tableAreaContainer">
						<div class="tableArea">
							<div>
								<table class="signTable1" border="1">
									<tbody>
										<tr>
											<td class="signTitle" rowspan="3">
												<strong><span>기</span></strong>
												<p></p>
												<strong><span>안</span></strong>
											</td>
											<td class="rankArea"><strong><span>${sanction.drafterRankNm }</span></strong></td>
										</tr>
										<tr>								
											<td class="singArea"><span class="singAreaInner"><img src="${sanction.drafterSignImg }"><br/>${sanction.drafterNm }</span></td>
										</tr>
										<tr>								
											<td class="dateArea">${sanction.sanctnDate }</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div id= "inputTableArea">
								<table class="signTable2" border="1">
									<tbody>
										<c:if test="${not empty sanction.lineList}">
											<tr>
												<td class="signTitle" rowspan="3">
													<strong><span>승</span></strong>
													<p></p>
													<strong><span>인</span></strong>
												</td>
											<c:forEach var="sanctionLineVO" items="${sanction.lineList}">
												<td class="rankArea"><strong><span>${sanctionLineVO.realSanctnerRankNm }</span></strong></td>
											</c:forEach>
											</tr>
											<tr>
											<c:forEach var="sanctionLineVO" items="${sanction.lineList}">
												<td class="singArea">
												<c:if test="${not empty sanctionLineVO.signImg }">
													<img src="${sanctionLineVO.signImg }"><br/>
												</c:if>
												<span class="singAreaInner">${sanctionLineVO.realSanctnerNm }</span></td>
											</c:forEach>
											</tr>
											<tr>
											<c:forEach var="sanctionLineVO" items="${sanction.lineList}">
												<td class="dateArea">${sanctionLineVO.signDate }</td>
											</c:forEach>
											</tr>
										</c:if>
									</tbody>
								</table>
							</div>		
						</div>
					</div>
						<h2 class="card-header">${sanction.sanctionForm.formNm }</h2>
							<div class="table-responsive mb-3">
								<table>
									<tbody>
										<tr>
											<td class="title-cell">
												<strong><span>제목</span></strong>
											</td>
											<td>
												${sanction.sanctnSj }
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>작성일자</span></strong>
											</td>
											<td>
												${sanction.sanctnDate }
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>작성부서</span></strong>
											</td>
											<td>
												${sanction.drafterDeptName }
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>작성자</span></strong>
											</td>
											<td>
												${sanction.drafterNm }
											</td>
										</tr>
										<tr>
											<td class="title-cell">
												<strong><span>수신자</span></strong>
											</td>
											<td>
											<c:if test="${not empty sanction.sanctnRcyer }">
												[${sanction.sanctnRcyerDeptName }] ${sanction.sanctnRcyerNm } ${sanction.sanctnRcyerRankNm }
											</c:if>
											</td>
										</tr>
									</tbody>
								</table>
								<br />
								<div class="mb-3">
									${sanction.sanctnSourc }
								</div>
						</div>
					</div>
				</div>
			</div>
			<!-- pdf변환영역 끝 -->
			</div>
			<div class="col-xl-4 col-lg-5 col-md-5 order-0 order-md-1">
				<div class="card mb-4">
					<div class="card-body first">
						<div class="d-flex justify-content-center pt-3">
						</div>
						<h5 class="pb-2 border-bottom mb-4">결재선</h5>
						<p>[${sanction.drafterDeptName }] ${sanction.drafterNm } ${sanction.drafterRankNm } <span class="badge rounded-pill bg-label-info">기안</span></p>
						<form>
							<div class="info-container" id="sanctionLine">
								<!-- 
								sanction.lineList : List<SanctionLineVO>
								 -->
								<c:if test="${not empty sanction.lineList}">
									<c:forEach var="sanctionLineVO" items="${sanction.lineList}">
								        <p>[${sanctionLineVO.realSanctnerDeptName}] ${sanctionLineVO.realSanctnerNm} ${sanctionLineVO.realSanctnerRankNm} 
										<c:choose>
											<c:when test="${sanctionLineVO.sanctnlineSttus eq 'I'}">
												<span class="badge rounded-pill bg-label-secondary">미결</span>
											</c:when>
											<c:when test="${sanctionLineVO.sanctnlineSttus eq 'C'}">
												<span class="badge rounded-pill bg-label-info">승인</span>
											</c:when>
											<c:when test="${sanctionLineVO.sanctnlineSttus eq 'P'}">
												<span class="badge rounded-pill bg-label-info">대결</span>
											</c:when>
											<c:when test="${sanctionLineVO.sanctnlineSttus eq 'R'}">
												<span class="badge rounded-pill bg-label-danger">반려</span>
											</c:when>
										</c:choose>
											<br/>
											${sanctionLineVO.sanctnOpinion}
										</p>
									</c:forEach>
								</c:if>
							</div>
						</form> 
					</div>
				</div>
				<div class="card mb-4">
					<div class="card-body second">
						<div class="d-flex justify-content-center pt-3">
						</div>
						<h5 class="pb-2 border-bottom mb-4">첨부파일</h5>
						<div class="info-container" id="fileList">
							<c:if test="${not empty sanction.attachList }">
								<c:forEach items="${sanction.attachList }" var="attach">
									<p class="mb-4">
									<c:url value="/sanction/${sanction.sanctnNo }/sanctionAttach/${attach.attachNo }" var="downloadUrl" />
									<a href="${downloadUrl }" title="${attach.attachFancysize }">${attach.attachOriginNm }
									</a>
									</p>	
								</c:forEach>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="modalApproval" tabindex="-1" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="modalTitle">결재승인</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close">
				</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col mb-3">
						<div>
						<input type="hidden" id="sanctnlineSttus" name="sanctnlineSttus">
						<input type="hidden" id="titleValue" name="titleValue">
						</div>
						<label for="nameWithTitle" class="form-label">결재의견</label>
						<textarea
						id="sanctnOpinion"
						class="form-control"
						placeholder="결재의견을 입력해주세요."></textarea>
					</div>
				</div>
				<div class="row g-2">
					<div class="col mb-0">
						<button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">취소</button>
						<button type="button" class="btn btn-primary" id="saveBtn">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>


<script>
 	function modifyFunction(){
 		var sttus = ${sanction.sanctnSttus};
 		
 		if(sttus=="0" || sttus=="1") {
	 		console.log("요거?",sttus);
 			location.href=`/sanction/${sanction.sanctnNo}/edit`;
 		} else {
			Swal.fire({
				icon: "error",
				title: "수정실패!",
				text: "현재문서의 결재가 진행중 입니다!",
				showConfirmButton: false,
				timer: 1500
			});
	 		
 		} 
 	}

	function deleteFunction(){
		Swal.fire({
			  title: "정말로 삭제하시겠습니까?",
			  icon: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#3085d6",
			  cancelButtonColor: "#d33",
			  cancelButtonText: "취소",
			  confirmButtonText: "확인"
	    }).then((result) => {
	        if (result.isConfirmed) {
	            $.ajax({
	                method: "delete",
	                url: `/sanction/${sanctnNo}`,
	                success: function (resp) {
	                    console.log("서버에서 돌아온 값", resp);
	                    if (resp.msg == "OK") {
	                        Swal.fire({
	                            icon: "success",
	                            title: "삭제성공!",
	                            text: "결재문서가 삭제되었습니다!",
	                            showConfirmButton: false,
	                            timer: 1500
	                        }).then((result) => {
		                        location.href = `/sanction/drafter`;
	                        });
	                    } else if (resp.msg == "CANNOTPROCEED") {
	                        Swal.fire({
	                            icon: "error",
	                            title: "삭제실패!",
	                            text: "현재문서의 결재가 진행중 입니다!",
	                            showConfirmButton: false,
	                            timer: 1500
	                        });
	                    } else {
	                        Swal.fire({
	                            icon: "error",
	                            text: "결재문서가 삭제에 실패하였습니다!",
	                            showConfirmButton: false,
	                            timer: 1500
	                        });
	                    }
	                },
	                error: (xhr) => {
	                    console.log(xhr.status);
	                }
	            });
	        }
	    });
	}
	
	// 승인 모달 내용 변경
	function setApprovalModalContent() {
	    $('#modalTitle').text('결재승인');
	    $('#sanctnlineSttus').val('C');
	    $('#titleValue').val('결재승인');
	    // 기타 필요한 변경 작업 수행
	}

	// 반려 모달 내용 변경
	function setRejectedModalContent() {
	    $('#modalTitle').text('결재반려');
	    $('#sanctnlineSttus').val('R');
	    $('#titleValue').val('결재반려');
	    // 기타 필요한 변경 작업 수행
	}

	// 승인 버튼 클릭 시
	$('#approvalBtn').on('click', function() {
	    // 승인 처리
	    setApprovalModalContent();
	    // 기타 필요한 작업 수행
	});
	
	// 반려 버튼 클릭 시
	$('#rejectedBtn').on('click', function() {
	    // 승인 처리
	    setRejectedModalContent();
	    // 기타 필요한 작업 수행
	});
	
	// 모달이 닫힐때 내용비우기
	$('#modalApproval').on('hidden.bs.modal', function(e) {
		$('#sanctnOpinion').val('');
	});

	// 결재의견 표시하기
	$("#saveBtn").on("click", function(){
	    let sanctnlineSttus = $("#sanctnlineSttus").val(); 
	    let sanctnOpinion = $("#sanctnOpinion").val(); 
	    let modalTitle = $("#titleValue").val();


	    let sanctionLineVO = {
	        sanctnlineSttus: sanctnlineSttus,
	        sanctnOpinion: sanctnOpinion
	    };

	    $.ajax({
	    	method: "put",  
	        url: `/sanction/${sanctnNo}/lineEdit`,  
	        contentType: "application/json; charset=utf-8",  
	        data: JSON.stringify(sanctionLineVO),  
	        success: function(resp) {
	        	$('#modalApproval').modal('hide');
	            if (resp.msg == "OK") {
				    Swal.fire({
						icon: "success",
						title: modalTitle,
						text: modalTitle+" 처리가 완료되었습니다!",
						showConfirmButton: false,
						timer: 1500
				    });

					updateHTML(resp.updateSanction);
					updateTable(resp.updateSanction);
				    var newButton = `<button class="btn btn-sm btn-outline-secondary" onClick="history.back()" >목록</button>`;
				    $("#buttonArea").html(newButton);
				    
	            } else {
					Swal.fire({
						icon: "error",
						title: "결재실패!",
						text: modalTitle+" 처리가 실패되었습니다!",
						showConfirmButton: false,
						timer: 1500
					});
	            }
	        },
			error: (xhr) => {
				console.log(xhr.status);
				
			}
	    });
	});
	
	function updateHTML(updateSanction) {
	    let updateList = updateSanction.lineList;
	    console.log(updateList);

	    var newSanctionLines = "";
	    for (var i = 0; i < updateList.length; i++) {
	        newSanctionLines += `<p>[\${updateList[i].realSanctnerDeptName}] \${updateList[i].realSanctnerNm} \${updateList[i].realSanctnerRankNm}`;
	        switch (updateList[i].sanctnlineSttus) {
	            case 'I':
	                newSanctionLines += '<span class="badge rounded-pill bg-label-secondary">미결</span>';
	                break;
	            case 'C':
	                newSanctionLines += '<span class="badge rounded-pill bg-label-info">승인</span>';
	                break;
	            case 'P':
	                newSanctionLines += '<span class="badge rounded-pill bg-label-info">대결</span>';
	                break;
	            case 'R':
	                newSanctionLines += '<span class="badge rounded-pill bg-label-danger">반려</span>';
	                break;
	        }
	        if(updateList[i].sanctnOpinion!=null)
	        	newSanctionLines += `<br/>\${updateList[i].sanctnOpinion}</p>`; 
	    }
	    $('#sanctionLine').html(newSanctionLines);
	    
	}
	
	function updateTable(updateSanction) {
	    let updateList = updateSanction.lineList;

	    var newRank = "";
	    var newSign = "";
	    var newDate = "";
	    for (var i = 0; i < updateList.length; i++) {
	        newRank += '<td class="rankArea"><strong><span>' + updateList[i].realSanctnerRankNm + '</span></strong></td>';
	        newSign += '<td class="singArea">' + (updateList[i].signImg ? '<img src="' + updateList[i].signImg + '"><br/>' : '') + '<span class="singAreaInner">' + updateList[i].realSanctnerNm + '</span></td>';
	        newDate += '<td class="dateArea">' + (updateList[i].signDate ? updateList[i].signDate : '') + '</td>';
	    }
	    
	    $('#inputTableArea').html(
	        '<table class="signTable2" border="1"><tbody><tr><td class="signTitle" rowspan="3"><strong><span>승</span></strong><strong><span>인</span></strong></td>' +
	        newRank + '</tr><tr>' + newSign + '</tr><tr>' + newDate +
	        '</tr></tbody></table>'
	    );
	}
	
    let jsPDF = jspdf.jsPDF;
    //pdf download
    $("#pdfBtn").on("click", function() {
        html2canvas($('#areaContainer')[0]).then(function(canvas) {
               // 캔버스를 이미지로 변환 
               var imgData = canvas.toDataURL('image/png');
               var imgWidth = 190;
               var pageHeight = 295; 
               var imgHeight = parseInt(canvas.height * imgWidth / canvas.width); // 소숫점 귀찮앙
               var heightLeft = imgHeight;
               var margin = 0; // 출력 페이지 왼쪽 여백설정
              
               var doc = new jsPDF('p', 'mm','a4'); /* p-> portrait, mm->millimeters, a4-> export a4로 디폴트값이당 */
               var position = 0;
               
               // 첫 페이지 출력
               doc.addImage(imgData, 'PNG', margin, position, imgWidth, imgHeight);
               heightLeft -= pageHeight;
               
               console.log("imgHeight",imgHeight);
               
              // 페이지가 더 있을 경우 루프 돌면서 출력
               while (heightLeft >= 0) {
                   position = heightLeft - imgHeight;
                   doc.addPage();
                   doc.addImage(imgData, 'PNG', margin, position, imgWidth, imgHeight);
                   heightLeft -= pageHeight;
               }
            
               // 파일 저장(다운로드)
               doc.save('결재완료문서.pdf');
           });
     });

</script>