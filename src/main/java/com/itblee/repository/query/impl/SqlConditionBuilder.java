package com.itblee.repository.query.impl;

import com.itblee.repository.query.ConditionKey;
import com.itblee.repository.query.SqlBuilder;
import com.itblee.repository.query.SqlMap;
import com.itblee.repository.query.bean.SqlQuery;

import java.sql.SQLSyntaxErrorException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class SqlConditionBuilder extends AbstractSqlBuilder implements SqlBuilder {

    private final Map<SqlQuery, Object> map;

    private SqlConditionBuilder(Map<SqlQuery, Object> map) {
        this.map = map;
    }

    @Override
    public StringBuilder buildSelectQuery() throws SQLSyntaxErrorException {
        return buildSelectQuery(map.keySet());
    }

    @Override
    public StringBuilder buildFromQuery() throws SQLSyntaxErrorException {
        return buildFromQuery(map.keySet());
    }

    @Override
    public StringBuilder buildJoinQuery() throws SQLSyntaxErrorException {
        return buildJoinQuery(map.keySet());
    }

    @Override
    public StringBuilder buildWhereQuery()  throws SQLSyntaxErrorException {
        return buildWhereQuery(map);
    }

    public static SqlBuilder map(SqlMap<? extends ConditionKey> conditions) {
        if (conditions == null)
            throw new IllegalArgumentException();
        Map<SqlQuery, Object> map = new LinkedHashMap<>();
        conditions.forEach((key, val) -> {
            if (key.queryProps() == null)
                throw new IllegalStateException("Invalid key without query pass to Sql Builder map.");
            map.put(key.queryProps(), val);
        });
        return new SqlConditionBuilder(map);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlConditionBuilder)) return false;
        SqlConditionBuilder that = (SqlConditionBuilder) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

}
