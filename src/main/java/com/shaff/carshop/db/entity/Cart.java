package com.shaff.carshop.db.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable {
    private Map<Car, Integer> cart = new LinkedHashMap<>();

    public boolean addItemToCart(Car car) {
        cart.put(car, 1 + countDuplicates(car));
        return true;
    }

    public BigDecimal getTotalCost() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Car, Integer> pair : cart.entrySet()) {
            totalPrice = totalPrice.add(calculateCost(pair.getValue(), pair.getKey().getPrice()));
        }
        return totalPrice;
    }

    public int getTotalItemCount() {
        int itemCount = 0;
        for (Integer currentValue : cart.values()) {
            itemCount += currentValue;
        }
        return itemCount;
    }

    public boolean reduceItemQuantity(Car car) {
        for (Map.Entry<Car, Integer> pair : cart.entrySet()) {
            if (pair.getKey().equals(car) && pair.getValue() > 1) {
                pair.setValue(pair.getValue() - 1);
                return true;
            } else {
                removeItemFromCart(car);
                return true;
            }
        }
        return false;
    }

    public Integer removeItemFromCart(Car car) {
        return cart.remove(car);
    }

    private BigDecimal calculateCost(int quantity, BigDecimal price) {
        return price.multiply(new BigDecimal(quantity));
    }

    private int countDuplicates(Car car) {
        int numberOfDuplicates = 0;
        for (Map.Entry<Car, Integer> pair : cart.entrySet()) {
            if (pair.getKey().equals(car)) {
                numberOfDuplicates = pair.getValue();
                break;
            }
        }
        return numberOfDuplicates;
    }

    public Map<Car, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<Car, Integer> cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cart=" + cart +
                '}';
    }
}