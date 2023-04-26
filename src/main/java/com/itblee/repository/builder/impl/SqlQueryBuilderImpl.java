package com.itblee.repository.builder.impl;

import java.io.Serializable;
import java.sql.SQLSyntaxErrorException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class SqlQueryBuilderImpl extends AbstractSqlQueryBuilder implements Serializable {

    private static final long serialVersionUID = 6764670140121398972L;

    private final Map<SqlQuery, ?> map;
    private String finalQuery;

    public SqlQueryBuilderImpl(Map<SqlQuery, ?> map) {
        this.map = Collections.unmodifiableMap(map);
    }

    @Override
    public String buildFinalQuery() throws SQLSyntaxErrorException {
        if (finalQuery == null)
            finalQuery = super.buildFinalQuery();
        return finalQuery;
    }

    @Override
    public StringBuilder buildSelectClause() throws SQLSyntaxErrorException {
        return super.buildSelectClause(map.keySet());
    }

    @Override
    public StringBuilder buildFromClause() throws SQLSyntaxErrorException {
        return super.buildFromClause(map.keySet());
    }

    @Override
    public StringBuilder buildJoinClause() throws SQLSyntaxErrorException {
        return super.buildJoinClause(map.keySet());
    }

    @Override
    public StringBuilder buildWhereClause()  throws SQLSyntaxErrorException {
        return super.buildWhereClause(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQueryBuilderImpl)) return false;
        SqlQueryBuilderImpl that = (SqlQueryBuilderImpl) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

}
