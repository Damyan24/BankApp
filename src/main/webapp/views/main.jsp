<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <script src="https://kit.fontawesome.com/67939a765b.js"></script>
</head>

<body>
    <header>
        <div class="logo">
            <i class="fas fa-building"></i>
            <p>BankApp</p>
        </div>

        <form action="${pageContext.request.contextPath}/LogoutServlet" method="post">
            <button type="submit"><i class="fa-solid fa-right-from-bracket"></i></button>
        </form>
    </header>

    <div class="main">
        <div class="left">
            <h1>Info:</h1> <br>

            <div class="info">
                <p class="name">${User.getFirstName() } ${User.getLastName()}</p>

                <p class="balance">Balance: ${User.getBalance()}$</p>
            </div>

            <div class="send">
                <h1>Send Money:</h1> <br>
                <form action="${pageContext.request.contextPath}/TransferServlet" method="post">
                    <p>Email:</p> <br/>
                    <input type="text" name="receiver">
                    <br />

                    <p>Amount<span>(10 min)</span>:</p> <br/>
                    <input type="number" name="amount" min="10" step="0.01">
                    <br />
                    <input class="button" type="submit" value="Submit" />
                    <c:if test="${not empty TransferError}">
                        <div style='color: red; font-weight:bold; width:100%; text-align:center;'>
                            - ${TransferError}<br>
                        </div>
                    </c:if>
                </form>
            </div>
        </div>

        <div class="right">
            <h1>Your Transfers:</h1>

            <table>
    <tr>
        <th>Amount</th>
        <th>Bank Account</th>
        <th>Date</th>
    </tr>
    <c:forEach var="transaction" items="${User.getTransactions()}" varStatus="status" begin = "0" end = "9">
        <tr>
            <td style="color: ${transaction.getTransactionAmount() ge 0 ? 'green' : 'red'};">
                ${transaction.getTransactionAmount()}
            </td>
            <td>${transaction.getReceiver()}</td>
            <td>${transaction.getTransactionDate()}</td>
        </tr>
    </c:forEach>
   
</table>
            
        </div>
    </div>
</body>

</html>
