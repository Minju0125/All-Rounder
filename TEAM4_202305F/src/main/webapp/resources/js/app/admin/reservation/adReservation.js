/**
 * <pre>
 * 
 * </pre>
 * @author 박민주
 * @since 2023. 12. 01
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 12. 01.  박민주       최초작성 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

//------------------------------차량 예약 목록 ag-grid 관련------------------------------
const columnDefsVR = [
	{ checkboxSelection: true, headerCheckboxSelection: true },
	{ field: "vhcleReserveCd", headerName: "차량예약 코드", sortable: true },
	{ field: "vhcleUseDate", headerName: "사용날짜", editable: true },
	{ 
		field: "vhcleUseTimeCd"
		, headerName: "사용시간"
		, editable: true
		, sortable: true
		,cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
			values: ['R_V1', 'R_V2']
		},
		valueFormatter: function (params) {
			switch (params.value) {
				case 'R_V1':
					return '09:00-13:00';
				case 'R_V2':
					return '13:00-18:00';
				default:
					return params.value;
			}
		}
	},
	{ field: "vhcleReservePur", headerName: "목적", editable: true },
	{ field: "vhcleReservePw", headerName: "취소용 비밀번호", editable: true },
	{ field: "vhcleCd", headerName: "차량코드"},
	{ field: "vhcle.vhcleModel", headerName: "차량모델"},
	{ field: "vhcle.vhcleNo", headerName: "차량번호"},
	{ field: "vhcleReserveEmpNm", headerName: "예약자명"},
	{ field: "vhcleReserveEmpDeptNm", headerName: "예약자 부서명"},
	{ field: "vhcleReserveEmpRankNm", headerName: "예약자 직급"}
];

const rowDataVR = []; //초기값

// 설정 옵션: 중요, 위에 정의한 것들이 여기서 통합됨에 주목
const gridOptionsVR = {
	columnDefs: columnDefsVR,
	rowSelection : 'multiple',
	rowData: rowDataVR,
	defaultColDef: {
		flex: 1,       // 자동으로 같은 사이즈롱
		filter: true,
		resizable: true,
		minWidth: 100,
		headerClass: "centered"
	},
	pagination: true, //페이지설정
	paginationAutoPageSize: true,
	paginationPageSize: 5,
	localText:{noRowsToShow: '조회 결과가 없습니다.'}, //데이터가 없는 경우 보여 주는 사용자 메시지
	suppressMovableColumns:"true",//헤더고정
	onCellEditingStarted: event => {
		console.log(event);
		console.log("편집시작!");
	},
	onCellEditingStopped: event => {
		if(event.newValue !== event.oldValue){ //새로운 데이터 입력시 update 함수 실행
			updateData(event);
		}
	}
};

const getDataVR = () => {
	var xhr = new XMLHttpRequest(); //비동기 통신을 담당하는 자바스크립트 객체
	xhr.open("GET", "/adReservation/vhcleReservationList", true); //요청 초기화
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) { //수신완료&&통신성공
		    let result = JSON.parse(xhr.responseText);
		    let rowData = result.vrList;
			console.log(result);
		    gridOptionsVR.api.setRowData(rowData);
		}
	};
	xhr.send();
}

//------------------------------회의실 예약 목록 ag-grid 관련------------------------------
const columnDefsCR = [
	{ checkboxSelection: true, headerCheckboxSelection: true },
	{ field: "confReserveCd", headerName: "회의실예약 코드", sortable: true },
	{ field: "confDate", headerName: "사용날짜", editable: true },
	{ 
		field: "confTimeCd"
		, headerName: "사용시간"
		, editable: true
		, sortable: true
		, cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
			values: ['R_C1', 'R_C2', 'R_C3', 'R_C4', 'R_C5', 'R_C6', 'R_C7', 'R_C8', 'R_C9']
		},
		valueFormatter: function (params) {
			switch (params.value) {
				case 'R_C1':
					return '09:00-10:00';
				case 'R_C2':
					return '10:00-11:00';
				case 'R_C3':
					return '11:00-12:00';
				case 'R_C4':
					return '12:00-13:00';
				case 'R_C5':
					return '13:00-14:00';
				case 'R_C6':
					return '14:00-15:00';
				case 'R_C7':
					return '15:00-16:00';
				case 'R_C8':
					return '16:00-17:00';
				default:
					return '17:00-18:00';
			}
		}
		 },
	{ field: "confReservePw", headerName: "취소용 비밀번호", editable: true },
	{ field: "confRoomCd", headerName: "회의실코드", editable: true },
	{ field: "confRoom.confRoomNm", headerName: "회의실이름", editable: true },
	{ field: "confReserveEmpNm", headerName: "예약자명", editable: true },
	{ field: "confReserveEmpDeptNm", headerName: "예약자 부서명", editable: true },
	{ field: "confReserveEmpRankNm", headerName: "예약자 직급", editable: true }
];

const rowDataCR = []; //초기값

// 설정 옵션: 중요, 위에 정의한 것들이 여기서 통합됨에 주목
const gridOptionsCR = {
	columnDefs: columnDefsCR,
	rowSelection : 'multiple',
	rowData: rowDataCR,
	defaultColDef: {
		flex: 1,       // 자동으로 같은 사이즈롱
		filter: true,
		resizable: true,
		minWidth: 100,
		headerClass: "centered"
	},
	pagination: true, //페이지설정
	paginationAutoPageSize: true,
	paginationPageSize: 5,
	localText:{noRowsToShow: '조회 결과가 없습니다.'}, //데이터가 없는 경우 보여 주는 사용자 메시지
	suppressMovableColumns:"true",//헤더고정
	onCellEditingStarted: event => {
		console.log(event);
		console.log("편집시작!");
	},
	onCellEditingStopped: event => {
		if(event.newValue !== event.oldValue){ //새로운 데이터 입력시 update 함수 실행
			updateData(event);
		}
	}
};

const getDataCR = () => {
	var xhr = new XMLHttpRequest(); //비동기 통신을 담당하는 자바스크립트 객체
	xhr.open("GET", "/adReservation/confReservationList", true); //요청 초기화
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) { //수신완료&&통신성공
		    let result = JSON.parse(xhr.responseText);
		    let rowData = result.crList;
			console.log(result);
		    gridOptionsCR.api.setRowData(rowData);
		}
	};
	xhr.send();
}


//----------------------------------------차량 예약 삭제버튼 클릭시----------------------------------------
function fn_vReservationDelBtn(){
	var selectedData = gridOptionsVR.api.getSelectedRows();

	if (selectedData.length == 0) {
		console.log("no data");
		Swal.fire("선택된 데이터가 없습니다.", '', 'error');
	} else if (selectedData.length == 1) {
		var selectedVhcleReserveCd = selectedData[0]['vhcleReserveCd'];
		console.log("selectedVhcleReserveCd ==> " + selectedVhcleReserveCd);
		var xhr = new XMLHttpRequest();
		xhr.open('DELETE', `/adReservation/vhcle/${selectedVhcleReserveCd}`, true);
		xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
		xhr.onreadystatechange = function () {
			if (xhr.readyState == 4 && xhr.status == 200) {
				console.log("성공 ! ==> ", xhr.responseText);
				if ((JSON.parse(xhr.responseText)).success == 'OK') {
					Swal.fire('삭제되었습니다.', '', 'success').then((result) => {
						location.reload();
					})
				} else {
					Swal.fire('삭제 실패하였습니다.', '', 'error').then((result) => {
						location.reload();
					})
				}
			}
		}
		xhr.send();
	} else {
		for (var i = 0; i < selectedData.length; i++) {
			var selectedVhcleReserveCd = selectedData[i]['vhcleReserveCd'];
			var xhr = new XMLHttpRequest();
			console.log("selectedVhcleReserveCd ==> " + selectedVhcleReserveCd);
			xhr.open('DELETE', `/adReservation/vhcle/${selectedVhcleReserveCd}`, true);
			xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
			xhr.onreadystatechange = function () {
				if (xhr.readyState == 4 && xhr.status == 200) {
					console.log("성공 ! ==> ", xhr.responseText);
					if ((JSON.parse(xhr.responseText)).success == 'OK') {
						Swal.fire('삭제되었습니다.', '', 'success').then((result) => {
							location.reload();
						})
					} else {
						Swal.fire('삭제 실패하였습니다.', '', 'error').then((result) => {
							location.reload();
						})
					}
				}
			}
			xhr.send();
		}
	}
}

//----------------------------------------회의실 예약 삭제버튼 클릭시----------------------------------------
function fn_cReservationDelBtn(){
	var selectedData = gridOptionsCR.api.getSelectedRows();

	if (selectedData.length == 0) {
		console.log("no data");
		Swal.fire("선택된 데이터가 없습니다.", '', 'error');
	} else if (selectedData.length == 1) {
		var selectedConfReserveCd = selectedData[0]['confReserveCd'];
		console.log("selectedConfReserveCd ==> " + selectedConfReserveCd);
		var xhr = new XMLHttpRequest();
		xhr.open('DELETE', `/adReservation/conf/${selectedConfReserveCd}`, true);
		xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
		xhr.onreadystatechange = function () {
			if (xhr.readyState == 4 && xhr.status == 200) {
				console.log("성공 ! ==> ", xhr.responseText);
				if ((JSON.parse(xhr.responseText)).success == 'OK') {
					Swal.fire('삭제되었습니다.', '', 'success').then((result) => {
						location.reload();
					})
				} else {
					Swal.fire('삭제 실패하였습니다.', '', 'error').then((result) => {
						location.reload();
					})
				}
			}
		}
		xhr.send();
	} else {
		for (var i = 0; i < selectedData.length; i++) {
			var selectedConfReserveCd = selectedData[i]['confReserveCd'];
			var xhr = new XMLHttpRequest();
			console.log("selectedConfReserveCd ==> " + selectedConfReserveCd);
			xhr.open('DELETE', `/adReservation/conf/${selectedConfReserveCd}`, true);
			xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
			xhr.onreadystatechange = function () {
				if (xhr.readyState == 4 && xhr.status == 200) {
					console.log("성공 ! ==> ", xhr.responseText);
					if ((JSON.parse(xhr.responseText)).success == 'OK') {
						Swal.fire('삭제되었습니다.', '', 'success').then((result) => {
							location.reload();
						})
					} else {
						Swal.fire('삭제 실패하였습니다.', '', 'error').then((result) => {
							location.reload();
						})
					}
				}
			}
			xhr.send();
		}
	}
}


$(function () {
	//차량
	var vReservationList = document.querySelector("#vReservationList");
	new agGrid.Grid(vReservationList, gridOptionsVR);
	getDataVR();  // 데이터 불러오깅
	
	//회의실
	var cReservationList = document.querySelector("#cReservationList");
	new agGrid.Grid(cReservationList, gridOptionsCR);
	getDataCR();  // 데이터 불러오깅

});