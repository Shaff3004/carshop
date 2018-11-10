<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" uri="../captcha.tld" %>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Registration</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="keywords"
          content="Car Rental Form Responsive Widget,Login form widgets, Sign up Web forms , Login signup Responsive web form,Flat Pricing table,Flat Drop downs,Registration Forms,News letter Forms,Elements"/>
    <script type="application/x-javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <link href="<c:url value="//fonts.googleapis.com/css?family=Niconne&amp;subset=latin-ext"/>" rel="stylesheet">
    <link href="<c:url value="//fonts.googleapis.com/css?family=Reem+Kufi&amp;subset=arabic"/>" rel="stylesheet">
    <link href="<c:url value="/style/css/style_registration.css"/>" rel="stylesheet" type="text/css" media="all"/>

    <script type="text/javascript" src="<c:url value="/style/js/jquery-2.1.4.min.js"/>"></script>
    <script src="<c:url value="/style/js/jquery.vide.min.js"/>"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/style/js/own/jquery-validation.js"/>"></script>
    <script type ="text/javascript" src ="<c:url value="/style/js/ajax.js"/>"></script>
</head>
<body>

<div class="video" data-vide-bg="style/video/daimler">

<customTag:localization/>
    <h1 id="heading">Registration</h1>
    <!-- main -->
    <div class="main-agilerow">
        <div class="sub-w3lsright agileits-w3layouts">



            <form class="registerForm" name="registration_form" action="registration" method="post" enctype="multipart/form-data">
                <div id="error">
                    <c:if test="${not empty sessionScope}">
                        <c:forEach var="error" items="${sessionScope.errors}">
                            <b>${error}</b>
                        </c:forEach>
                    </c:if>
                    <b id="loginExists"></b><br>
                    <b id="emailExists"></b>
                </div>
                <input type="text" class="name" name="name"
                       placeholder="<c:if test="${empty sessionScope.bean}">Your name</c:if>"
                       value="<c:if test="${sessionScope.bean ne null}">${sessionScope.bean.name}</c:if>">
                <input id="login" type="text" onchange="checkLogin(this.value)" class="name" name="login"
                       placeholder="<c:if test="${empty sessionScope.bean}">Your login</c:if>"
                       value="<c:if test="${sessionScope.bean ne null}">${sessionScope.bean.login}</c:if>">
                <input type="email" onchange="checkEmail(this.value)" name="email" placeholder="<c:if test="${empty sessionScope.bean}">Your email</c:if>"
                       value="<c:if test="${sessionScope.bean ne null}">${sessionScope.bean.email}</c:if>">
                <input type="password" class="inp_password" name="password" placeholder="Password">
                <input type="password" class="inp_password" name="confirmPassword" placeholder="Confirm password">
                <input type="text" name="captcha" placeholder="Enter captcha"><br>
                <my:captcha captchaId="${requestScope.captchaId}"/>
                <br><br>
                <label class="container">Male:
                    <input type="radio" name="gender" value="male"
                           <c:if test="${sessionScope.bean.gender == 'MALE'}">checked</c:if>>
                    <span class="checkmark"></span>
                </label>
                <label class="container">Female:
                    <input type="radio" name="gender" value="female"
                           <c:if test="${sessionScope.bean.gender == 'FEMALE'}">checked</c:if>>
                    <span class="checkmark"></span>
                </label>
                <br><br>
                <label class="containerCheck">Join newsletter:
                    <input type="checkbox" name="newsletter"
                           <c:if test="${sessionScope.bean.newsletter == 'on'}">checked</c:if>>
                    <span class="checkmarkBox"></span>
                </label>
                <input type="file" name="avatar" accept="image/*">
                <div class="clear"></div>
                <div class="form-control">
                </div>
                <div class="clear"></div>
                <input id="submit" type="submit" value="Register now"><br>
                <a class="leftSide" href="home">Back home</a>
            </form>
        </div>
    </div>
    <div class="copyw3-agile">
        <p> © 2017 Car Rental Form. All rights reserved | Design by <a href="http://w3layouts.com/" target="_blank">W3layouts.</a>
        </p>
    </div>
</div>
</body>
</html>