package com.shaff.carshop.db.mappers;

import com.shaff.carshop.db.entity.Order;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt(1));
        order.setUserId(resultSet.getInt(2));
        order.setDate(resultSet.getDate(3));
        order.setStatus(resultSet.getString(4));
        order.setMessage(resultSet.getString(5));
        return order;
    }
}
