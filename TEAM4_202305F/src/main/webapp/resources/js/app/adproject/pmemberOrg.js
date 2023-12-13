/**
 * <pre>
 * 
 * </pre>
 * @author 송석원
 * @since 2023. 11. 22.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 22.  송석원       최초작성 
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 
 
 
 
// 수신자 선택을 위한 리스트 출력
$(function(){
	$.getJSON("/org/list")
		.done(function(resp) {
			let empList = resp.list;
			console.log("empList================", empList);
            let tags = makeRecpList(resp); 
			console.log(tags);
			$("#sanctnRcyer").append(tags);
		})
		.fail(function(jqxhr, textStatus, error) {
			let err = textStatus + ", " + error;
			console.error("Request Failed: " + err);
		});
		
	// 전 직원목록을 반함
	let makeRecpList = function (resp) {
	   let respList = resp.list;
	   //console.log(deptList);
	   let tags = "";
	   
	   tags += `<option value="-1">선택하세요</option>`;
	
	   for (let i = 0; i < respList.length; i++) {
	      if (respList[i].empName != "관리자") {
	         tags += `<option data-avatar="5.png" value="${respList[i].empCd}">[${respList[i].dept.deptName}]${respList[i].empName} ${respList[i].common.commonCodeSj}</option>`;
	      }
	      // console.log(respList[i].empName);
	   }
	   return tags;
	}

	

	
});

$('')

 
	
let selectedDataArray = [];
// 결재선의 조직도출력
function listORG() {
	
var proSn = document.querySelector('input[name="proSn"]').value;
 console.log("프제값있어라 있어라있어라요",proSn); 
let url = '/org/do/'+proSn; 

console.log("아아 유알",url	);
 
    $.ajax({
		type: "get",
		url: url,   
		contentType: "application/json",
		dataType: "json",
		success: function (data) {
			let list = data.list;
			let dept = data.dept;

			console.log("체크1:", list);
			console.log("체크2:", dept);

			//일단 억지 데이터 변경 jstree에 맞춰서 쓰일수 있도록
			for (let i = 0; i < dept.length; i++) {
				dept[i].id = dept[i].deptCd;
				dept[i].parent = dept[i].udeptCd ? dept[i].udeptCd : "#";
				dept[i].text = dept[i].deptName;
				
				if(dept[i].text == "대표이사" || dept[i].text == "총괄사업본부" || dept[i].text == "관리부" ){
					dept[i].state = {'opened' : true}
				}else {
					dept[i].state = {'opened' : false}
				}
			}

			for(let i=0; i< list.length; i++){
				list[i].id = list[i].empCd;
				list[i].parent = list[i].deptCd;
				list[i].text = `${list[i].empName} ${list[i].common.commonCodeSj}`;
			}

			// 배열 합치기!  스프레드 오퍼레이터 ....
			let total = [...list,...dept];

			// 'admin' 조직도 제외
			total = total.filter(item => item.text != '관리자 관리자');

			console.log("total:",total);
			console.log("체크=================",$("#orgTreeContainer"));
			
			$("#orgTreeContainer").jstree({
				core: {
					data: total,
				}
			});

			// Select 했을때 값을 배열에 저장
			$('#orgTreeContainer').on("select_node.jstree", function (e, data) {
				console.log("select했을때", data);
				var id = data.node.id;
				var text = data.node.text;
				var dept = data.node.original.dept.deptName;
				console.log("select했을때 id", id);
				console.log("select했을때 text", text);
				console.log("select했을때 dept", dept);
				
				if (selectedDataArray.some(item => item.empCd == id)) {
					// 이미 선택된 경우
					data.instance.deselect_node(data.node);
					Swal.fire({
			            title: '이미 선택된 직원입니다!',
			            icon: 'warning', 
			            confirmButtonColor: '#d33',
			            confirmButtonText: '확인'
			        });  
					//alert("이미 선택된 직원입니다!");
				} else{    
					// id와 text를 배열에 저장
					let selectedData = {
						empCd: id,
						empName: text,
						deptName: dept
					};
					
					
					$('#orgTreeResult').append('<div>'+text+'<input name="pMember" type="hidden" value= "'+id+'"/><i class="bx bxs-x-square"></i></div>');
						selectedDataArray.push(selectedData);
						console.log("선택한 데이터:", selectedDataArray); 
				} 
				 
			});
		},
	    error: function (request, status, error) {
	        console.log("code: " + request.status)
	        console.log("message: " + request.responseText)
	        console.log("error: " + error);
	    }
    });
}

listORG();

$('#orgTreeResult').on('click', '.bx', function() {
    // 선택된 요소가 속한 div 요소를 찾아서 제거
    $(this).closest('div').remove();

    // 선택된 데이터 배열에서 해당 아이템 제거
    var removedEmpCd = $(this).siblings('input').val();
    selectedDataArray = selectedDataArray.filter(item => item.empCd != removedEmpCd);
    console.log("선택한 데이터:", selectedDataArray);
});
  
$('#remove').on("click", function(){
	$('#orgTreeResult').empty();
    selectedDataArray = [];
	console.log("remove버튼 클릭!!!!!!");
});
	
$('#addLine').on('hidden.bs.modal', function(e) {
	$('#orgTreeResult').empty();
	selectedDataArray = [];
});
	



$('#addBtn').on("click", function(){
	$('#sanctionLine').empty(); 

	//console.log("pppp",selectedDataArray);
	
	if(selectedDataArray.length > 0 ){
		for(let i = 0; i < selectedDataArray.length; i++) {
			let selectedId = selectedDataArray[i].empCd;
			let selectedName = selectedDataArray[i].empName;
			console.log("selectedId : "+selectedId); 
			//console.log("selectedName : "+selectedName);

		   $('#sanctionLine').append('<div><span>'+selectedName+'</span><input type="hidden" value="'+ selectedId+'"/></div>');
		}
	}
	// 모달 닫기
	$('#addLine').modal('hide');
 
});

	
