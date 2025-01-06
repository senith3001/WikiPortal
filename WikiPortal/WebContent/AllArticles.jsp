<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Articles</title>
<style>
h1 {
	text-align: center;
}

hr {
	border: 1px solid black;
	width: 80%;
	margin: 0 auto;
}

.search {
	text-align: center;
	margin-bottom: 20px;
}

.all-articles {
	display: inline-block;
	vertical-align: top;
	margin-left: 20px;
}

.navigation {
	display: inline-block;
	vertical-align: top;
	margin-left: 20px;
}
</style>
</head>
<body>
	<h1>Wiki Application</h1>
	<hr>
	<div class="navigation">
		<a href="index.jsp">Home</a><br> <a
			href="${pageContext.request.contextPath}/WikiPortalServlet?action=artnames">Articles</a>
		<br> <a
			href="${pageContext.request.contextPath}/WikiPortalServlet?action=catnames">Categories</a><br>
		<a href="Login.jsp">Admin</a>
	</div>
	<div class="all-articles">
		<h2>All Articles</h2>
		<c:forEach var="article" items="${listArticle}">
			<h4>
				<c:out value="${article.name}" />
				<a href="javascript:void(0);"
					onclick="confirmDelete('${pageContext.request.contextPath}/WikiPortalServlet?action=delete&id=${article.articleID}')">|Delete</a>
				<a
					href="${pageContext.request.contextPath}/WikiPortalServlet?action=showedit&id=${article.articleID}">|Edit</a>
				<a
					href="${pageContext.request.contextPath}/WikiPortalServlet?action=hide&id=${article.getArticleID()}">|Hide</a>
			</h4>
			<br>
			<p>
				<c:out value="${fn:substring(article.description, 0, 100)}" />
				... <a
					href="${pageContext.request.contextPath}/WikiPortalServlet?action=readmore&id=${article.articleID}">Read
					more</a>
			</p>
		</c:forEach>
	</div>
	<div>
		<form action="CreateNewArticle.jsp" method="post">
			<button type="submit">Create New Article</button>
		</form>
		<form action="CreateNewCategory.jsp" method="post">
			<button type="submit">Create New Category</button>
		</form>
		<form
			action="${pageContext.request.contextPath}/WikiPortalServlet?action=hidden"
			method="post">
			<button type="submit">Hidden Article List</button>
		</form>
		<form
			action="${pageContext.request.contextPath}/WikiPortalServlet?action=admincatnames"
			method="post">
			<button type="submit">Delete Category</button>
		</form>
		<form
			action="${pageContext.request.contextPath}/WikiPortalServlet?action=logout"
			method="post">
			<input type="submit" value="Logout">
		</form>
	</div>
	<script>
function confirmDelete(deleteAction) {
    if (confirm("Are you sure you want to delete this article?")) {
        window.location.href = deleteAction;
    }
}
</script>

</body>
</html>