<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2023. 11. 17.      김보영        최초작성
* 2023. 11. 24.      김보영        생성, 리스트,칸반보드
* 2023. 11. 29.      김보영        수정,삭제
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>        

<security:authorize access="isAuthenticated()">
  <security:authentication property="principal" var="realUser" />
</security:authorize>



<style>
.background-image {
    width: 100vw;
    height: 100vh;
    object-fit: cover; /* 이미지가 찌그러지지 않도록 설정 */
    position: fixed; /* 이미지를 고정시킴 */
    top: 0;
    left: 0;
    z-index: -1; /* 다른 콘텐츠 위에 나타나지 않도록 함 */
}

/* 나머지 콘텐츠의 스타일링 */
.content {
    padding: 20px;
    color: white;
    text-align: center;
    margin-top: 50vh; /* 화면 세로 중앙 정렬을 위한 설정 */
}
</style>


<input type="hidden"  id="realUser" value="${realUser.username }" />
<h1 class="mb-0">${proInfo.proNm}</h1>
<h5 class="mb-0">${proInfo.proBdate} ~ ${proInfo.proEdate }</h5><br>
<!-- <img src="/resources/css/bg/bg.jpg" alt="Background Image" class="background-image" > -->
<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
      <div>
        <!-- Content -->

        <!-- navbar -->
        <div class="col-md-7 col-lg-7">
          <ul class="nav nav-pills mb-3 nav-fill" role="tablist">
            <li class="nav-item" role="presentation">
              <a class="nav-link dropdown-toggle" href="javascript:void(0);" data-bs-toggle="dropdown"
                aria-expanded="false" data-trigger="hover">
                <i class='bx bxs-copy-alt'></i>
                　프로젝트</a>
              <div class="dropdown-menu">
                <c:forEach items="${proj}" var="proj">
                  <c:if test="${proj.proSttus == 1}">
                    <a class="dropdown-item" href="/issue/${proj.proSn}/home">${proj.proNm}</a>
                  </c:if>
                </c:forEach>
              </div>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link " href="/job/${proSn}/home">
                <i class='bx bx-edit'></i>
                　일감</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link active" href="javascript:void(0)">
                <i class='bx bx-calendar-exclamation'></i>
                　이슈</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link" href="/pms/gantt/${proSn}">
                <i class='bx bx-chart'></i>
                　간트차트</a>
            </li>
            <li class="nav-item" >
	            <c:if test="${role eq 'leader'}">
              <a class="nav-item nav-link " href="/pleader/${proSn }">
                <i class='bx bxs-doughnut-chart'></i>
                　리더통계</a>
	            </c:if>
	            <c:if test="${role ne 'leader'}">
              <a class="nav-item nav-link disabled " href="javascript:void(0)">
                <i class='bx bxs-doughnut-chart'></i>
                　리더통계</a>
	            </c:if>
            </li>
          </ul>
        </div>
        <!-- /navbar -->
       <div class="card mt-3" style="margin-left: 25px; margin-right: 25px;">
        <div class="row card-body my-n3" >
        	<div class="col-md-6 col-xl-6 ">
        		<div  class="row">
        			<div class="col-md-4 col-xl-4">
        				<select id="searchIssueImp" name="issueImp" class="form-select" required>
		                    <option value="">(중요도)</option>
		                    <option value="1">긴급</option>
		                    <option value="2">중간</option>
		                    <option value="3">낮음</option>
		                  </select>
        			</div>
        			<div class="col-md-4 col-xl-4">
	        			<select id="searchIssueSe" name="issueSe" class="form-select" required>
	                    <option value="">(구분)</option>
	                    <option value="1">일반이슈</option>
	                    <option value="2">결함이슈</option>
	                  </select>
        			</div>
        			<div class="col-md-4 col-xl-4">
        				<button type="button" class="btn btn-icon btn-label-info" onclick="fn_selectIssue()">
                              <span class="tf-icons bx bx bx-search"></span>
                            </button>
        				<button type="button" class="btn btn-icon btn-label-info" id="resetBtn">
                              <span class="tf-icons bx bx bx-revision"></span>
                            </button>
        			</div>
        		</div>
        	</div>
        	<div class="col-md-6 col-xl-6" style="text-align: end;">
        	 <button class="btn btn-info text-end fw-bold" type="button" data-bs-toggle="modal"
                  data-bs-target="#backDropModal">등록</button>
        	</div>
        </div>
        </div>
        

        <!-- 이슈 칸반 -->
        <div class="row mt-3">
          <div class="dropzone col-md-6 col-xl-4" data-issuesttus="1" data-add-bo="border-primary"
            style="height: 1000px;">
            <div class="card shadow-none bg-transparent  mb-3">
              <div class="card-body">
                <h4 class="card-title">진행</h4>
                <!-- 이곳에 이슈들 -->
                <div id="inProgressIssue" class="issueBox">

                </div>
                <!-- /이곳에 이슈들 -->
              </div>
            </div>
          </div>
          <div class=" dropzone col-md-6 col-xl-4" data-issuesttus="2" data-add-bo="border-warning"
            style="height: 1000px;">
            <div class="card shadow-none bg-transparent  mb-3">
              <div class="card-body">
                <h4 class="card-title">보류</h4>
                <!-- 이곳에 이슈들 -->
                <div id="onHoldIssue" class="issueBox">

                </div>
                <!-- /이곳에 이슈들 -->
              </div>
            </div>
          </div>
          <div class=" dropzone col-md-6 col-xl-4" data-issuesttus="3" data-add-bo="border-success"
            style="height: 1000px;">
            <div class="card shadow-none bg-transparent  mb-3">
              <div class="card-body">
                <h4 class="card-title">완료</h4>
                <!-- 이곳에 이슈들 -->
                <div id="completedIssue" class="issueBox">

                </div>
                <!-- /이곳에 이슈들 -->
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- /Content -->
    </div>
    <!-- /Content wrapper -->
  </div>
  <!-- /Layout container -->
</div>
<!-- /Layout wrapper -->



<!-- 등록 모달 -->
<!-- Modal -->
<div class="modal fade " id="backDropModal" data-bs-backdrop="static" tabindex="-1" style="display: none;"
  aria-modal="true">
  <div class="modal-dialog">
    <form class="modal-content fv-plugins-bootstrap5 fv-plugins-framework " id="issueForm">
      <security:csrfInput />
      <input type="hidden" value="${proSn}" id="proSn">
      <div class="modal-header">
        <h5 class="modal-title" id="backDropModalTitle">이슈등록</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">

        <div class="row g-2" style="text-align: center;">
          <div class=" mb-1 col-md-8 col-lg-8">
            <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
              <button type="button" class="btn-issueSe genIssue btn btn-outline-secondary" onclick="fn_issueSe('1')"
                style="border-bottom-left-radius: 0.375rem;border-top-left-radius: 0.375rem;">
                <i class="tf-icons bx bx-car">일반이슈</i>
              </button>
              <button type="button" class="btn-issueSe bugIssue btn btn-outline-secondary" onclick="fn_issueSe('2')">
                <i class="tf-icons bx bx-rocket">결함이슈</i>
              </button>
            </div>
              <div class="input-group has-validation">
	              <input type="hidden" id="issueSe" name="issueSe" value="" />
              </div>
          </div>
          <div class=" mb-1 col-md-3 col-lg-3">
              <div class="input-group has-validation">
            <div class="btn-group" role="group" aria-label="Button group with nested dropdown">
              <div class="btn-group" role="group">
                <select name="issueImp" id="issueImp" class="form-select" >
                  <option value="">(중요도)</option>
                  <option value="1">긴급</option>
                  <option value="2" >중간</option>
                  <option value="3">낮음</option>
                </select>
              </div>
              </div>
            </div>
          </div>
        </div>

        <div class="col-12 mb-3">
          <div class="row">
	            <div class="col-md mb-md-0 mb-2 p-2">
	              <div class="form-check custom-option custom-option-icon">
	                <label class="form-check-label custom-option-content" for="customRadioBuilder">
	                  <span class="custom-option-body">
	                    <i class="bx bx-building-house"></i>
	                    <span class="custom-option-title">진행</span>
	                  </span>
	                  <input name="issueSttus" class="form-check-input" type="radio" value="1" id="issueSttus" checked>
	                </label>
	              </div>
	            </div>
	            <div class="col-md mb-md-0 mb-2 p-2">
	              <div class="form-check custom-option custom-option-icon checked">
	                <label class="form-check-label custom-option-content" for="customRadioOwner">
	                  <span class="custom-option-body">
	                    <i class="bx bx-crown"></i>
	                    <span class="custom-option-title">보류</span>
	                  </span>
	                  <input name="issueSttus" class="form-check-input" type="radio" value="2" id="issueSttus">
	                </label>
	              </div>
	            </div>
	            <div class="col-md mb-md-0 mb-2 p-2">
	              <div class="form-check custom-option custom-option-icon">
	                <label class="form-check-label custom-option-content" for="customRadioBroker">
	                  <span class="custom-option-body">
	                    <i class="bx bx-briefcase-alt"></i>
	                    <span class="custom-option-title">완료</span>
	                  </span>
	                  <input name="issueSttus" class="form-check-input" type="radio" value="3" id="issueSttus">
	                </label>
	              </div>
	            </div>
          </div>
        </div>


        <div class="row">
          <div class="col mb-3">
            <label for="issueSj" class="fw-medium">이슈명</label>
            <div class="input-group has-validation">
	            <input type="text" id="issueSj" placeholder="이슈명을 입력하세요"  name="issueSj" class="form-control" >
              </div>
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="issueEdate" class="fw-medium">이슈기한</label>
            <input type="date" id="issueEdate" name="issueEdate" class="form-control" placeholder="Enter Name">
          </div>
        </div>
        <div class="row" id="issueJobSnDiv">
          <div class="col mb-3">
            <label for="jobSn" class="fw-medium">일감참조</label>
            <select name="jobSn" id="jobSn" class="form-select" tabindex="0">
              <option value="" >(선택)</option>
              <c:forEach items="${jobList}" var="uJob">
                <option value="${uJob.jobSn}" id="insertTempUJob">${uJob.jobSj}</option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="row">
          <div class="col mb-3">
            <label for="issueCn" class="fw-medium">내용</label>
             <div class="input-group has-validation">
	            <textarea name="issueCn" placeholder="내용을 입력하세요" id="issueCn" class="form-control" rows="5" cols="10"></textarea>
              </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
       <button type="button" class="btn btn-label-warning"  onclick="fillDummyData()">
          시연용
        </button>
        <button type="button" class="btn btn-label-secondary" data-bs-dismiss="modal">
          취소
        </button>
        <button type="button" class="btn btn-primary" onclick="fn_issueInsert()">저장</button>
      </div>
    </form>
  </div>
</div>


<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/issue/issue.js"></script>
<script>


function fillDummyData() {
    // 각 입력 태그에 더미값 설정
    document.getElementById('issueImp').value = '1';
    document.getElementById('issueSj').value = '305호 수료식';
    document.getElementById('issueEdate').value = '2023-12-15';
    document.getElementById('issueCn').value = '그동안 수고했어요 다들!';
}

//일반이슈 버튼 클릭시 일감명참조 안보이기
$('.genIssue').on("click", function(){
  if($(this).val() ==""){
    $('#issueJobSnDiv').hide();

  }else {
    $('#issueJobSnDiv').show();
  } 
});

$('.bugIssue').on("click", function(){
  if($(this).val() ==""){
    $('#issueJobSnDiv').show();

  }else {
    $('#issueJobSnDiv').hide();
  } 
});

</script>

