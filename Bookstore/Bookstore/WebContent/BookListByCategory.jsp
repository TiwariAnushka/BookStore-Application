<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Books Store Application</title>
	<style>
		body {
			font-family: Arial, sans-serif;
			background-color: #f4f4f4;
			margin: 0;
			padding: 0;
		}

		.logout-btn {
			position: fixed;
			top: 10px;
			right: 10px;
			text-decoration: none;
			background-color: black;
			color: white;
			padding: 5px 10px;
			border-radius: 5px;
		}

		center {
			margin-top: 30px;
		}

		h1 {
			color: #333;
		}

		h2 {
			color: #007bff;
		}

		form {
			background-color: #fff;
			padding: 20px;
			border-radius: 5px;
			box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
			width: 300px;
		}

		table {
			width: 100%;
			border-collapse: collapse;
			margin-top: 20px;
		}

		th, td {
			border: 1px solid #ddd;
			padding: 10px;
			text-align: left;
		}

		th {
			background-color: #f2f2f2;
		}

		a {
			text-decoration: none;
			color: #007bff;
		}

		a:hover {
			text-decoration: underline;
		}

		iframe {
			border: none;
		}
	</style>
</head>
<body>
	<div class="logout-btn">
		<a href="Login.jsp">Logout</a>
	</div>
	<center>
        <h1>Book Store</h1>
        <h2>Your Search Result Is Here</h2>
    </center>
	<center>
        <h2>
        	<a href="new">Register Book</a>
        	&nbsp;&nbsp;&nbsp;
        	<a href="list">Display Books</a>
        	&nbsp;&nbsp;&nbsp;
        	<form action="search" method="GET">
	            <label for="title">Enter Title:</label>
	            <input type="text" name="title" id="title" required />
	            <button type="submit">Search</button>
	        </form>
        </h2> 
	</center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Display Books</h2></caption>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <%-- <th>Price</th>--%>
                <th>Actions</th>
                <th>PDF</th>
                <th>Category</th>
            </tr>
            <c:forEach var="book" items="${listBook}">
                <tr>
                    <td><c:out value="${book.id}" /></td>
                    <td><c:out value="${book.title}" /></td>
                    <td><c:out value="${book.author}" /></td>
                    <%--<td><c:out value="${book.price}" /></td>--%>
                    <td>
                    	<a href="edit?id=<c:out value='${book.id}' />">Edit</a>
                    	&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="delete?id=<c:out value='${book.id}' />">Delete</a>                    	
                    </td>
                    <td>
					    <iframe src="<c:url value='showPdf?id=${book.id}' />" width="400" height="200"></iframe>
					</td>
					<td><c:out value="${book.category}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
