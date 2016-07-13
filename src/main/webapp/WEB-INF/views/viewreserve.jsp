<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<spring:url value="/viewreserve" var="queryReserveUrl" />

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
		<form:form class="form-horizontal" method="get" modelAttribute="reservation" action="${reserveActionUrl}">
		    <table>
		    <tbody>
		    	<tr>
				<td>Device Name :</td> <td>${reservation.deviceName}</td>
				</tr>
		 	</tbody>
			<tbody>
				<tr>                              
				<td>Reservation Date:</td> <td>${reservation.reserveDate}</td>
			    </tr>
			</tbody>
			<tbody>
			    <tr>
			    <td>From time:</td>  <td>${reservation.timeFrom}</td>
			    <td>To time: </td> <td>${reservation.timeTo}</td>
			    </tr>
		        <tr>      
		        <td>Location: </td><td>${reservation.location}</td>
		        </tr>
		        <tr>
		        <td>Additional Information:</td><td>${reservation.addInfo}</td>
		        </tr>
		    </tbody>
		    
		    </table>
		</form:form>
	</div>
</body>
</html>
