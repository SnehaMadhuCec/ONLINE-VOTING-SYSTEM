import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class ResultServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT c.name, COUNT(v.vote_id) AS votes " +
                           "FROM candidates c " +
                           "LEFT JOIN votes v ON c.candidate_id = v.candidate_id " +
                           "GROUP BY c.candidate_id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("name", rs.getString("name"));
                row.put("votes", rs.getInt("votes"));
                results.add(row);
            }

            // attach results to request
            request.setAttribute("results", results);

            // forward to JSP
            RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
            rd.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error in ResultServlet", e);
        }
    }
}
