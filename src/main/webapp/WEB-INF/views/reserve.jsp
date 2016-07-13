<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<spring:url value="/reserve" var="reserveActionUrl" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create/Update Reservation</title>
  <spring:url value="/static/css/style.css" var="styleCss" />
		<spring:url value="/static/css/font-awesome.min.css" var="fontAwesomeCss" />
		<spring:url value="/static/js/index.js" var="mainJs" />
	    <script type="text/javascript" src="https://code.jquery.com/jquery-3.0.0.min.js"></script>
	  	<link href="${styleCss}" rel="stylesheet" />
	  	<link href="${fontAwesomeCss}" rel="stylesheet" />
  	  	<script src="${mainJs}"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.2/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
  <script type="text/javascript">
  	function repeatValidation() {
  		var nHide = event.target.isBoxChecked(0)?display.visible:display.hidden; 
		if (document.getElementById("repeating").value == "Weekly" || document.getElementById("repeating").value == "Daily")
		{
			document.getElementById("repeatTo").style.visibility = 'visible';
			document.getElementById("repeatTo").style.display = 'block';
			document.getElementById("repeatTo").required = true;
		}
		else
		{	
			document.getElementById("repeatTo").style.visibility = 'hidden';
			document.getElementById("repeatTo").style.display = 'none';
			document.getElementById("repeatTo").required = false;
		}
	}
  </script>  
 <style>
    .datepicker{
      
    }
  </style>
  <script>
  $(function() {
    $( ".datepicker" ).datepicker();
  });
  </script>
</head>
<body>
	<div class="container">
		<form:form class="form-horizontal" method="post" modelAttribute="reservation" action="${reserveActionUrl}">
		    <form:hidden path="seqNo" />
		    <font color="red">${message}</font>
		    <table id="reservationDtl">
		    <tbody>
		    	<tr>
				<td>Device Name :</td>
		           	<td> 
						<form:select path="device.serialNo">
							<c:forEach items="${deviceList}" var="dl" varStatus="status">
								<c:choose>
									<c:when test="${dl.serialNo eq reservation.device.serialNo}">
										<option value="${dl.serialNo}" selected>${dl.deviceName}</option>
									</c:when>
									<c:otherwise>
										<option value="${dl.serialNo}">${dl.deviceName}</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</form:select>
					</td>
				</tr>
		 	</tbody>
			<tbody>
				<tr>                              
				<td>Reservation Date:</td> 
				<td>
					<spring:bind path="reserveDate">
				            <div class="form-group ${status.error ? 'has-error' : ''}">
				                   <div class="col-sm-10">
				                          <form:input path="reserveDate" type="text" class="datepicker " id="reserveDate" size="30" maxlength="10" placeholder="ReserveDate" required="true" />
				                          <form:errors path="reserveDate" class="control-label" />
				                   </div>
				            </div>
				     </spring:bind>
				</td>
			    </tr>
			</tbody>
			<tbody>
			    <tr>
			    <td>From time:</td> 
			    <td>
			    	<form:select path="timeFrom">
						<c:forEach items="${fromList}" var="tf" varStatus="status">
							<c:choose>
								<c:when test="${tf eq reservation.timeFrom}">
									<option value="${tf}" selected="selected">${tf}</option>
								</c:when>
								<c:otherwise>
									<option value="${tf}">${tf}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
			    </td>
			    <td>To time: </td> 
			    <td><form:select path="timeTo">
						<c:forEach items="${toList}" var="tt" varStatus="status">
							<c:choose>
								<c:when test="${tt eq reservation.timeTo}">
									<option value="${tt}" selected="selected">${tt}</option>
								</c:when>
								<c:otherwise>
									<option value="${tt}">${tt}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
				</td>
			    </tr>
		        <tr>
				<c:choose> 
				  <c:when test="${empty reservation.device.serialNo}">
				       <td>Repeat test: </td> 
				       <td><select name="repeating" onclick="repeatValidation()" id="repeating" > <option></option> <option>Daily</option> <option>Weekly</option>  </select></td>
					        
					   <td>Until: </td>  <td><input type="text" name="repeatTo" class="datepicker" size="50" maxlength="10" id="repeatTo"></td>
				  </c:when>
				</c:choose>
		        </tr>
		        <tr>      
		        <td>Location: </td>
		        <td><spring:bind path="location">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<div class="col-sm-10">
							<form:input path="location" type="text" id="location" size="30" maxlength="255" placeholder="Location" required="false" />
				            <form:errors path="reserveDate" class="control-label" /> 
						</div>
					</div>
				    </spring:bind></td>
		        </tr>
		        <tr>
		        <tr>
		        <td>Additional Information:</td>
		        <td><spring:bind path="addInfo">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<div class="col-sm-10">
							<form:input path="addInfo" type="text" id="addInfo" size="30" maxlength="255" placeholder="AddInfo" required="false" />
				            <form:errors path="addInfo" class="control-label" />
						</div>
					</div>
				    </spring:bind>
				</td>
		        </tr>
		        <tr>
		        <!-- input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" /-->
		        <td><input type="submit" value="Submit" />      </td>
		        </tr>
		    </tbody>
		    </table>
		</form:form>
	</div>
</body>
</html>
