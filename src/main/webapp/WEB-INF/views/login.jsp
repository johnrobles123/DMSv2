<%@include file="include.jsp"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<spring:url value="/static/css/style.css" var="styleCss" />
		<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
		<spring:url value="/static/js/index.js" var="mainJs" />
		<spring:url value="https://code.jquery.com/jquery-3.0.0.min.js" var="jqueryJs" /> 
	  	<link href="${styleCss}" rel="stylesheet" />
	  	<link href="${fontAwesomeCss}" rel="stylesheet" />
	  	<script src="${jqueryJs}"></script>
  	    <script src="${mainJs}"></script>  	
		<title>Login</title>
		<script>
		$(document).ready(function() {
		    $('form:first *:input[type!=hidden]:first').focus();
		});
		</script>
	</head>
	<body>
		<div class="login-page">
			<div class="form">
				<div class="errormsg">${message}</div>
				<c:url var="loginUrl" value="/login" />
				<form id="loginForm" method="post" action="${loginUrl}" class="login-form">
					<label path="username">Enter your user-name</label>
					<input id="username iconified" class="empty" name="ssoId" path="" placeholder="&#xf007 Username" required/><br>
					<label path="username">Please enter your password</label>
					<input type="password" id="password iconified" class="empty" name="password" path="" placeholder="&#xf084 Password" required/><br>
					<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
					<button type="submit" value="Submit" />login</button>
					</form>
				<span style="font: 15px normal; font-family: helvetica;">Not yet a member? Please sign in <a href="/DMSv2/registration">here</a></span>
			</div>
		</div>
	</body>
</html>