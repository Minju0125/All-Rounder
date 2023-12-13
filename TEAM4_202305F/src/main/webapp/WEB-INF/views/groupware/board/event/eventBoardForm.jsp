<%--
* [[개정이력(Modification Information)]]
* 수정일       수정자        수정내용
* ----------  ---------  -----------------
* 2023. 11. 20.      김보영        최초작성
* 2023. 11. 25.      김보영        경조사 등록폼
* Copyright (c) 2023 by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=057358babbeb10048427ddd4e876ed8a&libraries=services"></script>
<script src="<c:url value='/resources/js/ckeditor/ckeditor.js'/>"></script>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
  <div class="d-flex flex-column justify-content-center">
    <h4 class="mb-1 mt-3">경조사게시판등록</h4>
    <p class="text-muted">Add a New Event Board</p>
  </div>
</div>

<div class="card-body">

  <form enctype="multipart/form-data"  id="formEvent" class="fv-plugins-bootstrap5 fv-plugins-framework">
  <security:csrfInput />
    <div class="row g-2">
      <div class="col-md-8 col-lg-8 mb-3">
        <label for="bbsSj" class="fw-medium"><i class="bx bx-calendar-edit bx-sm mb-1"></i> 제목</label>
        <div class="input-group has-validation" >
	        <input type="text" name="bbsSj"  id="bbsSj" class="form-control"  value="" />
        </div>
      </div>
      <div class="col-md-4 col-lg-4  mb-3">
        <label for="eventSttus" class="fw-medium"><i class="bx bx-calendar-exclamation bx-sm mb-1"></i>
          구분</label>
         <div class="input-group has-validation" >
	        <select name="eventSttus" name="eventSttus" id="eventSttus" class="form-select" required>
	          <option value="">(선택)</option>
	          <option value="1" >결혼/출산</option>
	          <option value="2" >사망</option>
	          <option value="3" >생일</option>
	          <option value="4" >기타</option>
	        </select>
        </div>  
      </div>
    </div>
    <div class="row g-2">
      <div class="col-md-5 col-lg-5 mb-3">
        <label for="dobBackdrop" class="fw-medium"><i class="bx bx-calendar-edit bx-sm mb-1"></i> 시작일</label>
        	<input name="eventBdate" type="date" id="eventBdate" value="" class="form-control"  />
      </div>
      <div class="col-md-2 col-lg-2 mb-3" style="padding-top: 33px; padding-left: 20px;">
       <label for="dobBackdrop" class="fw-medium"><i class="bx bx-calendar-exclamation bx-sm mb-1"></i>종일</label>
        <input type="checkbox" class="form-check-input" name="allDay" id="allDay" />
      </div>
      <div class="col-md-5 col-lg-5 mb-3">
        <label for="eventEdate" class="fw-medium"><i class="bx bx-calendar-exclamation bx-sm mb-1"></i>
          종료일</label>
             <input type="date" id="eventEdate" name="eventEdate" class="form-control rounded-1"  value="">
      </div>
    </div>
    <div class="mb-3">
      <label for="dobBackdrop" class="fw-medium"><i class="bx bx-calendar-exclamation bx-sm mb-1"></i>
        내용</label>
        <textarea name="bbsCn" id="bbsCn" rows="5" cols="10"  class="form-control"></textarea>
    </div>
    <div class="row g-2">
      <div class="col mb-3">
        <label for="dobBackdrop" class="fw-medium"><i class="bx bx-calendar-edit bx-sm mb-1"></i> 주소</label>
        <input name="eventLoc" type="text" id="eventLoc" class="form-control" />
      </div>
      <div class="col mb-3">
        <button type="button" class="btn btn-primary" onclick="execDaumPostcode()" style="margin-top: 29px;">주소검색</button>
      </div>
    </div>

    <input name="bbsEquator" type="hidden" id="bbsEquator" class="form-control" />
    <input name="bbsLatitude" type="hidden" id="bbsLatitude" class="form-control" />

    <label for="dobBackdrop" class="fw-medium"><i class="bx bx-calendar-exclamation bx-sm mb-1"></i>
      위치</label>
    <div class="card mb-3">
      <div class="card-body" id="map" style="width:100%; height:400px;">
      </div>
    </div>

    <button type="button" class="btn btn-primary me-1" id="addBtn" onclick="fn_insertEvent()">등록</button>
    <button type="button" class="btn btn-label-secondary me-1"
      onclick="location.href='<c:url value='/event/home' />'">목록</button>
    <button type="button" class="btn btn-label-warning" onclick="fillDummyData()">시연용</button>
  </form>
</div>



<script src="${pageContext.request.contextPath}/resources/js/app/board/event.js"></script>


<script>


function fillDummyData() {
    // 각 입력 태그에 더미값 설정
    document.getElementById('bbsSj').value = '305호 최종 프로젝트 발표회 ';
    document.getElementById('eventSttus').value = '4';
    document.getElementById('eventBdate').value = '2023-12-14';
    document.getElementById('eventLoc').value = '대전 중구 계룡로 846';
}
	

  //카카오 지도 api
  var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
  var options = { //지도를 생성할 때 필요한 기본 옵션
    center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
    level: 3 //지도의 레벨(확대, 축소 정도)
  };

  //지도를 미리 생성
  var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

  //마커를 미리 생성
  var marker = new daum.maps.Marker({
    position: new daum.maps.LatLng(37.537187, 127.005476),
    map: map
  });

  function execDaumPostcode() {
    new daum.Postcode({
      oncomplete: function (data) {
        var addr = data.address; // 최종 주소 변수

        // 주소 정보를 해당 필드에 넣는다.
        document.getElementById("eventLoc").value = addr;
        // 주소로 상세 정보를 검색

        var geocoder = new daum.maps.services.Geocoder();

        geocoder.addressSearch(data.address, function (results, status) {
          // 정상적으로 검색이 완료됐으면
          if (status === daum.maps.services.Status.OK) {

            var result = results[0]; //첫번째 결과의 값을 활용
            //여기서 위도 경도 먼저 빼서 전역번수 안에 저장해
            // 해당 주소에 대한 좌표를 받아서
            var coords = new daum.maps.LatLng(result.y, result.x);
            // 지도를 보여준다.
            document.getElementById('bbsEquator').value = result.y;
            document.getElementById('bbsLatitude').value = result.x;

            //mapContainer.style.display = "block";
            map.relayout();
            // 지도 중심을 변경한다.
            map.setCenter(coords);
            // 마커를 결과값으로 받은 위치로 옮긴다.
            marker.setPosition(coords)
          }
        });
      }
    }).open();
  }

  
//-----------------------------------------------

  // 종일 체크 박스를 클릭했을 때 시작일과 종료일을 동기화하는 함수
   function syncAllDay() {
     var allDayCheckbox = document.getElementById('allDay');
     var startDateInput = document.getElementById('eventBdate');
     var endDateInput = document.getElementById('eventEdate');

     // 종일 체크 박스가 체크되었을 때
     if (allDayCheckbox.checked) {
       // 시작일과 종료일을 동일하게 설정
       endDateInput.value = startDateInput.value;
     }
   }

   // 종일 체크 박스의 상태에 따라 이벤트를 추가
   document.getElementById('allDay').addEventListener('change', syncAllDay);

   let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
	let csrf = $("meta[name='_csrf']").attr("content");

  
  CKEDITOR.replace('bbsCn',{
      filebrowserImageUploadUrl:`<c:url value='/event/image?type=image'/>&\${csrfparam}=\${csrf}`
  });
</script>