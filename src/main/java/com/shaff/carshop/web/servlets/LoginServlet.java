package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.ErrorMessages;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;
import com.shaff.carshop.containers.CaptchaStorage;
import com.shaff.carshop.converters.RequestToLoginBeanConverter;
import com.shaff.carshop.db.beans.UserLoginBean;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.UserService;
import com.shaff.carshop.utils.captcha.RegistrationCaptcha;
import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(urlPatterns = ServletPath.LOGIN_PAGE_REDIRECT,
        initParams = @WebInitParam(name = ContextAttributes.LOGIN_TIME_OUT, value = "00:01:00"))
public class LoginServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(LoginServlet.class);
    private static final long BAN_TIME_TEN_MINUTES = 60000L;
    private static final String USER_LOGIN_BEAN = "userLoginBean";
    private CaptchaStorageStrategy strategy;
    private CaptchaStorage storage;
    private String loginTimeOut;

    @Override
    public void init() throws ServletException {
        super.init();
        strategy = (CaptchaStorageStrategy) getServletContext().getAttribute(ContextAttributes.CAPTCHA_STRATEGY);
        storage = (CaptchaStorage) getServletContext().getAttribute(ContextAttributes.CAPTCHA_STORAGE);
        loginTimeOut = getInitParameter(ContextAttributes.LOGIN_TIME_OUT);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationCaptcha captcha = new RegistrationCaptcha(loginTimeOut);
        String captchaId = RandomStringUtils.randomNumeric(5);
        storage.getContainer().put(captchaId, captcha);
        strategy.provideCaptcha(req, resp, captchaId);
        req.setAttribute(RequestParameters.CAPTCHA_ID, captchaId);
        getServletContext().getRequestDispatcher(ViewPath.LOGIN_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserLoginBean bean = getUserLoginBean(req);
        UserService userService = getUserService();
        try {
            User user = userService.getUserByLogin(bean.getLogin());
            if (!checkCredentials(user, bean) || checkIfUserBlocked(user)) {
                checkUserAttempts(req, bean, user, userService);
                req.getRequestDispatcher(ViewPath.ERROR_PAGE).forward(req, resp);
                return;
            }

            req.getSession().setAttribute(RequestParameters.USER, user);
            req.getSession().removeAttribute(USER_LOGIN_BEAN);
        } catch (ApplicationException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION);
        }
        resp.sendRedirect(ServletPath.HOME);
    }

    private UserLoginBean getUserLoginBean(HttpServletRequest req) {
        UserLoginBean beanFromSession = (UserLoginBean) req.getSession().getAttribute(USER_LOGIN_BEAN);
        UserLoginBean bean = getRequestToLoginBeanConverter().convert(req);
        if (beanFromSession != null) {
            bean.setAttempts(beanFromSession.getAttempts());
        }

        return bean;
    }

    private boolean checkCredentials(User user, UserLoginBean bean) {
        if (user == null || !user.getPassword().equals(DigestUtils.md5Hex(bean.getPassword()))) {
            bean.setAttempts(bean.getAttempts() + 1);
            return false;
        }
        return true;
    }

    private void checkUserAttempts(HttpServletRequest req, UserLoginBean bean, User user, UserService userService) throws ApplicationException {
        if (bean.getAttempts() >= 3) {
            user.setBanExpiration(new Timestamp(new Date().getTime() + BAN_TIME_TEN_MINUTES));
            userService.blockUser(user);
            bean.setAttempts(0);
            req.setAttribute(RequestParameters.ERRORS, ErrorMessages.YOU_ARE_BLOCKED_MESSAGE);
        } else {
            req.setAttribute(RequestParameters.ERRORS, ErrorMessages.WRONG_LOGIN_OR_PASSWORD);
        }

        req.getSession().setAttribute(USER_LOGIN_BEAN, bean);
    }

    private boolean checkIfUserBlocked(User user) {
        return user.getBanExpiration() != null && user.getBanExpiration().after(new Timestamp(new Date().getTime()));
    }

    private RequestToLoginBeanConverter getRequestToLoginBeanConverter() {
        return (RequestToLoginBeanConverter) getServletContext().getAttribute(ContextAttributes.REQUEST_TO_LOGIN_BEAN_CONVERTER);
    }

    private UserService getUserService() {
        return (UserService) getServletContext().getAttribute(ContextAttributes.USER_SERVICE);
    }
}