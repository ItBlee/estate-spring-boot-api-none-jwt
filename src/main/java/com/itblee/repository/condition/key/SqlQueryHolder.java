package com.itblee.repository.condition.key;

import com.itblee.repository.condition.ConditionKey;

public class SqlQueryHolder implements ConditionKey {

    private final SqlQuery query;

    public SqlQueryHolder(SqlQuery query) {
        this.query = query;
    }

    @Override
    public ConditionKey getDefault() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlQuery props() {
        return query;
    }

    @Override
    public String getName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getTableName() {
        throw new UnsupportedOperationException();
    }
}
