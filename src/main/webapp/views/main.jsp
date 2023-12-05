<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="https://kit.fontawesome.com/67939a765b.js"></script>
</head>

<body>
    <header>
        <div class="logo">
            <i class="fas fa-building"></i>
            <p>BankApp</p>
        </div>
    </header>


	<div class = "left">
	<h1>Info:</h1> <br>
	
	<div class = "info">
	<p class = "name">
	${User.getFirstName() } ${User.getLastName()} </p>
	
	<p class = "balance">
	Balance: ${User.getBalance()}$
	</p>
	</div>
	
	<div>
	</div class = "send">
	
	</div>
    
   
</body>
</html>
