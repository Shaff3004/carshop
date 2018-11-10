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
        Order target = new Order();
        populators.forEach(populator -> populator.populate(source, target));
        return target;
    }

    @Override
    public void addPopulator(Populator<Cart, Order> populator) {
        populators.add(populator);
    }
}