/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 9.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 9.      오경석       조직도 가로형 조회
 * 2023. 11. 10.      오경석       직원 상세 조회
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */



/* 조직도 전체 출력 */
function listORG() {

	$.ajax(
		{
			type: "get",
			url: "/org/do",
			contentType: "application/json",
			dataType: "json",
			success: function(data) {
				let list = data.list;
				let dept = data.dept;


				//일단 억지 데이터 변경 jstree에 맞춰서 쓰일수 있도록
				for (let i = 0; i < dept.length; i++) {
					dept[i].id = dept[i].deptCd;
					dept[i].parent = dept[i].udeptCd ? dept[i].udeptCd : "#";
					dept[i].text = dept[i].deptName;

					if (dept[i].text == "대표이사" || dept[i].text == "총괄사업본부" || dept[i].text == "관리부") {
						dept[i].state = { 'opened': true }
					} else {
						dept[i].state = { 'opened': false }
					}
				}

				for (let i = 0; i < list.length; i++) {
					list[i].id = list[i].empCd;
					list[i].parent = list[i].deptCd;
					list[i].text = `${list[i].empName} ${list[i].common.commonCodeSj}`;
				}

				// 배열 합치기!  스프레드 오퍼레이터 ....
				let total = [...list, ...dept];

				// 'admin' 조직도 제외
				total = total.filter(item => item.text != '관리자 관리자');

				console.log("total:", total);

				$('#kt_docs_jstree_basic').jstree({
					'core': {
						data: total,
					},
					"search": {
						"show_only_matches": true,
						"show_only_matches_children": true
					},
					"plugins": ["search"]
					/*"core": {
						"themes": {
							"responsive": false
						}
					},
					"types": {
						"default": {
							"icon": "fa fa-folder"
						},
						"file": {
							"icon": "fa fa-file"
						}
					},
					"plugins": ["types"]*/
					
				});
							
			},
			error: function(request, status, error) {
				console.log("code: " + request.status)
				console.log("message: " + request.responseText)
				console.log("error: " + error);
			}
		}
	)

}

/*listORG();*/

// 검색어가 변경될 때 이벤트 처리
$('#searchInput').on('input', function() {
    let searchString = $(this).val();
console.log("★",searchString);
    $('#kt_docs_jstree_basic').jstree(true).search(searchString);
infoORG(searchString);
});


const info = (result) => {
	
		let emp = result.emp;
		let html = `
			<small class="text-muted text-uppercase">About</small>
	         <ul class="list-unstyled mb-4 mt-3">
			   <li class="text-muted text-uppercase">
	            <span>프로필</span><br>
				<img src='${emp.empProfileImg}' class="mypageImgSize"> 
	           </li>
	           <li class="d-flex align-items-center mb-3">
	             <i class="bx bx-user"></i><span class="text-muted text-uppercase">이 름　: </span> <span class="text-muted text-uppercase">　${emp.empName} </span>
	           </li>
 				<li class="d-flex align-items-center mb-3">
	             <i class="bx bx-flag"></i><span class="text-muted text-uppercase">사 번　: </span> <span class="text-muted text-uppercase"> 　${emp.empCd}</span>
	           </li>
	           <li class="d-flex align-items-center mb-3">
	             <i class="bx bx-flag"></i><span class="text-muted text-uppercase">부 서　: </span> <span class="text-muted text-uppercase">　 ${emp.dept.deptName}</span>
	           </li>
	           <li class="d-flex align-items-center mb-3">
	             <i class="bx bx-check"></i><span class="text-muted text-uppercase">직 급　: </span> <span class="text-muted text-uppercase"> 　${emp.common.commonCodeSj}</span>
	           </li>
	           <li class="d-flex align-items-center mb-3">
	             <i class="bx bx-detail"></i><span class="text-muted text-uppercase">내선전화　: </span> <span class="text-muted text-uppercase">　 ${emp.empExtension}</span>
	           </li>
	           <li class="d-flex align-items-center mb-3">
	             <i class="bx bx-detail"></i><span class="text-muted text-uppercase">휴대폰　: </span> <span class="text-muted text-uppercase">　 ${emp.empTelno}</span>
	           </li>
	           <li class="d-flex align-items-center mb-3">
	             <i class="bx bx-detail"></i><span class="text-muted text-uppercase">이메일　: </span> <span class="text-muted">　 ${emp.empMail}</span>
	           </li>
       			<li class="d-flex align-items-center mb-3">
	             <i class="bx bx-detail"></i><span class="text-muted text-uppercase">주소　: </span> <span class="text-muted text-uppercase">　 ${emp.empAdres} ${emp.empAdresDetail}</span>
	           </li>
	         </ul>
		`;

		document.querySelector('.card-body').innerHTML = html;
	}



/* 조직원 상세 출력 */
function infoORG() {

	$('#kt_docs_jstree_basic').on("select_node.jstree", function(e, data) {
		let empCd = document.getElementById('kt_docs_jstree_basic').getAttribute('aria-activedescendant');


		let url = "/org/" + empCd;

		let xhr = new XMLHttpRequest();
		xhr.open("get", url, true);
		xhr.setRequestHeader("Content-Type", "application/json")
		xhr.onreadystatechange = () => {
			if (xhr.readyState === 4 && xhr.status === 200) {
				if (xhr.responseText) {
					console.log(xhr.responseText + "!!!");
					info(JSON.parse(xhr.responseText));
				}
			}
		}
		xhr.send();

	});
}

var login = document.getElementById("schEmpCd").value;





/* 추가, 삭제 */
function insertDelete() {
	
	
	var dept = document.querySelectorAll('a.jstree-anchor[id^="DEPT_"]');


	dept.forEach(function(anchorElement) {
	    deptCd = anchorElement.id.replace('_anchor', '');
		deptName = anchorElement.innerText;
	});

	
		let insertDelete = "";
	
		insertDelete += `
		
		
			<form method="post" action="/org" id="insertInfo">
				<div id="insertModal" class="modal">
				     <div class="modal-dialog top20">
				          <div class="modal-content">
				               <div class="modal-header">
				                    <h4 class="modal-title">All-Rounder</h4>
				                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				               </div>
				               <div class="modal-body">
				                    <p>추가할 부서명을 입력하세요</p>
				                    <input type="text" name="deptName" id="deptName">
				               </div>
				               <div class="modal-footer">
				                    <button type="button" class="btn btn-primary" id="insertDept">추가</button>
				                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				               </div>
				          </div>
				     </div>
				</div>
			</form>
	
			<form method="post" id="deleteInfo">
				<input type="hidden" name="_method" value="delete"/>
				<div id="deleteModal" class="modal">
				     <div class="modal-dialog top20">
				          <div class="modal-content">
				               <div class="modal-header">
				                    <h4 class="modal-title">All-Rounder</h4>
				                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				               </div>
				               <div class="modal-body">
				                    <p>삭제할 부서명을 선택하세요</p>
				                    <select id="deptCode">`;
									dept.forEach(function(anchorElement) {
										    deptCd = anchorElement.id.replace('_anchor', '');
											deptName = anchorElement.innerText;
											insertDelete +=  "<option value="+deptCd+">"+deptName+"</option>"
										});
									insertDelete += `</select>
				               </div>
				               <div class="modal-footer">
				                    <button type="button" class="btn btn-primary" id="deleteDept">삭제</button>
				                    <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
				               </div>
				          </div>
				     </div>
				</div>
			</form>
		`;
	
	
	$('#insertDelete').html(insertDelete);
	insertDept();	
	
}



$(document).ready(function() {
	
	listORG();
//	let dept=document.querySelectorAll('a.jstree-anchor[id^="DEPT_"]')


	setTimeout(() => {
		insertDelete();
	}, 1000);
	console.log("$$$",login);
	
	
		let url = "/org/" + login;
		console.log(url);

		let xhr = new XMLHttpRequest();
		xhr.open("get", url, true);
		xhr.setRequestHeader("Content-Type", "application/json")
		xhr.onreadystatechange = () => {
			if (xhr.readyState === 4 && xhr.status === 200) {
				if (xhr.responseText) {
					console.log(xhr.responseText + "!!!");
					info(JSON.parse(xhr.responseText));
				}
			}
		}
		xhr.send();
	
	/*$.ajax({
		url:"/org/login",
		type:"get",
		dataType: "json",
		contentType: "application/json;charset=UTF-8",
		data:{
			"empCd":login
		},
		success:function(rslt){
			console.log("%%",rslt.empCd)
			
			info(rslt)
		},
		error:function(xhr){
			alert("2")
		}
		
	});*/
	
	
	infoORG(login);
	
	function alignModal() {
		var modalDialog = $(this).find(".modal-dialog");

		// 모달 대화 상자의 상단 여백을 적용하여 수직 중앙 정렬
		modalDialog.css("margin-top", Math.max(0, ($(window).height() - modalDialog.height()) / 2));
	}
	// 모달이 표시될 때 정렬
	$(".modal").on("shown.bs.modal", alignModal);

	// 사용자가 창 크기를 조정할 때 모달 정렬
	$(window).on("resize", function() {
		$(".modal:visible").each(alignModal);
	});

});




function insertDept() {
	$('#insertDept').on("click", function() {
		let deptName = $("input[name=deptName]").val();
		let url = "/org";

		$.ajax({
			type: "post",
			url: url,
			/*contentType: "application/json;charset=UTF-8",*/
			dataType: "json",
			data: {
				"deptName": deptName
			}
			,
			success: function(data) {
				console.log("12312312312312312");
				$('#kt_docs_jstree_basic').jstree();
				location.reload();
				/*location.href = '/org/organization/';*/
				$(".modal").remove();
				$(".modal-backdrop").remove();

				//listORG();
				
				

			},
			error: function(request, status, error) {
				console.log("code: " + request.status)
				console.log("message: " + request.responseText)
				console.log("error: " + error);
			}
		});

	})
}


	$(document).on("click",'#deleteDept', function() {
		
		console.log("!!!");
		let deptCd = $('#deptCode').val();
		console.log(deptCd)
		let url = "/org";
		


		$.ajax({
			type: "delete",
			url: url + "?deptCd=" + deptCd,
			contentType: "application/json; charset=UTF-8",
			dataType: "text",
/*			data: deptCd
			,*/
			success: function(resp) {
				console.log(resp)
				location.reload();
				
				// 모달을 숨기기
				$(".modal").remove();
				$(".modal-backdrop").remove();

			},
			error: function(request, status, error) {
				console.log("code: " + request.status)
				console.log("message: " + request.responseText)
				console.log("error: " + error);
			}
		});

	})
	














