package com.itblee.repository.query.key;

import com.itblee.repository.query.ConditionKey;

public class ConditionKeyHolder implements ConditionKey {

    private final SqlQuery query;

    ConditionKeyHolder(SqlQuery query) {
        if (query == null)
            throw new IllegalArgumentException();
        this.query = query;
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
}
