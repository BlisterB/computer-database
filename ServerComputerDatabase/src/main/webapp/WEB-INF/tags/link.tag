<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="pageUrl"		required="true"%>
<%@ attribute name="currentPage"	required="true"%>
<%@ attribute name="nbPerPage"		required="false"%>
<%@ attribute name="orderBy"		required="false"%>
<%@ attribute name="search"			required="false"%>
<%@ attribute name="cssClass"		required="false"%>
<%@ attribute name="type"			required="false"%>

<!-- nbPerPage, orderBy, search : we keep the request's parameter if not precise as tag attribute -->
<!-- This behavior permit to keep a clean code and maintains a coherent user experience -->

<a href="${pageUrl}?
		currentPage=${currentPage}&
		
		<c:choose>
			<c:when test="${not empty nbPerPage}">nbPerPage=${nbPerPage}&</c:when>
			<c:when test="${not empty param.nbPerPage}">nbPerPage=${param.nbPerPage}&</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty orderBy}">orderBy=${orderBy}&</c:when>
			<c:when test="${not empty param.orderBy}">orderBy=${param.orderBy}&</c:when>
		</c:choose>
		
		<c:choose>
			<c:when test="${not empty search}">search=${search}&</c:when>
			<c:when test="${not empty param.search}">search=${param.search}&</c:when>
		</c:choose>
		"
	<c:if test="${not empty type}">type="${type}"</c:if>
	<c:if test="${not empty cssClass}">class="${cssClass}"</c:if>
><jsp:doBody/></a>