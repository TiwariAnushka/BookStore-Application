<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sign-Up</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #f4f4f4;
	margin: 0;
	padding: 0;
}

center {
	margin-top: 50px;
}

h1 {
	color: #333;
}

form {
	background-color: #fff;
	padding: 20px;
	border-radius: 5px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	width: 300px;
}

input[type="text"], input[type="password"], select {
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
	padding: 12px 20px;
	border: none;
	border-radius: 3px;
	cursor: pointer;
}

input[type="submit"]:hover {
	background-color: #0056b3;
}
</style>
</head>
<body>
	<center>
		<h1>Sign Up</h1>
		<form action="RegisterServlet" method="post">
			Username: <input type="text" name="username" required><br>
			Password: <input type="password" name="password" required><br>
			<input type="hidden" name="role" value="user">
			<input type="submit" value="Register">
		</form>
	</center>
</body>
</html>