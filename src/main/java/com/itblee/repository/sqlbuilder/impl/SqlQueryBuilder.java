package com.itblee.repository.sqlbuilder.impl;

import com.itblee.repository.sqlbuilder.QueryBuilder;
import com.itblee.repository.sqlbuilder.SqlBuilder;
import com.itblee.repository.sqlbuilder.SqlFormatter;
import com.itblee.repository.sqlbuilder.model.SqlQuery;

import java.io.Serializable;
import java.sql.SQLSyntaxErrorException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static com.itblee.constant.SystemConstants.DEBUG_PRINT_RESULTS;

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
        if (DEBUG_PRINT_RESULTS)
            System.out.println("\n" + new SqlFormatter().format(sql.toString()));
        return sql.toString();
    }

    @Override
    public StringBuilder buildSelectClause() throws SQLSyntaxErrorException {
        return QueryBuilder.buildSelectClause(statements.keySet());
    }

    @Override
    public StringBuilder buildFromClause() throws SQLSyntaxErrorException {
        return QueryBuilder.buildFromClause(statements.keySet());
    }

    @Override
    public StringBuilder buildJoinClause() throws SQLSyntaxErrorException {
        return QueryBuilder.buildJoinClause(statements.keySet());
    }

    @Override
    public StringBuilder buildWhereClause()  throws SQLSyntaxErrorException {
        return QueryBuilder.buildWhereClause(statements);
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
