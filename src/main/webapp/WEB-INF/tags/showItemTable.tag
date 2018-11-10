<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<c:url value="/style/js/own/cart_ajax.js"/>" type="text/javascript"></script>
<script src="<c:url value="/style/js/own/home.js"/>" type="text/javascript"></script>

<c:choose>
    <c:when test="${requestScope.cars == null}">
        <b>Enter search parameters</b>
    </c:when>
    <c:when test="${empty requestScope.cars}">
        <b>Cars with current parameters not found</b>
    </c:when>

    <c:otherwise>
        <table>
            <tr>
                <th>Mark</th>
                <th>Model</th>
                <th>Type</th>
                <th>Year</th>
                <th>Max speed</th>
                <th>Price</th>
                <th>Action</th>
            </tr>
            <c:forEach var="car" items="${requestScope.cars}">
                <tr>
                    <td>${car.mark}</td>
                    <td>${car.model}</td>
                    <td>${car.type}</td>
                    <td>${car.year}</td>
                    <td>${car.maxSpeed}</td>
                    <td>${car.price}</td>
                    <td>
                        <input class="button" type="submit" value="Add to cart" onclick="addItemToCart(${car.id}); notifyMessage() ">
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>