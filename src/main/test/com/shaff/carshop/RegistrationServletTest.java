package com.shaff.carshop;

import com.shaff.carshop.utils.captcha.RegistrationCaptcha;
import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.containers.CaptchaStorage;
import com.shaff.carshop.converters.RegistrationBeanConverter;
import com.shaff.carshop.converters.RequestToRegistrationBeanConverter;
import com.shaff.carshop.converters.populators.UserPopulator;
import com.shaff.carshop.converters.populators.UserRegistrationBeanPopulator;
import com.shaff.carshop.db.dao.impl.UserDaoImpl;
import com.shaff.carshop.services.validators.UserRegistrationBeanValidator;
import com.shaff.carshop.web.servlets.RegistrationServlet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {
    private static final String PASSWORD_EXAMPLE = "qwerty777";
    private static final String CONFIRM_PASSWORD_EXAMPLE = "qwerty777";
    private static final String CAPTCHA_EXAMPLE = "121212";
    private static final String GENDER_EXAMPLE = "male";
    private static final String NEWSLETTER_EXAMPLE = "on";
    private static final String CAPTCHA_ID_EXAMPLE = "12345";
    private static final String NAME_SERHII_VOLODIN = "Serhii Volodin";
    private static final String LOGIN_SERHII_VOLODIN = "Shaff";
    private static final String EMAIL_SERHII_VOLODIN = "qwerty@email.ru";
    private static final String NAME_HARRY_KANE = "Harry Kane";
    private static final String EXISTING_LOGIN = "Czar";
    private static final String EXISTING_EMAIL = "voitenko@gmail.com";
    private static final String EMAIL_HARRY_CANE = "harrycane@raddit.com";
    private static final String LOGIN_HARRY_CANE = "Kane";
    private static final String CORRECT_CAPTCHA_VALUE = "121212";
    private static final String WRONG_CAPTCHA_VALUE = "666666";
    private static final String NAME_JULIA_TYMOSHENKO = "Julia Tymoshenko";
    private static final String LOGIN_JULIA_TYMOSHENKO = "TigerJulia";
    private static final String EMAIL_JULIA_TYMOSHENKO = "yanevtyrme@mail.ru";
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private CaptchaStorageStrategy strategy;
    @Mock
    private CaptchaStorage storage;
    @Mock
    private RegistrationCaptcha captcha;
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext context;

    private LocalTime localTime = LocalTime.now().plusMinutes(5);
    @Mock
    private ServletConfig config;
    @Mock
    private RequestDispatcher dispatcher;
    @InjectMocks
    private RegistrationServlet servlet = new RegistrationServlet();

    private UserDaoImpl userDao = new UserDaoImpl();
    //private List<User> users = userDao.getUsers();

    @Before
    public void setUp() {
        RegistrationBeanConverter converter = new RegistrationBeanConverter();

        setRequestMockBehavior();
        setCaptchaMockBehavior();
        setConverterBehavior(converter);
        setServletContextMockBehavior(converter);
        setStorageBehavior();
        setCaptchaMockBehavior();
    }

    private void setCaptchaMockBehavior() {
        when(strategy.getCaptcha(requestMock)).thenReturn(CAPTCHA_ID_EXAMPLE);
        when(captcha.getCaptchaKey()).thenReturn(CORRECT_CAPTCHA_VALUE);
        when(captcha.getTimeExpired()).thenReturn(localTime);
        //when(localTime.get(ChronoField.MILLI_OF_SECOND)).thenReturn(TIME_VALUE_IN_MILLIS);
    }

    private void setStorageBehavior() {
        HashMap map = mock(HashMap.class);
        when(storage.getContainer()).thenReturn(map);
        when(map.get(anyString())).thenReturn(captcha);
    }

    private void setServletContextMockBehavior(RegistrationBeanConverter converter) {
        RequestToRegistrationBeanConverter requestConverter = new RequestToRegistrationBeanConverter();
        requestConverter.addPopulator(new UserRegistrationBeanPopulator());
        when(context.getAttribute(ContextAttributes.REQUEST_TO_BEAN_CONVERTER)).thenReturn(requestConverter);
        when(context.getAttribute(ContextAttributes.BEAN_VALIDATOR)).thenReturn(new UserRegistrationBeanValidator());
        when(context.getAttribute(ContextAttributes.REGISTRATION_BEAN_CONVERTER)).thenReturn(converter);
        when(context.getAttribute(ContextAttributes.USER_DAO)).thenReturn(userDao);
        when(config.getServletContext()).thenReturn(context);
    }

    private void setRequestMockBehavior() {
        when(requestMock.getParameter(RequestParameters.PASSWORD)).thenReturn(PASSWORD_EXAMPLE);
        when(requestMock.getParameter(RequestParameters.CONFIRM_PASSWORD)).thenReturn(CONFIRM_PASSWORD_EXAMPLE);
        when(requestMock.getParameter(RequestParameters.CAPTCHA)).thenReturn(CAPTCHA_EXAMPLE);
        when(requestMock.getParameter(RequestParameters.GENDER)).thenReturn(GENDER_EXAMPLE);
        when(requestMock.getParameter(RequestParameters.NEWSLETTER)).thenReturn(NEWSLETTER_EXAMPLE);
        when(requestMock.getSession()).thenReturn(session);
    }

    private void setConverterBehavior(RegistrationBeanConverter converter) {
        converter.addPopulator(new UserPopulator());
    }

    @Test
    public void shouldPassRegistrationWhenAllDataAreCorrect() throws IOException {
        /*when(requestMock.getParameter(RequestParameters.NAME)).thenReturn(NAME_SERHII_VOLODIN);
        when(requestMock.getParameter(RequestParameters.LOGIN)).thenReturn(LOGIN_SERHII_VOLODIN);
        when(requestMock.getParameter(RequestParameters.EMAIL)).thenReturn(EMAIL_SERHII_VOLODIN);

        int sizeBefore = users.size();
        servlet.doPost(requestMock, responseMock);
        int sizeAfter = users.size();

        assertTrue(sizeAfter > sizeBefore);*/
    }

    @Test
    public void shouldFailRegistrationWhenTryToRegisterUserWithExistingLogin() throws IOException {
        /*assertEquals(users.get(0).getLogin(), EXISTING_LOGIN);

        when(requestMock.getParameter(RequestParameters.NAME)).thenReturn(NAME_HARRY_KANE);
        when(requestMock.getParameter(RequestParameters.LOGIN)).thenReturn(EXISTING_LOGIN);
        when(requestMock.getParameter(RequestParameters.EMAIL)).thenReturn(EMAIL_HARRY_CANE);

        int sizeBefore = users.size();
        servlet.doPost(requestMock, responseMock);
        int sizeAfter = users.size();

        assertEquals(sizeBefore, sizeAfter);*/
    }

    @Test
    public void shouldFailRegistrationWhenTryToRegisterUserWithExistingEmail() throws IOException {
       /* assertEquals(users.get(0).getEmail(), EXISTING_EMAIL);

        when(requestMock.getParameter(RequestParameters.NAME)).thenReturn(NAME_HARRY_KANE);
        when(requestMock.getParameter(RequestParameters.LOGIN)).thenReturn(LOGIN_HARRY_CANE);
        when(requestMock.getParameter(RequestParameters.EMAIL)).thenReturn(EXISTING_EMAIL);

        int sizeBefore = users.size();
        servlet.doPost(requestMock, responseMock);
        int sizeAfter = users.size();

        assertEquals(sizeBefore, sizeAfter);*/
    }

    @Test
    public void shouldFailRegistrationWhenCaptchaValueIsWrong() throws IOException {
        /*when(requestMock.getParameter(RequestParameters.NAME)).thenReturn(NAME_JULIA_TYMOSHENKO);
        when(requestMock.getParameter(RequestParameters.LOGIN)).thenReturn(LOGIN_JULIA_TYMOSHENKO);
        when(requestMock.getParameter(RequestParameters.EMAIL)).thenReturn(EMAIL_JULIA_TYMOSHENKO);
        when(requestMock.getParameter(RequestParameters.CAPTCHA)).thenReturn(WRONG_CAPTCHA_VALUE);

        int sizeBefore = users.size();
        servlet.doPost(requestMock, responseMock);
        int sizeAfter = users.size();

        assertEquals(sizeBefore, sizeAfter);*/
    }

    @Test
    public void verifyThatDoGetMethodWorksCorrectly() throws ServletException, IOException {
        when(config.getInitParameter(ContextAttributes.REGISTRATION_TIME_OUT)).thenReturn("00:01:30");
        when(context.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        servlet.doGet(requestMock, responseMock);

        verify(storage, times(1)).getContainer();
        verify(strategy, times(1)).provideCaptcha(any(HttpServletRequest.class), any(HttpServletResponse.class), anyString());
        verify(dispatcher, times(1)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void verifyThatInitMethodWorksCorrectly() throws ServletException {
        servlet.init();
        verify(context, times(1)).getAttribute(ContextAttributes.CAPTCHA_STRATEGY);
        verify(context, times(1)).getAttribute(ContextAttributes.CAPTCHA_STORAGE);
    }
}
