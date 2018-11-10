<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="../captcha.tld"%>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login</title>
    <link href="<c:url value="//fonts.googleapis.com/css?family=Niconne&amp;subset=latin-ext"/>" rel="stylesheet">
    <link href="<c:url value="//fonts.googleapis.com/css?family=Reem+Kufi&amp;subset=arabic"/>" rel="stylesheet">
    <link href="<c:url value="/style/css/style_registration.css"/>" rel="stylesheet" type="text/css" media="all"/>

    <script type="text/javascript" src="<c:url value="/style/js/jquery-2.1.4.min.js"/>"></script>
    <script src="<c:url value="/style/js/jquery.vide.min.js"/>"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/style/js/own/jquery-validation.js"/>"></script>
</head>
<body>
<div class="video size" data-vide-bg="style/video/daimler">
<customTag:localization/>
    <h1 id="heading">Login</h1>
    <div class="main-agilerow">
        <div id="Login" class="sub-w3lsright agileits-w3layouts tabcontent">
            <form class="loginForm" name="login_form" action="login" method="post">
                <div id="loginError"></div>
                <input type="text" class="name" name="login" placeholder="Your login">
                <input type="password" class="inp_password" name="password" placeholder="Password">
                <input type="text" name="captcha" placeholder="Enter captcha"><br>
                <my:captcha captchaId="${requestScope.captchaId}"/>
                <br><br>

                <div class="clear"></div>
                <div class="form-control"></div>
                <div class="clear"></div>
                <input type="submit" value="Log in"><br>
                <a class="leftSide" href="home">Back home</a>
            </form>
        </div>
    </div>
</div>
</body>
</html>
