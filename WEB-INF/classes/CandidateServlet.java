import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class CandidateServlet extends HttpServlet {

    // Show all candidates
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String, Object>> candidates = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT candidate_id, name FROM candidates";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("candidate_id"));
                row.put("name", rs.getString("name"));
                candidates.add(row);
            }

            request.setAttribute("candidates", candidates);
            RequestDispatcher rd = request.getRequestDispatcher("candidates.jsp");
            rd.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database error in CandidateServlet", e);
        }
    }

    // Handle add, delete, edit
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try (Connection con = DBConnection.getConnection()) {
            if ("add".equals(action)) {
                String name = request.getParameter("name");
                String sql = "INSERT INTO candidates (name) VALUES (?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.executeUpdate();

            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "DELETE FROM candidates WHERE candidate_id = ?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();

            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String sql = "UPDATE candidates SET name=? WHERE candidate_id=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, name);
                ps.setInt(2, id);
                ps.executeUpdate();
            }

            // Redirect back to candidates list
            response.sendRedirect("candidates");

        } catch (SQLException e) {
            throw new ServletException("Database error in CandidateServlet", e);
        }
    }
}
