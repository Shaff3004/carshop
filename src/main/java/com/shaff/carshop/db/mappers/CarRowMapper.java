package com.shaff.carshop.db.mappers;

import com.shaff.carshop.db.entity.Car;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarRowMapper implements RowMapper<Car> {

    @Override
    public Car mapRow(ResultSet resultSet) throws SQLException {
        Car car = new Car();
        car.setId(resultSet.getInt(1));
        car.setMark(resultSet.getString(2));
        car.setModel(resultSet.getString(3));
        car.setType(resultSet.getString(4));
        car.setYear(resultSet.getInt(5));
        car.setMaxSpeed(resultSet.getInt(6));
        car.setPrice(new BigDecimal(resultSet.getInt(7)));
        return car;
    }
}