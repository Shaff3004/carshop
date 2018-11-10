package com.shaff.carshop.converters.populators;

import com.shaff.carshop.db.beans.OrderItemBean;
import com.shaff.carshop.db.entity.Car;
import com.shaff.carshop.db.entity.Cart;
import com.shaff.carshop.db.entity.Order;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderPopulator implements Populator<Cart, Order> {
    private static final String ACCEPTED_STATUS = "registered";

    @Override
    public void populate(Cart source, Order target) {
        target.setDate(new Date());
        target.setStatus(ACCEPTED_STATUS);
        target.setMessage(StringUtils.EMPTY);
        List<OrderItemBean> itemsFromCart = new ArrayList<>();
        for (Map.Entry<Car, Integer> pair : source.getCart().entrySet()) {
            itemsFromCart.add(new OrderItemBean(pair.getKey().getId(), pair.getValue(), pair.getKey().getPrice()));
        }
        target.setItems(itemsFromCart);
    }
}