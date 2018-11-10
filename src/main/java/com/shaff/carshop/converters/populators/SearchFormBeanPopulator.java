package com.shaff.carshop.converters.populators;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.db.beans.SearchFormBean;
import org.apache.commons.lang3.Range;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class SearchFormBeanPopulator implements Populator<HttpServletRequest, SearchFormBean> {

    @Override
    public void populate(HttpServletRequest source, SearchFormBean target) {
        Map<String, String> singleParameters = target.getSingleParameters();
        singleParameters.put(RequestParameters.MARK, source.getParameter(RequestParameters.MARK));
        singleParameters.put(RequestParameters.MODEL, source.getParameter(RequestParameters.MODEL));
        singleParameters.put(RequestParameters.TYPE, source.getParameter(RequestParameters.TYPE));

        Map<String, Range<String>> rangeParameters = target.getRangeParameters();
        rangeParameters.put("year", Range.between(source.getParameter(RequestParameters.MIN_YEAR),
                            source.getParameter(RequestParameters.MAX_YEAR)));
        rangeParameters.put("speed", Range.between(source.getParameter(RequestParameters.MIN_SPEED),
                            source.getParameter(RequestParameters.MAX_SPEED)));
        rangeParameters.put("price", Range.between(source.getParameter(RequestParameters.MIN_PRICE),
                            source.getParameter(RequestParameters.MAX_PRICE)));

        target.setOrderBy(source.getParameter(RequestParameters.SORT_BY_PARAMETER));
        target.setSort(source.getParameter(RequestParameters.ORDER_BY));
        target.setNumberOfElements(Integer.parseInt(source.getParameter(RequestParameters.NUMBER_OF_ELEMENTS)));
        target.setCurrentPage(Integer.parseInt(source.getParameter(RequestParameters.CURRENT_PAGE)));
    }
}
