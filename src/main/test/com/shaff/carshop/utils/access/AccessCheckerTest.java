package com.shaff.carshop.utils.access;

import com.shaff.carshop.constants.Gender;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.db.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccessCheckerTest extends Assert {
    private static final String TEST_NAME = "Ivan Ivanov";
    private static final String TEST_LOGIN = "Ivanov";
    private static final String TEST_EMAIL = "example@email.ru";
    private static final String TEST_PASSWORD = "qwerty777";
    private static final String TEST_GENDER_VALUE = "male";
    private static final boolean TEST_NEWSLETTER_VALUE = true;
    private static final int TEST_ID = 5;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    private AccessChecker manager;
    private User testUser = new User(TEST_ID, TEST_NAME, TEST_LOGIN, TEST_EMAIL, TEST_PASSWORD, Gender.valueOf(TEST_GENDER_VALUE.toUpperCase()), TEST_NEWSLETTER_VALUE, 1, null);

    @Before
    public void setUp() {

        Map<String, List<String>> accessMap = new HashMap<>();
        accessMap.put("1", Arrays.asList("/confirm_order", "/buy_item"));
        accessMap.put("2", Arrays.asList("/purchaseStatistic", "/serverConfig"));
        manager = new AccessChecker(accessMap);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void shouldReturnTrueWhenResourceIsDefended() {
        when(request.getRequestURI()).thenReturn("/confirm_order");
        boolean result = manager.isResourceDefended(request);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenResourceIsNotDefended() {
        when(request.getRequestURI()).thenReturn("/home");
        boolean result = manager.isResourceDefended(request);

        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenUserIsLogged() {
        when(session.getAttribute(RequestParameters.USER)).thenReturn(testUser);
        boolean result = manager.isUserLogged(request);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenUserIsNotLogged() {
        when(session.getAttribute(RequestParameters.USER)).thenReturn(null);
        boolean result = manager.isUserLogged(request);

        assertFalse(result);
    }

    @Test
    public void shouldReturnTrueWhenAccessToResourceGranted() {
        when(request.getRequestURI()).thenReturn("/home");
        when(session.getAttribute(RequestParameters.USER)).thenReturn(testUser);
        boolean result = manager.checkAccessToResource(request);

        assertTrue(result);
    }

    @Test
    public void shouldReturnFalseWhenAccessToResourceDenied() {
        when(request.getRequestURI()).thenReturn("/confirm_order");
        when(session.getAttribute(RequestParameters.USER)).thenReturn(testUser);
        boolean result = manager.checkAccessToResource(request);

        assertFalse(result);
    }
}