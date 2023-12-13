<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script src="/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.serializejson.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script> 
<script src="/resources/js/app/util/swalUtil.js"></script>
    <script src="/resources/assets/vendor/libs/moment/moment.js"></script>
<script>

	const CSRFPARAMNAME = $("meta[name='_csrf_parameter']").attr("content");
	const CSRFHEADERNAME = $("meta[name='_csrf_header']").attr("content");
	const CSRFTOKEN = $("meta[name='_csrf']").attr("content");
	
	$.ajaxSetup({
		headers : {
			[CSRFHEADERNAME] : CSRFTOKEN
		}
	});
</script>

<c:if test="${not empty message }">
<script>
	alert("${message}");
</script>
</c:if> 