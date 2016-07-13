<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="common.jsp" %>
<%@ include file="header.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
	<head>
	  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	  <title>Administrator</title>
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
		
	    <meta name="_csrf" content="${_csrf.token}"/>
	    <!-- default header name is X-CSRF-TOKEN -->
	    <meta name="_csrf_header" content="${_csrf.headerName}"/>
	</head>
	 
	 <body>		
		<font color="red">${message}</font>
		
        <div id="confirmDeleteDialogue" style="display:none">Are you sure you want to delete the record?</div>

		<div class="container">
		
			<c:if test="${not empty msg}">
				<div class="alert alert-${css} alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<strong>${msg}</strong>
				</div>
			</c:if>
			<h1>Administration</h1>
			
			<h2>All Devices</h2>

			<spring:url value="/devicelist/${device.serialNo}" var="userUrl" />
			<spring:url value="/devicelist/add" var="addUrl" /> 
			<button class="btn btn-default" id="addDevice" onclick="location.href='${addUrl}'">Add New Device</button>

			<div class="datagrid">
				<table id="deviceList">
					<thead>
						<tr>
							<th>Serial No</th>
							<th>Device Name</th>
							<th>Additional Info</th>
							<th>Action</th>
						</tr>
					</thead>
					
					<tbody>
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
								
									<button class="btn btn-info" onclick="location.href='${userUrl}'">Details</button>
									<button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
									<button id="deleteBtn" class="btn btn-danger" onclick="confirmDelete('${deleteUrl}')">Delete</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div id="pageNavPosition" align="center"></div>
		    <script type="text/javascript">
	            var pager = new Pager("deviceList", 5);  
	            pager.init();  
	            pager.showPageNav('pager', 'pageNavPosition');  
	            pager.showPage(1);
		    </script>
		    
			<br>
			<h2>All Users</h2>
			
			<spring:url value="/user" var="addUserUrl" /> 
			<button class="btn btn-default" id="addUser" onclick="location.href='${addUserUrl}'">Add New User</button>			
			<div class="datagrid">
				<table id="userDataList">
					<thead>
						<tr>
							<th>ID</th>
							<th>User ID</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="user" items="${userList}">
						<tr>
							<td>${user.id}</td>
							<td>${user.ssoId}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>
								<spring:url value="/viewuser/${user.id}" var="userQueryUrl" />
								<spring:url value="/user/${user.id}" var="userUpdateUrl" />
								<spring:url value="/user/${user.id}/delete" var="userDeleteUrl" /> 
		
								<button class="btn btn-info" onclick="location.href='${userQueryUrl}'">Details</button>
								<button class="btn btn-primary" onclick="location.href='${userUpdateUrl}'">Update</button>
								<button class="btn btn-danger" onclick="confirmDelete('${userDeleteUrl}')">Delete</button>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div id="pageNavPosition2" align="center"></div>
		    <script type="text/javascript">
	            var pager2 = new Pager2("userDataList", 5);  
	            pager2.init();  
	            pager2.showPageNav2('pager2', 'pageNavPosition2');  
	            pager2.showPage2(1);
		    </script>
		    <br/>

		</div>
		
	    <!-- include you jquery ui theme -->
        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" />
		<!-- include the jquery library -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<!-- include the jquery ui library -->
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
		
		    <script type="text/javascript">
			function confirmDelete(_url) {
				$("#confirmDeleteDialogue").dialog({
				modal: true,
				title: "Confirmation",
				resizable: false,
				buttons: {
						"YES": function() {
							deleteDeviceUser(_url);
							$(this).dialog("close");
							location.reload(true);
						},
						"NO": function() {
							$(this).dialog("close");
						}
					}
				});
			}
			
			function deleteDeviceUser(_url) {
				$.ajax({
			        //url : '/DMSv2/devicelist/' + serialNo + '/delete',
			        url : _url,
			        type: 'POST',
			        data : {"${_csrf.parameterName}" : "${_csrf.token}"},
			        success : function(data) {
			        }
			    });

			}
			
			$(function() {
			    var token = $("meta[name='_csrf']").attr("content");
			    var header = $("meta[name='_csrf_header']").attr("content");
			    $(document).ajaxSend(function(e, xhr, options) {
			        xhr.setRequestHeader(header, token);
			    });
			});
		    </script>
	</body>
</html>