<%-- 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<spring:url value="/resources/css/style.css" var="mainCss" />	
	<link href="${mainCss}" rel="stylesheet" />
	
</head>
<body> --%>

<%@include file="../navigation.jsp" %>
<% if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("admin")) { %>

	<div class="container">
		<div class="col-md-offset-2 col-md-7">
			<h2 class="text-center">Items</h2>
			<form method="post" action="filterItems">
				<select name="filterType">
					<option value="itemName">Item Name</option>
					<option value="itemType">Item Type</option>
				</select>
				<input type="text" name="itemValue">
				<input type="submit" value="Find"> 
				</form>
				<button >Logout</button>
				
					<br>
					<div class="itemTable">
			<table class="items">
			<thead><tr>
			<th>Item Id</th>
			<th>Item Name</th>
			<th>Item Type</th>
			<th>Item Count</th>
			<th>Item Price</th>
			<th>Item Image</th>
			<th>Item Desc</th>
			<th>Update</th>
			<th>Delete</th>
			</tr></thead>
			<tfoot></tfoot>
			<tbody>
		<c:forEach var = "i" items="${itemList}" varStatus="status">
        <tr>        
        <td><c:out value = "${i.itemId}"/></td>
        <td><c:out value = "${i.itemName}"/></td>
        <td><c:out value = "${i.itemType}"/></td>
        <td><c:out value = "${i.itemCount}"/></td>
        <td><c:out value = "${i.itemPrice}"/></td>
        <td><img class="itemImage" src='data:image/jpg;base64,<c:out value = " ${imageList[status.index]}"/>'/></td>
        <td><c:out value = "${i.itemDesc}"/></td>
        <td><a href=''>update</a></td>
        <td><a href='/FreshMarket/admin/deleteItem?itemId=${i.itemId}'>delete</a></td>
        <td></td>
        </tr>
      </c:forEach>
			</tbody>
			</table>
			</div>
			<div class="panel panel-info">
				<div class="panel-body">
					
					<h2>Add Item</h2>
					<form method="post" action="saveItem" enctype="multipart/form-data">
					Item Name : <input type="text" name="itemName"> <br>
					Item Type : <input type="text" name="itemType"> <br>
					Item Image : <input type="file" name="itemImage"> <br>
					Item count : <input type="text" name="itemCount"> <br>
					Item Price : $<input type="text" name="itemPrice"> <br>
					Item Desc : <input type="text" name="itemDesc"> <br>
					<input type="submit" value="Add Item"> 
					</form>
					
					<h2>Update Item</h2>
					<form method="post" action="updateItem" enctype="multipart/form-data">
					Item Id : <input type="text" name="itemId"><br>
					Item Name : <input type="text" name="itemName"> <br>
					Item Type : <input type="text" name="itemType"> <br>
					Item Image : <input type="file" name="itemImage"> <br>
					Item count : <input type="text" name="itemCount"> <br>
					Item Price : $<input type="text" name="itemPrice"> <br>
					Item Desc : <input type="text" name="itemDesc"> <br>
					<input type="submit" value="Update Item"> 
					</form>
					
					
					<%-- <form:form action="login" cssClass="form-horizontal"
						method="post" modelAttribute="loginInfo">
							<div class="form-group">
							<label for="userType" class="col-md-3 control-label">userType</label>
							<div class="col-md-9">
								<form:input path="userType" cssClass="form-control" />
							</div>
						<div class="form-group">
							<label for="email" class="col-md-3 control-label">email</label>
							<div class="col-md-9">
								<form:input path="email" cssClass="form-control" />
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-md-3 control-label">Password</label>
							<div class="col-md-9">
								<form:password path="password" cssClass="form-control" />
							</div>
						</div>

						<div class="form-group">
							<!-- Button -->
							<div class="col-md-offset-3 col-md-9">
								<form:button cssClass="btn btn-primary">Submit</form:button>
							</div>
						</div>

					</form:form> --%>
				</div>
			</div>
		</div>
	</div>
	<% } else if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("customer")) { 
	response.sendRedirect("http://localhost:8080/FreshMarket/user/items");
	 } else {
		response.sendRedirect("http://localhost:8080/FreshMarket/login");
} %>
</body>
</html>