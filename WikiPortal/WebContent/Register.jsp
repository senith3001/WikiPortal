<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
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
    <h1>Register</h1>
    
    <form action="RegisterValidate.jsp" method="post">
        Username: <input type="text" name="username"><br>
        Password: <input type="password" name="password"><br>
        <input type="submit" value="Register">
    </form>
    </div>
</body>
</html>
