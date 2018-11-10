package com.shaff.carshop.db.dao.impl;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.SQL;
import com.shaff.carshop.db.dao.GenericDao;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.db.mappers.UserRowMapper;
import com.shaff.carshop.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements GenericDao<User, Integer> {
    private static final Logger LOG = Logger.getLogger(UserDaoImpl.class);
    private UserRowMapper rowMapper = new UserRowMapper();

    @Override
    public User create(Connection connection, User entity) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.CREATE_NEW_USER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getLogin());
            pstmt.setString(3, entity.getEmail());
            pstmt.setString(4, entity.getPassword());
            pstmt.setString(5, entity.getGender().name().toLowerCase());
            pstmt.setBoolean(6, entity.isNewsletter());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_REGISTER_NEW_USER, e);
            throw new DBException(LoggerMessages.CANNOT_REGISTER_NEW_USER, e);
        }
        return entity;
    }

    @Override
    public List<User> getAll(Connection connection) throws DBException {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery(SQL.GET_ALL_USERS)) {
            while (resultSet.next()) {
                users.add(rowMapper.mapRow(resultSet));
            }
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_GET_ALL_USERS, e);
            throw new DBException(LoggerMessages.CANNOT_GET_ALL_USERS, e);
        }
        return users;
    }

    @Override
    public User getEntityById(Connection connection, Integer id) throws DBException {
        User user = null;
        ResultSet resultSet;
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.GET_USER_BY_ID)) {
            pstmt.setInt(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                user = rowMapper.mapRow(resultSet);
            }
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_GET_USER, e);
            throw new DBException(LoggerMessages.CANNOT_GET_USER, e);
        }
        return user;
    }

    @Override
    public boolean update(Connection connection, User entity) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.UPDATE_USER)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getLogin());
            pstmt.setString(3, entity.getEmail());
            pstmt.setString(4, entity.getPassword());
            pstmt.setString(5, entity.getGender().name().toLowerCase());
            pstmt.setBoolean(6, entity.isNewsletter());
            pstmt.setInt(7, entity.getRoleId());
            pstmt.setTimestamp(8, entity.getBanExpiration());
            pstmt.setInt(9, entity.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_UPDATE_USER, e);
            throw new DBException(LoggerMessages.CANNOT_UPDATE_USER, e);
        }
        return true;
    }

    @Override
    public boolean delete(Connection connection, Integer id) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.DELETE_USER_BY_ID)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error(LoggerMessages.CANNOT_DELETE_USER + e.getMessage());
            throw new DBException(LoggerMessages.CANNOT_DELETE_USER, e);
        }
        return true;
    }

    public User getUserByLogin(Connection connection, String login) throws DBException {
        return getUserByParameter(connection, SQL.GET_USER_BY_LOGIN, login);
    }

    public User getUserByEmail(Connection connection, String email) throws DBException {
        return getUserByParameter(connection, SQL.GET_USER_BY_EMAIL, email);
    }

    private User getUserByParameter(Connection connection, String sql, String... parameters) throws DBException {
        User user = null;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            int index = 0;
            for (String parameter : parameters) {
                preparedStatement.setString(++index, parameter);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = rowMapper.mapRow(resultSet);
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
            throw new DBException(LoggerMessages.CANNOT_MAKE_OPERATION, ex);
        }
        return user;
    }
}