package com.shaff.carshop.utils.sql;

import com.shaff.carshop.db.beans.SearchFormBean;
import org.apache.commons.lang3.Range;

import java.util.Map;

public class SQLFormer {

    public String formParametrizedSelectQuery(String what, String tableName, SearchFormBean bean) {
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.select(what).from(tableName).where();
        setUpSingleParameters(sqlBuilder, bean.getSingleParameters());
        setUpRangeParameters(sqlBuilder, bean.getRangeParameters());
        setUpOrdering(sqlBuilder, bean.getOrderBy(), bean.getSort());
        setUpLimit(sqlBuilder, bean.getCurrentPage(), bean.getNumberOfElements());
        return sqlBuilder.build();
    }

    public String formSelectCountQuery(String what, String tableName, SearchFormBean bean) {
        SQLBuilder sqlBuilder = new SQLBuilder();
        sqlBuilder.select(what).from(tableName).where();
        setUpSingleParameters(sqlBuilder, bean.getSingleParameters());
        setUpRangeParameters(sqlBuilder, bean.getRangeParameters());
        return sqlBuilder.build();
    }

    private void setUpSingleParameters(SQLBuilder builder, Map<String, String> parameters) {
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            if (pair.getValue() != null && !pair.getValue().isEmpty()) {
                builder.addParameter(pair.getKey(), pair.getValue());
                builder.and();
            }
        }
    }

    private void setUpRangeParameters(SQLBuilder builder, Map<String, Range<String>> parameters) {
        for (Map.Entry<String, Range<String>> pair : parameters.entrySet()) {
            builder.between(pair.getKey(), pair.getValue().getMinimum(), pair.getValue().getMaximum());
            builder.and();
        }
        builder.removeCharactersFromEnd(5);

    }

    private void setUpOrdering(SQLBuilder builder, String parameter, String ordering) {
        if (checkIfParametersNotEmpty(parameter, ordering)) {
            builder.orderBy(parameter).ordering(ordering);
        }
    }

    private void setUpLimit(SQLBuilder builder, int offset, int limit) {
        if (offset == 1) {
            builder.limit(Integer.toString(0), Integer.toString(limit));
        } else {
            builder.limit(Integer.toString((offset * limit) - limit), Integer.toString(limit));
        }
    }

    private boolean checkIfParametersNotEmpty(String parameter, String ordering) {
        return parameter != null && !parameter.isEmpty() && ordering != null && !ordering.isEmpty();
    }
}