package com.shaff.carshop.utils.locale.strategies;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.utils.locale.LocaleStorageStrategy;
import com.shaff.carshop.utils.parsers.StringToTimeConverter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;
import java.util.Locale;

public class LocaleCookiesStoreStrategy implements LocaleStorageStrategy {
    private StringToTimeConverter timeConverter = new StringToTimeConverter();

    @Override
    public void putLocaleToStorage(HttpServletRequest req, HttpServletResponse resp, Locale locale) {
        Cookie cookie = new Cookie(RequestParameters.LANGUAGE, locale.getLanguage());
        LocalTime time = timeConverter.getOperationTimeOut(getCookieLocaleMaxInactiveTime(req));
        cookie.setPath("/");
        cookie.setMaxAge(convertTimeToSeconds(time));
        resp.addCookie(cookie);
    }

    @Override
    public Locale getLocaleFromStorage(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie currentCookie : cookies) {
                if (isItAppropriateCookie(currentCookie, req)) {
                    return new Locale(currentCookie.getValue());
                }
            }
        }
        return null;
    }

    private int convertTimeToSeconds(LocalTime time) {
        return (time.getHour() * 60 * 60) + (time.getMinute() * 60) + time.getSecond();
    }

    private String getCookieLocaleMaxInactiveTime(HttpServletRequest req) {
        return req.getServletContext().getInitParameter(ContextAttributes.LOCALE_COOKIE_TIME);
    }

    private boolean isItAppropriateCookie(Cookie cookie, HttpServletRequest req){
        return cookie.getName().equals(RequestParameters.LANGUAGE) && checkIfCookieValuesEquals(cookie, req);
    }

    private boolean checkIfCookieValuesEquals(Cookie cookie, HttpServletRequest req){
        return cookie.getValue().equals(req.getLocale().getLanguage());
    }
}