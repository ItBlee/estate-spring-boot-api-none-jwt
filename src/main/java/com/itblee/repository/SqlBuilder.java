package com.itblee.repository;

import java.util.Collection;
import java.util.Map;

public interface SqlBuilder<T extends ConditionKey> {
    StringBuilder buildFinalQuery();

    StringBuilder buildSelectQuery();
    StringBuilder buildSelectQuery(Collection<T> keys);

    StringBuilder buildJoinQuery();
    StringBuilder buildJoinQuery(Collection<T> keys);

    StringBuilder buildWhereQuery();
    StringBuilder buildWhereQuery(Map<T, Object> map);
}
