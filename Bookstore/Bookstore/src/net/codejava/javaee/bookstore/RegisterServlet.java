package net.codejava.javaee.bookstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        if (password.length() < 6) {
            response.sendRedirect("passwordLengthError.jsp");
            return;
        }
        System.out.println("RegisterServlet1");
        try (Connection conn = UserDAO.getConnection()) {
            if (!isUsernameExists(conn, username)) {
            	System.out.println("12345");
                String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, username);
                    pst.setString(2, password);
                    pst.setString(3, role);
                    pst.executeUpdate();
                }
                response.sendRedirect("Login.jsp");
            } 
            else {
                response.sendRedirect("duplicateUsernameError.jsp");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Error.jsp");
        }
    }

    private boolean isUsernameExists(Connection conn, String username) throws Exception {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        System.out.println("12345");
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, username);
            try (ResultSet resultSet = pst.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }
}
