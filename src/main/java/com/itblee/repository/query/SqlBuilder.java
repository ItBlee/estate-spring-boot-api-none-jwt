package com.itblee.repository.query;

import java.util.Collection;
import java.util.Map;

public interface SqlBuilder {
    StringBuilder buildFinalQuery();

    StringBuilder buildSelectQuery();
    StringBuilder buildSelectQuery(Collection<? extends ConditionKey> keys);

    StringBuilder buildJoinQuery();
    StringBuilder buildJoinQuery(Collection<? extends ConditionKey> keys);

    StringBuilder buildWhereQuery();
    StringBuilder buildWhereQuery(Map<? extends ConditionKey, Object> map);
}
