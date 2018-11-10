<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/style/css/cart.css"/>" rel="stylesheet" type="text/css" media="all"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="<c:url value="/style/js/cart.js"/>"></script>
<script src="<c:url value="/style/js/own/cart_ajax.js"/>"></script>

<c:choose>
<c:when test="${not empty sessionScope.cartContent}">
<c:forEach var="item" items="${sessionScope.cartContent}">
        <div id="close${item.key.id}" class="product-section">
            <div class="product-top">
                <div class="product-right">
                    <h2>${item.key.mark} ${item.key.model}</h2>
                    <h5>Max speed: ${item.key.maxSpeed}</h5>
                    <ul class="size">
                        <li>${item.key.year}</li>
                    </ul>
                </div>
            </div>
            <div class="product-middle">
                <b id="quantity${item.key.id}">${item.value}</b>
                <button type="submit" class="button green" onclick="addItemToCart(${item.key.id}, ${item.key.price})">+</button>
                <button type="submit" class="button red" onclick="reduceQuantity(${item.key.id}, ${item.key.price})">-</button>
            </div>
            <div class="product-right1">
                <b id="price${item.key.id}">${item.key.price * item.value}</b>
                <div class="close" onclick="deleteItemFromCart(${item.key.id})"></div>
            </div>
            <div class="clear"></div>
        </div>
</c:forEach>
</c:when>
<c:otherwise>
    <b>Your cart is empty</b>
</c:otherwise>
</c:choose>
        <div class="product-bottom">
                        <h3 id="total">summary : <span> ${sessionScope.cart.getTotalCost()}</span></h3>
                        <form action="cart" method="post">
                            <input type="submit" value="Checkout">
                        </form>
                        <div class="clear">
                        </div>
                        <a href="<c:url value="/home"/>"><< Home</a>
        </div>