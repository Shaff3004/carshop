package com.shaff.carshop;

import com.shaff.carshop.utils.captcha.strategies.CaptchaStorageStrategy;
import com.shaff.carshop.utils.captcha.strategies.HiddenFieldCaptchaStrategy;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HiddenFieldCaptchaStrategyTest {
    private static final String CAPTCHA_ID_TEST_VALUE = "12345";
    private static final String CAPTCHA_ID_PARAMETER_NAME = "hiddenId";
    private static final String CAPTCHA_HIDDEN_FIELD_NAME = "captchaHidden";
    @Mock
    private HttpServletRequest requestMock;
    @Mock
    private HttpServletResponse responseMock;
    @InjectMocks
    private CaptchaStorageStrategy strategy = new HiddenFieldCaptchaStrategy();

    @Before
    public void setUp() {
        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);

        when(requestMock.getParameter(CAPTCHA_HIDDEN_FIELD_NAME)).thenReturn(CAPTCHA_ID_TEST_VALUE);
    }

    @Test
    public void shouldVerifyThatParameterWasAddedInRequest() {
        strategy.provideCaptcha(requestMock, responseMock, CAPTCHA_ID_TEST_VALUE);
        verify(requestMock, times(1)).setAttribute(CAPTCHA_ID_PARAMETER_NAME, CAPTCHA_ID_TEST_VALUE);
    }

    @Test
    public void shouldReturnTrueWhenExpectedValueEqualsToValueFromRequest() {
        String realCaptchaId = strategy.getCaptcha(requestMock);
        assertEquals(realCaptchaId, CAPTCHA_ID_TEST_VALUE);
    }
}
