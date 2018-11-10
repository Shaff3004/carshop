package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(ServletPath.LOGOUT)
public class LogOutServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(LogOutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug(LoggerMessages.USER_LOGGED_OUT + req.getSession().getAttribute(RequestParameters.USER));
        req.getSession().invalidate();
        req.getRequestDispatcher(ViewPath.HOME_PAGE).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
