package com.shaff.carshop;

import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.utils.captcha.strategies.SessionCaptchaStrategy;
import com.shaff.carshop.constants.RequestParameters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionCaptchaStrategyTest {
    private static final String TEST_CAPTCHA_ID = "12345";
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private HttpSession session;
    @InjectMocks
    private CaptchaStorageStrategy strategy = new SessionCaptchaStrategy();

    @Before
    public void setUp() {
        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        when(requestMock.getSession()).thenReturn(session);
        when(session.getAttribute(RequestParameters.CAPTCHA_ID)).thenReturn(TEST_CAPTCHA_ID);
    }

    @Test
    public void shouldVerifyThatAttributeWithCurrentParametersWasAddedToTheSession() {
        strategy.provideCaptcha(requestMock, responseMock, TEST_CAPTCHA_ID);
        verify(session, times(1)).setAttribute(RequestParameters.CAPTCHA_ID, TEST_CAPTCHA_ID);
    }

    @Test
    public void shouldReturnTrueWhenCaptchaIdAreEquals() {
        String realCaptchaId = strategy.getCaptcha(requestMock);
        assertEquals(realCaptchaId, TEST_CAPTCHA_ID);
    }
}
