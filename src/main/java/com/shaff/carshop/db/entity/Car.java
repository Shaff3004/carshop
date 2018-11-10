package com.shaff.carshop.db.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Car {
    private int id;
    private String mark;
    private String model;
    private String type;
    private int year;
    private int maxSpeed;
    private BigDecimal price;

    public Car(){

    }

    public Car(int id, String mark, String model, String type, int year, int maxSpeed, BigDecimal price) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.type = type;
        this.year = year;
        this.maxSpeed = maxSpeed;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return getId() == car.getId() &&
                getYear() == car.getYear() &&
                getMaxSpeed() == car.getMaxSpeed() &&
                Objects.equals(getMark(), car.getMark()) &&
                Objects.equals(getModel(), car.getModel()) &&
                Objects.equals(getType(), car.getType()) &&
                Objects.equals(getPrice(), car.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMark(), getModel(), getType(), getYear(), getMaxSpeed(), getPrice());
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", mark='" + mark + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                ", maxSpeed=" + maxSpeed +
                ", price=" + price +
                '}';
    }
}
