<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<security:csrfInput/>
<security:authorize access="isAuthenticated()">
  <security:authentication property="principal" var="realUser" />
</security:authorize>
<h4>안녕하세요  ${realUser.username } 님 !</h4>