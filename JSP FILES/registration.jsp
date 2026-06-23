<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Voter Registration</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Voter Registration</h2>

        <% String error = (String) request.getAttribute("error");
           String message = (String) request.getAttribute("message");
           if (error != null) { %>
              <p class="error"><%= error %></p>
        <% } else if (message != null) { %>
              <p class="success"><%= message %></p>
        <% } %>

        <form action="registration" method="post">
            <label>Name:</label>
            <input type="text" name="name" required><br>
            <label>Age:</label>
            <input type="number" name="age" required><br>
            <label>Address:</label>
            <input type="text" name="address" required><br>
            <label>Phone:</label>
            <input type="text" name="phone" required><br>
            <label>Username:</label>
            <input type="text" name="username" required><br>
            <label>Password:</label>
            <input type="password" name="password" required><br>
            <button type="submit" class="btn">Register</button>
        </form>
        <a href="index.jsp" class="btn">Back to Home</a>
    </div>
</body>
</html>
