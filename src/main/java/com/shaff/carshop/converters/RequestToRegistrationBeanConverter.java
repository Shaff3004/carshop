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
        UserRegistrationBean target = new UserRegistrationBean();
        populators.forEach(populator -> populator.populate(source, target));
        return target;
    }

    @Override
    public void addPopulator(Populator<HttpServletRequest, UserRegistrationBean> populator) {
        populators.add(populator);
    }
}