package net.codejava.javaee.bookstore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        
        System.out.println("LoginServlet");
        try (Connection conn = UserDAO.getConnection()) {
            String query = "SELECT * FROM user WHERE username=? AND password=? AND role=?";
            try (PreparedStatement pst = conn.prepareStatement(query)) {
            	List<Book> listBook = bookDAO.listAllBooks();
        		request.setAttribute("listBook", listBook);
        		System.out.println("USERS BOOK LIST");
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, role);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                	System.out.println("USERS BOOK LIST11111");
                    if ("user".equals(role)) {
//                      response.sendRedirect("UsersBookList.jsp");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("UsersBookList.jsp");
//                		System.out.println("USERS BOOK LIST11111");
                		dispatcher.forward(request, response);
                    } 
                    else if ("admin".equals(role)) {
                    	System.out.println("USERS BOOK LIST56565");
//                      response.sendRedirect("BookForm.jsp");
                    	RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
//                		System.out.println("USERS BOOK LIST11111");
                		dispatcher.forward(request, response);
                    }
                } 
                else {
                    response.sendRedirect("Login.jsp");
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Error.jsp");
        }
    }
}
