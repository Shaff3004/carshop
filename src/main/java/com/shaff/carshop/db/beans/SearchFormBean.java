package com.shaff.carshop.db.beans;

import org.apache.commons.lang3.Range;

import java.util.LinkedHashMap;
import java.util.Map;

public class SearchFormBean {
    private Map<String, String> singleParameters = new LinkedHashMap<>();
    private Map<String, Range<String>> rangeParameters = new LinkedHashMap<>();
    private int currentPage;
    private int numberOfElements;
    private String orderBy;
    private String sort;

    public Map<String, String> getSingleParameters() {
        return singleParameters;
    }

    public void setSingleParameters(Map<String, String> singleParameters) {
        this.singleParameters = singleParameters;
    }

    public Map<String, Range<String>> getRangeParameters() {
        return rangeParameters;
    }

    public void setRangeParameters(Map<String, Range<String>> rangeParameters) {
        this.rangeParameters = rangeParameters;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "SearchFormBean{" +
                "singleParameters=" + singleParameters +
                ", rangeParameters=" + rangeParameters +
                ", currentPage=" + currentPage +
                ", numberOfElements=" + numberOfElements +
                ", orderBy='" + orderBy + '\'' +
                ", sort='" + sort + '\'' +
                '}';
    }
}
