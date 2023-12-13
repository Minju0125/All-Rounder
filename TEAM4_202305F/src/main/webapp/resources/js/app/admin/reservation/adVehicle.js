/**
 * 
 */
const cellRendererSelector = () => {
    return {
        component: GenderCellRenderer,
        params: {values: ['Male', 'Female']}
    };
}

const columnDefs = [
	{checkboxSelection : true, headerCheckboxSelection: true, width: "50px"},
	{ field: "vhcleCd", headerName: "차량 코드",sortable: true },
	{ field: "vhcleNo", headerName: "차량 번호" ,editable: true},
	{ field: "vhcleModel", headerName: "차량 모델", editable: true},
	 {
        field: "vhcleFlag",
        headerName: "차량 상태",
        editable: true,
        sortable: true,
        cellEditor: 'agSelectCellEditor',
        cellEditorParams: {
			values: ['0', '1']
		},
		valueFormatter: function (params) {
			switch (params.value) {
				case '0':
					return '사용가능';
				case '1':
					return '사용중지';
				default:
					return params.value;
			}
		}
    },
	{ field: "vhcleCapacity", headerName: "수용인원(명)", editable: true},
	{ field: "vhcleRegistDate", headerName: "등록일자" ,sortable: true }
];


// 데이터 정의
const rowData = []; //초기값

// 설정 옵션: 중요, 위에 정의한 것들이 여기서 통합됨에 주목
const gridOptions = {
	columnDefs: columnDefs,
	rowSelection : 'multiple',
	rowData: rowData,
	defaultColDef: {
		filter: true,
		resizable: true,
		sortable:true,
		headerClass: "centered"
	},
	rowHeight: 100,
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

//차량 목록에서 한 셀의 editing 이 끝났을 때 실행하는 함수
const updateData = (event) => {
	var vehicleVO = event.data;
	delete vehicleVO['vhcleImage'];
	var xhr = new XMLHttpRequest();
	var vhcleCd = event.data.vhcleCd;
	xhr.open('PUT', `/adVehicle/${vhcleCd}`, true);
	xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
	xhr.setRequestHeader('content-type', 'application/json');
	xhr.onreadystatechange = function(){
		if (xhr.readyState == 4 && xhr.status == 200 ){
			console.log("수정 성공?");
		}
	}
	xhr.send(JSON.stringify(vehicleVO));
};

const getData = () => {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/adVehicle", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let result = JSON.parse(xhr.responseText);
            for (var i = 0; i < result.length; i++) {
                var vhcleFlag = result[i].vhcleFlag;
                var vhcleFlagText = null;
				if(vhcleFlag=="0"){
					vhcleFlagText = "사용가능";
				}else{
					 vhcleFlagText = "사용중지";
				}
                result[i].vhcleFlag = vhcleFlagText;
            }
            function renderer(params) {
                let returnImg = `<span><img src='${params.value}' height="100px" /></span>`;
                return returnImg;
            }
            for (var i = 0; i < result.length; i++) {
                result[i].vhcleImg = result[i].vhcleImgUrl; // 이미지 URL을 사용
            }
			const imgColumn = {
				field : "vhcleImgUrl",
				headerName : "vhcleImgUrl",
				cellRenderer  : renderer
			}
			const indexToInsert = 2;
            gridOptions.columnDefs.splice(indexToInsert, 0 ,imgColumn);
            gridOptions.api.setColumnDefs(gridOptions.columnDefs);
            gridOptions.api.setRowData(result);
        }
    };
    xhr.send();
};
/*
function fSerialize(myForm){
	console.log("form ==> ", myForm);

	let sendData = [];
	let elems = myForm.elements;
	for(var elem of elems){
		console.log("element ==> ", elem.value);
		sendData[elem.name] = elem.value;
	}
	console.log("sendData ==> ", sendData);
}
*/

//차량 ag-grid 에서 삭제 버튼 클릭시
function fn_vhcleDelBtn(){
	var selectedData = gridOptions.api.getSelectedRows();
	
	if(selectedData.length==0){
		console.log("no data");
		Swal.fire("선택된 데이터가 없습니다.",'','error');
	}else if(selectedData.length==1){
		var selectedVhcleCd = selectedData[0]['vhcleCd'];
		console.log(selectedVhcleCd)
		var xhr = new XMLHttpRequest();
		xhr.open('DELETE', `/adVehicle/${selectedVhcleCd}`, true);
		xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
		xhr.onreadystatechange = function(){
			if(xhr.readyState==4 && xhr.status==200){
				console.log("성공 ! ==> ", xhr.responseText);
				if(xhr.responseText=='OK'){
					Swal.fire('삭제되었습니다.', '','success').then((result)=>{
						location.reload();
					})
				}else{
					Swal.fire('삭제 실패하였습니다.', '','error').then((result)=>{
						location.reload();
					})
				}
				
			}
		}
		xhr.send();	
	}else{
		for(var i=0; i<selectedData.length; i++){
			var selectedVhcleCd = selectedData[i]['vhcleCd'];
			var xhr = new XMLHttpRequest();
			console.log(selectedVhcleCd);
			xhr.open('DELETE', `/adVehicle/${selectedVhcleCd}`, true);
			xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
			xhr.onreadystatechange = function(){
				if(xhr.readyState==4 && xhr.status==200){
					console.log(i +"번째 데이터 삭제 성공 ! ==> ", xhr.responseText);
					Swal.fire(`삭제되었습니다.`, '','success').then((result)=>{
						location.reload();
					})
				}
			}
			xhr.send();	
		}
	}
}

//차량 등록 모달에서 save 클릭 시 발생하는 이벤트 함수
function fn_vehicleInsert(){
	// fSerialize(senderForm);
	
	var vhcleImgUrlFile = document.querySelector("#vhcleImgUrlFile").files[0];

	const vehicleVO = {};
	var vhcleModel = document.querySelector("#vhcleModel").value;
	var vhcleNo = document.querySelector("#vhcleNo").value;
	var vhcleCapacity = document.querySelector("#vhcleCapacity").value;

	vehicleVO.vhcleModel = vhcleModel;
	vehicleVO.vhcleNo = vhcleNo;
	vehicleVO.vhcleCapacity = vhcleCapacity;

	console.log("vehicleVO ==> " , vehicleVO);
	console.log("JSON.stringify(vehicleVO) ==> " , JSON.stringify(vehicleVO));

	const formData = new FormData();
	formData.append('file',vhcleImgUrlFile);
	formData.append('vehicleVO', new Blob([JSON.stringify(vehicleVO)], {type:'application/json;charset=utf-8'}));

	var xhr = new XMLHttpRequest();
	xhr.open("POST","/adVehicle", true);
	xhr.setRequestHeader(CSRFHEADERNAME, CSRFTOKEN);
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4 && xhr.status==200){
			console.log("성공 ! ==> ", xhr.responseText);
			Swal.fire('신규차량이 등록되었습니다.', '','success').then((result)=>{
				location.reload();
			})
		}
	};
	xhr.send(formData);
}

//층수를 선택할때마다 발생하는 이벤트
function changeCodeChar(){
	let selectedStair = $("#selectStair").val(); //선택된 값 A,B,C
	$.ajax({
		url : `/adVehicle/getMaxCount/${selectedStair}`,
		type: "GET",
		contentType : "application/json; charset=utf-8",
		success : function(resp){
			let nextConfroomCd = resp.nextConfroomCd;
			$("#confRoomCd").val(nextConfroomCd);
		},
		error : function(xhr){
			console.log("실패 : " + xhr);
		}
	})
	
}

/////////////////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////
$(function () {
	var vehicleList = document.querySelector("#vehicleList");
	new agGrid.Grid(vehicleList, gridOptions);
	getData();  // 데이터 불러오깅
	
	//var confRoomList = document.querySelector("#confRoomList");
	//new agGrid.Grid(confRoomList, gridOptions);
	//getDataConfRoom();  // 데이터 불러오깅
	
});
