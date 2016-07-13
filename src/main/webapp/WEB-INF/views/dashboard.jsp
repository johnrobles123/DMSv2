<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html>
	<head>
	  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
	  <title>Dashboard</title>
	    <script type='text/javascript' src='http://code.jquery.com/jquery-1.6.2.js'></script>
	    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/jquery-ui.js"></script>
	    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/base/jquery-ui.css">
	    <link rel="stylesheet" type="text/css" href="http://trirand.com/blog/jqgrid/themes/ui.jqgrid.css">
	    <script type='text/javascript' src="http://trirand.com/blog/jqgrid/js/i18n/grid.locale-en.js"></script>
	    <script type='text/javascript' src="http://trirand.com/blog/jqgrid/js/jquery.jqGrid.min.js"></script>
   		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
	    <spring:url value="/static/css/bootstrap.css" var="bootstrapCss" />        
	    <spring:url value="/static/css/style.css" var="styleCss" />
		<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
		<spring:url value="/static/js/index.js" var="mainJs" />
	    <script type="text/javascript" src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
	    <link href="${bootstrapCss}" rel="stylesheet" />
	  	<link href="${styleCss}" rel="stylesheet" />
	  	<link href="${fontAwesomeCss}" rel="stylesheet" />
  	  	<script src="${mainJs}"></script>
  	  	
	    <meta name="_csrf" content="${_csrf.token}"/>
	    <!-- default header name is X-CSRF-TOKEN -->
	    <meta name="_csrf_header" content="${_csrf.headerName}"/>
  	  	
	 </head>
	 
	 <body>						
		<div id="confirmReturnDialogue">Confirm return?</div>
		<div id="confirmCancelDialogue">Confirm cancellation?</div>
		
		<div class="container">
			<div class="filters">
				<table>
					<tr><font color="red">${message}</font></tr>
					<tr>
						<td>Select a Device Name :</td>
						<td> 
							<form action="listdevice" >
	                            <select id="ddDeviceList" onchange="ddListDeviceOnChange()">
	                            	<option value="*">ALL</option>
	                                <c:forEach var="devicelist" items="${deviceList}">
	                                    <!-- option value="${devicelist.serialNo}">${devicelist.deviceName}</option-->
										<c:choose>
											<c:when test="${devicelist.serialNo eq selectedDevice}">
												<option value="${devicelist.serialNo}" selected>${devicelist.deviceName}</option>
											</c:when>
											<c:otherwise>
												<option value="${devicelist.serialNo}">${devicelist.deviceName}</option>
											</c:otherwise>
										</c:choose>
	                                    
	                                </c:forEach>
	                            </select>
                        	</form>
                    	</td>
					</tr>
					<tr>
						<td>Current Status :</td>
						<td>${deviceStatus}</td>
					</tr>
				</table>
			</div>
		
			<div >
					<c:if test="${not empty msg}">
					    <div class="alert alert-${css} alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" 
			                                aria-label="Close">
							<span aria-hidden="true">×</span>
						</button>
						<strong>${msg}</strong>
					    </div>
					</c:if>
			
					<h2>Device Schedule</h2>
          			<div style="float:right; margin-top:20px">
          				<spring:url value="/reserve" var="addReserveUrl" />
						<button class="btn btn-default" id="reserveLink" href="reserve" style="float:right" onclick="location.href='${addReserveUrl}'">Make a Reservation</button>
						<button class="btn btn-default" id="returnLink" href="returnLink" >Return</button>
					</div>
					<div class="datagrid">
						<table id="devicejournaltable">
							<thead>
								<tr>
									<th>Seq No</th>
									<th>Device Name</th>
									<th>User Name</th>
									<th>Reserve Date</th>
									<th>Time From</th>
									<th>Time To</th>
									<th>Action</th>
									<th id="thCenter" align="center" !important>Returned?</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="dj" items="${deviceJournal}">
									<tr>
										<td id="seqNo" value="${dj.seqNo}">${dj.seqNo}</td>
										<td>${dj.device.deviceName}</td>
										<td>${dj.userName}</td>
										<td>${dj.reserveDate}</td>
										<td>${dj.timeFrom}</td>
										<td>${dj.timeTo}</td>
										<td>
											<spring:url value="/viewreserve/${dj.seqNo}" var="queryReserveUrl" />
											<spring:url value="/reserve/${dj.seqNo}/update" var="updateReservationUrl" /> 
											<spring:url value="/reserve/${dj.seqNo}/cancel" var="cancelDeviceUrl" />
									  		<spring:url value="/reserve/${dj.seqNo}/return" var="returnDeviceUrl" />
					
											<button class="btn btn-info" onclick="location.href='${queryReserveUrl}'">Query</button>
											<button class="btn btn-primary" onclick="location.href='${updateReservationUrl}'">Update</button>
									  		<button class="btn btn-danger" id="cancelReservation" onclick="processCancel('${dj.seqNo}')">Cancel</button>
					                	</td>
						                <td width="10%" align="center">
							                <c:set var="role" value="${role}"/>
							                <c:set var="admin_role" value="ADMIN"/>
											<c:choose>
											 <c:when test="${role == admin_role}">
											     <input type="checkbox" class="checkbox" id="returned_chk" value="${dj.returned}" onclick="checkBoxValidation()" />
											 </c:when>
											 <c:otherwise>
											     <input type="checkbox" class="checkbox" id="returned_chk" value="${dj.returned}" disabled="disabled" />
											 </c:otherwise>
											</c:choose>
										  	
						                </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
				    <div id="pageNavPosition" align="center"></div>
				    <script type="text/javascript">
			            var pager = new Pager("devicejournaltable", 10);  
			            pager.init();  
			            pager.showPageNav('pager', 'pageNavPosition');  
			            pager.showPage(1);
				    </script>
			</div>
		</div>
		
	    <!-- include you jquery ui theme -->
        <link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" />
		<!-- include the jquery library -->
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<!-- include the jquery ui library -->
		<script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>
				
		<script type="text/javascript">
			$("#returnLink").click(function() {
				$("#confirmReturnDialogue").dialog({
					modal: true,
					title: "Confirmation",
					buttons: {
						"YES": function() {
							processReturn();
							$(this).dialog("close");
							location.reload(true);
						},
						"NO": function() {
							$(this).dialog("close");
						}
					}
				});
			});
			
			function processCancel(seqNo) {
				$("#confirmCancelDialogue").dialog({
					modal: true,
					title: "Confirmation",
					buttons: {
						"YES": function() {
				        	$.ajax({
				                url : '/DMSv2/dashboard/' + seqNo + '/cancel',
				                type: 'POST',
				                data : {"${_csrf.parameterName}" : "${_csrf.token}"},
				                success : function(data) {
				                }
				            });
				        	
							$(this).dialog("close");
							location.reload(true);
						},
						"NO": function() {
							$(this).dialog("close");
						}
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