<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.currentPage > 1}">
    <li>
        <a id="previous" href="#" onmouseover="getModifiedUrl(${requestScope.currentPage}, -1)">Previous</a>
    </li>
</c:if>