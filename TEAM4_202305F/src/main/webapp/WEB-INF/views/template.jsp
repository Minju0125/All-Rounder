<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<meta charset="UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

<title>올라운더</title>
<sec:csrfMetaTags />

<meta name="description" content="" />
<meta name="keywords"
	content="dashboard, bootstrap 5 dashboard, bootstrap 5 design, bootstrap 5">

<tiles:insertAttribute name="preScript" />
<tiles:insertAttribute name="cssScript" />

<style>
.msgModal {
	opacity: 0;
	position: fixed;
	top: 0px;
	left: 0;
	width: 100%;
	height: 100%;
	max-width: 100%;
	max-height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	transition: opacity 0.5s;
	z-index: 1000;
}

.unclicked-chatroom{
	background-color : #696cff12;
}

.clicked-chatroom{
	background-color : none;
}

.msgModal.opaque {
	opacity: 1;
	transition: opacity 0.5s;
}

.msgModal.unstaged {
	top: -100px;
	height: 0;
}

.modal-overlay {
	position: absolute;
	width: 100%;
	height: 100%;
	background-color: RGBA(0, 0, 0, 0.6);
}

.modal-contents {
	display: flex;
	flex-direction: column;
	align-items: center;
	position: relative;
	padding: 10px 10px;
	width: 53%;
	height: 65%;
	max-width: 80%;
	max-height: 80%;
	text-align: center;
	background-color: rgb(255, 255, 255);
	border-radius: 20px;
}
</style>

<script src="/resources/js/app/messenger/chat_new.js"></script>
</head>

<body data-context-path="${pageContext.request.contextPath }">

	<!-- Layout wrapper -->
	<div class="layout-wrapper layout-content-navbar  ">
		<div class="layout-container">

			<!-- leftMenu -->
			<tiles:insertAttribute name="leftMenu" />
			<!-- /leftMenu -->

			<!-- Layout container -->
			<div class="layout-page">

				<tiles:insertAttribute name="headerMenu" />



				<!-- Content wrapper -->
				<div class="content-wrapper">

					<div class="container-xxl flex-grow-1 container-p-y">
						<!-- Main Content Area start -->
						<tiles:insertAttribute name="content" />
						<!-- Main Content Area end -->
					</div>
				</div>
				<tiles:insertAttribute name="footer" />

				<div class="content-backdrop fade"></div>
			</div>
			<!-- Content wrapper -->
		</div>
		<!-- / Layout page -->
	</div>

	<!-- Overlay -->
	<div class="layout-overlay layout-menu-toggle"></div>

	<!-- / Layout wrapper -->
	
	<!-- admin 인 경우 메신저 띄우지 않음 -->
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="realUser2"/>
	</sec:authorize>
	<c:if test="${realUser2.username   ne 'admin'}">
<!-- 		<div class="buy-now" id="msgIcon"> -->
<!-- 			<a class="btn btn-danger btn-buy-now" style="width: 10px; height: 42px; border-radius: 100%;"><i -->
<!-- 				class='btn-danger bx bxl-messenger'></i></a> -->
<!-- 		</div>  -->
		<span id="msgIcon" style="cursor:pointer;" onclick="msgToggle()"><i class="bx shadow-lg bx-border-circle bx-md bx-tada-hover bxs-message-rounded-dots btn-buy-now" style="color: #696cff;position: fixed;bottom: 3rem; right: 1.625rem;z-index: 1080; box-shadow: "></i></span>
	
	<!-- 메신저 모달 -->
	<div id="modal-container" class="msgModal unstaged">
		<div class="modal-overlay"></div>
		<div class="modal-contents">
			<div class="app-chat overflow-hidden card">
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal" var="realUser" />
				</sec:authorize>
				<div class="row g-0">
					<input type="hidden" id="empCd" value="${realUser.username}" />
					<!-- Chat & Contacts -->
					<div
						class="col app-chat-contacts app-sidebar flex-grow-0 overflow-hidden border-end"
						id="app-chat-contacts">
						<div class="sidebar-header pt-3 px-3 mx-1">
							<div class="d-flex align-items-center me-3 me-lg-0">
								<div class="flex-shrink-0 avatar avatar-online me-2"
									data-bs-toggle="sidebar" data-overlay="app-overlay-ex"
									data-target="#app-chat-sidebar-left">
									<img class="user-avatar rounded-circle cursor-pointer"
										src="/resources/assets/img/avatars/1.png" alt="Avatar" id="myProfile"/>
								</div>
								<div
									class="flex-grow-1 input-group input-group-merge rounded-pill ms-1">
									<span class="input-group-text" id="basic-addon-search31"><i
										class="bx bx-search fs-4"></i></span> <input type="text"
										class="form-control chat-search-input" placeholder="Search..."
										aria-label="Search..." aria-describedby="basic-addon-search31" />
								</div>
							</div>
							<i
								class="bx bx-x cursor-pointer position-absolute top-0 end-0 mt-2 me-1 fs-4 d-lg-none d-block"
								data-overlay data-bs-toggle="sidebar"
								data-target="#app-chat-contacts"></i>
						</div>
						<hr class="container-m-nx mt-3 mb-0" />
						<div class="sidebar-body">
						
							<div class="nav-align-top mb-4">
                    <ul class="nav nav-tabs" role="tablist">
                      <li class="nav-item" role="presentation" id="chatBtn">
                        <button onclick="setChatType('G')" type="button" class="nav-link active" role="tab" data-bs-toggle="tab" data-bs-target="#navs-top-home" aria-controls="navs-top-home" aria-selected="true" >
                          일반
                        </button>
                      </li>
                      <li class="nav-item" role="presentation"  id="projectChatBtn">
                        <button onclick="setChatType('P')" type="button" class="nav-link" role="tab" data-bs-toggle="tab" data-bs-target="#navs-top-profile" aria-controls="navs-top-profile" aria-selected="false" tabindex="-1">
                          프로젝트
                        </button>
                      </li>
                      <li class="nav-item" role="presentation">
                      	<span id="addEmpBtn">
                          <i class="bx bx-plus bx-sm mt-2" style="margin-left:10px; cursor:pointer"></i>
                        </span>
                      </li>
                    </ul>
                    <div class="tab-content" style="box-shadow:none">
                      <div class="tab-pane fade active show" id="navs-top-home" role="tabpanel">
                      	<ul class="list-unstyled chat-contact-list pt-1" id="chat-list">
					  	</ul>
                      </div>
                      <div class="tab-pane fade" id="navs-top-profile" role="tabpanel">
                      	<ul class="list-unstyled chat-contact-list pt-1" id="project-chat-list">
					  	</ul>
                      </div>
                    </div>
                  </div>
						</div>
					</div>
					<!-- /Chat contacts -->

					<!-- Chat History -->
					<div class="col app-chat-history">
						<div class="chat-history-wrapper">

							<!-- 이 아래에 append ~ -->
							<div class="chat-history-header border-bottom">
								<div class="d-flex justify-content-between align-items-center">
									<div class="d-flex overflow-hidden align-items-center">
										<i
											class="bx bx-menu bx-sm cursor-pointer d-lg-none d-block me-2"
											data-bs-toggle="sidebar" data-overlay
											data-target="#app-chat-contacts"></i>
										<div class="flex-shrink-0 avatar" id="chatRoomImg" >
										</div>
										<div class="chat-contact-info flex-grow-1 ms-3">
<!-- 											<input type="text" id="chatRoomTitle1" readonly="readonly"/> -->
											<h6 class="m-0" id="chatRoomTitle1"></h6>
											<small class="user-status text-muted" id="chatRoomTitle2"></small>
										</div>
									</div>
									<div class="d-flex align-items-center">
										<div class="dropdown">
											<i class='bx bx-sync bx-spin' id="syncBtn" style="cursor:pointer;"></i>
											<button class="btn p-0" type="button"
												id="chat-header-actions" data-bs-toggle="dropdown"
												aria-haspopup="true" aria-expanded="false">
												<i class="bx bx-dots-vertical-rounded fs-4"></i>
											</button>
											<div class="dropdown-menu dropdown-menu-end"
												aria-labelledby="chat-header-actions">
												<a class="dropdown-item" href="javascript:void(0);" id="modifyTitleBtn">채팅방 이름변경</a>
												<a class="dropdown-item" href="javascript:void(0);" id="delBtn">삭제</a>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 여기까지! 위에 상대방 정보 ~ -->

							<!-- 여기부터 내역~ -->
							<div class="chat-history-body">
								<ul class="list-unstyled chat-history mb-0" id="historyUl">
								</ul>
							</div>

							<!-- 여기부터 내역~ -->


							<!-- Chat message form -->
							<div class="chat-history-footer">
								<form
									class="form-send-message d-flex justify-content-between align-items-center">
									<input
										class="form-control message-input border-0 me-3 shadow-none"
										id="inputText" placeholder="Type your message here..." />
									<div class="message-actions d-flex align-items-center">
										<label for="attach-doc" class="form-label mb-0"> <i
											class="bx bx-paperclip bx-sm cursor-pointer mx-3 text-body"></i>
											<input type="file" id="attach-doc" hidden />
										</label>
										<button class="btn btn-primary d-flex send-msg-btn"
											id="msgSendBtn">
											<i class="bx bx-paper-plane me-md-1 me-0"></i>
											<span class="align-middle d-md-inline-block d-none">Send</span>
										</button>
									</div>
								</form>
							</div>
						</div>
					</div>
					<!-- /Chat History -->

					<!-- Sidebar Right -->
					<div class="col app-chat-sidebar-right app-sidebar overflow-hidden"
						id="app-chat-sidebar-right">
						<div
							class="sidebar-header d-flex flex-column justify-content-center align-items-center flex-wrap p-4 mt-2">
							<div class="avatar avatar-xl avatar-online">
								<img src="/resources/assets/img/avatars/2.png" alt="Avatar"
									class="rounded-circle" />
							</div>
							<h6 class="mt-3 mb-1">Felecia Rower</h6>
							<small class="text-muted">NextJS Developer</small> <i
								class="bx bx-x bx-sm cursor-pointer close-sidebar me-1 fs-4 d-block"
								data-bs-toggle="sidebar" data-overlay
								data-target="#app-chat-sidebar-right"></i>
						</div>
						<div class="sidebar-body px-4 pb-4">
							<div class="my-3">
								<span class="text-muted text-uppercase">About</span>
								<p class="mb-0 mt-2">A Next. js developer is a software
									developer who uses the Next. js framework alongside ReactJS to
									build web applications.</p>
							</div>
							<div class="my-4">
								<span class="text-muted text-uppercase">Personal
									Information</span>
								<ul class="list-unstyled d-grid gap-2 mt-2">
									<li class="d-flex align-items-center"><i
										class="bx bx-envelope"></i> <span class="align-middle ms-2">josephGreen@email.com</span>
									</li>
									<li class="d-flex align-items-center"><i
										class="bx bx-phone-call"></i> <span class="align-middle ms-2">+1(123)
											456 - 7890</span></li>
									<li class="d-flex align-items-center"><i
										class="bx bx-time-five"></i> <span class="align-middle ms-2">Mon
											- Fri 10AM - 8PM</span></li>
								</ul>
							</div>
							<div class="mt-4">
								<span class="text-muted text-uppercase">Options</span>
								<ul class="list-unstyled d-grid gap-2 mt-2">
									<li class="cursor-pointer d-flex align-items-center"><i
										class="bx bx-bookmark"></i> <span class="align-middle ms-2">Add
											Tag</span></li>
									<li class="cursor-pointer d-flex align-items-center"><i
										class="bx bx-star"></i> <span class="align-middle ms-2">Important
											Contact</span></li>
									<li class="cursor-pointer d-flex align-items-center"><i
										class="bx bx-image-alt"></i> <span class="align-middle ms-2">Shared
											Media</span></li>
									<li class="cursor-pointer d-flex align-items-center"><i
										class="bx bx-trash-alt"></i> <span class="align-middle ms-2">Delete
											Contact</span></li>
									<li class="cursor-pointer d-flex align-items-center"><i
										class="bx bx-block"></i> <span class="align-middle ms-2">Block
											Contact</span></li>
								</ul>
							</div>
						</div>
					</div>
					<!-- /Sidebar Right -->

					<div class="app-overlay"></div>
				</div>
			</div>
		</div>
	</div>
	</c:if>
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="realUser"/>
</sec:authorize>
<input type="hidden" id="empCd" value="${realUser.username}" />
<!-- 신규 채팅 직원 검색 모달 -->
<div class="modal fade" id="smallModal" tabindex="-1" style="display: none;" aria-hidden="true">
   <div class="modal-dialog modal-sm" role="document">
     <div class="modal-content">
       <div class="modal-header">
         <h5 class="modal-title" id="exampleModalLabel2">직원 검색</h5>
       </div>
       <div class="modal-body">
         <div class="row">
           <div class="col mb-3">
             <label for="nameSmall" class="form-label">검색/추가</label>
                 <div class="select2-primary border-0 shadow-none flex-grow-1 mx-2">
                   <select class="select2 select-email-contacts form-select" id="receiversCd"
                     	   name="mailReceivers" multiple>
                   </select>
                 </div>
            </div>
       </div>
       <div class="modal-footer">
         <button type="button" class="btn btn-primary" id="makeChatBtn">확인</button>
         <button id="addCloseBtn" type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">
           Close
         </button>
       </div>
     </div>
   </div>
 </div>
 </div>
 
 <%-- toast로 실시간알림 받아올곳!! --%>
<div id="msgStack"></div>
</body>
<script>
	//open modal
// 	$("#msgIcon").on("click", function(){
// 		alert("제이쿼리");
// 	});
// 	document.getElementById("msgIcon").addEventListener("click",function(e) {
// 				alert("dfdf");
// 				$("#msgIcon").find("i").removeClass("bx-tada");
// 				document.getElementById('modal-container').classList
// 						.toggle('opaque');
// 				document.getElementById('modal-container').classList
// 						.toggle('unstaged');
// 			});
	function msgToggle(){
		$("#msgIcon").find("i").removeClass("bx-tada");
		document.getElementById('modal-container').classList
				.toggle('opaque');
		document.getElementById('modal-container').classList
				.toggle('unstaged');
	}

</script>

<tiles:insertAttribute name="postScript" />

</html>

<!-- beautify ignore:end -->


