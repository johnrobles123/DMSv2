<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="common.jsp" %>
<%@ include file="header.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>

<spring:url value="/user" var="addActionUrl" />

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Add User</title>
	<spring:url value="/static/css/bootstrap.css" var="bootstrapCss" />
	<spring:url value="/static/css/style.css" var="styleCss" />
	<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
	<spring:url value="/static/css/jquery-ui.css" var="jqueryuiCss" />
	
	<spring:url value="/static/js/index.js" var="mainJs" />
	<spring:url value="/static/js/grid.locale-en.js" var="gridJs" />
	<spring:url value="/static/js/jquery-3.0.0.js" var="jqueryJs" />
	<spring:url value="/static/js/jquery-ui.js" var="jqueryuiJs" />
	
	<link href="${bootstrapCss}" rel="stylesheet" />
	<link href="${styleCss}" rel="stylesheet" />
	<link href="${fontAwesomeCss}" rel="stylesheet" />
	<link href="${jqueryuiCss}" rel="stylesheet" />
	
	<script src="${mainJs}"></script>  	
	<script src="${gridJs}"></script>  	
	<script src="${jqueryJs}"></script>  	
	<script src="${jqueryuiJs}"></script> 
</head>
<body>
<div class="container">

	<font color="red">${message}</font>
	
	<h1>Enter User Information Here</h1>
	<br />

	<form:form class="form-horizontal" method="post" modelAttribute="user" action="${addActionUrl}">
		<form:hidden path="id" />
		<center>
			<table border="2" width="25%" cellpadding="5">
				
				<spring:bind path="firstName">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-3 control-label">First Name :</label>
						<div class="col-sm-8">
							<form:input path="firstName" type="text" id="firstName" class="form-control" placeholder="FirstName" required="true" />
				            <form:errors path="firstName" class="control-label" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="lastName">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-3 control-label">Last Name :</label>
						<div class="col-sm-8">
							<form:input path="lastName" type="text" id="lastName" class="form-control" placeholder="LastName" required="true" />
				            <form:errors path="lastName" class="control-label" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="email">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-3 control-label">Email :</label>
						<div class="col-sm-8">
							<form:input path="email" type="text" id="email" class="form-control" placeholder="Email" required="true" />
				            <form:errors path="email" class="control-label" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="ssoId">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-3 control-label">User Name :</label>
						<div class="col-sm-8">
							<form:input path="ssoId" type="text" id="ssoId" class="form-control" placeholder="SsoId" required="true" />
				            <form:errors path="ssoId" class="control-label" />
						</div>
					</div>
				</spring:bind>
				
				<spring:bind path="password">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<label class="col-sm-3 control-label">Password :</label>
						<div class="col-sm-8">
							<form:input path="password" type="password" class="form-control" id="password" placeholder="Password" required="true" />
					            <form:errors path="password" class="control-label" />
						</div>
					</div>
				</spring:bind>
				
				<div class="form-group ${status.error ? 'has-error' : ''}">
					<label class="col-sm-3 control-label">State :</label>
					<div class="col-sm-8">
						<select name="state" id="state" class="form-control" style="width:auto; float:left"><option>Active</option> <option>Inactive</option>  </select>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button type="submit" class="btn btn-primary pull-right" value="Submit">Submit</button>
					</div>
				</div>
				
			</table>
		</center>
	</form:form>
</div>
</body>
</html>
