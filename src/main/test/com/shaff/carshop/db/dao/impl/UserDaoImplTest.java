package com.shaff.carshop.db.dao.impl;

import com.shaff.carshop.constants.Gender;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.DBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDaoImplTest extends Assert {
    private static final String TEST_NAME = "Ivan Ivanov";
    private static final String TEST_LOGIN = "Ivanov";
    private static final String TEST_EMAIL = "example@email.ru";
    private static final String TEST_PASSWORD = "qwerty777";
    private static final String TEST_GENDER_VALUE = "male";
    private static final boolean TEST_NEWSLETTER_VALUE = true;
    private static final int TEST_ID = 5;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement pstmt;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Statement statement;
    @InjectMocks
    private UserDaoImpl userDao = new UserDaoImpl();

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        pstmt = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        statement = mock(Statement.class);

        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(pstmt);
        when(connection.createStatement()).thenReturn(statement);

        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(TEST_ID);
        when(resultSet.getString(2)).thenReturn(TEST_NAME);
        when(resultSet.getString(3)).thenReturn(TEST_LOGIN);
        when(resultSet.getString(4)).thenReturn(TEST_EMAIL);
        when(resultSet.getString(5)).thenReturn(TEST_PASSWORD);
        when(resultSet.getString(6)).thenReturn(TEST_GENDER_VALUE);
        when(resultSet.getBoolean(7)).thenReturn(TEST_NEWSLETTER_VALUE);

        when(pstmt.executeQuery()).thenReturn(resultSet);
        when(pstmt.getGeneratedKeys()).thenReturn(resultSet);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }

    @Test
    public void shouldReturnTrueWhenUserWithCurrentLoginDoesNotExist() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(false);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        assertTrue(userDao.getUserByLogin(connection, TEST_LOGIN) == null);
    }

    @Test
    public void shouldReturnFalseWhenUserWithCurrentLoginExists() throws SQLException, DBException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        assertFalse(userDao.getUserByLogin(connection, TEST_LOGIN) == null);
    }

    @Test
    public void shouldReturnTrueWhenUserWithCurrentEmailDoesNotExist() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(false);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        assertTrue(userDao.getUserByEmail(connection, TEST_EMAIL) == null);
    }

    @Test
    public void shouldReturnFalseWhenUserWithCurrentEmailExists() throws DBException, SQLException {
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        assertFalse(userDao.getUserByEmail(connection, TEST_EMAIL) == null);
    }

    @Test
    public void shouldReturnEqualUserInstanceWhenRegistrationFinishedSuccessful() throws DBException, SQLException {
        User testUser = new User(TEST_ID, TEST_NAME, TEST_LOGIN, TEST_EMAIL, TEST_PASSWORD, Gender.valueOf(TEST_GENDER_VALUE.toUpperCase()), TEST_NEWSLETTER_VALUE, 1, null);
        User returnedUser = userDao.create(connection, testUser);
        assertEquals(testUser, returnedUser);
    }

    @Test
    public void shouldReturnExpectedUserInstanceWhenItWasFound() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        User expectedUser = userDao.getEntityById(connection, TEST_ID);

        assertEquals(TEST_NAME, expectedUser.getName());
        assertEquals(TEST_LOGIN, expectedUser.getLogin());
        assertEquals(TEST_EMAIL, expectedUser.getEmail());
        assertEquals(TEST_PASSWORD, expectedUser.getPassword());
        assertEquals(Gender.MALE, expectedUser.getGender());
    }

    @Test
    public void shouldReturnListOfUsersWithSizeEqualsThreeWhenMethodWasCalled() throws SQLException, DBException {
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);

        int expectedSize = 3;
        List<User> users = userDao.getAll(connection);

        assertEquals(expectedSize, users.size());
    }

    @Test
    public void shouldReturnTrueWhenUpdateWasSuccessful() throws DBException, SQLException {
        User testUser = new User(TEST_ID, TEST_NAME, TEST_LOGIN, TEST_EMAIL, TEST_PASSWORD, Gender.valueOf(TEST_GENDER_VALUE.toUpperCase()), TEST_NEWSLETTER_VALUE, 1, null);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        boolean result = userDao.update(connection, testUser);

        assertTrue(result);
    }

    @Test
    public void shouldReturnTrueWhenUserWasDeleted() throws DBException, SQLException {
        int testId = 5;
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        assertTrue(userDao.delete(connection, testId));
    }

    @Test
    public void shouldReturnAppropriateUserInstanceWhenUserWasFound() throws DBException, SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);
        User returnedUser = userDao.getUserByLogin(connection, TEST_LOGIN);

        assertEquals(TEST_NAME, returnedUser.getName());
        assertEquals(TEST_LOGIN, returnedUser.getLogin());
        assertEquals(TEST_EMAIL, returnedUser.getEmail());
        assertEquals(TEST_PASSWORD, returnedUser.getPassword());
        assertEquals(Gender.MALE, returnedUser.getGender());
    }
}