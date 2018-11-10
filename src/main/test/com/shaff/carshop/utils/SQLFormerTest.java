package com.shaff.carshop.utils;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.db.beans.SearchFormBean;
import com.shaff.carshop.utils.sql.SQLFormer;
import org.apache.commons.lang3.Range;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLFormerTest extends Assert {
    private static final String TABLE_NAME = "carshop.cars";
    private static final String SELECT_ALL = "*";
    private static final String SELECT_COUNT_ALL = "COUNT(*)";
    private static final String TEST_MARK = "Toyota";
    private static final String TEST_MODEL = "Supra";
    private static final String TEST_MIN_PRICE = "1000";
    private static final String TEST_MAX_PRICE = "2000";
    private SQLFormer former;
    private SearchFormBean bean;

    @Before
    public void setUp() {
        former = new SQLFormer();
        bean = mock(SearchFormBean.class);
        when(bean.getNumberOfElements()).thenReturn(5);
        when(bean.getCurrentPage()).thenReturn(1);
        when(bean.getOrderBy()).thenReturn("");
        when(bean.getSort()).thenReturn("");
        Map<String, String> singleParameters = new LinkedHashMap<>();
        singleParameters.put(RequestParameters.MARK, TEST_MARK);
        singleParameters.put(RequestParameters.MODEL, TEST_MODEL);
        Map<String, Range<String>> rangeParameters = new LinkedHashMap<>();
        rangeParameters.put("price", Range.between(TEST_MIN_PRICE, TEST_MAX_PRICE));
        when(bean.getSingleParameters()).thenReturn(singleParameters);
        when(bean.getRangeParameters()).thenReturn(rangeParameters);
    }

    @Test
    public void shouldReturnEqualSelectAllQueryWhenMethodWasCalled() {
        String expectedQuery = "SELECT * FROM carshop.cars WHERE mark='Toyota' AND model='Supra' AND price BETWEEN 1000 AND 2000 LIMIT 0,5";
        String returnedQuery = former.formParametrizedSelectQuery(SELECT_ALL,TABLE_NAME, bean);
        assertEquals(expectedQuery, returnedQuery);
    }

    @Test
    public void shouldReturnEqualSelectCountQueryWhenMethodWasCalled(){
        String expectedQuery = "SELECT COUNT(*) FROM carshop.cars WHERE mark='Toyota' AND model='Supra' AND price BETWEEN 1000 AND 2000";
        String returnedQuery = former.formSelectCountQuery(SELECT_COUNT_ALL, TABLE_NAME, bean);
        assertEquals(expectedQuery, returnedQuery);
    }
}