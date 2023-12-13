<%--
* [[개정이력(Modification Information)]]
* 수정일       	수정자        수정내용
* ----------  	---------  -----------------
* 2023. 11. 21. 김보영        최초작성
* Copyright (c) 2023 by DDIT All right reserved
 --%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>        
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>        

<security:authorize access="isAuthenticated()">
  <security:authentication property="principal" var="realUser" />
</security:authorize>
 
<h1 class="mb-0">${proInfo.proNm}</h1>
<h5 class="mb-0">${proInfo.proBdate} ~ ${proInfo.proEdate }</h5><br>
<!-- Layout wrapper -->
<div class="layout-wrapper layout-content-navbar">
  <div class="layout-container">
    <!-- Layout container -->
    <div class="layout-page">
      <!-- Content wrapper -->
      <div>
        <!-- Content -->
        <!-- navbar -->
        <input type="hidden" value="${proInfo.proBdate }"  id="projectBdate"/>
        <input type="hidden" value="${proInfo.proEdate }"  id="projectEdate"/>
        <c:if test="${realUser.username ne 'admin'}">
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
                    <a class="dropdown-item" href="/job/${proj.proSn}/home">${proj.proNm}</a>
                  </c:if>
                </c:forEach>
              </div>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link active" href="/job/${proSn}/home">
                <i class='bx bx-edit'></i>
                　일감목록</a>
            </li>
            <li class="nav-item" role="presentation">
              <a class="nav-item nav-link" href="/issue/${proSn}/home">
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
        </c:if>
        <!-- /navbar -->
        <!-- 내용 -->
		<input type="hidden" data-set-prosn="${detail.proSn}" class="dproSn"/>
    	<input type="hidden" data-set-jobSn="${detail.jobSn}" class="djobSn"/>
    	<input type="hidden" data-set-jobuSn="${detail.jobuSn}" class="djobuSn" name="updateTempUJob" id="updateTempUJob">
    	<div class="row">
          <div class="col-xl-6 col-lg-5 col-md-5">
            <!-- About User -->
            <div class="card mb-4">
              <div class="card-body">
                 <h5 class="card-action-title mb-0"><i class="bx bx-list-ul me-2"></i>상세정보</h5>
                <ul class="list-unstyled mb-4 mt-3">
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-user"></i><span class="fw-medium mx-2">일감순번 :</span> <span>${detail.jobSn}</span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-check"></i><span class="fw-medium mx-2">일감명 :</span> <span>${detail.jobSj}</span>
                  </li>
                  <c:if test="${not empty detail.jobuSn}">
	                  <li>
	                  	<div style="position: absolute;width: 100%;height: 60px;z-index: 10;"></div>
	                    <div class="row g-2" style="text-align: center;">
	                      <div class="col mb-3">
				            <button type="button"
				              class="btn rounded-pill btn-label-primary <c:if test="${detail.jobStcd eq '1'}">active</c:if>">진행</button>
				          </div>
				          <div class="col mb-3">
				            <button  type="button"
				              class="btn rounded-pill btn-label-success <c:if test="${detail.jobStcd eq '2'}">active</c:if>">요청</button>
				          </div>
				          <div class="col mb-3">
				            <button  type="button"
				              class="btn rounded-pill btn-label-danger <c:if test="${detail.jobStcd eq '3'}">active</c:if>">피드백</button>
				          </div>
				          <div class="col mb-3">
				            <button  type="button"
				              class="btn rounded-pill btn-label-warning <c:if test="${detail.jobStcd eq '4'}">active</c:if>">보류</button>
				          </div>
				          <div class="col mb-3">
				            <button  type="button"
				              class="btn rounded-pill btn-label-info <c:if test="${detail.jobStcd eq '5'}">active</c:if>">완료</button>
				          </div>
	                    </div>
	                  </li>
                  </c:if>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-star"></i><span class="fw-medium mx-2">작성자 :</span> <span>${detail.findName}</span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-flag"></i><span class="fw-medium mx-2">작성일 :</span> <span>${detail.jobRdate}</span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-detail"></i><span class="fw-medium mx-2">시작일 :</span>
                    <span>${detail.jobBdate}</span>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-detail"></i><span class="fw-medium mx-2">종료일 :</span>
                    <span>${detail.jobEdate}</span>
                  </li>
                  <c:if test="${not empty detail.jobCdate}">
	                  <li class="d-flex align-items-center mb-3">
	                    <i class="bx bx-detail"></i><span class="fw-medium mx-2">완료일 :</span>
	                    <span>${detail.jobCdate}</span>
	                  </li>
                  </c:if>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-star"></i><span class="fw-medium mx-2">우선순위 :</span>
                    <c:if test="${detail.jobPriort eq 1 }">
                   	 <span>긴급</span>
                    </c:if>
                    <c:if test="${detail.jobPriort eq 2 }">
                   	 <span>높음</span>
                    </c:if>
                    <c:if test="${detail.jobPriort eq 3 }">
                   	 <span>보통</span>
                    </c:if>
                    <c:if test="${detail.jobPriort eq 4 }">
                   	 <span>낮음</span>
                    </c:if>
                  </li>
                  <li class="d-flex align-items-center mb-3">
                    <i class="bx bx-tag-alt"></i><span class="fw-medium mx-2">진행도 :</span>
                  </li>
                  <li>
                    <div class="progress">
                      <div class="progress-bar progress-bar-striped progress-bar-animated bg-primary" role="progressbar"
                        style="width: ${detail.jobProgrs}%" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                    </div>
                  </li>
                </ul>
                <c:if test="${empty detail.jobuSn}">
	                <!-- 상위일감일때  -->
	                <small class="text-muted text-uppercase">하위일감</small>
	                <ul>
	                	<c:forEach items="${LowJList }" var="row">
		                  <li class="d-flex align-items-center mb-3">
		                   <a href='/job/${detail.proSn}/${row.jobSn}/detail'><i class="bx bx-detail"></i><span class="fw-medium mx-2">${row.jobSj }</span></a>
		                  </li>
	                	</c:forEach>
	                </ul>
                </c:if>
                
                <!-- 담당자 리스트 -->

                <h5 class="card-action-title mb-0"><i class="bx bxs-face me-2"></i>담당자</h5>
                <ul class="list-unstyled mb-0 mt-2">
                  <li class="d-flex align-items-center ">
                    <!-- 담당자 정보 표시  -->
                    <c:if test="${not empty chargerList}">
                    	<div> 
		                    <div class="d-flex align-items-center avatar-group">
		                    <c:forEach items="${chargerList}" var="charger" varStatus="i" >
		                    	<c:if test="${not empty charger.emp.empProfileImg}">
                              <div class="avatar pull-up" data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" aria-label="Vinnie Mostowy" data-bs-original-title="${charger.emp.empName}">
                                <img src="${charger.emp.empProfileImg}" alt="Avatar" class="rounded-circle">
                              </div>
		                    	
		                    	</c:if>
		                    	<c:if test="${empty charger.emp.empProfileImg}">
                              <div class="avatar pull-up" data-bs-toggle="tooltip" data-popup="tooltip-custom" data-bs-placement="top" aria-label="Vinnie Mostowy" data-bs-original-title="${charger.emp.empName}">
                                <img src='/resources/images/basic.png' alt="Avatar" class="rounded-circle">
                              </div>
		                    	
		                    	</c:if>
		                    	
                              
	                    	</c:forEach>
                            </div>
	                    </div>
                    </c:if>
                  </li>
                </ul>
                <!-- /담당자 리스트 -->
                
              </div>
            </div>
            <!--/ About User -->
            <!-- 파일 내역 -->
            <div class="card mb-4">
              <div class="card-body">
                 <h5 class="card-action-title mb-0"><i class="bx bxs-folder-open me-2"></i>파일</h5>
                <ul class="list-unstyled mt-3 mb-0">
                	<c:if test="${not empty fileList }">
                	<c:forEach items="${fileList }" var="file">
                  <li class="d-flex align-items-center mb-3">
                  <a href="/job/${detail.proSn}/download?proFileCode=${file.proFileCode}&proAtchSnm=${file.proAtchSnm}">
                    <i class="bx bx-check"></i><span class="fw-medium mx-2 fs-5">${file.proAtchnm}</span>
                    <span>${file.proAtchFancysize}</span>　
                    </a>
                     <c:if test="${realUser.username ne 'admin'}">
                   	<a onclick="fn_deleteFile('${file.proFileCode}','${file.proAtchSnm}')"><span class="badge badge-center rounded-pill bg-danger fw-bold" >X</span></a>
                 	</c:if>
                  </li>
                    </c:forEach>
                  </c:if>
                </ul>
              </div>
            </div>
            <c:if test="${realUser.username eq 'admin'}">
            	<button type="button" class="btn btn-primary" onclick="fn_goAdminProject('${detail.proSn}')">뒤로가기</button>
            </c:if>
            <c:forEach items="${chargerList}" var="charger">
<!--             로그인한사람이랑 담당자가 동일하면 수정가능 -->
	            <c:if test="${loginEmpCd eq charger.emp.empCd}">
		          	<!-- 수정폼버튼 -->
		            <button type="button" class="btn btn-label-primary mb-3" style="width: 100%;" data-bs-toggle="offcanvas" data-bs-target="#offcanvasBoth" aria-controls="offcanvasBoth">
		              <span class="tf-icons bx bx-pie-chart-alt me-1"></span>일감 수정하기
		            </button>
		            <!-- /수정폼버튼 -->
	            </c:if>
            </c:forEach>
          </div>
          <div class="col-xl-6 col-lg-7 col-md-7">
          	 <!-- 내용 -->
            <div class="card card-action mb-4">
              <div class="card-header align-items-center">
                <h5 class="card-action-title mb-0"><i class="bx bx-list-ul me-2"></i>내용</h5>
              </div>
              <div class="card-body">
              <!-- 일감내용부분 -->
              <% pageContext.setAttribute("LF", "\n"); %>
              ${fn:replace(detail.jobCn,LF, '<br>')}
              </div>
            </div>
            <!--/ 내용 -->
            <!-- Activity Timeline -->
            <div class="card card-action mb-4">
              <div class="card-header align-items-center">
                <h5 class="card-action-title mb-0"><i class="bx bx-list-ul me-2"></i>Activity Timeline</h5>
              </div>
              <div class="card-body">
                <ul class="timeline ms-2">
                <c:if test="${not empty logList}">
                	<c:forEach items="${logList }" var="log" varStatus="i">
                  <li class="timeline-item timeline-item-transparent">
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
                    <span class="timeline-point-wrapper"><span
                        class="timeline-point timeline-point-${color }"></span></span>
                    <div class="timeline-event">
                      <div class="timeline-header mb-1">
                        <h6 class="mb-0">수정자 : ${log.plogWriter}</h6>
                        <small class="text-muted">${log.plogCdate}</small>
                      </div>
                      <p class="mb-2">${log.plogCn}</p>
                    </div>
                  </li>
                  </c:forEach>
                  </c:if>
                  <c:if test="${empty logList}">
                  	<li class="timeline-item timeline-item-transparent">
                    <span class="timeline-point-wrapper"><span
                        class="timeline-point timeline-point-warning"></span></span>
                    <div class="timeline-event">
                      <div class="timeline-header mb-1">
                        <h6 class="mb-0">수정 내역이 없습니다.</h6>
                      </div>
                    </div>
                  </li>	
                  </c:if>
                  <li class="timeline-end-indicator">
                  
                    <i class="bx bx-check-circle"></i>
                  </li>
                </ul>
              </div>
            </div>
            <!--/ Activity Timeline -->
<!-- 			로그인한 사람이 담당자이면서 작성자이고 하위일감이면 삭제가능 -->
	        <!-- 일감삭제 -->
			<c:if test="${not empty detail.jobuSn}">
				<c:forEach items="${chargerList}" var="charger">
				<c:if test="${loginEmpCd eq charger.emp.empCd}">
	            <div class="card ">
	              <div class="card-body">
	               <h5 class="card-action-title mb-3 "><i class="bx bxs-message-square-x me-2"></i>일감삭제</h5>
	                <div class="mb-3 col-12 mb-0">
	                  <div class="alert alert-warning">
	                    <h6 class="alert-heading fw-medium mb-1">정말로 일감을 삭제하시겠습니까?</h6>
	                    <p class="mb-0">일감을 삭제하면 되돌릴 수 없습니다.</p>
	                  </div>
	                </div>
	                <form id="formAccountDeactivation">
	                  <security:csrfInput />
	                  <input type="hidden" value="${charger.emp.empCd}" name="tempEmpCd"/>
	                  <input type="hidden" name="empCd" value='<c:out value="${emp.empCd}"/>' />
	                  <div class="form-check mb-3">
	                    <input class="form-check-input" type="checkbox" name="accountActivation" id="accountActivation">
	                    <label class="form-check-label" for="accountActivation">확인했습니다.</label>
	                  </div>
	                  <button type="button" onclick="fn_jobDelete()" class="btn btn-danger">삭제</button>
	                </form>
	              </div>
	            </div>
	            </c:if>
	            </c:forEach>
            </c:if>
	        <!-- /일감삭제 -->
	        <!-- 일감삭제 안내 -->
	        <c:if test="${empty detail.jobuSn}">
	        	<div class="alert alert-info">
                   <h6 class="alert-heading fw-medium mb-1"><i class='bx bx-error'></i> 상위일감은 삭제할 수 없습니다.</h6>
                   <p class="mb-0">참조되어있는 하위일감을 모두 삭제 하세요.</p>
                 </div>
	        </c:if>
          </div>
        </div>
        <!-- /내용 -->
      </div>
      <!-- /Content -->
    </div>
    <!-- /Content wrapper -->
  </div>
  <!-- /Layout container -->
</div>
<!-- /Layout wrapper -->



<!-- 오프캔버스-수정 -->

<div class="offcanvas offcanvas-end " data-bs-scroll="true" tabindex="-1" id="offcanvasBoth"
  aria-labelledby="offcanvasBothLabel" aria-modal="true" role="dialog" style="width: 600px;">
  <div class="offcanvas-header">
    <h3 id="offcanvasBothLabel" class="offcanvas-title">
      <font style="vertical-align: inherit;">
        <font style="vertical-align: inherit;">일감 수정</font>
      </font>
    </h3>
    <button type="button" class="btn-close text-reset" data-bs-dismiss="offcanvas" aria-label="닫다"></button>
  </div>
  <div class="offcanvas-body my-auto mx-0 flex-grow-0">
    <!--내용-->
    <form enctype="multipart/form-data" id="formJobSettings" class="fv-plugins-bootstrap5 fv-plugins-framework">
    <security:csrfInput/>
      <div class="card-body">
        <div class="row">
          <div class="col mb-3">
            <label for="jobSj" class="fw-medium"><i class='bx bxs-book-alt bx-sm mb-1' ></i> 일감명</label>
            <div class="input-group has-validation">
            	<input name="jobSj"  value="${detail.jobSj}" type="text" id="jobSj" class="form-control" placeholder="일감제목을 입력하세요"  />
          	</div>
          </div>
        </div>
         <div class="row g-2">
          <div class="col mb-3">
            <label for="jobBdate" class="fw-medium"><i class='bx bx-calendar-edit bx-sm mb-1'></i> 시작일</label>
            <div class="input-group has-validation">
            	<input name="jobBdate" value="${detail.jobBdate}" type="date" id="jobBdate" class="form-control" >
          	</div>
          </div>
          <div class="col mb-3">
            <label for="jobEdate" class="fw-medium"><i class='bx bx-calendar-exclamation bx-sm mb-1'></i> 종료일</label>
            <div class="input-group has-validation">
            	<input name="jobEdate" value="${detail.jobEdate}" type="date" id="jobEdate" class="form-control" >
          	</div>
          </div>
        </div>
        <div id="proPeriod">
        	<label for="nameBackdrop" class="fw-light mb-3 proPeriod">프로젝트 기간 : ${proInfo.proBdate} ~ ${proInfo.proEdate }</label>
        </div>
        <c:if test="${empty detail.jobuSn }">
        <div id="ujobPeriod">
        	<c:forEach items="${jobList}" var="uJob">
        		<input type="hidden" 
        		data-bdate=" <c:if test='${detail.jobSn eq uJob.jobSn}'> ${uJob.jobBdate} </c:if>" 
        		data-edate=" <c:if test='${detail.jobSn eq uJob.jobSn}'> ${uJob.jobEdate} </c:if>" id="insertjobuSn" />
        	</c:forEach>
        </div> 
        </c:if>
        <c:if test="${ not empty detail.jobuSn }">
        <div id="ujobPeriod">
       		<input type="hidden" 
       		data-bdate="${detail.jobBdate}" 
       		data-edate="${detail.jobEdate}" id="insertjobuSn" value="${detail.jobuSn}"/>
        	<label for="nameBackdrop" class="fw-light mb-3 ujobPeriod">상위일감 기간 :  <span id="uJobBdate"></span> ~ <span id="uJobEdate"></span></label>
        </div> 
        </c:if>
       <input type="hidden" value="${detail.jobuSn}" class="jobuSn" name="jobuSn" id="jobuSn">
        <c:if test="${ not empty detail.jobuSn}">
              <div class="row g-2" style="text-align: center;">
             	 <div class="input-group has-validation">
            		<input id="jobStcd" type="hidden" name="jobStcd" value="${detail.jobStcd}" />
                </div>	
              <div class="col mb-3">
         <button type="button" onclick="fn_jobStcd('1')"
           class="btn-jobStcd btn rounded-pill btn-label-primary <c:if test="${detail.jobStcd eq '1'}">active</c:if>">진행</button>
       </div>
       <div class="col mb-3">
         <button  type="button" onclick="fn_jobStcd('2')"
           class="btn-jobStcd btn rounded-pill btn-label-success <c:if test="${detail.jobStcd eq '2'}">active</c:if>">요청</button>
       </div>
       <div class="col mb-3">
         <button  type="button" onclick="fn_jobStcd('3')"
           class="btn-jobStcd btn rounded-pill btn-label-danger <c:if test="${detail.jobStcd eq '3'}">active</c:if>">피드백</button>
       </div>
       <div class="col mb-3">
         <button  type="button" onclick="fn_jobStcd('4')"
           class="btn-jobStcd btn rounded-pill btn-label-warning <c:if test="${detail.jobStcd eq '4'}">active</c:if>">보류</button>
       </div>
       <div class="col mb-3"> 
         <button  type="button"  onclick="fn_jobStcd('5')"
           class="btn-jobStcd btn rounded-pill btn-label-info <c:if test="${detail.jobStcd eq '5'}">active</c:if>">완료</button>
       </div>
              </div>
           </c:if>
           
            <div class="row g-2">
          <div class="col mb-3">
            <label for="dobBackdrop" class="fw-medium"><i class='bx bx-edit bx-sm mb-1' ></i> 내용</label>
            <div class="input-group has-validation">
            	<textarea name="jobCn" rows="5" cols="20" id="jobCn" class="form-control" >${detail.jobCn}</textarea>
          	</div>
          </div>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium"><i class="bx bx-objects-horizontal-left bx-sm mb-1"></i>
              진행도</label>
            <select name ="jobProgrs" class="form-select" tabindex="0" id="roleEx4">
                <c:forEach begin="0" end="100" step="10" var="percentage" varStatus="loop">
                  <option value="${percentage}" <c:if test="${detail.jobProgrs eq percentage }">selected</c:if>>${percentage}%</option>
                </c:forEach>
            </select>
          </div>
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium"><i class="bx bx-error-circle bx-sm mb-1"></i> 우선순위</label>
            <select name="jobPriort" class="form-select" tabindex="0" id="">
              <option value="1"  <c:if test="${detail.jobPriort eq '1'}">selected</c:if>>긴급</option>
              <option value="2"  <c:if test="${detail.jobPriort eq '2'}">selected</c:if>>높음</option>
              <option value="3"  <c:if test="${detail.jobPriort eq '3'}">selected</c:if>>보통</option>
              <option value="4"  <c:if test="${detail.jobPriort eq '4'}">selected</c:if>>낮음</option>
            </select>
          </div>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="nameBackdrop" class="fw-medium d-block"><i class='bx bx-universal-access bx-sm mb-1'></i>
              담당자</label>
            <c:if test="${not empty proM }">
              <c:forEach items="${proM}" var="proM" varStatus="i">
                <div class=" col mx-2 form-check custom-option custom-option-basic mb-3 checked d-inline-block">
                  <label class="form-check-label custom-option-content" for="proM${i.index }">
                  	<c:set var="checked" value=""/>
                  	<c:forEach items="${chargerList}" var="charger">
                  		<c:if test="${proM.emp.empCd eq charger.empCd }">
                  			<c:set var="checked" value="checked"/>
                  		</c:if>
                  	</c:forEach>
                    <input class="form-check-input updateChar" type="checkbox" id="proM${i.index }" name="tempEmpCd"
                      value="${proM.emp.empCd}" ${checked }>
                    <span class="custom-option-header pb-0">
                      <span class="fw-medium">${proM.emp.empName}</span>
                    </span>
                  </label>
                </div>
              </c:forEach>
            </c:if>
          </div>
        </div>
        <div class="row g-2">
          <div class="col mb-3">
            <label for="dobBackdrop" class="fw-medium"><i class='bx bx-file-find bx-sm mb-1'></i> 첨부파일</label>
            <input type="file" class="form-control" id="insertJobFiles" name="jobFile" multiple />
            <div id="fileList"></div>
          </div>
        </div>
        <div class="btn-group" role="group" aria-label="Basic example">
          <button type="button" onclick="fn_jobUpdate()" class="btn btn-label-primary">수정</button>
          <button type="button" data-bs-dismiss="offcanvas" class="btn btn-label-info">닫기</button>
        </div>
      </div>
    </form>
    <!--/내용-->
  </div>
</div>





<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/app/job/job.js"></script>
<script>

	const uJobBdate = $('#insertjobuSn').data('bdate');	
	const uJobEdate = $('#insertjobuSn').data('edate');	

	//초기화
	if($('#insertjobuSn').val() ==""){
	  $('#jobStcdDiv').hide();
	  $('#jobStcd').prop("disabled",true);
	  $('#ujobPeriod').hide();
	}else {
	  $('#jobStcdDiv').show();
	  $('#jobStcd').prop("disabled",false);
	  $('#ujobPeriod').show();
	  $('#uJobBdate').text(uJobBdate);
	  $('#uJobEdate').text(uJobEdate);
	} 

</script>



