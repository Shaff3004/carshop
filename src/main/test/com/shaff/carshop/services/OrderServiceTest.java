package com.shaff.carshop.services;

import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.beans.OrderItemBean;
import com.shaff.carshop.db.dao.impl.OrderDaoImpl;
import com.shaff.carshop.db.entity.Order;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.exceptions.DBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Arrays;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OrderServiceTest extends Assert{
    private static final int TEST_ID = 1;
    private static final int TEST_USER_ID = 30;
    private static final Date TEST_DATE = new Date(new java.util.Date().getTime());
    private static final String TEST_STATUS = "registered";
    private static final String TEST_MESSAGE = "do not touch";
    private static final int TEST_CAR_ID = 2;
    private static final int TEST_QUANTITY = 5;
    private static final int TEST_PRICE_VALUE = 20000;
    private OrderDaoImpl orderDao = mock(OrderDaoImpl.class);
    private DataSource dataSource = mock(DataSource.class);
    private Connection connection = mock(Connection.class);
    private TransactionManager manager = spy(new TransactionManager(dataSource));
    private Order testOrder = new Order();

    @InjectMocks
    private OrderService orderService = new OrderService(manager);

    @Before
    public void setUp() throws SQLException, DBException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(orderDao.create(any(Connection.class), any(Order.class))).thenReturn(testOrder);

        setUpOrderFields();
        orderService.setOrderDao(orderDao);
    }

    private void setUpOrderFields(){
        testOrder.setId(TEST_ID);
        testOrder.setUserId(TEST_USER_ID);
        testOrder.setDate(TEST_DATE);
        testOrder.setStatus(TEST_STATUS);
        testOrder.setMessage(TEST_MESSAGE);
        testOrder.setItems(Arrays.asList(new OrderItemBean(TEST_CAR_ID, TEST_QUANTITY, new BigDecimal(TEST_PRICE_VALUE))));
    }

    @Test
    public void shouldVerifyThatOrderDaoCreateMethodWasCalled() throws ApplicationException {
        orderService.addNewOrder(testOrder);
        verify(orderDao, times(1)).create(any(Connection.class), any(Order.class));
    }

    @Test
    public void shouldVerifyThatOrderDaoAddNewRowToOrderItemTableMethodWasCalled() throws ApplicationException {
        orderService.addNewOrder(testOrder);
        verify(orderDao, times(1)).addNewRowToOrderItemTable(connection, TEST_ID, testOrder.getItems().get(0));
    }

    @Test
    public void shouldVerifyThanOrderDaoGetAllRowsByIdMethodWasCalled() throws ApplicationException {
        when(orderDao.getEntityById(any(Connection.class), anyInt())).thenReturn(testOrder);

        orderService.getConcreteOrderById(TEST_ID);
        verify(orderDao, times(1)).getAllRowsFromOrderItemById(connection, TEST_ID);
    }
}