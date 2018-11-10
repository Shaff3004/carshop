<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach begin="1" end="${requestScope.numberOfPages}" var="i">
    <c:choose>
        <c:when test="${requestScope.currentPage eq i}">
            <li>
                <a class="choosedPage">${i}</a>
            </li>
        </c:when>

        <c:otherwise>
            <li>
                <a id="page${i}" class="pagingLink" href="#" onmouseover="getModifiedUrl(${i}, 0)">${i}</a>
            </li>
        </c:otherwise>
    </c:choose>
</c:forEach>