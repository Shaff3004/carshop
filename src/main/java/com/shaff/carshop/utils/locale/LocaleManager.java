package com.shaff.carshop.utils.locale;

import com.shaff.carshop.constants.RequestParameters;
import com.mysql.jdbc.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public class LocaleManager {
    private LocaleStorageStrategy strategy;
    private List<Locale> supportedLocales;
    private Locale defaultLocale;

    public LocaleManager(LocaleStorageStrategy strategy, Locale defaultLocale, List<Locale> supportedLocales) {
        this.strategy = strategy;
        this.defaultLocale = defaultLocale;
        this.supportedLocales = supportedLocales;
    }

    public Locale getAppropriateLocale(HttpServletRequest req) {
        Locale appropriateLocale = null;

        if (getLocaleFromUrl(req) != null) {
            appropriateLocale = getLocaleFromUrl(req);
        } else if (getLocaleFromStorage(req) != null) {
            appropriateLocale = getLocaleFromStorage(req);
        } else if (getMatchLocale(req) != null) {
            appropriateLocale = getMatchLocale(req);
        }
        return appropriateLocale;
    }

    private Locale getLocaleFromUrl(HttpServletRequest req) {
        String localeString = req.getParameter(RequestParameters.LANGUAGE);
        if (!StringUtils.isNullOrEmpty(localeString)) {
            return new Locale(localeString);
        }
        return null;
    }

    private Locale getLocaleFromStorage(HttpServletRequest req) {
        return strategy.getLocaleFromStorage(req);
    }

    private Locale getMatchLocale(HttpServletRequest req) {
        Locale resultLocale = null;
        Enumeration<Locale> browserLocales = req.getLocales();

        while (browserLocales.hasMoreElements()) {
            Locale currentLocale = browserLocales.nextElement();
            resultLocale = returnLocaleIfSupported(currentLocale);
            if(resultLocale == defaultLocale) {
                break;
            }
        }
        return resultLocale;
    }

    private Locale returnLocaleIfSupported(Locale browserLocale) {
        for (Locale currentLocale : supportedLocales) {
            if (browserLocale.equals(currentLocale)) {
                return currentLocale;
            }

            if (localeToLanguageCountryString(browserLocale).equals(localeToLanguageCountryString(currentLocale))) {
                return currentLocale;
            }

            if (browserLocale.getLanguage().equals(currentLocale.getLanguage())) {
                return currentLocale;
            }
        }
        return defaultLocale;
    }

    private String localeToLanguageCountryString(Locale locale) {
        return locale.getLanguage() + "_" + locale.getCountry();
    }
}