package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(ServletPath.CRUD_CHECKER)
public class CredentialCheckServlet extends HttpServlet {
    private static final String TEXT_PLAIN = "text/plain";
    private static final Logger LOG = Logger.getLogger(CredentialCheckServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(RequestParameters.LOGIN);
        String email = req.getParameter(RequestParameters.EMAIL);
        UserService userService = (UserService) getServletContext().getAttribute(ContextAttributes.USER_SERVICE);
        PrintWriter writer = resp.getWriter();
        resp.setContentType(TEXT_PLAIN);

        try {
            if (login != null && !userService.checkIfLoginNotExists(login)) {
                writer.write(LoggerMessages.CURRENT_LOGIN_ALREADY_EXISTS);
            }

            if (email != null && !userService.checkIfEmailNotExists(email)) {
                writer.write(LoggerMessages.CURRENT_EMAIL_ALREADY_EXISTS);
            }
        } catch (ApplicationException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, e);
        }

        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
