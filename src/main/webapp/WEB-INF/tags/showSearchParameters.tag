<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:if test="${requestScope.itemCount gt 0}">
    <b>Found ${requestScope.itemCount} items</b>
    <b>with current parameters: </b>
        <ul>
            <c:forEach var="parameter" items="${requestScope.searchBean.singleParameters}">
                <c:if test="${not empty parameter.getValue()}">
                    <li>
                        ${parameter.getKey()}: ${parameter.getValue()}
                    </li>
                </c:if>
            </c:forEach>

            <c:forEach var="parameter" items="${requestScope.searchBean.rangeParameters}">
                <c:if test="${not empty parameter.getValue()}">
                    <li>
                        ${parameter.getKey()} from ${parameter.getValue().getMinimum()} to ${parameter.getValue().getMaximum()}
                    </li>
                </c:if>
            </c:forEach>
        </ul>
    </c:if>
</div>