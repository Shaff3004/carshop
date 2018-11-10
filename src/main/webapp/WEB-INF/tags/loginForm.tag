<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="w3-card w3-round pic">
        <div class="w3-container profile">
        <c:choose>
            <c:when test="${sessionScope.user ne null}">
            <h4 class="w3-center">${sessionScope.user.name}</h4>
                <div class="avatarDiv">
                    <img class="avatarImg" alt="Avatar" src = "<c:url value="/avatar"/>">
                </div>
                <details align="center">
                    <summary>Choose your avatar</summary>
                        <form action="avatar" method="post" enctype="multipart/form-data">
                            <input type="file" name="avatar" required accept="image/*">
                            <input type="submit" value="Change">
                        </form>
                </details>
                <button type="button"><a href="<c:url value="/logout"/>"><fmt:message key="homePage.button.logout"/></a></button>
             </c:when>
             <c:otherwise>
                <form action="login" method="post" name="loginForm">
                    <input type="text" name="login" placeholder="Your login">
                    <input type="password" name="password" placeholder="Your password">
                    <input type="submit" value="Submit">
                </form>
             </c:otherwise>
        </c:choose>
        </div>
    </div>