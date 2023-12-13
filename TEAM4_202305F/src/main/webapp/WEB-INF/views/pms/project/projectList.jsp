<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
 
<%--
* [[개정이력(Modification Information)]]
* 수정일                 수정자      수정내용
* ----------  ---------  -----------------
* 2023. 11. 10    송석원      최초작성 
* 2023. 11. 14    송석원      리더만 변경가능한 제약 생성
* 2023. 11. 17    송석원      완료로갈때의 제약 생성
* Copyright (c) ${year} by DDIT All right reserved
 --%>


<div class="container">
	<div class="dropzone" data-pro-sttus="1" data-add-bg="bg-primary">
		<div class="kanban-header col-md-6 card  text-white mb-3" style="width: 200px; height: 50px; background-color: rgba(106,108,246); border-radius: 5px; display: flex; align-items: center; justify-content: center;">
            진행
        </div>
		<c:if test="${not empty proj}">
			<c:forEach items="${proj}" var="proj" varStatus="vs">
				<c:if test="${proj.proSttus == 1}">
					<div id="project1_${vs.count}" class="draggable  col-md-6   card bg-primary text-white mb-3"
						draggable="true" data-pro-sn="${proj.proSn }">
						<div class="card-header">프로젝트</div>
						<div class="card-body">
							<h3 class="card-title text-white">
							    <a href="<c:url value='/job/${proj.proSn}/home'/>" style="color: white;" draggable="false">
							        <c:choose>
							            <c:when test="${fn:length(proj.proNm) > 8}">
							                ${fn:substring(proj.proNm, 0, 8)}...
							            </c:when>
							            <c:otherwise>
							                ${proj.proNm}
							            </c:otherwise>
							        </c:choose>
							    </a>
							</h3> 

							<p class="card-text">시작일:${proj.proBdate}</p> 
						</div>
					</div>
				</c:if>
			</c:forEach> 
		</c:if>
	</div>
 

	<div class="dropzone  borderline" data-pro-sttus="2" data-add-bg="bg-warning">
		
        <div class="kanban-header col-md-6 card  text-white mb-3" style="width: 200px; height: 50px; background-color: orange; border-radius: 5px; display: flex; align-items: center; justify-content: center;">
            보류
        </div>
    

		<c:if test="${not empty proj}">
			<c:forEach items="${proj}" var="proj" varStatus="vs">
				<c:if test="${proj.proSttus == 2}">
					<div id="project2_${vs.count}" class="draggable  col-md-6   card bg-warning text-white mb-3"
						draggable="true" data-pro-sn="${proj.proSn }">
						<div class="card-header">프로젝트</div>
						<div class="card-body">
							<h3 class="card-title text-white">
							    <a href="<c:url value='/job/${proj.proSn}/home'/>" style="color: white;" draggable="false">
							        <c:choose>
							            <c:when test="${fn:length(proj.proNm) > 8}">
							                ${fn:substring(proj.proNm, 0, 8)}...
							            </c:when>
							            <c:otherwise>
							                ${proj.proNm}
							            </c:otherwise>
							        </c:choose>
							    </a>
							</h3>
							<p class="card-text">생성일:${proj.proBdate}</p>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</c:if>
	</div>
 
	<div class="dropzone  borderline" data-pro-sttus="3" data-add-bg="bg-success">
		<div class="kanban-header col-md-6 card  text-white mb-3" style="width: 200px; height: 50px; background-color: rgb(116,216,58); border-radius: 5px; display: flex; align-items: center; justify-content: center;">
            완료
        </div>

		<c:if test="${not empty proj}">
			<c:forEach items="${proj}" var="proj" varStatus="vs">
				<c:if test="${proj.proSttus == 3}">
					<div id="project3_${vs.count}" class="draggable  col-md-6   card bg-success text-white mb-3"
						draggable="true" data-pro-sn="${proj.proSn }">
						<div class="card-header">프로젝트</div> 
						<div class="card-body">
							<h3 class="card-title text-white">
							    <a href="<c:url value='/job/${proj.proSn}/home'/>" style="color: white;" draggable="false">
							        <c:choose>
							            <c:when test="${fn:length(proj.proNm) > 8}">
							                ${fn:substring(proj.proNm, 0, 8)}...
							            </c:when>
							            <c:otherwise>
							                ${proj.proNm}
							            </c:otherwise>
							        </c:choose>
							    </a>
							</h3>
							<p class="card-text">생성일:${proj.proBdate}</p>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</c:if>
	</div>
	

  
</div>



<script>
let dragged;

/* 드래그 가능한 대상에서 발생하는 이벤트 */
document.addEventListener("drag", (event) => {
  console.log("dragging");
});

document.addEventListener("dragstart", (event) => {
  // 드래그한 요소에 대한 참조 저장
  dragged = event.target;
  // 반투명하게 만들기
  event.target.classList.add("dragging");
  
  const proSn = event.target.dataset.proSn;//추가
  event.dataTransfer.setData("proSnkey",proSn);
  //porSnkey에 proSn의 정보를 저장한다.=>보드에서 보드로 이동하면서 값이 바뀌기때문에 
  //dataTransfer을 이용해 데이터를 저장한거임!
  const proSttus = event.target.getAttribute("data-proSttus");//추가
  event.dataTransfer.setData("proSttuskey",proSttus);//이하동문
});

document.addEventListener("dragend", (event) => {
  // 투명도 초기화
  event.target.classList.remove("dragging");
});

/* 드롭 대상에서 발생하는 이벤트 */
document.addEventListener(
  "dragover",
  (event) => {
    // 드롭을 허용하기 위해 기본 동작 취소
    event.preventDefault();
  },
  false,
);

document.addEventListener("dragenter", (event) => {
  // 드래그 가능한 요소가 대상 위로 오면 강조
  if (event.target.classList.contains("dropzone")) {
    event.target.classList.add("dragover");
  }
});

document.addEventListener("dragleave", (event) => {
  // 드래그 가능한 요소가 대상 밖으로 나가면 강조 제거
  if (event.target.classList.contains("dropzone")) {
    event.target.classList.remove("dragover");
  }
});

let dropElements = document.querySelectorAll(".dropzone");
let bgArray=['bg-success', 'bg-warning', 'bg-primary']

dropElements.forEach(el=>{
 	el.addEventListener("drop",event=>{
 		console.log("ffffffffffffff");
 		 // 일부 요소의 링크 열기와 같은 기본 동작 취소
 		  event.preventDefault();
 		  // 드래그한 요소를 선택한 드롭 대상으로 이동
 		  if (event.target.classList.contains("dropzone")) {
 		    event.target.classList.remove("dragover");
 		    
 		    const proSn = event.dataTransfer.getData("proSnkey")
 		    const proSttus = event.target.dataset.proSttus//추가 
 		    
 		   Swal.fire({
 			  title: '프로젝트의 상태를 변경하시겠습니까?',
 			  icon: 'question',
 			  showCancelButton: true,
 			  confirmButtonColor: '#3085d6',
 			  cancelButtonColor: '#d33',
 			  confirmButtonText: '승인',
 			  cancelButtonText: '취소',
 			  reverseButtons: false // 버튼 순서 거꾸로
 			}).then(result => {
 				// 만약 모달창에서 confirm 버튼을 눌렀다면
 				if (result.isConfirmed) {
 			    // AJAX 요청 시작
 			    $.ajax({
 			      type: "PUT",
 			      url: "/pms/project",
 			      contentType: "application/json",
 			      dataType: "json",
 			      data: JSON.stringify({ proSn: proSn, proSttus: proSttus }), // 추가
 			      success: function (data) {
 			        if (data.result === "OK") {
 			          console.log("성공");
 			          dragged.parentNode.removeChild(dragged);
 			          event.target.appendChild(dragged);

 			          bgArray.forEach(b => { // 백그라운 컬러 색 변경을 위한 기존 컬러 제거 
 			            dragged.classList.remove(b);
 			          });

 			          dragged.classList.add(event.target.dataset.addBg); // 드랍시 새로운컬러 적용
 			          Swal.fire('변경되었습니다.', '', 'success');
 			        } else {
 			        	//완료되지 못한 일감이 있는 프로젝트가 완료로 갈경우 실패 
 			        	 Swal.fire({
 		 			          icon: 'warning',
 		 			          title: '완료되지 않은 일감이 있습니다!',
 		 			        });
 			        }
 			      },
 			      error: function (xhr) {
 			        console.log("실패");
 			       Swal.fire({
 			          icon: 'error',
 			          title: '변경 권한이 없습니다!',
 			        });
 			      } 
 			    });
 			  }
 			});


 		   

 		    
 
 		    
 		  }
 	});
});

</script>




<style>
 
body {
	/* 사용자가 예제의 텍스트를 선택하지 못하도록 */
	user-select: none;
}

.container {
	display: flex;
	justify-content: center;
	height: 100vh;
}

.draggable {
	background: white;
	/*   border: 1px solid black; */
	width: 200px;
	height: 80px;
	margin-top: 30px;
}

.dropzone {
	width: 500px;
	background: blueviolet;
	padding: 10px;
	padding-left: 100px;
	display: inline-block;
	background-color: rgba(255, 0, 0, 0);
}

.dropzone.dragover {
	/*   background-color: purple; */
	background-color: rgba(0, 0, 200, 0.1);
}

.dragging {
	opacity: 0.5;
}

.kanban-header {
	color: black;
	font-size: 18pt;
}

.borderline {
	border-left: 1px solid #d7d7d7; /* 1px 두께의 선, 색상은 #000 (검은색) */
	margin: 0 10px; /* 왼쪽과 오른쪽에 간격을 줍니다. */
	padding: 10px; /* 구분선과 내용 사이에 간격을 줍니다. */
	padding-left: 100px;
}


.card-body {
    font-size: 9px; 
    padding: 0px 25px;
}

a {
    font-size: 17px;
}

.card-header {
    padding: 7px 25px;
    }
    
h3, .h3 { 
    font-size: 0rem;
}

.card-title {
    margin-bottom: 1px;
}
</style>
