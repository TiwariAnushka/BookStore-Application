<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Books Store Application</title>
	<style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        .logout-btn {
            position: fixed;
            top: 10px;
            right: 10px;
            text-decoration: none;
            padding: 10px;
            color: white;
            background-color: black;
            border: 1px solid #007bff;
            border-radius: 5px;
        }

        h1, h2 {
            color: #007bff;
        }

        table {
            width: 80%;
            margin-top: 20px;
            border-collapse: collapse;
        }

        th, td {
            border: 1px solid #dee2e6;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: #fff;
        }

        iframe {
            border: 1px solid #dee2e6;
        }
    </style>
</head>
<body>
	<div class="logout-btn">
		<a href="Login.jsp">Logout</a>
	</div>
	<center>
		<h1>Books Store</h1>
        <h2>
        	<form action="search" method="GET">
	            <label for="title">Enter Title:</label>
	            <input type="text" name="title" id="title" required />
	            <button type="submit">Search</button>
	        </form>
	        <form action="usercategorylist" method="GET">
				Category: <select name="category">
					<option value="fictional">Fictional</option>
					<option value="mythological">Mythological</option>
					<option value="study">Study</option>
					<option value="others">Others</option>
				</select>
				<input type="submit" value="Submit">
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
                <th>PDF</th>
                <th>Action</th>  <%--Added column for download button --%>
                <th>Category</th>
  			</tr>
            </tr>
            <c:forEach var="book" items="${listBook}">
                <tr>
                    <td><c:out value="${book.id}" /></td>
                    <td><c:out value="${book.title}" /></td>
                    <td><c:out value="${book.author}" /></td>
                    <%-- <td><c:out value="${book.price}" /></td>--%>
                    <td>
					    <iframe src="<c:url value='showPdf?id=${book.id}' />" width="400" height="200"></iframe>
					</td>
					<td>
                        <!-- Download button -->
                        <a href="<c:url value='downloadPdf?id=${book.id}' />" class="download-btn" target="_blank">Download PDF</a>
                    </td>
                    <td><c:out value="${book.category}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>	
</body>
</html>
