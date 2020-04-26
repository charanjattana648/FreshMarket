<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link href="<c:url value="/resources/css/bootstrap.min.css" />"	rel="stylesheet">
<script src="<c:url value="/resources/js/jquery-1.11.1.min.js" />"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
</head>
<body background="<c:url value='/resources/images/back_groc.jpg' />">
<h1>Online Grocery</h1>
<nav><ul class="top_nav">
<% if (session.getAttribute("user_email") != null && session.getAttribute("user_type") != null && !session.getAttribute("user_type").equals("")) { %>
<li><form method="get" action="login"><input type="submit" value="Home"/></form></li>
<% } %>
<% if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("customer")) { %>
<li><form method="post" action="showcart"><input type="submit" value="Show Cart"/></form></li>
<% }else if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("admin")){ %>

<li><form method="post" action="showOrders"><input type="hidden" name="status" value="Pending"/><input type="submit" value="Show Orders"/></form></li>
<% } %>
<% if (session.getAttribute("user_email") != null && session.getAttribute("user_type") != null && !session.getAttribute("user_type").equals("")) { %>
<li><form method="post" action="logout"><input type="submit" value="Logout"/></form></li>
<% }%>
</ul></nav>