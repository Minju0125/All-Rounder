<%--
* [[개정이력(Modification Information)]]
* 수정일           수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 11.  전수진      최초작성
* Copyright (c) ${year} by DDIT All right reserved
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>  
<script src="<c:url value='/resources/js/ckeditor/ckeditor.js'/>"></script>
<style>
	#drop {
	    height:200px;
	}
	
	.dropBox {
		height: 150px;
	   
		overflow: auto;
	  
		display: flex;
		justify-content: center;
		align-items: center;
	
		border-radius: 5px;
		border: 4px dashed #ddd;
		user-select: none;
		transition: 0.4s;
	}
</style>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3">
	<div class="d-flex flex-column justify-content-center">
		<h4 class="mb-1 mt-3">공지사항 등록</h4>
		<p class="text-muted">Add a New Notice Board</p>
	</div>
</div>

<div class="card-body">
 
<form:form  enctype="multipart/form-data" modelAttribute="boardVO">
	<div class="mb-3">	
		<label class="form-label" for="bbsSj">제목</label>
		<form:input type="text" path="bbsSj" class="form-control" required="true" placeholder="제목을 입력하세요!" />
		<form:errors path="bbsSj" element="span" cssClass="error" />
		
	</div>			
	<div class="mb-3">	
		<label class="form-label" for="bbsCn">내용</label>
		<form:textarea path="bbsCn" class="form-control" required="true" />
		<form:errors path="bbsCn" element="span" cssClass="error" />
	</div>
	<div class="mb-3">
		<label class="form-label">첨부파일</label>
		<input type="file" class="form-control" name ="boFile" />
	</div>

	<div class="mb-3">
		<label class="form-label" for="noticeTmlmt">공지마감일자</label>
		<input name="noticeTmlmt" id="noticeTmlmt" class="form-control" value="${bbs.noticeTmlmt}" placeholder="공지마감일자 선택"/>
		<span class="error">${errors.noticeTmlmt}</span>
	</div>

	<div class="mb-3">
	    <input type="checkbox" class="form-check-input" name="noiceMustRead" id="noiceMustRead" />  <label class="form-label">상단고정하기</label>
	</div>
	<button type="submit" class="btn btn-primary me-1" id="addBtn">등록</button>
	<button type="reset" class="btn btn-label-secondary me-1" id="reset">취소</button>
	<button type="button" class="btn btn-label-secondary me-1" onclick="location.href='<c:url value='/notice' />'">목록</button>
</form:form>
</div>

<script>
 	let csrfparam = $("meta[name='_csrf_parameter']").attr("content");
 	let csrf = $("meta[name='_csrf']").attr("content");
 	
    CKEDITOR.replace('bbsCn',{
        filebrowserImageUploadUrl:`<c:url value='/notice/image?type=image'/>&\${csrfparam}=\${csrf}`
    });
    
    $("input:checkbox").click(function() {
        if (this.checked) {
            $(this).val('Y');
        } else {
            $(this).val('N');
        }
    });
    
    
</script>


<script type="text/javascript">
$(document).ready(function() {
	$("#noticeTmlmt").datepicker({
		format: "yyyy-mm-dd",	//데이터 포맷 형식(yyyy : 년 mm : 월 dd : 일 )
		startDate: '0d',	//달력에서 선택 할 수 있는 가장 빠른 날짜. 이전으로는 선택 불가능 ( d : 일 m : 달 y : 년 w : 주)
		autoclose: true,	//사용자가 날짜를 클릭하면 자동 캘린더가 닫히는 옵션
		calendarWeeks: false, //캘린더 옆에 몇 주차인지 보여주는 옵션 기본값 false 보여주려면 true
		clearBtn: false, //날짜 선택한 값 초기화 해주는 버튼 보여주는 옵션 기본값 false 보여주려면 true
		datesDisabled: ['2019-06-24', '2019-06-26'],//선택 불가능한 일 설정 하는 배열 위에 있는 format 과 형식이 같아야함.
		daysOfWeekDisabled: [0, 6],	//선택 불가능한 요일 설정 0 : 일요일 ~ 6 : 토요일
		daysOfWeekHighlighted: [3], //강조 되어야 하는 요일 설정
		// disableTouchKeyboard : false,	//모바일에서 플러그인 작동 여부 기본값 false 가 작동 true가 작동 안함.
		immediateUpdates: false,	//사용자가 보는 화면으로 바로바로 날짜를 변경할지 여부 기본값 :false 
		multidate: false, //여러 날짜 선택할 수 있게 하는 옵션 기본값 :false 
		multidateSeparator: ",", //여러 날짜를 선택했을 때 사이에 나타나는 글짜 2019-05-01,2019-06-01
		templates: {
			leftArrow: '&laquo;',
			rightArrow: '&raquo;'
		}, //다음달 이전달로 넘어가는 화살표 모양 커스텀 마이징 
		showWeekDays: true,// 위에 요일 보여주는 옵션 기본값 : true
		title: "사용일자",	//캘린더 상단에 보여주는 타이틀
		todayHighlight: true,	//오늘 날짜에 하이라이팅 기능 기본값 :false 
		toggleActive: true,	//이미 선택된 날짜 선택하면 기본값 : false인경우 그대로 유지 true인 경우 날짜 삭제
		weekStart: 0,//달력 시작 요일 선택하는 것 기본값은 0인 일요일 
		language: "ko"	//달력의 언어 선택, 그에 맞는 js로 교체해줘야한다.
	});
	
});



</script>