<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Error</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href='<c:url value="//fonts.googleapis.com/css?family=Playball"/>' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/style/css/style.css"/>" rel="stylesheet" type="text/css" media="all"/>
</head>
<body>
<customTag:localization/>
<div class="header-bg">
    <div class="wrap">
        <div class="h-bg">
            <div class="total">
                <div class="banner-top">
                    <div class="header-bottom">
                        <div class="page-not-found">
                            <c:forEach var="error" items="${requestScope.errors}">
                                <b>${error}</b>
                            </c:forEach>
                        </div>
                        <div class="footer-bottom">
                            <div class="copy">
                                <p>&copy 2013 Cars Online . All rights Reserved | Design by <a
                                        href="http://w3layouts.com">W3Layouts</a></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
