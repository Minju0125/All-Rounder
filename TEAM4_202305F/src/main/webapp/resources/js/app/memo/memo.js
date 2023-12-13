/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 16.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 16.      오경석       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */


var no = document.querySelector('.page-header').getAttribute('data-maxno') * 1 + 1;  // id값

var zindex = document.querySelector('.page-header').getAttribute('data-maxzindex') * 1 + 1;  // 클릭한 postit 가장 앞으로
//var zindex = 1;

// 메모지 생성 시 무작위 left값
function getLeft() {
	let left = 0;
	while (left < 100) {
		left = Math.random() * $(document).width() - 250;
	}
	return left;
}

// 메모지 생성 시 무작위 top값
function getTop() {
	let top = 0;
	while (top < 100) {
		top = Math.random() * $(document).height() - 250;
	}
	return top;
}

function adjustMemoContainerSize() {
	// Textarea의 높이를 자동으로 조절
	$('.memo textarea').each(function() {
		$(this).css('height', 'auto');
		$(this).css('height', this.scrollHeight + 'px');
	});

	// Memo 컨테이너의 높이 조절
	$('.memo').each(function() {
		$(this).css('height', 'auto');
		$(this).css('height', $(this).prop('scrollHeight') + 'px');
	});


}

function memoList() {
	// 저장된 메모 먼저 출력
	$.ajax({
		type: "GET",
		url: "/memo/list",
		dataType: "json",
		success: function(memoList) {
			// 컨테이너를 찾거나 만듭니다. 여기서는 body를 기준으로 했습니다.
			let container = $('body');

			// memoList를 순회하며 각 메모에 대한 <div>를 생성합니다.
			$.each(memoList, function(index, memo) {
				let left = memo.memoX;
				let top = memo.memoY;

				let color = "";
				if (memo.memoBkmkYn === 'Y') {
					color = 'rgb(0, 0, 0)';
				} else {
					color = '#fff';
				}


				let $newMemo = $('<div class="memo" id="' + memo.memoNo + '" style="background-image: url(' + memo.memoBg + ');z-index:' + memo.memoZindex + '; left: ' + left + 'px; top: ' + top + 'px; width:177px; "><div class="btnClose">&times;</div><div class="btnCheck" style="color: ' + color + ';" data-check="' + memo.memoBkmkYn + '">&#9733;</div><textarea class="txtMemo">' + memo.memoCn + '</textarea></div>');


				// 새로운 메모를 컨테이너에 추가합니다.
				container.append($newMemo)
					.children()
					.last()
					.draggable({
						stop: function(event, ui) {

							let left = ui.position.left;
							let top = ui.position.top;
							let id = this.id;
							let zindex = $(this).css('z-index');
							let memo = $(this).find('textarea').val();
							let check = document.querySelector(".btnCheck").getAttribute("data-check");


							// 서버로 데이터 전송
							let jsondata = {
								"memoNo": id,
								"memoCn": memo,
								"memoX": left,
								"memoY": top,
								"memoZindex": zindex,
								"memoBkmkYn": check
							};



							$.ajax({
								type: 'PUT',
								url: '/memo',
								contentType: "application/json",
								data: JSON.stringify(jsondata),
								dataType: 'text',
								success: function(result) {
									console.log("성공여부:", result);
								},
								error: function(request, status, error) {
									console.log("code: " + request.status)
									console.log("message: " + request.responseText)
									console.log("error: " + error);
								}
							});

						}
					}) // 메모 객체
					.css('position', 'absolute')  // 메모객체
					.css('left', left + 'px')
					.css('top', top + 'px')
					.mousedown(function() { // 메모객체
						$(this).css('z-index', zindex);
						zindex++;
					})
					.find('.btnClose').click(function() { // 메모객체의 닫기버튼
						let id = this.parentNode.id;

						Swal.fire({
							title: '정말 삭제 하시겠습니까?',
							text: "다시 되돌릴 수 없습니다.",
							icon: 'warning',
							showCancelButton: true,
							confirmButtonColor: '#3085d6',
							cancelButtonColor: '#d33',
							confirmButtonText: '승인',
							cancelButtonText: '취소',
							reverseButtons: false, // 버튼 순서 거꾸로

						}).then(result => {
							if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면

								$.ajax({
									type: 'DELETE',
									url: '/memo',
									contentType: "application/json",
									data: JSON.stringify(id),
									dataType: 'json',
									success: function(result) {
										console.log("성공여부:", result);
										Swal.fire('삭제되었습니다.', '', 'success');


									},
									error: function(request, status, error) {
										console.log("code: " + request.status)
										console.log("message: " + request.responseText)
										console.log("error: " + error);
									}
								});
								// Remove the memo from the UI
								$(this).parent().remove();
							}
						});



					})
					.end() // .find 이후의 체인을 원래 상태로 복원
					.find('.btnCheck').click(function() { // .btnCheck에 대한 클릭 이벤트


						let memoCheck = "";
						let currentColor = $(this).css('color');
						if (currentColor === 'rgb(0, 0, 0)') {
							$(this).css('color', '#fff');
							memoCheck = "N";
						} else {
							$(this).css('color', 'rgb(0, 0, 0)');
							memoCheck = "Y";
						}

						let left = $(this).parent().offset().left;
						let top = $(this).parent().offset().top;
						let id = $(this).parent().attr('id');
						let zindex = $(this).parent().css('z-index');
						let memo = $(this).parent().find('textarea').val();


						// 서버로 데이터 전송
						let jsondata = {
							"memoNo": id,
							"memoCn": memo,
							"memoX": left,
							"memoY": top,
							"memoZindex": zindex,
							"memoBkmkYn": memoCheck
						};
						console.log(JSON.stringify(jsondata))

						$.ajax({
							type: 'PUT',
							url: '/memo',
							contentType: "application/json",
							data: JSON.stringify(jsondata),
							dataType: 'text',
							success: function(result) {
								console.log("성공여부:", result);

							},
							error: function(request, status, error) {
								console.log("code: " + request.status)
								console.log("message: " + request.responseText)
								console.log("error: " + error);
							}
						});


					})
					.end() // .find 이후의 체인을 원래 상태로 복원
					.find('textarea').on('input', function() {
						// 이벤트 핸들러: Textarea의 내용이 변경될 때마다 실행됨

						// Textarea의 높이를 자동으로 조절
						$(this).css('height', 'auto');
						$(this).css('height', this.scrollHeight + 'px');

						// Memo 컨테이너의 높이 조절
						let memoContainer = $(this).parent();
						memoContainer.css('height', 'auto');
						memoContainer.css('height', memoContainer.prop('scrollHeight') + 'px');

						// 추가: 입력된 내용을 확인
						let memo = $(this).val();
						console.log("!!!", memo);
					})
					.end() // .find 이후의 체인을 원래 상태로 복원
					.keydown(function(event) {
						// Enter 키가 눌렸을 때의 코드
						if (event.key === "Enter") {
							let left = parseFloat($(this).css('left'));
							let top = parseFloat($(this).css('top'));
							let id = this.id;
							let memo = $(this).find('.txtMemo').val();;
							let zindex = $(this).css('z-index');
							let memoCheck = this.querySelector('.btnCheck').getAttribute('data-check');

							console.log(memo);

							// 서버로 데이터 전송
							let jsondata = {
								"memoNo": id,
								"memoCn": memo,
								"memoX": left,
								"memoY": top,
								"memoZindex": zindex,
								"memoBkmkYn": memoCheck
							};

							console.log(JSON.stringify(jsondata))

							$.ajax({
								type: 'PUT',
								url: '/memo',
								contentType: "application/json",
								data: JSON.stringify(jsondata),
								dataType: 'text',
								success: function(result) {
									console.log("성공여부:", result);

								},
								error: function(request, status, error) {
									console.log("code: " + request.status)
									console.log("message: " + request.responseText)
									console.log("error: " + error);
								}
							});

							// 기본 동작을 막기 (Enter 키의 역할을 수행하지 않도록)
							event.preventDefault();

							// 추가: textarea 포커스 아웃
							$(this).find('.txtMemo').blur();
						}
					});

				// ID 카운터를 증가시킵니다
				// no++;

				// 메모를 컨테이너에 추가한 후에 호출
				adjustMemoContainerSize();
			});
		},
		error: function(xhr, status, error) {
			// 에러 처리
			console.error("메모 목록을 불러오는 도중 에러 발생:", error);
		}
	});
}

// 페이지 로딩 시 실행되는 부분
$(document).ready(function() {
	// 페이지 로딩 시 서버에서 메모 목록을 가져와서 UI를 업데이트
	memoList();
	
	// 페이지 로딩 후 잠시 기다린 후에 다시 호출하여 초기 로딩 시 메모 크기를 조절
	setTimeout(adjustMemoContainerSize, 0);
});


// ----------------------------------------------------------------------------------------------

// 모달 열기 함수
function insertOpenModal() {
	// 모달을 나타나게 설정
	openMemo.style.display = "block";

	// 최상위 z-index 값 찾기
	let maxZIndex = 0;
	$('.memo').each(function() {
		let zIndex = parseInt($(this).css('z-index'));
		maxZIndex = Math.max(maxZIndex, zIndex);
	});

	// 모달의 z-index를 최상위로 설정
	openMemo.style.zIndex = maxZIndex + 1;


	// 클릭 이벤트를 여러 번 바인딩하지 않도록 이전에 바인딩된 이벤트를 언바인딩
	$('#addMemo').off('click');



	// 버튼 클릭시 새 메모 추가하기
	$('#addMemo').click(function() {
		// 배경 색상(background)
		let select = document.getElementById('memoBColor');
		let option = select.options[select.selectedIndex];
		let back = option.value;
		console.log(select)
		console.log(option)
		console.log(back)

		// 메모 내용
		let content = document.getElementById('memoContent');
		let memo = content.value;

		// 즐겨찾기 여부
		let check = document.getElementById('memoCheck').checked;
		
		console.log(check)

		// 무작위 left, top 값
		let left = getLeft();
		let top = getTop();

		// 서버로 데이터 전송
		let jsondata = {
			"memoCn": memo,
			"memoBg": back,
			"memoBkmkYn": check ? 'Y' : 'N',
			"memoX": left,
			"memoY": top,
			"memoZindex": zindex
		};
		console.log(jsondata);
		console.log(JSON.stringify(jsondata));

		$.ajax({
			type: "post",
			url: "/memo",
			contentType: "application/json",
			dataType: "json",
			data: JSON.stringify(jsondata),
			success: function() {
				console.log("성공");
				
				// 클릭 이벤트를 여러 번 바인딩하지 않도록 이전에 바인딩된 이벤트를 언바인딩
				$('#addMemo').off('click');		
						
				// 입력 필드 초기화
			    $('#memoContent').val(''); // 메모 내용 초기화
			    $('#memoBColor').val($('#memoBColor option:first').val()); // 배경색 초기화
			    $('#memoCheck').prop('checked', false); // 즐겨찾기 초기화
				$(".txtMemo").remove();
				$(".ui-draggable-handle").remove();
				memoList();
			},
			error: function(request, status, error) {
				console.log("code: " + request.status)
				console.log("message: " + request.responseText)
				console.log("error: " + error);
			}
		});

		openMemo.style.display = "none";

	});


}

function deleteAllModal() {
	let empCd = document.getElementById("empCd").value;
	console.log(empCd);
	
	Swal.fire({
        title: '메모 전체 삭제 하시겠습니까?',
        text: "다시 되돌릴 수 없습니다.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: '승인',
        cancelButtonText: '취소',
        reverseButtons: false, // 버튼 순서 거꾸로
        
      }).then(result => {
        if (result.isConfirmed) { // 만약 모달창에서 confirm 버튼을 눌렀다면
        
    	$.ajax({
    		url:"/memo/all",
    		type:"DELETE",
    		contentType: "application/json;charset=UTF-8",
    		data : empCd,
    		dataType : 'text',
    		success : function(res) {
        		fMClose();
          		Swal.fire('삭제되었습니다.', '','success');
				$(".txtMemo").remove();
				$(".ui-draggable-handle").remove();
				memoList();
    		},
    		error: function (xhr) {
	          // 삭제 요청이 실패한 경우를 처리합니다.
	          console.log("메모 삭제 중 오류:", xhr.responseText);
	          Swal.fire('메모 삭제 중 오류가 발생했습니다.', '', 'error');
	        }
    	})
        }
     });
}

// ---------------------------------------------------------------------------------------------------------

// 모달 닫기 함수
function fMClose() {
	// 모달을 숨기는 설정
	openMemo.style.display = "none";
	
	// 입력 필드 초기화
    $('#memoContent').val(''); // 메모 내용 초기화
    $('#memoBColor').val($('#memoBColor option:first').val()); // 배경색 초기화
    $('#memoCheck').prop('checked', false); // 즐겨찾기 초기화
}


