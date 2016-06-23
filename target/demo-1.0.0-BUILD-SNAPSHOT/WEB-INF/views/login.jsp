<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<style>
.container {display:block; }
.row { display:block;}
.col {display:inline-block;}
.error {color:red;}
</style>

</head>


<body>
	<div id="login-box">

		<h2>Please, Login</h2>

		<div class="container">
			<form name='login' action="<c:url value='/login' />" method='POST'>
				<div class="in-container">
					<c:if test="${param.error != null}">
						<div class="error">    
		                    Invalid username or password.
		                    <br /><br />
		                </div>
		            </c:if>
                </div>
				
				<div class="col">
					<div class="row">Username</div>
					<div class="row">Password</div>
				</div>
  
				<div class="col">
					<div class="row"><input type="text" name="username"/> </div>
					<div class="row"><input type="password" name="password"/></div>
				</div>

				<div class="row">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				</div>
				
				<input name="submit" type="submit" value="submit" /></td>
			</form>
    	</div>
</body>
</html>