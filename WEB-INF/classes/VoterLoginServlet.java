import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class VoterLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM voters WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // ✅ create session for voter
                HttpSession session = request.getSession();
                session.setAttribute("voter_id", rs.getInt("voter_id"));
                session.setAttribute("voter_username", rs.getString("username"));

                // ✅ redirect to VoteServlet, not vote.jsp
                response.sendRedirect("vote");
            } else {
                // invalid login
                request.setAttribute("error", "Invalid voter credentials.");
                RequestDispatcher rd = request.getRequestDispatcher("voterlogin.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {/
            throw new ServletException("Database error in VoterLoginServlet", e);
        }
    }
}
