package com.shaff.carshop.converters;

import com.shaff.carshop.converters.populators.Populator;
import com.shaff.carshop.db.beans.UserRegistrationBean;
import com.shaff.carshop.db.entity.User;

import java.util.ArrayList;
import java.util.List;

public class RegistrationBeanConverter implements Converter<UserRegistrationBean, User> {
    private List<Populator<UserRegistrationBean, User>> populators = new ArrayList<>();

    @Override
    public User convert(UserRegistrationBean source) {
        User user = new User();
        for (Populator<UserRegistrationBean, User> currentPopulator : populators) {
            currentPopulator.populate(source, user);
        }
        return user;
    }

    @Override
    public void addPopulator(Populator<UserRegistrationBean, User> populator) {
        populators.add(populator);
    }
}