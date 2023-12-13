<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
    <script src="https://kit.fontawesome.com/67939a765b.js"></script>
</head>

<body>
    <header>
        <div class="logo">
            <i class="fas fa-building"></i>
            <p>BankApp</p>
        </div>
    </header>

    <h1>Registration</h1>
    <div class="form">
        <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
            <p>Email:</p> <br/>
            <input type="text" name="email">
            <br/>
            <p>Password<span class="lower">(8+ characters and 1 upper case)</span>:</p> <br/>
            <input type="password" name="password"/>
            <br/>
            <p>First Name:</p> <br/>
            <input type="text" name="fname">
            <p>Last Name:</p> <br/>
            <input type="text" name="lname">
            <p>Birthday<span class="lower">(Should be at least 18)</span>:</p> <br/>
            <input type="date" id="birthday" name="birthday">
            <br/>
            <input class="button" type="submit" value="Submit"/>
        </form>
         <c:if test="${not empty TransferError}">
    <div style='color: red; font-weight:bold; width:100%; text-align:center;'>
        - ${TransferError}<br>
    </div>
</c:if>
    </div>
    <p class="signup">Already have an account? &#160<a href="${pageContext.request.contextPath}/views/index.jsp">Login</a></p>
   
</body>
</html>
