package com.shaff.carshop.web.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GZIPFilterTest extends Assert {
    private static final String CONTENT_ENCODING = "Content-Encoding";
    private static final String ACCEPT_ENCODING = "Accept-Encoding";
    private static final String GZIP = "gzip";
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private GZIPFilter filter = spy(new GZIPFilter());

    @Before
    public void setUp() throws IOException, ServletException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        when(request.getHeader(ACCEPT_ENCODING)).thenReturn("gzip");
        when(request.getHeader("Accept")).thenReturn("text/html");
        doNothing().when(filterChain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
    }

    @Test
    public void shouldVerifyThatHeaderWasChecked() throws IOException, ServletException {
        filter.doFilter(request, response, filterChain);

        verify(request, times(1)).getHeader(ACCEPT_ENCODING);
        verify(request, times(1)).getHeader("Accept");
    }

    @Test
    public void shouldVerifyThatDoFilterWasCalled() throws IOException, ServletException {
        filter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(any(ServletRequest.class), any(ServletResponse.class));
    }
}