package com.shaff.carshop.db.mappers;

import com.shaff.carshop.db.beans.OrderItemBean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItemBean> {

    @Override
    public OrderItemBean mapRow(ResultSet resultSet) throws SQLException {
        OrderItemBean bean = new OrderItemBean();
        bean.setCarId(resultSet.getInt(2));
        bean.setQuantity(resultSet.getInt(3));
        bean.setCost(resultSet.getBigDecimal(4));
        return bean;
    }
}
