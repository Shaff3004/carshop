package com.shaff.carshop.db;

import com.shaff.carshop.exceptions.DBException;

import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction<T> {
    T execute(Connection connection) throws SQLException, DBException;
}
