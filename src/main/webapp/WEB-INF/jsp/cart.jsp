<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<customTag:localization/>
    <title>Cart</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords"
          content="Flash Registration Form  Responsive, Login form web template, Sign up Web Templates, Flat Web Templates, Login signup Responsive web template, Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design"/>
    <link href='<c:url value="//fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic"/>'
          rel='stylesheet' type='text/css'>
    <link href='<c:url value="//fonts.googleapis.com/css?family=Montserrat:400,700"/>' rel='stylesheet' type='text/css'>
    <link href='<c:url value="//fonts.googleapis.com/css?family=Yanone+Kaffeesatz:400,700,300,200"/>' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/style/css/cart.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="<c:url value="/style/js/cart.js"/>"></script>

</head>
<body>
<div class="header">
    <h1>Products In Cart</h1>
</div>
<div class="main">
    <div class="main-section">

        <customTag:showCartItems/>
    </div>
    <div class="footer">
        <p>&copy 2016 Products In Cart . All rights reserved | Design by <a href="http://w3layouts.com">W3layouts</a>
        </p>
    </div>
</div>
</body>
</html>
