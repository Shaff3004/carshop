package com.shaff.carshop.services.validators;

import com.shaff.carshop.db.beans.UserRegistrationBean;

import java.util.ArrayList;
import java.util.List;

public class UserRegistrationBeanValidator extends AbstractValidator<UserRegistrationBean> {
    private List<String> errors = new ArrayList<>();

    public UserRegistrationBeanValidator(){

    }

    @Override
    public List<String> validate(UserRegistrationBean element) {
        validateName(element.getName(), errors);
        validateLogin(element.getLogin(), errors);
        validateEmail(element.getEmail(), errors);
        validatePassword(element.getPassword(), errors);
        validateConfirmationPassword(element.getPassword(), element.getConfirmationPassword(), errors);
        validateCaptcha(element.getCaptcha(), errors);
        return errors;
    }
}
