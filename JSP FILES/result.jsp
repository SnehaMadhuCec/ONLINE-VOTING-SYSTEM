<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Election Results</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
    <div class="container">
        <h2>Election Results</h2>
        <table>
            <tr><th>Candidate</th><th>Votes</th></tr>
            <%
                List<Map<String, Object>> results = (List<Map<String, Object>>) request.getAttribute("results");
                if (results != null && !results.isEmpty()) {
                    for (Map<String, Object> row : results) {
            %>
                        <tr>
                            <td><%= row.get("name") %></td>
                            <td><%= row.get("votes") %></td>
                        </tr>
            <%
                    }
                } else {
            %>
                    <tr><td colspan="2">No results available yet.</td></tr>
            <%
                }
            %>
        </table>
        <a href="admindash.jsp" class="btn">Back to Dashboard</a>
    </div>
</body>
</html>
