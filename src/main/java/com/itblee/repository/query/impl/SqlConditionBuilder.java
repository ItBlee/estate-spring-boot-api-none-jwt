package com.itblee.repository.query.impl;

import com.itblee.repository.query.ConditionKey;
import com.itblee.repository.query.SqlBuilder;
import com.itblee.repository.query.SqlMap;
import com.itblee.repository.query.key.SqlQuery;

import java.util.*;

public class SqlConditionBuilder extends AbstractSqlBuilder implements SqlBuilder {

    private final Map<SqlQuery, Object> map = new LinkedHashMap<>();

    private SqlConditionBuilder(Class<? extends ConditionKey> type) {
        super(type);
    }

    @Override
    public StringBuilder buildSelectQuery() {
        return buildSelectQuery(map.keySet());
    }

    @Override
    public StringBuilder buildFromQuery() {
        return buildFromQuery(map.keySet());
    }

    @Override
    public StringBuilder buildJoinQuery() {
        return buildJoinQuery(map.keySet());
    }

    @Override
    public StringBuilder buildWhereQuery() {
        return buildWhereQuery(map);
    }

    public Map<SqlQuery, Object> getMap() {
        return map;
    }

    public static SqlBuilder build(SqlMap<? extends ConditionKey> conditions) {
        if (conditions == null)
            throw new IllegalArgumentException();
        SqlConditionBuilder builder = new SqlConditionBuilder(conditions.getType());
        conditions.forEach((key, val) -> builder.getMap().put(key.props(), val));
        return builder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlConditionBuilder)) return false;
        SqlConditionBuilder that = (SqlConditionBuilder) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }
}
