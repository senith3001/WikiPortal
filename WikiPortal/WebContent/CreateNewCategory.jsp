<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Article</title>
</head>
<body>
<div align="center">
		<c:if test="${category == null}">
			<form action="${pageContext.request.contextPath}/WikiPortalServlet" method="post">
			<input type="hidden" name="action" value="insertnewcat">
		</c:if>
		
		<table border="1" cellpadding="5">
			<caption>
				<h2>
					<c:if test="${category == null}"> Insert Category</c:if>
			
				</h2>
			</caption>
			
			<tr>
				<th>Category Name:</th>
				<td><input type="text" name="name" size="45" required
					value="${category.getName()}" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					value="Save" /></td>
			</tr>


		</table>
	</div>

</body>
</html>