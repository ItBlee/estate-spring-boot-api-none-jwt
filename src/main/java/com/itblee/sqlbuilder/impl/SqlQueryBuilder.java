package com.itblee.sqlbuilder.impl;

import com.itblee.sqlbuilder.SqlBuilder;
import com.itblee.sqlbuilder.model.SqlQuery;

import java.io.Serializable;
import java.sql.SQLSyntaxErrorException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

class SqlQueryBuilder implements Serializable, SqlBuilder.Query {

    private static final long serialVersionUID = 6764670140121398972L;

    private final Map<SqlQuery, ?> statements;
    private String finalQuery;

    public SqlQueryBuilder(Map<SqlQuery, ?> statements) {
        this.statements = Collections.unmodifiableMap(statements);
    }

    @Override
    public String build() throws SQLSyntaxErrorException {
        if (finalQuery == null)
            finalQuery = buildQuery();
        return finalQuery;
    }

    @Override
    public String buildQuery() throws SQLSyntaxErrorException {
        StringBuilder sql = new StringBuilder();
        sql.append(buildSelectClause());
        sql.append(buildFromClause());
        sql.append(buildJoinClause());
        sql.append(buildWhereClause());
        System.out.println("\n" + sql);
        return sql.toString();
    }

    @Override
    public StringBuilder buildSelectClause() throws SQLSyntaxErrorException {
        return SqlQueryBuilders.buildSelectClause(statements.keySet());
    }

    @Override
    public StringBuilder buildFromClause() throws SQLSyntaxErrorException {
        return SqlQueryBuilders.buildFromClause(statements.keySet());
    }

    @Override
    public StringBuilder buildJoinClause() throws SQLSyntaxErrorException {
        return SqlQueryBuilders.buildJoinClause(statements.keySet());
    }

    @Override
    public StringBuilder buildWhereClause()  throws SQLSyntaxErrorException {
        return SqlQueryBuilders.buildWhereClause(statements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQueryBuilder)) return false;
        SqlQueryBuilder that = (SqlQueryBuilder) o;
        return Objects.equals(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statements);
    }

}
