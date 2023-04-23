package com.itblee.repository.query.impl;

import com.itblee.repository.query.bean.Code;
import com.itblee.repository.query.bean.Range;
import com.itblee.repository.query.SqlBuilder;
import com.itblee.repository.query.bean.SqlJoin;
import com.itblee.repository.query.bean.SqlQuery;
import com.itblee.utils.StringUtils;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

public abstract class AbstractSqlBuilder implements SqlBuilder {

    @Override
    public StringBuilder buildFinalQuery() throws SQLSyntaxErrorException {
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
        set.forEach(s -> s = s.trim());
        set.removeIf(StringUtils::isBlank);
        return new StringBuilder(String.join(delim, set));
    }

    private String buildQueryTable(SqlQuery queryTable) throws SQLSyntaxErrorException {
        if (queryTable == null)
            throw new IllegalArgumentException();
        StringBuilder clause = new StringBuilder();
        if (!queryTable.getSelectColumn().isEmpty()
            && !queryTable.getFromTable().isEmpty())  {
            List<SqlQuery> one = Collections.singletonList(queryTable);
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
            else throw new SQLSyntaxErrorException("Invalid query.");
        }
        return clause.toString();
    }

    @Override
    public StringBuilder buildSelectQuery(Collection<SqlQuery> queries) throws SQLSyntaxErrorException {
        if (queries == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        for (SqlQuery query : queries)
            set.addAll(query.getSelectColumn());
        if (set.isEmpty())
            throw new SQLSyntaxErrorException("Wrong query structure.");
        return format(set, ", ");
    }


    @Override
    public StringBuilder buildFromQuery(Collection<SqlQuery> queries) throws SQLSyntaxErrorException {
        if (queries == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            if (query.isFromQueryTable())
                for (SqlQuery q : query.getFromQueryTable())
                    set.add(buildQueryTable(q));
            else set.addAll(query.getFromTable());
        }
        if (set.isEmpty())
            throw new SQLSyntaxErrorException("Wrong query structure.");
        return format(set, ", ");
    }

    @Override
    public StringBuilder buildJoinQuery(Collection<SqlQuery> queries) throws SQLSyntaxErrorException {
        if (queries == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            for (SqlJoin join : query.getJoin()) {
                SqlJoin.Type type = join.getJoinType();
                if (type == null || StringUtils.isBlank(type.getKeyword()))
                    throw new SQLSyntaxErrorException("Invalid Join type.");
                StringBuilder clause = new StringBuilder();
                clause.append(type.getKeyword()).append(" ");
                clause.append(buildQueryTable(join.getJoinTable()));
                if (StringUtils.isBlank(join.getJoinON()))
                    throw new SQLSyntaxErrorException("Missing ON clause in Join.");
                clause.append(" ON ").append(join.getJoinON());
                set.add(clause.toString());
            }
        }
        return format(set, " ");
    }

    @Override
    public StringBuilder buildWhereQuery(Map<SqlQuery, Object> map) throws SQLSyntaxErrorException {
        if (map == null)
            throw new IllegalArgumentException();
        Set<String> set = new LinkedHashSet<>();
        set.add("1=1");
        //convert elements of Map(ConditionKey, value) to query clause after WHERE.
        for (Map.Entry<SqlQuery, Object> entry : map.entrySet()) {
            final SqlQuery query = entry.getKey();
            final Object val = entry.getValue();
            if (val == null)
                continue;
            StringBuilder clause = new StringBuilder();

            //add column name (example: building.name)
            if (StringUtils.isBlank(query.getWhereColumn()))
                throw new SQLSyntaxErrorException("Missing Where columns.");
            clause.append(query.getWhereColumn()).append(" ");

            //add operator and values (example: building.name like "%abc%")
            if (val instanceof Code) {
                clause.append("= '").append(val).append("'");
            } else if (val instanceof CharSequence) {
                clause.append("LIKE '%").append(val).append("%'");
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
            } else throw new IllegalStateException("Value type unsupported.");
            set.add(clause.toString());
        }
        return format(set, " AND ");
    }

}
