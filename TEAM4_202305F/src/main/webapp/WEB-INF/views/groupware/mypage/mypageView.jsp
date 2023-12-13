<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* Nov 27, 2023      오경석      최초작성
* Nov 27, 2023      송석원     qr코드 생성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<style>
	.ks_wrap{    width: 780px;
    transform: translateY(-100px) scale(0.8);
    margin: 0 auto;
}}
</style>
<link href="${pageContext.request.contextPath}/resources/css/mypage/mypage.css" rel="stylesheet" type="text/css">


<security:authorize access="isAuthenticated()">
	<security:authentication property="principal" var="realUser" />
</security:authorize>




<div class="col-lg-4 col-md-6">
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
		<div class="ks_wrap" role="document">
			<div class="modal-content text-center">
				<div class="modal-header border-0">
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body p-0">
					<!-- 여기 -->

					<div class="rounded-top topImg" _mstvisible="4">
						<h2 class="text-center mb-2 mt-0 mt-md-4 px-2" _msttexthash="87591023" _msthash="306" _mstvisible="5">사이트에 적합한 플랜 찾기</h2>
						<p class="text-center pb-3 px-2" _msttexthash="1022980881" _msthash="307" _mstvisible="5">우리와 함께 시작하십시오 - 개인과 팀에 적합합니다. 다음과 같은 구독 요금제를 선택합니다.당신의 필요를 충족시킵니다.</p>


						<div class="row mx-0 gy-3" _mstvisible="5">
							<div id="test"></div>
							<div class="row">
								<div class="col-12">
									<div class="card mb-4" id="leftBbody">
										<div class="user-profile-header-banner">
											<img src="${pageContext.request.contextPath}/resources/images/profile-banner.png" alt="배너 이미지" class="rounded-top resized-image" _mstalt="175734" _msthash="272">
										</div>
										<div class="user-profile-header d-flex flex-column flex-sm-row text-sm-start text-center mb-4">
											<div class="flex-shrink-0 mt-n2 mx-sm-0 mx-auto" id="profileArea">
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
									<div class="card mb-4 bodySize" id="bodySize">
										<div class="card-body">
											<div id="rightMypageArea">
												<small class="text-muted text-uppercase" _msttexthash="15624336" _msthash="294">연락처</small>
												<ul class="list-unstyled mb-4 mt-3">
													<li class="d-flex align-items-center mb-3" id="extensionPhoneMypage"><i class="bx bx-phone"></i><span class="fw-medium mx-2" _msttexthash="10126389" _msthash="295">내선전화:</span> <span _msttexthash="111761" _msthash="296" id="extensionMypage">(123) 456-7890</span></li>
													<li class="d-flex align-items-center mb-3" id="cellPhoneMypage"><i class="bx bx-chat"></i><span class="fw-medium mx-2" _msttexthash="23102430" _msthash="297">휴대폰:</span> <span _msttexthash="9034857" _msthash="298" id="empTelnoMypage">john.doe 님</span></li>
													<li class="d-flex align-items-center mb-3" id="myMailMypage"><i class="bx bx-envelope"></i><span class="fw-medium mx-2" _msttexthash="15589756" _msthash="299">이메일:</span> <span _msttexthash="421655" _msthash="300" id="emailMypage">john.doe@example.com</span></li>
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
													<li class="list-inline-item fw-medium" id="myZipMypage"><i class="bx bx-map"></i><span class="fw-medium mx-2" _msttexthash="15295280" _msthash="292">우편번호:</span> <font _mstmutation="1" _msttexthash="29015311" _msthash="276" id="zipMypage"> 우편번호</font></li>
													<li class="d-flex align-items-center mb-3" id="myAdressMypage"><i class="bx bx-map"></i><span class="fw-medium mx-2" _msttexthash="15295280" _msthash="292">주소:</span> <span _msttexthash="9876347" _msthash="293" id="ranknameMypage">영어</span> <font _mstmutation="1" _msttexthash="29015311" _msthash="276" id="adresMypage"> 기본주소</font> <font _mstmutation="1" _msttexthash="29015311" _msthash="276" id="adresdetailMypage"> 상세주소</font></li>
												</ul>
											</div>
											<div class="card mb-4" id="signArea">
												<div class="card-body">
													<small class="text-muted text-uppercase" _msttexthash="9296404" _msthash="306">서명</small>
													<ul class="list-unstyled mt-3 mb-0">
														<img id="signMypage" src="../../assets/img/avatars/1.png" alt="사용자 이미지" class="d-block h-auto ms-0 ms-sm-4 rounded user-profile-img" _mstalt="136097" _msthash="273">
														<span>서명이미지</span>
													</ul>
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
	<!--/ Form with Image Modal -->
	<div class="demo-inline-spacing">


		<!-- Form with Image Modal -->
		<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#onboardImageModal">Modal with form</button>
		<!--/ Form with Image Modal -->
	</div>
</div>


<script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/app/mypage/mypage.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/app/admin/account/account.js"></script>