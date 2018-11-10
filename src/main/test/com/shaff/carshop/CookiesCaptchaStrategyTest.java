package com.shaff.carshop;

import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.utils.captcha.strategies.CookiesCaptchaStrategy;
import com.shaff.carshop.constants.RequestParameters;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CookiesCaptchaStrategyTest {
    private static final String TEST_CAPTCHA_ID = "12345";
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @Mock
    private Cookie cookie;
    @InjectMocks
    private CaptchaStorageStrategy strategy = new CookiesCaptchaStrategy();


    @Before
    public void setUp() {
        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);
        cookie = mock(Cookie.class);
        Cookie[] cookies = new Cookie[]{cookie};

        when(requestMock.getCookies()).thenReturn(cookies);
        when(cookie.getName()).thenReturn(RequestParameters.CAPTCHA_ID);
        when(cookie.getValue()).thenReturn(TEST_CAPTCHA_ID);
    }

    @Test
    public void shouldVerifyThatCookieWasAddedToResponse() {
        strategy.provideCaptcha(requestMock, responseMock, TEST_CAPTCHA_ID);
        verify(responseMock, times(1)).addCookie(anyObject());
    }

    @Test
    public void shouldReturnTrueWhenCaptchaIdAreEquals() {
        String realCaptchaId = strategy.getCaptcha(requestMock);
        assertEquals(realCaptchaId, TEST_CAPTCHA_ID);
    }
}
