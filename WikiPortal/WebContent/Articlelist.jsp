<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Articles</title>
<style>
    h1 {
        text-align: center;
    }
    hr {
        border: 1px solid black;
        width: 80%; 
        margin: 0 auto; 
    }
   .recent-articles{
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
<div>
<c:forEach var="articles" items="${articlesByCategory}">
				<h4><c:out value="${articles.getName()}" /></h4><br>
     <p>
            <c:out value="${fn:substring(articles.description, 0, 100)}" />...
            <a href="${pageContext.request.contextPath}/WikiPortalServlet?action=readmore&id=${article.articleID}">Read more</a>
        </p>
					
				
			</c:forEach>
			</div>

</body>
</html>