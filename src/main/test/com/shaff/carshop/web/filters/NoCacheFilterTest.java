package com.shaff.carshop.web.filters;

import org.junit.Assert;
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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class NoCacheFilterTest extends Assert {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @InjectMocks
    private NoCacheFilter filter = new NoCacheFilter();

    @Test
    public void shouldVerifyThatAppropriateHeadersWereSettled() throws IOException, ServletException {
        filter.doFilter(request, response, filterChain);
        verify(response, times(1)).setHeader("Cache-Control", "no-cache, no-store");
        verify(response, times(1)).setDateHeader("Expires", 0);
    }

    @Test
    public void shouldVerifyThatDoFilterWasCalled() throws IOException, ServletException {
        filter.doFilter(request, response, filterChain);
        verify(filterChain, times(1)).doFilter(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }
}