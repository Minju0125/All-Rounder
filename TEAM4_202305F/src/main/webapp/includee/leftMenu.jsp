<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="currUri" value='<%=request.getAttribute("javax.servlet.forward.request_uri") %>'/>
<sec:authorize access="isAuthenticated()">
  <sec:authentication property="principal" var="realUser" />
</sec:authorize>
<!-- Menu -->
<aside id="layout-menu" class="layout-menu menu-vertical menu bg-menu-theme">
  <div class="app-brand demo">
    <a 
    	href='<c:if test="${realUser.username   ne 'admin'}">/dashBoard/${realUser.username}</c:if>' 
    	href='<c:if test="${realUser.username   eq 'admin'}">#</c:if>'   class="app-brand-link">
      <span class="app-brand-logo demo">
        <img src="/resources/images/minilogo.png" style="width: 35%;"></span>
      <span class="app-brand-text demo menu-text fw-bold ms-n4">all-rounder</span>
    </a> <a href="javascript:void(0);" class="layout-menu-toggle menu-link text-large ms-auto"> <i
        class="bx bx-chevron-left bx-sm align-middle"></i>
    </a>
  </div>
  <div class="menu-inner-shadow"></div>

  <c:if test="${realUser.username   ne 'admin'}">

    <ul class="menu-inner py-1 ps ps--active-y">
      <!-- Dashboards -->
      <li class="menu-item  <c:if test='${fn:startsWith(currUri, "/dashBoard")}'>active</c:if>" >
        <a href="/dashBoard/${realUser.username}" class="menu-link ">
          <i class="menu-icon tf-icons bx bx-home-circle"></i>
          <div class="text-truncate">Dashboards</div>
        </a>
      </li>
      <!-- 전자결재 -->
      <li class="menu-item  <c:if test='${fn:startsWith(currUri, "/sanctionform") or fn:startsWith(currUri, "/sanctionhome") or fn:startsWith(currUri, "/sanction/") }'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bx-detail"></i>
          <div class="text-truncate">전자결재</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanctionhome")}'>active</c:if>">
            <a href="<c:url value='/sanctionhome' />" class="menu-link" >
              <div class="text-truncate">결재홈</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanctionform")}'>active</c:if>">
            <a href="<c:url value='/sanctionform' />" class="menu-link" >
              <div class="text-truncate">결재하기</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanction/drafter")}'>active</c:if>">
            <a href="<c:url value='/sanction/drafter' />" class="menu-link" >
              <div class="text-truncate">기안문서함</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanction/sanctner")}'>active</c:if>">
            <a href="<c:url value='/sanction/sanctner' />" class="menu-link" >
              <div class="text-truncate">결재대기함</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanction/rcyer")}'>active</c:if>">
            <a href="<c:url value='/sanction/rcyer' />" class="menu-link" >
              <div class="text-truncate">수신문서함</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanction/dept")}'>active</c:if>">
            <a href="<c:url value='/sanction/dept' />" class="menu-link" >
              <div class="text-truncate">부서문서함</div>
            </a>
          </li>           
        </ul>
      </li>

      <!--프로젝트 -->
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">PROJECT</span>
      </li>

      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/pms") or fn:startsWith(currUri, "/프로젝트일정") or fn:startsWith(currUri, "/memo") or fn:startsWith(currUri, "/job")  or fn:startsWith(currUri, "/issue")}'>active open</c:if>" >
        <a class="menu-link menu-toggle">
          <i class='menu-icon tf-icons bx bx-archive'></i>
          <div class="text-truncate">프로젝트</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/pms") or fn:startsWith(currUri, "/job") or fn:startsWith(currUri, "/issue")}'>active</c:if>">
            <a href="<c:url value='/pms/project'/>" class="menu-link">
              <div class="text-truncate">프로젝트 목록</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/memo")}'>active</c:if>">
            <a href="<c:url value='/memo'/>" class="menu-link">
              <div class="text-truncate">메모장</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- 그룹웨어 -->
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">GROUPWARE</span>
      </li>

      <!-- 근태현황 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/attendance")}'>active</c:if>">
        <a href="<c:url value='/attendance'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bxs-user-detail"></i>
          <div class="text-truncate">근태현황</div>
        </a>
      </li>
      <!-- 일정 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/cal")}'>active</c:if>">
        <a href="<c:url value='/cal'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-calendar"></i>
          <div class="text-truncate">일정</div>
        </a>
      </li>
      <!-- 메일 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/mail")}'>active</c:if>">
        <a href="<c:url value='/mail/mailForm'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-mail-send"></i>
          <div class="text-truncate">메일함</div>
        </a>
      </li>
      <!-- 웹하드 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/web")}'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bx-save"></i>
          <div class="text-truncate">웹하드</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/web/c")}'>active</c:if>">
            <a href="<c:url value='/web/c'/>" class="menu-link">
              <div class="text-truncate">개인</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/web/d")}'>active</c:if>">
            <a href="<c:url value='/web/d'/>" class="menu-link">
              <div class="text-truncate">부서</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- 자원예약 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/vehicle") or fn:startsWith(currUri, "/confRoom") or fn:startsWith(currUri, "/myReservation") }'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bxs-car-garage"></i>
          <div class="text-truncate">자원예약</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/vehicle/vehicleHome")}'>active</c:if>">
            <a href="<c:url value='/vehicle/vehicleHome'/>" class="menu-link">
              <div class="text-truncate">차량</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/confRoom/confRoomHome")}'>active</c:if>">
            <a href="<c:url value='/confRoom/confRoomHome'/>" class="menu-link">
              <div class="text-truncate">회의실</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/myReservation/myReservationHome")}'>active</c:if>">
            <a href="<c:url value='/myReservation/myReservationHome'/>" class="menu-link">
              <div class="text-truncate">내예약보기</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- 게시판 -->
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">BOARD</span>
      </li>
      
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice") or fn:startsWith(currUri, "/event") or fn:startsWith(currUri, "/자유게시판") or fn:startsWith(currUri, "/faq")}'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bx-clipboard"></i>
          <div class="text-truncate">게시판</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice")}'>active</c:if>">
            <a href="<c:url value='/notice' />" class="menu-link">
              <div class="text-truncate">공지사항</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/event")}'>active</c:if>">
            <a href="<c:url value='/event/home'/>" class="menu-link">
              <div class="text-truncate">경조사게시판</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/자유게시판")}'>active</c:if>">
            <a href="<c:url value='/free'/>" class="menu-link">
              <div class="text-truncate">자유게시판</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/faq")}'>active</c:if>">
            <a href="<c:url value='/faq'/>" class="menu-link">
              <div class="text-truncate">FAQ</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- user -->
      <li class="menu-header small text-uppercase <c:if test='${fn:startsWith(currUri, "/org") or fn:startsWith(currUri, "/mypage")}'>active</c:if>">
        <span class="menu-header-text">USER</span>
      </li>
      <!-- 조직도 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/org")}'>active</c:if>">
        <a href="<c:url value='/org'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-sitemap"></i>
          <div class="text-truncate">조직도</div>
        </a>
      </li>
    </ul>
  </c:if>



  <!--  관리자일때 -->
  <c:if test="${realUser.username eq 'admin'}">
    <ul class="menu-inner py-1 ps ps--active-y">

      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">MANAGEMENT</span>
      </li>

      <!-- 직원정보관리 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/account")}'>active</c:if>">
        <a href="<c:url value='/account/home'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bxs-face"></i>
          <div class="text-truncate">직원정보관리</div>
        </a>
      </li>
      <!-- 전자결재관리 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanctionform") or fn:startsWith(currUri, "/sanction") }'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bx-copy-alt"></i>
          <div class="text-truncate">전자결재관리</div>
        </a>
        <ul class="menu-sub " >
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanctionform/listUI")}'>active</c:if>">
            <a href="<c:url value='/sanctionform/listUI'/>" class="menu-link">
              <div class="text-truncate">결재양식관리</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/sanction/admin")}'>active</c:if>">
            <a href="<c:url value='/sanction/admin'/>" class="menu-link">
              <div class="text-truncate">결재문서관리</div>
            </a>
          </li>
        </ul>
      </li>
      <!-- 프로젝트관리 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/adminproject")}'>active</c:if>">
        <a href="<c:url value='/adminproject'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-book"></i>
          <div class="text-truncate">프로젝트관리</div>
        </a>
      </li>
      <!-- 근태관리 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/adminAttendance")}'>active</c:if>">
        <a href="<c:url value='/adminAttendance'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bxs-user-detail"></i>
          <div class="text-truncate">근태관리</div>
        </a>
      </li>
      

      <!-- 일정관리 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/cal")}'>active</c:if>">
        <a href="<c:url value='/cal'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-calendar"></i>
          <div class="text-truncate">일정관리</div>
        </a>
      </li>
      <!-- 관리자웹하드 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/web")}'>active</c:if>">
        <a href="<c:url value='/web/admin'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-save"></i>
          <div class="text-truncate">관리자웹하드</div>
        </a>
      </li>
     
      <!-- 자원예약 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/adVehicle") or fn:startsWith(currUri, "/adReservation") }'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bxs-car-garage"></i>
          <div class="text-truncate">자원예약관리</div>
        </a>
        <ul class="menu-sub " >
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/adVehicle")}'>active</c:if>">
            <a href="<c:url value='/adVehicle/adVehicleHome'/>" class="menu-link">
              <div class="text-truncate">자원관리</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/adReservation")}'>active</c:if>">
            <a href="<c:url value='/adReservation/adReservationHome'/>" class="menu-link">
              <div class="text-truncate">예약관리</div>
            </a>
          </li>
        </ul>
      </li>
      
      <!-- 게시판 -->
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">BOARD MANAGEMENT</span>
      </li>
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice") or  fn:startsWith(currUri, "/event") or fn:startsWith(currUri, "/free") or fn:startsWith(currUri, "/faq") }'>active open</c:if>">
        <a class="menu-link menu-toggle">
          <i class="menu-icon tf-icons bx bx-clipboard"></i>
          <div class="text-truncate">게시판관리</div>
        </a>
        <ul class="menu-sub">
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/notice")}'>active</c:if>">
            <a href="<c:url value='/notice' />" class="menu-link">
              <div class="text-truncate">공지사항</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/event")}'>active</c:if>">
            <a href="<c:url value='/event/home'/>" class="menu-link">
              <div class="text-truncate">경조사게시판</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/free")}'>active</c:if>">
            <a href="<c:url value='/free'/>" class="menu-link">
              <div class="text-truncate">자유게시판</div>
            </a>
          </li>
          <li class="menu-item <c:if test='${fn:startsWith(currUri, "/faq")}'>active</c:if>">
            <a href="<c:url value='/faq'/>" class="menu-link">
              <div class="text-truncate">FAQ</div>
            </a>
          </li>
        </ul>
      </li>
      <li class="menu-header small text-uppercase">
        <span class="menu-header-text">ANALYSIS</span>
      </li>
      <!-- 지표분석 -->
      <li class="menu-item <c:if test='${fn:startsWith(currUri, "/analysis")}'>active</c:if>">
        <a href="<c:url value='/analysis/home'/>" class="menu-link">
          <i class="menu-icon tf-icons bx bx-line-chart"></i>
          <div class="text-truncate">지표분석</div>
        </a>
      </li>
    </ul>
  </c:if>
  <!-- 여기까지 -->
</aside>
<!-- / Menu -->