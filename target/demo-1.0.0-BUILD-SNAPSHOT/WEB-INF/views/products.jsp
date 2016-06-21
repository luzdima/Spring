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
		width: 100%; 
		margin: auto; 
		text-align: left;
	}
	.leftAlign {
		margin:0 auto; text-align:left;
	}
</style>
	
	
	<%!
		String getURLParameters(String category, Integer page, String sortDir, String sortPar, HttpSession session) {
			String res = "?";

			res += "page=" + page + "&";
			res += "limit=" + session.getAttribute("limit") + "&";
			if (category != null) 
				res += "category=" + category + "&";
			if (sortDir != null) 
				res += "sortDir=" + sortDir + "&";
			if (sortPar != null) 
				res += "sortPar=" + sortPar + "&";
			return res;
	    }
	%>
	
	<%!
		String makeHeaderURL(String name, HttpServletRequest request, HttpSession session) {
			return getURLParameters(
					request.getParameter("category"), 
					request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page")),
					(request.getParameter("sortPar") != null && request.getParameter("sortPar").equals(name) ? 
							(request.getParameter("sortDir") != null && request.getParameter("sortDir").equals("ASC") ? 
									"DESC" : "ASC") : 
							"ASC"
					),
					name, 
					session
				);
	    }
	%>

	
<body width="90%" align="center">
	
	<div class="leftAlign">
		<div style="margin:10 0 10;" >
			<a href="/555/categories">to Categories</a>
		</div>
	</div>
	
	<div>
		<h1>Products</h1>
		<br />
	</div>
	<br/>
	
	<div style="margin:10 0 10;" class="leftAlign">
		<a href="/555/products/add">Add Product</a>
	</div>
	<br />

	<div text-align="left">
		<div style="float:left">Select category: </div>
		<div style="float:left">
			<form:form method="GET" action="/555/products/"> 
				<input type="hidden" name="page" value="0" />
				<input type="hidden" name="sortDir" value="${param['sortDir']}" />
				<input type="hidden" name="sortPar" value="${param['sortPar']}" />
				<select onchange="this.form.submit()" name="category">
					<option>-</option>
					<c:forEach var="item" items="${categoriesList}" >
						<option value="${item.name}"
							<c:if test="${item.name == selectedCategory}"> selected </c:if>
						> ${item.name}</option>
					</c:forEach>
				</select>
			</form:form>
		</div>
		<div style="clear:both"></div>
	</div>
	<br />

	
						<%@ page import="org.springframework.data.domain.Sort" %>
<!-- TABLE -->	
	<table border=1 align="center">
		<tr>
			<th width="10%">
				<a href="
					<% 
						out.write(makeHeaderURL("name", request, session)); 
					%>
				">Name</a>
				<c:if test="${sortPar == 'name'}">
					<c:choose>
						<c:when test="${sortDir == 'ASC'}"> ↑ </c:when>
						<c:otherwise> ↓ </c:otherwise>
					</c:choose>
				</c:if>
			</th>
			<th width="10%">Category</th>
			<th width="10%">Photo</th>
			<th width="55%">Description</th>
			<th width="5%">
				<a href="
					<% 
						out.write(makeHeaderURL("price", request, session)); 
					%>
				">Price</a>
				<c:if test="${sortPar == 'price'}">
					<c:choose>
						<c:when test="${sortDir == 'ASC'}"> ↑ </c:when>
						<c:otherwise> ↓ </c:otherwise>
					</c:choose>
				</c:if>
			</th>
			<th width="5%">
				<a href="
					<% 
						out.write(makeHeaderURL("count", request, session)); 
					%>
				">Count</a>
				<c:if test="${sortPar == 'count'}">
					<c:choose>
						<c:when test="${sortDir == 'ASC'}"> ↑ </c:when>
						<c:otherwise> ↓ </c:otherwise>
					</c:choose>
				</c:if>
			</th>
			<th width="5%" text-align="center">Actions</th>
		</tr>
		<c:forEach items="${productsList}" var="item" varStatus="status"> 
			<tr>
				<td>${item.name}</td>
				<td>${item.category.name}</td>
				<td align="center"><img src=""></td>
				<td>${item.description}</td>
				<td>${item.price}</td>
				<td>${item.count}</td>
				<td align="center">
					<form:form method="GET" action="/555/products/update">
						<input type="hidden" name="id" value="${item.productId}" /> 
						<input type="submit" value="Update" /> 
					</form:form>
					
					<form:form method="POST" action="/555/products/delete">
						<input type="hidden" name="id" value="${item.productId}" /> 
						<input type="submit" value="Delete" /> 
					</form:form>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	
<!-- PAGING -->
	<c:if test="${curPage > 0}" >
		<a href="
			<% 
				out.write(getURLParameters(
						request.getParameter("category"),
						(Integer)request.getAttribute("curPage") - 1, 
						request.getParameter("sortDir"),
						request.getParameter("sortPar"),
						session)
				);
			%>
		">Prev</a>
	</c:if>
	
	<c:if test="${curPage + 1 < pageSize}" > 
		<a href="
			<% 
			out.write(getURLParameters(
					request.getParameter("category"),
					(Integer)request.getAttribute("curPage") + 1, 
					request.getParameter("sortDir"),
					request.getParameter("sortPar"),
					session)
			);
			%>
		">Next</a>
  	</c:if>
  	
  	<br />
  	

<!-- PAGE LIMIT CHOOSER -->
	<form:form method="POST" action="/555/products/setLimit" class="leftAlign"> 
		limit: 
		<input type="hidden" name="page" value="0" />
		<input type="hidden" name="category" value="${selectedCategory}" />
		<input type="hidden" name="sortDir" value="${param['sortDir']}" />
		<input type="hidden" name="sortPar" value="${param['sortPar']}" />
		<select style="width:50px;" onchange="this.form.submit()" name="limit">
			<c:forEach var="i" begin="1" end="50">
				<option value="${i}"
					<c:if test="${i == limit || i == param['limit']}"> selected </c:if>
				> ${i}</option>
			</c:forEach>
		</select>
	</form:form>


</body>
</html>