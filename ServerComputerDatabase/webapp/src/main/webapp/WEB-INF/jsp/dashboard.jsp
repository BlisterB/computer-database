<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags/"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<%@include file='../template/head.jsp'%>
<body>
	<%@include file='../template/header.jsp'%>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbResults}
				<spring:message code="computerFound" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="SearchName" />" /> <input
							type="submit" id="searchsubmit" value="<spring:message code="FilterByName" />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
							code="AddComputer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="Edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<!-- We include the CSRF token in the request, because of Spring Security -->
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Table headers -->
						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><tag:link pageUrl="dashboard" page="0"
								column="computerName" order="ASC">
								<spring:message code="ComputerName" />
							</tag:link></th>
						<th><tag:link pageUrl="dashboard" page="0"
								column="introduced" order="DESC">
								<spring:message code="IntroducedDate" />
							</tag:link></th>
						<th><tag:link pageUrl="dashboard" page="0"
								column="discontinued" order="DESC">
								<spring:message code="DiscontinuedDate" />
							</tag:link></th>
						<th><tag:link pageUrl="dashboard" page="0"
								column="companyName" order="ASC">
								<spring:message code="CompanyName" />
							</tag:link></th>

					</tr>
				</thead>

				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${computerList}">
						<tr>
							<!-- Cellule de suppression du computer -->
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}" id="${computer.name}_id"></td>

							<!--  Cellules d'affichage des informations du computer -->
							<td><a href="editComputer?idComputer=${computer.id}"
								id="${computer.name}_name">${computer.name}</a></td>
							<td>${computer.introduced}</td>
							<td>${computer.discontinued}</td>
							<td>${computer.companyName}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<!-- Pagination buttons -->
			<tag:pagination pageUrl="dashboard" page="${page}"
				nbResults="${nbResults}" pageSize="${pageSize}"></tag:pagination>

			<!-- NumberPerPage buttons -->
			<div class="btn-group btn-group-sm pull-right" role="group">
				<tag:link pageUrl="dashboard" page="0" pageSize="10" type="button"
					cssClass="btn btn-default">10</tag:link>
				<tag:link pageUrl="dashboard" page="0" pageSize="50" type="button"
					cssClass="btn btn-default">50</tag:link>
				<tag:link pageUrl="dashboard" page="0" pageSize="100" type="button"
					cssClass="btn btn-default">100</tag:link>
			</div>
		</div>
	</footer>

	<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/resources/js/dashboard.js"/>"></script>
</body>
</html>