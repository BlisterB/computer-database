<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<%@include file='static/head.html'%>
<body>
	<%@include file='static/header.html'%>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>Add Computer</h1>
					<form action="addComputer" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" placeholder="Computer name"
									required="required" pattern="^([a-zA-Z0-9)$">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label>
								<select class="form-control" id="companyId" name="companyId">
									<c:forEach var="company" items="${companyList}">
										<option value="${company.id}" <c:if test="${company.id == computer.companyId}">selected</c:if> >${company.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<div class="actions pull-right">
							<input type="submit" value="Add" class="btn btn-primary">
							or <a href="dashboard.html" class="btn btn-default">Cancel</a>
						</div>
					</form>
					<script
						src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
					<!-- Appeller le script en local -->
					<script
						src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.2.8/jquery.form-validator.min.js"></script>
					<script>
						$.validate({
							modules : 'html5'
						});
					</script>
				</div>
			</div>
		</div>
	</section>
</body>
</html>