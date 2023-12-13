/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 8.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일       	   수정자          수정내용
 * --------     --------    ----------------------
 * 2023. 11. 8.      박민주       최초작성
 * 2023. 12. 4. 	 박민주		  
 * 2023. 12. 9.		 박민주		자동완성 추가
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

//입력한 직원 정보들이 일치하는 경우, 인증번호 입력& 확인란 추가하기
function appendCertiNum() {

	let tags = `
			<form method="post" class="mb-3" action="/checkCertiNum"
					enctype="application/x-www-form-urlencoded" id="certiNumForm">
				<div class="mb-3">
					<label for="empCrtfcNo" class="form-label">인증번호</label>
					<input type="text" class="form-control" id="empCrtfcNo" name="empCrtfcNo" placeholder="인증번호를 입력해주세요." autofocus />
					<input type="hidden" name="empCd" id="empCd"/>
				</div>
				<input type="button" class="btn btn-primary d-grid w-100" value="확인" id="crtfCheckBtn">
				<sec:csrfInput/>
			</form>
			`;
	let formDiv = $("#formDiv");
	$("#crtfSendBtn").remove();
	//위 세가지 항목들 비활성화
	$("#empName").attr("readonly", true);
	$("#empCd").attr("readonly", true);
	$("#empTelno").attr("readonly", true);
	//인증번호 발송 버튼
	console.log(formDiv);
	formDiv.append(tags);
};

//인증번호 일치 확인
$(document).on("click", "#crtfCheckBtn", function () {
	const inputData = {
		empName: $("#empName").val(),
		empCd: $("#empCd").val(),
		empTelno: $("#empTelno").val(),
		empCrtfcNo: $("#empCrtfcNo").val()
	};
	$.ajax({
		type: "POST",
		url: "/findPw/checkCertiNum",
		data: JSON.stringify(inputData),
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		headers: {
			"X-CSRF-TOKEN": CSRFTOKEN
		},
		success: function (resp) {
			if (resp.success == "OK") { //일치하는 경우
				Swal.fire("비밀번호가 초기화되었습니다.", "생년월일 8자리로 로그인해주세요.", "success").then(function(){
					location.href = "/logout";
				});
			} else {
				Swal.fire("인증번호가 일치하지 않습니다.", "다시 입력해주세요.", "error");
			}
		},
		error: function (xhr, status) {
			console.log("실패? : ", xhr.responseText);
			console.log("실패 상태? : ", status);
		}
	});
});

$(function () {
	const myContent = $("#myContent");
	console.log(myContent);
	let makeForm = function () {
		let formText = `
			<form method="post" class="mb-3" action="/findPw/findPwProcess"
				enctype="application/x-www-form-urlencoded" id="sendCertiNumForm">
			<div class="mb-3" id="formDiv">
				<label for="empName" class="form-label" req>이름</label>
				<input type="text"  class="form-control" id="empName" maxlength="30" name="empName" placeholder="이름을 입력해주세요." required='required' autofocus />
				<label for="empCd" class="form-label">사번</label>
				<input type="text" class="form-control" id="empCd" name="empCd" maxlength="10" placeholder="사번을 입력해주세요." required='required' autofocus />
				<label for="empTelno" class="form-label">휴대폰 번호</label>
				<input type="text" class="form-control" id="empTelno" name="empTelno" maxlength="11" placeholder="휴대폰 번호를 입력해주세요. (하이픈(-) 없이)" required='required' autofocus />
			</div>
			<a style="float: right; cursor:pointer; margin-bottom:20px;" class="autoCompleteMj"><small>자동완성</small></a>
			<br>
			<input type="button" class="btn btn-primary d-grid w-100" id="crtfSendBtn" value="인증번호 발송">
		</form>
		`;
		$(myContent).html(formText);
	}
	makeForm();
	
	$(document).on("click", ".autoCompleteMj", function(e){
		e.stopPropagation();
		$("input[id='empName']").val("전수진");
		$("input[id='empCd']").val("E220901001");
		$("input[id='empTelno']").val("01045742335");
	});

	//이름에는 한글만 입력
	$("#empName").keyup(function() {
		var replace_text = $(this).val().replace(/[^ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g, '');
		$(this).val(replace_text);
	});
	//사번에는 숫자, 영문대문자만 입력
	$("#empCd").keyup(function() {
		var replace_text = $(this).val().replace(/[^0-9A-Z]/g, '');
		$(this).val(replace_text);
	});
	//휴대폰번호에는 숫자만 입력
	$("#empTelno").keyup(function() {
		var replace_text = $(this).val().replace(/[^0-9]/g, '');
		$(this).val(replace_text);
	});

	//인증번호 발송 버튼
	$("#crtfSendBtn").on("click", function () {
		$(".autoCompleteMj").css("display", "none");
		if(($("#empName").val().length < 1)||($("#empCd").val().length < 1)||($("#empTelno").val().length < 1)){
			Swal.fire("모든 항목을 입력해주세요.", "", "error");
			return false;
		}
		const inputData = {
			empName: $("#empName").val(),
			empCd: $("#empCd").val(),
			empTelno: $("#empTelno").val()
		};
		$.ajax({
			url: "/findPw/findPwProcess",
			type: "POST",
			data: JSON.stringify(inputData),
			dataType: "json",
			contentType: "application/json; charset=utf-8",
			headers: {
				"X-CSRF-TOKEN": CSRFTOKEN
			},
			success: function (resp) {
				let messege = resp.messege;
				console.log(messege);
				console.log(resp);
				if (resp.msgResult == "OK" && resp.updateResult == "OK") {
					//메시지 전송 성공 && 인증번호 업데이트 성공
					Swal.fire(`${messege}`, "", "success").then(function () {
						appendCertiNum();
					});
				} else if (resp.msgResult == "OK" && resp.updateResult == "Fail") {
					//메시지 전송 성공 && 인증번호 업데이트 실패
					Swal.fire("시스템상 오류로 인해 인증번호 재전송이 필요합니다.", "직원 정보를 다시 입력해주세요.", "error");
					$("#empName").val('');
					$("#empCd").val('');
					$("#empTelno").val('');
				} else if (resp.msgResult == "Fail") {
					//메시지 전송 실패
					Swal.fire(`${messege}`, "정보를 다시 입력해주세요.", "error");
					$("#empName").val('');
					$("#empCd").val('');
					$("#empTelno").val('');
				} else {
					Swal.fire(`${messege}`, "올바르게 입력해주세요.", "error");
					$("#empName").val('');
					$("#empCd").val('');
					$("#empTelno").val('');
				}
			}
		})
	});

	// 	//직원정보가 없어서 아래 안생긴 경우는.?
	// 	if ($("#empCrtfcNo").length === 0) {//없다면
	// 		console.log("폼 없음");
	// 	} else {
	// 		console.log("폼 잇음")
	// 		$("#certiNumForm").on("submit", function (event) {
	// 			event.preventDefault();
	// 			console.log(this.action);
	// 			let url = this.action;
	// 			let method = this.method;

	// 			//사번과 입력한 인증번호가 들어가 있어야함
	// 			let empCrtfcNo = $("#empCrtfcNo").val;
	// 			if (!empCrtfcNo) {
	// 				alert("인증번호를 입력해주세요.");
	// 				return;
	// 			}

	// 			let data = $(this).serializeJSON(); //제이쿼리 슬림버전에는 존재하지 않음
	// 			let json = JSON.stringify(data);

	// 			let settings = {
	// 				url: url,
	// 				method: method,
	// 				data: json,
	// 				dataType: "json",
	// 				contentType: "application/json; charset=utf-8"
	// 			};

	// 			console.log("아래 콘솔 : ", settings);

	// 			$.ajax(settings).done(function (resp) {
	// 				console.log("resp.success : ", resp.success);
	// 				if (resp.success === true) { //인증번호 일치
	// 					location.href = "";//비밀번호 변경 창으로 이동, 사번은?
	// 				} else { //인증번호 불일치
	// 					alert(resp.message);
	// 					$("#empCrtfcNo").val("");
	// 				}
	// 			})
	// 		}); //certiNumForm submit end
	// 	};//if-else end
	// }); //ajax,done,always end

})
