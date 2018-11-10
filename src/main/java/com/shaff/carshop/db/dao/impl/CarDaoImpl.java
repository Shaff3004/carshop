package com.shaff.carshop.db.dao.impl;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.SQL;
import com.shaff.carshop.db.dao.GenericDao;
import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.db.mappers.CarRowMapper;
import com.shaff.carshop.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class CarDaoImpl implements GenericDao<Car, Integer> {
    private static final Logger LOG = Logger.getLogger(CarDaoImpl.class);
    private static final String MIN = "MIN";
    private static final String MAX = "MAX";
    private CarRowMapper rowMapper = new CarRowMapper();

    @Override
    public Car create(Connection connection, Car entity) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.CREATE_NEW_CAR, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getMark());
            pstmt.setString(2, entity.getModel());
            pstmt.setString(3, entity.getType());
            pstmt.setInt(4, entity.getYear());
            pstmt.setInt(5, entity.getMaxSpeed());
            pstmt.setBigDecimal(6, entity.getPrice());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_CREATE_NEW_CAR, ex);
            throw new DBException(LoggerMessages.CANNOT_CREATE_NEW_CAR, ex);
        }
        return entity;
    }

    @Override
    public List<Car> getAll(Connection connection) throws DBException {
        List<Car> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(SQL.GET_ALL_CARS);
            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
            throw new DBException(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
        }
        return result;
    }

    @Override
    public Car getEntityById(Connection connection, Integer id) throws DBException {
        Car car = null;
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.GET_CAR_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                car = rowMapper.mapRow(resultSet);
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_GET_CAR_BY_ID, ex);
            throw new DBException(LoggerMessages.CANNOT_GET_CAR_BY_ID, ex);
        }
        return car;
    }

    @Override
    public boolean update(Connection connection, Car entity) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.UPDATE_CAR)) {
            pstmt.setString(1, entity.getMark());
            pstmt.setString(2, entity.getModel());
            pstmt.setString(3, entity.getType());
            pstmt.setInt(4, entity.getYear());
            pstmt.setInt(5, entity.getMaxSpeed());
            pstmt.setBigDecimal(6, entity.getPrice());
            pstmt.setInt(7, entity.getId());
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_UPDATE_CAR, ex);
            throw new DBException(LoggerMessages.CANNOT_UPDATE_CAR, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Connection connection, Integer id) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.DELETE_CAR_BY_ID)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_REMOVE_CAR_BY_ID, ex);
            throw new DBException(LoggerMessages.CANNOT_REMOVE_CAR_BY_ID, ex);
        }
        return true;
    }

    public List<Car> getAllCarsByParameters(Connection connection, String sql) throws DBException {
        List<Car> cars = new ArrayList<>();
        ResultSet resultSet;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                cars.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, e);
            throw new DBException(LoggerMessages.CANNOT_MAKE_OPERATION, e);
        }
        return cars;
    }

    public long getFilterItemCount(Connection connection, String sql) throws DBException {
        ResultSet resultSet;
        long count = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
            throw new DBException(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
        }
        return count;
    }

    public int getMaxValueByColumnName(Connection connection, String columnName) throws DBException {
        return getExtremumValueByColumnName(connection, MAX, columnName);
    }

    public int getMinValueByColumnName(Connection connection, String columnName) throws DBException {
        return getExtremumValueByColumnName(connection, MIN, columnName);
    }

    private int getExtremumValueByColumnName(Connection connection, String value, String columnName) throws DBException {
        int result = 0;
        try (Statement stmt = connection.createStatement();
             Formatter formatter = new Formatter()) {

            ResultSet resultSet = stmt.executeQuery(formatter.format(SQL.SELECT_VALUE_TEMPLATE, value, columnName).toString());
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_GET_MIN_VALUE, ex);
            throw new DBException(LoggerMessages.CANNOT_GET_MIN_VALUE, ex);
        }
        return result;
    }

    public List<String> getAllCarTypes(Connection connection) throws DBException {
        List<String> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(SQL.GET_ALL_CAR_TYPES);
            while (resultSet.next()) {
                result.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_GET_CAR_TYPES, ex);
            throw new DBException(LoggerMessages.CANNOT_GET_CAR_TYPES, ex);
        }
        return result;
    }
}