<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<%@include file='../template/head.jsp'%>
<body>
	<%@include file='../template/header.jsp'%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form id="addComputer" action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" minlength="3" class="form-control"
									pattern="^([a-zA-Z0-9)$" required="required"
									placeholder="Computer name" id="name"
									name="name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date (YYYY-MM-dd) :</label>
								<input type="text" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date (YYYY-MM-dd) :</label> <input type="text" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach var="company" items="${companyList}">
										<option value="${company.id}"
											<c:if test="${company.id == computer.companyId}">selected</c:if>>${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>

					<!-- Form validation -->
					<!-- JQuery -->
					<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
					<!-- JQuery form validator -->
					<script src="<c:url value="/resources/js/jquery.validate.js"/>"></script>

					<!-- Form validator -->
					<script>
						$("#addComputer").validate({
							rules : {
								introduced : {
									dateISO : true
								},
								discontinued : {
									dateISO : true
								}
							}
						});
					</script>
				</div>
			</div>
		</div>
	</section>
</body>
</html>