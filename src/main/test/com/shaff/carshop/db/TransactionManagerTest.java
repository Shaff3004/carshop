package com.shaff.carshop.db;

import com.shaff.carshop.constants.Gender;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.ApplicationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionManagerTest extends Assert {
    private static final String TEST_NAME = "Ivan Ivanov";
    private static final String TEST_LOGIN = "Ivanov";
    private static final String TEST_EMAIL = "example@email.ru";
    private static final String TEST_PASSWORD = "qwerty777";
    private static final String TEST_GENDER_VALUE = "male";
    private static final boolean TEST_NEWSLETTER_VALUE = true;
    private static final int TEST_ID = 5;
    @Mock
    private DataSource dataSource = mock(DataSource.class);
    @Mock
    Connection connection;
    @InjectMocks
    private TransactionManager manager = new TransactionManager(dataSource);

    @Before
    public void setUp() throws SQLException {
        connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        doNothing().when(connection).close();
    }

    @Test
    public void shouldReturnExpectedUserInstanceWhenTransactionWasFinishedSuccessful() throws ApplicationException {
        User user = manager.runInTransaction(connection -> new User(TEST_ID, TEST_NAME, TEST_LOGIN, TEST_EMAIL,
                TEST_PASSWORD, Gender.valueOf(TEST_GENDER_VALUE.toUpperCase()), TEST_NEWSLETTER_VALUE, 1, null));

        assertEquals(TEST_NAME, user.getName());
        assertEquals(TEST_LOGIN, user.getLogin());
        assertEquals(TEST_EMAIL, user.getEmail());
        assertEquals(TEST_PASSWORD, user.getPassword());
        assertEquals(Gender.MALE, user.getGender());
    }

    @Test(expected = ApplicationException.class)
    public void shouldVerifyThatRollbackWasCalledWhenTransactionThrewException() throws SQLException, ApplicationException {
        manager.runInTransaction((Transaction<User>) connection -> {
            throw new SQLException();
        });
        verify(connection, times(1)).rollback();
    }
}