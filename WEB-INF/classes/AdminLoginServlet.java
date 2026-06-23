import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AdminLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // create session for admin
                HttpSession session = request.getSession();
                session.setAttribute("admin_id", rs.getInt("admin_id"));
                session.setAttribute("admin_username", rs.getString("username"));

                // forward to dashboard JSP
                RequestDispatcher rd = request.getRequestDispatcher("admindash.jsp");
                rd.forward(request, response);
            } else {
                // send back to login with error
                request.setAttribute("error", "Invalid admin credentials.");
                RequestDispatcher rd = request.getRequestDispatcher("adminlogin.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in AdminLoginServlet", e);
        }
    }
}
