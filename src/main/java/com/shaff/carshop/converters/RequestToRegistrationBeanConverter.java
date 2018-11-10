package com.shaff.carshop.converters;

import com.shaff.carshop.converters.populators.Populator;
import com.shaff.carshop.db.beans.UserRegistrationBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RequestToRegistrationBeanConverter implements Converter<HttpServletRequest, UserRegistrationBean> {
    private List<Populator<HttpServletRequest, UserRegistrationBean>> populators = new ArrayList<>();

    @Override
    public UserRegistrationBean convert(HttpServletRequest source) {
        UserRegistrationBean bean = new UserRegistrationBean();
        for (Populator<HttpServletRequest, UserRegistrationBean> currentPopulator : populators) {
            currentPopulator.populate(source, bean);
        }
        return bean;
    }

    @Override
    public void addPopulator(Populator<HttpServletRequest, UserRegistrationBean> populator) {
        populators.add(populator);
    }
}