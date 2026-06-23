<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Voter Login</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Voter Login</h2>

        <% String error = (String) request.getAttribute("error");
           String message = (String) request.getAttribute("message");
           if (error != null) { %>
              <p class="error"><%= error %></p>
        <% } else if (message != null) { %>
              <p class="success"><%= message %></p>
        <% } %>

        <form action="voterlogin" method="post">
            <label>Username:</label>
            <input type="text" name="username" required><br>
            <label>Password:</label>
            <input type="password" name="password" required><br>
            <button type="submit" class="btn">Login</button>
        </form>
        <p>Don’t have an account? <a href="registration.jsp">Register here</a></p>
        <a href="index.jsp" class="btn">Back to Home</a>
    </div>
</body>
</html>
