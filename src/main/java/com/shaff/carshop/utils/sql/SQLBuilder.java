package com.shaff.carshop.utils.sql;

public class SQLBuilder {
    private final StringBuilder query;

    public SQLBuilder() {
        this.query = new StringBuilder();
    }

    public SQLBuilder select(String toBeSelected) {
        query.append("SELECT ").append(toBeSelected);
        return this;
    }

    public SQLBuilder from(String from) {
        query.append(" FROM ").append(from);
        return this;
    }

    public SQLBuilder where() {
        query.append(" WHERE ");
        return this;
    }

    public SQLBuilder between(String parameter, String start, String end) {
        query.append(parameter).append(" BETWEEN ").append(start).append(" AND ").append(end);
        return this;
    }

    public SQLBuilder addParameter(String parameter, String value) {
        query.append(parameter).append("=").append("\'").append(value).append("\'");
        return this;
    }

    public SQLBuilder and() {
        query.append(" AND ");
        return this;
    }

    public SQLBuilder orderBy(String parameter) {
        query.append(" ORDER BY ").append(parameter);
        return this;
    }

    public SQLBuilder ordering(String order) {
        query.append(" ").append(order).append(" ");
        return this;
    }

    public SQLBuilder limit(String offset, String limit) {
        query.append(" LIMIT ").append(offset).append(",").append(limit);
        return this;
    }

    public SQLBuilder removeCharactersFromEnd(int number){
        query.delete(query.length() - number, query.length());
        return this;
    }

    public String build() {
        return query.toString();
    }
}