package com.itblee.repository.query;

import com.itblee.repository.query.key.SqlQuery;

public interface ConditionKey {
    ConditionKey getDefault();
    SqlQuery props();
    String getParam();
    boolean isRange();
    String getName();
    String getTableName();
}
