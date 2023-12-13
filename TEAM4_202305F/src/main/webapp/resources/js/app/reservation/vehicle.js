/**
 * 
 */

//예약버튼 onclick 이벤트
function fn_vReserve() {
	const reservation = {
		vhcleUseDate: $("#vhcleUseDate").val(),
		vhcleReservePur: $("#vhcleReservePur").val(),
		vhcleReserveEmpCd: $("#vhcleReserveEmpCd").val(),
		vhcleReservePw: $("#vhcleReservePw").val(),
		vhcleUseTimeCd: $("#vhcleUseTimeCd").val(),
		vhcleCd: $("#thisVhcleCd").val()
	};

	vhcleUseTimeCdText = "";
	if(reservation.vhcleUseTimeCd =="R_V1"){
		vhcleUseTimeCdText ="09:00-13:00";
	}else if(reservation.vhcleUseTimeCd == "R_V2"){
		vhcleUseTimeCdText ="13:00-18:00";
	}

	console.log("모달에 입력된 예약내용 ==> " + JSON.stringify(reservation));

	$.ajax({
		type: "POST",
		url: "/vehicle/reserve",
		data: JSON.stringify(reservation),
		dataType: "json",
		contentType: "application/json",
		success: function (resp) {
			console.log("성공입니당 ==> ", resp.success);
			if(resp.success=="OK"){
				Swal.fire("예약이 완료되었습니다.", `${reservation.vhcleUseDate}/${vhcleUseTimeCdText}`, "success").then(function(){
					location.reload(); //내 예약보기로 이동
				});
			}else{
				Swal.fire("예약 실패", "다시 시도해주세요.", "error");
			}
		},
		error: function (xhr) {
			Swal.fire("예약실패", "", "error");
		}
	})
}

//시간표 날짜 세팅
function setDate(dateInfo) {
	if (!dateInfo.mondayDate) { //시작일이 설정되지 않은 경우(로딩 초기) 오늘이 기준일
		dateInfo.mondayDate = moment().day(1).format("YYYY-MM-DD"); //시작일을 이번주 월요일로 설정
	}

	let mondayDate = moment(dateInfo.mondayDate);

	//월요일을 기준으로 화수목금 날짜 세팅
	dateInfo.tuesdayDate = mondayDate.clone().add(1, 'day').format('YYYY-MM-DD');
	dateInfo.wednesdayDate = mondayDate.clone().add(2, 'day').format('YYYY-MM-DD');
	dateInfo.thursdayDate = mondayDate.clone().add(3, 'day').format('YYYY-MM-DD');
	dateInfo.friDate = mondayDate.clone().add(4, 'day').format('YYYY-MM-DD');

	console.log("할당 및 변환 후 (moment 사용) dateInfo: " + JSON.stringify(dateInfo));

	//시간표 thead 에 들어갈 날짜,요일 변환 및 값 할당
	monTag = `(${moment(dateInfo.mondayDate).format("MM/DD")})<br>MON`;
	tueTag = `(${moment(dateInfo.tuesdayDate).format("MM/DD")})<br>TUE`;
	wedTag = `(${moment(dateInfo.wednesdayDate).format("MM/DD")})<br>WED`;
	thuTag = `(${moment(dateInfo.thursdayDate).format("MM/DD")})<br>THU`;
	friTag = `(${moment(dateInfo.friDate).format("MM/DD")})<br>FRI`;

	$(".mon").html(monTag);
	$(".tue").html(tueTag);
	$(".wed").html(wedTag);
	$(".thu").html(thuTag);
	$(".fri").html(friTag);

	//속성값으로 실제 사용가능한 형식의 날짜 데이터
	$(".mon").attr('data-realDate', dateInfo.mondayDate);
	$(".tue").attr('data-realDate', dateInfo.tuesdayDate);
	$(".wed").attr('data-realDate', dateInfo.wednesdayDate);
	$(".thu").attr('data-realDate', dateInfo.thursdayDate);
	$(".fri").attr('data-realDate', dateInfo.friDate);

	//시간표 상단에 표시될 startDate와 endDate text 업데이트
	$("#startDate").text(dateInfo.mondayDate);
	$("#endDate").text(dateInfo.friDate);
}

//랜덤색상 만들기
function makeRndColor() {
	const letters = '89ABCDEF';
	let color = '#';
	for (let i = 0; i < 6; i++) {
		color += letters[Math.floor(Math.random() * letters.length)];
	}
	return color;
}

//예약 시간표 출력
function setTimeTable(resp) {
	if (resp.resultList.length > 0) { //예약 내역이 존재하는 경우만
		for (let i = 0; i < resp.resultList.length; i++) {
			const timetableData = {};
			timetableData.thisDate = resp.resultList[i].vhcleUseDate; //날짜
			timetableData.thisDay = new Date(timetableData.thisDate).getDay(); //요일
			timetableData.vhcleReserveEmpDeptNm = resp.resultList[i].vhcleReserveEmpDeptNm;//부서
			timetableData.vhcleReserveEmpRankNm = resp.resultList[i].vhcleReserveEmpRankNm;//직급
			timetableData.vhcleReserveEmpNm = resp.resultList[i].vhcleReserveEmpNm;//이름
			timetableData.vhcleReservePur = resp.resultList[i].vhcleReservePur;//사용 목적
			if (!resp.resultList[i].vhcleReserveEmpProfileImg) {
				timetableData.vhcleReserveEmpProfileImg = "/resources/assets/img/avatars/5.png";
			} else {
				timetableData.vhcleReserveEmpProfileImg = resp.resultList[i].vhcleReserveEmpProfileImg;//프로필이미지
			}
			timetableData.rndColor = makeRndColor();
			var thisTimeCd = resp.resultList[i].vhcleUseTimeCd; //시간대 코드 문자열
			if(thisTimeCd == "R_V1"){
				timetableData.thisTimeCd = thisTimeCd;
				timetableData.thisTime = "09:00-13:00";
			}else if(thisTimeCd == "R_V2"){
				timetableData.thisTimeCd = thisTimeCd;
				timetableData.thisTime = "13:00-18:00";
			}
			makeTimeTable(timetableData);
		} //내역 list end
	}//내역이 존재하는 경우 if 문 end
}//setTimeTable funcion 끝

//실제로 시간표에 ui 입히는 함수
function makeTimeTable(timetableData) {
	var divTag = `
	<div style="white-space: nowrap;">
	<div style="display: inline-block;">
			<div>${timetableData.thisDate}</div>
			<div>${timetableData.thisTime}</div>
			<div>[${timetableData.vhcleReserveEmpDeptNm}|${timetableData.vhcleReserveEmpRankNm}] ${timetableData.vhcleReserveEmpNm} <img width="32px" height="32px" src="${timetableData.vhcleReserveEmpProfileImg}" class="rounded-circle"/></div>
			<div>${timetableData.vhcleReservePur}</div>
			</div>
			</div>
	`;
	$(`.${timetableData.thisDay}.${timetableData.thisTimeCd}`).css("background", `${timetableData.rndColor}70`);
	$(`.${timetableData.thisDay}.${timetableData.thisTimeCd}`).removeClass("clickable");
	$(`.${timetableData.thisDay}.${timetableData.thisTimeCd}`).append(divTag);
}

function getVehicleInfo(vhcleCd, callback) {
	$.ajax({
		type: "GET",
		url: `/vehicle/oneVehicleInfo/${vhcleCd}`,
		contentType: "application/json;charset=utf-8",
		success: function (resp) {
			console.log("가져온 결과 ---> " + resp.vehicle);
			callback(resp); // 데이터를 콜백 함수로 전달
		},
		error: function (xhr) {
			console.log("오류 ==> " + xhr);
		}
	});
}

//차량목록의 차량 사용 가능 상태 여부를 세팅하는 함수
function setVehicleStatus(){
	$.ajax({
		type:"GET",
		url : "/reserveStatus/vehicleStatus",
		contentType : "application/json; charset=UTF-8",
		success : function (resp){
			if(resp.statusList){
				for(var i=0; i<resp.statusList.length; i++){
					var reserveCd = resp.statusList[i].reserveCd;
					var status = resp.statusList[i].status;
					var btnClass = "";
					if(status == "예약가능"){
						btnClass = "btn-success";
					}else{
						btnClass = "btn-danger";
					}
					var statusDivTag = `
						<button type="button" style="height:35px; margin-right:20px" class="btn rounded-pill ${btnClass} status-flag" id="${status}">${status}</button>
					`;
					$(`div[data-vhcleCd=${reserveCd}] .statusDiv`).append(statusDivTag);
				}
			}
		},
		error : function(xhr){
			console.log("resp.statusList 를 못가져옴 !");
		}
	})
}

$(function () {
	//현재 차량의 사용가능 상태여부
	setVehicleStatus();
	$('#vhcleUseTimeCd').prop('size', 1);
	dateInfo = {
		vhcleCd: "", 
		standardDate: "",
		mondayDate: "",
		tuesdayDate: "",
		wednesdayDate: "",
		thursdayDate: "",
		friDate: "",
	};
	setDate(dateInfo);
	//오늘이 이 일주일 사이에 포함되어 있다면,
	const today = moment().format('YYYY-MM-DD');
	
	$("#vhcleUseDate").datepicker({
			format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
			startDate: '0d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
			endDate: '+10d',	//달력에서 선택 할 수 있는 가장 느린 날짜. 이후로 선택 불가 ( d : 일 m : 달 y : 년 w : 주)
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
		}); //datepicker end
	
///////////////////////////////////////////////여기 나중에///
	$(".vehicleOne").on("click", function () {
		$(".oneBox")
			.css("transition", "")
			.css("box-shadow", "");

		$(this).find('.oneBox')
			.css("transition", "0.3s ease-in-out")
			.css("box-shadow", "0px 0px 10px #696cff");

		$(".timetable").each(function () {
			if (!$(this).hasClass('clickable')) {
				$(this).addClass('clickable');
			}
		});
		$(".timetable").css("background", ""); // 백그라운드 색상 제거
		$(".timetable").empty(); // 내용 비우기

		dateInfo.vhcleCd = $(this).data('vhclecd'); // 데이터 속성 가져오기
		var vhcleCd = dateInfo.vhcleCd;

		var startDate = moment(dateInfo.mondayDate).format("YYYY-MM-DD");
		var endDate = moment(dateInfo.friDate).format("YYYY-MM-DD");

		//해당 vhcleCd 을 통해서 예약 내역 확인가져오기!
		$.ajax({
			type: "GET",
			url: `/vehicle/oneVehicleReservation/${vhcleCd}`,
			data: {
				startDate: startDate,
				endDate: endDate
			},
			contentType: "application/json",
			success: function (resp) {
				console.log("성공 ==> ", resp);
				setTimeTable(resp);
			},
			error: function (xhr) {
				console.log("실패 ==> ", xhr);
			}
		})
		console.log("vhcleCd = " + vhcleCd);
	});

	// 일주일 전으로 startDate & endDate 설정
	$("#beforeIcon").on("click", function () {
		$(".timetable").css("background", ""); // 백그라운드 색상 제거
		$(".timetable").empty(); // 내용 비우기
		//mondayDate 를 일주일 전으로 할당 후 startdate 를 넘겨줌
		var mondayDate = moment(dateInfo.mondayDate).subtract("7", "day").format("YYYY-MM-DD");
		dateInfo.mondayDate = mondayDate;

		setDate(dateInfo);

		// AJAX 호출 - startDate와 endDate를 업데이트된 값으로 전달
		const vhcleCd = dateInfo.vhcleCd;
		$.ajax({
			type: "GET",
			url: `/vehicle/oneVehicleReservation/${vhcleCd}`,
			data: {
				startDate: moment(dateInfo.mondayDate).format("YYYY-MM-DD"),
				endDate: moment(dateInfo.friDate).format("YYYY-MM-DD")
			},
			contentType: "application/json",
			success: function (resp) {
				console.log(resp);
				setTimeTable(resp);
			},
			error: function (xhr) {
				console.log(xhr);
			}
		});
	});
	// 일주일 후로 startDate & endDate 설정
	$("#afterIcon").on("click", function () {
		$(".timetable").css("background", ""); // 백그라운드 색상 제거
		$(".timetable").empty(); // 내용 비우기
		//mondayDate 를 일주일 전으로 할당 후 startdate 를 넘겨줌
		var mondayDate = moment(dateInfo.mondayDate).add("7", "day").format("YYYY-MM-DD");
		dateInfo.mondayDate = mondayDate;

		// setDate 함수 호출하여 날짜 설정
		setDate(dateInfo);

		// AJAX 호출 - startDate와 endDate를 업데이트된 값으로 전달
		const vhcleCd = dateInfo.vhcleCd;
		$.ajax({
			type: "GET",
			url: `/vehicle/oneVehicleReservation/${vhcleCd}`,
			data: {
				startDate: moment(dateInfo.mondayDate).format("YYYY-MM-DD"),
				endDate: moment(dateInfo.friDate).format("YYYY-MM-DD")
			},
			contentType: "application/json",
			success: function (resp) {
				console.log(resp);
				setTimeTable(resp);
			},
			error: function (xhr) {
				console.log(xhr);
			}
		});
	});

	$(".clickable").on("click", function (event) { //clickable 없어도 왜?
		console.log("this가 뭐야 --> ", this);
		if (dateInfo.vhcleCd !== null && dateInfo.vhcleCd.trim() !== "") {

			if ($(this).text().trim() == '') {
				var timeCd = "";
				if ($(this).hasClass('R_V1')) {
					timeCd = 'R_V1';
				} else {
					timeCd = 'R_V2';
				}

				var thOfClickedTD = $(this).closest('tr').closest('table').find('thead th').eq($(this).index());
				var realDateValue = thOfClickedTD.data('realdate');

				//넘겨야하는 데이터 : 날짜, 시간, 차량코드
				console.log("차량번호 ==> ", dateInfo.vhcleCd);
				console.log('시간코드~ ==> ' + timeCd);
				console.log('해당하는 요일의 data-realdate 값:', realDateValue);

				$("#vhcleReserveEmpCd").val($("#empCd").val());
				$("#vhcleUseDate").val(realDateValue);
				$("#vhcleUseTimeCd").val(timeCd);
				$(".form-select").css("padding", "0.4375rem 2rem 0.7rem 2rem");

				$('#backDropModal').modal('show');

				//해당요일의 예약완료된 시간대는 disanbled 처리하기
				
				getVehicleInfo(dateInfo.vhcleCd, function (resp) {
					let vehicleData = resp.vehicle;
					let reserveLsit = resp.reserveList;

					console.log('받아온 차량 데이터:', vehicleData);
					//모델명, 차량번호, n인승, 이미지
					var vModelText = `${vehicleData.vhcleModel}(${vehicleData.vhcleCapacity}인승)`;
					
					console.log("특정 차량의 예약 내역 ==> ", reserveLsit);
					//내가 선택한 날짜 realDateValue , 시간 timeCd 과 일치하는 예약내역이 존재한다면 
					 
					/* 나중에~~
					//disabled 처리하기
					for(var i=0; i<reserveLsit.length; i++){
						var reserveTimeCds = JSON.parse(reserveLsit[i].vhcleUseTimeCd);//시간대 코드 JSON
						for (let j = 0; j < reserveTimeCds.length; j++) {
							if (reserveTimeCds[j] == "R_V1") {
								$(`#vhcleUseTimeCd option[value=".${reserveTimeCds[j]}"]`).prop('disabled', true);
							} else {
								$(`#vhcleUseTimeCd option[value=".${reserveTimeCds[j]}"]`).prop('disabled', true);
							}
						}
					}
					*/
					$("#vModel").text(vModelText);
					$("#vNo").text(vehicleData.vhcleNo);
					$("#modalImg").attr("src", vehicleData.vhcleImg);
					$("#thisVhcleCd").val(vehicleData.vhcleCd);
				});
			} else {
				$('#backDropModal').modal('hide');
				Swal.fire("예약불가한 차량입니다.", "예약중 / 사용중 / 사용완료", "error");
			}
		} else {
			Swal.fire('차량을 먼저 선택해주세요.', '', 'error');
		}
	})
});