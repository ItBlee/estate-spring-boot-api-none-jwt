package com.itblee.repository.impl;

import com.itblee.repository.ConditionKey;
import com.itblee.repository.SqlBuilder;
import com.itblee.repository.key.SqlJoin;
import com.itblee.utils.StringUtils;

import java.util.*;

public abstract class AbstractSqlBuilder<T extends ConditionKey> implements SqlBuilder<T> {

    protected final Class<T> typeFlag;

    protected AbstractSqlBuilder(Class<T> type) {
        if (type == null)
            throw new IllegalArgumentException("Type required");
        if (type.getEnumConstants().length == 0)
            throw new IllegalStateException("Invalid key with no enum.");
        this.typeFlag = type;
    }

    protected final static class Range {
        public Number from;
        public Number to;

        private Range(Number from, Number to) {
            this.from = from;
            this.to = to;
        }

        public static Range newRange(Number from, Number to) {
            if ((from == null && to == null))
                throw new IllegalStateException();
            if (from != null && to != null && from.doubleValue() > to.doubleValue())
                throw new IllegalArgumentException("FROM value is greater than TO value");
            return new Range(from, to);
        }
    }

    @Override
    public StringBuilder buildFinalQuery() {
        ConditionKey[] keys = typeFlag.getEnumConstants();
        if (keys == null || keys.length == 0)
            throw new IllegalStateException("Illegal state Key.");
        ConditionKey tar = keys[0].getDefault();

        StringBuilder query = new StringBuilder();
        query.append(" SELECT ");
        query.append(buildSelectQuery());
        query.append(" FROM ").append(tar.getTableName()).append(" ");
        query.append(buildJoinQuery());
        query.append(" WHERE 1 = 1 ");
        query.append(buildWhereQuery());
        System.out.println("\n" + query);
        return query;
    }

    @Override
    public StringBuilder buildSelectQuery(Collection<T> keys) {
        if (keys == null)
            throw new IllegalArgumentException();
        Set<String> cols = new LinkedHashSet<>();
        for (ConditionKey key : keys)
            cols.addAll(key.props().getSelectColumn());
        cols.forEach(col -> col = col.trim());
        cols.removeIf(StringUtils::isBlank);
        return new StringBuilder(String.join(", ", cols));
    }

    @Override
    public StringBuilder buildJoinQuery(Collection<T> keys) {
        if (keys == null)
            throw new IllegalArgumentException();
        Set<String> joins = new LinkedHashSet<>();
        for (ConditionKey key : keys) {
            for (SqlJoin join : key.props().getJoin()) {
                if (!StringUtils.isBlank(join.getJoinTable()) && join.getJoinType() != null) {
                    String sql = join.getJoinType().getKeyword() + " " + join.getJoinTable();
                    if (!StringUtils.isBlank(join.getJoinON()))
                        sql += " ON " + join.getJoinON();
                    joins.add(sql);
                }
            }
        }
        joins.forEach(join -> join = join.trim());
        return new StringBuilder(String.join(" ", joins));
    }

    @Override
    public StringBuilder buildWhereQuery(Map<T, Object> map) {
        if (map == null)
            throw new IllegalArgumentException();
        StringBuilder sql = new StringBuilder();
        //convert elements of Map(ConditionKey, value) to query clause after WHERE.
        for (Map.Entry<? extends ConditionKey, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            StringBuilder clause = new StringBuilder();
            clause.append("AND ");

            //add column name (example: building.name)
            ConditionKey key = entry.getKey();
            String whereCol = key.props().getWhereColumn();
            if (StringUtils.isBlank(whereCol))
                continue;
            clause.append(whereCol).append(" ");

            //add operator and values (example: building.name like "%abc%")
            final Object val = entry.getValue();
            if (val == null)
                continue;
            if (val instanceof CharSequence) {
                clause.append("LIKE CONCAT('%','").append(val).append("','%')");
            } else if (val instanceof Number) {
                clause.append("= ").append(val);
            } else if (val.getClass().isArray()) {
                List<String> list = new ArrayList<>();
                for (Object o : (Object[]) val) {
                    String s = o.toString();
                    if (o instanceof CharSequence)
                        s = "'" + s + "'";
                    list.add(s);
                }
                String join = String.join(",", list);
                clause.append("IN (").append(join).append(")");
            } else if (val instanceof Range) {
                Range range = (Range) val;
                if (range.from == null)
                    clause.append("<= ").append(range.to);
                else if (range.to == null)
                    clause.append(">= ").append(range.from);
                else clause.append("BETWEEN ").append(range.from)
                            .append(" AND ").append(range.to);
            } else continue;
            sql.append(clause);
        }
        return sql;
    }

}
