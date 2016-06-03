<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tag"	tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="pageUrl" required="true"%>
<%@ attribute name="page" required="true"%>
<%@ attribute name="nbResults" required="true"%>
<%@ attribute name="pageSize" required="true"%>

<ul class="pagination">
	<!-- "First page" button -->
	<c:if test="${page > 0}">
		<li><tag:link page="0" pageUrl="dashboard"><span aria-hidden="true">&laquo;</span></tag:link></li>
	</c:if>

	<!-- Numeroted buttons -->
    <c:choose>
    	<c:when test="${page < 2}">
    		<c:set var="begining" value="0" />
    	</c:when>
    	<c:otherwise>
    		<c:set var="begining" value="${page - 2}" />
    	</c:otherwise>
    </c:choose>
    
	<c:forEach begin="${begining}" end="${begining + 4}" varStatus="loop">
		<c:choose>
	    	<c:when test="${loop.index == page}">
	    		<li><a>${page}</a></li>
	    	</c:when>
	    	<c:when test="${ loop.index < nbResults/pageSize }">
    			<li><tag:link page="${loop.index}" pageUrl="dashboard">${loop.index}</tag:link></li>
    		</c:when>
	    </c:choose>
	</c:forEach>

	<!-- "Last page" button -->
	<c:if test="${page + 1 < nbResults/pageSize}">
		<li><tag:link page="${fn:substringBefore(nbResults/pageSize, '.')}" pageUrl="dashboard"><span aria-hidden="true">&raquo;</span></tag:link></li>
	</c:if>
</ul>