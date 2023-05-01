package com.itblee.sqlbuilder.impl;

import com.itblee.sqlbuilder.SqlBuilder;
import com.itblee.sqlbuilder.SqlMap;
import com.itblee.sqlbuilder.model.SqlQuery;
import com.itblee.util.ValidateUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SqlBuilderFactory {

    private final SqlMap<?> statements;

    public SqlBuilderFactory(SqlMap<?> statements) {
        this.statements = statements;
    }

	public SqlBuilder getInstance(String action) {
        switch (action) {
            case "query":
                return newQueryBuilder();
            case "insert":
                return newInsertBuilder();
            case "update":
                return newUpdateBuilder();
            case "delete":
                return newDeleteBuilder();
            default:
                throw new UnsupportedOperationException();
        }
    }

    private SqlBuilder newQueryBuilder() {
        ValidateUtils.requireNonNull(statements);
        Map<SqlQuery, Object> queries = new LinkedHashMap<>();
        statements.entrySet().stream()
                .filter(stmt -> stmt.getKey() instanceof SqlQuery)
                .forEach(stmt -> queries.put((SqlQuery) stmt.getKey(), stmt.getValue()));
        return new SqlQueryBuilderImpl(queries);
    }

    private SqlBuilder newInsertBuilder() {
        throw new UnsupportedOperationException();
    }

    private SqlBuilder newUpdateBuilder() {
        throw new UnsupportedOperationException();
    }

    private SqlBuilder newDeleteBuilder() {
        throw new UnsupportedOperationException();
    }
}
