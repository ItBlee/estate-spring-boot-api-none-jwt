package com.itblee.repository.builder.impl;

import com.itblee.repository.builder.SqlBuilder;
import com.itblee.repository.builder.util.Code;
import com.itblee.repository.builder.util.Range;
import com.itblee.utils.StringUtils;

import java.sql.SQLSyntaxErrorException;
import java.util.*;

public abstract class AbstractSqlQueryBuilder implements SqlBuilder.Query {

    @Override
    public String buildFinalQuery() throws SQLSyntaxErrorException {
        StringBuilder query = new StringBuilder();
        query.append(" SELECT ");
        query.append(buildSelectClause());
        query.append(" FROM ");
        query.append(buildFromClause());
        query.append(" ");
        query.append(buildJoinClause());
        query.append(" WHERE ");
        query.append(buildWhereClause());
        System.out.println("\n" + query);
        return query.toString();
    }

    private StringBuilder format(Set<String> clauses, String delim) {
        clauses.forEach(clause -> clause = clause.trim());
        clauses.removeIf(StringUtils::isBlank);
        return new StringBuilder(String.join(delim, clauses));
    }

    private String buildInnerTable(SqlQuery query) throws SQLSyntaxErrorException {
        Objects.requireNonNull(query);
        StringBuilder clause = new StringBuilder();
        Set<SqlQuery> oneQuery = Collections.singleton(query);
        clause.append("(SELECT ");
        clause.append(buildSelectClause(oneQuery));
        clause.append(" FROM ");
        clause.append(buildFromClause(oneQuery));
        clause.append(" ");
        clause.append(buildJoinClause(oneQuery));
        if (StringUtils.isNotBlank(query.getWhereColumn()))
            clause.append(" WHERE ").append(query.getWhereColumn());
        clause.append(")");
        if (StringUtils.isNotBlank(query.getAlias()))
            clause.append(" AS ").append(query.getAlias());
        return clause.toString();
    }

    @Override
    public StringBuilder buildSelectClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException {
        Objects.requireNonNull(queries);
        Set<String> clauses = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            clauses.addAll(query.getSelectColumn());
        }
        if (clauses.isEmpty())
            throw new SQLSyntaxErrorException("Wrong query structure.");
        return format(clauses, ", ");
    }

    @Override
    public StringBuilder buildFromClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException {
        Objects.requireNonNull(queries);
        Set<String> clauses = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            if (query.isInnerTable()) {
                for (SqlQuery inner : query.getFromInnerTable()) {
                    clauses.add(buildInnerTable(inner));
                }
            } else clauses.addAll(query.getFromTable());
        }
        if (clauses.isEmpty())
            throw new SQLSyntaxErrorException("Wrong query structure.");
        return format(clauses, ", ");
    }

    @Override
    public StringBuilder buildJoinClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException {
        Objects.requireNonNull(queries);
        Set<String> clauses = new LinkedHashSet<>();
        Set<SqlJoin> joins = new LinkedHashSet<>();
        for (SqlQuery query : queries) {
            joins.addAll(query.getJoin());
        }
        for (SqlJoin join : joins) {
            String joinTable;
            if (join.isInnerTable())
                joinTable = buildInnerTable(join.getJoinInnerTable());
            else joinTable = join.getJoinTable();
            String clause = join.getJoinType().getKeyword() + " " + joinTable +
                    " ON " + join.getJoinOn();
            clauses.add(clause);
        }
        return format(clauses, " ");
    }

    @Override
    public StringBuilder buildWhereClause(Map<SqlQuery, ?> map) throws SQLSyntaxErrorException {
        Objects.requireNonNull(map);
        Set<String> clauses = new LinkedHashSet<>();
        clauses.add("1=1");
        //convert elements of Map(ConditionKey, value) to query clause after WHERE.
        for (Map.Entry<SqlQuery, ?> entry : map.entrySet()) {
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
            clauses.add(clause.toString());
        }
        return format(clauses, " AND ");
    }

}
