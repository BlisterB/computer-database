<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag"	tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="pageUrl" required="true"%>
<%@ attribute name="currentPage" required="true"%>
<%@ attribute name="nbResults" required="true"%>
<%@ attribute name="nbPerPage" required="true"%>

<ul class="pagination">
	<!-- "First page" button -->
	<c:if test="${currentPage > 0}">
		<li><tag:link currentPage="0" pageUrl="dashboard"><span aria-hidden="true">&laquo;</span></tag:link></li>
	</c:if>

	<!-- Numeroted buttons -->
	<c:set var="i" value="${currentPage - 2}" />
	<c:if test="${i < 0}">
    		<c:set var="i" value="0" />
    </c:if>
    
	<c:forEach begin="${i}" end="${currentPage + 2}" varStatus="loop">
    	<c:if test="${loop.index > 0 && loop.index < nbResults/nbPerPage }">
    		<li><tag:link currentPage="${loop.index}" pageUrl="dashboard">${loop.index}</tag:link></li>
    	</c:if>
	</c:forEach>

	<!-- "Last page" button -->
	<c:if test="${currentPage + 1 < nbResults/nbPerPage}">
		<li><tag:link currentPage="${fn:substringBefore(nbResults/nbPerPage, '.')}" pageUrl="dashboard"><span aria-hidden="true">&raquo;</span></tag:link></li>
	</c:if>
</ul>