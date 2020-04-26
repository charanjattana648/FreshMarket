<%-- 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html>
<html>
<head>
<spring:url value="/resources/css/style.css" var="mainCss" />
	
	<link href="${mainCss}" rel="stylesheet" />
<meta charset="ISO-8859-1">
<title>Item</title>
</head>
<body> --%>
<%@include file="../navigation.jsp" %>
<% if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("customer")) { %>
	<div class="container">
		<div class="col-md-offset-2 col-md-7">
			<h2 class="text-center">Items</h2>
			<p>${message}</p>
			<form method="post" action="filterItems">
				<select name="filterType">
					<option value="itemName">Item Name</option>
					<option value="itemType">Item Type</option>
				</select>
				<input type="text" name="itemValue">
				<input type="submit" value="Find"> 
				</form> &nbsp;&nbsp;&nbsp;&nbsp;
				<!-- <a href="http://localhost:8080/FreshMarket/user/show-cart">AbstractWizardFormController example</a> -->
				<!-- <form action="showcart"><input type="submit" value="Show Cart"> </form> -->
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
			<th>Add to Cart</th>
			<th>Remove From Cart</th>
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
        <td>
        <form action="addItemToCart" method="post">
        -<input type="text" name="itemQty" width="30px" value="0" min="0" max="${i.itemCount}">+
        <input type="hidden" name="itemId" value = "${i.itemId}">
        <input type="hidden" name="itemName" value = "${i.itemName}">
        <input type="hidden" name="itemCount" value = "${i.itemCount}">
        <input type="hidden" name="itemPrice" value = "${i.itemPrice}">
        &nbsp;
        <input type="submit" value="AddToCart">
       <%--  <a href='/FreshMarket/items/addItemToCart?itemId=${i.itemId}&itemName=${i.itemName}&itemPrice=${i.itemPrice}&itemQty=0'> Add </a></td>
         --%>
        </form>
        <td> 
        <form action="removeFromCart" method="post">
          <input type="hidden" name="itemId" width="30" value = "${i.itemId}">
             &nbsp;
        <input type="submit" value="Remove From Cart">
        </form>
        </td>
        <td></td>
        </tr>
      </c:forEach>
			</tbody>
			</table>
</div>
				</div>
			</div>
		</div>
	</div>
<%-- 	<h1>${qty}</h1> --%>
<% } else if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("admin")) { 
	response.sendRedirect("http://localhost:8080/FreshMarket/admin/items");
	 } else {
		response.sendRedirect("http://localhost:8080/FreshMarket/login");
} %>
</body>
</html>