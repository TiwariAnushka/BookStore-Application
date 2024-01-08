package net.codejava.javaee.bookstore;

import java.io.InputStream;

public class Book {
	protected int id;
	protected String title;
	protected String author;
//	protected float price;
	protected InputStream pdfFile;
	protected String category;
	public Book() {
	}

	public Book(int id) {
		this.id = id;
	}

	public Book(int id, String title, String author, String category/*, float price*/) {
		this(title, author, category/*, price*/);
		this.id = id;
//		this.category = category;
	}
	
	public Book(String title, String author, String category/*, float price*/) {
		this.title = title;
		this.author = author;
//		this.price = price;
		this.category = category;
	}

	public Book(int id, String title, String author, String category/*, float price*/, InputStream pdfFile) {
		this(id, title, author, category/*, price*/);
		this.pdfFile = pdfFile;
//		this.category = category;
	}
	
	public InputStream getPdfFile() {
		return pdfFile;
	}
	
	public Book(String title, String author, String category/*, float price*/, InputStream pdfFile) {
		super();
		this.title = title;
		this.author = author;
//		this.price = price;
		this.pdfFile = pdfFile;
		this.category = category;
}

	public void setPdfFile(InputStream pdfFile) {
		this.pdfFile = pdfFile;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

//	public float getPrice() {
//		return price;
//	}

//	public void setPrice(float price) {
//		this.price = price;
//	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
}
