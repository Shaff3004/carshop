package com.shaff.carshop.db.dao.impl;

import com.shaff.carshop.db.beans.OrderItemBean;
import com.shaff.carshop.db.entity.Order;
import com.shaff.carshop.exceptions.DBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderDaoImplTest extends Assert {
    private static final int TEST_ID = 1;
    private static final int TEST_USER_ID = 30;
    private static final Date TEST_DATE = new Date(new java.util.Date().getTime());
    private static final String TEST_STATUS = "registered";
    private static final String TEST_MESSAGE = "do not touch";
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement pstmt;
    @Mock
    private Statement stmt;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private OrderDaoImpl orderDao = new OrderDaoImpl();
    private Order testOrder = new Order();
    private OrderItemBean bean = new OrderItemBean();

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        pstmt = mock(PreparedStatement.class);
        stmt = mock(Statement.class);
        resultSet = mock(ResultSet.class);

        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(connection.createStatement()).thenReturn(stmt);

        when(resultSet.getInt(1)).thenReturn(TEST_ID);
        when(resultSet.getInt(2)).thenReturn(TEST_USER_ID);
        when(resultSet.getDate(3)).thenReturn(TEST_DATE);
        when(resultSet.getString(4)).thenReturn(TEST_STATUS);
        when(resultSet.getString(5)).thenReturn(TEST_MESSAGE);

        when(pstmt.executeQuery()).thenReturn(resultSet);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);

        setUpOrderFields();
    }

    private void setUpOrderFields() {
        testOrder.setUserId(TEST_USER_ID);
        testOrder.setDate(TEST_DATE);
        testOrder.setStatus(TEST_STATUS);
        testOrder.setMessage(TEST_MESSAGE);

        bean.setCarId(1);
        bean.setQuantity(2);
        bean.setCost(new BigDecimal(20000));
    }

    @Test
    public void shouldReturnEntityWithSettledIdWhenOperationFinishedSuccessfully() throws DBException, SQLException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(pstmt.getGeneratedKeys()).thenReturn(resultSet);

        assertTrue(testOrder.getId() == 0);
        Order returnedOrder = orderDao.create(connection, testOrder);
        assertTrue(returnedOrder.getId() == TEST_ID);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenMethodWasCalled() throws SQLException, DBException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(pstmt);
        when(pstmt.getGeneratedKeys()).thenThrow(SQLException.class);
        orderDao.create(connection, testOrder);
    }

    @Test
    public void shouldReturnAllOrdersWhenMethodWasCalled() throws SQLException, DBException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<Order> result = orderDao.getAll(connection);
        int expectedSize = 2;
        assertEquals(expectedSize, result.size());
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenImpossibleGetAllUsers() throws SQLException, DBException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        orderDao.getAll(connection);
    }

    @Test
    public void shouldReturnAppropriateEntityWhenMethodWasCalled() throws SQLException, DBException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        Order returnedOrder = orderDao.getEntityById(connection, TEST_ID);

        assertEquals(TEST_ID, returnedOrder.getId());
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenImpossibleToGetOrderById() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        orderDao.getEntityById(connection, TEST_ID);
    }

    @Test
    public void shouldReturnTrueWhenOrderWasUpdatedSuccessfully() throws DBException, SQLException {
        boolean result = orderDao.update(connection, testOrder);

        assertTrue(result);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenItIsImpossibleToUpdateOrder() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        orderDao.update(connection, testOrder);
    }

    @Test
    public void shouldReturnTrueWhenUserWasRemoved() throws SQLException, DBException {
        boolean result = orderDao.delete(connection, TEST_ID);

        assertTrue(result);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenItIsImpossibleToDeleteOrder() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        orderDao.delete(connection, TEST_ID);
    }

    @Test
    public void shouldReturnTrueWhenNewRowWasInsertedToOrderItemTable() throws DBException {
        boolean result = orderDao.addNewRowToOrderItemTable(connection, TEST_ID, bean);
        assertTrue(result);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenItIsImpossibleToInsertNewRow() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        orderDao.addNewRowToOrderItemTable(connection, TEST_ID, bean);
    }

    @Test
    public void shouldReturnListOfItemsWithSizeEqualTwoWhenMethodWasCalled() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        List<OrderItemBean> result = orderDao.getAllRowsFromOrderItemById(connection, TEST_ID);

        int expectedSize = 2;
        assertEquals(expectedSize, result.size());
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenItIsImpossibleToGetAllRowsById() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        orderDao.getAllRowsFromOrderItemById(connection, TEST_ID);
    }
}