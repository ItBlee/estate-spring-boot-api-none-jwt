package com.itblee.repository.builder;

import com.itblee.repository.builder.impl.SqlQuery;

import java.util.Set;

public interface SqlStatement {
    Set<String> getIdentifiers();
    String getCondition();
    SqlQuery toQuery();
}
