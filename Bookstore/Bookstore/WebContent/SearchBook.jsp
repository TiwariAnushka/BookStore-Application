<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Book</title>
    <style>
        body {
            font-family: Arial, sans-serif;
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
<body >
	<div class="logout-btn">
		<a href="Login.jsp">Logout</a>
	</div>
	<center>
        <h1>Book Store</h1>
        <h2>Your Search Result Is Here</h2>
    </center>

    <div align="center">
        <h2>Search Result</h2>
        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Author</th>
                <%--<th>Price</th>--%>
                <th>PDF</th>
                <th>Action</th>
            </tr>
            <c:if test="${getBook != null}">
                <tr>
                    <td><c:out value="${getBook.id}" /></td>
                    <td><c:out value="${getBook.title}" /></td>
                    <td><c:out value="${getBook.author}" /></td>
                    <%-- <td><c:out value="${getBook.price}" /></td>--%>
                    <td>
					    <iframe src="<c:url value='showPdf?id=${getBook.id}' />" width="400" height="200"></iframe>
					</td>
					<td>
                        <!-- Download button -->
                        <a href="<c:url value='downloadPdf?id=${getBook.id}' />" class="download-btn" target="_blank">Download PDF</a>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
</body>
</html>