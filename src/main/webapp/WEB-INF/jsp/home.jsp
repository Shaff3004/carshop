<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="customTag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<html>
<head>
    <title>Welcome</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href='<c:url value="//fonts.googleapis.com/css?family=Playball"/>' rel='stylesheet' type='text/css'>
    <link href="<c:url value="/style/css/style.css"/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<c:url value="/style/css/avatar_style.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/style/css/rangeslider.css"/>" rel="stylesheet" type="text/css">
    <script src="<c:url value="/style/js/jquery-2.1.4.min.js"/>"></script>
    <script src="<c:url value="/style/js/script.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/style/js/own/home.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/style/js/own/cart_ajax.js"/>" type="text/javascript"></script>
</head>
<body onload="dropAllTabs()">

<fmt:setBundle basename="resources"/>
<customTag:loginForm/>
<div class="header-bg">
    <div class="wrap">
        <div class="h-bg">
            <div class="total">
                <div class="header">
                    <div class="box_header_user_menu">
                        <ul class="user_menu">
                            <li class="act first"><a href="#">
                                <div class="button-t"><span><fmt:message key="homePage.tab.shipping"/></span></div>
                            </a></li>
                            <li class=""><a href="#">
                                <div class="button-t"><span><fmt:message key="homePage.tab.advancedSearch"/></span></div>
                            </a></li>
                            <li class=""><a href="<c:url value="/registration"/>">
                                <div class="button-t"><span><fmt:message key="homePage.tab.createAccount"/></span></div>
                            </a></li>
                            <li class="last"><a href="<c:url value="/login"/>">
                                <div class="button-t"><span><fmt:message key="homePage.tab.login"/></span></div>
                            </a></li>
                        </ul>
                    </div>
                    <div class="header-right">
                        <ul class="follow_icon">
                            <li><a href="<c:url value="/cart"/>"><img class="cart-img"
                                                         src="<c:url value="/style/images/cart-icon.jpg"/>"></a>
                            </li>

                            <customTag:localization/>
                        </ul>
                    </div>
                    <div class="clear"></div>
                    <div class="header-bot">
                        <div class="logo">
                            <a href="../../index.html"><img src="<c:url value="/style/images/logo.png"/>" alt=""/></a>
                        </div>
                        <div class="search">
                            <input type="text" class="textbox" value="" onfocus="this.value = '';"
                                   onblur="if (this.value == '') {this.value = '';}">
                            <button class="gray-button"><span><fmt:message key="homePage.button.search"/></span></button>
                        </div>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="menu">
                    <div class="top-nav tab">
                        <ul>
                            <li>
                                <button id="defaultOpen" class="tablinks specTab" onclick="openTab(event, 'Home'); rememberButton(0)">
                                    <fmt:message key="homePage.tab.home"/>
                                </button>
                            </li>
                            <li>
                                <button class="tablinks specTab" onclick="openTab(event, 'Specials'); rememberButton(1)"><fmt:message key="homePage.tab.specials"/></button>
                            </li>

                        </ul>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="banner-top">
                    <div class="header-bottom">
                        <div id="Home" class="header_bottom_right_images tabcontent">
                            <div id="slideshow">
                                <ul class="slides">
                                    <li><a href="">
                                        <canvas></canvas>
                                        <img src="<c:url value="/style/images/banner4.jpg"/>"
                                             alt="Marsa Alam underawter close up"></a>
                                    </li>
                                    <li><a href="">
                                        <canvas></canvas>
                                        <img src="<c:url value="/style/images/banner2.jpg"/>"
                                             alt="Turrimetta Beach - Dawn"></a></li>
                                    <li><a href="">
                                        <canvas></canvas>
                                        <img src="<c:url value="/style/images/banner3.jpg"/>" alt="Power Station"></a>
                                    </li>
                                    <li><a href="">
                                        <canvas></canvas>
                                        <img src="<c:url value="/style/images/banner1.jpg"/>"
                                             alt="Colors of Nature"></a></li>
                                </ul>
                                <span class="arrow previous"></span>
                                <span class="arrow next"></span>
                            </div>
                            <div class="content-wrapper">
                                <div class="content-top">
                                    <div class="box_wrapper"><h1>New Products For July</h1>
                                    </div>
                                    <div class="text">
                                        <div class="grid_1_of_3 images_1_of_3">
                                            <div class="grid_1">
                                                <a href=""><img src="<c:url value="/style/images/pic5.jpg"/>"
                                                                title="continue reading"></a>
                                                <div class="grid_desc">
                                                    <p class="title">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <p class="title1">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <div class="price" style="height: 19px;">
                                                        <span class="reducedfrom">$66.00</span>
                                                        <span class="actual">$12.00</span>
                                                    </div>
                                                    <div class="cart-button">
                                                        <div class="cart">
                                                            <a href="#"><img
                                                                    src="<c:url value="/style/images/cart.png"/>"/></a>
                                                        </div>
                                                        <button class="button"><span>Details</span></button>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="grid_1_of_3 images_1_of_3">
                                            <div class="grid_1">
                                                <a href=""><img src="<c:url value="/style/images/pic6.jpg"/>"
                                                                title="continue reading"></a>
                                                <div class="grid_desc">
                                                    <p class="title">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <p class="title1">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <div class="price" style="height: 19px;">
                                                        <span class="reducedfrom">$66.00</span>
                                                        <span class="actual">$12.00</span>
                                                    </div>
                                                    <div class="cart-button">
                                                        <div class="cart">
                                                            <a href="#"><img
                                                                    src="<c:url value="/style/images/cart.png"/>"/></a>
                                                        </div>
                                                        <button class="button"><span>Details</span></button>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="grid_1_of_3 images_1_of_3">
                                            <div class="grid_1">
                                                <a href=""><img src="<c:url value="/style/images/pic4.jpg"/>"
                                                                title="continue reading"></a>
                                                <div class="grid_desc">
                                                    <p class="title">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <p class="title1">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <div class="price" style="height: 19px;">
                                                        <span class="reducedfrom">$66.00</span>
                                                        <span class="actual">$12.00</span>
                                                    </div>
                                                    <div class="cart-button">
                                                        <div class="cart">
                                                            <a href="#"><img
                                                                    src="<c:url value="/style/images/cart.png"/>"/></a>
                                                        </div>
                                                        <button class="button"><span>Details</span></button>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                                <div class="content-top">
                                    <div class="box_wrapper"><h1>Featured Products</h1>
                                    </div>
                                    <div class="text">
                                        <div class="grid_1_of_3 images_1_of_3">
                                            <div class="grid_1">
                                                <a href=""><img src="<c:url value="/style/images/pic1.jpg"/>"
                                                                title="continue reading"></a>
                                                <div class="grid_desc">
                                                    <p class="title">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <p class="title1">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <div class="price" style="height: 19px;">
                                                        <span class="reducedfrom">$66.00</span>
                                                        <span class="actual">$12.00</span>
                                                    </div>
                                                    <div class="cart-button">
                                                        <div class="cart">
                                                            <a href="#"><img
                                                                    src="<c:url value="/style/images/cart.png"/>"/></a>
                                                        </div>
                                                        <button class="button"><span>Details</span></button>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="grid_1_of_3 images_1_of_3">
                                            <div class="grid_1">
                                                <a href=""><img src="<c:url value="/style/images/pic2.jpg"/>"
                                                                title="continue reading"></a>
                                                <div class="grid_desc">
                                                    <p class="title">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <p class="title1">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <div class="price" style="height: 19px;">
                                                        <span class="reducedfrom">$66.00</span>
                                                        <span class="actual">$12.00</span>
                                                    </div>
                                                    <div class="cart-button">
                                                        <div class="cart">
                                                            <a href="#"><img
                                                                    src="<c:url value="/style/images/cart.png"/>"/></a>
                                                        </div>
                                                        <button class="button"><span>Details</span></button>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="grid_1_of_3 images_1_of_3">
                                            <div class="grid_1">
                                                <a href=""><img src="<c:url value="/style/images/pic3.jpg"/>"
                                                                title="continue reading"></a>
                                                <div class="grid_desc">
                                                    <p class="title">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <p class="title1">Lorem ipsum dolor sitconsectetuer adipiscing
                                                        elit</p>
                                                    <div class="price" style="height: 19px;">
                                                        <span class="reducedfrom">$66.00</span>
                                                        <span class="actual">$12.00</span>
                                                    </div>
                                                    <div class="cart-button">
                                                        <div class="cart">
                                                            <a href="#"><img
                                                                    src="<c:url value="/style/images/cart.png"/>"/></a>
                                                        </div>
                                                        <button class="button"><span>Details</span></button>
                                                        <div class="clear"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </div>
                                        <div class="clear"></div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div id="Specials" class="header_bottom_right_images tabcontent">

                            <div class="content-wrapper">
                                <div class="content-top">
                                    <div class="about_wrapper">
                                        <select name="numberOfElements" form="searchForm">
                                            <option>5</option>
                                            <option>10</option>
                                            <option>20</option>
                                        </select>
                                    </div>

                                    <customTag:showSearchParameters/>
                                    <div class="text">
                                        <customTag:showItemTable/>
                                    </div>
                                </div>
                            </div>
                            <div class="paging">
                                <ul>
                                    <customTag:previousButton/>
                                    <customTag:showPageButtons/>
                                    <customTag:nextButton/>
                                </ul>
                            </div>
                        </div>

                        <div class="header-para">
                            <div class="box">
                                <div class="box-heading"><h1><a href="#"><fmt:message key="homePage.naming.cart"/>:</a></h1></div>
                                <div class="box-content">
                                    <fmt:message key="homePage.naming.cartItems"/><strong> 0 items</strong>
                                </div>
                            </div>
                            <div class="box-title">
                                <h1><span class="title-icon"></span><a><fmt:message key="homePage.naming.searchParameters"/></a></h1>

                                <form id="searchForm" action="search" method="get">
                                    <input type="hidden" name="currentPage" value="1">
                                    <input type="text" name="mark" placeholder="<fmt:message key="homePage.placeholder.enterMark"/>">
                                    <input type="text" name="model" placeholder="<fmt:message key="homePage.placeholder.enterModel"/>">
                                    <select name="type">
                                        <option value="" disabled selected><fmt:message key="homePage.placeholder.chooseBodyType"/></option>
                                        <c:forEach var="type" items="${sessionScope.carTypes}">
                                            <option value="${type}">${type}</option>
                                        </c:forEach>
                                    </select>

                                    <fieldset>
                                        <legend><fmt:message key="homePage.naming.chooseYear"/> </legend>
                                        <div class="rangeslider">
                                            <input id="min_year_input" class="min" name="minYear" type="range"
                                                   min="${yearRange.minimum}" max="${yearRange.maximum}" value="${yearRange.minimum}"
                                                   onchange="changeValue('min_year_input','min_year_output')"/>
                                            <output id="min_year_output">${sessionScope.yearRange.minimum}</output>
                                            <input id="max_year_input" class="max" name="maxYear" type="range"
                                                   min="${yearRange.minimum}" max="${yearRange.maximum}" value="${yearRange.maximum}"
                                                   onchange="changeValue('max_year_input', 'max_year_output')"/>
                                            <output id="max_year_output" class="maxOutput">${sessionScope.yearRange.maximum}</output>
                                            <br>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <legend><fmt:message key="homePage.naming.chooseSpeed"/> </legend>
                                        <div class="rangeslider">
                                            <input id="min_speed_input" class="min" name="minSpeed" type="range"
                                                   min="${speedRange.minimum}" max="${speedRange.maximum}" value="${speedRange.minimum}"
                                                   onchange="changeValue('min_speed_input', 'min_speed_output')"/>
                                            <output id="min_speed_output">${sessionScope.speedRange.minimum}</output>
                                            <input id="max_speed_input" class="max" name="maxSpeed" type="range"
                                                   min="${speedRange.minimum}" max="${speedRange.maximum}" value="${speedRange.maximum}"
                                                   onchange="changeValue('max_speed_input', 'max_speed_output')"/>
                                            <output id="max_speed_output" class="maxOutput">${sessionScope.speedRange.maximum}</output>
                                            <br>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <legend><fmt:message key="homePage.naming.choosePrice"/> </legend>
                                        <div class="rangeslider">
                                            <input id="min_price_input" class="min" name="minPrice" type="range"
                                                   min="${priceRange.minimum}" max="${priceRange.maximum}" value="${priceRange.minimum}"
                                                   onchange="changeValue('min_price_input', 'min_price_output')"/>
                                            <output id="min_price_output">${sessionScope.priceRange.minimum}</output>
                                            <input id="max_price_input" class="max" name="maxPrice" type="range"
                                                   min="${priceRange.minimum}" max="${priceRange.maximum}" value="${priceRange.maximum}"
                                                   onchange="changeValue('max_price_input', 'max_price_output')"/>
                                            <output id="max_price_output" class="maxOutput">${sessionScope.priceRange.maximum}</output>
                                            <br>
                                        </div>
                                    </fieldset>

                                    <fieldset>
                                        <legend><fmt:message key="homePage.naming.chooseSort"/></legend>
                                        <input type="radio" name="sort" value="price"><fmt:message key="homePage.naming.price"/>
                                        <input type="radio" name="sort" value="year"><fmt:message key="homePage.naming.year"/>
                                        <input type="radio" name="sort" value="speed"><fmt:message key="homePage.naming.speed"/>
                                        <input type="radio" name="sort" value="type"><fmt:message key="homePage.naming.type"/>
                                    </fieldset>

                                    <fieldset>
                                        <legend><fmt:message key="homePage.naming.ordering"/></legend>
                                        <input type="radio" name="ordering" value="ASC"><fmt:message key="homePage.naming.asc"/>
                                        <input type="radio" name="ordering" value="DESC"><fmt:message key="homePage.naming.desc"/>
                                    </fieldset>

                                    <input type="submit" value="<fmt:message key="homePage.button.submit"/>">
                                </form>

                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                    <div class="footer-bottom">
                        <div class="copy">
                            <p>&copy 2013 Cars Online . All rights Reserved | Design by <a href="http://w3layouts.com">W3Layouts</a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>