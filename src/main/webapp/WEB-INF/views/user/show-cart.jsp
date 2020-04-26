
<%@include file="../navigation.jsp" %>
<h2>Items Added to Cart</h2>
<% if (session.getAttribute("user_email") != null) { %>
<p>Cart Items.....</p>
<table>
<tr><th>Item Id</th><th>Item Name</th><th>Item Price</th><th>Item Qty</th><th>Item Total Price</th><th>Remove</th></tr>
<c:forEach var = "i" items="${cartList}" varStatus="status">
<tr>
<td><c:out value = "${i.itemId}"/></td>
<td><c:out value = "${i.itemName}"/></td>
<td><c:out value = "${i.itemPrice}"/></td>
<td><c:out value = "${i.qty}"/></td>
<td><c:out value = "${i.itemTotalPrice}"/></td>
<td><a href="/FreshMarket/removeFromCart?itemId=${i.itemId}">Remove</a></td></tr>
</c:forEach>
<tr><td>Total Item Price</td><td>$<c:out value = "${totalItemPrice}"/> </td></tr>
<tr><td>Total Tax</td><td>$<c:out value = "${totalTax}"/> </td></tr>
<tr><td>Total Price</td><td>$<c:out value = "${totalPrice}"/> </td></tr>
</table>

<form action="placeOrder" method="post">
<input type="submit" value="Place Order">
<% }else{
	response.sendRedirect("http://localhost:8080/FreshMarket/login");
	} %>
}
</form>
</body>
</html>