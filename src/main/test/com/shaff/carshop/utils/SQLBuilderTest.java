package com.shaff.carshop.utils;

import com.shaff.carshop.utils.sql.SQLBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SQLBuilderTest extends Assert{
    private SQLBuilder builder;

    @Before
    public void setUp(){
        builder = new SQLBuilder();
    }

    @Test
    public void shouldAppendSelectClauseWhenMethodWasCalled(){
        String testValue = "*";
        String expectedString = "SELECT *";
        String returnedString = builder.select(testValue).build();

        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendFromClauseWhenMethodWasCalled(){
        String testValue = "mydb.myelements";
        String expectedString = " FROM mydb.myelements";
        String returnedString = builder.from(testValue).build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendWhereClauseWhenMethodWasCalled(){
        String expectedString = " WHERE ";
        String returnedString = builder.where().build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendParameterWhenMethodWasCalled(){
        String expectedString = "type='sedan'";
        String returnedString = builder.addParameter("type", "sedan").build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendRangeParameterWhenMethodWasCalled(){
        String expectedString ="price BETWEEN 10000 AND 20000";
        String returnedString = builder.between("price", "10000", "20000").build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendAndClauseWhenMethodWasCalled(){
        String expectedString = " AND ";
        String returnedString = builder.and().build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendOrderByClauseWhenMethodWasCalled(){
        String expectedString = " ORDER BY price";
        String returnedString = builder.orderBy("price").build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendSortDirectionWhenMethodWasCalled(){
        String expectedString = " ASC ";
        String returnedString = builder.ordering("ASC").build();
        assertEquals(expectedString, returnedString);
    }

    @Test
    public void shouldAppendLimitClauseWhenMethodWasCalled(){
        String expectedString = " LIMIT 0,5";
        String returnedString = builder.limit("0", "5").build();
        assertEquals(expectedString, returnedString);
    }
}