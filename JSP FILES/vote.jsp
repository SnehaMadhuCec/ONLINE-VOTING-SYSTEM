<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Cast Your Vote</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Cast Your Vote</h2>

        <%
            List<Map<String, String>> candidates = (List<Map<String, String>>) request.getAttribute("candidates");
            String error = (String) request.getAttribute("error");
            String message = (String) request.getAttribute("message");

            if (error != null) {
        %>
            <p style="color:red;"><%= error %></p>
        <%
            }
            if (message != null) {
        %>
            <p style="color:green;"><%= message %></p>
        <%
            }

            if (candidates == null || candidates.isEmpty()) {
        %>
            <p>No candidates available for voting.</p>
        <%
            } else {
        %>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Action</th>
            </tr>
            <%
                for (Map<String, String> c : candidates) {
            %>
                <tr>
                    <td><%= c.get("id") %></td>
                    <td><%= c.get("name") %></td>
                    <td>
                        <form action="vote" method="post">
                            <input type="hidden" name="candidate_id" value="<%= c.get("id") %>">
                            <button type="submit" class="btn">Vote</button>
                        </form>
                    </td>
                </tr>
            <%
                }
            %>
        </table>
        <%
            }
        %>

        <br>
        <a href="index.jsp" class="btn">Logout</a>
    </div>
</body>
</html>
