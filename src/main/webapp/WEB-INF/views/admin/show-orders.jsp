

<%@include file="../navigation.jsp" %>

<%-- <% if(session.getAttribute("user_email")!=null && session.getAttribute("user_type")!=null && session.getAttribute("user_type").equals("admin")){ %>
	<c:out value = "${purchase_history_list}"/>
<%-- 	<c:forEach var = "i" items="${itemList}" varStatus="status">
        <tr>        
        <td><c:out value = "${i.itemId}"/></td>
        </tr>
        </c:forEach> --%>
<%-- <% } else if (session.getAttribute("user_email") != null && session.getAttribute("user_type").equals("customer")) { 
	response.sendRedirect("FreshMarket/user/items");
	 } else {
		response.sendRedirect("http://localhost:8080/FreshMarket/login");
} %>  --%>
<c:out value = "${purchase_history_list}"/>
</body>
</html>