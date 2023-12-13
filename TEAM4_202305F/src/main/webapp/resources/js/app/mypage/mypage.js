/**
 * <pre>
 * 
 * </pre>
 * @author 작성자명
 * @since 2023. 11. 28.
 * @version 1.0
 * <pre>
 * [[개정이력(Modification Information)]]
 * 수정일        수정자       수정내용
 * --------     --------    ----------------------
 * 2023. 11. 28.      작성자명       최초작성
 * Copyright (c) 2023 by DDIT All right reserved
 * </pre>
 */ 

$( document ).ready(function() {

	// 프로필 모달이 열릴 때 발생하는 이벤트
    $('#onboardImageModal').on('show.bs.modal', function (e) {
		let show = document.querySelectorAll(".modal-backdrop");
			console.log(show);
			for(let i=0; i<show.length; i++){
				console.log(show[0])
				show[0].setAttribute("style", "display: block;")
			}		
        console.log('프로필 모달이 열림');
        // 여기에 원하는 작업을 추가하면 됩니다.
		mypageInfo();
    });

    // 프로필 모달이 닫힐 때 발생하는 이벤트
    $('#onboardImageModal').on('hide.bs.modal', function (e) {
		let modal = document.getElementById("onboardImageModal");
		modal.style.display="none";
        console.log('프로필 모달이 닫힘');
        // 여기에 원하는 작업을 추가하면 됩니다.
    });

	
});
  

// QR 코드를 생성할 값입니다
    //const qrValue = "${realUser.username}"; 
	let qrValue = document.getElementById("empCd").value;  
    const qrCodeContainer = document.getElementById("qrcode");   
 
//     여기서 크기조절
    const qrCode = new QRCode(qrCodeContainer, {
        text: qrValue, 
        width: 150,
        height: 150,
        colorDark : "#000000",
        colorLight : "#ffffff",
        correctLevel : QRCode.CorrectLevel.H
    });


function mypageInfo(){
	let nameMypage = document.getElementById("nameMypage"); // 이름	
	let empCdMypage = document.getElementById("empCdMypage"); // 사번
	let hiredateMypage = document.getElementById("hiredateMypage"); // 입사일
	let profileMypage = document.getElementById("profileMypage"); // 프로필이미지
	let nameMypage1 = document.getElementById("nameMypage1"); // 이름	
	let deptnameMypage = document.getElementById("deptnameMypage"); // 부서명
	let positionMypage = document.getElementById("positionMypage"); // 직책
	let ranknameMypage = document.getElementById("ranknameMypage"); // 직급명
	let zipMypage = document.getElementById("zipMypage"); // 우편번호
	let adresMypage = document.getElementById("adresMypage"); // 주소1
	let adresdetailMypage = document.getElementById("adresdetailMypage"); // 주소2
	let extensionMypage = document.getElementById("extensionMypage"); // 내선전화
	let empTelnoMypage = document.getElementById("empTelnoMypage"); // 휴대폰번호
	let emailMypage = document.getElementById("emailMypage"); //이메일
	let signMypage = document.getElementById("signMypage"); // 서명이미지
	
	$.ajax({
		type:"GET",
		url:"/mypage/info",
		contentType: "application/json",
		dataType: 'json',
		success:function(res){
			console.log("%%%%",res)
			nameMypage.innerHTML = res.empName; // 이름					
			empCdMypage.innerHTML = "사번 : " + res.empCd; // 사번
			hiredateMypage.innerHTML = "입사일 : "  + (res.empHiredate).substr(0,4) + "-" + (res.empHiredate).substr(4,2) + "-" + (res.empHiredate).substr(6,2) // 입사일
			profileMypage.setAttribute("src", res.empProfileImg);// 프로필이미지
			nameMypage1.innerHTML = res.empName; // 이름
			deptnameMypage.innerHTML = res.deptName; // 부서명
			positionMypage.innerHTML = res.empPosition; // 직책
			ranknameMypage.innerHTML = res.rankName; // 직급명
			zipMypage.innerHTML = res.empZip; // 우편번호
			adresMypage.innerHTML = res.empAdres; // 주소1
			adresdetailMypage.innerHTML = res.empAdresDetail; // 주소2
			extensionMypage.innerHTML = res.empExtension; // 내선전화
			empTelnoMypage.innerHTML = res.empTelno; // 휴대폰번호
			emailMypage.innerHTML = res.empMail; //이메일
			signMypage.setAttribute("src", "data:image/*;base64,"+res.empSignImgBase64); // 서명이미지
						
			res.deptCd // 부서코드
			
			
			res.Birth // 생일
			
			
						
			res.Falg // 로그인 유형
			
			
			res.Ssn // 주민번호
			
			
		},
		error: function(request, status, error) {
			console.log("code: " + request.status)
			console.log("message: " + request.responseText)
			console.log("error: " + error);
		}
	});
	
};

function updateClick(){	
	
	let pwMypage = document.getElementById("pwMypage"); // 비밀번호 
	let pwReMypage = document.getElementById("pwReMypage"); // 비밀번호 확인
	let zipMypage = document.getElementById("zipMypage"); // 우편번호
	let adresMypage = document.getElementById("adresMypage"); // 주소1
	let adresdetailMypage = document.getElementById("adresdetailMypage"); // 주소2
	let extensionMypage = document.getElementById("extensionMypage"); // 내선전화
	let empTelnoMypage = document.getElementById("empTelnoMypage"); // 휴대폰번호
	let emailMypage = document.getElementById("emailMypage"); //이메일
	
	console.log(zipMypage.innerHTML)
	console.log(adresMypage.innerHTML)
	console.log(adresdetailMypage.innerHTML)
	console.log(extensionMypage.innerHTML)
	console.log(empTelnoMypage.innerHTML)
	console.log(emailMypage.innerHTML)
	
	let data = { "zipMypage":zipMypage.innerHTML, "adresMypage" : adresMypage.innerHTML, "adresdetailMypage":adresdetailMypage.innerHTML, "extensionMypage":extensionMypage.innerHTML, "empTelnoMypage":empTelnoMypage.innerHTML, "emailMypage":emailMypage.innerHTML};
	
	let onboardImageModal = document.getElementById("onboardImageModal");
	onboardImageModal.style.display="none";
	input(data);
	
	
}
function input(data) {
	
    Swal.fire({
        input: 'password',
        inputLabel: '비밀번호를 입력하세요',
        inputPlaceholder: '비밀번호를 입력하세요',
        showCancelButton: true,
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        preConfirm: (value) => {
            // 여기서 비밀번호를 확인하고 정상이면 반환
            return new Promise((resolve) => {
			console.log("버튼 클릭")
			console.log(data)	
					let jsondata = {
						"empPw" : value
					}					
					console.log(jsondata);
				
				$.ajax({
					url:"/mypage/pw",
					contentType: "application/json;charset=utf-8",
					data:JSON.stringify(jsondata),
					type:"post",
					dataType:'json',
					success:function(res){
						console.log("res : " ,res);
						let onboardImageModal = document.getElementById("onboardImageModal");
						onboardImageModal.style.display="block";
						document.querySelector(".modal-backdrop").style.display="block";
						resolve();
					},
					error:function(xhr){
						console.log(xhr)
						Swal.showValidationMessage('비밀번호가 일치하지 않습니다.');
						document.querySelector(".modal-backdrop").style.display="none";
						resolve();
					}
					
				});
			
            });
        }
    })
    .then((result) => {
		if (result.dismiss === Swal.DismissReason.cancel) {
	        console.log('취소 버튼을 눌렀습니다.');

			let onboardImageModal = document.getElementById("onboardImageModal");
			onboardImageModal.style.display="none";
			document.querySelector(".modal-backdrop").style.display="none";
	        // 원하는 이벤트를 여기에 추가
	    }
        // 이후 동작 수행
        if (result.isConfirmed) {
	
			let updateProfile = `
				<label class="btn btn-primary mb-3 p-2 rounded" for="inputMypageImg" id="profile_update_Img">프로필 변경</label>
                <input type="file" id="inputMypageImg" onchange="updateMypageProfile()" name="empProfileImage" style="display:none;" />
			`;
	
			let profileUp = document.getElementById("profileUp");
			profileUp.innerHTML = updateProfile;
			
            // 비밀번호가 일치할 때의 로직
            let passwordText = `
                <i class="bx bx-check re"></i><span class="fw-medium mx-2 s_tit" _msttexthash="10057853" _msthash="286">비밀번호:</span> 
                <input type="password" class="form-control" _msttexthash="22410817" _msthash="287" id="pwMypage" ></span>
            `;

            let passwordReText = `
                <i class="bx bx-check re"></i><span class="fw-medium mx-2 s_tit" _msttexthash="10057853" _msthash="286">비밀번호 확인:</span> 
                <input type="password" class="form-control" _msttexthash="22410817" _msthash="287" id="pwReMypage"></span>
            `;
            let password = document.getElementById("Pw");
            password.innerHTML = passwordText;

            let passwordRe = document.getElementById("rePw");
            passwordRe.innerHTML = passwordReText;

            let extensionPhone = `
                <i class="bx bx-phone"></i><span class="fw-medium mx-2 s_tit" _msttexthash="10126389" _msthash="295">내선전화:</span> 
                <input type="text" class="form-control" _msttexthash="111761" _msthash="296" id="extensionMypage" value="${data.extensionMypage}" placeholder="- 포함 입력">
            `;

            let extensionPhoneMypage = document.getElementById("extensionPhoneMypage");
            extensionPhoneMypage.innerHTML = extensionPhone;

            let cellPhone = `
                <i class="bx bx-chat"></i><span class="fw-medium mx-2 s_tit" _msttexthash="23102430" _msthash="297">휴대폰:</span> 
                <input type="text" class="form-control" _msttexthash="9034857" _msthash="298" id="empTelnoMypage" value="${data.empTelnoMypage}" placeholder="- 포함 입력">
            `;

            let cellPhoneMypage = document.getElementById("cellPhoneMypage");
            cellPhoneMypage.innerHTML = cellPhone;

            let eMail = `
                <i class="bx bx-envelope"></i><span class="fw-medium mx-2 s_tit" _msttexthash="15589756" _msthash="299">이메일:</span> 
                <input type="email" class="form-control" _msttexthash="421655" _msthash="300" id="emailMypage" value="${data.emailMypage}" placeholder="@ 포함 입력">
            `;

            let myMailMypage = document.getElementById("myMailMypage");
            myMailMypage.innerHTML = eMail;

            let myPageUpdate = `
                <button class="btn btn-primary btn-icon btn-sm updateMypage" id="updateMypage" onclick="updateMypage()"><i class="bx bx-user"></i><span>확인</span></button>
                <button class="btn btn-primary btn-icon btn-sm updateMypage" id="cancellMypage" onclick="cancellMypage()"><i class="bx bx-user"></i><span>취소</span></button>
            `;

            let buttonMypage = document.getElementById("buttonMypage");
            buttonMypage.innerHTML = myPageUpdate;

            let empZipMypage = `
              <i class="bx bx-map"></i><span class="fw-medium mx-2 s_tit" _msttexthash="15295280" _msthash="292">우편번호:</span>
                  <div class="row">
                    <div class="mb-6 col-md-9 pd0">
                      <input class="form-control " type="text" name="empZip" id="empZip" maxlength="5" required value="${data.zipMypage}">
                    </div>
                    <div class="mb-6 col-md-3 ">
                      <button class="btn btn-primary btn-icon btn-sm updateMypage" onclick="execDaumPostcode()" id="searchMypage">검색</button>
                    </div>
                  </div>
            `;

            let myZipMypage = document.getElementById("myZipMypage");
            myZipMypage.innerHTML = empZipMypage;

            let adressMypage = `
            <i class="bx bx-map"></i><span class="fw-medium mx-2 s_tit" _msttexthash="15295280" _msthash="292">주소:</span>
                <div class="row"> 
                      <input class="form-control sinput_02 mr10 mb10" type="text" id="empAdres" name="empAdres"  required value="${data.adresMypage}">
					  <input type="text" class="form-control sinput_02" id="empAdresDetail" name="empAdresDetail" required value="${data.adresdetailMypage}">
                </div>
                
            `;

            let myAdressMypage = document.getElementById("myAdressMypage");
            myAdressMypage.innerHTML = adressMypage;
        }
    });
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

function updateMypageProfile(){
	let accountUserImage = document.getElementById("profileMypage");
	console.log(accountUserImage)
	let fileInput = document.getElementById("inputMypageImg");
	console.log(inputMypageImg)
	

	// 선택한 파일이 있는지 확인
    if (fileInput.files.length > 0) {
        // 선택한 파일의 정보 가져오기
        var selectedFile = fileInput.files[0];

 		// 파일의 이름, 크기, 타입 등 출력
        console.log('File Name:', selectedFile.name);
        console.log('File Size:', selectedFile.size, 'bytes');
        console.log('File Type:', selectedFile.type);

        // 파일을 읽기 위한 FileReader 객체 생성
        var reader = new FileReader();

        // 파일을 읽고 로딩이 완료되면 실행되는 콜백 함수
        reader.onload = function (e) {
            // 이미지 경로를 설정하여 화면에 표시
            accountUserImage.setAttribute("src", e.target.result);
        };

        // 파일을 읽기
        reader.readAsDataURL(selectedFile);
    } else {
        console.log('파일을 선택하지 않았습니다.');
    }	
}


function updateMypage(){
	let formData = new FormData();
	
	let inputMypageImg = document.querySelector("#inputMypageImg").files[0];
	console.log(inputMypageImg);
	let accountUserImage = document.getElementById("profileMypage");
	console.log(accountUserImage)	
	let pwMypage = document.getElementById("pwMypage").value;
	console.log(pwMypage);
	let pwReMypage = document.getElementById("pwReMypage").value;
	console.log(pwReMypage);
	let empZip = document.getElementById("empZip").value;
	console.log(empZip);
	let empAdres = document.getElementById("empAdres").value;
	console.log(empAdres);
	let empAdresDetail = document.getElementById("empAdresDetail").value;
	console.log(empAdresDetail);
	
	let extensionMypage = document.getElementById("extensionMypage").value;
	console.log(extensionMypage);
	let empTelnoMypage = document.getElementById("empTelnoMypage").value;
	console.log(empTelnoMypage);
	let emailMypage = document.getElementById("emailMypage").value;
	console.log(emailMypage);
	
	if(inputMypageImg){
		formData.append("empProfileImage",inputMypageImg);
	}
	formData.append("empPw", pwMypage);
	formData.append("empZip", empZip);
	formData.append("empAdres", empAdres);
	formData.append("empAdresDetail", empAdresDetail);
	
	formData.append("empExtension", extensionMypage);
	formData.append("empTelno", empTelnoMypage);
	formData.append("empMail", emailMypage);
	
	
	console.log(formData)
	
	if(pwMypage != pwReMypage){
		 Swal.fire({
	      icon: 'error',
	      title: '비밀번호 오류',
	      text: '비밀번호를 다시 한번 확인해주세요.',
	    });
	}else if(!pwMypage){
		Swal.fire({
	      icon: 'error',
	      title: '비밀번호 미입력',
	      text: '비밀번호를 입력하시오.',
	    });
	}else{
		const Toast = Swal.mixin({
	      toast: true,
	      position: 'center-center',
	      showConfirmButton: false,
	      timer: 3000,
	      timerProgressBar: true,
	      didOpen: (toast) => {
	        toast.addEventListener('mouseenter', Swal.stopTimer)
	        toast.addEventListener('mouseleave', Swal.resumeTimer)
	      }
	    })

		$.ajax({
			type:"PUT",
//			enctype: 'multipart/form-data',
			url:"/mypage",
			contentType:false,
			processData: false,
			data:formData,
			success:function(res){
				console.log(res);
				
				let onboardImageModal = document.getElementById("onboardImageModal");
				onboardImageModal.style.display="none";
				document.querySelector(".modal-backdrop").style.display="none";
				
			    Toast.fire({
			      icon: 'success',
			      title: '회원 정보가 정상적으로 수정되었습니다.'
			    })
				
			},
			error: function(request, status, error) {
				console.log("code: " + request.status)
				console.log("message: " + request.responseText)
				console.log("error: " + error);
			}
		});
	

	}
	
}


function cancellMypage(){
	console.log("22")
	let modal = document.getElementById("onboardImageModal");
	modal.style.display="none";
	
	let show = document.querySelectorAll(".modal-backdrop");
	console.log(show);
	for(let i=0; i<show.length; i++){
		console.log(show[0])
		show[0].setAttribute("style", "display: none;")
	}
}

