<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<spring:url value="/viewuser" var="addActionUrl" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display Reservation</title>
  <spring:url value="/static/css/style.css" var="styleCss" />
		<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
		<spring:url value="/static/js/index.js" var="mainJs" />
	    <script type="text/javascript" src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
	  	<link href="${styleCss}" rel="stylesheet" />
	  	<link href="${fontAwesomeCss}" rel="stylesheet" />
  	  	<script src="${mainJs}"></script>
</head>
<body>
	<div class="container">
		<font color="red">${message}</font>
		<form:form class="form-horizontal" method="get" modelAttribute="user" action="${addActionUrl}">
		    <table>
		    <tbody>
		    	<tr>
				<td>First Name :</td> <td>${user.firstName}</td>
				</tr>
		 	</tbody>
			<tbody>
				<tr>                              
				<td>Last Name :</td> <td>${user.lastName}</td>
			    </tr>
			</tbody>
			<tbody>
			    <tr>
			    <td>Email :</td>  <td>${user.email}</td>
			    </tr>
		        <tr>      
		        <td>User Name :</td><td>${user.ssoId}</td>
		        </tr>
		
		    </tbody>
		    
		    </table>
		</form:form>
	</div>
</body>
</html>
