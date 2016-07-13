<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<spring:url value="/static/css/bootstrap.css" var="bootstrapCss" />	
	<spring:url value="/static/css/style.css" var="styleCss" />
	<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
	<link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/base/jquery-ui.css">
	<spring:url value="/static/js/index.js" var="mainJs" />
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
	<link href="${bootstrapCss}" rel="stylesheet" />
	<link href="${styleCss}" rel="stylesheet" />
	<link href="${fontAwesomeCss}" rel="stylesheet" />
	<script src="${mainJs}"></script>
</head>
<body>

	<div id="header">
		<h1 style="display: inline-block; margin-right: -66px;">Device Monitoring System</h1>
		<div id="welcome">
			<p style="margin:0">Welcome ${user}</p>
			<a id="iconified" style="font-family: FontAwesome;" href="/DMSv2/logout">Logout &#xf011;</a>
		</div>
	</div>
	<ul class="nav nav-pills nav-justified">
	    <li><a href="/DMSv2/dashboard">Home</a></li>
	    <li><a class="hsubs" href = "/DMSv2/dashboard/${user}/myreservation">My Reservations</a></li>
	    <li><a class="hsubs" href = "/DMSv2/admin">Admin</a></li>
	    <li><a class="hsubs" href = "/DMSv2/about">About</a></li>
	</ul>
</body>
</html>