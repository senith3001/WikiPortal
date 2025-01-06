<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
    <%
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
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
            try {
            	
            	Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LOGIN", "root", "bit235mysql");
                
                String query = "INSERT INTO users (username, password) VALUES (?, ?)";
                
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, username);
                pstmt.setString(2, encryptedPassword);
                
                int rowsAffected = pstmt.executeUpdate();
                
                if (rowsAffected > 0) {
                	//System.out.println(username + encryptedPassword);
    %>
                    <p>Registration successful. <a href="Login.jsp">Login</a></p>
    <%          } else { %>
                    <p>Registration failed. Please try again.</p>
    <%          }
            } catch (SQLException e) {
                e.printStackTrace();
    %>          <p>Database error occurred. Please try again later.</p>
    <%      } finally {
                try {
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
