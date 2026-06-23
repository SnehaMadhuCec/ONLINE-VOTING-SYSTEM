import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class VoterRegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO voters(name, age, address, phone, username, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, address);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);

            int result = ps.executeUpdate();

            if (result > 0) {
                request.setAttribute("message", "Registration successful! You can now log in from the homepage.");
                RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
                rd.forward(request, response);
            } else {
                request.setAttribute("error", "Registration failed. Please try again.");
                RequestDispatcher rd = request.getRequestDispatcher("registration.jsp");
                rd.forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error in VoterRegistrationServlet", e);
        }
    }
}
