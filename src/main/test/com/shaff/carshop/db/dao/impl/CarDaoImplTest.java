package com.shaff.carshop.db.dao.impl;

import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.exceptions.DBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarDaoImplTest extends Assert {
    private static final int TEST_ID = 1;
    private static final String TEST_MARK = "Toyota";
    private static final String TEST_MODEL = "Supra";
    private static final String TEST_BODY_TYPE = "sedan";
    private static final int TEST_YEAR = 1996;
    private static final int TEST_MAX_SPEED = 260;
    private static final int TEST_PRICE = 22500;
    private Car testCar = new Car(TEST_ID, TEST_MARK, TEST_MODEL, TEST_BODY_TYPE, TEST_YEAR, TEST_MAX_SPEED, new BigDecimal(TEST_PRICE));
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement pstmt;
    @Mock
    private Statement stmt;
    @Mock
    private ResultSet resultSet;
    @InjectMocks
    private CarDaoImpl carDao = new CarDaoImpl();

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        pstmt = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        stmt = mock(Statement.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(pstmt);
        when(connection.createStatement()).thenReturn(stmt);

        when(resultSet.getInt(1)).thenReturn(TEST_ID);
        when(resultSet.getString(2)).thenReturn(TEST_MARK);
        when(resultSet.getString(3)).thenReturn(TEST_MODEL);
        when(resultSet.getString(4)).thenReturn(TEST_BODY_TYPE);
        when(resultSet.getInt(5)).thenReturn(TEST_YEAR);
        when(resultSet.getInt(6)).thenReturn(TEST_MAX_SPEED);
        when(resultSet.getInt(7)).thenReturn(TEST_PRICE);

        when(pstmt.executeQuery()).thenReturn(resultSet);
        when(pstmt.getGeneratedKeys()).thenReturn(resultSet);
        when(stmt.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    public void shouldReturnCarInstanceWithEqualFieldValuesWhenCarWasCreatedSuccessfully() throws DBException {
        Car returnedCar = carDao.create(connection, testCar);

        assertEquals(TEST_ID, returnedCar.getId());
        assertEquals(TEST_MARK, returnedCar.getMark());
        assertEquals(TEST_MODEL, returnedCar.getModel());
        assertEquals(TEST_BODY_TYPE, returnedCar.getType());
        assertEquals(TEST_PRICE, returnedCar.getPrice().intValue());
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCreationFailed() throws SQLException, DBException {
        when(pstmt.executeUpdate()).thenThrow(SQLException.class);
        carDao.create(connection, testCar);
    }

    @Test
    public void shouldReturnTrueWhenSizeOfCarListWasChanged() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        List<Car> cars = new ArrayList<>();

        assertTrue(cars.size() == 0);
        cars = carDao.getAll(connection);
        assertTrue(cars.size() > 0);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenGetAllMethodFailed() throws SQLException, DBException {
        when(stmt.executeQuery(anyString())).thenThrow(SQLException.class);
        carDao.getAll(connection);
    }

    @Test
    public void shouldReturnCarWithEqualFieldsWhenItWasFound() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        Car returnedCar = carDao.getEntityById(connection, TEST_ID);

        assertEquals(TEST_ID, returnedCar.getId());
        assertEquals(TEST_MODEL, returnedCar.getModel());
        assertEquals(TEST_MAX_SPEED, returnedCar.getMaxSpeed());
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCannotGetCarById() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        carDao.getEntityById(connection, TEST_ID);
    }

    @Test
    public void shouldReturnTrueWhenElementWasUpdated() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        testCar.setMark("Lada");
        testCar.setModel("Priora");
        boolean result = carDao.update(connection, testCar);

        assertTrue(result);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCannotUpdateTable() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        carDao.update(connection, testCar);
    }

    @Test
    public void shouldReturnTrueWhenCarWasDeleted() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        boolean result = carDao.delete(connection, TEST_ID);
        assertTrue(result);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCannotDeleteCarById() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        carDao.delete(connection, TEST_ID);
    }

    @Test
    public void shouldReturnListOfAppropriateCarInstancesWhenMethodWasCalled() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        String testSql = "SELECT * FROM carshop.cars WHERE mark='Toyota' LIMIT 0, 10";
        List<Car> result = carDao.getAllCarsByParameters(connection, testSql);

        assertTrue(result.size() == 4);
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCannotGetListOfCars() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        String testSql = "SELECT * FROM table";
        carDao.getAllCarsByParameters(connection, testSql);
    }

    @Test
    public void shouldReturnCountOfAppropriateCarsEqualFiveWhenMethodWasCalled() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        long expectedValue = 5L;
        when(resultSet.getLong(anyInt())).thenReturn(expectedValue);
        String testSql = "SELECT COUNT(*) FROM carshop.cars WHERE mark = 'Toyota'";

        long returnedCount = carDao.getFilterItemCount(connection, testSql);
        assertEquals(expectedValue, returnedCount);

    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCannotGetCountOfAppropriateElements() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        String testSql = "SELECT COUNT(*) FROM table";
        carDao.getFilterItemCount(connection, testSql);
    }

    @Test
    public void shouldReturnExpectedMaxPriceValueWhenMethodWasCalled() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        int expectedMaxPrice = 50000;
        when(resultSet.getInt(anyInt())).thenReturn(expectedMaxPrice);

        int returnedMaxPrice = carDao.getMaxValueByColumnName(connection, "price");
        assertEquals(expectedMaxPrice, returnedMaxPrice);
    }

    @Test(expected = DBException.class)
    public void shouldReturnNewDBExceptionInstanceWhenCannotGetMaxPrice() throws DBException, SQLException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        carDao.getMaxValueByColumnName(connection, "price");
    }

    @Test
    public void shouldReturnExpectedMinPriceValueWhenMethodWasCalled() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        int expectedMinPrice = 10000;
        when(resultSet.getInt(anyInt())).thenReturn(expectedMinPrice);

        int returnedMinPrice = carDao.getMinValueByColumnName(connection, "price");
        assertEquals(expectedMinPrice, returnedMinPrice);
    }

    @Test(expected = DBException.class)
    public void shouldReturnNewDBExceptionInstanceWhenCannotGetMinPrice() throws DBException, SQLException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        carDao.getMinValueByColumnName(connection, "price");
    }

    @Test
    public void shouldReturnAllExistingCarTypesWhenMethodWasCalled() throws SQLException, DBException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        String expectedType = "sedan";
        when(resultSet.getString(anyInt())).thenReturn(expectedType);

        List<String> carTypes = carDao.getAllCarTypes(connection);
        assertEquals(expectedType, carTypes.get(0));
    }

    @Test(expected = DBException.class)
    public void shouldThrowNewDBExceptionInstanceWhenCannotGetCarTypes() throws DBException, SQLException {
        when(connection.createStatement()).thenThrow(SQLException.class);
        carDao.getAllCarTypes(connection);
    }
}