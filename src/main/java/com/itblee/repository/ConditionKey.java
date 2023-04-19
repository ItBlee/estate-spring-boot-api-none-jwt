package com.itblee.repository;

import com.itblee.repository.key.SqlQuery;

public interface ConditionKey {
    ConditionKey getDefault();
    SqlQuery props();
    String getParam();
    boolean isRange();
    String getName();
    String getTableName();
}
