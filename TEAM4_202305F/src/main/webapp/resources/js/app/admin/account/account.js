/**
 * <pre>
 * 
 * </pre>
 * @author 김보영
 * @since 2023. 11. 15.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        	수정자       수정내용
 * --------     	--------    ----------------------
 * 2023. 11. 15.    김보영       최초작성 , ag그리드
 * 2023. 11. 20.    김보영       직원CRUD
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */
//------------------------------------------------------ 
//시연용

function fillDummyData() {
    // 각 입력 태그에 더미값 설정
    document.getElementById('empName').value = '홍길동';
    document.getElementById('empSsn').value = '980101-1234567';
    document.getElementById('empPw').value = '980101';
    document.getElementById('confirmPassword').value = '980101';
    document.getElementById('empHiredate').value = '2023-01-01';
    document.getElementById('empMail').value = 'dummy@example.com';
    document.getElementById('empPosition').value = '팀원';
    document.getElementById('empTelno').value = '010-1234-5678';
    document.getElementById('empExtension').value = '070-1234-5678';
    document.getElementById('empZip').value = '12345';
    document.getElementById('empAdres').value = '세종시 새샘마을';
    document.getElementById('empAdresDetail').value = '101동 1001호';
}



 
 
//------------------------------------------------------ 

let fv;

//페이지 로딩 후 실행
$(function () {

	fn_suprrList();
	
	/*부서에따른 직원목록 조회*/
	$("#deptCd").on("change", function () {

		fn_suprrList();

	})
	
	fv = FormValidation.formValidation(document.querySelector('#formAccountSettings'), {
	    fields: {
	    	empName: {
	        validators: {
	          notEmpty: {
	            message: '이름을 입력하세요.'
	          },
	          stringLength: {
	            max: 10,
	            min:2,
	            message: '이름을 2~10자리 이내로 입력하세요.'
	          }
	        }
	      },
	    	empMail: {
	        validators: {
	          notEmpty: {
	            message: '이메일을 입력하세요.'
	          },
	          regexp: {
      			regexp: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
     			 message: '이메일 형식이 올바르지 않습니다.'
   			  }
	        }
	      },
	    	empHiredate: {
	        validators: {
	          notEmpty: {
	            message: '입사일을 입력하세요.'
	          }
	        }
	      },
	    	deptCd: {
	        validators: {
	          notEmpty: {
	            message: '부서를 선택하세요.'
	          }
	        }
	      },
	    	empSuprr: {
	        validators: {
	          notEmpty: {
	            message: '상급자를 선택하세요.'
	          }
	        }
	      },
	    	empRank: {
	        validators: {
	          notEmpty: {
	            message: '직급을 선택하세요.'
	          }
	        }
	      },
	    	empPosition: {
	        validators: {
	          notEmpty: {
	            message: '직책을 선택하세요.'
	          }
	        }
	      },
	    	empAdres: {
	        validators: {
	          notEmpty: {
	            message: '주소를 입력하세요.'
	          }
	        }
	      },
	    	empAdresDetail: {
	        validators: {
	          notEmpty: {
	            message: '상세주소를 입력하세요.'
	          }
	        }
	      },
	    	empTelno: {
	        validators: {
	          notEmpty: {
	            message: '휴대전화 번호를 입력하세요.'
	          },
	          regexp: {
      			regexp: /^\d{3}-\d{3,4}-\d{4}$/,
     			 message: '휴대전화 형식이 올바르지 않습니다.(예: 000-0000-0000)'
   			  }
	        }
	      },
	    	empSsn: {
	        validators: {
	          notEmpty: {
	            message: '주민번호를 입력하세요.'
	          },
	          regexp: {
      			regexp: /^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$/,
     			 message: '주민번호 형식이 올바르지 않습니다. (예: 000000-0000000)'
   			  }
	        }
	      },
	    	empPw: {
	        validators: {
	          notEmpty: {
	            message: '비밀번호를 입력하세요.'
	          },
	           stringLength: {
                max: 15,
                min: 6,
                message: '비밀번호를 6~15자 이내로 입력하세요.'
              }
	        }
	      },
	    	empZip: {
	        validators: {
	          notEmpty: {
	            message: '우편번호를 입력하세요.'
	          },
	           regexp: {
      			regexp: /^\d{5}$/,
     			message: '숫자 5자리를 입력하세요.'
   			  }
	        }
	      },
	      confirmPassword: {
            validators: {
              notEmpty: {
                message: '비밀번호를 한번 더 입력하세요'
              },
              identical: {
                compare: function () {
                  return document.querySelector('[name="empPw"]').value;
                },
                message: '비밀번호가 동일하지 않습니다.'
              },
            }
          }
		},
	    plugins: {
	      trigger: new FormValidation.plugins.Trigger(),
	      bootstrap5: new FormValidation.plugins.Bootstrap5({
	        eleValidClass: '',
	        rowSelector: '.input-group'
	      }),
	      //submitButton: new FormValidation.plugins.SubmitButton(),
	      // Submit the form when all fields are valid
	      // defaultSubmit: new FormValidation.plugins.DefaultSubmit(),
	      autoFocus: new FormValidation.plugins.AutoFocus()
	    },
        init: instance => {
          instance.on('plugins.message.placed', function (e) {
            if (e.element.parentElement.classList.contains('input-group')) {
              e.element.parentElement.insertAdjacentElement('afterend', e.messageElement);
            }
          });
        }
   });
})
//--------------------------------------------------------------------------------------------

/* 직원목록 AG-GRID */
const gridOptions = {
	// define grid columns
	columnDefs: [
		{
			field: 'empProfileImg', headerName: '프로필사진'
			, cellRenderer: function (row) {
				if (row.data.empProfileImg != null) {
					return "<img src='" + row.data.empProfileImg + "' style='height: 100%;'/>";
				}else{
					return "<img src='/resources/images/basic.png' style='height: 100%;'/>";
				}
			}
		},
		{ field: 'empCd', headerName: '사번' },
		{ field: 'empName', headerName: '이름' },
		{ field: 'empAdres', headerName: '주소' },
		{ field: 'empBirth', headerName: '생년월일' },
		{ field: 'empSsn', headerName: '주민번호' },
		{ field: 'empRank', headerName: '직급' },
		{ field: 'empPosition', headerName: '직책' },
		{ field: 'empHiredate', headerName: '입사일' },
		{ field: 'deptName', headerName: '부서명' },
		{ field: 'empMail', headerName: '이메일' },
		{ field: 'empTelno', headerName: '휴대전화' },
		{ field: 'empExtension', headerName: '내선전화' },
	],
	rowHeight: 60,
	defaultColDef: {
		sortable: true,
		resizable: true,
		filter: true,
		width: 150,
	},
	pagination: true,
	paginationAutoPageSize: false,
	paginationPageSize: 10,
	onRowClicked: function (event) {
		console.log(event);
		location.href = "/account/edit?empCd=" + event.data.empCd
	}
};


//페이지 로딩 후 실행
document.addEventListener('DOMContentLoaded', function () {

	fn_setAgGrid();//AG그리드 세팅

});


function fn_setAgGrid() {
	var gridDiv = document.querySelector("#empList");
	if (gridDiv != null) {

		new agGrid.Grid(gridDiv, gridOptions);


		const httpRequest = new XMLHttpRequest();
		httpRequest.open('GET', '/account/empList');
		httpRequest.send();

		httpRequest.onreadystatechange = function () {
			if (httpRequest.readyState === 4 && httpRequest.status === 200) {
				httpResult = JSON.parse(httpRequest.responseText);

				var json = [];
				for (var i = 0; i < httpResult.empList.length; i++) {
					var obj = new Object();

					obj.empProfileImg = httpResult.empList[i].empProfileImg;
					obj.empCd = httpResult.empList[i].empCd;
					obj.empName = httpResult.empList[i].empName;
					obj.empAdres = httpResult.empList[i].empAdres+"　"+httpResult.empList[i].empAdresDetail;
					obj.empBirth = httpResult.empList[i].empBirth;
					obj.empSsn = httpResult.empList[i].empSsn;
					obj.empRank = httpResult.empList[i].empRank;
					obj.empPosition = httpResult.empList[i].empPosition;
					obj.empHiredate = httpResult.empList[i].empHiredate;
					obj.deptName = httpResult.empList[i].dept.deptName;
					obj.empMail = httpResult.empList[i].empMail;
					obj.empTelno = httpResult.empList[i].empTelno;
					obj.empExtension = httpResult.empList[i].empExtension;
					json.push(obj);
				}

				gridOptions.api.setRowData(json);
			}
		};
	}
}

function excelDownload() {
	location.href = '/account/excel/download';
}

function accountForm() {
	location.href = '/account/form';
}

function fn_goList() {
	location.href = '/account/home';
}


/* 우편번호찾기	*/

function execDaumPostcode() {
	new daum.Postcode({
		oncomplete: function (data) {
			document.getElementById('empZip').value = data.zonecode;
			document.getElementById('empAdres').value = data.address;
		}
	}).open();
}


function fn_suprrList() {

	let settings = {

		url: '/account/suprr',
		contentType: 'application/json',
		method: "get",
		dataType: "json",
		data: {
			"deptCd": $("#deptCd").val()
		}
	};

	let tags = "";

	$.ajax(settings)
		.done(function (resp) {
			console.log(resp)
			let suprrList = resp;

			tags = `
				 <option value="">(선택)</option>
			`;
			if (suprrList != null) {
				$.each(suprrList, function (i, v) {

					let sel = "";
					if(v.empCd == $("#tempEmpSuprr").val()){
						sel = "selected";
					}
					tags += `
						<option value="${v.empCd}" ${sel}>${v.empName}</option>
					`;
				})
			}
			$("#empSuprr").html(tags);
		})


}

//------------------------------------------------------------
//직원 등록 비동기

function fn_insertEmp() {

	fv.validate().then(function(status) {
		
		if(status !== "Valid"){
			return;
		}
		
		//저장확인모달창
		fn_swalConfirm("저장하시겠습니까?", function(){
			//비동기
			var data = new FormData($("#formAccountSettings")[0]);
	
			$.ajax({
				type: "POST",
				enctype: 'multipart/form-data',
				url: "/account/insert",
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "등록이 완료되었습니다.";
						icon = "success";
					} else {
						text = "등록이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/account/home", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	
	
	});
}

//--------------------------------------------------------------------







//직원수정
function fn_updateEmp(){
	
	fv.validate().then(function(status) {
	
		if(status !== "Valid"){
			return;
		}
		
		//수정확인모달창
		fn_swalConfirm("수정하시겠습니까?", function(){
			//비동기
			var data = new FormData($("#formAccountSettings")[0]);
	
			$.ajax({
				type: "PUT",
				enctype: 'multipart/form-data',
				url: "/account/update",
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "수정이 완료되었습니다.";
						icon = "success";
					} else {
						text = "수정이 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/account/home", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	});
}

//------------------------------------------------------------------------
//직원 삭제
function fn_deleteEmp(){
	
	//삭제확인모달창
	if(accountActivation.checked == true){
	
		fn_swalConfirm("삭제하시겠습니까?", function(){
			//비동기
			var data = new FormData($("#formAccountDeactivation")[0]);
	
			$.ajax({
				type: "PUT",
				enctype: 'multipart/form-data',
				url: "/account/delete",
				data: data,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					var text;
					var icon;
					if (data.success == "Y") {
						text = "삭제가 완료되었습니다.";
						icon = "success";
					} else {
						text = "삭제가 실패되었습니다.";
						icon = "warning";
					}
					//모달창열기
					fn_swalComplete(text, icon, "/account/home", data.success);
				},
				error: function () {
					fn_swalError();
				}
			});
		});
	}
}


// 이미지파일 미리보기
let accountUserImage = document.getElementById('uploadedAvatar');
const fileInput = document.querySelector('#input-file');

if (accountUserImage) {
	const resetImage = accountUserImage.src;
	fileInput.onchange = () => {
		if (fileInput.files[0]) {
			accountUserImage.src = window.URL.createObjectURL(fileInput.files[0]);
		}
	};
	
//	resetFileInput.onclick = () => {
//		fileInput.value = '';
//		accountUserImage.src = resetImage;
//	};
}

