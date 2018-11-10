package com.shaff.carshop.web.servlets;

import com.shaff.carshop.utils.captcha.RegistrationCaptcha;
import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.constants.ViewPath;
import com.shaff.carshop.containers.CaptchaStorage;
import com.shaff.carshop.converters.RegistrationBeanConverter;
import com.shaff.carshop.converters.RequestToRegistrationBeanConverter;
import com.shaff.carshop.db.beans.UserRegistrationBean;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.UserService;
import com.shaff.carshop.services.validators.UserRegistrationBeanValidator;
import com.shaff.carshop.utils.avatar.AvatarSaver;
import com.shaff.carshop.utils.captcha.CaptchaVerificator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = ServletPath.REGISTRATION_PAGE_REDIRECT,
        initParams = @WebInitParam(name = ContextAttributes.REGISTRATION_TIME_OUT, value = "00:01:00")
)
@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(RegistrationServlet.class);
    private CaptchaStorageStrategy strategy;
    private CaptchaStorage storage;

    @Override
    public void init() throws ServletException {
        LOG.debug(LoggerMessages.INITIALIZATION_STARTED);
        super.init();
        strategy = (CaptchaStorageStrategy) getServletContext().getAttribute(ContextAttributes.CAPTCHA_STRATEGY);
        storage = (CaptchaStorage) getServletContext().getAttribute(ContextAttributes.CAPTCHA_STORAGE);
        LOG.debug(LoggerMessages.INITIALIZATION_FINISHED);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RegistrationCaptcha captcha = new RegistrationCaptcha(getInitParameter(ContextAttributes.REGISTRATION_TIME_OUT));
        String captchaId = RandomStringUtils.randomNumeric(5);
        storage.getContainer().put(captchaId, captcha);
        strategy.provideCaptcha(req, resp, captchaId);
        getServletContext().getRequestDispatcher(ViewPath.REGISTRATION_PAGE).forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LOG.debug(LoggerMessages.REGISTRATION_PROCESS_STARTED);

        String path = ViewPath.ERROR_PAGE;
        try {
            path = processRequest(req);
        } catch (ApplicationException e) {
            req.setAttribute(RequestParameters.ERRORS, e.getMessage());
            getServletContext().getRequestDispatcher(path).forward(req, resp);
            return;
        }
        resp.sendRedirect(path);
    }

    private String processRequest(HttpServletRequest req) throws ApplicationException, IOException, ServletException {
        String path = ServletPath.REGISTRATION_PAGE_REDIRECT;
        UserRegistrationBean bean = getRequestToRegistrationBeanConverter().convert(req);
        List<String> errors = validateAllInfo(req, bean);

        if (errors.isEmpty()) {
            User user = registerUser(bean);
            if (user != null) {
                req.getSession().setAttribute(RequestParameters.USER, user);
                new AvatarSaver().saveImageToConcretePath(req, bean.getLogin());
                System.out.println(user.toString());
                return ServletPath.HOME;
            }
            req.getSession().setAttribute(RequestParameters.ERRORS, Arrays.asList(LoggerMessages.LOGIN_OR_EMAIL_EXISTS));
        } else {
            saveUserInfo(req, bean, errors);
        }
        return path;
    }

    private List<String> validateAllInfo(HttpServletRequest req, UserRegistrationBean bean) {
        String captchaId = strategy.getCaptcha(req);
        List<String> errors = getUserRegistrationBeanValidator().validate(bean);
        errors.addAll(new CaptchaVerificator().verifyCaptcha(storage, bean.getCaptcha(), captchaId));
        return errors;
    }

    private User registerUser(UserRegistrationBean bean) throws ApplicationException {
        bean.setPassword(DigestUtils.md5Hex(bean.getPassword()));
        UserService userService = (UserService) getServletContext().getAttribute(ContextAttributes.USER_SERVICE);
        return userService.registerNewUser(getRegistrationBeanConverter().convert(bean));
    }

    private void saveUserInfo(HttpServletRequest req, UserRegistrationBean bean, List<String> errors) {
        req.getSession().setAttribute(RequestParameters.ERRORS, errors);
        req.getSession().setAttribute(RequestParameters.USER_BEAN, bean);
    }

    private RequestToRegistrationBeanConverter getRequestToRegistrationBeanConverter() {
        return (RequestToRegistrationBeanConverter) getServletContext().getAttribute(ContextAttributes.REQUEST_TO_BEAN_CONVERTER);
    }

    private UserRegistrationBeanValidator getUserRegistrationBeanValidator() {
        return (UserRegistrationBeanValidator) getServletContext().getAttribute(ContextAttributes.BEAN_VALIDATOR);
    }

    private RegistrationBeanConverter getRegistrationBeanConverter() {
        return (RegistrationBeanConverter) getServletContext().getAttribute(ContextAttributes.REGISTRATION_BEAN_CONVERTER);
    }
}