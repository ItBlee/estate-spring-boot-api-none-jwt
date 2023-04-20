package com.itblee.repository.query.impl;

import com.itblee.repository.query.ConditionKey;
import com.itblee.repository.query.SqlBuilder;
import com.itblee.repository.query.SqlMap;

import java.util.Objects;

public class SqlConditionBuilder extends AbstractSqlBuilder implements SqlBuilder {

    private final SqlMap<? extends ConditionKey> map;

    private SqlConditionBuilder(SqlMap<? extends ConditionKey> conditions) {
        super(conditions.getType());
        this.map = conditions;
    }

    @Override
    public StringBuilder buildSelectQuery() {
        return buildSelectQuery(map.keySet());
    }

    @Override
    public StringBuilder buildJoinQuery() {
        return buildJoinQuery(map.keySet());
    }

    @Override
    public StringBuilder buildWhereQuery() {
        return buildWhereQuery(map);
    }

    public static SqlBuilder build(SqlMap<? extends ConditionKey> conditions) {
        if (conditions == null)
            throw new IllegalArgumentException();
        return new SqlConditionBuilder(conditions);
    }

    @Override
    public String toString() {
        return buildFinalQuery().toString();
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
