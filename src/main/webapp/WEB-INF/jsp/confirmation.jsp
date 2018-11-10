<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Confirm order</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="<c:url value="/style/css/cart.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<c:url value="/style/css/order_confirmation.css"/>" rel="stylesheet" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/style/js/own/jquery-validation.js"/>"></script>
</head>
<body>
<customTag:localization/>
<div class="header">
    <div class="row">
        <div class="col-75">
            <div id="confirmationError"></div>
            <div class="container">
                <form action="confirm_order" method="post" name="confirmation">
                    <div class="row">
                        <div class="col-50">
                            <h3>Billing Address</h3>
                            <br><br>
                            <label for="fname"><i class="fa fa-user"></i> Full Name</label>
                            <input type="text" id="fname" name="firstname" placeholder="Enter name"
                                   value="<c:if test="${not empty sessionScope.user}">${sessionScope.user.name}</c:if>">
                            <label for="email"><i class="fa fa-envelope"></i> Email</label>
                            <input type="text" id="email" name="email" placeholder="Enter email"
                                   value="<c:if test="${not empty sessionScope.user}">${sessionScope.user.email}</c:if>">
                            <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
                            <input type="text" id="adr" name="address" placeholder="Enter address">
                        </div>
                        <div class="col-50">
                            <h3>Payment</h3>
                            <br><br>
                            <label for="ccnum">Credit card number</label>
                            <input type="text" id="ccnum" name="cardnumber" placeholder="Enter card name">
                            <label for="expmonth">Exp Month</label>
                            <input type="text" id="expmonth" name="expmonth" placeholder="Enter exp month">
                            <div class="row">
                                <div class="col-50">
                                    <label for="expyear">Exp Year</label>
                                    <input type="text" id="expyear" name="expyear" placeholder="Enter exp year">
                                </div>
                                <div class="col-50">
                                    <label for="cvv">CVV</label>
                                    <input type="password" id="cvv" name="cvv" placeholder="Enter CVV">
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="submit" value="Confirm order" class="btn">
                </form>
            </div>
        </div>
        <div class="col-25">
            <div class="container">
                <h4>Cart <span class="price" style="color:black"><i
                        class="fa fa-shopping-cart"></i> <b>${sessionScope.cart.getTotalItemCount()}</b></span>
                </h4>
                <c:forEach var="item" items="${sessionScope.cartContent}">
                    <p><b>${item.key.mark} ${item.key.model} x ${item.value}</b> <span class="price">$${item.key.price}</span></p>
                </c:forEach>
                <hr>
                <p>Total <span class="price" style="color:black"><b>$${sessionScope.cart.getTotalCost()}</b></span></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>