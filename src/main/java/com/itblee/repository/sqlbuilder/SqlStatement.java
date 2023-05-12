package com.itblee.repository.sqlbuilder;

import java.util.Set;

public interface SqlStatement {
    Set<String> getIdentifiers();
    Set<?> getTables();
    Object getCondition();
}