package com.shaff.carshop.converters.populators;

import com.shaff.carshop.constants.Gender;
import com.shaff.carshop.db.beans.UserRegistrationBean;
import com.shaff.carshop.db.entity.User;

public class UserPopulator implements Populator<UserRegistrationBean, User> {
    private static final String ON = "on";

    @Override
    public void populate(UserRegistrationBean source, User target) {
        target.setName(source.getName());
        target.setLogin(source.getLogin());
        target.setEmail(source.getEmail());
        target.setPassword(source.getPassword());
        target.setGender(Gender.valueOf(source.getGender().toUpperCase()));
        if(source.getNewsletter() != null) {
            target.setNewsletter(source.getNewsletter().equals(ON));
        }
        target.setRoleId(2);
    }
}
