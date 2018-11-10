package com.shaff.carshop.db.dao;

import com.shaff.carshop.exceptions.DBException;

import java.sql.Connection;
import java.util.List;

public interface GenericDao<T, K> {
    T create(Connection connection, T entity) throws DBException;
    List<T> getAll(Connection connection) throws DBException;
    T getEntityById(Connection connection, K id) throws DBException;
    boolean update(Connection connection, T entity) throws DBException;
    boolean delete(Connection connection, K id) throws DBException;
}
