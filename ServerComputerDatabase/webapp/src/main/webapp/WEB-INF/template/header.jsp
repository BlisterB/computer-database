<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard"> Application - Computer
			Database </a>
	</div>


	<!-- Logout -->
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<div>
			${pageContext.request.userPrincipal.name} |
			<c:url var="logoutUrl" value="/logout"/>
			<form action="${logoutUrl}" method="post">
				<input type="submit" value="Logout" />
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			</form>
		</div>
	</c:if>
</header>