package com.shaff.carshop.converters;

import com.shaff.carshop.converters.populators.Populator;
import com.shaff.carshop.db.entity.Cart;
import com.shaff.carshop.db.entity.Order;

import java.util.ArrayList;
import java.util.List;

public class CartToOrderConverter implements Converter<Cart, Order> {
    private List<Populator<Cart, Order>> populators = new ArrayList<>();

    @Override
    public Order convert(Cart source) {
        Order order = new Order();
        for (Populator<Cart, Order> currentPopulator : populators) {
            currentPopulator.populate(source, order);
        }
        return order;
    }

    @Override
    public void addPopulator(Populator<Cart, Order> populator) {
        populators.add(populator);
    }
}