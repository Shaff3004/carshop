package com.shaff.carshop.constants;

public final class SQL {
    public static final String CREATE_NEW_USER = "INSERT INTO carshop.users VALUES(default,?,?,?,?,?,?, 2, null)";
    public static final String GET_USER_BY_LOGIN = "SELECT * FROM carshop.users WHERE login = ?";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM carshop.users WHERE email = ?";
    public static final String DELETE_USER_BY_ID = "DELETE FROM carshop.users WHERE id = ?";
    public static final String GET_USER_BY_ID = "SELECT * FROM carshop.users WHERE id = ?";
    public static final String GET_ALL_USERS = "SELECT * FROM carshop.users";
    public static final String UPDATE_USER = "UPDATE carshop.users SET name =?,login =?,email=?,password=?,gender=?,newsletter=?, roleId=?, banExpiration=? WHERE id =?";
    public static final String GET_ALL_CARS = "SELECT * FROM carshop.cars";
    public static final String GET_ALL_CAR_TYPES = "SELECT DISTINCT type FROM carshop.cars";
    public static final String CREATE_NEW_CAR = "INSERT INTO carshop.cars VALUES(default, ?,?,?,?,?,?)";
    public static final String GET_CAR_BY_ID = "SELECT * FROM carshop.cars WHERE id =?";
    public static final String UPDATE_CAR = "UPDATE carshop.cars SET mark=?, model=?,type=?,year=?,speed=?,price=? WHERE id=?";
    public static final String DELETE_CAR_BY_ID = "DELETE FROM carshop.cars WHERE id=?";
    public static final String SELECT_VALUE_TEMPLATE = "SELECT %s(%s) FROM carshop.cars";
    public static final String CREATE_NEW_ORDER = "INSERT INTO carshop.orders VALUES(default,?,?,?,?)";
    public static final String ADD_NEW_ORDER_ITEM_COLUMN = "INSERT INTO carshop.orders_items VALUES(?,?,?,?)";
    public static final String GET_ALL_ORDERS = "SELECT * FROM carshop.orders";
    public static final String GET_ORDER_BY_ID = "SELECT * FROM carshop.orders WHERE id = ?";
    public static final String UPDATE_ORDER = "UPDATE carshop.orders SET userId = ?, date = ?, status = ?, message =? WHERE id = ?";
    public static final String DELETE_ORDER_BY_ID = "DELETE FROM carshop.orders WHERE id = ?";
    public static final String GET_ALL_ORDER_ITEMS_BY_ID = "SELECT * FROM carshop.orders_items WHERE order_id = ?";

    private SQL(){

    }
}