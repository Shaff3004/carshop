package com.shaff.carshop.web.listeners;

import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.containers.CaptchaStorage;
import com.shaff.carshop.containers.CaptchaStrategyStorage;
import com.shaff.carshop.converters.CartToOrderConverter;
import com.shaff.carshop.converters.RegistrationBeanConverter;
import com.shaff.carshop.converters.RequestToLoginBeanConverter;
import com.shaff.carshop.converters.RequestToRegistrationBeanConverter;
import com.shaff.carshop.converters.RequestToSearchFormBeanConverter;
import com.shaff.carshop.converters.populators.OrderPopulator;
import com.shaff.carshop.converters.populators.SearchFormBeanPopulator;
import com.shaff.carshop.converters.populators.UserLoginBeanPopulator;
import com.shaff.carshop.converters.populators.UserPopulator;
import com.shaff.carshop.converters.populators.UserRegistrationBeanPopulator;
import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.dao.impl.CarDaoImpl;
import com.shaff.carshop.db.dao.impl.OrderDaoImpl;
import com.shaff.carshop.db.dao.impl.UserDaoImpl;
import com.shaff.carshop.services.CarService;
import com.shaff.carshop.services.OrderService;
import com.shaff.carshop.services.UserService;
import com.shaff.carshop.services.validators.UserLoginBeanValidator;
import com.shaff.carshop.services.validators.UserRegistrationBeanValidator;
import com.shaff.carshop.utils.captcha.CaptchaStorageScheduledCleaner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ContextListener.class);
    private static final String LOG4J_PROPERTIES = "WEB-INF/log4j.properties";
    private static final String JAVA_COMP_ENV = "java:/comp/env";
    private static final String JDBC_CARSHOP = "jdbc/carshop";
    private CaptchaStorage storage;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOG.debug(LoggerMessages.SERVLET_CONTEXT_INITIALIZATION_STARTS);

        try {
            setAttributesToServletContext(sce);
        } catch (NamingException e) {
            LOG.error(LoggerMessages.CANNOT_SET_VALUES_TO_CONTEXT);
        }
        initLog4J(sce.getServletContext());

        new Thread(() -> startCaptchaStorageCleaner(sce)).start();

        LOG.debug(LoggerMessages.SERVLET_CONTEXT_INITIALIZATION_FINISHED);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOG.debug(LoggerMessages.SERVLET_CONTEXT_DESTRUCTION_STARTED);
        //NOP
        LOG.debug(LoggerMessages.SERVLET_CONTEXT_DESTRUCTION_WAS_FINISHED);
    }

    private void initLog4J(ServletContext servletContext) {
        LOG.debug(LoggerMessages.LOGGER_INITIALIZATION_STARTED);
        try {
            PropertyConfigurator.configure(servletContext.getRealPath(LOG4J_PROPERTIES));
        } catch (Exception e) {
            LOG.error(LoggerMessages.CANNOT_CONFIGURE_LOGGER, e);
        }
        LOG.debug(LoggerMessages.LOGGER_HAS_BEEN_INITIALIZED);
    }

    private void setAttributesToServletContext(ServletContextEvent sce) throws NamingException {
        sce.getServletContext().setAttribute(ContextAttributes.CAPTCHA_STRATEGY, getCaptchaStrategy(sce));
        sce.getServletContext().setAttribute(ContextAttributes.CAPTCHA_STORAGE, getCaptchaStorage());
        sce.getServletContext().setAttribute(ContextAttributes.BEAN_VALIDATOR, new UserRegistrationBeanValidator());
        sce.getServletContext().setAttribute(ContextAttributes.LOGIN_BEAN_VALIDATOR, new UserLoginBeanValidator());
        sce.getServletContext().setAttribute(ContextAttributes.REQUEST_TO_BEAN_CONVERTER, getRequestToRegistrationBeanConverter());
        sce.getServletContext().setAttribute(ContextAttributes.REGISTRATION_BEAN_CONVERTER, getRegistrationBeanConverter());
        sce.getServletContext().setAttribute(ContextAttributes.REQUEST_TO_LOGIN_BEAN_CONVERTER, getRequestToLoginBeanConverter());
        sce.getServletContext().setAttribute(ContextAttributes.REQUEST_TO_SEARCH_FORM_BEAN_CONVERTER, getRequestToSearchFormBeanConverter());
        sce.getServletContext().setAttribute(ContextAttributes.CART_TO_ORDER_BEAN_CONVERTER, getCartToOrderConverter());
        sce.getServletContext().setAttribute(ContextAttributes.UPLOADS_PATH, getUploadPath(sce));
        setUpAllServices(sce);
    }

    private RegistrationBeanConverter getRegistrationBeanConverter() {
        RegistrationBeanConverter converter = new RegistrationBeanConverter();
        converter.addPopulator(new UserPopulator());
        return converter;
    }

    private RequestToRegistrationBeanConverter getRequestToRegistrationBeanConverter() {
        RequestToRegistrationBeanConverter converter = new RequestToRegistrationBeanConverter();
        converter.addPopulator(new UserRegistrationBeanPopulator());
        return converter;
    }

    private RequestToLoginBeanConverter getRequestToLoginBeanConverter() {
        RequestToLoginBeanConverter converter = new RequestToLoginBeanConverter();
        converter.addPopulator(new UserLoginBeanPopulator());
        return converter;
    }

    private RequestToSearchFormBeanConverter getRequestToSearchFormBeanConverter(){
        RequestToSearchFormBeanConverter converter = new RequestToSearchFormBeanConverter();
        converter.addPopulator(new SearchFormBeanPopulator());
        return converter;
    }

    private CartToOrderConverter getCartToOrderConverter(){
        CartToOrderConverter converter = new CartToOrderConverter();
        converter.addPopulator(new OrderPopulator());
        return converter;
    }

    private CaptchaStorage getCaptchaStorage() {
        storage = new CaptchaStorage();
        return storage;
    }

    private CaptchaStorageStrategy getCaptchaStrategy(ServletContextEvent sce) {
        String strategyName = sce.getServletContext().getInitParameter(ContextAttributes.CAPTCHA_STRATEGY);
        return new CaptchaStrategyStorage().getStrategies().get(strategyName);
    }

    private void startCaptchaStorageCleaner(ServletContextEvent sce) {
        LOG.debug(LoggerMessages.CAPTCHA_STORAGE_CLEANER_STARTED);
        String timeDelay = getCaptchaCleanerDelay(sce);
        new CaptchaStorageScheduledCleaner(storage, timeDelay).clean();
    }

    private String getCaptchaCleanerDelay(ServletContextEvent sce) {
        return sce.getServletContext().getInitParameter(ContextAttributes.TIME_DELAY);
    }

    private String getUploadPath(ServletContextEvent sce) {
        return sce.getServletContext().getInitParameter(ContextAttributes.UPLOADS_PATH);
    }

    private TransactionManager getTransactionManager() throws NamingException {
        Context initContext = new InitialContext();
        Context envContext = (Context) initContext.lookup(JAVA_COMP_ENV);
        DataSource dataSource = (DataSource) envContext.lookup(JDBC_CARSHOP);
        return new TransactionManager(dataSource);
    }

    private void setUpAllServices(ServletContextEvent sce) throws NamingException {
        TransactionManager manager = getTransactionManager();
        UserDaoImpl userDao = new UserDaoImpl();
        OrderDaoImpl orderDao = new OrderDaoImpl();
        CarDaoImpl carDao = new CarDaoImpl();

        UserService userService = new UserService(manager);
        OrderService orderService = new OrderService(manager);
        CarService carService = new CarService(manager);

        userService.setUserDao(userDao);
        userService.setCarDao(carDao);
        userService.setOrderDao(orderDao);

        orderService.setCarDao(carDao);
        orderService.setOrderDao(orderDao);
        orderService.setUserDao(userDao);

        carService.setCarDao(carDao);
        carService.setOrderDao(orderDao);
        carService.setUserDao(userDao);

        sce.getServletContext().setAttribute(ContextAttributes.USER_SERVICE, userService);
        sce.getServletContext().setAttribute(ContextAttributes.CAR_SERVICE, carService);
        sce.getServletContext().setAttribute(ContextAttributes.ORDER_SERVICE, orderService);
    }
}