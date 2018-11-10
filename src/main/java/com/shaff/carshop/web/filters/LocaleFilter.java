package com.shaff.carshop.web.filters;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.containers.LocaleStrategyStorage;
import com.shaff.carshop.utils.locale.LocaleManager;
import com.shaff.carshop.utils.locale.LocaleStorageStrategy;
import com.shaff.carshop.web.filters.wrappers.LocaleRequestWrapper;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(LocaleFilter.class);
    private LocaleManager localeManager;
    private LocaleStorageStrategy strategy;

    @Override
    public void init(FilterConfig filterConfig) {
        strategy = getLocaleStorageStrategy(filterConfig);
        Locale defaultLocale = getDefaultLocale(filterConfig);
        List<Locale> supportedLocales = getAllSupportedLocales(filterConfig);
        localeManager = new LocaleManager(strategy, defaultLocale, supportedLocales);
        LOG.debug(LoggerMessages.FILTER_INITIALIZATION);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Locale appropriateLocale = localeManager.getAppropriateLocale(req);

        strategy.putLocaleToStorage(req, resp, appropriateLocale);
        filterChain.doFilter(new LocaleRequestWrapper(req, appropriateLocale), resp);
    }

    @Override
    public void destroy() {
        LOG.debug(LoggerMessages.FILTER_DESTROYED);
    }

    private LocaleStorageStrategy getLocaleStorageStrategy(FilterConfig config) {
        String strategyName = config.getInitParameter(ContextAttributes.LOCALE_STORAGE_STRATEGY);
        LocaleStrategyStorage strategyStorage = new LocaleStrategyStorage();
        return strategyStorage.getLocaleStrategies().get(strategyName);
    }

    private Locale getDefaultLocale(FilterConfig config) {
        String defaultLocaleName = config.getInitParameter(ContextAttributes.DEFAULT_LOCALE);
        return LocaleUtils.toLocale(defaultLocaleName);
    }


    private List<Locale> getAllSupportedLocales(FilterConfig config) {
        String[] supportedLocales = config.getInitParameter(ContextAttributes.SUPPORTED_LOCALES_LIST).split(",");
        List<Locale> locales = new ArrayList<>();
        for (String currentLocale : supportedLocales) {
            locales.add(LocaleUtils.toLocale(currentLocale));
        }
        return locales;
    }
}