<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Wiki Home</title>
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
    .recent-articles {
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
    <a href="index.jsp">Home</a><br>
    <a href="${pageContext.request.contextPath}/WikiPortalServlet?action=artnames">Articles</a> <br>
    <a href="${pageContext.request.contextPath}/WikiPortalServlet?action=catnames">Categories</a><br>
    <a href="Login.jsp">Admin</a>
</div>
<div class="recent-articles">
    <h2>Hidden Articles</h2>
    <c:forEach var="article" items="${listArticle}">
        <h4><c:out value="${article.name}" /><a href="${pageContext.request.contextPath}/WikiPortalServlet?action=show&id=${article.getArticleID()}">|Show</a></h4><br>
        <p>
            <c:out value="${fn:substring(article.description, 0, 100)}" />...
            <a href="${pageContext.request.contextPath}/WikiPortalServlet?action=readmore&id=${article.articleID}">Read more</a>
            
        </p>
    </c:forEach>
</div>
</body>
</html>
