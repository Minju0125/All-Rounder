/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 25.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 25.    김보영       최초작성
 * 2023. 11. 25.    김보영       등록
 * 2023. 11. 27.    김보영       목록
 * 2023. 11. 28.    김보영       이모지 처리
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 
 
let fv; 

//초기값세팅
$(function(){
	fn_boardList(1);
	
	var startDate = $('#eventBdate').val();
	
	//검증
	fv = FormValidation.formValidation(document.querySelector('#formEvent'), {
	    fields: {
	    	bbsSj: {
	        validators: {
	          notEmpty: {
	            message: '제목을 입력하세요.'
	          },
	          stringLength: {
	            max: 100,
	            message: '제목을 100자 이내로 입력하세요.'
	          }
	        }
	      },
	    	eventSttus: {
	        validators: {
	          notEmpty: {
	            message: '유형을 선택하세요'
	          }
	        }
	      },
	    	eventEdate: {
	        validators: {
	          callback: {
	                message: '시작일 이후로 선택하세요',
	                callback: function(value, validator, $field) {
	                	
	                	if(value.value == ''){
                        	return true;
                        }
	                    // 종료일이 시작일 이후인지 확인
	                    return new Date(value.value) >= new Date(startDate);
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


 
//-----------------------------------------------

//삭제
function fn_deleteEvent(bbsNo){

	
	//삭제확인모달창
	fn_swalConfirm("글을 삭제하시겠습니까?", function(){
		//비동기

		$.ajax({
			type: "DELETE",
			url: "/event/delete/"+bbsNo,
			data: {"bbsNo":bbsNo},
			contentType: 'application/json',
			success: function (data) {
				var text;
				var icon;
				if (data.success == "Y") {
					text = "삭제가 완료되었습니다.";
					icon = "success";
				} else {
					text = "삭제가 실패되었습니다.";
					icon = "warning";
				}
				//모달창열기
				fn_swalComplete(text, icon,'/event/home',data.success);
			},
			error: function () {
				fn_swalError();
			}
		});
	});
}





//-----------------------------------------------
//수정
function fn_updateEvent(){

/*
	fv.validate().then(function(status) {
		
		if(status !== "Valid"){
			return;
		}
*/
	
		//수정확인모달창
		fn_swalConfirm("글을 수정하시겠습니까?", function(){
			//비동기
			var bbsCn = CKEDITOR.instances.bbsCn.getData();
			$("#bbsCn").val(bbsCn);
			var data = new FormData($("#formEvent")[0]);
	
			$.ajax({
				type: "PUT",
				enctype: 'multipart/form-data',
				url: "/event/update",
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "수정이 완료되었습니다.";
						icon = "success";
					} else {
						text = "수정이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, 
					'/event/detail?bbsNo='+data.bbsNo+'&eventSttus='+data.eventSttus,
					 data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});

}
 
 
 
 
 
//-----------------------------------------------

function fn_updateEmoji(emojiCd){
	
	//이모지 업데이트 비동기
	var emojiCd = $('input[name=radioGroup]:checked').val();
	
	var data = new FormData($("#formEventEmoji")[0]);
	data.append("emojiCd", emojiCd);
	
	
	$.ajax({
		type: "PUT",
		enctype: 'multipart/form-data',
		url: "/event/emojiUpdate",
		data: data,
		processData: false,
		contentType: false,
		cache: false,
		success: function (data) {
			//window.location.href="/event/detail?bbsNo="+bbsNo+"&eventSttus="+eventSttus;
			
			//클릭시 바로 데이터가 바뀜
			document.getElementById('angryCnt').innerHTML = data.cnt.angryCnt;
			document.getElementById('sadCnt').innerHTML = data.cnt.sadCnt;
			document.getElementById('smileCnt').innerHTML = data.cnt.smileCnt;
			document.getElementById('impressCnt').innerHTML = data.cnt.impressCnt;
			document.getElementById('thumbsCnt').innerHTML = data.cnt.thumbsCnt;
		},
		error: function () {
			fn_swalError();
		}
	});
}

//-----------------------------------------------

//분류에 따른 게시글 목록 조회

function fn_boardList(eventSttus) {
	
	$("#sttusId").val(eventSttus);
	let formData = $("#searchForm").serialize();
	let settings = {
		url : '/event/list',
		contentType: 'application/json',
		method: "get",
		data : formData,
		dataType: "json"
	};
	
	let tag ="";
	
	$.ajax(settings)
		.done(function(resp){
			let pagingEventList = resp.paging.dataList;
			if(pagingEventList[0] != null){
				
				let classIndex = 0;
			
				 $.each(pagingEventList, function(i, v) {
				 
				 	const rndColor = ['alert-primary','alert-info', 'alert-success']; 
				 	let alertClass = rndColor[classIndex];
				 	classIndex = (classIndex + 1) % rndColor.length;
				 	
				 	if (v.eventSttus == "1") {
					 	//결혼/출산게시판
				 		tag +=`
							<div class="alert ${alertClass}" onclick="fn_eventDetail(${v.bbsNo},${v.eventSttus})">
				 				<div class="row g-2">
				 					<div class="col-md-9 col-lg-9">
										<h5 class="alert-heading fw-medium mb-1 text-truncate"><i class='bx bx-message-square-detail'></i> ${v.bbsSj}</h5>
									</div>
				 					<div class="col-md-3 col-lg-3" style="text-align: right;">
										${v.eventRdate}
									</div>
								</div>
							</div>
						`;
						$("#WBArea").html(tag);
				 		
				 	}else if(v.eventSttus == "2"){
				 		//사망
				 		tag +=`
							<div class="alert alert-secondary" onclick="fn_eventDetail(${v.bbsNo},${v.eventSttus})">
				 				<div class="row g-2">
				 					<div class="col-md-9 col-lg-9">
										<h5 class="alert-heading fw-medium mb-1 text-truncate"><i class='bx bx-message-square-detail'></i> ${v.bbsSj}</h5>
									</div>
				 					<div class="col-md-3 col-lg-3" style="text-align: right;">
										${v.eventRdate}
									</div>
								</div>
							</div>
						`;
						$("#deadArea").html(tag);
				 		
				 	
				 	}else if(v.eventSttus == "3"){
				 		//생일
				 		tag +=`
							<div class="alert ${alertClass}" onclick="fn_eventDetail(${v.bbsNo},${v.eventSttus})">
				 				<div class="row g-2">
				 					<div class="col-md-9 col-lg-9">
										<h5 class="alert-heading fw-medium mb-1 text-truncate"><i class='bx bx-message-square-detail'></i> ${v.bbsSj}</h5>
									</div>
				 					<div class="col-md-3 col-lg-3" style="text-align: right;">
										${v.eventRdate}
									</div>
								</div>
							</div>
						`;
						$("#birthArea").html(tag);
				 		
				 	
				 	}else if(v.eventSttus == "4"){
				 		//기타
				 		tag +=`
							<div class="alert ${alertClass}" onclick="fn_eventDetail(${v.bbsNo},${v.eventSttus})">
				 				<div class="row g-2">
				 					<div class="col-md-8 col-lg-8">
										<h5 class="alert-heading fw-medium mb-1 text-truncate"><i class='bx bx-message-square-detail'></i> ${v.bbsSj}</h5>
									</div>
				 					<div class="col-md-4 col-lg-4" style="text-align: right;">
										${v.eventRdate}
									</div>
								</div>
							</div>
						`;
						$("#etcArea").html(tag);
				 	}
				 	
				 });
				 //페이징버튼
				 tag=`
				 	${resp.paging.pagingHTML}
				 `;
				 $("#eventPaging").html(tag);
			}
			
	});
}


//페이징 버튼 클릭
function fn_paging(page) {
	searchForm.page.value = page;
	var eventSttus = $('#sttusId').val();
	fn_boardList(eventSttus);
}

//검색 버튼 클릭
$(document).on("click", "#searchBtn", function(){
	
	var eventSttus = $('#sttusId').val();
	fn_boardList(eventSttus);
});





//-------------------------------------------------------

//경조사게시판 등록 비동기

function fn_insertEvent(){

	
		//저장확인모달창
		fn_swalConfirm("글을 등록하시겠습니까?", function(){
			//비동기
			var data = new FormData($("#formEvent")[0]);
			var bbsCn = CKEDITOR.instances.bbsCn.getData();
			data.append("bbsCn", bbsCn);
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/event/insert",
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "등록이 완료되었습니다.";
						icon = "success";
					} else {
						text = "등록이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/event/home", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	
}

//이벤트 상세페이지
function fn_eventDetail(bbsNo, eventSttus){

	
	location.href ='/event/detail?bbsNo='+bbsNo+'&eventSttus='+eventSttus;
}

//이벤트 수정폼 
function fn_eventEdit(bbsNo){
	
	location.href ='/event/edit?bbsNo='+bbsNo;
}
//경조사 홈
function fn_eventHome(){
	location.href ='/event/home';
}


//이벤트 등록폼 
function fn_eventForm(){
	
	location.href ='/event/form';
}



