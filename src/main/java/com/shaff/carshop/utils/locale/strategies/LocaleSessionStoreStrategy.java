package com.shaff.carshop.utils.locale.strategies;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.utils.locale.LocaleStorageStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class LocaleSessionStoreStrategy implements LocaleStorageStrategy {

    @Override
    public void putLocaleToStorage(HttpServletRequest req, HttpServletResponse resp, Locale locale) {
        HttpSession session = req.getSession();
        session.setAttribute(RequestParameters.LANGUAGE, locale);
    }

    @Override
    public Locale getLocaleFromStorage(HttpServletRequest req) {
        return (Locale) req.getSession().getAttribute(RequestParameters.LANGUAGE);
    }
}