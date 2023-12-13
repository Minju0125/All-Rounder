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
 * 2023. 11. 28.  박민주       최초작성 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

const columnDefsConf = [
	{ checkboxSelection: true, headerCheckboxSelection: true, width: "50px" },
	{ field: "confRoomCd", headerName: "회의실 코드", sortable: true },
	{ field: "confRoomNm", headerName: "호실명", editable: true },
	{ field: "confRoomCapacity", headerName: "수용인원", editable: true },
	{
        field: "confYn",
        headerName: "회의실 상태",
        editable: true,
        sortable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
			values: ['Y', 'N']
		},
		valueFormatter: function (params) {
			switch (params.value) {
				case 'Y':
					return '사용가능';
				case 'N':
					return '사용중지';
				default:
					return params.value;
			}
		}
	}
	// { field: "vhcleImgUrl", headerName:"모델"},
];

const rowDataConf = []; //초기값

// 설정 옵션: 중요, 위에 정의한 것들이 여기서 통합됨에 주목
const gridOptionsConf = {
	columnDefs: columnDefsConf,
	rowSelection: 'multiple',
	rowData: rowData,
	defaultColDef: {
		filter: true,
		resizable: true,
		headerClass: "centered",
		sortable:true
	},
	pagination: true, //페이지설정
	paginationAutoPageSize: true,
	paginationPageSize: 5,
	localText: { noRowsToShow: '조회 결과가 없습니다.' }, //데이터가 없는 경우 보여 주는 사용자 메시지
	suppressMovableColumns: "true",//헤더고정
	onCellEditingStarted: event => {
		console.log(event);
		console.log("편집시작!");
	},
	onCellEditingStopped: event => {
		if (event.newValue !== event.oldValue) { //새로운 데이터 입력시 update 함수 실행
			updateDataConf(event);
		}
	},onGridReady: function (event) {
            event.api.sizeColumnsToFit();
        }
};

const updateDataConf = (event)=>{
	var confVO =event.data;
	var xhr = new XMLHttpRequest();
	var confRoomCd = event.data.confRoomCd;
	xhr.open('PUT', `/adConfRoom/${confRoomCd}`, true);
	xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
	xhr.setRequestHeader('content-type', 'application/json');
	xhr.onreadystatechange = function(){
		if (xhr.readyState == 4 && xhr.status == 200 ){
			console.log("수정 성공?");
		}
	}
	xhr.send(JSON.stringify(confVO));
}


const getDataConf = () => {
	var xhr = new XMLHttpRequest(); //비동기 통신을 담당하는 자바스크립트 객체
	xhr.open("GET", "/adConfRoom", true); //요청 초기화
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) { //수신완료&&통신성공
			let result = JSON.parse(xhr.responseText);
			console.log(result.confRoomList);
			for (var i = 0; i < result.confRoomList.length; i++) {
				if (result.confRoomList[i].confYn == "Y") {
					result.confRoomList[i].confYn = "사용가능";
				} else {
					result.confRoomList[i].confYn = "사용불가";
				}
				gridOptionsConf.api.setRowData(result.confRoomList);
			}
		}
	};
	xhr.send();
}

//회의실 등록 모달에서 save 클릭 시 발생하는 이벤트 함수
function fn_confRoomInsert() {
	// fSerialize(senderForm);

	const confRoomVO = {};
	var confRoomNm = document.querySelector("#confRoomNm").value;
	var confRoomCapacity = document.querySelector("#confRoomCapacity").value;
	var confRoomCd = document.querySelector("#confRoomCd").value;

	confRoomVO.confRoomNm = confRoomNm;
	confRoomVO.confRoomCapacity = confRoomCapacity;
	confRoomVO.confRoomCd = confRoomCd;

	console.log("confRoomVO ==> ", confRoomVO);
	console.log("JSON.stringify(confRoomVO) ==> ", JSON.stringify(confRoomVO));

	var xhr = new XMLHttpRequest();
	xhr.open("POST", "/adConfRoom", true);
	// xhr.responseType = 'json';
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
	xhr.onreadystatechange = function () {
		if (xhr.readyState == 4 && xhr.status == 200) {
			console.log("성공 ! ==> ", xhr.responseText);
			Swal.fire('신규 회의실이 등록되었습니다.', '', 'success').then((result) => {
				location.reload();
			})
		}
	};
	xhr.send(JSON.stringify(confRoomVO));
}


//회의실 ag-grid 에서 삭제 버튼 클릭시
function fn_confRoomDelBtn() {
	var selectedData = gridOptionsConf.api.getSelectedRows();

	if (selectedData.length == 0) {
		console.log("no data");
		Swal.fire("선택된 데이터가 없습니다.", '', 'error');
	} else if (selectedData.length == 1) {
		var selectedConfRoomCd = selectedData[0]['confRoomCd'];
		console.log("selectedConfRoomCd ==> " + selectedConfRoomCd);
		var xhr = new XMLHttpRequest();
		xhr.open('DELETE', `/adConfRoom/${selectedConfRoomCd}`, true);
		xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
		xhr.onreadystatechange = function () {
			if (xhr.readyState == 4 && xhr.status == 200) {
				console.log("성공 ! ==> ", xhr.responseText);
				if (xhr.responseText == 'OK') {
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
			var selectedConfRoomCd = selectedData[i]['confRoomCd'];
			var xhr = new XMLHttpRequest();
			console.log("selectedConfRoomCd ==> " + selectedConfRoomCd);
			xhr.open('DELETE', `/adConfRoom/${selectedConfRoomCd}`, true);
			xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
			xhr.onreadystatechange = function () {
				if (xhr.readyState == 4 && xhr.status == 200) {
					console.log(i + "번째 데이터 삭제 성공 ! ==> ", xhr.responseText);
					Swal.fire(`삭제되었습니다.`, '', 'success').then((result) => {
						location.reload();
					})
				}
			}
			xhr.send();
		}
	}
}


$(function () {
	var confRoomList = document.querySelector("#confRoomList");
	new agGrid.Grid(confRoomList, gridOptionsConf);
	getDataConf();  // 데이터 불러오깅
});