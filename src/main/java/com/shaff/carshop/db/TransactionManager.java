package com.shaff.carshop.db;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.exceptions.DBException;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final Logger LOG = Logger.getLogger(TransactionManager.class);
    private DataSource dataSource;

    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T runInTransaction(Transaction<T> transaction) throws ApplicationException {
        T result;
        Connection connection = null;
        try{
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            result = transaction.execute(connection);
            connection.commit();
        } catch (SQLException | DBException e) {
            LOG.error(LoggerMessages.CANNOT_RUN_OPERATION_IN_TRANSACTION, e);
            rollback(connection);
            throw new ApplicationException(e.getMessage(), e);
        } finally {
            closeConnection(connection);
        }
        return result;
    }

    private void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_ROLLBACK, e);
        }
    }

    private void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_CLOSE_CONNECTION, e);
        }
    }
}
