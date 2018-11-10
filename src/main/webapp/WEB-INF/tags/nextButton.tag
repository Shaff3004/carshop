<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.currentPage lt requestScope.numberOfPages}">
    <li>
        <a id="next" href="#" onmouseover="getModifiedUrl(${requestScope.currentPage}, 1)">Next</a>
    </li>
</c:if>