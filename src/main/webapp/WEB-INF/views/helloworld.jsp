<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head><%@ page isELIgnored="false"%>
<meta charset="ISO-8859-1">
<title>Spring 5 MVC - Hello World Example | javaguides.net</title>
</head>
<body>
	<h1>Server date time is : ${helloWorld.dateTime}</h1>
	<c:forEach var="msg" items="${helloWorld.messages}">
		<h2>${msg.content}</h2>
	</c:forEach>

</body>
</html>