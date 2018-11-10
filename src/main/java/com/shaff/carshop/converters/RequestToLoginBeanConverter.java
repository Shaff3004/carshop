package com.shaff.carshop.converters;

import com.shaff.carshop.converters.populators.Populator;
import com.shaff.carshop.db.beans.UserLoginBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RequestToLoginBeanConverter implements Converter<HttpServletRequest, UserLoginBean> {
    private List<Populator<HttpServletRequest, UserLoginBean>> populators = new ArrayList<>();

    @Override
    public UserLoginBean convert(HttpServletRequest source) {
        UserLoginBean bean = new UserLoginBean();
        for (Populator<HttpServletRequest, UserLoginBean> currentPopulator : populators) {
            currentPopulator.populate(source, bean);
        }
        return bean;
    }

    @Override
    public void addPopulator(Populator<HttpServletRequest, UserLoginBean> populator) {
        populators.add(populator);
    }
}