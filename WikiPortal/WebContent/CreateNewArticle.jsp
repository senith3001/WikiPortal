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
		<c:if test="${article == null}">
			<form action="${pageContext.request.contextPath}/WikiPortalServlet" method="post">
			<input type="hidden" name="action" value="insert">
		</c:if>
		
		<table border="1" cellpadding="5">
			<caption>
				<h2>
					<c:if test="${article == null}"> Insert article </c:if>
			
				</h2>
			</caption>
			
			<tr>
				<th>Article Name:</th>
				<td><input type="text" name="name" size="45" required
					value="${article.getName()}" /></td>
			</tr>
			<tr>
				<th>Description:</th>
				<td><input type="text" name="description" required
					value="${article.getDescription()}" /></td>
			</tr>
			<tr>
				<th>Date:</th>
				<td><input type="text" name="date" required
					value="${article.getDate()}" /></td>
			</tr>
			<tr>
			<th>Show to Public:</th>
			<td><input type="checkbox" name="display" value = "true" /></td>
				
		</tr>
		<tr>
				<th>Category Name:</th>
				<td><input type="text" name="catname" required
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