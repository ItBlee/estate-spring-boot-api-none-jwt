package com.itblee.repository.builder.util;

import com.itblee.repository.builder.SqlBuilder;
import com.itblee.repository.builder.SqlMap;
import com.itblee.repository.builder.impl.SqlQuery;
import com.itblee.repository.builder.impl.SqlQueryBuilderImpl;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SqlBuilderFactory {

    private final SqlMap<?> properties;

    public SqlBuilderFactory(SqlMap<?> properties) {
        this.properties = properties;
    }

    public SqlBuilder getInstance(String action) {
        switch (action) {
            case "query":
                return newQueryBuilder();
            default:
                throw new NotImplementedException();
        }
    }

    private SqlBuilder newQueryBuilder() {
        Objects.requireNonNull(properties);
        Map<SqlQuery, Object> queries = new LinkedHashMap<>();
        properties.forEach((key, val) -> {
            Objects.requireNonNull(key.getStatement(), "Invalid key without query pass to Sql Builder map.");
            queries.put(key.getStatement().toQuery(), val);
        });
        return new SqlQueryBuilderImpl(queries);
    }
}
