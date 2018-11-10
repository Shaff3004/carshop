package com.shaff.carshop.utils.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public interface LocaleStorageStrategy {
    void putLocaleToStorage(HttpServletRequest req, HttpServletResponse resp, Locale locale);
    Locale getLocaleFromStorage(HttpServletRequest req);
}
