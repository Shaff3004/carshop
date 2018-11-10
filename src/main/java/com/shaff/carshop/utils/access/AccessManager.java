package com.shaff.carshop.utils.access;

import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessManager {
    private AccessChecker checker;

    public AccessManager(AccessChecker checker) {
        this.checker = checker;
    }

    public void checkAccessPermission(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        giveAccessIfResourceNotDefended(req, resp, chain);
    }

    private void giveAccessIfResourceNotDefended(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (checker.isResourceDefended(req)) {
            giveAccessIfUserLogged(req, resp, chain);
        } else {
            chain.doFilter(req, resp);
        }
    }

    private void giveAccessIfUserLogged(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (checker.isUserLogged(req)) {
            giveAccessIfAllowed(req, resp, chain);
            return;
        }
        resp.sendRedirect(ServletPath.LOGIN_PAGE_REDIRECT);
    }

    private void giveAccessIfAllowed(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if (checker.checkAccessToResource(req)) {
            chain.doFilter(req, resp);
            return;
        }
        req.setAttribute("errors", "ACCESS DENIED");
        req.getRequestDispatcher(ViewPath.ERROR_PAGE).forward(req, resp);
    }
}