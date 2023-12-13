/**
 * <pre>
 * 
 * </pre>
 * @author 전수진
 * @since 2023. 11. 28.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.  전수진       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 


$(function(){
	
	let cPath = document.body.dataset.contextPath;
	let dUrl = cPath+'/sanction/drafterData';
	
	
	$.getJSON(dUrl)
		.done(function(resp){
			let dataList = resp.paging.dataList.slice(0, 5);
			let total = resp.paging.totalRecord;
			console.log("resp.paging",resp.paging);
			console.log("dataList",dataList);
			console.log("dataList.length",dataList.length);
			let trTag = "";
			if(dataList.length > 0 ){
				$.each(dataList, function(idx, list){
					trTag += "<tr><td>"+list.rnum+"</td><td>"+list.sanctionForm.formNm+"</td>";
					trTag += `<td><a href="${cPath}/sanction/${list.sanctnNo}">${list.sanctnSj}</td>`;
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
			} else {
				trTag += `
					<tr>
						<td colspan = '7'>검색 조건에 맞는 리스트 없음</td>
					</tr>
				`;
			}
			$("#drafterTrBody").html(trTag);
			
			if(total>0) {
				$("#drafting").text(total);
			} else {
				$("#drafting").text('0');
			}
		}); 
		
	
	
	
	
	let sUrl = cPath+'/sanction/sanctnerData';
	let data = $(this).serialize();
	
	$.getJSON(sUrl)
		.done(function(resp){
			let dataList = resp.paging.dataList.slice(0, 5);
			let total = resp.paging.totalRecord;
			console.log("resp.paging",resp.paging);
			console.log("dataList",dataList);
			console.log("dataList.length",dataList.length);
			let trTag = "";
			if(dataList.length > 0 ){
				$.each(dataList, function(idx, list){
					trTag += "<tr><td>"+list.rnum+"</td><td>"+list.sanctionForm.formNm+"</td>";
					trTag += `<td><a href="${cPath}/sanction/${list.sanctnNo}">${list.sanctnSj}</td>`;
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
			} else {
				trTag += `
					<tr>
						<td colspan = '7'>검색 조건에 맞는 리스트 없음</td>
					</tr>
				`;
			}
			$("#sanctnerTrBody").html(trTag);
			
			if(total>0) {
				$("#pending").text(total);
			} else {
				$("#pending").text('0');
			}
		}); 
		
	let rUrl = cPath+'/sanction/rcyerData';
	$.getJSON(rUrl)
		.done(function(resp){
			let total = resp.paging.totalRecord;
			if(total>0) {
				$("#receive").text(total);
			} else {
				$("#receive").text('0');
			}
		}); 
		
	
//========================================대결자선택===============================================
	$.getJSON("/org/list")
		.done(function(resp) {
			let empList = resp.list;
			
			console.log("empListProxy================", empList);
            let tags = makeRecpList(empList); 
			$("#prxsanctnCnfer").append(tags);
		})
		.fail(function(jqxhr, textStatus, error) {
			let err = textStatus + ", " + error;
			console.error("Request Failed: " + err);
		});
		
	// 전 직원목록을 반함
	let makeRecpList = function (respList) {
	   let tags = "";
	   
	   tags += `<option value="-1">대결자 선택</option>`;
	
	   for (let i = 0; i < respList.length; i++) {
	      if (respList[i].empName != "관리자") {
	         tags += `<option data-avatar="5.png" value="${respList[i].empCd}">[${respList[i].dept.deptName}]${respList[i].empName} ${respList[i].common.commonCodeSj}</option>`;
	      }
	   }
	   return tags;
	}
	
});


//========================================== 대결권해제 ===========================================
	$(document).on("click", ".offProxy", function(){
		
		Swal.fire({
		  title: "대결권을 해제하시겠습니까?",
		  icon: "warning",
		  showCancelButton: true,
		  confirmButtonColor: "#3085d6",
		  cancelButtonColor: "#d33",
		  cancelButtonText: "취소",
		  confirmButtonText: "확인"
		}).then((result) => {
		  if (result.isConfirmed) {
			let prxsanctnNo = $("#prxsanctnNo").val();
			$.ajax({
				type:"put",
				url:`/proxy/${prxsanctnNo}`,
				dataType:"text",
				success:function(rslt){
					if(rslt == "OK"){
						let newButtonTag = `<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#proxyModal">대결권설정</button>`;
						console.log("newButtonTag",newButtonTag);
						$("#buttonArea").html(newButtonTag);
						
					    Swal.fire({
							icon: "success",
							title: "대결권 해제완료!",
							text: "대결권 해제에 성공하였습니다",
							showConfirmButton: false,
							timer: 1500
					    });
	
					} else {
						Swal.fire({
							icon: "error",
							title: "대결권 해제실패!",
							text: "대결권 등록에 실패 하였습니다",
							showConfirmButton: false,
							timer: 1500
						});
					}
				},
			    error: function (request, status, error) {
			        console.log("code: " + request.status)
			        console.log("message: " + request.responseText)
			        console.log("error: " + error);
			    }
			});
		  }
		});
	});


//========================================== 대결권설정 ===========================================
	$('#saveBtn').on("click", function(){
		let prxsanctnCnfer = $("#prxsanctnCnfer").val();
		let alwncDate = $("#alwncDate").val();
		let extshDate = $("#extshDate").val();
		let alwncReason = $("#alwncReason").val();
		
	    if (prxsanctnCnfer == '-1' || alwncDate == '' || extshDate == '' || alwncReason == '') {
			Swal.fire({
				icon: "error",
				title: "대결권 설정실패!",
				text: "모든 값을 입력해주세요!",
				showConfirmButton: false,
				timer: 1500
			});
			$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
			
	        return; 
	    }
		
	
	
		let SanctionByProxyVO = {};
		SanctionByProxyVO.prxsanctnCnfer = prxsanctnCnfer;
		SanctionByProxyVO.alwncDate = alwncDate;
		SanctionByProxyVO.extshDate = extshDate;
		SanctionByProxyVO.alwncReason = alwncReason;
		
		console.log("SanctionByProxyVO======="+SanctionByProxyVO);
		console.log("SanctionByProxyVOJSON======="+JSON.stringify(SanctionByProxyVO));
		
		
		$.ajax({
			type:"post",
			url:"/proxy/new",
			contentType:"application/json",  // post
			data: JSON.stringify(SanctionByProxyVO) ,
			dataType:"json",
			success:function(rslt){
				// JSON.parse(rslt)  jQuery가 몰래해줌
				console.log("서버에서 온 값:", rslt);
	
				if(rslt.msg == "OK"){
					Swal.fire({
						icon: "success",
						title: "대결권 등록완료!",
						text: "대결권 등록이 완료 되었습니다",
						showConfirmButton: false,
						timer: 1500
					});
				
					$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000); 
					let buttonTag = `
							<small class="text-muted">대결권 설정기간 : ${rslt.proxy.alwncDate} 부터 ${rslt.proxy.extshDate} 까지 / 
							대결권 수여자 : [${rslt.proxy.prxsanctnCnferDeptName }] ${rslt.proxy.prxsanctnCnferNm } ${rslt.proxy.prxsanctnCnferRankNm }</small>
							<button type="button" class="btn btn-primary offProxy">대결권해제</button>
						`;
					
					
					$("#buttonArea").html(buttonTag);
					$('#proxyModal').modal('hide');

				} else if(rslt.msg == "PKDUPLICATED") {
					Swal.fire({
						icon: "error",
						title: "대결권 등록실패!",
						text: "대결자나 대결권설정기간이 중복되었습니다.",
						showConfirmButton: false,
						timer: 1500
					});
					$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
					
				} else {
					Swal.fire({
						icon: "error",
						title: "대결권 등록실패!",
						text: "대결권 등록에 실패 하였습니다",
						showConfirmButton: false,
						timer: 1500
					});
					$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
					
				}
			},
		    error: function (request, status, error) {
		        console.log("code: " + request.status)
		        console.log("message: " + request.responseText)
		        console.log("error: " + error);
		    }
		});
	});
	
//========================================== dataPicker설정 ===========================================
$(document).ready(function() {
	$("#alwncDate").datepicker({
		format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
		startDate: '+1d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
		endDate: '+14d',	//달력에서 선택 할 수 있는 가장 느린 날짜. 이후로 선택 불가 ( d : 일 m : 달 y : 년 w : 주)
		autoclose: true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
		calendarWeeks: false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
		clearBtn: false, //날짜 선택한 값 초기화 해주는 버튼 보여주는 옵션 기본값 false 보여주려면 true
		datesDisabled: ['2019-06-24', '2019-06-26'],//선택 불가능한 일 설정 하는 배열 위에 있는 format 과 형식이 같아야함.
		daysOfWeekDisabled: [0, 6],	//선택 불가능한 요일 설정 0 : 일요일 ~ 6 : 토요일
		daysOfWeekHighlighted: [3], //강조 되어야 하는 요일 설정
		// disableTouchKeyboard : false,	//모바일에서 플러그인 작동 여부 기본값 false 가 작동 true가 작동 안함.
		immediateUpdates: false,	//사용자가 보는 화면으로 바로바로 날짜를 변경할지 여부 기본값 :false 
		multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false 
		multidateSeparator: ",", //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
		templates: {
			leftArrow: '&laquo;',
			rightArrow: '&raquo;'
		}, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징 
		showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
		title: "사용일자",	//캘린더 상단에 보여주는 타이틀
		todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false 
		toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
		weekStart: 0,//달력 시작 요일 선택하는 것 기본값은 0인 일요일 
		language: "ko"	//달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
	}).on('hide', function(selected) {
        var startDate = new Date(selected.date.valueOf());
        // 종료일 datepicker의 최소 선택 가능 날짜 설정
        $('#extshDate').datepicker('setStartDate', startDate);
		endDatePicker.datepicker('setDate', new Date(startDate.setDate(startDate.getDate() + 1)));

        // 날짜 범위 유효성 검사
        validateDateRange();
    });
	
	$("#extshDate").datepicker({
		format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
		startDate: '+1d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
		endDate: '+14d',	//달력에서 선택 할 수 있는 가장 느린 날짜. 이후로 선택 불가 ( d : 일 m : 달 y : 년 w : 주)
		autoclose: true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
		calendarWeeks: false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
		clearBtn: false, //날짜 선택한 값 초기화 해주는 버튼 보여주는 옵션 기본값 false 보여주려면 true
		datesDisabled: ['2019-06-24', '2019-06-26'],//선택 불가능한 일 설정 하는 배열 위에 있는 format 과 형식이 같아야함.
		daysOfWeekDisabled: [0, 6],	//선택 불가능한 요일 설정 0 : 일요일 ~ 6 : 토요일
		daysOfWeekHighlighted: [3], //강조 되어야 하는 요일 설정
		// disableTouchKeyboard : false,	//모바일에서 플러그인 작동 여부 기본값 false 가 작동 true가 작동 안함.
		immediateUpdates: false,	//사용자가 보는 화면으로 바로바로 날짜를 변경할지 여부 기본값 :false 
		multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false 
		multidateSeparator: ",", //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
		templates: {
			leftArrow: '&laquo;',
			rightArrow: '&raquo;'
		}, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징 
		showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
		title: "사용일자",	//캘린더 상단에 보여주는 타이틀
		todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false 
		toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
		weekStart: 0,//달력 시작 요일 선택하는 것 기본값은 0인 일요일 
		language: "ko"	//달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
	}).on('hide', function(selected) {
        var endDate = new Date(selected.date.valueOf());
        // 시작일 datepicker의 최대 선택 가능 날짜 설정
        $('#alwncDate').datepicker('setEndDate', endDate);

        // 날짜 범위 유효성 검사
        validateDateRange();
    });

    // 날짜 범위를 검사하는 함수
    function validateDateRange() {
        var startDate = new Date($("#alwncDate").val());
        var endDate = new Date($("#extshDate").val());

        // 시작일이 종료일보다 빠를 경우
        if (startDate >= endDate) {
            // 경고 메시지 표시
			Swal.fire({
				icon: "warning",
				title: "날짜 입력 오류!",
				text: "종료일은 시작일 이후로 설정해주세요.",
				showConfirmButton: false,
				timer: 1500
			});
			$(".swal2-container.swal2-center.swal2-backdrop-show").css("z-index",3000);   // default 1060, 동적생성이라 맹글고 바꿔야 함!
					

            // 선택한 날짜 초기화
            $('#alwncDate').datepicker('clearDates');
            $('#extshDate').datepicker('clearDates');
        }
    }
});