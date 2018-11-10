package com.shaff.carshop.db.mappers;

import com.shaff.carshop.constants.Gender;
import com.shaff.carshop.db.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setName(resultSet.getString(2));
        user.setLogin(resultSet.getString(3));
        user.setEmail(resultSet.getString(4));
        user.setPassword(resultSet.getString(5));
        user.setGender(Gender.valueOf(resultSet.getString(6).toUpperCase()));
        user.setNewsletter(getNewsletter(resultSet));
        user.setRoleId(resultSet.getInt(8));
        user.setBanExpiration(resultSet.getTimestamp(9));
        return user;
    }

    private boolean getNewsletter(ResultSet resultSet) throws SQLException {
        return resultSet.getInt(7) == 1;
    }
}
