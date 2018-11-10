package com.shaff.carshop.db.dao.impl;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.constants.SQL;
import com.shaff.carshop.db.beans.OrderItemBean;
import com.shaff.carshop.db.dao.GenericDao;
import com.shaff.carshop.db.entity.Order;
import com.shaff.carshop.db.mappers.OrderItemRowMapper;
import com.shaff.carshop.db.mappers.OrderRowMapper;
import com.shaff.carshop.exceptions.DBException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements GenericDao<Order, Integer> {
    private static final Logger LOG = Logger.getLogger(OrderDaoImpl.class);
    private OrderRowMapper orderRowMapper = new OrderRowMapper();
    private OrderItemRowMapper orderItemRowMapper = new OrderItemRowMapper();

    @Override
    public Order create(Connection connection, Order entity) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.CREATE_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, entity.getUserId());
            pstmt.setDate(2, new java.sql.Date(entity.getDate().getTime()));
            pstmt.setString(3, entity.getStatus());
            pstmt.setString(4, entity.getMessage());
            pstmt.executeUpdate();
            ResultSet resultSet = pstmt.getGeneratedKeys();
            while (resultSet.next()) {
                entity.setId(resultSet.getInt(1));
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_CREATE_NEW_ORDER, ex);
            throw new DBException(LoggerMessages.CANNOT_CREATE_NEW_ORDER, ex);
        }
        return entity;
    }

    @Override
    public List<Order> getAll(Connection connection) throws DBException {
        List<Order> orders = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(SQL.GET_ALL_ORDERS);
            while (resultSet.next()) {
                orders.add(orderRowMapper.mapRow(resultSet));
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_GET_ALL_ORDERS, ex);
            throw new DBException(LoggerMessages.CANNOT_GET_ALL_ORDERS, ex);
        }
        return orders;
    }

    @Override
    public Order getEntityById(Connection connection, Integer id) throws DBException {
        Order order = new Order();
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.GET_ORDER_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                order = orderRowMapper.mapRow(resultSet);
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_GET_ORDER_BY_ID, ex);
            throw new DBException(LoggerMessages.CANNOT_GET_ORDER_BY_ID, ex);
        }
        return order;
    }

    @Override
    public boolean update(Connection connection, Order entity) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.UPDATE_ORDER)) {
            pstmt.setInt(1, entity.getUserId());
            pstmt.setDate(2, new Date(entity.getDate().getTime()));
            pstmt.setString(3, entity.getStatus());
            pstmt.setString(4, entity.getMessage());
            pstmt.setInt(5, entity.getId());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_UPDATE_ORDER, ex);
            throw new DBException(LoggerMessages.CANNOT_UPDATE_ORDER, ex);
        }
        return true;
    }

    @Override
    public boolean delete(Connection connection, Integer id) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.DELETE_ORDER_BY_ID)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_DELETE_ORDER_BY_ID, ex);
            throw new DBException(LoggerMessages.CANNOT_DELETE_ORDER_BY_ID, ex);
        }
        return true;
    }

    public boolean addNewRowToOrderItemTable(Connection connection, int id, OrderItemBean bean) throws DBException {
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.ADD_NEW_ORDER_ITEM_COLUMN)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, bean.getCarId());
            pstmt.setInt(3, bean.getQuantity());
            pstmt.setBigDecimal(4, bean.getCost());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_ADD_NEW_ROW_TO_ORDER_ITEMS, ex);
            throw new DBException(LoggerMessages.CANNOT_ADD_NEW_ROW_TO_ORDER_ITEMS, ex);
        }
        return true;
    }

    public List<OrderItemBean> getAllRowsFromOrderItemById(Connection connection, int id) throws DBException {
        List<OrderItemBean> result = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(SQL.GET_ALL_ORDER_ITEMS_BY_ID)) {
            pstmt.setInt(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                result.add(orderItemRowMapper.mapRow(resultSet));
            }
        } catch (SQLException ex) {
            LOG.error(LoggerMessages.CANNOT_GET_ORDER_ITEMS, ex);
            throw new DBException(LoggerMessages.CANNOT_GET_ORDER_ITEMS, ex);
        }
        return result;
    }
}