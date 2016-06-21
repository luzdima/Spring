<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Categories</title>
</head>

<style>
	.leftAlign {
		margin:0 auto; text-align:left;
	}
</style>
	
<body align="center">
	<div class="leftAlign">
		<div style="margin:10 0 10;" >
			<a href="/555/products">to Products</a>
		</div>
	</div>
	
	<div>
		<h1>Projects</h1>
		<br />
	</div>
	<br/>
		
	<div style="margin:10 0 10;" class="leftAlign">
		<a href="/555/categories/add">Add Category</a>
	</div>
	<br />
	
	<table border=1 align="center">
		<tr>
			<th>Name</th>
			<th>Description</th>
			<th>Count</th>
			<th>Actions</th>
		</tr>
		
		<c:forEach items="${categories}" var="item" varStatus="status"> 

			<tr>
				<c:choose>
					<c:when test="${updateId == item.categoryId}">
						<form:form id="updateForm" method="POST" action="categories/add">
							<input type="hidden" name="categoryId" value="${item.categoryId}"/>
							<td><input type="text" name="name" value="${item.name}" style="width:100%"/>							</td>
							<td><textarea name="description"> ${item.description} </textarea></td>
						</form:form>
					</c:when>
					<c:otherwise>
						<td>${item.name}</td>
						<td>${item.description}</td>
					</c:otherwise>
				</c:choose>

					
					<td>
						<c:set var="itemCount" value="${0}" />
						<c:forEach items="${item.products}" var="product">
							<c:set var="itemCount" value="${itemCount + product.count}" />
						</c:forEach>
						<c:out value="${itemCount}" />
					</td>
					
					<td align="center">
						<c:choose>
							<c:when test="${updateId != item.categoryId}" >
								<form:form method="GET" action="/555/categories">
									<input type="hidden" name="id" value="${item.categoryId}" /> 
									<input type="submit" value="Update" /> 
								</form:form> 
							</c:when>
							<c:otherwise>	
								<input type="submit" value="Save" form="updateForm"/>
							</c:otherwise>
						</c:choose>

						
						<form:form method="POST" action="categories/delete">
							<input type="hidden" name="id" value="${item.categoryId}" /> 
							<input type="submit" value="Delete" />
						</form:form>
					</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>