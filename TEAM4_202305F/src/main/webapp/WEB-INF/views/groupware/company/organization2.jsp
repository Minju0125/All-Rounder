<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>


<script src="/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.serializejson.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="/resources/js/app/util/swalUtil.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css" /> 
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" /> 
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css" />
 <link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/org/organization.css"> 

 <!-- Bootstrap CSS --> 
 <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"> 


 <!-- Popper.js -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"></script> 


<!-- Bootstrap JS -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<script>
	window.addEventListener("DOMContentLoaded", function(){
		let script = document.createElement("script");
		script.src = "https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js";
		document.body.appendChild(script);
		
		let org = document.createElement("script");
		org.src = "${pageContext.request.contextPath }/resources/js/app/org/organization.js";
		document.body.appendChild(org);
		
	})
</script>
<style>
.center {
    margin: 0 auto;
    margin-top: -50px;
}

.row {
    font-size: 15px;
}

#orgName {
    font-size: large;
    font: small-caps bold 35px/1 sans-serif;
}

.app-brand {
    background-color: #696cff; /* 적절한 배경색으로 변경 */
    padding: 10px;
    color: white;
}

.app-brand img {
    width: 50px; /* 적절한 크기로 변경 */
}

.plus_wrap {
    width: 100%;
    height: 60px;
    position: relative;
    top: 73px;
    float: right;
    left: 0%;
    margin-top: 20px; /* 적절한 여백 추가 */
}

#searchInput {
    border-color: #696cff; /* 적절한 테두리 색으로 변경 */
    border-radius: 5px; /* 적절한 테두리 둥글기 설정 */
    padding: 5px; /* 적절한 여백 추가 */
}

.ksksk {
    float: left;
    width: 300px; /* 적절한 너비로 변경 */
    height: 700px;
    background-color: white;
    border: 1px solid #ccc; /* 적절한 테두리 색으로 변경 */
    overflow: auto;
    margin-top: 20px; /* 적절한 여백 추가 */
}

#information {
    display: inline-block;
    width: 400px;
    height: 700px;
    margin-left: 10px;
    background-color: white;
    border: 1px solid #ccc; /* 적절한 테두리 색으로 변경 */
    overflow: auto;
    margin-top: 20px; /* 적절한 여백 추가 */
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}
#searchInput {
  position: absolute;
  z-index: 9999;
}
.mypageImgSize{
    width: 60%;
    scale: 80%;
    margin-top: -2%;
    margin-left: 20%;
}
</style>

<security:authentication property="principal" var="principal" />
<input type="hidden" id="schEmpCd" name="schEmpCd" value="${principal.realUser.empCd }" />
</head>
<div class="app-brand demo">
      <img src="/resources/images/minilogo.png" style="width: 10%; margin-left:10px; margin-top:5px;">
      <span class="app-brand-text demo menu-text fw-bold ms-n4" id="orgName">all-rounder</span><br />
    <i class="bx bx-chevron-left bx-sm align-middle"></i>
 </div>
<body style="overflow-x: hidden">
<div class="row">
	
	<div class="center">
		<div class="plus_wrap" style="left: 2%;">
			<div>
				<input type="text" id="searchInput" placeholder="검색" >
			</div>
			
		</div>
		
		<div id="kt_docs_jstree_basic" class="ksksk">
	
		</div>
		
	
		<div id="information" class="card mb-4">
			<div class="card-body"></div>
		</div>
	</div>
</div>



</body>


