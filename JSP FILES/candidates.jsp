<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Candidate Management</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Candidate Management</h2>

        <!-- Add Candidate -->
        <form action="candidates" method="post">
            <input type="hidden" name="action" value="add">
            <input type="text" name="name" placeholder="Candidate Name" required>
            <button type="submit" class="btn">Add Candidate</button>
        </form>

        <hr>

        <!-- Candidate List -->
        <h3>Existing Candidates</h3>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
            <%
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) request.getAttribute("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    for (Map<String, Object> c : candidates) {
            %>
                        <tr>
                            <td><%= c.get("id") %></td>
                            <td><%= c.get("name") %></td>
                            <td>
                                <!-- Delete -->
                                <form action="candidates" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= c.get("id") %>">
                                    <button type="submit" class="btn">Delete</button>
                                </form>

                                <!-- Edit -->
                                <form action="candidates" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="edit">
                                    <input type="hidden" name="id" value="<%= c.get("id") %>">
                                    <input type="text" name="name" value="<%= c.get("name") %>" required>
                                    <button type="submit" class="btn">Update</button>
                                </form>
                            </td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr><td colspan="3">No candidates found.</td></tr>
            <%
                }
            %>
        </table>

        <br>
        <a href="admindash.jsp" class="btn">Back to Dashboard</a>
    </div>
</body>
</html>
