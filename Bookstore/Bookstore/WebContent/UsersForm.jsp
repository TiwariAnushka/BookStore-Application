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

        a {
            text-decoration: none;
            color: #007bff;
        }

        a:hover {
            text-decoration: underline;
        }

        form {
            margin-top: 20px;
        }

        label {
            margin-right: 5px;
        }

        input, button {
            padding: 8px;
            margin-right: 10px;
            border: 1px solid #007bff;
            border-radius: 5px;
            font-size: 16px;
        }

        button {
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
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
        	<a href="list1">Display Books</a>
        	&nbsp;&nbsp;&nbsp;
        	<form action="search" method="GET">
	            <label for="title">Enter Title:</label>
	            <input type="text" name="title" id="title" required />
	            <button type="submit">Search</button>
	        </form>
        </h2>
	</center>	
</body>
</html>
