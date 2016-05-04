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
    <c:choose>
    	<c:when test="${currentPage < 2}">
    		<c:set var="begining" value="0" />
    	</c:when>
    	<c:otherwise>
    		<c:set var="begining" value="${currentPage - 2}" />
    	</c:otherwise>
    </c:choose>
    
	<c:forEach begin="${begining}" end="${begining + 4}" varStatus="loop">
		<c:choose>
	    	<c:when test="${loop.index == currentPage}">
	    		<li><a>${currentPage}</a></li>
	    	</c:when>
	    	<c:when test="${ loop.index < nbResults/nbPerPage }">
    			<li><tag:link currentPage="${loop.index}" pageUrl="dashboard">${loop.index}</tag:link></li>
    		</c:when>
	    </c:choose>
	</c:forEach>

	<!-- "Last page" button -->
	<c:if test="${currentPage + 1 < nbResults/nbPerPage}">
		<li><tag:link currentPage="${fn:substringBefore(nbResults/nbPerPage, '.')}" pageUrl="dashboard"><span aria-hidden="true">&raquo;</span></tag:link></li>
	</c:if>
</ul>