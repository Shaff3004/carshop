package com.shaff.carshop.converters;

import com.shaff.carshop.converters.populators.Populator;
import com.shaff.carshop.db.beans.SearchFormBean;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class RequestToSearchFormBeanConverter implements Converter<HttpServletRequest, SearchFormBean> {
    private List<Populator<HttpServletRequest, SearchFormBean>> populators = new ArrayList<>();

    @Override
    public SearchFormBean convert(HttpServletRequest source) {
        SearchFormBean bean = new SearchFormBean();
        for(Populator<HttpServletRequest, SearchFormBean> currentPopulator: populators){
            currentPopulator.populate(source, bean);
        }
        return bean;
    }

    @Override
    public void addPopulator(Populator<HttpServletRequest, SearchFormBean> populator) {
        populators.add(populator);
    }
}