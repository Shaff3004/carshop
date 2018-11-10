package com.shaff.carshop.services;

import com.shaff.carshop.constants.Gender;
import com.shaff.carshop.db.Transaction;
import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.dao.impl.UserDaoImpl;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.exceptions.DBException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest extends Assert {
    private static final int TEST_ID = 5;
    private static final String TEST_NAME = "Ivan Ivanov";
    private static final String TEST_LOGIN = "Ivanov";
    private static final String TEST_EMAIL = "example@email.ru";
    private static final String TEST_PASSWORD = "qwerty777";
    private static final String TEST_GENDER_VALUE = "male";
    private static final boolean TEST_NEWSLETTER_VALUE = true;

    private UserDaoImpl userDao = mock(UserDaoImpl.class);
    private DataSource dataSource = mock(DataSource.class);
    private Connection connection = mock(Connection.class);
    private MockTransactionManager manager = spy(new MockTransactionManager(dataSource));
    @InjectMocks
    private UserService userService = new UserService(manager);
    private User testUser = new User(TEST_ID, TEST_NAME, TEST_LOGIN, TEST_EMAIL, TEST_PASSWORD, Gender.valueOf(TEST_GENDER_VALUE.toUpperCase()), TEST_NEWSLETTER_VALUE, 1, null);

    @Before
    public void setUp() throws DBException {
        userService.setUserDao(userDao);
        when(userDao.create(any(Connection.class), any(User.class))).thenReturn(testUser);
    }

    @Test
    public void shouldVerifyThatAllRequiredDaoMethodsWereCalledWhenRegistrationWasFinished() throws ApplicationException {
        userService.registerNewUser(testUser);
        verify(userDao, times(1)).getUserByEmail(connection, testUser.getEmail());
        verify(userDao, times(1)).create(connection, testUser);
        verify(userDao, times(1)).getUserByLogin(connection, testUser.getLogin());
    }

    @Test
    public void shouldReturnUserInstanceWithEqualFieldValuesVWhenRegistrationWasFinishedSuccessfully() throws ApplicationException {
        User returnedUser = userService.registerNewUser(testUser);
        assertEquals(TEST_NAME, returnedUser.getName());
        assertEquals(TEST_LOGIN, returnedUser.getLogin());
        assertEquals(TEST_EMAIL, returnedUser.getEmail());
        assertEquals(TEST_PASSWORD, returnedUser.getPassword());
        assertEquals(Gender.MALE, returnedUser.getGender());
    }

    @Test
    public void shouldReturnUserInstanceWithEqualFieldsWhenMethodWasCalled() throws ApplicationException {
        when(userDao.getUserByLogin(connection, TEST_LOGIN)).thenReturn(testUser);
        User returnedUser = userService.getUserByLogin(testUser.getLogin());

        assertEquals(TEST_NAME, returnedUser.getName());
        assertEquals(TEST_LOGIN, returnedUser.getLogin());
        assertEquals(TEST_EMAIL, returnedUser.getEmail());
        assertEquals(TEST_PASSWORD, returnedUser.getPassword());
        assertEquals(Gender.MALE, returnedUser.getGender());
    }

    @Test
    public void shouldReturnTrueWhenUserWithCurrentLoginDoesNotExist() throws ApplicationException {
        assertTrue(userService.checkIfLoginNotExists(testUser.getLogin()));
    }

    @Test
    public void shouldReturnFalseWhenUserWithCurrentLoginExists() throws ApplicationException {
        when(userDao.getUserByLogin(connection, TEST_LOGIN)).thenReturn(testUser);
        assertFalse(userService.checkIfLoginNotExists(testUser.getLogin()));
    }

    @Test
    public void shouldReturnTrueWhenUserWithCurrentEmailDoesNotExist() throws ApplicationException {
        assertTrue(userService.checkIfEmailNotExists(testUser.getEmail()));
    }

    @Test
    public void shouldReturnFalseWhenUserWithCurrentEmailExists() throws ApplicationException {
        when(userDao.getUserByEmail(connection, testUser.getEmail())).thenReturn(testUser);
        assertFalse(userService.checkIfEmailNotExists(testUser.getEmail()));
    }

    class MockTransactionManager extends TransactionManager {

        MockTransactionManager(DataSource dataSource) {
            super(dataSource);
        }

        @Override
        public <T> T runInTransaction(Transaction<T> transaction) throws ApplicationException {
            try {
                return transaction.execute(connection);
            } catch (SQLException e) {
                throw new ApplicationException();
            }
        }
    }
}