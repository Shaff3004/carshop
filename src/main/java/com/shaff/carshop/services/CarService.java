package com.shaff.carshop.services;

import com.shaff.carshop.constants.ColumnNames;
import com.shaff.carshop.db.TransactionManager;
import com.shaff.carshop.db.beans.SearchFormBean;
import com.shaff.carshop.db.dao.impl.CarDaoImpl;
import com.shaff.carshop.db.dao.impl.OrderDaoImpl;
import com.shaff.carshop.db.dao.impl.UserDaoImpl;
import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.exceptions.ApplicationException;
import com.shaff.carshop.utils.sql.SQLFormer;
import org.apache.commons.lang3.Range;

import java.util.List;

public class CarService {
    private static final String CAR_TABLE = "carshop.cars";
    private static final String SELECT_ALL_OPERATION = "*";
    private static final String SELECT_COUNT_ALL_OPERATION = "COUNT(*)";
    private TransactionManager transactionManager;
    private OrderDaoImpl orderDao;
    private UserDaoImpl userDao;
    private CarDaoImpl carDao;
    private SQLFormer former;

    public CarService(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.former = new SQLFormer();
    }

    public List<Car> getAllSpecificCars(SearchFormBean bean) throws ApplicationException {
        String query = former.formParametrizedSelectQuery(SELECT_ALL_OPERATION, CAR_TABLE, bean);
        return transactionManager.runInTransaction(x -> carDao.getAllCarsByParameters(x, query));
    }

    public long getNumberOfRows(SearchFormBean bean) throws ApplicationException {
        String query = former.formSelectCountQuery(SELECT_COUNT_ALL_OPERATION, CAR_TABLE, bean);
        return transactionManager.runInTransaction(x -> carDao.getFilterItemCount(x, query));
    }

    public Range<Integer> getPriceRange() throws ApplicationException {
        return transactionManager.runInTransaction(x -> {
            int minimum = carDao.getMinValueByColumnName(x, ColumnNames.PRICE);
            int maximum = carDao.getMaxValueByColumnName(x, ColumnNames.PRICE);
            return Range.between(minimum, maximum);
        });
    }

    public Range<Integer> getYearRange() throws ApplicationException {
        return transactionManager.runInTransaction(x -> {
            int minimum = carDao.getMinValueByColumnName(x, ColumnNames.YEAR);
            int maximum = carDao.getMaxValueByColumnName(x, ColumnNames.YEAR);
            return Range.between(minimum, maximum);
        });
    }

    public Range<Integer> getSpeedRange() throws ApplicationException {
        return transactionManager.runInTransaction(x -> {
            int minimum = carDao.getMinValueByColumnName(x, ColumnNames.MAX_SPEED);
            int maximum = carDao.getMaxValueByColumnName(x, ColumnNames.MAX_SPEED);
            return Range.between(minimum, maximum);
        });
    }

    public List<String> getAvailableCarTypes() throws ApplicationException {
        return transactionManager.runInTransaction(x -> carDao.getAllCarTypes(x));
    }

    public Car getCarById(int id) throws ApplicationException {
        return transactionManager.runInTransaction(x -> carDao.getEntityById(x, id));
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