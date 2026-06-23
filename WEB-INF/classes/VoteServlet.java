import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class VoteServlet extends HttpServlet {

    // Utility: load candidates from DB
    private List<Map<String, String>> loadCandidates() throws SQLException {
        List<Map<String, String>> candidates = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT candidate_id, name FROM candidates";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Map<String, String> c = new HashMap<>();
                c.put("id", String.valueOf(rs.getInt("candidate_id")));
                c.put("name", rs.getString("name"));
                candidates.add(c);
            }
        }
        return candidates;
    }

    // Show voting page
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Map<String, String>> candidates = loadCandidates();
            request.setAttribute("candidates", candidates);
        } catch (SQLException e) {
            request.setAttribute("error", "Error loading candidates: " + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("vote.jsp");
        rd.forward(request, response);
    }

    // Handle voting
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("voter_id") == null) {
            response.sendRedirect("voterlogin.jsp");
            return;
        }

        int voterId = (int) session.getAttribute("voter_id");
        String candidateParam = request.getParameter("candidate_id");

        if (candidateParam != null && !candidateParam.isEmpty()) {
            int candidateId = Integer.parseInt(candidateParam);

            try (Connection con = DBConnection.getConnection()) {
                String checkQuery = "SELECT has_voted FROM voters WHERE voter_id = ?";
                PreparedStatement ps = con.prepareStatement(checkQuery);
                ps.setInt(1, voterId);
                ResultSet rs = ps.executeQuery();

                if (rs.next() && rs.getBoolean("has_voted")) {
                    request.setAttribute("error", "You have already voted!");
                } else {
                    String voteQuery = "INSERT INTO votes(voter_id, candidate_id) VALUES (?, ?)";
                    ps = con.prepareStatement(voteQuery);
                    ps.setInt(1, voterId);
                    ps.setInt(2, candidateId);
                    ps.executeUpdate();

                    String updateQuery = "UPDATE voters SET has_voted = TRUE WHERE voter_id = ?";
                    ps = con.prepareStatement(updateQuery);
                    ps.setInt(1, voterId);
                    ps.executeUpdate();

                    request.setAttribute("message", "Your vote has been cast successfully!");
                }
            } catch (SQLException e) {
                request.setAttribute("error", "Database error: " + e.getMessage());
            }
        } else {
            request.setAttribute("error", "Please select a candidate before voting.");
        }

        // reload candidates list
        try {
            List<Map<String, String>> candidates = loadCandidates();
            request.setAttribute("candidates", candidates);
        } catch (SQLException e) {
            request.setAttribute("error", "Error loading candidates: " + e.getMessage());
        }

        RequestDispatcher rd = request.getRequestDispatcher("votedisplay.jsp");
        rd.forward(request, response);
    }
}
