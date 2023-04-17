package com.itblee.repository.condition;

import com.itblee.repository.condition.key.SqlQuery;

public interface ConditionKey {
    ConditionKey getDefault();
    SqlQuery props();
    String getName();
    String getTableName();
}
