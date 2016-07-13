<%@ include file="../common.jsp"%>
<%@ include file="../header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html">
	<!-- script type="text/javascript">
	
		function goBack() {
		    window.history.back()
		} 
	
	</script--> 

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

	<c:if test="${not empty msg}">
		<div class="alert alert-${css} alert-dismissible" role="alert">
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
			<strong>${msg}</strong>
		</div>
	</c:if>	

	<h1>Device Detail</h1>
	<br />
	<form id="deviceForm" class="form-horizontal">
	<spring:url value="/admin" var="adminUrl" />
	<!-- <button class="btn btn-primary" onclick="goBack()">Go Back</button>  -->
	<br />
	<div class="form-group">
		<label class="col-sm-3 control-label" style="padding-top: 0;">Device Serial Numer:</label>
		<div class="col-sm-8">${device.serialNo}</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label" style="padding-top: 0;">Device Name:</label>
		<div class="col-sm-8">${device.deviceName}</div>
	</div>

	<div class="form-group">
		<label class="col-sm-3 control-label" style="padding-top: 0;">Additional Information</label>
		<div class="col-sm-8">${device.additionalInfo}</div>
	</div>
	</form>
</div>

</body>
</html>