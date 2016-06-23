<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
	.rightAlign {
		margin:0 auto; text-align:right;
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

	<div style="display:table; width:100%;">
		<div class="leftAlign" style="width:50%; display:table-cell;">
			<div style="margin:10 auto auto 10;" >
				<a href="categories">to Categories</a>
			</div>
		</div>
		<div class="rightAlign"  style="width:50%; display:table-cell;">
			<div style="margin:10 10 auto aauto;" >
				<c:url value="/logout" var="login?logout" />
				<form id="logout" action="login?logout" method="post" >
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</form>
				<c:choose>
					<c:when test="${pageContext.request.userPrincipal.name != null}">
						Hi, ${pageContext.request.userPrincipal.name} | 
						<a href="javascript:document.getElementById('logout').submit()">Logout</a>
					</c:when>
					<c:otherwise>
						<a href="login">Login</a>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
		
	<div>
		<h1>Products</h1>
		<br />
	</div>
	<br/>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<div style="margin:10 0 10;" class="leftAlign">
			<a href="products/add">Add Product</a>
		</div>
	</sec:authorize>

	<br />

	<div text-align="left">
		<div style="float:left">Select category: </div>
		<div style="float:left">
			<form:form method="GET" action="products/"> 
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
			<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
				<th width="5%" text-align="center">Actions</th>
			</sec:authorize>
		</tr>
		<c:forEach items="${productsList}" var="item" varStatus="status"> 
			<tr>
				<td>${item.name}</td>
				<td>${item.category.name}</td>
				<td align="center">
					<c:choose>
						<c:when test="${not empty item.image}">
							<img src="${pageContext.request.contextPath}/products/image?id=${item.productId}" width="200">
						</c:when>
						<c:otherwise> No Image </c:otherwise>
					</c:choose>
				</td>
				<td>${item.description}</td>
				<td>${item.price}</td>
				<td>${item.count}</td>
				<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
					<td align="center">
						<form:form method="GET" action="products/update">
							<input type="hidden" name="id" value="${item.productId}" /> 
							<input type="submit" value="Update" /> 
						</form:form>
						
						<form:form method="POST" action="products/delete">
							<input type="hidden" name="id" value="${item.productId}" /> 
							<input type="submit" value="Delete" /> 
						</form:form>
					</td>
				</sec:authorize>
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
	<form:form method="POST" action="products/setLimit" class="leftAlign"> 
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