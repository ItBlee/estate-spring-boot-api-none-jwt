package com.itblee.repository.query;

import com.itblee.repository.query.key.SqlQuery;

public interface ConditionKey {
    SqlQuery props();
    String getParam();
    Class<?> getType();
    boolean isRange();
    String getName();
}
