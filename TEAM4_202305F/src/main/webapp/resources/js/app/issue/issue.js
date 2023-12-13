/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 23.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 23.     김보영       최초작성 ,이슈등록
 * 2023. 11. 24.     김보영       목록, 칸반보드
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 





//------------------------------------------------------------------------

let fv;

//페이지 로딩 후 실행
$(function () {

	fv = FormValidation.formValidation(document.querySelector('#issueForm'), {
	    fields: {
	    	issueSe: {
	        validators: {
	          notEmpty: {
	            message: '이슈 종류를 선택하세요.'
	          }
	        }
	      },
	    	issueImp: {
	        validators: {
	          notEmpty: {
	            message: '중요도를 선택하세요.'
	          }
	        }
	      },
	    	issueSj: {
	        validators: {
	          notEmpty: {
	            message: '이슈명을 입력하세요.'
	          }
	        }
	      },
	    	issueCn: {
	        validators: {
	          notEmpty: {
	            message: '이슈내용을 입력하세요.'
	          }
	        }
          }
		},
	    plugins: {
	      trigger: new FormValidation.plugins.Trigger(),
	      bootstrap5: new FormValidation.plugins.Bootstrap5({
	        eleValidClass: '',
	        rowSelector: '.input-group'
	      }),
	      //submitButton: new FormValidation.plugins.SubmitButton(),
	      // Submit the form when all fields are valid
	      // defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
	      autoFocus: new FormValidation.plugins.AutoFocus()
	    },
        init: instance => {
          instance.on('plugins.message.placed', function (e) {
            if (e.element.parentElement.classList.contains('input-group')) {
              e.element.parentElement.insertAdjacentElement('afterend', e.messageElement);
            }
          });
        }
   });
})


//-----------------------------------------------------------------

//이슈수정
function  fn_issueUpdate(issueNo){

	fv.validate().then(function(status) {
		
		if(status !== "Valid"){
			return;
		}
	
		var proSn = $('#proSn').val();
	
		//수정확인모달창
		fn_swalConfirm("이슈를 수정하시겠습니까?", function(){
			//비동기
			var data = new FormData($("#issueForm")[0]);
	
			$.ajax({
				type: "PUT",
				enctype: 'multipart/form-data',
				url: "/issue/"+proSn+"/updateInfo/"+issueNo,
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "이슈 수정이 완료되었습니다.";
						icon = "success";
					} else {
						text = "이슈 수정이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, '/issue/'+proSn+'/detail/'+issueNo, data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	});
	
}


//-----------------------------------------------------------------

//이슈삭제
function  fn_issueDelete(issueNo){

	var proSn = $('#proSn').val();

	//수정확인모달창
	fn_swalConfirm("이슈를 삭제하시겠습니까?", function(){
		//비동기
		var data = new FormData($("#issueForm")[0]);

		$.ajax({
			type: "DELETE",
			enctype: 'multipart/form-data',
			url: "/issue/"+proSn+"/delete/"+issueNo,
			data: data,
			processData: false,
			contentType: false,
			cache: false,
			success: function (data) {
				var text;
				var icon;
				if (data.success == "Y") {
					text = "이슈 삭제가 완료되었습니다.";
					icon = "success";
				} else {
					text = "이슈 삭제가 실패되었습니다.";
					icon = "warning";
				}
				//모달창열기
				fn_swalComplete(text, icon, '/issue/'+proSn+'/home', data.success);
			},
			error: function () {
				fn_swalError();
			}
		});
	});

	
}


//-----------------------------------------------------------------

//옵션 종류에 따른 리스트 출력





//-----------------------------------------------------------------
// 칸반기능(수정)
let dragged;

/* 드래그 가능한 대상에서 발생하는 이벤트 */
document.addEventListener("drag", (event) => {
	console.log("dragging");
});

document.addEventListener("dragstart", (event) => {
	// 드래그한 요소에 대한 참조 저장
	dragged = event.target;
	// 반투명하게 만들기
	event.target.classList.add("dragging");
	console.log("dragged", dragged);

	const issueNo = dragged.getAttribute("data-issueNo"); // 추가
	const writer = dragged.getAttribute("data-writer"); // 추가
	
	console.log("issueNo", issueNo);
	event.dataTransfer.setData("issueNokey", issueNo);
	event.dataTransfer.setData("writerkey", writer);

	// issueNokey에 issueNo의 정보를 저장한다.=>보드에서 보드로 이동하면서 값이 바뀌기때문에
	// dataTransfer을 이용해 데이터를 저장한거임!

	let dropzoneElement = document.querySelector('.dropzone');
	console.log("dropzoneElement", dropzoneElement);
	const issueSttus = dropzoneElement.getAttribute('data-issuesttus'); // 추가
	event.dataTransfer.setData("issueSttuskey", issueSttus); // 이하동문
});

document.addEventListener("dragend", (event) => {
	// 투명도 초기화
	event.target.classList.remove("dragging");
});

/* 드롭 대상에서 발생하는 이벤트 */
document.addEventListener("dragover", (event) => {
	event.preventDefault();
});

document.addEventListener("dragenter", (event) => {
	// 드래그 가능한 요소가 대상 위로 오면 강조
	if (event.target.classList.contains("dropzone")) {
		event.target.classList.add("dragover");
	}
});

document.addEventListener("dragleave", (event) => {
	// 드래그 가능한 요소가 대상 밖으로 나가면 강조 제거
	if (event.target.classList.contains("dropzone")) {
		event.target.classList.remove("dragover");
	}
});

let dropElements = document.querySelectorAll(".dropzone");
let bgArray = ['border-primary', 'border-warning', 'border-success'];

dropElements.forEach((el) => {
	el.addEventListener("drop", (event) => {
		event.preventDefault();

		const issueNo = event.dataTransfer.getData("issueNokey");
		const writer = event.dataTransfer.getData("writerkey");
		const issueSttus = event.target.dataset.issuesttus;
		var proSn = $('#proSn').val();
		console.log(issueNo, issueSttus, proSn);
		const realUser = document.getElementById('realUser').value;
		
		if(writer == realUser ){
			// Check if the issue is in the correct dropzone
			if (event.target.classList.contains("dropzone")) {
	
				// Check if the issue status is different
				if (issueSttus !== dragged.getAttribute("data-issuesttus")) {
					// AJAX request
					$.ajax({
						type: "PUT",
						url: "/issue/" + proSn + "/update",
						contentType: "application/json",
						dataType: "json",
						data: JSON.stringify({ issueNo: issueNo, issueSttus: issueSttus }),
						success: function (data) {
							if (data.result === "OK") {
								console.log("성공");
								dragged.parentNode.removeChild(dragged);
								$(event.target).find(".issueBox").append(dragged);
	
								bgArray.forEach((b) => {
									dragged.classList.remove(b);
								});
	
								dragged.classList.add(event.target.dataset.addBo);
								Swal.fire("변경되었습니다.", "", "success");
							}
						},
						error: function (xhr) {
							console.log("실패");
							Swal.fire({
								icon: "error",
								title: "처리 중 오류가 발생했습니다.",
							});
						},
					});
				}
			}
		}else{
			Swal.fire({
	          icon: 'error',
	          title: '작성자만 변경가능합니다!',
	        });
		}
	});
});






//----------------------------------------------------------------------------
//일반인지 결함인지 버튼클릭이벤트
function fn_issueSe(issueSe) {

	document.getElementById("issueSe").value = issueSe;

	var elements = document.querySelectorAll('.btn-issueSe');
	elements.forEach(function (element) {
		element.classList.remove('active');
	});

	//제이쿼리 사용법
	// 	    $(".btn-jobStcd").each(function(i, ele){
	// 	    $.each(".btn-jobStcd", function(i, ele){
	// 	    	$(ele).removeClass("active");
	// 	    })

	$(".btn-issueSe").eq(issueSe - 1).addClass("active");
	//$("#jobStcd").val(jobStcd);
}

//------------------------------------------------------------------------------

//이슈등록 비동기

function fn_issueInsert() {

	fv.validate().then(function(status) {
		
		if(status !== "Valid"){
			return;
		}


		fn_swalConfirm("이슈를 등록하시겠습니까?", function () {
			//비동기
			var data = new FormData($("#issueForm")[0]);
			var proSn = $('#proSn').val();
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/issue/" + proSn + "/insert",
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "이슈 등록이 완료되었습니다.";
						icon = "success";
					} else {
						text = "이슈 등록이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/issue/" + proSn + "/home", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	});	
}

//------------------------------------------------------------------------------
//이슈 목록 조회

$(function () {

	fn_selectIssue();
});




//진행 이슈 조회
function fn_selectIssue() {

	var proSn = $('#proSn').val();

	let settings = {
		url: '/issue/' + proSn + '/list',
		contentType: 'application/json',
		method: "get",
		dataType: "json",
		data: {
			issueImp: $("#searchIssueImp").val(),
			issueSe: $("#searchIssueSe").val()
		}
	};

	let tag1 = "";
	let tag2 = "";
	let tag3 = "";

	$.ajax(settings)
		.done(function (resp) {

			//진행 ->1
			let issueList1 = resp.issueList1;
			if (issueList1[0] != null) {
				$.each(issueList1, function (i, v) {
					if (v.issueNo != null) {
					
						tag1 +=`
							<div data-issueNo="${v.issueNo}" data-writer="${v.empCd}" draggable="true" class=" draggable card shadow-none  border border-primary mb-3">
								<div onclick="fn_detailIssue(${v.issueNo})" class="m-2" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">
									<div class="row">
										<div class="col-xl-7 col-lg-7">
							 `;
				       					if (v.issueImp == 1) {
											tag1 += `<span class="text-danger"><i class='bx bx-dizzy mb-1'></i>긴급</span>`;
										} else if (v.issueImp == 2) {
											tag1 += `<span class="text-primary"><i class='bx bx-smile mb-1'></i>중간</span> `;
										} else if (v.issueImp == 3) {
											tag1 += `<span class="text-success"><i class='bx bx-laugh mb-1'></i>낮음</span>`;
										}
										
						tag1 +=`				
										</div>
										<div class="col-xl-5 col-lg-5" style="text-align: end;">
											작성 : ${v.writer}
										</div>
										<h5 class="card-title text-truncate">[
									`;
						if (v.issueSe == 1) { tag1 += "일반"; } else { tag1 += "결함"; }			
						tag1 +=`	] ${v.issueSj}</h5>		
									</div>
								</div>
							</div>	
						`;
					}
				});
			}


			//보류 ->2 
			let issueList2 = resp.issueList2;
			if (issueList2[0] != null) {
				$.each(issueList2, function (i, v) {
					if (v.issueNo != null) {
					
						tag2 +=`
							<div data-issueNo="${v.issueNo}" data-writer="${v.empCd}" draggable="true" class=" draggable card shadow-none  border border-warning mb-3">
								<div onclick="fn_detailIssue(${v.issueNo})" class="m-2" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">
									<div class="row">
										<div class="col-xl-7 col-lg-7">
							 `;
				       					if (v.issueImp == 1) {
											tag2 += `<span class="text-danger"><i class='bx bx-dizzy mb-1'></i>긴급</span>`;
										} else if (v.issueImp == 2) {
											tag2 += `<span class="text-primary"><i class='bx bx-smile mb-1'></i>중간</span> `;
										} else if (v.issueImp == 3) {
											tag2 += `<span class="text-success"><i class='bx bx-laugh mb-1'></i>낮음</span>`;
										}
										
						tag2 +=`				
										</div>
										<div class="col-xl-5 col-lg-5" style="text-align: end;">
											작성 : ${v.writer}
										</div>
										<h5 class="card-title text-truncate">[
									`;
						if (v.issueSe == 1) { tag2 += "일반"; } else { tag2 += "결함"; }			
						tag2 +=`	] ${v.issueSj}</h5>		
									</div>
								</div>
							</div>	
						`;
					}
				});
			}



			//완료 ->3
			let issueList3 = resp.issueList3;
			if (issueList3[0] != null) {
				$.each(issueList3, function (i, v) {
					if (v.issueNo != null) {
					
			
						tag3 +=`
							<div data-issueNo="${v.issueNo}" data-writer="${v.empCd}" draggable="true" class=" draggable card shadow-none  border border-success mb-3">
								<div onclick="fn_detailIssue(${v.issueNo})" class="m-2" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBottom" aria-controls="offcanvasBottom">
									<div class="row">
										<div class="col-xl-7 col-lg-7">
							 `;
				       					if (v.issueImp == 1) {
											tag3 += `<span class="text-danger"><i class='bx bx-dizzy mb-1'></i>긴급</span>`;
										} else if (v.issueImp == 2) {
											tag3 += `<span class="text-primary"><i class='bx bx-smile mb-1'></i>중간</span> `;
										} else if (v.issueImp == 3) {
											tag3 += `<span class="text-success"><i class='bx bx-laugh mb-1'></i>낮음</span>`;
										}
										
						tag3 +=`				
										</div>
										<div class="col-xl-5 col-lg-5" style="text-align: end;">
											작성 : ${v.writer}
										</div>
										<h5 class="card-title text-truncate">[
									`;
						if (v.issueSe == 1) { tag3 += "일반"; } else { tag3 += "결함"; }			
						tag3 +=`	] ${v.issueSj}</h5>		
									</div>
								</div>
							</div>	
						`;
					}
				});
			}

			$('#inProgressIssue').html(tag1);
			$('#onHoldIssue').html(tag2);
			$('#completedIssue').html(tag3);
		});
}
//--------------------------------------------

//이슈 디테일
function fn_detailIssue(issueNo){
	
	var proSn = $('#proSn').val();
	
	location.href ='/issue/' + proSn + '/detail/'+issueNo;
}

//이슈홈이동
function fn_issueHome(proSn){
	
	location.href ='/issue/' + proSn + '/home';
}
//--------------------------------------------
// 초기화 버튼 클릭 시 호출되는 함수

$("#resetBtn").on("click", function () {

	//$("#searchIssueImp").val("");

	document.getElementById("searchIssueImp").value = "";
	document.getElementById("searchIssueSe").value = "";
	fn_selectIssue();
});

//--------------------------------------------
//차트





