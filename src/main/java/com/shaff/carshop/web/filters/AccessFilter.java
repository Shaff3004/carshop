package com.shaff.carshop.web.filters;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.utils.access.AccessChecker;
import com.shaff.carshop.utils.access.AccessManager;
import com.shaff.carshop.utils.xml.XmlDomParser;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AccessFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AccessFilter.class);
    private AccessManager accessManager;


    @Override
    public void init(FilterConfig filterConfig) {
        try {
            Map<String, List<String>> accessMap = getPatternMap(filterConfig);
            AccessChecker accessChecker = new AccessChecker(accessMap);
            accessManager = new AccessManager(accessChecker);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION);
        }
        LOG.debug(LoggerMessages.FILTER_INITIALIZATION);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (checkContentType(req)) {
            accessManager.checkAccessPermission(req, resp, filterChain);
        } else {
            filterChain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {
        LOG.debug(LoggerMessages.FILTER_DESTROYED);
    }

    private boolean checkContentType(HttpServletRequest req) {
        return req.getHeader("Accept") != null && req.getHeader("Accept").startsWith("text/html");
    }

    private Map<String, List<String>> getPatternMap(FilterConfig config) throws IOException, SAXException, ParserConfigurationException {
        return new XmlDomParser().parseDocument(getXmlSecurityConfigurationPath(config));
    }

    private String getXmlSecurityConfigurationPath(FilterConfig config) {
        return config.getInitParameter(ContextAttributes.XML_SECURITY_CONFIGURATION_PATH);
    }
}