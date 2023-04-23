package com.itblee.repository.query;

import com.itblee.repository.query.bean.SqlQuery;

public interface ConditionKey {
    SqlQuery queryProps();
    String getParamName();
    Class<?> getType();
    boolean isRange();
}
