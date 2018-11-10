package com.shaff.carshop.web.filters;

import com.shaff.carshop.constants.LoggerMessages;
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

public class NoCacheFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(NoCacheFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug(LoggerMessages.FILTER_INITIALIZATION);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        resp.setHeader("Cache-Control", "no-cache, no-store");
        resp.setDateHeader("Expires", 0);
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        LOG.debug(LoggerMessages.FILTER_DESTROYED);
    }
}