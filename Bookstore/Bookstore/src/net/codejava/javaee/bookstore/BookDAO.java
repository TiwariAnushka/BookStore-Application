package net.codejava.javaee.bookstore;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

public class BookDAO {
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;
	
	public BookDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}
	
	protected void connect() throws SQLException {
		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}
	
	protected void disconnect() throws SQLException {
		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}
	
	public boolean insertBook(Book book) throws SQLException {
//		String sql = "INSERT INTO book (title, author, price, pdf_data) VALUES (?, ?, ?, ?)";
		String sql = "INSERT INTO book (title, author,category, pdf_data) VALUES (?, ?, ?, ?)";
		connect();
		
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setString(1, book.getTitle());
		statement.setString(2, book.getAuthor());
//		statement.setFloat(3, book.getPrice());
//		statement.setBlob(4, book.getPdfFile());
		statement.setString(3, book.getCategory());
		InputStream pdfFile = book.getPdfFile();
        if (pdfFile != null) {
//        	System.out.println(pdfFile);
            statement.setBlob(4, pdfFile);
        } 
        else {
//        	System.out.println(pdfFile+"HELLOOOO");
            statement.setNull(4, java.sql.Types.BLOB);
        }
		
		boolean rowInserted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowInserted;
	}
	
	public List<Book> listAllBooks() throws SQLException {
		List<Book> listBook = new ArrayList<>();
		String sql = "SELECT * FROM book";
		connect();
		Statement statement = jdbcConnection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			int id = resultSet.getInt("book_id");
			String title = resultSet.getString("title");
			String author = resultSet.getString("author");
			String category = resultSet.getString("category");
//			float price = resultSet.getFloat("price");
			InputStream pdfFile = resultSet.getBinaryStream("pdf_data");
			Book book = new Book(id, title, author, category/*, price*/, pdfFile);
			listBook.add(book);
		}
		resultSet.close();
		statement.close();
		disconnect();
		return listBook;
	}
	
	public boolean deleteBook(Book book) throws SQLException {
		String sql = "DELETE FROM book where book_id = ?";
		connect();
		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
		statement.setInt(1, book.getId());
		boolean rowDeleted = statement.executeUpdate() > 0;
		statement.close();
		disconnect();
		return rowDeleted;		
	}
	
//	public boolean updateBook(Book book) throws SQLException {
//		String sql = "UPDATE book SET title = ?, author = ?, price = ?, pdf_data = ?";
//		sql += " WHERE book_id = ?";
//		connect();
//		
//		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setString(1, book.getTitle());
//		statement.setString(2, book.getAuthor());
//		statement.setFloat(3, book.getPrice());
//		statement.setInt(4, book.getId());
//		InputStream pdfFile = book.getPdfFile();
//        if (pdfFile != null) {
////        	System.out.println(pdfFile);
//            statement.setBlob(5, pdfFile);
//        } 
//        else {
////        	System.out.println(pdfFile+"HELLOOOO");
//            statement.setNull(5, java.sql.Types.BLOB);
//        }
//		boolean rowUpdated = statement.executeUpdate() > 0;
//		statement.close();
//		disconnect();
//		return rowUpdated;		
//	}
	public boolean updateBook(Book book, int id) throws SQLException {
//	    String sql = "UPDATE book SET title = ?, author = ?, price = ?, pdf_data = ?";
		String sql = "UPDATE book SET title = ?, author = ?, category = ?, pdf_data = ?";
	    sql += " WHERE book_id = "+id;
	    System.out.println("Book ID to update: " + book.getId());
	    connect();
	    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//	    statement.setInt(1, book.getId());
	    statement.setString(1, book.getTitle());
	    statement.setString(2, book.getAuthor());
	    statement.setString(3, book.getCategory());
//	    statement.setFloat(3, book.getPrice());
	    // Create a Blob object from the InputStream
	    InputStream pdfFile = book.getPdfFile();
	    if (pdfFile != null) {
	        statement.setBlob(4, pdfFile);
	    } 
	    else {
	        statement.setNull(4, java.sql.Types.BLOB);
	    }	    
//	    statement.setInt(5, book.getId());	    
	    System.out.println("SQL: " + statement.toString());
	    boolean rowUpdated = statement.executeUpdate() > 0;
	    statement.close();
	    disconnect();	    
	    System.out.println("Rows updated: " + rowUpdated);
	    return rowUpdated;       
	}
	
	public Book getBook(int id) throws SQLException {
//		Book book = null;
//		String sql = "SELECT * FROM book WHERE book_id = ?";
//		System.out.println("get book me hu");
//		connect();
//		
//		PreparedStatement statement = jdbcConnection.prepareStatement(sql);
//		statement.setInt(1, id);
////		book=new Book();
////		ResultSet resultSet = statement.executeQuery();
//		System.out.println(book);
//		if (resultSet.next()) {
////			String title = resultSet.getString("title");
////			String author = resultSet.getString("author");
////			float price = resultSet.getFloat("price");
////			InputStream pdfInputStream = resultSet.getBinaryStream("pdf_data");
////	        book.setPdfFile(pdfInputStream);
////			book = new Book(id, title, author, price, pdfInputStream);
//			System.out.println("fgtuk8l78");
//			book = extractBookFromResultSet(resultSet);			
//		}	
//		resultSet.close();
//		statement.close();
		Book book = null;
	    String sql = "SELECT * FROM book WHERE book_id = ?";	    
	    connect(); // Assuming this method is for establishing the database connection	    
	    try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
	        statement.setInt(1, id);
	        try (ResultSet resultSet = statement.executeQuery()) {
	            if (resultSet.next()) {
	                book = extractBookFromResultSet(resultSet);
	            }
	        }
	    } 
	    catch (SQLException e) {
	        // Handle or log the exception appropriately
	        e.printStackTrace();
	    } 
	    finally {
	        // Close the connection here if needed
	    }
	    System.out.println("get Book ID : " + book.getPdfFile());
//	    return book;
		return book;
	}
	
	private Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
        Book book = new Book();
//      book.setId(resultSet.getInt("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        System.out.println("extract book");
        book.setCategory(resultSet.getString("category"));
//      book.setPrice(resultSet.getFloat("price"));
        // Assuming "pdf_data" is the column name for your PDF data
        InputStream pdfInputStream = resultSet.getBinaryStream("pdf_data");
        book.setPdfFile(pdfInputStream);
        return book;
    }
	// Method to retrieve a single book by its title from the database
	public Book getBookByTitle(String title) throws SQLException {
	    Book book = null;
	    String sql = "SELECT * FROM book WHERE title = ?";
	    connect();
	    // Prepare and execute the SQL statement
	    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	    statement.setString(1, title);
	    // Process the result set and create a book object
	    ResultSet resultSet = statement.executeQuery();
	    if (resultSet.next()) {
	        int id = resultSet.getInt("book_id");
	        String author = resultSet.getString("author");
//	        float price = resultSet.getFloat("price");
	        String category = resultSet.getString("category");
	        book = new Book(id, title, author, category/*, price*/);
	    }
	    // Close resources
	    resultSet.close();
	    statement.close();
	    disconnect();
	    return book;
	}
	
	public void downloadPdf(int bookId, HttpServletResponse response) throws SQLException, IOException {
        String sql = "SELECT pdf_data FROM book WHERE book_id = ?";
        connect();
        try (PreparedStatement statement = jdbcConnection.prepareStatement(sql)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    InputStream pdfInputStream = resultSet.getBinaryStream("pdf_data");                   
                    if (pdfInputStream != null) {
                        // Set the content type for PDF
                        response.setContentType("application/pdf");                        
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
                        // Handle the case where the PDF file is not found
                        response.getWriter().println("PDF not found for the specified book.");
                    }
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            disconnect();
        }
    }

	public List<Book> listAllBooksByCategory(String category) throws SQLException {
		// TODO Auto-generated method stub
		List<Book> listBook = new ArrayList<>();
		
		String sql = "SELECT * FROM book WHERE category = ?";
	    connect();
	    // Prepare and execute the SQL statement
	    PreparedStatement statement = jdbcConnection.prepareStatement(sql);
	    statement.setString(1, category);
	    // Process the result set and create a book object
	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
	        int id = resultSet.getInt("book_id");
	        String author = resultSet.getString("author");
//	        float price = resultSet.getFloat("price");
	        String title = resultSet.getString("title");
	        Book book = new Book(id, title, author, category);
	        listBook.add(book);
	    }
	    // Close resources
	    resultSet.close();
	    statement.close();
	    disconnect();
	    return listBook;
//	    String sql = "SELECT * FROM book WHERE category = ?";
//	    connect();
//	    Statement statement = jdbcConnection.createStatement();
//	    ResultSet resultSet = statement.executeQuery(sql);
//	    while (resultSet.next()) {
//	        int id = resultSet.getInt("book_id");
//	        String title = resultSet.getString("title");
//	        String author = resultSet.getString("author");
//	        String category1 = resultSet.getString("category");
//	        // float price = resultSet.getFloat("price");
//	        InputStream pdfFile = resultSet.getBinaryStream("pdf_data");
//
//	        Book book = new Book(id, title, author, category1, pdfFile);
//	        listBook.add(book);
//	    }
//	    resultSet.close();
//	    statement.close();
//
//	    disconnect();
//
//	    return listBook;
//		return null;
	}
}
