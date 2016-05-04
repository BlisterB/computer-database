<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="perso"	uri="/WEB-INF/taglink.tld" %>
<%@ taglib prefix="tag"	tagdir="/WEB-INF/tags/" %>

<!DOCTYPE html>
<html>
<%@include file='static/head.html'%>
<body>
	<%@include file='static/header.html'%>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbResults} computers found</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer">Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
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
						<th><a href="dashboard?orderby=name&limit=${limit}&current=0">Computer
								Name</a></th>
						<th><a
							href="dashboard?orderby=introduced&limit=${limit}&current=0">Introduced
								date</a></th>
						<th><a
							href="dashboard?orderby=discontinued&limit=${limit}&current=0">Discontinued
								date</a></th>
						<th><a
							href="dashboard?orderby=company&limit=${limit}&current=0">Company</a></th>

					</tr>
				</thead>

				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach var="computer" items="${computerList}">
						<tr>
							<!-- Cellule de suppression du computer -->
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>

							<!--  Cellules d'affichage des informations du computer -->
							<td><a href="editComputer?idComputer=${computer.id}"><c:out
										value="${computer.name}" /></a></td>
							<td><c:out value="${computer.introduced}" /></td>
							<td><c:out value="${computer.discontinued}" /></td>
							<td><c:out value="${computer.companyName}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<c:choose>
				<c:when test="${not empty param.search}">
					<perso:taglink limit="${limit}" orderby="${orderby}"
						current="${current}" nbComputer="${nbResults}"
						search="${param.search}" />
				</c:when>
				<c:otherwise>
					<perso:taglink limit="${limit}" orderby="${orderby}"
						current="${current}" nbComputer="${nbResults}" />
				</c:otherwise>
			</c:choose>

			<!-- NumberPerPage buttons -->
			<div class="btn-group btn-group-sm pull-right" role="group">
				<tag:link pageUrl="dashboard" currentPage="0" nbPerPage="10"	type="button" cssClass="btn btn-default">10</tag:link>
				<tag:link pageUrl="dashboard" currentPage="0" nbPerPage="50"	type="button" cssClass="btn btn-default">50</tag:link>
				<tag:link pageUrl="dashboard" currentPage="0" nbPerPage="100"	type="button" cssClass="btn btn-default">100</tag:link>
			</div>
		</div>
	</footer>

	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
</body>
</html>