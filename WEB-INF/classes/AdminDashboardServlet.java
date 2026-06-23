import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> candidates = new ArrayList<>();
        List<Map<String, Object>> results = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            // --- Fetch candidates ---
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM candidates");
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("candidate_id"));
                row.put("name", rs.getString("name"));
                candidates.add(row);
            }

            // --- Fetch results (votes per candidate) ---
            String resultQuery = "SELECT c.name, COUNT(v.vote_id) AS votes " +
                                 "FROM candidates c LEFT JOIN votes v ON c.candidate_id = v.candidate_id " +
                                 "GROUP BY c.candidate_id";
            rs = stmt.executeQuery(resultQuery);
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("name", rs.getString("name"));
                row.put("votes", rs.getInt("votes"));
                results.add(row);
            }

        } catch (SQLException e) {
            throw new ServletException("Error loading admin dashboard data", e);
        }

        request.setAttribute("candidates", candidates);
        request.setAttribute("results", results);

        RequestDispatcher rd = request.getRequestDispatcher("admindash.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                PreparedStatement ps = con.prepareStatement("INSERT INTO candidates(name) VALUES (?)");
                ps.setString(1, name);
                ps.executeUpdate();

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                PreparedStatement ps = con.prepareStatement("DELETE FROM candidates WHERE candidate_id=?");
                ps.setInt(1, id);
                ps.executeUpdate();

            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                PreparedStatement ps = con.prepareStatement("UPDATE candidates SET name=? WHERE candidate_id=?");
                ps.setString(1, name);
                ps.setInt(2, id);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            throw new ServletException("Error updating candidates", e);
        }

        // Reload dashboard after action
        response.sendRedirect("admindash");
    }
}
