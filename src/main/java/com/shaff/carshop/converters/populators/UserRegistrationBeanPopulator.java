package com.shaff.carshop.converters.populators;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.db.beans.UserRegistrationBean;

import javax.servlet.http.HttpServletRequest;

public class UserRegistrationBeanPopulator implements Populator<HttpServletRequest, UserRegistrationBean> {

    @Override
    public void populate(HttpServletRequest source, UserRegistrationBean target) {
        target.setName(source.getParameter(RequestParameters.NAME));
        target.setLogin(source.getParameter(RequestParameters.LOGIN));
        target.setEmail(source.getParameter(RequestParameters.EMAIL));
        target.setPassword(source.getParameter(RequestParameters.PASSWORD));
        target.setConfirmationPassword(source.getParameter(RequestParameters.CONFIRM_PASSWORD));
        target.setGender(source.getParameter(RequestParameters.GENDER));
        target.setNewsletter(source.getParameter(RequestParameters.NEWSLETTER));
        target.setCaptcha(source.getParameter(RequestParameters.CAPTCHA));
    }
}
