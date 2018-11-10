package com.shaff.carshop.services;

import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.beans.OrderItemBean;
import com.shaff.carshop.db.dao.impl.CarDaoImpl;
import com.shaff.carshop.db.dao.impl.OrderDaoImpl;
import com.shaff.carshop.db.dao.impl.UserDaoImpl;
import com.shaff.carshop.db.entity.Order;
import com.shaff.carshop.exceptions.ApplicationException;

public class OrderService {
    private TransactionManager transactionManager;
    private OrderDaoImpl orderDao;
    private UserDaoImpl userDao;
    private CarDaoImpl carDao;

    public OrderService(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public boolean addNewOrder(Order order) throws ApplicationException {
        return transactionManager.runInTransaction(x -> {
            Order returnedOrder = orderDao.create(x, order);
            for (OrderItemBean bean : order.getItems()) {
                orderDao.addNewRowToOrderItemTable(x, returnedOrder.getId(), bean);
            }
            return true;
        });
    }

    public Order getConcreteOrderById(int id) throws ApplicationException {
        return transactionManager.runInTransaction(x -> {
            Order order = orderDao.getEntityById(x, id);
            order.setItems(orderDao.getAllRowsFromOrderItemById(x, id));
            return order;
        });
    }

    public OrderDaoImpl getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }

    public UserDaoImpl getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public CarDaoImpl getCarDao() {
        return carDao;
    }

    public void setCarDao(CarDaoImpl carDao) {
        this.carDao = carDao;
    }
}