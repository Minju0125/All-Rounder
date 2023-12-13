/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 10.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 10.    김보영       최초작성
 * 2023. 11. 14.    김보영       일감목록 비동기
 * 2023. 11. 16.    김보영       일감삭제
 * 2023. 12. 1.    김보영        일감등록 기간검증
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

let fv;

$(function (){
	
	
	fv = FormValidation.formValidation(document.querySelector('#formJobSettings'), {
	    fields: {
	    	"jobSj": {
	        validators: {
	          notEmpty: {
	            message: '일감 제목을 입력하세요.'
	          },
	          stringLength: {
	            max: 100,
	            message: '제목을 100자 이내로 입력하세요.'
	          }
	        }
	      },
	    	jobStcd: {
	        validators: {
	         callback:{
	         	message: function(value, validator, $field) {
                // Your custom condition to determine the error message
	                if ($('#insertjobuSn').val() == '') {//상위일감
	                    return true;
	                } else {
	                    return '일감상태를 선택하세요.';
	                }
            	}
	         }
	        }
	      },
	    	jobCn: {
	        validators: {
	          notEmpty: {
	            message: '일감 내용을 입력하세요.'
	          }
	        }
	      },
	    	jobBdate: {
	        validators: {
	          notEmpty: {
	            message: '시작일을 입력하세요.'
	          },
	          callback: {
                    message: '시작일은 종료일보다 늦을 수 없으며,<br> 기간 내의 날짜를 선택하세요.',
                    callback: function (value, validator, $field) {
                    	//비어있으면 패스
                        if(value.value == ''){
                        	return true;
                        }
                        
                        // 시작일이 프로젝트 시작일 이후인지 확인
						var startDate = "";
						var endDate = "";
						
						if($('#insertjobuSn').val() == ''){
							//상위일감이면 프로젝트 날짜 넣어주고
							startDate =$('#projectBdate').val();
							endDate = $('#projectEdate').val();
						}else{
							//하위일감이면 상위일감날짜 넣어주고
							startDate=$('#uJobBdate').text();
							endDate=$('#uJobEdate').text();;
						}
                        
                        return new Date(value.value) >= new Date(startDate) 
                        		&& new Date(value.value)<= new Date(endDate)
                        		&& new Date(value.value) <=new Date($('#jobEdate').val());
                    }
                }
	        }
	      },
	    	jobEdate: {
	        validators: {
	          notEmpty: {
	            message: '종료일을 입력하세요.'
	          },
	          callback: {
	                message: '종료일은 시작일보다 빠를수 없으며, <br>기간 내의 날짜를 선택하세요.',
	                callback: function(value, validator, $field) {
                    	//비어있으면 패스
                        if(value.value == ''){
                        	return true;
                        }
                        
	                   // 시작일이 프로젝트 시작일 이후인지 확인
						var startDate = "";
						var endDate = "";
						
						if($('#insertjobuSn').val() == ''){
							//상위일감이면 프로젝트 날짜 넣어주고
							startDate =$('#projectBdate').val();
							endDate = $('#projectEdate').val();
						}else{
							//하위일감이면 상위일감날짜 넣어주고
							startDate=$('#uJobBdate').text();
							endDate=$('#uJobEdate').text();;
						}
	                    
	                    return new Date(value.value) >= new Date(startDate) 
                        		&& new Date(value.value)<= new Date(endDate)
                        		&& new Date(value.value) >=new Date($('#jobBdate').val());
	                }
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



//--------------------------------------------------
//파일 삭제
function fn_deleteFile(proFileCode,proAtchSnm){

	fn_swalConfirm("파일을 삭제하시겠습니까?", function(){
	
	var proSn = $('.dproSn').data('setProsn');
	var jobSn = $('.djobSn').data('setJobsn');
	
	let file = {
		proFileCode : proFileCode,
		proAtchSnm : proAtchSnm,
		proJobsn: jobSn,
		proSn : proSn
	}
		
		$.ajax({
			type: "DELETE",
			url: "/job/"+proSn+"/deleteFile",
			data: JSON.stringify(file), 
			dataType: "json",
			contentType: "application/json",
			success: function (data) {
				var text;
				var icon;
				if (data.success == "Y") {
					text = "파일 삭제가 완료되었습니다.";
					icon = "success";
				} else {
					text = "파일 삭제가 실패되었습니다.";
					icon = "warning";
				}
				//모달창열기
				fn_swalComplete(text, icon, "/job/"+proSn+"/"+jobSn+"/detail", data.success);
			},
			error: function () {
				fn_swalError();
			}
		});
	});
}



//-----------------------------------------------------------

/*
$(document).ready(function() {
	
	var projectBdate = $('#projectBdate').val();
	var projectEdate = $('#projectEdate').val();

    // 시작일에 대한 datepicker 초기화
    $("#jobBdate").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        startDate: projectBdate,
        endDate: projectEdate,
        language: "ko",
        todayHighlight: true,
        immediateUpdates: false,
    }).on('changeDate', function(selected) {
        var startDate = new Date(selected.date.valueOf());
        // 종료일 datepicker의 최소 선택 가능 날짜 설정
        $('#jobEdate').datepicker('setStartDate', startDate);

        // 날짜 범위 유효성 검사
        validateDateRange();
    });

    // 종료일에 대한 datepicker 초기화
    $("#jobEdate").datepicker({
        format: "yyyy-mm-dd",
        autoclose: true,
        startDate: projectBdate,
        endDate: projectEdate,
        language: "ko",
        todayHighlight: true,
    }).on('changeDate', function(selected) {
        var endDate = new Date(selected.date.valueOf());
        // 시작일 datepicker의 최대 선택 가능 날짜 설정
        $('#jobBdate').datepicker('setEndDate', endDate);

        // 날짜 범위 유효성 검사
        validateDateRange();
    });

    // 날짜 범위를 검사하는 함수
    function validateDateRange() {
        var startDate = new Date($("#jobBdate").val());
        var endDate = new Date($("#jobEdate").val());

        // 시작일이 종료일보다 빠를 경우
        if (startDate > endDate) {
            // 경고 메시지 표시
            alert("종료일은 시작일 이후로 설정해주세요.");

            // 선택한 날짜 초기화
            $('#jobBdate').datepicker('clearDates');
            $('#jobEdate').datepicker('clearDates');
        }
    }

});

*/
//-----------------------------------------------------------

//일감삭제

function fn_jobDelete(){
	
	//일감삭제 모달창
	if(accountActivation.checked == true){
	
		fn_swalConfirm("일감을 삭제하시겠습니까?", function(){
			//비동기
			var deleteForm = new FormData($("#formAccountDeactivation")[0]);
			var proSn = $('.dproSn').data('setProsn');
			console.log("데이터셋",proSn);
			var jobSn = $('.djobSn').data('setJobsn');
			console.log("데이터셋",jobSn); 
			
			$.ajax({
				type: "DELETE",
				enctype: 'multipart/form-data',
				url: "/job/"+proSn+"/"+jobSn+"/delete",
				data: deleteForm,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "일감 삭제가 완료되었습니다.";
						icon = "success";
					} else {
						text = "일감 삭제가 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/job/"+proSn+"/home", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	}
}

//-----------------------------------------------------------
 
//일감업데이트 

function fn_jobUpdate(){

	
	var checkBoxes = document.querySelectorAll(".form-check-input");
	console.log(checkBoxes);
	var checkCnt = 0;
	
	for(var i =0 ; i < checkBoxes.length; i++){
		if(checkBoxes[i].checked){
			checkCnt++;
		}
	}
	var updateTempUJob = $('.djobuSn').data('setJobusn');
	
	//하위일감일때
	if (updateTempUJob != ""){
	
		if(checkCnt != 1){
			let text ="담당자를 한명 선택하세요";
			let icon ="warning";
		 	fn_swalAlert(text,icon);
		}else{
			console.log("하위",updateTempUJob);
			fn_ajaxjobUpdate();
		}
		
	}else{ //상위일감일때
	
		if(checkCnt <1){
			let text ="담당자를 선택하세요";
			let icon ="warning";
			fn_swalAlert(text,icon);
		}else{
			console.log("상위");
			fn_ajaxjobUpdate();
		}		
	}
	
}

//-----------------------------------------------------------
function fn_ajaxjobUpdate(){

	fn_swalConfirm("일감을 수정하시겠습니까?", function(){
		//비동기
		
		var updateForm = new FormData($("#formJobSettings")[0]);
		
		
		var proSn = $('.dproSn').data('setProsn');
		console.log("데이터셋",proSn);
		var jobSn = $('.djobSn').data('setJobsn');
		console.log("데이터셋",jobSn); 
	
		$.ajax({
			type: "PUT",
			enctype: 'multipart/form-data',
			url: "/job/"+proSn+"/"+jobSn+"/update",
			data: updateForm,
			processData: false,
			contentType: false,
			cache: false,
			success: function (data) {
				var text;
				var icon;
				//var jobSn = data.jobSn;
				if (data.success == "Y") {
					text = "일감 수정이 완료되었습니다.";
					icon = "success";
				} else {
					text = "일감 수정이 실패되었습니다.";
					icon = "warning";
				}
				//모달창열기
				fn_swalComplete(text, icon, '/job/' + proSn + '/'+jobSn+'/detail', data.success);
			},
			error: function () {
				fn_swalError();
			}
		});
	});
}

//----------------------------------------------------------- 
 
//파일 선택시 밑에 파일명뜨기
document.querySelector('#insertJobFiles').addEventListener('change', function (event) {
    // 선택된 파일들의 정보를 가져옵니다.
    var files = event.target.files;

    // 파일 이름을 표시할 엘리먼트를 선택합니다.
    var fileListContainer = document.querySelector('#fileList');

    // 이전에 표시된 파일 이름들을 초기화합니다.
    fileListContainer.innerHTML = '';

    // 선택된 파일들의 이름을 표시합니다.
    for (var i = 0; i < files.length; i++) {
        var fileName = files[i].name;
        var listItem = document.createElement('div');
        listItem.textContent = fileName;
        fileListContainer.appendChild(listItem);
    }
});

//-----------------------------------------------------------

//일감등록
function fn_jobInsert(){

	
		/*
		저장할때
		상위일감이 없으면 -> 상위일감
		담당자가 여려명이어도 상관없이 저장
		
		상위일감이 선택되어 있으면 -> 하위일감
		담당자 체크박스에 체크된 갯수 확인 후 하나 보다 많으면 알림창으로 한명만 선택하라고 알림 필요
		*/
		
		var checkBoxes = document.querySelectorAll(".form-check-input");
		console.log(checkBoxes);
		var checkCnt = 0;
		
		for(var i =0 ; i < checkBoxes.length; i++){
			if(checkBoxes[i].checked){
				checkCnt++;
			}
		}
		var insertTempUJob = $('#insertjobuSn').val();
		
		//하위일감일때
		if (insertTempUJob != ""){
		
			if(checkCnt != 1){
				let text ="담당자를 한명 선택하세요";
				let icon ="warning";
			 	fn_swalAlert(text,icon);
			}else{
				console.log("하위",insertTempUJob);
				fn_ajaxInsertJob();
			}
			
		}else{ //상위일감일때
		
			if(checkCnt <1){
				let text ="담당자를 선택하세요";
				let icon ="warning";
			 	fn_swalAlert(text,icon);
			}else{
				console.log("상위");
				fn_ajaxInsertJob();
			}		
		}
}

//-----------------------------------------------------------
function fn_ajaxInsertJob(){

	fv.validate().then(function(status) {
		
		if(status !== "Valid"){
			return;
		}	

		fn_swalConfirm("일감을 등록하시겠습니까?", function(){
			//비동기
			var insertForm = new FormData($("#formJobSettings")[0]);
			var proSn = $("#tempProSn").val();
		
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/job/"+proSn+"/insert",
				data: insertForm,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					var jobSn = data.jobSn;
					if (data.success == "Y") {
						text = "일감 등록이 완료되었습니다.";
						icon = "success";
					} else {
						text = "일감 등록이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/job/"+proSn+"/"+jobSn+"/detail", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	});
}

//-----------------------------------------------------------

/*일감상태버튼 이벤트*/
function fn_jobStcd(jobStcd){
		
		document.getElementById("jobStcd").value = jobStcd;
		
		var elements = document.querySelectorAll('.btn-jobStcd');
	    elements.forEach(function(element) {
	        element.classList.remove('active');
	    });
	    
	    //제이쿼리 사용법
// 	    $(".btn-jobStcd").each(function(i, ele){
// 	    $.each(".btn-jobStcd", function(i, ele){
// 	    	$(ele).removeClass("active");
// 	    })
	    
	   
	    
	    $(".btn-jobStcd").eq(jobStcd-1).addClass("active");
		//$("#jobStcd").val(jobStcd);
		
	}

	
//-----------------------------------------------------------

/*일감목록 비동기*/

$(function () {

	fn_jobPaging();

});

//일감 목록 페이징
function fn_jobPaging(){

	let proSn = $('.divider-text').data('pro-sn');
	let formData = $("#searchForm").serialize();
	let settings = {

		url: '/job/' + proSn + '/jobList',
		contentType: 'application/json',
		method: "get",
		data : formData,
		dataType: "json"
	};

	let trTag = "";


	$.ajax(settings)
		.done(function (resp) {
			let pagingJobList = resp.paging.dataList;
			if (pagingJobList[0] != null) {
				$.each(pagingJobList, function (i, v) {

					if (v.jobSn != null) {
						trTag += `
								<tr onclick="fn_jobDetail('${v.proSn}', '${v.jobSn}')" style="cursor: pointer;">							
								  <td>
									<div>
										<i class="fab  text-info me-3">${v.rnum}</i>
										<span class="fw-medium">${v.jobSj}</span>
									</div>
								  </td>
								  <td id="writter" data-writter="${v.jobWriter}">${v.findName}</td>
								  <td id="progrs" data-progrs="${v.jobProgrs}">
								  	<div class="progress">
                      					<div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar" style="width: ${v.jobProgrs}%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                    					</div>
								  </td>
								  <td>
									<ul class="list-unstyled users-list m-0 avatar-group d-flex align-items-center">
						`;	
								
						$.each(v.empProfileImgs.split(', '), function(a,b){
							
							trTag +=`
								  <li data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" class="avatar avatar-xs pull-up" aria-label="Lilian Fuller" data-bs-original-title="Lilian Fuller">
									<img src="${b}" alt="" class="rounded-circle">
								  </li>
							`;
						})		
						
						trTag += `
									</ul>
								  </td>
								  <td>
								 `;
						if (v.jobStcd == 1) {
							trTag += `<span class="badge bg-label-primary me-1 fw-bold">진행</span>`;
						} else if (v.jobStcd == 2) {
							trTag += `<span class="badge bg-label-success me-1 fw-bold">요청</span> `;
						} else if (v.jobStcd == 3) {
							trTag += `<span class="badge bg-label-danger me-1 fw-bold">피드백</span>`;
						} else if (v.jobStcd == 4) {
							trTag += `<span class="badge bg-label-warning me-1 fw-bold">보류</span>`;
						} else if (v.jobStcd == 5) {
							trTag += `<span class="badge bg-label-info me-1 fw-bold">완료</span>`;
						} else if (v.jobuSn == null) {
							trTag += `<span class="badge bg-label-secondary me-1 fw-bold">상위</span>`;
						} else {
							trTag +="";
						}
						trTag += ` 
								 </td>
								  <td id="writter" data-writter="${v.jobEdate}">${v.jobEdate}</td>
								</tr>
							`;
					} else {
						trTag += `
								<tr>
									<td colspan='6'>일감 내역 없음</td>
								<tr>	
							`;
					}
					$("#jobList").html(trTag);
				});
				trTag = `
				<tr>
					<td colspan="6">
							<hr class="my-0 mb-3 mt-2">
						${resp.paging.pagingHTML}
						<form id="searchForm">
							<div id ="searchUI" class="row g-3 d-flex justify-content-center">
								<input type="hidden" name="page" readonly="readonly"/>
								<div class="col-auto">
									<select name=" searchType" class="form-select"> 
										<option value="" >전체</option>
										<option value="title" >제목</option>
										<option value="charger" >담당자</option>
									</select>
								</div>
								<div class="col-auto">
									<input name="searchWord" placeholder="입력하세요" class="form-control" />
								</div>
								<div class="col-auto">
									<input type="button" value="검색" id="searchBtn" class="btn btn-primary" />
								</div>
							</div>
						</form>
					</td>
				</tr>
				`;
				$("#jobPaging").html(trTag);
			}
		});
}

//-----------------------------------------------------------

//페이징 버튼 클릭
function fn_paging(page) {
	searchForm.page.value = page;
	fn_jobPaging();
}

//검색 버튼 클릭
$(document).on("click", "#searchBtn", function(){
	fn_jobPaging();
});

const proSn = $('.divider-text').data('pro-sn');

//-----------------------------------------------------------

//일감 home으로 이동 
const fInit = () => {
	let xhr = new XMLHttpRequest();
	xhr.open("get", "/job/" + proSn + "/home", true);
	xhr.onreadystatechange = () => {
		if (xhr.readyState == 4 && xhr.status == 200) {
			console.log("항상 먼저 체크:", JSON.parse(xhr.responseText));
			fList(JSON.parse(xhr.responseText));
		}
	}
	xhr.send();
}

//-----------------------------------------------------------
//해당 프로젝트의 나의 일감 확인

$(function () {

	let proSn = $('.divider-text').data('pro-sn');

	let settings = {

		url: '/job/' + proSn + '/myjob',
		contentType: 'application/json',
		method: "get",
		dataType: "json"
	};

	let trTag = "";


	$.ajax(settings)
		.done(function (resp) {
			let jobList = resp.myjob;
			if (jobList[0] != null) {
				$.each(jobList, function (i, v) {

					if (v.jobSn != null) {
						trTag += `
								<tr>							
								  <td>
									<div>
										<span class="fw-medium">${v.jobSj}</span>
									</div>
								  </td>
								  <td id="progrs" data-progrs="${v.jobProgrs}">
								  	<div class="progress">
                      <div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar" style="width: ${v.jobProgrs}%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">
											</div>
                    </div>
								  </td>
								  <td>${v.jobEdate}</td>
								  <td>
								 `;

						if (v.jobPriort == 1) {
							trTag += `<span class="text-danger">긴급</span>`;
						} else if (v.jobPriort == 2) {
							trTag += `<span class="text-warning">높음</span> `;
						} else if (v.jobPriort == 3) {
							trTag += `<span class="text-primary">중간</span>`;
						} else {
							trTag += `<span class="text-success">낮음</span>`;

						}
						trTag += `
								 </td>
								 <td>
								 
								 `;

						if (v.jobStcd == 1) {
							trTag += `<span class="badge bg-label-primary me-1 fw-bold">진행</span>`;
						} else if (v.jobStcd == 2) {
							trTag += `<span class="badge bg-label-success me-1 fw-bold">요청</span> `;
						} else if (v.jobStcd == 3) {
							trTag += `<span class="badge bg-label-danger me-1 fw-bold">피드백</span>`;
						} else if (v.jobStcd == 4) {
							trTag += `<span class="badge bg-label-warning me-1 fw-bold">보류</span>`;
						} else if (v.jobStcd == 5) {
							trTag += `<span class="badge bg-label-info me-1 fw-bold">완료</span>`;
						} else {
							trTag += `<span class="badge bg-label-secondary me-1 fw-bold">상위</span>`;
						}

						trTag += `
								 </td>`;

					} else {
						trTag += `
								<tr>
									<td colspan='5'>내 일감 내역 없음</td>
								<tr>	
							`;
					}
					$("#myJob").html(trTag);
				});
			}
		});
});

//-----------------------------------------------------------

function fn_jobDetail(proSn, jobSn){
	
	location.href ='/job/' + proSn + '/'+jobSn+'/detail';
}
//-----------------------------------------------------------

function fn_goAdminProject(proSn){

	location.href ='/adminprojectDetail/' + proSn ;
}


