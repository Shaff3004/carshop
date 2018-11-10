package com.shaff.carshop.web.filters;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.web.filters.wrappers.GZIPResponseWrapper;
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

public class GZIPFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(GZIPFilter.class);
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String GZIP = "gzip";

    @Override
    public void init(FilterConfig filterConfig) {
        LOG.debug(LoggerMessages.FILTER_INITIALIZATION);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (checkIfAcceptEncodingSupported(req) && checkIfContentTypeSupported(req)) {
            resp.setHeader(CONTENT_ENCODING, GZIP);
            GZIPResponseWrapper responseWrapper = new GZIPResponseWrapper(resp);
            filterChain.doFilter(req, responseWrapper);
            responseWrapper.closeStreams();
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        LOG.debug(LoggerMessages.FILTER_DESTROYED);
    }

    private boolean checkIfAcceptEncodingSupported(HttpServletRequest req) {
        String acceptEncoding = req.getHeader(ACCEPT_ENCODING);
        return acceptEncoding != null && acceptEncoding.indexOf(GZIP) != -1;
    }

    private boolean checkIfContentTypeSupported(HttpServletRequest req) {
        String contentType = req.getHeader("Accept");
        return contentType != null && contentType.contains("text/");
    }
}