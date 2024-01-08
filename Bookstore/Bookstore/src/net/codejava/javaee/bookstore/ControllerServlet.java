package net.codejava.javaee.bookstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		bookDAO = new BookDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String author = request.getParameter("author");
        if (!isValidAuthor(author)) {
            // Handle invalid author
            response.getWriter().println("Invalid author. Author must contain only letters and spaces.");
            return;
        }

        // Validate Title (should not be a number)
        String title = request.getParameter("title");
        if (!isValidTitle(title)) {
            // Handle invalid title
            response.getWriter().println("Invalid title. Title cannot be a number.");
            return;
        }

        // Validate Price (should not be negative)
//       String priceStr = request.getParameter("price");
//        double price;
//        try {
//            price = Double.parseDouble(priceStr);
//            if (price < 0) {
//                // Handle negative price
//                response.getWriter().println("Invalid price. Price cannot be negative.");
//                return;
//            }
//        } 
//        catch (NumberFormatException e) {
//            // Handle invalid price format
//            response.getWriter().println("Invalid price format.");
//            return;
//        }

        // Validate PDF File (should be a PDF)
        Part pdfPart = request.getPart("pdfFile");
        if (!isValidPdf(pdfPart)) {
            // Handle invalid PDF
            response.getWriter().println("Invalid PDF file. Please select a PDF file.");
            return;
        }
		doGet(request, response);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertBook(request, response);
				break;
			case "/delete":
				deleteBook(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateBook(request, response);
				break;
			case "/search":
				getBookByTitle(request, response);
				break;
			case "/list1":
				usersBooklist(request, response);
				break;
			case "/showPdf":
				showPdf(request, response);
				break;
			case "/categorylist":
				listBookByCategory(request, response);
				break;
			case "/usercategorylist":
				userListBookByCategory(request, response);
				break;
			case "/downloadPdf":
				downloadPdf(request, response);
				break;
			default:
				listBook(request, response);
				break;
			}
		} 
		catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private boolean isValidAuthor(String author) {
        // Validate author using a regular expression
        return author.matches("^[a-zA-Z\\s]+$");
    }

    private boolean isValidTitle(String title) {
        // Validate title by checking if it's not a number
        return !title.matches("\\d+");
    }

    private boolean isValidPdf(Part pdfPart) {
        // Validate PDF by checking the file extension
        String fileName = Paths.get(pdfPart.getSubmittedFileName()).getFileName().toString();
        return fileName.toLowerCase().endsWith(".pdf");
    }
	private void listBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<Book> listBook = bookDAO.listAllBooks();
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookList.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listBookByCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String category = request.getParameter("category");
		List<Book> listBook = bookDAO.listAllBooksByCategory(category);
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookListByCategory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void userListBookByCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		String category = request.getParameter("category");
		List<Book> listBook = bookDAO.listAllBooksByCategory(category);
		request.setAttribute("listBook", listBook);
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserBookListByCategory.jsp");
		dispatcher.forward(request, response);
	}
	
	private void usersBooklist(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<Book> listBook = bookDAO.listAllBooks();
		request.setAttribute("listBook1", listBook);
//		System.out.println("USERS BOOK LIST");
		RequestDispatcher dispatcher = request.getRequestDispatcher("UsersBookList.jsp");
//		System.out.println("USERS BOOK LIST11111");
		dispatcher.forward(request, response);
	}
	
	private void getBookByTitle(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException{
		String title = request.getParameter("title");
		Book getBook = bookDAO.getBookByTitle(title);
		RequestDispatcher dispatcher = request.getRequestDispatcher("SearchBook.jsp");
		request.setAttribute("getBook", getBook);
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		dispatcher.forward(request, response);
	}


	private void insertBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
//		String title = request.getParameter("title");
//		String author = request.getParameter("author");
//		float price = Float.parseFloat(request.getParameter("price"));
//
//		Book newBook = new Book(title, author, price);
//		bookDAO.insertBook(newBook);
//		response.sendRedirect("list");
		
		String title = request.getParameter("title");
        String author = request.getParameter("author");
//        String priceString = request.getParameter("price");
//        float price = 0.0f; 
        
//        if (priceString != null && !priceString.trim().isEmpty()) {
//            try {
//                price = Float.parseFloat(priceString.trim());
//            } 
//            catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
        InputStream pdfFile = null;
        Part filePart = request.getPart("pdfFile");
        if (filePart != null) {
            pdfFile = filePart.getInputStream();
        }
        String category=request.getParameter("category");
        Book book = new Book(title, author/*, price*/,  category, pdfFile);
        try {
            bookDAO.insertBook(book);
            response.sendRedirect("list");
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("Error.jsp");
        }
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String category=request.getParameter("category");
		Book existingBook = bookDAO.getBook(id);
		
		System.out.println("Book ID to update: " + id);
		request.setAttribute("id", id);
		System.out.println("Book exsisting book to update: " + existingBook.getPdfFile());
		request.setAttribute("book", existingBook);
		request.setAttribute("category", category);
		RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
		request.setAttribute("pdfId", id);
//		request.setAttribute(category, existingBook);
		dispatcher.forward(request, response);
		
//		int id = Integer.parseInt(request.getParameter("id"));
//	    Book existingBook = bookDAO.getBook(id);
//
//	    if (existingBook != null) {
//	        System.out.println("Book ID to update: " + id);
//	        request.setAttribute("id", id);
//	        request.setAttribute("book", existingBook); // Set the book attribute if it's not null
//	        RequestDispatcher dispatcher = request.getRequestDispatcher("BookForm.jsp");
//	        request.setAttribute("pdfId", id);
//	        dispatcher.forward(request, response);
//	    } 
//	    else {
//	        // Handle the case where the book is not found
//	        response.getWriter().println("Book not found for the specified ID.");
//	    }
	}

	private void updateBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
//		int id = Integer.parseInt(request.getParameter("id"));
//		String title = request.getParameter("title");
//		String author = request.getParameter("author");
////		float price = Float.parseFloat(request.getParameter("price"));
////		System.out.println("UPDATE BOOK ");
//		InputStream pdfFile = null;
//        Part filePart = request.getPart("pdfFile");
//        if (filePart != null) {
//            pdfFile = filePart.getInputStream();
//        }
//        Book book = new Book(title, author/*, price*/, pdfFile);
//        System.out.println("PDF File " + pdfFile);
//		bookDAO.updateBook(book, id);
//		response.sendRedirect("list");
		int id = Integer.parseInt(request.getParameter("id"));
	    String title = request.getParameter("title");
	    String author = request.getParameter("author");
	    String category = request.getParameter("category");
//	    float price = Float.parseFloat(request.getParameter("price"));

	    InputStream pdfFile = null;
	    Part filePart = request.getPart("pdfFile");
	    if (filePart != null) {
	        pdfFile = filePart.getInputStream();
	    }

	    // Fetch the existing book
	    Book existingBook = bookDAO.getBook(id);

	    // Update the existing book's properties
	    existingBook.setTitle(title);
	    existingBook.setAuthor(author);
	    existingBook.setCategory(category);
//	    existingBook.setPrice(price);
	    existingBook.setPdfFile(pdfFile);

	    System.out.println("Book ID to update: " + id);

	    // Use the existing book for updating
	    bookDAO.updateBook(existingBook, id);
	    response.sendRedirect("list");
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Book book = new Book(id);
		bookDAO.deleteBook(book);
		response.sendRedirect("list");

	}
	
	private void showPdf(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException{
		int bookId = Integer.parseInt(request.getParameter("id"));
        try {
        	System.out.println("PDF ");
        	System.out.println("BOOK ID PRINT"+ bookId);
            Book book = bookDAO.getBook(bookId);
            System.out.println(book.getPdfFile());
            System.out.println("PDF File");
            if (book != null && book.getPdfFile() != null) {
                // Set the content type for PDF
            	System.out.println("PDF File 2");
                response.setContentType("application/pdf");
                InputStream pdfInputStream = book.getPdfFile();
                OutputStream out = response.getOutputStream();

                // Buffer to read and write data
                byte[] buffer = new byte[1024];
                int bytesRead;

                // Read from the input stream and write to the output stream
                while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }

                // Close streams
                pdfInputStream.close();
                out.flush();
                out.close();
            } 
            else {
                // Handle the case where the book or PDF file is not found
                response.getWriter().println("PDF not found for the specified book.");
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	private void downloadPdf(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));

        try {
            Book book = bookDAO.getBook(bookId);

            if (book != null && book.getPdfFile() != null) {
                InputStream pdfInputStream = book.getPdfFile();
                
                // Set the content type for PDF
                response.setContentType("application/pdf");
                
                // Set the disposition type to make the browser open the file in a new tab
                response.setHeader("Content-Disposition", "inline; filename=" + book.getTitle() + ".pdf");
                
                // Read PDF content from the input stream
                int bytesRead;
                byte[] buffer = new byte[1024];
                
                try (OutputStream out = response.getOutputStream()) {
                    while ((bytesRead = pdfInputStream.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                }
            } 
            else {
                // Handle the case where the book or PDF file is not found
                response.getWriter().println("PDF not found for the specified book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
