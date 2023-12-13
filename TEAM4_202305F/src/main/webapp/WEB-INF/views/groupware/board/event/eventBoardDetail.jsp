<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2023. 11. 20.      김보영        최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<security:authorize access="isAuthenticated()">
    <security:authentication property="principal" var="realUser" />
</security:authorize>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=057358babbeb10048427ddd4e876ed8a&libraries=services"></script>

<style>
	.custom-radio {
  display: flex;
  justify-content: center; /* Center content horizontally */
  align-items: center; /* Center content vertically */
}

.custom-radio input[type="radio"] {
  display: none;
}

.custom-radio label {
  cursor: pointer;
  margin-right: 15px;
  text-align: center;
}

.custom-radio label img {
  width: 65x; /* Adjust the width as needed */
  height: 65px; /* Adjust the height as needed */
  border-radius: 50%; /* Make it round */
  border: 2px solid transparent; /* Add a border to make it look like a button */
  transition: border-color 0.3s ease, transform 0.3s ease;
}

.custom-radio label span {
  display: block;
  margin-top: 5px; /* Adjust the margin as needed */
}

.custom-radio input[type="radio"]:checked + label img {
  border-color: #696cff; /* Change border color when selected */
  transform: scale(1.1);
}

</style>




<div class="content-wrapper">
	<!-- Content -->

	<div class="container-xxl flex-grow-1 container-p-y">
		<h4 class="py-3 mb-4"><span class="text-muted fw-light">게시판 / </span> 경조사게시판</h4>
		<div class="row mt-4">
		<!-- 상세내용 -->
		
		<div class="row">
	          <!-- 게시글 상세-->
	          <div class="col-xl-6 col-12">
	            <div class="card mb-4">
	              <h5 class="card-header">제목 : ${detail.bbsSj}</h5>
	               <hr class="my-0 mb-3">
	              <div class="card-body">
	              	<input type="hidden" value="${detail.bbsEquator }" name="bbsEquator" id="bbsEquator">	               
	              	<input type="hidden" value="${detail.bbsLatitude }" name="bbsLatitude" id="bbsLatitude">	               
	              	
	              		<ul class="timeline ms-2">
                  	<c:set var="color" value=""/>
                  	<c:if test="${i.index % 5 eq 0 }">
                  		<c:set var="color" value="info"/>
                  	</c:if>
                  	<c:if test="${i.index % 5 eq 1 }">
                  		<c:set var="color" value="primary"/>
                  	</c:if>
                  	<c:if test="${i.index % 5 eq 2 }">
                  		<c:set var="color" value="danger"/>
                  	</c:if>
                  	<c:if test="${i.index % 5 eq 3 }">
                  		<c:set var="color" value="warning"/>
                  	</c:if>
                  	<c:if test="${i.index % 5 eq 4 }">
                  		<c:set var="color" value="success"/>
                  	</c:if>
                  <li class="timeline-item timeline-item-transparent">
                    <span class="timeline-point-wrapper"><span
                        class="timeline-point timeline-point-info"></span></span>
                    <div class="timeline-event">
                      <div class="timeline-header mb-1">
                        <h6 class="mt-1">내용</h6>
                        <small class="text-muted mb-3">등록일 ${detail.eventRdate}</small>
                      </div>
                      <p class="mb-2 mt-2">
                      	${detail.bbsCn}
                      </p>
                    </div>
                  </li>
                  <li class="timeline-item timeline-item-transparent">
                    <span class="timeline-point-wrapper"><span
                        class="timeline-point timeline-point-primary"></span></span>
                    <div class="timeline-event">
                      <div class="timeline-header mb-1">
                        <h6 class="mt-1">위치 </h6>
                        <small class="text-muted"></small>
                      </div>
                      <p class="mb-2 mt-2">
                      	${detail.eventLoc}
                      </p>
                    </div>
                  </li>
                  <li class="timeline-item timeline-item-transparent">
                    <span class="timeline-point-wrapper"><span
                        class="timeline-point timeline-point-success"></span></span>
                    <div class="timeline-event">
                      <div class="timeline-header mb-1">
                        <h6 class="mt-1">일시 </h6>
                        <small class="text-muted"></small>
                      </div>
                      <c:if test="${detail.eventBdate ne detail.eventEdate}">
                      <p class="mb-2 mt-2">
                      	시작일 : ${detail.eventBdate} ~ 종료일 : ${detail.eventEdate}
                      </p>
                      </c:if>
                      <c:if test="${detail.eventBdate eq detail.eventEdate}">
                      <p class="mb-2 mt-2">
                      	${detail.eventBdate}
                      </p>
                      </c:if>
                    </div>
                  </li>
                  
                  
                  <li class="timeline-end-indicator">
                    <i class="bx bx-check-circle"></i>
                  </li>
                </ul>
	              </div>
	            </div>
	            <button class="btn btn-warning" onclick="fn_eventHome()">
					<i class="bx bx-cube faq-nav-icon me-1"></i>
					<span class="align-middle">목록</span>
				</button>
				<c:if test="${realUser.username eq 'admin' }">
	            <button class="btn btn-primary" onclick="fn_eventEdit(${detail.bbsNo})">
					<i class="bx bx-cube faq-nav-icon me-1"></i>
					<span class="align-middle">수정</span>
				</button>
	            <button class="btn btn-danger" onclick="fn_deleteEvent(${detail.bbsNo})">
					<i class="bx bx-cube faq-nav-icon me-1"></i>
					<span class="align-middle">삭제</span>
				</button>
				</c:if>
	          </div>
	          <!-- /게시글 상세-->
	          <!-- 지도와 이모지-->
	          <div class="col-xl-6 col-12">
	            <c:if test="${not empty detail.bbsEquator}">
	            <div class="card mb-4" id="card-block">
		              <h5 class="card-header">위치</h5>
		              <div class="card-body" id="map" style="width:100%; height:400px;">
		              </div>
	            </div>
	            </c:if>
	            <div class="card mb-4" id="card-block">
	            	<c:if test="${detail.eventSttus eq '2' and empty detail.bbsEquator}">
						 <div class="card-body" >
							<img alt="" src="/resources/images/event2.png" style="width: 100%;">
						</div>
					</c:if>
	            
	              <c:if test="${detail.eventSttus eq '1' or detail.eventSttus eq '3' or detail.eventSttus eq '4' }">
	              <div class="card-body">
	              	<form id="formEventEmoji">
		              	<input type="hidden" id="empCd" value="${realUser.username }"  name="empCd"/>
		              	<input type="hidden" id="bbsNo" value="${detail.bbsNo}" name="bbsNo"/>
		              	<input type="hidden" value="${detail.eventSttus }" name="eventSttus" id="eventSttus">	               
	              	</form>
	              	
	              	<div class="custom-radio">
					  <input type="radio" id="radio1" name="radioGroup" value="1" onclick="fn_updateEmoji(1)" <c:if test="${detail.selectEmo eq '1'}">checked</c:if>/>
					  <label for="radio1">
					    <img src="/resources/images/emoji/a.png" alt="Option 1" />
					    <span id="angryCnt">${cnt.angryCnt}</span>
					  </label>
					
					  <input type="radio" id="radio2" name="radioGroup"  value="2" onclick="fn_updateEmoji(2)" <c:if test="${detail.selectEmo eq '2'}">checked</c:if>/>
					  <label for="radio2">
					    <img src="/resources/images/emoji/b.png" alt="Option 2" />
					    <span id="sadCnt">${cnt.sadCnt}</span>
					  </label>
					  
					  <input type="radio" id="radio3" name="radioGroup"  value="3" onclick="fn_updateEmoji(3)" <c:if test="${detail.selectEmo eq '3'}">checked</c:if>/>
					  <label for="radio3">
					    <img src="/resources/images/emoji/c.png" alt="Option 3" />
					    <span id="smileCnt">${cnt.smileCnt}</span>
					  </label>
					  
					  <input type="radio" id="radio4" name="radioGroup"  value="4" onclick="fn_updateEmoji(4)" <c:if test="${detail.selectEmo eq '4'}">checked</c:if>/>
					  <label for="radio4">
					    <img src="/resources/images/emoji/d.png" alt="Option 4" />
					    <span id="impressCnt">${cnt.impressCnt}</span>
					  </label>
					  
					  <input type="radio" id="radio5" name="radioGroup" value="5" onclick="fn_updateEmoji(5)" <c:if test="${detail.selectEmo eq '5'}">checked</c:if>/>
					  <label for="radio5">
					    <img src="/resources/images/emoji/e.png" alt="Option 5" />
					    <span id="thumbsCnt">${cnt.thumbsCnt}</span>
					  </label>
					</div>
	              </div>
				</c:if>
				
	            </div>
	          </div>
	          <!-- 지도와 이모지-->
	        </div>
		</div>
		<!-- /Contact -->
	</div>
	<!-- / Content -->
</div>




<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/board/event.js"></script>

<script>

	const bbsEquator = document.getElementById('bbsEquator').value;
	const bbsLatitude = document.getElementById('bbsLatitude').value;
	
	//카카오 지도 api
	var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
	  center: new kakao.maps.LatLng(bbsEquator, bbsLatitude), //지도의 중심좌표.
	  level: 3 //지도의 레벨(확대, 축소 정도)
	};
	
	//지도를 미리 생성
	var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
	
	//마커를 미리 생성
	var marker = new daum.maps.Marker({
	  	position: new daum.maps.LatLng(bbsEquator, bbsLatitude),
	 	map: map
	});

</script>
