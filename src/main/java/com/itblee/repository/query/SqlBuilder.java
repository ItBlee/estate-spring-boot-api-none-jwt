package com.itblee.repository.query;

import com.itblee.repository.query.key.SqlQuery;

import java.util.Collection;
import java.util.Map;

public interface SqlBuilder {
    StringBuilder buildFinalQuery();

    StringBuilder buildSelectQuery();
    StringBuilder buildSelectQuery(Collection<SqlQuery> queries);

    StringBuilder buildFromQuery();
    StringBuilder buildFromQuery(Collection<SqlQuery> queries);

    StringBuilder buildJoinQuery();
    StringBuilder buildJoinQuery(Collection<SqlQuery> queries);

    StringBuilder buildWhereQuery();
    StringBuilder buildWhereQuery(Map<SqlQuery, Object> map);
}
