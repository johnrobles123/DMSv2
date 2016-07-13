<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false"%>
<%@ include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<spring:url value="/registration" var="actionUrl" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
		<spring:url value="/static/css/style.css" var="styleCss" />
		<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
		<spring:url value="/static/css/bootstrap.css" var="bootstrapCss" />
		<spring:url value="/static/css/jquery-ui.css" var="jqueryUICss" />
		<spring:url value="/static/js/index.js" var="mainJs" />
		<spring:url value="/static/js/jquery-3.0.0.js" var="jqueryJs" /> 
		<spring:url value="/static/js/jquery-ui.js" var="jqueryUIJs" /> 
	  	<link href="${styleCss}" rel="stylesheet" />
	  	<link href="${fontAwesomeCss}" rel="stylesheet" />
		<link href="${bootstrapCss}" rel="stylesheet" />
	  	<link href="${jqueryUICss}" rel="stylesheet" />
	    <script src="${mainJs}"></script>
	  	<script src="${jqueryJs}"></script>
	  	<script src="${jqueryUIJs}"></script>
</head>
<body>
	<div id="header">
		<h1 style="display: inline-block; margin-right: -66px;">Device Monitoring System</h1>
		</div>
		<div class="login-page" style="width:70%; padding-top: calc(8% - 74px)">
		<div class="form" style="padding: 20px; max-width: 1000px;">
		<%-- <c:url var="loginUrl" value="/login" /> --%>
		<form:form class="form-horizontal" method="post" modelAttribute="user" action="${actionUrl}">
		
					<div class="errormsg">${message}</div>
					<h3 style="margin-top:5px">Enter User Information Here</h3>
					
			<table width="100%">
				<tr>
					<td>First Name :</td>
					<td><spring:bind path="firstName">
							<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-bottom: 0">
								<div class="col-sm-12">
									<form:input path="firstName" type="text" id="firstName" size="35" maxlength="50" placeholder="FirstName" required="true" style="padding: 7px; margin: 10px 5px"/>
				            <form:errors path="firstName" class="control-label" />
						</div>
					</div>
				    </spring:bind>
					</td>
				</tr>
				<tr>
					<td>Last Name :</td>
					<td><spring:bind path="lastName">
							<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-bottom: 0">
								<div class="col-sm-12">
									<form:input path="lastName" type="text" id="lastName"  size="35" maxlength="50"  placeholder="LastName" required="true" style="padding: 7px; margin: 10px 5px"/>
				            <form:errors path="lastName" class="control-label" />
						</div>
					</div>
				    </spring:bind>
					</td>
				</tr>
				<tr>
					<td>Email :</td>
					<td>
						<spring:bind path="email">
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-bottom: 0">
									<div class="col-sm-12">
										<form:input path="email" type="text" id="email"  size="35" maxlength="50"  placeholder="Email" required="true" style="padding: 7px; margin: 10px 5px"/>
					            <form:errors path="email" class="control-label" />
							</div>
						</div>
					    </spring:bind>
				    </td>
				</tr>
				<tr>
					<td>User Name :</td>
					<td>
						<spring:bind path="ssoId">
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-bottom: 0">
									<div class="col-sm-12">
										<form:input path="ssoId" type="text" id="ssoId"  size="50" maxlength="50"  placeholder="User Name" required="true" style="padding: 7px; margin: 10px 5px"/>
					            <form:errors path="ssoId" class="control-label" />
							</div>
						</div>
					    </spring:bind>
				    </td>
				</tr>
				<tr>
					<td>Password :</td>
					<td>
						<spring:bind path="password">
								<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-bottom: 0">
								<div class="col-sm-12">
									<form:input path="password" type="password" id="password"  size="35" maxlength="50"  placeholder="Password" required="true" style="padding: 7px; margin: 10px 5px"/>
				            <form:errors path="password" class="control-label" />
						</div>
						</div>
					    </spring:bind>
					</td>
				</tr>
				<tr>
					<td>Confirm Password :</td>
					<td>
						<spring:bind path="confirmPassword">
							<div class="form-group ${status.error ? 'has-error' : ''}" style="margin-bottom: 0">
								<div class="col-sm-12">
									<form:input path="confirmPassword" type="password" name="confirmPassword" id="confirmPassword" required="true" size="35" maxlength="50" style="padding: 7px; margin: 10px 5px"/>
									<form:errors path="confirmPassword" class="control-label" />
								</div>
							</div>
						</spring:bind>
					</td>
				</tr>
						<tr><td>
							<button type="submit" value="Submit" style="margin-left: 150%;"/>submit</button>
						</td></tr>
						<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
			</table>
		</form:form>
	</div>
	</div>
</body>
</html>
