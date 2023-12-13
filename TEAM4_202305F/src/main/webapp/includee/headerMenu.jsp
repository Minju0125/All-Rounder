<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<security:csrfMetaTags/>
	<c:set value="${pageContext.request.serverName }" var="serverSideAddress" />
<script>

		const SERVERSIDEADDRESS = '${serverSideAddress}';
		
		let loginEmpCd = $("#empCd").val();
		console.log("로그인 loginEmpCd ==> " + loginEmpCd)

	    const COpen = ()=>{
	        console.log("[채팅] 접속 성공!!!!!!!");
	        // 서버로 메세지 보내기
	    }
		
	    // message이벤트가 발생했을 때 실행되는 함수
	    const CMsg = ()=>{
	    	let loginEmpCd = $("#empCd").val();
	    	const receivedData = JSON.parse(event.data);
		        
		    let chatRoomCd = receivedData.chatRoomCd;
		    console.log("receivedData ==> " + JSON.stringify(receivedData));
		    //console.log(loginEmpCd);
		    //console.log(receivedData.senderEmpCd);
		    if(receivedData.senderEmpCd==loginEmpCd){
		    }else{
		    	$("#msgIcon").find("i").addClass("bx-tada"); //메시지 오면 꿈틀꿈틀
		    	$(`li[data-value='\${chatRoomCd}']`).addClass("unclicked-chatroom"); //전달받은 채팅방 배경화면 바꾸기
		    }
	    	/*$chatRoomCd 는 채팅방 목록 중 하나를 클릭할 때마다 이 전역변수에 담김, 이 전역변수에 값이 들어있다면 사용자는 현태 특정 채팅방하나를 클릭한 상태임*/
			if(chatRoomCd==$chatRoomCd){//사용자가 현재 선택한 채팅방이라면 바로 띄워줌 (같지 않은 경우는 현재 사용자가 다른 채팅방을 클릭한 상태)
		    	//console.log("[채팅] 서버에서 온 메세지", event.data); //서버가 보낸 내용은 event.data에 담겨넘어옴 //여기서 화면에 뿌려주기
		        //console.log("로그인계정 사번 : ", loginEmpCd, "//발신자 사번 : ", receivedData.senderEmpCd, "//채팅방번호 : "+ chatRoomCd);
		        
				let profileImg = receivedData.senderEmpProfileImg;
		        let startLiTags = "";
		        let cssStyle = "";
		        if (receivedData.senderEmpCd == loginEmpCd) { //본인이면 오른쪽에 출력
		            startLiTags = '<li class="chat-message chat-message-right">';
		            cssStyle = "style='color:white;'"; 
		        } else { //아니면 왼쪽에 출력
		            startLiTags = '<li class="chat-message">';
		            cssStyle = "style='color:grey;'"; 
		        }
		        let formatDate ="";
		        if(!compareToToday(receivedData.sendTime)){
		        	formatDate = `\${receivedData.sendTime}`;
		        }else{
		        	formatDate = `\${receivedData.sendDate}`;
		        }
		        let liTags = `
		                    \${startLiTags}
		                        <div class="d-flex overflow-hidden">
		                            <div class="chat-message-wrapper flex-grow-1">
		                                <div class="chat-message-text">
		                                    <p class="mb-0" \${cssStyle}>\${receivedData.sendContent}</p>
		                                </div>
		                                <div class="text-end text-muted mt-1">
		                                    <i class="bx bx-check-double text-success"></i> 
		                                    <small>\${formatDate}</small>
		                                </div>
		                            </div>
		                            <div class="user-avatar flex-shrink-0 ms-3">
		                                <div class="avatar avatar-sm">
		                                    <img src="\${profileImg}" alt="Avatar"
		                                        class="rounded-circle" />
		                                </div>
		                                <div>\${receivedData.senderEmpName}</div>
		                            </div>
		                        </div>
		                    </li>
		                    `;
		   		$("#historyUl").append(liTags);
			//기존 대화 가져오기
	        var storageData = []; //데이터가 쌓일 배열
	        let existingData = localStorage.getItem(chatRoomCd);
	        if (existingData) { //기존데이터가 있다면
	            storageData = JSON.parse(existingData);
	        }
	        //console.log(storageData);
	        storageData.push(receivedData);
	   		localStorage.setItem(chatRoomCd, JSON.stringify(storageData));
		
	   		
	    }
	    }

<%--============================== 여기부터 실시간알림 ===========================--%>
	    
/*	    const fOpen = ()=>{
	        console.log("◀◀◀◀ [알림] 접속 성공 ▶▶▶▶");
	        // 서버로 메세지 보내기
	        
	    }
	    
	    // message이벤트가 발생했을 때 실행되는 함수
	    //서버가 보낸 내용은 event.data에 담겨넘어옴
	    const fMsg = ()=>{
	        console.log("◀◀◀◀ [알림] 서버에서 온 메세지 ▶▶▶▶",event.data); 
	        var data = event.data;
	        // toast
	        let toast = "<div class='bs-toast toast toast-placement-ex m-2' role='alert' aria-live='assertive' aria-atomic='true' data-bs-delay='2000'>";
	        toast += "<div class='toast-header'><i class='bx bx-bell me-2'></i><div class='me-auto fw-medium'>알림</div>";
	        toast += "<small class='text-muted'>just now</small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
	        toast += "<span aria-hidden='true'>&times;</span></button>";
	        toast += "</div> <div class='toast-body'>" + data + "</div></div>";
	        $("#msgStack").append(toast);   // msgStack div에 생성한 toast 추가
	        $(".toast").toast({"animation": true, "autohide": false});
	        $('.toast').toast('show');
	        
	    };
*/	    
    //URL에서 임시로 서버명 또는 도메인명 가져오깅
    //alert(location.href);
	let domainName = location.href.split("/")[2];
//     alert("확인:"+domainName);
    let webSocket = new WebSocket(`ws://\${domainName}/webSocket`);
	let webSocketChat = new WebSocket(`ws://\${domainName}/chat`);
    //webSocket.onopen = fOpen;
    //webSocket.onmessage = fMsg;
    
    webSocketChat.onopen = COpen;
    webSocketChat.onmessage = CMsg;

</script>
<style>
.sign_img{width:150px; height:150px;}
.sign_img img{width:100%;}
.s_tit{min-width:100px; text-align:left;}
.ks_list{width:100%; padding:0;}
.ks_list li{list-style:none; width:100%;}
.sinput_01{width:40%; float:left;}
.sinput_02{width:100%;}
.sinput_03{width:40%; float:left;}
.mr10{margin-right:10px;}
.pd0{padding-left:0 !important;}
.mb10{margin-bottom:10px;}
.pro_img{width:160px; height:200px;}
.pro_img img{width:100%; height:100% !important;}
</style>

 <!-- Navbar -->
<nav class="navbar navbar-expand-lg bg-primary">
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="realUser"/>
	</sec:authorize>
	<input type="hidden" id="empCd" value="${realUser.username}" />
	
	
<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="realUser2"/>
	</sec:authorize>
	<c:if test="${realUser2.username   ne 'admin'}">
			<span id="msgIcon"><i class="bx shadow-lg bx-border-circle bx-md bx-tada-hover bxs-message-rounded-dots btn-buy-now" style="color: #696cff;position: fixed;bottom: 3rem; right: 1.625rem;z-index: 1080; box-shadow: "></i></span>
	</c:if>
	
  <div class="container-fluid">
    <a class="navbar-brand" href="javascript:void(0)"></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbar-ex-7">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbar-ex-7">

      <ul class="navbar-nav ms-lg-auto">
      <c:if test="${realUser.username   ne 'admin'}">
      	<li class="nav-item">
      		<a class="nav-link" href="javascript:void(0);" onclick="openPopup();"><i class="bx bx-food-menu fs-4"></i> Org</a>
      	</li>
        <li class="nav-item">

          <a class="nav-link" href="/mypage" data-bs-toggle="modal" data-bs-target="#onboardImageModal"><i class="tf-icons navbar-icon bx bx-user"></i> Profile</a>
        </li> 
        <!-- 실시간 알림 구현예정 -->
<!--         <li class="nav-item"> -->
<!--           <a class="nav-link" href="javascript:void(0);"><i class="tf-icons bx bx-xs bx-bell"> -->
<!--           <span class="badge rounded-pill bg-danger text-white badge-notifications">5</span></i> Alarm</a> -->
<!--         </li> -->
        
		<!-- Notification -->
<!-- 		<li class="nav-item dropdown-notifications navbar-dropdown dropdown me-3 me-xl-1"> -->
<!--             <a class="nav-link dropdown-toggle hide-arrow" href="javascript:void(0);" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-expanded="false"> -->
<!--               <i class="bx bx-bell bx-sm"></i> -->
<!--               <span class="badge bg-danger rounded-pill badge-notifications">5</span> -->
<!--             </a> -->
<!--             <ul class="dropdown-menu dropdown-menu-end py-0"> -->
<!--               <li class="dropdown-menu-header border-bottom"> -->
<!--                 <div class="dropdown-header d-flex align-items-center py-3"> -->
<!--                   <h5 class="text-body mb-0 me-auto">Notification</h5> -->
<!--                   <a href="javascript:void(0)" class="dropdown-notifications-all text-body" data-bs-toggle="tooltip" data-bs-placement="top" aria-label="Mark all as read" data-bs-original-title="Mark all as read"><i class="bx fs-4 bx-envelope-open"></i></a> -->
<!--                 </div> -->
<!--               </li> -->
<!--               <li class="dropdown-notifications-list scrollable-container ps"> -->
<!--                 <ul class="list-group list-group-flush"> -->
<!--                   <li class="list-group-item list-group-item-action dropdown-notifications-item"> -->
<!--                     <div class="d-flex"> -->
<!--                       <div class="flex-shrink-0 me-3"> -->
<!--                         <div class="avatar"> -->
<!--                           <img src="../../assets/img/avatars/1.png" alt="" class="w-px-40 h-auto rounded-circle"> -->
<!--                         </div> -->
<!--                       </div> -->
<!--                       <div class="flex-grow-1"> -->
<!--                         <h6 class="mb-1">Congratulation Lettie 🎉</h6> -->
<!--                         <p class="mb-0">Won the monthly best seller gold badge</p> -->
<!--                         <small class="text-muted">1h ago</small> -->
<!--                       </div> -->
<!--                       <div class="flex-shrink-0 dropdown-notifications-actions"> -->
<!--                         <a href="javascript:void(0)" class="dropdown-notifications-read"><span class="badge badge-dot"></span></a> -->
<!--                         <a href="javascript:void(0)" class="dropdown-notifications-archive"><span class="bx bx-x"></span></a> -->
<!--                       </div> -->
<!--                     </div> -->
<!--                   </li> -->
<!--                 </ul> -->
<!--               <div class="ps__rail-x" style="left: 0px; bottom: 0px;"><div class="ps__thumb-x" tabindex="0" style="left: 0px; width: 0px;"></div></div><div class="ps__rail-y" style="top: 0px; right: 0px;"><div class="ps__thumb-y" tabindex="0" style="top: 0px; height: 0px;"></div></div></li> -->
<!--           </ul> -->
<!--         </li> -->
        </c:if>
        
        <li class="nav-item" >
          <a class="nav-link" id="logoutUI" href="javascript:;"><i class="tf-icons navbar-icon bx bx-lock-open-alt"></i> Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>



<form:form action="/logout" id="logoutForm" method="POST">
	<input type="hidden" value="로그아웃">
</form:form>
    
<script type="text/javascript">
	$("#logoutUI").on("click", function(event) {
		$(logoutForm).submit();
	});

	const compareToToday = (dateData) => {
		let today = new Date();
		let year = today.getFullYear();
		let month = String(today.getMonth() + 1).padStart(2, '0');
	    let day = String(today.getDate()).padStart(2, '0');
	    let formattedToday = `\${year}-\${month}-\${day}`;
	    
	    if(dateData==formattedToday){
	    	return true;
	    }else{
	    	return false;
	    }
	}
	
	function openPopup() {

		// 열려는 페이지의 URL
		var url = "/org/organization/pop";

		// 팝업 창 열기
		var popup = window.open(url, '조직도', 'width=700,height=850, left=500, top=220');


	}
</script>


<!-- -------------------------------------------------------------------------------------------------------------- -->



          
<link href="${pageContext.request.contextPath}/resources/css/mypage/mypage.css" rel="stylesheet" type="text/css">




	<!-- slider modal -->
	<div class="modal-onboarding modal fade animate__animated" id="onboardingSlideModal" tabindex="-1" style="display: none;" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content text-center">
				<div class="modal-header border-0">
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div id="modalCarouselControls" class="carousel slide pb-4 mb-2" data-bs-interval="false">
					<div class="carousel-indicators">
						<button type="button" data-bs-target="#modalCarouselControls" data-bs-slide-to="0" class="active"></button>
						<button type="button" data-bs-target="#modalCarouselControls" data-bs-slide-to="1"></button>
						<button type="button" data-bs-target="#modalCarouselControls" data-bs-slide-to="2"></button>
					</div>
					<div class="carousel-inner">
						<div class="carousel-item active">
							<div class="onboarding-media">
								<div class="mx-2">
									<img src="../../assets/img/illustrations/girl-with-laptop-light.png" alt="girl-with-laptop-light" width="335" class="img-fluid" data-app-dark-img="illustrations/girl-with-laptop-dark.png" data-app-light-img="illustrations/girl-with-laptop-light.png">
								</div>
							</div>
							<div class="onboarding-content">
								<h4 class="onboarding-title text-body">Example Request Information</h4>
								<!-- <div class="onboarding-info">In this example you can see a form where you can request some
                      additional information from the customer when they land on the app page.</div> -->
								<form>
									<div class="row">
										<div class="col-sm-6">
											<div class="mb-3">
												<label for="nameEx" class="form-label">Your Full Name</label> <input class="form-control" placeholder="Enter your full name..." type="text" value="" tabindex="0" id="nameEx">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="mb-3">
												<label for="roleEx" class="form-label">Your Role</label> <select class="form-select" tabindex="0" id="roleEx">
													<option>Web Developer</option>
													<option>Business Owner</option>
													<option>Other</option>
												</select>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="carousel-item">
							<div class="onboarding-media">
								<div class="mx-2">
									<img src="../../assets/img/illustrations/boy-with-laptop-light.png" alt="boy-with-laptop-light" width="300" class="img-fluid" data-app-dark-img="illustrations/boy-with-laptop-dark.png" data-app-light-img="illustrations/boy-with-laptop-light.png">
								</div>
							</div>
							<div class="onboarding-content">
								<h4 class="onboarding-title text-body">Example Request Information</h4>
								<div class="onboarding-info">In this example you can see a form where you can request some additional information from the customer when they land on the app page.</div>
								<form>
									<div class="row">
										<div class="col-sm-6">
											<div class="mb-3">
												<label for="nameEx1" class="form-label">Your Full Name</label> <input class="form-control" placeholder="Enter your full name..." type="text" value="" tabindex="0" id="nameEx1">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="mb-3">
												<label for="roleEx1" class="form-label">Your Role</label> <select class="form-select" tabindex="0" id="roleEx1">
													<option>Web Developer</option>
													<option>Business Owner</option>
													<option>Other</option>
												</select>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div class="carousel-item">
							<div class="onboarding-media">
								<div class="mx-2">
									<img src="../../assets/img/illustrations/girl-verify-password-light.png" alt="girl-verify-password-light" width="300" class="img-fluid" data-app-dark-img="illustrations/girl-verify-password-dark.png" data-app-light-img="illustrations/girl-verify-password-light.png">
								</div>
							</div>
							<div class="onboarding-content">
								<h4 class="onboarding-title text-body">Example Request Information</h4>
								<div class="onboarding-info">In this example you can see a form where you can request some additional information from the customer when they land on the app page.</div>
								<form>
									<div class="row">
										<div class="col-sm-6">
											<div class="mb-3">
												<label for="nameEx2" class="form-label">Your Full Name</label> <input class="form-control" placeholder="Enter your full name..." type="text" value="" tabindex="0" id="nameEx2">
											</div>
										</div>
										<div class="col-sm-6">
											<div class="mb-3">
												<label for="roleEx2" class="form-label">Your Role</label> <select class="form-select" tabindex="0" id="roleEx2">
													<option>Web Developer</option>
													<option>Business Owner</option>
													<option>Other</option>
												</select>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
					<a class="carousel-control-prev" href="#modalCarouselControls" role="button" data-bs-slide="prev"> <i class="bx bx-chevrons-left lh-1"></i><span>Previous</span>
					</a> <a class="carousel-control-next" href="#modalCarouselControls" role="button" data-bs-slide="next"> <span>Next</span><i class="bx bx-chevrons-right lh-1"></i>
					</a>
				</div>
			</div>
		</div>
	</div>
	<!--/ slider modal -->

	<!-- Form with Image Modal -->
	<div class="modal-onboarding modal fade animate__animated" id="onboardImageModal" tabindex="-1" style="display: none;" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content text-center">
				<div class="modal-header border-0">
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" id="closeMypage"></button>
				</div>
				<div class="modal-body p-0" id="resetMypage">
					<!-- 여기 -->

					<div class="rounded-top" _mstvisible="4">
						<div class="row mx-0 gy-3" _mstvisible="5">
							<div id="test"></div>
							<div class="row">
								<div class="col-12">
									<div class="card mb-4" id="leftBbody">
										<div class="user-profile-header-banner">
											<img src="${pageContext.request.contextPath}/resources/images/profile-banner.png" alt="배너 이미지" class="rounded-top resized-image" _mstalt="175734" _msthash="272">
										</div>
										<div class="user-profile-header d-flex flex-column flex-sm-row text-sm-start text-center mb-4">
											<div class="flex-shrink-0 mt-n2 mx-sm-0 mx-auto pro_img" id="profileArea">
												<img id="profileMypage" src="../../assets/img/avatars/1.png" alt="사용자 이미지" class="d-block h-auto ms-0 ms-sm-4 rounded user-profile-img" _mstalt="136097" _msthash="273">
											</div>
											<div class="flex-grow-1 mt-3 mt-sm-5">
												<div class="d-flex align-items-md-end align-items-sm-start align-items-center justify-content-md-between justify-content-start mx-4 flex-md-row flex-column gap-4">
													<div class="user-profile-info">
														<h4 _msttexthash="9584913" _msthash="274" id="nameMypage">존 도(John Doe)</h4>
														<ul class="list-inline mb-0 d-flex align-items-center flex-wrap justify-content-sm-start justify-content-center gap-2">
															<li class="list-inline-item fw-medium"><i class="bx bx-pen"></i><font _mstmutation="1" _msttexthash="26453479" _msthash="275" id="empCdMypage"> 사번</font></li>
															<li class="list-inline-item fw-medium"><i class="bx bx-calendar-alt"></i><font _mstmutation="1" _msttexthash="33095153" _msthash="277" id="hiredateMypage"> 입사일 </font></li>
														</ul>
													</div>
													<div id="qrcode"></div>
												</div>
												<br />
												<div id="profileUp"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xl-4 col-lg-5 col-md-5" id="bodyArea">
									<!-- About User -->
									<div class="card mb-4 bodySize" id="bodySize"">
										<div class="card-body">
											<div id="rightMypageArea">
												<small class="text-muted text-uppercase" _msttexthash="15624336" _msthash="294">연락처</small>
												<ul class="list-unstyled mb-4 mt-3">
													<li class="d-flex align-items-center mb-3" id="extensionPhoneMypage"><i class="bx bx-phone"></i><span class="fw-medium mx-2 s_tit" _msttexthash="10126389" _msthash="295">내선전화:</span> <span _msttexthash="111761" _msthash="296" id="extensionMypage">(123) 456-7890</span></li>
													<li class="d-flex align-items-center mb-3" id="cellPhoneMypage"><i class="bx bx-chat"></i><span class="fw-medium mx-2 s_tit" _msttexthash="23102430" _msthash="297">휴대폰:</span> <span _msttexthash="9034857" _msthash="298" id="empTelnoMypage">john.doe 님</span></li>
													<li class="d-flex align-items-center mb-3" id="myMailMypage"><i class="bx bx-envelope"></i><span class="fw-medium mx-2 s_tit" _msttexthash="15589756" _msthash="299">이메일:</span> <span _msttexthash="421655" _msthash="300" id="emailMypage">john.doe@example.com</span></li>
												</ul>
												<div class="ms-auto" id="buttonMypage">
													<button class="btn btn-primary btn-icon btn-sm updateMypage" id="updateClick" onclick="updateClick()">
														<i class="bx bx-user"></i><span>수정</span>
													</button>
												</div>
											</div>
											<div id="leftMypageArea">
												<small class="text-muted text-uppercase" _msttexthash="4600687" _msthash="283">개인정보</small>
												<ul class="list-unstyled mb-4 mt-3">
													<li class="d-flex align-items-center mb-3"><i class="bx bx-user"></i><span class="fw-medium mx-2" _msttexthash="9473269" _msthash="284">성명:</span> <span _msttexthash="9584913" _msthash="285" id="nameMypage1">존 도(John Doe)</span></li>
													<li class="d-flex align-items-center mb-3" id="Pw"><i class="bx bx-check"></i><span class="fw-medium mx-2" _msttexthash="10057853" _msthash="286">비밀번호:</span> <span _msttexthash="22410817" _msthash="287" id="pwMypage">****</span></li>
													<li class="d-flex align-items-center mb-3 rePw" id="rePw"></li>
													<li class="d-flex align-items-center mb-3"><i class="bx bx-star"></i><span class="fw-medium mx-2" _msttexthash="10298561" _msthash="288">부서:</span> <span _msttexthash="14994980" _msthash="289" id="deptnameMypage">개발자</span></li>
													<li class="d-flex align-items-center mb-3"><i class="bx bx-flag"></i><span class="fw-medium mx-2" _msttexthash="9005802" _msthash="290">직책:</span> <span _msttexthash="8996208" _msthash="291" id="positionMypage">미국</span></li>
													<li class="d-flex align-items-center mb-3"><i class="bx bx-detail"></i><span class="fw-medium mx-2" _msttexthash="15295280" _msthash="292">직급:</span> <span _msttexthash="9876347" _msthash="293" id="ranknameMypage">영어</span></li>
																				</ul>
											</div>
											
											<ul class="ks_list">
												<li class="d-flex align-items-center mb-3" id="myZipMypage"><i class="bx bx-map"></i><span class="fw-medium mx-2 s_tit" _msttexthash="15295280" _msthash="292">우편번호:</span> <font _mstmutation="1" _msttexthash="29015311" _msthash="276" id="zipMypage"> 우편번호</font></li>
												<li class="d-flex align-items-center mb-3" id="myAdressMypage"><i class="bx bx-map"></i><span class="fw-medium mx-2 s_tit" _msttexthash="15295280" _msthash="292">주소:</span> <font _mstmutation="1" _msttexthash="29015311" _msthash="276" id="adresMypage"> 기본주소</font> <font _mstmutation="1" _msttexthash="29015311" _msthash="276" id="adresdetailMypage"> 상세주소</font></li>
												</ul>
											<div class="card mb-4  fixed_img" id="signArea">
												<div class="card-body">
													<small class="text-muted text-uppercase" _msttexthash="9296404" _msthash="306">서명</small>
														<div class="sign_img fixed_img">
															<img id="signMypage" src="../../assets/img/avatars/1.png" alt="사용자 이미지" class="d-block h-auto ms-0 ms-sm-4 rounded user-profile-img" _mstalt="136097" _msthash="273">
														</div>
												</div>
											</div>
										</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>



<script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/app/mypage/mypage.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>


