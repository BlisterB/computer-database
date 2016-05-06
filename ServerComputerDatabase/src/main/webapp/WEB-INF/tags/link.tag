<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageUrl" required="true"%>
<%@ attribute name="page" required="true"%>
<%@ attribute name="pageSize" required="false"%>
<%@ attribute name="column" required="false"%>
<%@ attribute name="order" required="false"%>
<%@ attribute name="search" required="false"%>
<%@ attribute name="cssClass" required="false"%>
<%@ attribute name="type" required="false"%>

<!-- pageSize, column, search : we keep the request's parameter if not precise as tag attribute -->
<!-- This behavior permit to keep a clean code and maintains a coherent user experience -->

<a
	href="${pageUrl}?
		page=${page}&
		
		<c:choose>
			<c:when test="${not empty pageSize}">pageSize=${pageSize}&</c:when>
			<c:when test="${not empty param.pageSize}">pageSize=${param.pageSize}&</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty column}">column=${column}&</c:when>
			<c:when test="${not empty param.column}">column=${param.column}&</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty search}">search=${search}&</c:when>
			<c:when test="${not empty param.search}">search=${param.search}&</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty search}">order=${order}&</c:when>
			<c:when test="${not empty param.search}">order=${param.order}&</c:when>
		</c:choose>
		"
	<c:if test="${not empty type}">type="${type}"</c:if>
	<c:if test="${not empty cssClass}">class="${cssClass}"</c:if>><jsp:doBody /></a>