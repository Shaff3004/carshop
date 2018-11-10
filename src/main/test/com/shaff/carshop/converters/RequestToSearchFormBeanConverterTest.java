package com.shaff.carshop.converters;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.converters.populators.SearchFormBeanPopulator;
import com.shaff.carshop.db.beans.SearchFormBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestToSearchFormBeanConverterTest extends Assert {
    private static final String TEST_CURRENT_PAGE = "1";
    private static final String TEST_NUMBER_OF_ELEMENTS = "10";
    private static final String TEST_MARK = "Toyota";
    private static final String TEST_MODEL = "Supra";
    private static final String TEST_BODY_TYPE = "sedan";
    private static final String TEST_MIN_YEAR = "1990";
    private static final String TEST_MAX_YEAR = "2000";
    private static final String TEST_MIN_SPEED = "150";
    private static final String TEST_MAX_SPEED = "320";
    private static final String TEST_MIN_PRICE = "10000";
    private static final String TEST_MAX_PRICE = "35000";
    private static final String SORT_BY_PRICE = "price";
    private static final String ORDERING = "DESC";
    @Mock
    private HttpServletRequest request;
    @InjectMocks
    private SearchFormBeanPopulator populator = new SearchFormBeanPopulator();

    @Before
    public void setUp() {
        request = mock(HttpServletRequest.class);

        when(request.getParameter(RequestParameters.CURRENT_PAGE)).thenReturn(TEST_CURRENT_PAGE);
        when(request.getParameter(RequestParameters.NUMBER_OF_ELEMENTS)).thenReturn(TEST_NUMBER_OF_ELEMENTS);
        when(request.getParameter(RequestParameters.MARK)).thenReturn(TEST_MARK);
        when(request.getParameter(RequestParameters.MODEL)).thenReturn(TEST_MODEL);
        when(request.getParameter(RequestParameters.TYPE)).thenReturn(TEST_BODY_TYPE);
        when(request.getParameter(RequestParameters.MIN_YEAR)).thenReturn(TEST_MIN_YEAR);
        when(request.getParameter(RequestParameters.MAX_YEAR)).thenReturn(TEST_MAX_YEAR);
        when(request.getParameter(RequestParameters.MIN_SPEED)).thenReturn(TEST_MIN_SPEED);
        when(request.getParameter(RequestParameters.MAX_SPEED)).thenReturn(TEST_MAX_SPEED);
        when(request.getParameter(RequestParameters.MIN_PRICE)).thenReturn(TEST_MIN_PRICE);
        when(request.getParameter(RequestParameters.MAX_PRICE)).thenReturn(TEST_MAX_PRICE);
        when(request.getParameter(RequestParameters.SORT_BY_PARAMETER)).thenReturn(SORT_BY_PRICE);
        when(request.getParameter(RequestParameters.ORDER_BY)).thenReturn(ORDERING);
    }

    @Test
    public void shouldConvertRequestToSearchFormBeanWhenMethodWasCalled() {
        RequestToSearchFormBeanConverter converter = new RequestToSearchFormBeanConverter();
        converter.addPopulator(populator);

        SearchFormBean convertedBean = converter.convert(request);
        Map<String, String> parameters = convertedBean.getSingleParameters();

        assertEquals(TEST_CURRENT_PAGE, parameters.get(RequestParameters.CURRENT_PAGE));
        assertEquals(TEST_NUMBER_OF_ELEMENTS, parameters.get(RequestParameters.NUMBER_OF_ELEMENTS));
        assertEquals(TEST_MARK, parameters.get(RequestParameters.MARK));
        assertEquals(TEST_MODEL, parameters.get(RequestParameters.MODEL));
        assertEquals(TEST_BODY_TYPE, parameters.get(RequestParameters.TYPE));
        assertEquals(TEST_MIN_YEAR, parameters.get(RequestParameters.MIN_YEAR));
        assertEquals(TEST_MAX_YEAR, parameters.get(RequestParameters.MAX_YEAR));
        assertEquals(TEST_MIN_SPEED, parameters.get(RequestParameters.MIN_SPEED));
        assertEquals(TEST_MAX_SPEED, parameters.get(RequestParameters.MAX_SPEED));
        assertEquals(TEST_MIN_PRICE, parameters.get(RequestParameters.MIN_PRICE));
        assertEquals(TEST_MAX_PRICE, parameters.get(RequestParameters.MAX_PRICE));
        assertEquals(SORT_BY_PRICE, parameters.get(RequestParameters.SORT_BY_PARAMETER));
        assertEquals(ORDERING, parameters.get(RequestParameters.ORDER_BY));
    }
}