package com.shaff.carshop.services;

import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.dao.impl.CarDaoImpl;
import com.shaff.carshop.db.dao.impl.OrderDaoImpl;
import com.shaff.carshop.db.dao.impl.UserDaoImpl;
import com.shaff.carshop.db.entity.User;
import com.shaff.carshop.exceptions.ApplicationException;

public class UserService {
    private UserDaoImpl userDao;
    private CarDaoImpl carDao;
    private OrderDaoImpl orderDao;
    private TransactionManager transactionManager;

    public UserService(TransactionManager transactionManager) {

        this.transactionManager = transactionManager;
    }

    public User registerNewUser(User user) throws ApplicationException {
        return transactionManager.runInTransaction(x -> {
            if (userDao.getUserByLogin(x, user.getLogin()) == null && userDao.getUserByEmail(x, user.getEmail()) == null) {
                userDao.create(x, user);
                return user;
            }
            return null;
        });
    }

    public boolean blockUser(User user) throws ApplicationException {
        return transactionManager.runInTransaction(x -> userDao.update(x, user));
    }

    public User getUserByLogin(String login) throws ApplicationException {
        return transactionManager.runInTransaction(x -> userDao.getUserByLogin(x, login));
    }

    public boolean checkIfEmailNotExists(String email) throws ApplicationException {
        return transactionManager.runInTransaction(x -> userDao.getUserByEmail(x, email) == null);
    }

    public boolean checkIfLoginNotExists(String login) throws ApplicationException {
        return transactionManager.runInTransaction(x -> userDao.getUserByLogin(x, login) == null);
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

    public OrderDaoImpl getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDaoImpl orderDao) {
        this.orderDao = orderDao;
    }
}