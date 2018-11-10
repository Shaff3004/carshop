package com.shaff.carshop.web.servlets;

import com.shaff.carshop.constants.ContextAttributes;
import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.constants.ServletPath;
import com.shaff.carshop.converters.RequestToSearchFormBeanConverter;
import com.shaff.carshop.converters.populators.SearchFormBeanPopulator;
import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.beans.SearchFormBean;
import com.shaff.carshop.db.dao.impl.CarDaoImpl;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.services.CarService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ParametrizedSearchServletTest extends Assert {
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    private DataSource dataSource = mock(DataSource.class);
    private TransactionManager manager = new TransactionManager(dataSource);
    private CarService carService = spy(new CarService(manager));

    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpServletResponse response = mock(HttpServletResponse.class);
    private ParametrizedSearchServlet searchServlet = spy(new ParametrizedSearchServlet());

    @Before
    public void setUp() throws SQLException, ServletException, IOException {
        setUpServletConfigBehaviour();
        setUpRequestParameters();
        setUpConnectionBehavior();
    }

    private void setUpConnectionBehavior() throws SQLException {
        PreparedStatement pstmt = mock(PreparedStatement.class);
        Statement stmt = mock(Statement.class);
        ResultSet resultSet = mock(ResultSet.class);
        Connection connection = mock(Connection.class);

        when(dataSource.getConnection()).thenReturn(connection);
        doNothing().when(connection).close();
        when(connection.createStatement()).thenReturn(stmt);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);
        when(pstmt.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
    }

    private void setUpServletConfigBehaviour() {
        ServletConfig config = mock(ServletConfig.class);
        ServletContext context = mock(ServletContext.class);
        when(config.getServletContext()).thenReturn(context);
        when(searchServlet.getServletConfig()).thenReturn(config);

        RequestToSearchFormBeanConverter converter = new RequestToSearchFormBeanConverter();
        converter.addPopulator(new SearchFormBeanPopulator());
        when(context.getAttribute(ContextAttributes.REQUEST_TO_SEARCH_FORM_BEAN_CONVERTER)).thenReturn(converter);
        carService.setCarDao(new CarDaoImpl());
        when(context.getAttribute(ContextAttributes.CAR_SERVICE)).thenReturn(carService);
    }

    private void setUpRequestParameters() throws ServletException, IOException {
        when(request.getParameter(RequestParameters.MARK)).thenReturn("Toyota");
        when(request.getParameter(RequestParameters.MODEL)).thenReturn("Supra");
        when(request.getParameter(RequestParameters.TYPE)).thenReturn("sedan");
        when(request.getParameter(RequestParameters.MIN_YEAR)).thenReturn("1990");
        when(request.getParameter(RequestParameters.MAX_YEAR)).thenReturn("2000");
        when(request.getParameter(RequestParameters.MIN_SPEED)).thenReturn("200");
        when(request.getParameter(RequestParameters.MAX_SPEED)).thenReturn("300");
        when(request.getParameter(RequestParameters.MIN_PRICE)).thenReturn("20000");
        when(request.getParameter(RequestParameters.MAX_PRICE)).thenReturn("40000");
        when(request.getParameter(RequestParameters.SORT_BY_PARAMETER)).thenReturn("price");
        when(request.getParameter(RequestParameters.ORDER_BY)).thenReturn("ASC");
        when(request.getParameter(RequestParameters.CURRENT_PAGE)).thenReturn("1");
        when(request.getParameter(RequestParameters.NUMBER_OF_ELEMENTS)).thenReturn("5");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        doNothing().when(dispatcher).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void shouldVerifyThatGetAllSpecificCarsMethodWasCalledOnlyOnce() throws ServletException, IOException, ApplicationException {
        searchServlet.doGet(request, response);
        verify(carService, times(1)).getAllSpecificCars(any(SearchFormBean.class));
    }

    @Test
    public void shouldVerifyThatGetNumberOfRowsMethodWasCalledOnlyOnce() throws ServletException, IOException, ApplicationException {
        searchServlet.doGet(request, response);
        verify(carService, times(1)).getNumberOfRows(any(SearchFormBean.class));
    }

    @Test
    public void shouldVerifyThatSetAttributeMethodWasCalledSixTimes() throws ServletException, IOException {
        searchServlet.doGet(request, response);
        verify(request, times(6)).setAttribute(anyString(), any(SearchFormBean.class));
    }

    @Test
    public void shouldVerifyThatForwardMethodWasCalled() throws ServletException, IOException {
        searchServlet.doGet(request, response);
        verify(dispatcher, times(1)).forward(any(HttpServletRequest.class), any(HttpServletResponse.class));
    }

    @Test
    public void shouldVerifyThatSendRedirectMethodWasCalledWhenErrorOccurred() throws ApplicationException, IOException, ServletException {
        SearchFormBean bean = mock(SearchFormBean.class);
        when(carService.getNumberOfRows(bean)).thenThrow(ApplicationException.class);
        searchServlet.doGet(request, response);
        verify(response, times(1)).sendRedirect(ServletPath.HOME);
    }
}