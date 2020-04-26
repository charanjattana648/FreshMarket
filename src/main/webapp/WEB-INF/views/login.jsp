<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>

<%@include file="./navigation.jsp" %>
	<div class="container">
		<div class="col-md-offset-2 col-md-7">
			<h2 class="text-center">Student Login: ${message}</h2>
			<div class="panel panel-info">
				<div class="panel-body">
				<%-- <img src="<c:url value='/resources/images/back_groc.jpg' />"/> --%>
				<%@include file="./navigation.jsp" %>
					<form:form action="login" cssClass="form-horizontal"
						method="post" modelAttribute="loginInfo">
							<div class="form-group">
							<label for="userType" class="col-md-3 control-label">userType</label>
							<div class="col-md-9">
								<%-- <form:input path="userType" cssClass="form-control" /> --%>
								<form:select path="userType" cssClass="form-control" >
									<option value="admin">Admin</option>
									<option value="customer">customer</option>
								</form:select>
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

					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>