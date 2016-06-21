<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Products</title>
</head>

<style>
	table {
		border-spacing: 7px 11px;
	}
	td {
		padding: 5px;
	}
	.err {
		text-color: red;
	}
 </style>
	
<body>
	<h1 align = "center">New Product</h1>
	<br />
	
	<div style="margin:0 0 0 10px; width:100%"><a href="/555/products" >back to Products</a></div>
	<br />
	<br />
	<br />

	<form:form method="POST" action="add">
		<table align="left" style="margin:0 0 0 20px;">
			<tr>
				<td>Name*</td>
				<td><input type="text" name="name" value="${product.name}" style="width:100%"/></td>
				<td><form:errors path="name"/></td>
			</tr>
			<tr>
				<td>Description</td>
				<td>
					<textarea name="description" style="width:100%"> ${product.description} </textarea>
				</td>
			</tr>
			<tr>
				<td>Image</td>
				<td><input type="file" name="description" value="${product.description}"  style="width:100%"/>
				</td>
			</tr>
			<tr>
				<td>Category</td>
				<td>
					<select name="${catName}" style="width:100%">
						<c:forEach items="${categoriesList}" var="item" >
							<option value="${item.name}">${item.name}</option>	
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Price*</td>
				<td><input type="text" name="price" value="${product.price}"  style="width:100%"/></td>
			</tr>
			<tr>
				<td>Count*</td>
				<td><input type="text" name="count"  value="${product.count}" style="width:100%"/></td>
			</tr>
			
			<tr><td>
				<input type="submit" value="Add" />
				<input type="hidden" name="productId" value="${product.productId}" />
			</td></tr>
		</table>
		
	</form:form>

</body>
</html>