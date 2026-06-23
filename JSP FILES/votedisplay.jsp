<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Vote Status</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Voting Status</h2>

        <%
            String voterName = (String) session.getAttribute("voter_name");
            String message = (String) request.getAttribute("message");
            String error = (String) request.getAttribute("error");
            String status = (String) request.getAttribute("status");
        %>

        <% if (message != null) { %>
            <p class="success">✅ Thank you, <%= voterName %>! <%= message %></p>
        <% } else if (error != null) { %>
            <p class="error">⚠️ <%= error %></p>
        <% } else if ("not_voted".equals(status)) { %>
            <p class="warning">ℹ️ You have not cast your vote yet.</p>
        <% } else { %>
            <p class="info">No status available.</p>
        <% } %>

        <a href="index.jsp" class="btn">Back to Home</a>
    </div>
</body>
</html>
