package com.shaff.carshop.services;

import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.beans.SearchFormBean;
import com.shaff.carshop.db.dao.impl.CarDaoImpl;
import com.shaff.carshop.exceptions.ApplicationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CarServiceTest extends Assert {
    private CarDaoImpl carDao = mock(CarDaoImpl.class);
    private DataSource dataSource = mock(DataSource.class);
    private Connection connection = mock(Connection.class);
    private TransactionManager manager = spy(new TransactionManager(dataSource));

    @InjectMocks
    private CarService carService = new CarService(manager);

    @Before
    public void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        carService.setCarDao(carDao);
    }

    @Test
    public void shouldVerifyThatAllCrucialMethodsWereCalledUntilOperation() throws ApplicationException {
        carService.getAllSpecificCars(new SearchFormBean());
        verify(carDao, times(1)).getAllCarsByParameters(any(Connection.class), anyString());
    }

    @Test
    public void shouldVerifyThatGetItemFilterCountMethodWasCalled() throws ApplicationException {
        carService.getNumberOfRows(new SearchFormBean());
        verify(carDao, times(1)).getFilterItemCount(any(Connection.class), anyString());
    }

    @Test
    public void shouldVerifyThatGetPriceRangeMethodCalledAllAppropriateMethods() throws ApplicationException {
        carService.getPriceRange();
        verify(carDao, times(1)).getMinValueByColumnName(any(Connection.class), anyString());
        verify(carDao, times(1)).getMaxValueByColumnName(any(Connection.class), anyString());
    }

    @Test
    public void shouldVerifyThatGetYearRangeMethodCalledAllAppropriateMethods() throws ApplicationException {
        carService.getYearRange();
        verify(carDao, times(1)).getMinValueByColumnName(any(Connection.class), anyString());
        verify(carDao, times(1)).getMaxValueByColumnName(any(Connection.class), anyString());
    }

    @Test
    public void shouldVerifyThatGetSpeedRangeMethodCalledAllAppropriateMethods() throws ApplicationException {
        carService.getSpeedRange();
        verify(carDao, times(1)).getMinValueByColumnName(any(Connection.class), anyString());
        verify(carDao, times(1)).getMaxValueByColumnName(any(Connection.class), anyString());
    }

    @Test
    public void shouldVerifyThatGetAvailableCarTypesMethodCalledAllAppropriateMethods() throws ApplicationException {
        carService.getAvailableCarTypes();
        verify(carDao, times(1)).getAllCarTypes(any(Connection.class));
    }
}