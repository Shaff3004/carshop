package com.shaff.carshop.converters.populators;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.db.beans.UserLoginBean;

import javax.servlet.http.HttpServletRequest;

public class UserLoginBeanPopulator implements Populator<HttpServletRequest, UserLoginBean> {
    @Override
    public void populate(HttpServletRequest source, UserLoginBean target) {
        target.setLogin(source.getParameter(RequestParameters.LOGIN));
        target.setPassword(source.getParameter(RequestParameters.PASSWORD));
    }
}
