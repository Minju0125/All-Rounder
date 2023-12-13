<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<%
  String contextPath = request.getContextPath();
%>
<div class="webRoute" style="display: flex;" data-web-user="${cours}"></div>
<div class="card" id="webBody" style="width: 100%; height: 90%;">
	<div class="card-body" id="listBody"
		style="display: flex; flex-wrap: wrap;">
		<%-- <c:forEach items="${webList}" var="web"> --%>
		<!-- 	<tr> -->
		<%-- 	<c:if test="${empty web.webTy}"> --%>
		<%-- 		<td><a href="<c:url value='/web?webCours=${web.webCours}${web.webSnm}/'/>">폴더 : ${web.webRnm}</a></td> --%>
		<%-- 	</c:if> --%>
		<%-- 	<c:if test="${not empty web.webTy}"> --%>
		<%-- 		<td>파일 : ${web.webRnm}</td> --%>
		<%-- 	</c:if> --%>
		<!-- 	</tr> -->
		<%-- 	</c:forEach> --%>
	</div>
</div>
<div class="modal fade" id="pricingModal" tabindex="-1" style="display: none;" aria-hidden="true">
	<div class="modal-dialog modal-xl modal-simple modal-pricing">
		<div class="modal-content p-0 p-md-2 p-xl-5">
			<div class="modal-body px-2 px-md-4">
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
				<!-- Pricing Plans -->
				<div class="rounded-top" id="webTextReader">
				</div>
			</div>
			<!--/ Pricing Plans -->
		</div>
	</div>
</div>
<div class="modal fade" id="paymentMethods" tabindex="-1" style="display: none;" aria-hidden="true">
                <div class="modal-dialog modal-lg modal-simple modal-enable-otp modal-dialog-centered">
                  <div class="modal-content p-3 p-md-5">
                    <div class="modal-body">
                      <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                      <div class="text-center mb-4" id="detailHeader">
                      </div>
						<div id="detailBody">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
<script>

$(function(){
// 	console.log(csrfparam);
// 	console.log(csrf);
	ajaxList(cours);
});
let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
let csrf = $("meta[name='_csrf']").attr("content");
let webHardUser=document.querySelector(".webRoute").getAttribute('data-web-user');

// var contextPath='\${pageContext.request.contextPath}';
var cours="/";
var beforeCours="";
var rnm="내 드라이브";
if(webHardUser=='d'){
	rnm="부서 드라이브";
}

var ajaxList=function(cours){
	let uri="/web/list?webCours="+cours+"&who="+webHardUser;
	let xhr=new XMLHttpRequest();
    xhr.open("get",uri,true);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4&&xhr.status==200){
        	let datas = JSON.parse(xhr.responseText) 
        	let target = datas.webList;
//         	console.log(target);
        	makeList(target);
        }
    }
    xhr.send();
}

let div=``;
let webRoute = document.querySelector(".webRoute");

var makeList = function(webList){
// 	$("<div>").addClass("back")
// 	=
// 	let newDiv= document.createElement("div");
// 	newDiv.classList.add("back");
	let newDiv= document.createElement("h4");
	newDiv.setAttribute("data-value", cours);
	newDiv.setAttribute("data-name", rnm);
	newDiv.style.marginRight = "5px";
	newDiv.style.borderRadius= "5px";
	newDiv.textContent =rnm;
	newDiv.addEventListener("click",function(){
		cours=this.getAttribute("data-value");
		deleteCours = webRoute.querySelectorAll(`[data-value*="\${cours}"]`);
		for(let i=0;i<deleteCours.length;i++){
			let ab=cours.length;
			let abc=deleteCours[i].getAttribute("data-value").length;
			if(ab<=abc){
// 				console.log("삭제 : ",deleteCours[i]);
				if(cours!==beforeCours){
					rnm=this.getAttribute("data-name");
					webRoute.removeChild(deleteCours[i]);
				}
			}
		}
// 		deleteCours.forEach(function(deleteCour){
// 			let ab=cours.length;
// 			let abc=deleteCour.length;
// 			if(ab<abc){
// 				webRoute.removeChild(deleteCour);
// 			}
// 		});
		ajaxList(cours);
	});
	newDiv.addEventListener("mouseover",function(){
//		newDiv.onmouseover = function() {
			newDiv.style.background="#ADB5BD";
		});
	newDiv.addEventListener("mouseout",function(){
			newDiv.style.background="#F0F0F2";
		});
	if(cours!==beforeCours){
		webRoute.appendChild(newDiv);
	}
	beforeCours=cours;
// 	div+=`<div class="back" data-value="\${cours}">\${rnm}</div>`;
	let list=``;
	if(cours.length>1){
		list+=`<div class="webRouteFolder" style="width:100px; height:130px; text-align:center; margin: 0 1.5%;" data-value="\${cours}">
			<img style="width:100px; height:100px;" src="<%= contextPath %>/resources/images/webHard/webFolder.png"/>...
			</div>`;
	}
	for(let i=0;i<webList.length;i++){
		list+=`<div class="webFileMenu" style="width:100px; height:130px; text-align:center; margin: 0 1.5%;">`;
		if(!webList[i].webTy){	/* 폴더일 때 */
			list+=
				`<div class="webFolder" data-serial="\${webList[i].webSnm}" data-name="\${webList[i].webRnm}" data-value="\${cours}\${webList[i].webSnm}/">
					<img style="width:100px; height:100px;" src="<%= contextPath %>/resources/images/webHard/webFolder.png"/>\${webList[i].webRnm}
				</div>`;
		}else{	/* 파일일 때 */
			list+=
				`<div class="webFile" data-ty="\${webList[i].webTy}" data-serial="\${webList[i].webSnm}" data-local="\${webList[i].webRnm}" data-value="\${cours}\${webList[i].webSnm}.\${webList[i].webTy}">
					<img style="width:100px; height:100px;" src="<%= contextPath %>/resources/images/webHard/\${webList[i].webTy}.png"/>\${webList[i].webRnm}
				</div>`;
		}
		list+=`</div>`;
	}
// 	console.log("list", list)
// 	let listBody = document.querySelector("#listBody");
	listBody.innerHTML=list;
	
// 	console.log("listBody", document.querySelector("#listBody"))
	var folder=document.querySelectorAll(".webFolder");
	
	for(let i=0; i<folder.length; i++){
		folder[i].addEventListener("dblclick", function(){
	 		cours=this.getAttribute("data-value");
	 		rnm="/       " + this.getAttribute("data-name");
	 		ajaxList(cours);
		});
	}
	
	$('.webRouteFolder').on('dblclick',function(){
//		cours=this.getAttribute("data-value");
		//console.log(this.getAttribute("data-name"));
		deleteCours = webRoute.querySelectorAll(`[data-value*="/"]`);
		cours=deleteCours[deleteCours.length-2].getAttribute("data-value");
		console.log(deleteCours.length);
		console.log(deleteCours);
		console.log(deleteCours[0]);
		console.log(cours);
//		for(let i=0;i<deleteCours.length;i++){
//			let ab=cours.length;
//			let abc=deleteCours[i].getAttribute("data-value").length;
//			if(ab<abc){
// 				console.log("삭제 : ",deleteCours[i]);
//				if(cours!==beforeCours){
					rnm=deleteCours[deleteCours.length-2].getAttribute("data-name");
					webRoute.removeChild(deleteCours[deleteCours.length-1]);
					webRoute.removeChild(deleteCours[deleteCours.length-2]);
//				}
//			}
//		}
		ajaxList(cours);
	});
	
	let webFileMenu=document.querySelectorAll(".webFileMenu");
	
	for(let i=0;i<webFileMenu.length;i++){
		let webFileMenus=webFileMenu[i];
		//var webMenuDiv = document.createElement('div');
		webFileMenus.addEventListener("contextmenu", function(e){
			e.preventDefault();
			
			var target=e.target;
			var webParentNode = null;
			
			while(webParentNode==null){
				if(target.tagName.toLowerCase()==='div'){
					webParentNode=target;
					break;
				}
				target=target.parentNode;
			}
			let dataSerial=webParentNode.getAttribute('data-serial');
			
			webFileMenusRemove(e.target);
			
		    // 메뉴를 담을 div 요소 생성
		    /* if(!webMenuDiv.contains(e.target) && webMenuDiv){
		    	console.log(webMenuDiv);
		    	document.body.removeChild(webMenuDiv);
		    } */
		    var webMenuDiv = document.createElement('div');
		    webMenuDiv.classList.add('webFileMenus');
		    webMenuDiv.style.cursor="pointer";
			
		    console.log(webParentNode.classList[0]);
		    if(webParentNode.classList[0]!=='webFolder'){
		    	// 메뉴 아이템들 생성
			    var webDownload = document.createElement('div');
			    webDownload.textContent = '다운로드';
			    webDownload.style.borderBottom = '1px solid #ccc';
			    webDownload.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
			    webDownload.addEventListener('click', function(e) {
			    	let dataValue=webParentNode.getAttribute('data-value');
			    	let dataLocal=webParentNode.getAttribute('data-local');
				    let dataTy=webParentNode.getAttribute('data-ty');
			        
			        let formData = new FormData();
			    	formData.append("ftpFile",dataValue);
			    	formData.append("localFile",dataLocal);
			    	
 			    	fileDown(formData);
			    	
			    	successPhrases=dataLocal;
			    	const Toast = Swal.mixin({
			    	    toast: true,
			    	    position: 'center-center',
			    	    showConfirmButton: false,
			    	    timer: 1500,
			    	    timerProgressBar: true,
			    	    didOpen: (toast) => {
			    	        toast.addEventListener('mouseenter', Swal.stopTimer)
			    	        toast.addEventListener('mouseleave', Swal.resumeTimer)
			    	    }
			    	    
			    	})

			    	Toast.fire({
			    	    icon: 'success',
			    	    title: '다운로드가 진행되고 있습니다.'
			    	})
			    	
			    	setTimeout(
			    			function() {Swal.fire({
				    	      icon: 'success',
				    	      title: '다운로드가 완료되었습니다.',
				    	      text: successPhrases,
			    			});
			    		},
			    	1500);
			    });
			    
			    webMenuDiv.appendChild(webDownload);
			    
			    let dataTy=webParentNode.getAttribute('data-ty');
			    if(dataTy=='txt'){
		    		// 메뉴 아이템들 생성
				    var webFileReader = document.createElement('div');
				    webFileReader.textContent = '파일 읽기';
				    webFileReader.setAttribute('data-bs-toggle',"modal");
				    webFileReader.setAttribute('data-bs-target',"#pricingModal");
				    webFileReader.style.borderBottom = '1px solid #ccc';
				    webFileReader.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
				    webFileReader.addEventListener('click', function(e) {
				    	let dataValue=webParentNode.getAttribute('data-value');
				    	let dataLocal=webParentNode.getAttribute('data-local');
						let dataTy=webParentNode.getAttribute('data-ty');
						
						webTxtReader(dataValue);
				    });
				    
				    webMenuDiv.appendChild(webFileReader);
			    }
		    }

		    var webDelete = document.createElement('div');
		    webDelete.textContent = '삭제';
		    webDelete.style.borderBottom = '1px solid #ccc';
		    webDelete.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
		    webDelete.addEventListener('click', function() {
		    	let dataLocal=webParentNode.getAttribute('data-local');
	        	let dataSerial=webParentNode.getAttribute('data-serial');
	        	let dataTy=webParentNode.getAttribute('data-ty');
	        	deleteCours=
	        	//console.log("dataLocal : ",dataLocal);
	        	//console.log("dataSerial : ",dataSerial);
		    	realCheck='';
	            if(webParentNode.classList[0]!=='webFolder'){
	            	realCheck+=dataLocal+' 파일을 삭제하시겠습니까?';
	            }else{
	            	realCheck+=dataLocal+' 폴더를 삭제하시겠습니까?';
	            }
		    	Swal.fire({
		        title: realCheck,
		        icon: 'warning',
		        showCancelButton: true,
		        confirmButtonColor: '#3085d6',
		        cancelButtonColor: '#d33',
		        confirmButtonText: '승인',
		        cancelButtonText: '취소',
		        reverseButtons: true, // 버튼 순서 거꾸로
		        
		      }).then((result) => {
		        if (result.isConfirmed) {
			    	successPhrases='';
		            if(webParentNode.classList[0]!=='webFolder'){
		            	successPhrases+=dataLocal+' 파일을 삭제했습니다.';
		            }else{
		            	successPhrases+=dataLocal+' 폴더를 삭제했습니다.';
		            }
		  		    webFileDel(dataSerial,dataTy);
		          Swal.fire(
		            '승인이 완료되었습니다.',
		            successPhrases,
		            'success'
		          )
		        }
		      });
		    });

		    var webUpdName = document.createElement('div');
		    webUpdName.textContent = '이름 변경';
		    webUpdName.style.borderBottom = '1px solid #ccc';
		    webUpdName.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
		    webUpdName.addEventListener('click', function() {
		    	(async () => {
		            const { value: getName } = await Swal.fire({
		                title: '변경할 이름을 입력해주세요.',
		                text: '',
		                input: 'text',
		                inputPlaceholder: '변경 이름'
		            })

			    	let udpName=getName;
			    	let dataSerial=webParentNode.getAttribute('data-serial');
			    	let dataTy=webParentNode.getAttribute('data-ty');
			    	if(dataTy){
			    		udpName+='.'+dataTy;
			    	}

			    	let data={
			    		webSnm:dataSerial,
			    		webRnm:udpName,
			    		webCours:cours
			    	}

		            // 이후 처리되는 내용.
		            if (getName) {
				    	webUdpName(data);
		                Swal.fire({
		                    icon: 'success',
		                    title: '성공적으로 이름이 변경되었습니다.',
		                });
		            }else{
		            	Swal.fire({
		            	      icon: 'warning',
		            	      title: '이름을 입력해주세요.',
		            	    });
		            }
		        })()
		    });
		    
		    var webFileDetail = document.createElement('div');
		    webFileDetail.setAttribute("data-bs-toggle","modal");
		    webFileDetail.setAttribute("data-bs-target","#paymentMethods");
		    webFileDetail.textContent = '상세 보기';
		    webFileDetail.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
		    webFileDetail.addEventListener('click', function() {
		    	let dataSerial=webParentNode.getAttribute('data-serial');
				
		    	selectFtpFileDetail(dataSerial);
		    });

		    // 메뉴 아이템들을 메뉴에 추가
		    webMenuDiv.appendChild(webDelete);
		    webMenuDiv.appendChild(webUpdName);
		    webMenuDiv.appendChild(webFileDetail);

		    // 스타일 설정 - 위치 설정
		    webMenuDiv.style.position = 'absolute';
		    webMenuDiv.style.left = event.clientX + 'px'; // 마우스 클릭 위치 X 좌표
		    webMenuDiv.style.top = event.clientY + 'px'; // 마우스 클릭 위치 Y 좌표
		    webMenuDiv.style.background = '#fff';
		    webMenuDiv.style.padding = '5px';
		    webMenuDiv.style.zIndex = '500'; // 다른 요소 위에 나타나도록 설정

		    // 생성된 div를 body에 추가
		    document.body.appendChild(webMenuDiv);
		    
		    // document에 클릭 이벤트 추가하여 메뉴를 닫음
		    document.addEventListener('click', function(e) {
		       	//if (!webMenuDiv.contains(e.target)) {
					webFileMenusRemove(e.target);
		       	//}
		    });
		    
		    document.addEventListener('contextmenu', function(e) {
		       	//if (!webMenuDiv.contains(e.target)) {
		       		webFileMenusRemove(e.target);
		        //}
		    });
		    
			// 겹쳐지는 이벤트(우클릭) 발생 막기
			event.stopPropagation();
		});
	}
}

function fileUpload(file){
	let formData = new FormData();
	/*
	console.log(formData);
    let web = {
    	file:document.getElementById("file").files[0],
    	webRnm:document.getElementById("webRnm").value,
   		webCours:cours
    };
    console.log(web);
    */
// 	let webRnm=document.getElementById("webRnm");
// 	let file=document.getElementById("file");
// 	let webCours=cours;
	
	//console.log("cours", cours);
	if(file){
		formData.append("file",file);
	}
	//formData.append("webRnm",document.querySelector("#webRnm").value);
	formData.append("webCours",cours);
	formData.append("who",webHardUser);

	let xhr=new XMLHttpRequest();
    xhr.open("post","/web",true);
    //xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	console.log("성공");
        	ajaxList(cours);
        }
    }
    xhr.send(formData);
}
	
function fileDown(form){
	
	let formData = form;
	
	let xhr=new XMLHttpRequest();
    xhr.open("post","/web/download",true);
//     xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	console.log("다운로드");
        }
    }
    xhr.send(formData);
}
	
function webFileDel(dataSerialCode,dataTy){
	//console.log("딜 함수");
	let formData = new FormData();
	
	formData.append("webSnm",dataSerialCode);
	formData.append("webCours",cours);
	formData.append("webTy",dataTy);

	let xhr=new XMLHttpRequest();
    xhr.open("post","/web/delete",true);
//     xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	ajaxList(cours);
        }
    }
    xhr.send(formData);
}
	
function webUdpName(data){
	let xhr=new XMLHttpRequest();
    xhr.open("post","/web/update",true);
    xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	ajaxList(cours);
        }
    }
    xhr.send(JSON.stringify(data));
}

// var second=function(){
// // 	console.log("여기 있다.")
// 	var back=document.querySelectorAll(".back");
// 	console.log(back);
// 	for(let i=0; i<back.length; i++){
// 		back[i].addEventListener("click",function(){
// 			console.log(this);
// 			cours=this.getAttribute("data-value");
// 			console.log("corus : ",cours);
// 			ajaxList(cours);
// 		});
// 	}
// }
// $(document).on("click",".back",function(){
// 	console.log(this);
// 	cours=this.getAttribute("data-value");
// 	console.log("corus : ",cours);
// 	ajaxList(cours);
// });
// 	folder.addEventListner('click',function(){
// 		cours=folder.getAttribute("data-value");
// 		console.log(cours);
// 	});
// }

ajaxList(cours);

// 마우스 우클릭 이벤트
// is_right_click=(event.which==3) || (event.button==2)

// const mouse_end = async(event) => {
//     if(is_right_click) return;
//     console.log("오른 클릭");
// }

// element.addEventListener("mouseup",mouse_end);

// window.oncontextmenu=function(){
//     return false;
// }

function makeFolder(folderName){
	let formData = new FormData();
	formData.append("webRnm",folderName);
	formData.append("webCours",cours);
	formData.append("who",webHardUser);

	let xhr=new XMLHttpRequest();
    xhr.open("post","/web",true);
    //xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	console.log("성공");
        	ajaxList(cours);
        }
    }
    xhr.send(formData);
}

//let listBody=document.getElementById('listBody');

// document에 클릭 이벤트 추가하여 메뉴를 닫음
webBody.addEventListener('contextmenu', function(e) {
	e.preventDefault();
	e.stopPropagation();
	webFileMenusRemove(e.target);
	
    // 메뉴를 담을 div 요소 생성
    var webMenuDiv = document.createElement('div');
    webMenuDiv.classList.add('webFileMenus');
    webMenuDiv.style.cursor="pointer";

    // 메뉴 아이템들 생성
    var newFolder = document.createElement('div');
    newFolder.textContent = '새 폴더';
    newFolder.style.borderBottom = '1px solid #ccc';
    newFolder.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
    newFolder.addEventListener('click', function() {
        // 여기에 추가 기능을 구현할 수 있습니다.
        //console.log('추가 기능 실행');
        (async () => {
            const { value: getName } = await Swal.fire({
                title: '폴더 이름을 입력해주세요.',
                input: 'text',
                inputPlaceholder: '폴더 이름'
            })

            // 이후 처리되는 내용.
            if (getName) {
            	makeFolder(getName);
            	Swal.fire({
            	      icon: 'success',
            	      title: '폴더가 생성되었습니다.',
            	    });
            }else{
            	Swal.fire({
            	      icon: 'warning',
            	      title: '이름을 입력해주세요.',
            	    });
            }
        })()
    });

    var uploadItem = document.createElement('div');
    uploadItem.textContent = '파일 업로드';
    uploadItem.style.margin = '5px'; // 아이템 간격을 줄 바꿈으로 대체
    uploadItem.addEventListener('click', function() {
		var inputElement = document.createElement('input');
        inputElement.type = 'file';
        inputElement.style.display="none";
        
        inputElement.addEventListener('change', function(event) {
            var selectedFile = event.target.files[0]; // 선택된 파일 가져오기
            if (selectedFile) {
            	Swal.fire({
            		title:'파일을 업로드하였습니다.',         // Alert 제목
            		  icon:'success',                         // Alert 타입
            		});
            	fileUpload(selectedFile);
            	
            	const Toast = Swal.mixin({
    	    	    toast: true,
    	    	    position: 'center-center',
    	    	    showConfirmButton: false,
    	    	    timer: 1500,
    	    	    timerProgressBar: true,
    	    	    didOpen: (toast) => {
    	    	        toast.addEventListener('mouseenter', Swal.stopTimer)
    	    	        toast.addEventListener('mouseleave', Swal.resumeTimer)
    	    	    }
    	    	    
    	    	})

    	    	Toast.fire({
    	    	    icon: 'success',
    	    	    title: '파일 업로드가 진행되고 있습니다.'
    	    	})
    	    	
    	    	setTimeout(
    	    			function() {Swal.fire({
    		    	      icon: 'success',
    		    	      title: '파일 업로드가 완료되었습니다.',
    		    	      text: selectedFile.name,
    	    			});
    	    		},
    	    	1500);
            } else {
            	Swal.fire({
            		title: '파일 업로드에 실패하였습니다.',         // Alert 제목
          		icon:'error',                         // Alert 타입
          		});
            }
        });
        
        webBody.appendChild(inputElement);
        inputElement.click();
    });

    // 메뉴 아이템들을 메뉴에 추가
    webMenuDiv.appendChild(newFolder);
    webMenuDiv.appendChild(uploadItem);

    // 스타일 설정 - 위치 설정
    webMenuDiv.style.position = 'absolute';
    webMenuDiv.style.left = event.clientX + 'px'; // 마우스 클릭 위치 X 좌표
    webMenuDiv.style.top = event.clientY + 'px'; // 마우스 클릭 위치 Y 좌표
    webMenuDiv.classList.add('card-body');
    webMenuDiv.style.background = '#fff';
    webMenuDiv.style.padding = '5px';
    webMenuDiv.style.zIndex = '1000'; // 다른 요소 위에 나타나도록 설정

    // 생성된 div를 body에 추가
    document.body.appendChild(webMenuDiv);
    
    // document에 클릭 이벤트 추가하여 메뉴를 닫음
    document.addEventListener('click', function(e) {
		if (!webMenuDiv.contains(e.target)) {
			webFileMenusRemove(e.target);
	    }
    });
    
    document.addEventListener('contextmenu', function(e) {
       	if (!webMenuDiv.contains(e.target)) {
       		webFileMenusRemove(e.target);
        }
    });
});

function webFileMenusRemove(e){
	let webFileMenusCheck=document.querySelectorAll(".webFileMenus");
// 	console.log("여기",e);
// 	console.log("여기",webFileMenusCheck);
	if(webFileMenusCheck?.length >0){
		for(let i=0;i<webFileMenusCheck.length;i++){
//			if(!webFileMenusCheck[i].contains(e)){
// 				debugger;
// 				console.log("삭제돼!!!!!!!!!!!!!!!!!!!!!!!!",e);
				webFileMenusCheck[i].remove();
//			}
		}
	}
}

function webTxtReader(dataValue){
	let xhr=new XMLHttpRequest();
    xhr.open("get","/web/txtRead?ftpFile="+dataValue,true);
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	console.log(xhr.responseText);
//        	console.log(JSON.parse(xhr.responseText));
			// 모달
//	    	let pricingModal=document.getElementById('pricingModal');
//	    	pricingModal.setAttribute('role','dialog');
//	    	pricingModal.style.display="block";

			// 내용 들어갈 곳
	    	let webTextReader=document.getElementById('webTextReader');
			
	    	webTextReader.innerHTML =xhr.responseText;
	    	
//	    	let webBtnClose=document.querySelector('.btn-close');
	    	
//	    	webBtnClose.addEventListener("click",function(){
//	    		pricingModal.style.display="none";
//	    	});
        }
    }
    xhr.send(dataValue);
}

/* function webImgReader(dataValue){
	let xhr=new XMLHttpRequest();
    xhr.open("get","/web/imgRead?ftpFile="+dataValue,true);
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
        if(xhr.readyState==4 && xhr.status==200){
        	console.log(xhr.responseText);
        	//rslt=JSON.parse(xhr.responseText);

			// 내용 들어갈 곳
	    	let webTextReader=document.getElementById('webTextReader');
			img=`<img src="data:image/jpg;base64,\${xhr.responseText}" alt="FTP Image"/>`;
			//img=`<img src="data:image/jpg;base64,${rslt}" alt="FTP Image"/>`;
	    	webTextReader.innerHTML =img;
        }
    }
    xhr.send(dataValue);
} */

function selectFtpFileDetail(dataSerial){
	data = {
		webSnm:dataSerial,
		webCours:cours,
		who:webHardUser
	}
	let xhr=new XMLHttpRequest();
    xhr.open("post","/web/detail",true);
    xhr.setRequestHeader("Content-Type","application/json");
    xhr.setRequestHeader("${_csrf.headerName}",csrf);
    xhr.onreadystatechange=()=>{
    	if(xhr.readyState==4 && xhr.status==200){
        	rslt=JSON.parse(xhr.responseText);
        	detailHeader=document.getElementById('detailHeader');
        	type='';
        	header='';
        	ftpFileMakeDate=rslt.webDate.replace(/(\d{4})(\d{2})(\d{2})/, "$1-$2-$3");
        	if(!rslt.webTy){
        		header+=`<img src="<%= contextPath %>/resources/images/webHard/webFolder.png"`;
        		type='폴더';
        	}else{
        		header+=`<img src="<%= contextPath %>/resources/images/webHard/\${rslt.webTy}.png"`;
        		type='.'+rslt.webTy+' 파일';
        	}
        	header+=` style="width:100px; height:100px;"/><p class="text-muted">\${rslt.webRnm}</p><br>`;
        	
        	detailHeader.innerHTML=header;
        	
        	detailBody=document.getElementById('detailBody');

        	body=``;
        	body+=`<div class="d-flex justify-content-between align-items-center border-bottom pb-3 mb-3">
                <div class="d-flex gap-2 align-items-center">
                <h6 class="m-0">작성자</h6>
              </div>
              <h6 class="m-0 d-none d-sm-block">\${rslt.webMaker}</h6>
            </div>
            <div class="d-flex justify-content-sm-between align-items-center border-bottom pb-3 mb-3">
            <div class="d-flex gap-2 align-items-center">
              <h6 class="m-0">작성일</h6>
            </div>
            <h6 class="m-0 d-none d-sm-block">\${ftpFileMakeDate}</h6>
          </div>
          <div class="d-flex justify-content-between align-items-center border-bottom pb-3 mb-3">
          <div class="d-flex gap-2 align-items-center">
            <h6 class="m-0">종류</h6>
          </div>
          <h6 class="m-0 d-none d-sm-block">\${type}</h6>
        </div>
        <div class="d-flex justify-content-between align-items-center border-bottom pb-3 mb-3">
        <div class="d-flex gap-2 align-items-center">
          <h6 class="m-0">권한자</h6>
        </div>
        <h6 class="m-0 d-none d-sm-block">\${rslt.fileAuthor.faNm}</h6>
      </div>`;
      
      detailBody.innerHTML=body;
        }
    }
    xhr.send(JSON.stringify(data));
}
</script>













