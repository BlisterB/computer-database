<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<%@include file='../template/head.jsp'%>
<body>
	<%@include file='../template/header.jsp'%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1><spring:message code="login.title" /></h1>

					<div id="login-box">

						<c:if test="${not empty error}">
							<div class="error">${error}</div>
						</c:if>
						<c:if test="${not empty msg}">
							<div class="msg">${msg}</div>
						</c:if>

						<form name='loginForm'
							action="<c:url value='login' />" method='POST'>

							<table>
								<tr>
									<td><spring:message code="login.user" /></td>
									<td><input type='text' name='username' id='username' value=''></td>
								</tr>
								<tr>
									<td><spring:message code="login.password" /></td>
									<td><input type='password' name='password' id='password' /></td>
								</tr>
								<tr>
									<td colspan='2'><input name="submit" type="submit"
										value="<spring:message code="login.submit" />" /></td>
								</tr>
							</table>

							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						</form>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>
</html>