package com.shaff.carshop.db.beans;

import java.math.BigDecimal;

public class OrderItemBean {
    private int carId;
    private int quantity;
    private BigDecimal cost;

    public OrderItemBean(){

    }

    public OrderItemBean(int carId, int quantity, BigDecimal cost) {
        this.carId = carId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}