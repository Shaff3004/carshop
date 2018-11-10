package com.shaff.carshop.db.entity;

import com.shaff.carshop.db.beans.OrderItemBean;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Date date;
    private String status;
    private String message;
    private List<OrderItemBean> items;

    public Order() {

    }

    public Order(int id, int userId, Date date, String status, String message, List<OrderItemBean> items) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
        this.message = message;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<OrderItemBean> getItems() {
        return items;
    }

    public void setItems(List<OrderItemBean> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", date=" + date +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", items=" + items +
                '}';
    }
}