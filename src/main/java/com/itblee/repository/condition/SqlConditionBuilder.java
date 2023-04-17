package com.itblee.repository.condition;

import java.util.Collection;
import java.util.Map;

public interface SqlConditionBuilder {
    StringBuilder buildFinalQuery(Class<? extends ConditionKey> kClass);

    StringBuilder buildSelectQuery();
    StringBuilder buildSelectQuery(Collection<ConditionKey> keys);

    StringBuilder buildJoinQuery();
    StringBuilder buildJoinQuery(Collection<ConditionKey> keys);

    StringBuilder buildWhereQuery();
    StringBuilder buildWhereQuery(Map<ConditionKey, Object> map);
}
