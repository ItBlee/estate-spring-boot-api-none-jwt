package com.itblee.repository.sqlbuilder.impl;

import com.itblee.repository.sqlbuilder.SqlQueryBuilder;
import com.itblee.repository.sqlbuilder.model.SqlQuery;

import java.io.Serializable;
import java.sql.SQLSyntaxErrorException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

class SqlQueryBuilderImpl implements Serializable, SqlQueryBuilder {

    private static final long serialVersionUID = 6764670140121398972L;

    private final Map<SqlQuery, ?> statements;
    private String finalQuery;

    public SqlQueryBuilderImpl(Map<SqlQuery, ?> statements) {
        this.statements = Collections.unmodifiableMap(statements);
    }

    @Override
    public String build() throws SQLSyntaxErrorException {
        if (finalQuery == null)
            finalQuery = buildQuery() + " GROUP BY id ";
        return finalQuery;
    }

    @Override
    public String buildQuery() throws SQLSyntaxErrorException {
        StringBuilder sql = new StringBuilder();
        sql.append(buildSelectClause());
        sql.append(buildFromClause());
        sql.append(buildJoinClause());
        sql.append(buildWhereClause());
        return sql.toString();
    }

    @Override
    public StringBuilder buildSelectClause() throws SQLSyntaxErrorException {
        return SqlQueryBuilder.buildSelectClause(statements.keySet());
    }

    @Override
    public StringBuilder buildFromClause() throws SQLSyntaxErrorException {
        return SqlQueryBuilder.buildFromClause(statements.keySet());
    }

    @Override
    public StringBuilder buildJoinClause() throws SQLSyntaxErrorException {
        return SqlQueryBuilder.buildJoinClause(statements.keySet());
    }

    @Override
    public StringBuilder buildWhereClause()  throws SQLSyntaxErrorException {
        return SqlQueryBuilder.buildWhereClause(statements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQueryBuilderImpl)) return false;
        SqlQueryBuilderImpl that = (SqlQueryBuilderImpl) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

}
