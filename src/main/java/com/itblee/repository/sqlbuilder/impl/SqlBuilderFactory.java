package com.itblee.repository.sqlbuilder.impl;

import com.itblee.repository.sqlbuilder.SqlBuilder;
import com.itblee.repository.sqlbuilder.SqlExecutor;
import com.itblee.repository.sqlbuilder.SqlExtractor;
import com.itblee.repository.sqlbuilder.SqlMap;
import com.itblee.repository.sqlbuilder.model.SqlQuery;
import com.itblee.util.ValidateUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public class SqlBuilderFactory {

    private final SqlMap<?> statements;
    private SqlBuilder builder;
    private SqlExecutor executor;

    private SqlBuilderFactory(SqlMap<?> statements) {
        this.statements = statements;
    }

    public static SqlBuilderFactory newInstance(String action, SqlMap<?> statements) {
        ValidateUtils.requireNonNull(statements);
        SqlBuilderFactory factory = new SqlBuilderFactory(statements);
        switch (action) {
            case "query":
                SqlExtractor extractor = new ResultSetExtractor();
                factory.executor = new SqlExecutorImpl(extractor);
                factory.builder = factory.newQueryBuilder();
                break;
            case "insert":
                factory.executor = new SqlExecutorImpl(null);
                factory.builder = factory.newInsertBuilder();
                break;
            case "update":
                factory.executor = new SqlExecutorImpl(null);
                factory.builder = factory.newUpdateBuilder();
                break;
            case "delete":
                factory.executor = new SqlExecutorImpl(null);
                factory.builder = factory.newDeleteBuilder();
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return factory;
    }

    public SqlBuilder getBuilder() {
        return this.builder;
    }

    public SqlExecutor getExecutor() {
        return this.executor;
    }

    private SqlBuilder newQueryBuilder() {
        Map<SqlQuery, Object> queries = new LinkedHashMap<>();
        statements.entrySet().stream()
                .filter(stmt -> stmt.getKey() instanceof SqlQuery)
                .forEach(stmt -> queries.put((SqlQuery) stmt.getKey(), stmt.getValue()));
        return new SqlQueryBuilder(queries);
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
