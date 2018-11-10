package com.shaff.carshop.web.filters;

import com.shaff.carshop.utils.locale.LocaleManager;
import com.shaff.carshop.utils.locale.LocaleStorageStrategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LocaleFilterTest extends Assert {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private LocaleManager manager;
    @Mock
    private LocaleStorageStrategy strategy;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private LocaleFilter filter = new LocaleFilter();
    private Locale testLocale = new Locale("ru_RU");

    @Before
    public void setUp() {
        when(manager.getAppropriateLocale(any(HttpServletRequest.class))).thenReturn(testLocale);
    }

    @Test
    public void shouldVerifyThatRequiredMethodWasCalled() throws IOException, ServletException {
        filter.doFilter(request, response, filterChain);

        verify(manager, times(1)).getAppropriateLocale(any(HttpServletRequest.class));
    }

    @Test
    public void shouldVerifyThatDoFilterWasCalled() throws IOException, ServletException {
        filter.doFilter(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
}