CREATE DATABASE 'Bookstore';
USE Bookstore;

CREATE TABLE book (
    book_id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL UNIQUE,
    author VARCHAR(255) NOT NULL,
    price FLOAT(10, 2) NOT NULL,
    PRIMARY KEY (book_id)
);

