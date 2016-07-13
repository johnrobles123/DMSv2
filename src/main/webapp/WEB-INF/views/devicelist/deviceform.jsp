<%@ include file="../common.jsp"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Device List</title>
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

	<c:choose>
		<c:when test="${deviceForm['new']}">
			<h1>Add Device</h1>
		</c:when>
		<c:otherwise>
			<h1>Update Device</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<spring:url value="/devicelist" var="deviceActionUrl" />

	<form:form class="form-horizontal" method="post" modelAttribute="deviceForm" action="${deviceActionUrl}">

		<spring:bind path="serialNo">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-3 control-label">Serial No:</label>
				<div class="col-sm-8">
					<form:input path="serialNo" type="text" class="form-control " id="serialNo" placeholder="serialNo" />
					<form:errors path="serialNo" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="deviceName">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-3 control-label">Device Name</label>
				<div class="col-sm-8">
					<form:input path="deviceName" class="form-control" id="deviceName" placeholder="deviceName" />
					<form:errors path="deviceName" class="control-label" />
				</div>
			</div>
		</spring:bind>

		<spring:bind path="additionalInfo">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<label class="col-sm-3 control-label">Additional Information</label>
				<div class="col-sm-8">
					<form:textarea path="additionalInfo" rows="5" class="form-control" id="additionalInfo" placeholder="additionalInfo" />
					<form:errors path="additionalInfo" class="control-label" />
				</div>
			</div>
		</spring:bind>


		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${deviceForm['new']}">
						<button type="submit" class="btn btn-primary pull-right">Add</button>
					</c:when>
					<c:otherwise>
						<button type="submit" class="btn btn-primary pull-right">Update</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>

</div>

</body>
</html>