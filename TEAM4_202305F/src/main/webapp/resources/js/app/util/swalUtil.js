/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 20.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 20.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 


//에러창
function fn_swalError(){
	Swal.fire({
		text: "처리 중 에러가 발생했습니다. 다시 시도하세요.",
		icon: "error",
		//showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			//cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	})
}


/**
 * 알림창
 * text  : 문구
 * icon  : 아이콘
 */
function fn_swalAlert(text, icon){
	Swal.fire({
		text: text,
		icon: icon,
		//showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			//cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	})
}

/**
 * 완료 시 알림창
 * text  : 문구
 * icon  : 아이콘
 * moveUrl  : 버튼 클릭 시 이동할 주소
 * succeccYn  : 성공여부
 */
function fn_swalComplete(text, icon, moveUrl, succeccYn){
	
	Swal.fire({
		text: text,
		icon: icon,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
		},
		buttonsStyling: false
	}).then((result) => {
		if (succeccYn != "N") {
			if (result.isConfirmed) {
				location.href = moveUrl;
			}
		}
	});
}

/**
 * confirm창
 * text  : 문구
 * handler  : 버튼클릭 시 실행할 함수
 */
function fn_swalConfirm(text, handler){
	
	Swal.fire({
		text: text,
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: '확인',
		customClass: {
			confirmButton: 'btn btn-primary me-2',
			cancelButton: 'btn btn-label-secondary'
		},
		buttonsStyling: false
	}).then((result) => {
		if (result.isConfirmed) {
			handler();
		}
	});
}