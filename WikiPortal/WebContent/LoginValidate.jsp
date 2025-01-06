<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="wikiportalpackage.model.bean.LoginStatus" %>
<%@ page import="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>

    <%
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean isUsernameCorrect = false;
        boolean isPasswordCorrect = false;

        
        if (username != null && password != null) {
            String encryptedPassword = null;
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] messageDigest = md.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();
                for (byte b : messageDigest) {
                    hexString.append(String.format("%02x", b));
                }
                encryptedPassword = hexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            
            Connection conn = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LOGIN", "root", "bit235mysql");

                
                String queryUsername = "SELECT * FROM users WHERE username = ?";
                pstmt = conn.prepareStatement(queryUsername);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    isUsernameCorrect = true;
                    
                  
                    String queryPassword = "SELECT * FROM users WHERE username = ? AND password = ?";
                    pstmt = conn.prepareStatement(queryPassword);
                    pstmt.setString(1, username);
                    pstmt.setString(2, encryptedPassword);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        isPasswordCorrect = true;
                    }
                }

                if (isUsernameCorrect && isPasswordCorrect) {
                	LoginStatus.setLoggedIn(true);
                   
    %>
                    <c:redirect url="WikiPortalServlet?action=allart"/>
    <%
                } else if (!isUsernameCorrect) {
                    
    %>
                    <p>Incorrect username. Please try again.</p>
    <%
                } else if (isUsernameCorrect && !isPasswordCorrect) {
                    
    %>
                    <p>Incorrect password. Please try again.</p>
>
    
    <%
                }

            } catch (SQLException e) {
                e.printStackTrace();
    %>
                <p>Error Please try again later.</p>
    <%
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    %>
</body>
</html>
