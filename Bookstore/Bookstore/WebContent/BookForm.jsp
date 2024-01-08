<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

input[type="text"], input[type="password"], input[type="file"], input[type="submit"]
	{
	width: 100%;
	padding: 10px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
	border-radius: 3px;
}

input[type="submit"] {
	background-color: #007bff;
	color: white;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #0056b3;
}
</style>
<script>
	function validateForm() {
		var author = document.forms["bookForm"]["author"].value;
		var title = document.forms["bookForm"]["title"].value;
<%--var price = document.forms["bookForm"]["price"].value;--%>
	var pdfFile = document.forms["bookForm"]["pdfFile"].files[0];
		// Validate Author (should only contain letters)
		if (!/^[a-zA-Z\s]+$/.test(author)) {
			alert("Author must contain only letters and spaces.");
			return false;
		}

		// Validate Title (should not be a number)
		if (!isNaN(title)) {
			alert("Title cannot be a number.");
			return false;
		}

		// Validate Price (should not be negative)
<%--if (parseFloat(price) < 0) {
                alert("Price cannot be negative.");
                return false;
            }--%>
	if (pdfFile) {
			var fileName = pdfFile.name.toLowerCase();
			if (!fileName.endsWith(".pdf")) {
				alert("Please select a PDF file.");
				return false;
			}
		}
		return true;
	}
</script>
</head>
<body>
	<%-- <%@ page import="net.codejava.javaee.bookstore.Book"%>

	<c:if test="${not empty book}">
		<c:out value="Book is not null" />
	</c:if>
	<c:if test="${empty book}">
		<c:out value="Book is null" />
	</c:if>--%>
	<div class="logout-btn">
		<a href="Login.jsp">Logout</a>
	</div>
	<center>
		<h1>Books Store</h1>
		<h2>
			<a href="new">Register Book</a> &nbsp;&nbsp;&nbsp; <a href="list">Display
				Books</a>
		</h2>
	</center>
	<div align="center">
		<form name="bookForm"
			action="<c:if test='${book != null}'>update</c:if><c:if test='${book == null}'>insert</c:if>"
			method="post" enctype="multipart/form-data"
			onsubmit="return validateForm()">
			<table border="1" cellpadding="5">
				<caption>

					<h2>
						<c:if test="${book != null}">
                            Edit Book
                        </c:if>
						<c:if test="${book == null}">
                            Register Book
                        </c:if>
					</h2>

				</caption>

				<c:if test="${not empty id}">
					<input type="hidden" name="id" value="${id}" />
				</c:if>

				<tr>
					<th>Title:</th>
					<td><input type="text" name="title"
						placeholder="Enter Character Values Only" size="45"
						value="<c:out value='${book.title}' />" /></td>
				</tr>

				<tr>
					<th>Author:</th>
					<td><input type="text" name="author" size="45"
						value="<c:out value='${book.author}' />" /></td>
				</tr>
				<%-- <tr>
                    <th>Price: </th>
                    <td>
                        <input type="text" placeholder="Enter Positive Values Only" name="price" size="5" value="<c:out value='${book.price}' />" />
                    </td>
                </tr>
                <tr>
                    <th>PDF File: </th>
                    <td>
                        <input type="file" name="pdfFile" accept=".pdf" />
                    </td>
                </tr>--%>

				<%-- <tr>
                    <th>PDF File: </th>
                    <td>
                        <c:choose>
                            <c:when test="${not empty book and not empty book.pdfFile}">
                                <a href="<c:url value='/showPdf?id=${book.id}' />" target="_blank">View Existing PDF</a>
                            </c:when>
                            <c:otherwise>
                                <input type="file" name="pdfFile" accept=".pdf" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>--%>
				<tr>
					<th>PDF File:</th>
					<td><c:choose>
							<c:when test="${not empty book and not empty book.pdfFile}">
								<a href="<c:url value='/showPdf?id=${pdfId}' />" target="_blank">View
									Existing PDF</a>
								<input type="file" name="pdfFile" value="${book.id}" />
							</c:when>
							<c:otherwise>
								<input type="file" name="pdfFile" accept=".pdf" />
							</c:otherwise>
						</c:choose></td>
				</tr>

				<%-- Category:
				<select name="category">
					<option value="fictional">Fictional</option>
					<option value="mythological">Mythological</option>
					<option value="study">Study</option>
					<option value="others">Others</option>
				</select>
				<input type="submit" value="Submit">--%>
				<tr>
					<th>Category:</th>
					<td><select name="category">
							<option value="fictional"
								${category eq 'fictional' ? 'selected' : ''}>Fictional</option>
							<option value="mythological"
								${category eq 'mythological' ? 'selected' : ''}>Mythological</option>
							<option value="study" ${category eq 'study' ? 'selected' : ''}>Study</option>
							<option value="others" ${category eq 'others' ? 'selected' : ''}>Others</option>
					</select></td>
				</tr>
				<br>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="Save" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>

<!--  <style>
     
        .logout-btn {
            position: fixed;
            top: 10px;
            right: 10px;
        }
</style>-->