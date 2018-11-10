package com.shaff.carshop.services.validators;

import com.shaff.carshop.db.beans.UserLoginBean;

import java.util.ArrayList;
import java.util.List;

public class UserLoginBeanValidator extends AbstractValidator<UserLoginBean> {
    private List<String> errors = new ArrayList<>();
    @Override
    public List<String> validate(UserLoginBean element) {
        validateLogin(element.getLogin(), errors);
        validatePassword(element.getPassword(), errors);
        return errors;
    }
}
