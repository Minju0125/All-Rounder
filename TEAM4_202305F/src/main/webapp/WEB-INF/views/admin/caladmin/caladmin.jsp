<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 30.      오경석      최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js'></script>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/calendar/calendar.css">
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>


<security:authentication property="principal" var="principal" />

<style>
#yrModal, #detailModal { 
    position: fixed;
    top: 50%;
    left: 61%;
    transform: translate(-50%, -50%);
    width: 60%;
    height: 67.7%;
    background-color: rgba(0,0,0,0);
    display: none;
    z-index: 1000;
} 
</style>

<!-- 실제 화면을 담을 영역 -->
<div id="Wrapper">
	<div id='calendar'></div>
	<button onclick="insertOpenModal()" id="openCal" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#enableOTP" _msttexthash="15070224" _msthash="286">등록</button>
</div>


<!-- 모달 영역 밖으로 빼어 놓는게 좋음-->
<div id="yrModal" class="modal-dialog modal-lg modal-simple modal-add-new-address" _mstvisible="1">
	<div class="modal-content p-3 p-md-5" _mstvisible="2" id="calInsertModal"
	style="
    width: 550px;
    margin-top: -207px;
    scale: 80%;
    z-index: 999999;
    height: 900px;
    background: white;">
		<div class="modal-body" _mstvisible="3">
			<button onclick="fMClose()" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫다" _mstaria-label="59709" _msthash="363" _mstvisible="4"></button>
			<div class="text-center mb-4" _mstvisible="4">
				<h3 class="address-title" _msttexthash="28777372" _msthash="364" _mstvisible="5">Calendar</h3>
				<p class="address-subtitle" _msttexthash="97182306" _msthash="365" _mstvisible="5">( 일정 추가 )</p>
			</div>
			<form method="post" id="addNewAddressForm" class="row g-3 fv-plugins-bootstrap5 fv-plugins-framework" onsubmit="return false" novalidate="novalidate" _mstvisible="4">
				<div class="col-12 col-md-12 fv-plugins-icon-container" _mstvisible="5">
					범위<select class="form-control" id="schType" name="schType" onchange="selectBoxChange(this.value);">
							<option value="" style="display: none;" disabled selected>--선택--</option>
									<option value="C">공용</option>
						</select>
						<select class="form-control" id="projectType" name="projectType" onchange="selectBoxChange(this.value);">
								<c:forEach items="${proList }" var="project">
									<option value="${project.proSn}">${project.proNm}</option>
								</c:forEach>
						</select><br>							
					시작일 <input class="form-control" type="datetime-local" id="schStart" name="schStart" value=""><br> 
					<div id="dateStratEnd">	
						종료일 <input class="form-control" type="datetime-local" id="schEnd" name="schEnd" value=""><br> 
					</div>
					제목 <input class="form-control" type="text" id="schTitle" name="schTitle" value=""><br> 
					내용<textarea class="form-control" name="schContent" id="schContent" name="schContent" cols="40" rows="8"></textarea><br> 
					하루종일 <input  type="checkbox" id="allDay" name="allDay" class="allDay"><br> 
				<!-- 	알람여부 <input  type="checkbox" id="schAlarm" name="schAlarm"><br> -->
					글자색<input type="color" id="schFColor" name="schFColor">
					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback" _mstvisible="6"></div>
				</div>

				<button onclick="fCalAdd()" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#enableOTP" _msttexthash="15070224" _msthash="286">등록</button>
				<input type="hidden" id="schEmpCd" name="schEmpCd" value="${principal.realUser.empCd }"> <input type="hidden" id="schCode" name="schCode" _mstvisible="5">
				<input type="hidden" id="schDeptName" name="schDeptName" value="${principal.realUser.deptCd }">
			<security:csrfInput />
			</form>
		</div>
	</div>
</div>

<!-- 모달 영역 밖으로 빼어 놓는게 좋음-->
<div id="detailModal" class="modal-dialog modal-lg modal-simple modal-add-new-address" _mstvisible="1">
	<div class="modal-content p-3 p-md-5" _mstvisible="2" id="calDetailModal"
	style="
    width: 550px;
    margin-top: -207px;
    scale: 80%;
    z-index: 999999;
    height: 900px;
    background: white;">
		<div class="modal-body" _mstvisible="3">
			<button onclick="fMClose()" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="닫다" _mstaria-label="59709" _msthash="363" _mstvisible="4"></button>
			<div class="text-center mb-4" _mstvisible="4">
				<h3 class="address-title" _msttexthash="28777372" _msthash="364" _mstvisible="5">Calendar</h3>
				<p class="address-subtitle" _msttexthash="97182306" _msthash="365" _mstvisible="5">( 일정 수정, 삭제 )</p>
			</div>
			<form method="post" id="addNewAddressForm" class="row g-3 fv-plugins-bootstrap5 fv-plugins-framework" onsubmit="return false" novalidate="novalidate" _mstvisible="4">
				<div class="col-12 col-md-12 fv-plugins-icon-container" _mstvisible="5">
					시작일 <input class="form-control" type="datetime-local" id="detailStart" name="detailStart" value=""><br>
					<div id="detailStratEnd"> 
						종료일 <input class="form-control" type="datetime-local" id="detailEnd" name="detailEnd" value=""><br> <br>
					</div> 
					제목 <input class="form-control" type="text" id="detailTitle" name="detailTitle" value=""><br> 
					내용	<textarea class="form-control" name="schContent" id="detailContent" name="detailContent" cols="40" rows="8"></textarea><br> 
					하루종일 <input type="checkbox" id="detailAllDay" name="detailAllDay" class="detailAllDay"><br> 
					알람여부 <input type="checkbox" id="detailAlarm" name="detailAlarm"><br> 글자색<input type="color" id="detailFColor" name="detailFColor">
					<div class="fv-plugins-message-container fv-plugins-message-container--enabled invalid-feedback" _mstvisible="6"></div>
				</div>
				<input type="hidden" id="detailEmpCd" name="detailEmpCd" value="" />
				<button onclick="fCalUpdate()" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#enableOTP" _msttexthash="15070224" _msthash="286">수정</button>
				<button onclick="fCalDelete()" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#enableOTP" _msttexthash="15070224" _msthash="286">삭제</button>
				<input type="hidden" id="detailEmp" name="detailEmp" value="" disabled>
				<security:csrfInput />
			</form>
		</div>
	</div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath }/resources/js/app/calendar/calendar.js"></script> 


 