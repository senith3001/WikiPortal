<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8">
<title>Article</title>

<style>
    h1 {
        text-align: center;
    }
    hr {
        border: 1px solid black;
        width: 80%; 
        margin: 0 auto; 
    }
   .article-body{
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
<div class="article-body">
    
    
			
       
            <h2><c:out value="${article.getName()}" /></h2>
            <h3>Created:<c:out value="${article.getDate()}" /></h3>
            <h3>Category:<c:out value="${article.getCategoryID()}" /></h3>
            <p><c:out value="${article.getDescription()}" /></p>
       
    </div>
		
       
    



</body>
</html>