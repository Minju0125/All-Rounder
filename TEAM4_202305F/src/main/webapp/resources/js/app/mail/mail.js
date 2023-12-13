/**
 * <pre>
 * 
 * </pre>
 * @author 박민주
 * @since 2023. 11. 12.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 12.      박민주    최초작성
 * 2023. 11. 13.      박민주	sendForm 구현 완료
 * 2023. 11. 14.	  박민주	첨부파일 전송 위해 sendForm 수정
 * 2023. 11. 15.      박민주		
 * 2023. 11. 16.
 * 2023. 12. 01		  박민주    (전체) 수정 중
 * 2023. 12. 02		박민주		리스트 출력 수정 (수신, 발신, 중요메일함)
 * 2023. 12. 08		박민주		리스트 출력 수정 (휴지통)
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */

const empCd = $("#empCd").val(); //로그인된 사원의 사번

// 날짜 형식 formatting : 문자열 형태의 date 객체 -> '1997-01-25' 형식으로 formatting 
function changeDateFormat(dateText) {
	return moment(dateText).format('YYYY-MM-DD');
}
//-------------------------------------------------------- 1) 목록 조회를 위한 함수들 ----------------------------------------------------------------
//수신 메일 리스트 태그 만들기 함수
function makeMailLiTag(resp) {
	let dataList = resp.paging.dataList;
	let liTag = "";

	if (dataList.length == 0) { //메일 내역이 없는 경우
		liTag += `
			<li> 메일함이 비었습니다.</li>
		`;
	} else { // 메일 내역이 존재하는 경우
		for (var i = 0; i < dataList.length; i++) {
			let dateText = dataList[i].mailTrnsmisDt; //메일 발송 날짜
			let formattedDate = changeDateFormat(dateText);
			let mrImpoYn = dataList[i].mrImpoYn;
			let starredAdded = "";
			if (mrImpoYn == 'Y') {
				starredAdded = `style="color: #ffab00;"`;
			}

			// <li data-mailBoxType="${mailBoxType}" style="z-index:-5" data-value="${dataList[i].mailCd}" class="email-list-item email-marked-read"

			liTag += `
					<li style="z-index:-5" data-value="${dataList[i].mailCd}" class="email-list-item email-marked-read "
						data-starred="true" data-bs-toggle="sidebar" data-target="#app-email-view">
						<div class="d-flex align-items-center">
							<div class="form-check">
								<input class="email-list-item-input form-check-input" type="checkbox" id="email-1" />
								<label class="form-check-label" for="email-1"></label>
							</div>
							<i id="starBtn" class="email-list-item-bookmark bx bx-star d-sm-inline-block d-none cursor-pointer mx-4 bx-sm" ${starredAdded}></i>
							<img src="${dataList[i].senderVO.empProfileImg}" alt="user-avatar" class="d-block flex-shrink-0 rounded-circle me-sm-3 me-0" height="32" width="32" />
							<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="display: flex;">
								<div style="width:180px;">
									<span class="badge rounded-pill bg-label-primary">[${dataList[i].senderVO.dept.deptName}/${dataList[i].senderVO.common.commonCodeSj}] ${dataList[i].senderVO.empName}</span>
								</div>
								<div>
									<span class="email-list-item-username me-2 h6">${dataList[i].mailSj}</span>
								</div>
							</div>
							<div class="email-list-item-meta ms-auto d-flex align-items-center">
								<small style="width:80px;" class="email-list-item-time text-muted">${formattedDate}</small>
								<ul class="list-inline email-list-item-actions">
									<li class="list-inline-item email-delete" data-btnId="delBtn" data-value="${dataList[i].mailCd}"><i class="bx bx-trash-alt fs-4"></i></li>
									<li class="list-inline-item email-unread"><i class="bx bx-envelope fs-4"></i></li>
									<li class="list-inline-item"><i class="bx bx-error-circle fs-4"></i></li>
								</ul>
							</div>
						</div>
					</li>`;
		}
	}
	return liTag;
}

//발신 메일 리스트 태그 만들기 함수
function makeSentMailLiTag(resp) {
	let dataList = resp.paging.dataList;
	let variousCondition = resp.paging.variousCondition;
	let liTag = "";
	//XXX 외 2명 ()
	//툴팁으로 이름만 출력해도 좋을듯

	if (dataList.length == 0) { //메일 내역이 없는 경우
		liTag += `
			<li> 메일함이 비었습니다.</li>
		`;
	} else { // 메일 내역이 존재하는 경우
		for (var i = 0; i < dataList.length; i++) {
			let dateText = dataList[i].mailTrnsmisDt; //메일 발송 날짜
			let formattedDate = changeDateFormat(dateText);

			let mrImpoYn = dataList[i].mrImpoYn;
			let starredAdded = "";
			if (mrImpoYn == 'Y') {
				starredAdded = `style="color: #ffab00;"`;
			}

			let mailCd = dataList[i].mailCd;
			let recievers = variousCondition[`${mailCd}`]
			//수신자 처리
			const mailReceiverObj = [];
			if (recievers.length == 1) { //수신자가 한명이라면
				mailReceiverObj.mailReceiver = recievers[0].mailReceiver; 			/* 수신자 사번*/
				mailReceiverObj.rdeptName = recievers[0].rdeptName;				/* 수신자 부서*/
				mailReceiverObj.rempRank = recievers[0].rempRank; 					/* 수신자 직급*/
				mailReceiverObj.rempName = recievers[0].rempName; 					/* 수신자 이름*/
				mailReceiverObj.rempProfileImg = recievers[0].rempProfileImg; 		/* 수신자 프로필 사진*/
			} else { //여러명이라면
				let mailReceiverCnt = recievers.length - 1;
				mailReceiverObj.mailReceiver = recievers[0].mailReceiver; 			/* 수신자 사번*/
				mailReceiverObj.rdeptName = recievers[0].rdeptName;				/* 수신자 부서*/
				mailReceiverObj.rempRank = recievers[0].rempRank; 					/* 수신자 직급*/
				mailReceiverObj.rempName = `${recievers[0].rempName} 외(${mailReceiverCnt}명)`;  /* 수신자 이름*/
				mailReceiverObj.rempProfileImg = recievers[0].rempProfileImg; 		/* 수신자 프로필 사진*/
			}

			liTag += `
					<li style="z-index:-5" data-value="${dataList[i].mailCd}" class="email-list-item email-marked-read"
						data-starred="true" data-bs-toggle="sidebar" data-target="#app-email-view">
						<div class="d-flex align-items-center">
							<div class="form-check">
								<input class="email-list-item-input form-check-input" type="checkbox" id="email-1" />
								<label class="form-check-label" for="email-1"></label>
							</div>
							<i id="starBtn" ${starredAdded} class="email-list-item-bookmark bx bx-star d-sm-inline-block d-none cursor-pointer mx-4 bx-sm"></i>
							<img src="${mailReceiverObj.rempProfileImg}" alt="user-avatar" class="d-block flex-shrink-0 rounded-circle me-sm-3 me-0" height="32" width="32" />
							<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="display: flex;">
								<div style="width:220px;">
									<span class="badge rounded-pill bg-label-info">[${mailReceiverObj.rdeptName}/${mailReceiverObj.rempRank}] ${mailReceiverObj.rempName}</span>
								</div>
								<div>
									<span class="email-list-item-username me-2 h6">${dataList[i].mailSj}</span>
								</div>
							</div>
							<div class="email-list-item-meta ms-auto d-flex align-items-center">
								<small style="width:80px;" class="email-list-item-time text-muted">${formattedDate}</small>
								<ul class="list-inline email-list-item-actions">
									<li class="list-inline-item email-delete" data-btnId="delBtn" data-value="${dataList[i].mailCd}"><i class="bx bx-trash-alt fs-4"></i></li>
									<li class="list-inline-item email-unread"><i class="bx bx-envelope fs-4"></i></li>
									<li class="list-inline-item"><i class="bx bx-error-circle fs-4"></i></li>
								</ul>
							</div>
						</div>
					</li>`;
		}
	}
	return liTag;
}

//중요메일 리스트 태그 만들기 함수
function makeStarredMailLiTag(resp) {
	let dataList = resp.paging.dataList;
	let liTag = "";
	if (dataList.length == 0) { //메일 내역이 없는 경우
		liTag += `
			<li> 메일함이 비었습니다.</li>`;
	} else { // 메일 내역이 존재하는 경우
		for (var i = 0; i < dataList.length; i++) {
			let dateText = dataList[i].mailTrnsmisDt; //메일 발송 날짜
			let formattedDate = changeDateFormat(dateText);

			const mailContents = [];
			if (dataList[i].mailReceiver == empCd) { //수신 메일인 경우
				mailContents.mailType = "수신";
				//발신자 정보
				mailContents.yourName = dataList[i].sempName;
				mailContents.yourProfileImg = dataList[i].sempProfileImg;
				mailContents.yourDeptName = dataList[i].sdeptName;
				mailContents.yourRankName = dataList[i].sempRank;
				mailContents.color = "primary";
			} else { //발신메일인 경우
				mailContents.mailType = "발신";
				//수신자 정보
				mailContents.yourName = dataList[i].rempName;
				mailContents.yourProfileImg = dataList[i].rempProfileImg;
				mailContents.yourDeptName = dataList[i].rdeptName;
				mailContents.yourRankName = dataList[i].rempRank;
				mailContents.color = "info";
			}
			liTag += `
					<li style="z-index:-5" data-value="${dataList[i].mailCd}" class="email-list-item email-marked-read"
						data-starred="true" data-bs-toggle="sidebar" data-target="#app-email-view">
						<div class="d-flex align-items-center">
							<div class="form-check">
								<input class="email-list-item-input form-check-input" type="checkbox" id="email-1" />
								<label class="form-check-label" for="email-1"></label>
							</div>
							<i id="starBtn" style="color: #ffab00;" class="email-list-item-bookmark bx bx-star d-sm-inline-block d-none cursor-pointer mx-4 bx-sm"></i>
							<img src="${mailContents.yourProfileImg}" alt="user-avatar" class="d-block flex-shrink-0 rounded-circle me-sm-3 me-0" height="32" width="32" />
							<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="display: flex;">
								<div style="width:180px;">
									<span class="badge rounded-pill bg-label-${mailContents.color}">[${mailContents.yourDeptName}/${mailContents.yourRankName}] ${mailContents.yourName}</span>
								</div>
								<div>
									<span class="email-list-item-username me-2 h6">${dataList[i].mailSj}</span>
									<span class="badge rounded-pill bg-label-${mailContents.color}">${mailContents.mailType}</span>
								</div>
							</div>
							<div class="email-list-item-meta ms-auto d-flex align-items-center">
								<small style="width:80px;" class="email-list-item-time text-muted">${formattedDate}</small>
								<ul class="list-inline email-list-item-actions">
									<li class="list-inline-item email-delete" data-btnId="delBtn" data-value="${dataList[i].mailCd}"><i class="bx bx-trash-alt fs-4"></i></li>
									<li class="list-inline-item email-unread"><i class="bx bx-envelope fs-4"></i></li>
									<li class="list-inline-item"><i class="bx bx-error-circle fs-4"></i></li>
								</ul>
							</div>
						</div>
					</li>`;
		}
	}
	return liTag;
}
//휴지통 리스트 태그 만들기 함수
function makeTrashMailLiTag(resp) {
	let dataList = resp.paging.dataList;
	let liTag = "";

	if (dataList.length == 0) { //메일 내역이 없는 경우
		liTag += `
			<li> 메일함이 비었습니다.</li>
		`;
	} else { // 메일 내역이 존재하는 경우
		for (var i = 0; i < dataList.length; i++) {
			let dateText = dataList[i].mailTrnsmisDt; //메일 발송 날짜
			let formattedDate = changeDateFormat(dateText);

			const mailContents = [];
			if (dataList[i].mailReceiver == empCd) { //수신 메일인 경우
				mailContents.mailType = "수신";
				//발신자 정보
				mailContents.yourName = dataList[i].sempName;
				mailContents.yourProfileImg = dataList[i].sempProfileImg;
				mailContents.yourDeptName = dataList[i].sdeptName;
				mailContents.yourRankName = dataList[i].sempRank;
				mailContents.color = "primary";
			} else { //발신메일인 경우
				mailContents.mailType = "발신";
				//수신자 정보
				mailContents.yourName = dataList[i].rempName;
				mailContents.yourProfileImg = dataList[i].rempProfileImg;
				mailContents.yourDeptName = dataList[i].rdeptName;
				mailContents.yourRankName = dataList[i].rempRank;
				mailContents.color = "info";
			}

			liTag += `
					<li style="z-index:-5" data-value="${dataList[i].mailCd}" class="email-list-item email-marked-read"
						data-starred="true" data-bs-toggle="sidebar" data-target="#app-email-view">
						<div class="d-flex align-items-center">
							<div class="form-check">
								<input class="email-list-item-input form-check-input" type="checkbox" id="email-1" />
								<label class="form-check-label" for="email-1"></label>
							</div>
							<img src="${mailContents.yourProfileImg}" alt="user-avatar" class="d-block flex-shrink-0 rounded-circle me-sm-3 me-0" height="32" width="32" />
							<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="display: flex;">
								<div style="width:180px;">
									<span class="badge rounded-pill bg-label-${mailContents.color}">[${mailContents.yourDeptName}/${mailContents.yourRankName}] ${mailContents.yourName}</span>
								</div>
								<div>
									<span class="email-list-item-username me-2 h6">${dataList[i].mailSj}</span>
									<span class="badge rounded-pill bg-label-${mailContents.color}" id="mailTypeHere" data-mailType="${mailContents.mailType}">${mailContents.mailType}</span>
								</div>
								
								
							</div>
							<div class="email-list-item-meta ms-auto d-flex align-items-center">
								<small style="width:80px;" class="email-list-item-time text-muted">${formattedDate}</small>
								<ul class="list-inline email-list-item-actions">
									<li class="list-inline-item email-delete" id="permanentlyDelBtn" data-value="${dataList[i].mailCd}"><i class="bx bx-trash-alt fs-4"></i></li>
									<li class="list-inline-item email-restore" id="restoreBtn" data-value="${dataList[i].mailCd}"><i class='bx bx-share'></i></li>
								</ul>
							</div>
						</div>
					</li>`;
		}
	}
	return liTag;
}

//페이징 처리
function fn_paging(page) {
	getMailDataList(page);
}

//페이지 a 태그 클릭 시 이벤트 막기
$(document).on("click", ".page-link", function (e) {
	e.preventDefault();
});

//메일 목록 조회 함수 : 1) DomContentLoaded 이벤트로 로딩 직후 실행, 2) 받은메일함 클릭시 실행
function getMailDataList(page) {
	console.log("해당 메일함 ==> " + targetArea);
	let urlDetail = "receptionMail";
	switch (targetArea) {
		case "inbox":
			urlDetail = "receptionMail";
			break;
		case "sent":
			urlDetail = "sentMail"
			break;
		case "starred":
			urlDetail = "starredMail"
			break;
		default :
			urlDetail = "trashMail"
			break;
		// 	// url = "/mail/mail"
	}

	$("#mailUl").empty();
	$("#paginationArea").empty();

	if (urlDetail == "starredMail") { //중요메일함
		$.ajax({
			type: "GET",
			url: `/mail/${urlDetail}`,
			contentType: "application/json; charset=utf-8",
			data: { page: page },
			dataType: "JSON",
			success: function (resp) {
				//console.log(JSON.stringify(resp));
				let liTags = makeStarredMailLiTag(resp);
				$("#mailUl").append(liTags);
				$("#paginationArea").append(resp.paging.pagingHTML);
			},
			error: function (xhr) {
				console.log("중요메일함 리스트 못가져옴 ! " + xhr);
			}
		})
	} else if (urlDetail == "sentMail") { //발신메일함
		$.ajax({
			type: "GET",
			url: `/mail/${urlDetail}`,
			contentType: "application/json; charset=utf-8",
			data: { page: page },
			dataType: "JSON",
			success: function (resp) {
				//console.log(JSON.stringify(resp));
				let liTags = makeSentMailLiTag(resp);
				$("#mailUl").append(liTags);
				$("#paginationArea").append(resp.paging.pagingHTML);
			},
			error: function (xhr) {
				console.log("발신메일함 리스트 못가져옴 ! " + xhr);
			}
		})
	} else if (urlDetail == "receptionMail") { //수신메일함
		$.ajax({
			type: "GET",
			url: `/mail/${urlDetail}`,
			contentType: "application/json; charset=utf-8",
			data: { page: page },
			dataType: "JSON",
			success: function (resp) {
				//console.log(JSON.stringify(resp));
				let liTags = makeMailLiTag(resp);
				$("#mailUl").append(liTags);
				$("#paginationArea").append(resp.paging.pagingHTML);
				$("#receptionMailBoxCount").text(resp.paging.totalRecord);
			},
			error: function (xhr) {
				console.log("수신메일함 리스트 못가져옴 ! " + xhr);
			}
		})
	} else if (urlDetail == "trashMail") {//trashMail
		$.ajax({
			type: "GET",
			url: `/mail/${urlDetail}`,
			contentType: "application/json; charset=utf-8",
			data: { page: page },
			dataType: "JSON",
			success: function (resp) {
				//console.log(JSON.stringify(resp));
				let liTags = makeTrashMailLiTag(resp);
				$("#mailUl").append(liTags);
				$("#paginationArea").append(resp.paging.pagingHTML);
				$("#receptionMailBoxCount").text(resp.paging.totalRecord);
			},
			error: function (xhr) {
				console.log("휴지통 리스트 못가져옴 ! " + xhr);
			}
		})
	}else{
		let liTags = getTempMails();
		$("#mailUl").append(liTags);
	}

}

//-------------------------------------------------------- 2) 메일 상세보기를 위한 함수들 ----------------------------------------------------------------
function makeEmailView(mailCd) {
	let emailView = $("#app-email-view");
	// 메일 상세 스크롤바
	if (emailView.length > 0) {
		new PerfectScrollbar(emailView[0], {
			wheelPropagation: false,
			suppressScrollX: true
		});
	}
	$.ajax({
		url: `/mail/${mailCd}`,
		type: "get",
		dataType: "JSON",
		success: (resp) => {
			if (resp) {
				//메일 상세페이지 모달 input 태그 비우기
				$("#emailViewImgDiv").empty(); //이름란 비우기
				$("#emailViewDateTime").empty(); //발신날짜 비우기
				$("#emailViewCardBody").empty(); // 내용란 비우기
				let tags = `
									<img src="${resp.senderVO.empProfileImg}"
															alt="user-avatar" class="flex-shrink-0 rounded-circle me-2"
															height="38" width="38" />
									<div class="flex-grow-1 ms-1">
										<h6 class="m-0" id="mailViewName">${resp.mailSj}</h6>
										<small class="text-muted" id="mailSenderInfo">[${resp.senderVO.dept.deptName}/${resp.senderVO.empName}] ${resp.senderVO.empMail}</small>
									</div>
				`;

				//모달에 넣기
				$("#emailViewDateTime").text(resp.mailTrnsmisDt); //발신날자 넣기
				$("#emailViewCardBody").html(resp.mailCn); //내용 넣기
				$("#emailViewImgDiv").append(tags);
				//첨부파일 처리
				var attachesListText = `
						<hr/>
						<p class="mb-2">Attachments</p>
						<div class="cursor-pointer" id="emailViewAttaches">
				`;
				if (!resp.attachments) { //첨부파일 없을 시
					attachesListText += "첨부파일 없음";
				} else { //첨부파일 있을 시
					for (var i = 0; i < resp.attachments.length; i++) {
						var attNo = resp.attachments[i].mailAttachNo;
						if (!i == 0) { //i가 0이 아니라면
							attachesListText += "<br/>";
						}
						attachesListText += `
							<a href="/mail/${mailCd}/mailAttach/${attNo}">
							<i class="bx bx-file"></i>
							<span class="align-middle ms-1">${resp.attachments[i].mailAttachName}</span></a>
						`;
					}
				}
				attachesListText += "</div>";
				$("#emailViewCardBody").append(attachesListText);
			}
		}
	})
}



//////////////////////////////////////////////////////////////////////////////

//DomContentLoaded 이벤트
$(function () {
	getMailDataList(1); //수신메일 목록 조회 함수

	//받은 메일함에 total record 고정
	// $.ajax({
	// 	type : "GET",

	// })
});

//---------------------------------------------------메일함 선택---------------------------------------------------
let targetArea = "inbox"; //메일함 클릭 시 마다 해당 메일함 분류가 전역변수(targetArea)에 들어감

//받은메일함 클릭 시
$("li[data-target='inbox']").on("click", function () {
	if ($("#app-email-view").hasClass("show")) {
		$("#app-email-view").toggleClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
	}
	targetArea = "inbox";
	setCssStyle($(this));
	getMailDataList(1);
});
//보낸메일함 클릭 
$("li[data-target='sent']").on("click", function () {
	if ($("#app-email-view").hasClass("show")) {
		$("#app-email-view").toggleClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
	}
	targetArea = "sent";
	setCssStyle($(this));
	getMailDataList(1);
});

$("li[data-target='draft']").on("click", function () {
	if ($("#app-email-view").hasClass("show")) {
		$("#app-email-view").toggleClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
	}
	targetArea = "draft";
	setCssStyle($(this));
	getMailDataList(1);
});

//중요메일함 클릭
$("li[data-target='starred']").on("click", function () {
	if ($("#app-email-view").hasClass("show")) {
		$("#app-email-view").toggleClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
	}
	targetArea = "starred";
	setCssStyle($(this));
	getMailDataList(1);
});
//휴지통 클릭
$("li[data-target='trash']").on("click", function () {
	if ($("#app-email-view").hasClass("show")) {
		$("#app-email-view").toggleClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
	}
	targetArea = "trash";
	setCssStyle($(this));
	getMailDataList(1);
});
/* --------------------------------
//임시메일함?

//임시저장 버튼 클릭 시 
//list 에서 클릭을 해서 뜬 화면이면 insert
//list 에서 클릭을 한게 아니라면 update
$("#draftBtn").on("click", function(){
	//formdata 에 있는 값 다가져오기?
	//다 가져오는데, 파일은 지우기
	let formData = new FormData($("#sendForm")[0]);
	formData.delete("files"); //파일처리는 안하도록!
	let mjMailCn = CKEDITOR.instances.mailCn.getData().toString();
	formData.set("mailCn", mjMailCn); //이거 안하면 쉼표(,) 만 넘어감 ㅠㅠ
	for (let key of formData.keys()) { //formData 확인하기
		console.log(key, ":", formData.get(key));
	}
	$.ajax({
		type: "post",
		enctype: 'multipart/form-data',
		url: "/mail",
		data: formData,
		processData: false,  // 필수: FormData를 string으로 변환하지 않음
		contentType: false,   // 필수: 파일 업로드를 위해 false로 설정
		dataType: "json",
		success: (resp) => {
			console.log(resp);
			if (resp.status === "OK") { //메일 전송 성공
				Swal.fire("메일을 저장되었습니다.", "", "success").then(function () {
					getMailDataList(1);
				});
			} else { //메일 전송 실패
				Swal.fire("메일 저장에 실패하였습니다.", "새로고침 후 다시 작성해주세요.", "error")
			}
		},
		error: (xhr) => {
			console.log(xhr.status);
		}
	})

});
//--------------------------------------------------------*/


//--------------------------------------------------------동적으로 생긴 요소에 바인딩 하는 이벤트들--------------------------------------------------------
//메일 목록의 삭제 버튼 클릭 시, 해당 메일 삭제 처리 함수
$(document).on("click", "li[data-btnId='delBtn']", function (e) {
	e.stopPropagation();
	let mailCd = $(this).data('value');
	Swal.fire({
		title: '정말 삭제하시겠습니까?',
		text: "삭제된 메일은 휴지통에 저장됩니다.",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		cancelButtonColor: '#d33',
		confirmButtonText: '삭제',
		cancelButtonText: '취소'
	}).then((result) => {
		if (result.value) {
			$.ajax({
				type: "PUT",
				url: `/mail/delete/${mailCd}`,
				data: JSON.stringify({ targetArea: targetArea }),
				dataType: "json",
				contentType: "application/json; charset=utf-8",
				success: function (resp) {
					if (resp.success == "OK") {
						Swal.fire("삭제되었습니다.", "", "success").then(function () {
							getMailDataList(1);
						});
					} else {
						Swal.fire("삭제 실패하였습니다.", "", "error");
					}
				},
				error: function (xhr) {
					console.log("삭제 실패 ! ==> " + xhr.responseText);
				}

			})
		}
	})
});

//메일 상세 sidebar 띄우기 +  데이터 채우기
$(document).on("click", ".email-list-item", function (e) {
	var clickedEmpCd = $(this).data('value'); // data-target 값 가져오기 (보여줄 대상 : #app-email-view)
	var target = $(this).data("target"); // data-target 값 가져오기 (보여줄 대상 : #app-email-view)
	$(target).toggleClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
	makeEmailView(clickedEmpCd); //사이드바로 생긴 email View 에 데이터 채우기
});

//sidebar 닫기
$("#backBtn").on("click", function () {
	//console.log("눌려?" + this);
	$("#app-email-view").removeClass("show"); // 사이드바 토글 (class 가 show를 가지고 있으면 삭제, 아니면 추가)
});

//중요메일 toggle
$(document).on("click", ".email-list-item-bookmark", function (e) {
	e.stopPropagation();
	let mailCd = $(this).closest('li').data('value');
	$.ajax({
		type: "PUT",
		url: `/mail/important/${mailCd}`,
		data: JSON.stringify({ targetArea: targetArea }),
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success: function (resp) {
			if (resp.success == "OK") {
				//console.log("표시 성공!");
				getMailDataList(1);
			} else {
				console.log("중요표시 토글 실패")
			}
		},
		error: function (xhr) {
			console.log("중요표시 토글 실패 ! ==> " + xhr.responseText);
		}

	})
})

//휴지통에서 영구삭제 버튼
$(document).on("click", "#permanentlyDelBtn", function (e) {
    e.stopPropagation(); // 이벤트 전파 차단
	let mailCd = $(this).data('value');
    let mailType = "";
    if ($(this).closest('li').find('#mailTypeHere').data('mailType') == "수신") { // 수신메일
        mailType = "reception";
    } else { // 발신메일
        mailType = "sent";
    }

    Swal.fire({
        title: '영구삭제 하시겠습니까?',
        text: "영구삭제된 메일은 복구할 수 없습니다.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        reverseButtons: false // 버튼 순서 거꾸로
    }).then((result) => {
        if (result.isConfirmed) {
            $.ajax({
                type: "PUT",
                url: `/mail/permanentlyDelete/${mailCd}`, // mailCd 변수를 사용하여 URL 생성
                data: JSON.stringify({ mailType: mailType }), // mailType 데이터를 JSON 형태로 전송
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (resp) {
					if(resp.success=="OK") {
						Swal.fire("영구삭제되었습니다.", "", "success").then(function(){
							getMailDataList(1);
						});
					}
                },
                error: function (xhr, status, err) {
                    // 실패 시 처리할 내용
                    console.error("영구삭제 실패");
                }
            });
        }
    });
});

//휴지통에서 메일복구 버튼
$(document).on("click", "#restoreBtn", function (e) {
	e.stopPropagation();
	let mailCd = $(this).data('value');
	let mailType = "";
	if ($(this).closest('li').find('#mailTypeHere').data('mailType') == "수신") { //수신메일
		mailType = "reception";
	} else { //발신메일
		mailType = "sent";
	}
	//console.log("복구 mailType: " + mailType);
	$.ajax({
		type: "PUT",
		url: `/mail/restoreDelete/${mailCd}`,
		data: JSON.stringify({ mailType: mailType }),
		dataType: "json",
		contentType: "application/json; charset=utf-8",
		success: function (resp) {
			if(resp.success=="OK") {
				Swal.fire("메일이 복구되었습니다.", "", "success").then(function(){
					getMailDataList(1);
				});
			}
		},
		error: function (xhr) {
			console.log("복구 실패입니다!");
		}
	})
});

//----------------------------------------------메일 작성----------------------------------------------
let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
let csrf = $("meta[name='_csrf']").attr("content");
CKEDITOR.replace("mailCn", {
	filebrowserImageUploadUrl: `/mail/image?type=image&${csrfparam}=${csrf}`
});

//sendBtn 클릭시 메일 전송
$("#sendBtn").on("click", function (event) {
	let formData = new FormData($("#sendForm")[0]);
	//console.log("폼데이터 : " + JSON.stringify(formData));
	// JSON Data append
	let mailSj = $("#mailSj").val();
	let receiversCd = $("#receiversCd").val(); //배열
	let mjMailCn = CKEDITOR.instances.mailCn.getData().toString();

	//console.log("작성된 formData ====>", formData)
	
	formData.set("mailCn", mjMailCn); //이거 안하면 쉼표(,) 만 넘어감 ㅠㅠ

	for (let key of formData.keys()) {
		console.log(key, ":", formData.get(key));
	}

	$.ajax({
		type: "post",
		enctype: 'multipart/form-data',
		url: "/mail",
		data: formData,
		processData: false,  // 필수: FormData를 string으로 변환하지 않음
		contentType: false,   // 필수: 파일 업로드를 위해 false로 설정
		dataType: "json",
		success: (resp) => {
			console.log(resp);
			if (resp.status === "OK") { //메일 전송 성공
				Swal.fire("메일을 전송하였습니다.", "", "success").then(function () {
					getMailDataList(1);
				});
			} else { //메일 전송 실패
				Swal.fire("메일 전송에 실패하였습니다.", "새로고침 후 다시 시도해주세요.", "error")
			}
		},
		error: (xhr) => {
			console.log(xhr.status);
		}
	})
}); //메일 전송 submit

//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------
//----------------------------------------------------------------------

function getRandomName(){
	const name = [
    '김지현', '이승우', '박지민', '최영희', '정민수',
    '강서연', '윤성준', '임지원', '홍승민', '송지훈',
    '신유진', '오동현', '서현주', '김태영', '이지수'
	];
	const randomIdx = Math.floor(Math.random() * name.length);
	return name[randomIdx];
}

function getRandomMailTitle(){
	const mailTitle = [
    "인사 및 조직 변경 안내",
    "회사 규정 및 정책 업데이트 공지",
    "회의 일정 및 참석 요청",
    "고객 불만 처리 및 조치 안내",
    "기술 업데이트 및 패치 안내",
    "새로운 기술 도입 및 활용 방법 안내",
    "개발 일정 변경 및 업데이트 안내",
    "개발자를 위한 교육 자료 및 리소스 공유",
    "회사 이벤트 및 기념일 안내",
    "생산 라인 운영 일정 변경 안내",
    "서비스 장애 발생: 대응 및 복구 안내",
    "비상 상황 대비 계획 안내",
    "긴급 회사 내부 안전 교육 일정 안내",
    "부서 간 협업 과제 할당 안내",
    "고객 서비스 우수 성과 인정 안내",
    "신규 직원 온보딩 일정 안내",
    "서비스 개선을 위한 고객 피드백 요청",
    "제품 라인업 확장에 따른 인력 필요 안내",
    "세일즈 팀 업무 공유 및 협업 안내",
    "프로젝트 진행 상황 업데이트 공유"
	];
	const randomIdx = Math.floor(Math.random() * mailTitle.length);
	return mailTitle[randomIdx];
}

function getRandomPosition() {
	const position = ['대리', '과장', '사원', '부장'];
	const randomIdx = Math.floor(Math.random() * position.length);
	return position[randomIdx];
}
function getRandomDeptName() {
	const rndDeptName = ['관리부','영업부','생산부','기술부','개발부'];
	const randomIdx = Math.floor(Math.random() * rndDeptName.length);
	return rndDeptName[randomIdx];
}
//클릭한 리스트 css
function setCssStyle(cssTaret){
	$(".d-flex").css("background-color", "");
	$(cssTaret).css("background-color", "rgba(67, 89, 113, 0.1)");
}
//임시 메일 가져오기
function getTempMails() {
	let tempMailListTags = "";
	for (var i = 1; i < 9; i++) {
		tempMailListTags += `
					<li style="z-index:-5" class="email-list-item email-marked-read">
						<div class="d-flex align-items-center">
							<div class="form-check">
								<input class="email-list-item-input form-check-input" type="checkbox" id="email-1" />
								<label class="form-check-label" for="email-1"></label>
							</div>
							<i id="starBtn" class="email-list-item-bookmark bx bx-star d-sm-inline-block d-none cursor-pointer mx-4 bx-sm"></i>
							<img src="/resources/assets/img/avatars/${i}.png" alt="user-avatar" class="d-block flex-shrink-0 rounded-circle me-sm-3 me-0" height="32" width="32" />
							<div class="email-list-item-content ms-2 ms-sm-0 me-2" style="display: flex;">
								<div style="width:220px;">
									<span class="badge rounded-pill bg-label-info">[${getRandomDeptName()}/${getRandomPosition()}]${getRandomName()}</span>
								</div>
								<div>
									<span class="email-list-item-username me-2 h6">${getRandomMailTitle()}</span>
								</div>
							</div>
							<div class="email-list-item-meta ms-auto d-flex align-items-center">
								<small style="width:80px;" class="email-list-item-time text-muted">2023-12-15</small>
								<ul class="list-inline email-list-item-actions">
									<li class="list-inline-item email-delete" data-btnId="delBtn"><i class="bx bx-trash-alt fs-4"></i></li>
									<li class="list-inline-item email-unread"><i class="bx bx-envelope fs-4"></i></li>
									<li class="list-inline-item"><i class="bx bx-error-circle fs-4"></i></li>
								</ul>
							</div>
						</div>
					</li>
					`;
	}
	return tempMailListTags;
}


