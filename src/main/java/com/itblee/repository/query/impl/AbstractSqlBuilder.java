package com.itblee.repository.query.impl;

import com.itblee.repository.query.ConditionKey;
import com.itblee.repository.query.SqlBuilder;
import com.itblee.repository.query.key.SqlJoin;
import com.itblee.repository.query.key.SqlQuery;
import com.itblee.utils.StringUtils;

import java.util.*;

public abstract class AbstractSqlBuilder implements SqlBuilder {

    protected final Class<? extends ConditionKey> typeFlag;

    protected AbstractSqlBuilder(Class<? extends ConditionKey> type) {
        if (type == null)
            throw new IllegalArgumentException("Type required");
        if (type.getEnumConstants().length == 0)
            throw new IllegalStateException("Invalid key with no enum.");
        this.typeFlag = type;
    }

    public static class Range {
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
        StringBuilder query = new StringBuilder();
        query.append(" SELECT ");
        query.append(buildSelectQuery());
        query.append(" FROM ");
        query.append(buildFromQuery());
        query.append(" ");
        query.append(buildJoinQuery());
        query.append(" WHERE ");
        query.append(buildWhereQuery());
        System.out.println("\n" + query);
        return query;
    }

    private StringBuilder format(Set<String> set, String delim) {
        if (set.isEmpty())
            throw new IllegalStateException("Wrong query structure.");
        set.forEach(s -> s = s.trim());
        set.removeIf(StringUtils::isBlank);
        return new StringBuilder(String.join(delim, set));
    }

    private Optional<String> buildQueryTable(SqlQuery queryTable) {
        StringBuilder clause = new StringBuilder();
        if (queryTable == null || queryTable.getFromTable().isEmpty())
            return Optional.empty();
        List<SqlQuery> one = Collections.singletonList(queryTable);
        if (!queryTable.getSelectColumn().isEmpty())  {
            clause.append("(SELECT ");
            clause.append(buildSelectQuery(one));
            clause.append(" FROM ");
            clause.append(buildFromQuery(one));
            clause.append(" ");
            clause.append(buildJoinQuery(one));
            if (!StringUtils.isBlank(queryTable.getWhereColumn()))
                clause.append(" WHERE ").append(queryTable.getWhereColumn());
            clause.append(")");
            if (!StringUtils.isBlank(queryTable.getAlias()))
                clause.append(" AS ").append(queryTable.getAlias());
        } else {
            Optional<String> from = queryTable.getFromTable().stream().findFirst();
            if (from.isPresent())
                clause.append(from.get());
            else return Optional.empty();
        }
        return Optional.of(clause.toString());
    }

    @Override
    public StringBuilder buildSelectQuery(Collection<SqlQuery> queries) {
        if (queries == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        for (SqlQuery query : queries)
            set.addAll(query.getSelectColumn());
        return format(set, ", ");
    }


    @Override
    public StringBuilder buildFromQuery(Collection<SqlQuery> queries) {
        if (queries == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            if (query.isFromQueryTable())
                for (SqlQuery q : query.getFromQueryTable())
                    buildQueryTable(q).ifPresent(set::add);
            else set.addAll(query.getFromTable());
        }
        return format(set, ", ");
    }

    @Override
    public StringBuilder buildJoinQuery(Collection<SqlQuery> queries) {
        if (queries == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            for (SqlJoin join : query.getJoin()) {
                SqlJoin.Type type = join.getJoinType();
                if (type == null || StringUtils.isBlank(type.getKeyword()))
                    continue;
                StringBuilder clause = new StringBuilder();
                clause.append(type.getKeyword()).append(" ");

                Optional<String> joinTable = buildQueryTable(join.getJoinTable());
                if (joinTable.isPresent())
                    clause.append(joinTable.get());
                else continue;

                if (!StringUtils.isBlank(join.getJoinON()))
                    clause.append(" ON ").append(join.getJoinON());
                else continue;
                set.add(clause.toString());
            }
        }
        return format(set, " ");
    }

    @Override
    public StringBuilder buildWhereQuery(Map<SqlQuery, Object> map) {
        if (map == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        set.add("1=1");
        //convert elements of Map(ConditionKey, value) to query clause after WHERE.
        for (Map.Entry<SqlQuery, Object> entry : map.entrySet()) {
            if (entry.getValue() == null)
                continue;
            StringBuilder clause = new StringBuilder();

            //add column name (example: building.name)
            SqlQuery query = entry.getKey();
            String whereCol = query.getWhereColumn();
            if (!StringUtils.isBlank(whereCol))
                clause.append(whereCol).append(" ");
            else continue;

            //add operator and values (example: building.name like "%abc%")
            final Object val = entry.getValue();
            if (val == null)
                continue;
            if (val instanceof CharSequence) {
                clause.append("LIKE CONCAT('%','").append(val).append("','%')");
            } else if (val instanceof Number) {
                clause.append("= ").append(val);
            } else if (val.getClass().isArray()) {
                List<String> list = new LinkedList<>();
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
            set.add(clause.toString());
        }
        return format(set, " AND ");
    }

}
