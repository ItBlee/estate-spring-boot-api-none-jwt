package com.itblee.repository.key;

import com.itblee.repository.ConditionKey;

public class ConditionKeyHolder implements ConditionKey {

    private final SqlQuery query;

    ConditionKeyHolder(SqlQuery query) {
        if (query == null)
            throw new IllegalArgumentException();
        this.query = query;
    }

    @Override
    public ConditionKey getDefault() {
        return this;
    }

    @Override
    public SqlQuery props() {
        return query;
    }

    @Override
    public String getParam() {
        return null;
    }

    @Override
    public boolean isRange() {
        return false;
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
