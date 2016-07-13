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

	<script type="text/javascript">
	
	function deleteDevice(serialNo) {
		
	        	$.ajax({
	                url : '/DMSv2/devicelist/' + serialNo + '/delete',
	                type: 'DELETE',
	                success : function(data) {
	                }
	            });
	
	}
	
	</script> 
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

		<h1>All Devices</h1>

		<spring:url value="/devicelist/${device.serialNo}" var="userUrl" />
		<spring:url value="/devicelist/add" var="addUrl" /> 
		<button class="btn btn-primary" onclick="location.href='${addUrl}'">Add</button>

		<table class="table table-striped" border='1'>
			<thead>
				<tr>
					<th>Serial_No</th>
					<th>Device_Name</th>
					<th>Additional_Info</th>
					<th>Action</th>
				</tr>
			</thead>

			<c:forEach var="device" items="${deviceList}">
				<tr>
					<td>
						${device.serialNo}
					</td>
					<td>${device.deviceName}</td>
					<td>${device.additionalInfo}</td>
					<td>
						<spring:url value="/devicelist/${device.serialNo}" var="userUrl" />
						<spring:url value="/devicelist/add" var="addUrl" /> 
						<spring:url value="/devicelist/${device.serialNo}/delete" var="deleteUrl" /> 
						<spring:url value="/devicelist/${device.serialNo}/update" var="updateUrl" />
					
						<button class="btn btn-info" onclick="location.href='${userUrl}'">Query</button>
						<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
						<button id="deleteBtn" class="btn btn-danger" onclick="deleteDevice('${device.serialNo}')">Delete</button></td>
				</tr>
			</c:forEach>
		</table>

	</div>

</body>
</html>