


/**
 * <pre>
 * 전년 월별 대비 자원의 사용률
 * </pre>
 * @author 박민주
 * @since 2023. 12. 05
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 12. 05. 박민주       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

const context = document.getElementById('reservationChart').getContext('2d');
const labelMonths = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

let lastYearData = Array(12).fill(0); // 작년 월별 데이터 배열 초기화
let thisYearData = Array(12).fill(0); // 올해 월별 데이터 배열 초기화

let thisyearLabel = "";
let lastyearLabel = "";

let reserve ="";

function getMakeLabel(reserveType) {
    reserve = reserveType;
    if (reserveType == "vehicle") {
        thisyearLabel = "올해 월별 차량 사용 건수";
        lastyearLabel = "작년 월별 차량 사용 건수";
        $("#confChart").css("background-color", "");
        $("#vehicleChart").css("background-color", "yellow");
    } else {
        thisyearLabel = "올해 월별 회의실 사용 건수";
        lastyearLabel = "작년 월별 회의실 사용 건수";
        $("#vehicleChart").css("background-color", "");
        $("#confChart").css("background-color", "yellow");
    }
}

function getMakeData(resp) {
	lastYearData = Array(12).fill(0);
    thisYearData = Array(12).fill(0);
	if(reserve=="vehicle"){
		// 작년 데이터 누적
		resp.reserveLast.forEach(reservation => {
			let useDate = moment(reservation.vhcleUseDate);
			let useDateMonth = useDate.month(); // 월 인덱스(0부터 시작)
			lastYearData[useDateMonth]++; // 해당 월에 데이터 추가
		});
	
		// 올해 데이터 누적
		resp.reserveThis.forEach(reservation => {
			let useDate = moment(reservation.vhcleUseDate);
			let useDateMonth = useDate.month(); // 월 인덱스(0부터 시작)
			thisYearData[useDateMonth]++; // 해당 월에 데이터 추가
		});
	}else{ //회의실
		// 작년 데이터 누적
		resp.reserveLast.forEach(reservation => {
			let useDate = moment(reservation.confDate);
			let useDateMonth = useDate.month(); // 월 인덱스(0부터 시작)
			lastYearData[useDateMonth]++; // 해당 월에 데이터 추가
		});
	
		// 올해 데이터 누적
		resp.reserveThis.forEach(reservation => {
			let useDate = moment(reservation.confDate);
			let useDateMonth = useDate.month(); // 월 인덱스(0부터 시작)
			thisYearData[useDateMonth]++; // 해당 월에 데이터 추가
		});
	}
    drawChart(); // 데이터 누적 후 차트 업데이트
}

function drawChart() {
	getMakeLabel(reserve);
    const data = {
        labels: labelMonths,
        datasets: [
            {
                label: lastyearLabel,
                data: lastYearData,
                backgroundColor: 'rgba(105, 108, 255,  0.7)'
            },
            {
                label: thisyearLabel,
                data: thisYearData,
                backgroundColor: 'rgba(255, 171, 0, 0.7)'
            }
        ]
    };

    // 차트 생성
    const reservationChart = new Chart(context, {
        type: 'bar',
        data: data,
        options: options
    });
}

function getMakeConfroomCharts() {
    // ajax 로 가져와서
    // 월별로 담기
    reserve = "confroom";
    $.ajax({
        type: "GET",
        url: `/analysis/monthlyUsageRateReserve/${reserve}`,
        contentType: "application/json; charset=utf-8",
        success: function (resp) {
            //console.log("가져오기 올해 " + JSON.stringify(resp.reserveThis));
            //console.log("가져오기 작년" + JSON.stringify(resp.reserveLast));
            getMakeData(resp);
        },
        error: function (error) {
            console.log("가져오기 실패" + error);
        }
    })
}

function getMakeVehicleCharts() {
    // ajax 로 가져와서
    // 월별로 담기
    reserve = "vehicle";
    $.ajax({
        type: "GET",
        url: `/analysis/monthlyUsageRateReserve/${reserve}`,
        contentType: "application/json; charset=utf-8",
        success: function (resp) {
            console.log("가져오기 올해 " + JSON.stringify(resp.reserveThis));
            console.log("가져오기 작년" + JSON.stringify(resp.reserveLast));
            getMakeData(resp);
        },
        error: function (error) {
            console.log("가져오기 실패" + error);
        }
    })
}

// 페이지 로드 직후, 차량 차트 로드
$(function () {
    getMakeConfroomCharts();
})

// 회의실 차트 클릭 시
$("#confChart").on("click", function () {
    $("#reservationChart").empty();
    $(this).css("background-color", "yellow");
    $("#vehicleChart").css("background-color", "");
    getMakeConfroomCharts();
});

// 차량 차트 클릭 시
$("#vehicleChart").on("click", function () {
    $("#reservationChart").empty();
    $(this).css("background-color", "yellow");
    $("#confChart").css("background-color", "");
    getMakeVehicleCharts();
});