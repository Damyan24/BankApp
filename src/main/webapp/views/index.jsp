<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <script src="https://kit.fontawesome.com/67939a765b.js"></script>
  	<link rel="stylesheet" type="text/css" href="/Bank/css/index.css">
</head>

<body>
	<header>
	<div class="logo">
        <i class="fas fa-building"></i>
        <p>BankApp</p>
    </div>
	</header>
   
	<h1>Login</h1>	
    <div class="form">
        <form action="${pageContext.request.contextPath}/LoginServlet" method="POST">
            <p>Email:</p> <br/>
            <input type="text" name="email">
            <br />
            <p>Password:</p> <br/>
            <input type="password" name="password" />
            <br/>
            <input class = "button" type="submit" value="Submit" />
        </form>
    </div>
    
    <p class = "signup">Don't have an account yet? &#160<a href="/Bank/views/register.jsp">Sign Up</a> </p>
</body>
</html>