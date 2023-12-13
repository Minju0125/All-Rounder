/**
 * <pre>
 * 
 * </pre>
 * @author 박민주
 * @since 2023. 11. 28.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 29.  박민주       최초작성 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

//회의실 예약 등록 함수
function fn_confRoomReserve() {
    const confReserveVO = {
        confRoomCd: $("#confRoomCd").val(), //회의실코드
        confDate: $("#confRoomUseDate").val(), //사용일자
        confTimeCd: $("#confTimeCd").val(), //사용시간코드
        confReserveEmpCd: $("#confReserveEmpCd").val(), //예약자사번
        confReservePw: $("#confRoomReservePw").val() //취소용 비밀번호
    }
    $.ajax({
        type: "POST",
        url: "/confRoom/reserve",
        data: JSON.stringify(confReserveVO),
        dataType: "json",
        contentType: "application/json; charset=UTF-8",
        success: function (resp) {
            Swal.fire('예약이 완료되었습니다.', '', 'success').then(function () {
                location.reload();
            });
        },
    })
}

//특정 회의실 클릭 이벤트 (모달 오픈)
$(".confRoomContainer").on("click", function () {
	let confRoomCd = $(this).data("confroomcd");
	let confRoomUseDate = moment().format('YYYY-MM-DD'); //오늘날짜
	let nowHours = moment().format('H');
	let allOptions = $("#confTimeCd").find("option");
    $("#confReserveEmpCd").val($("#empCd").val()); //로그인된 직원 정보 기본으로 입력
    $("#confRoomCd").val(confRoomCd);
 	$("#confRoomUseDate").val(confRoomUseDate);
    $('#confRoomReserveModal').modal('show');
	console.log("confRoomCd : "  +confRoomCd );
	console.log("confRoomUseDate : " + confRoomUseDate);
    $.ajax({
        type: "GET",
        url: `/confRoom/reservedTimeCheck/${confRoomCd}`,
        data: {
            confRoomUseDate: confRoomUseDate,
        },
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function (resp) {
            if (resp.length > 0) { //해당 회의실의 해당 날짜에 예약내역이 존재하는 경우
                $.each(resp, function (i, v) {
                    console.log("v ==> ", v);
                    $("#confTimeCd option[value='" + v + "']").prop("disabled", true);
                });
            }
	   		 allOptions.each(function (index, option) {
		        console.log("옵션 " + index + " ==> " + $(option).text().substring(0, 2));
		        var timeCd = $(option).val().toString();
		        if (nowHours > parseInt($(option).text().substring(0, 2))) {
		            $("#confTimeCd option[value='" + timeCd + "']").prop("disabled", true);
		        }
		    }); //옵션 반복문 끝
        }
    }); // ajax 끝
});

function setConfRoomStatus(){
    $.ajax({
        type : "GET",
        url : "/reserveStatus/confRoomStatus",
        contentType : "application/json; charset=utf-8",
        success : function(resp){
            console.log(resp.statusList);
            if(resp.statusList.length > 0){
                for(var i = 0; i < resp.statusList.length; i++){
					console.log(JSON.stringify(resp));
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
                    $(`div[data-confRoomCd=${reserveCd}]`).append(statusDivTag);
                }
            }
        },
        error : function(xhr){
            console.log("resp.success 가져오기 ");
        }
    })
}

$(function () {
    //회의실 각 칸마다 회의실 정보 넣어주기
    setConfRoomStatus();
    
})
