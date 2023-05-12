package com.itblee.repository.sqlbuilder;

import com.itblee.repository.sqlbuilder.model.Code;
import com.itblee.repository.sqlbuilder.model.Range;
import com.itblee.repository.sqlbuilder.model.SqlJoin;
import com.itblee.repository.sqlbuilder.model.SqlQuery;
import com.itblee.util.StringUtils;
import com.itblee.util.ValidateUtils;

import java.sql.SQLSyntaxErrorException;
import java.util.*;
import java.util.stream.Collectors;

public interface SqlQueryBuilder extends SqlBuilder.Query {

    static String buildQuery(Map<SqlQuery, ?> statements) throws SQLSyntaxErrorException {
        ValidateUtils.requireNonNull(statements);
        StringBuilder sql = new StringBuilder();
        Set<SqlQuery> queries = statements.keySet();
        sql.append(buildSelectClause(queries));
        sql.append(buildFromClause(queries));
        sql.append(buildJoinClause(queries));
        sql.append(buildWhereClause(statements));
        return sql.toString();
    }

    static String buildSubQuery(SqlQuery query, Object value) throws SQLSyntaxErrorException {
        ValidateUtils.requireNonNull(query);
        StringBuilder clause = new StringBuilder();
        Map<SqlQuery, ?> statement = Collections.singletonMap(query, value);
        clause.append("(");
        clause.append(SqlQueryBuilder.buildQuery(statement));
        clause.append(")");
        if (StringUtils.isNotBlank(query.getAlias()))
            clause.append(" AS ").append(query.getAlias());
        return clause.toString();
    }

    static StringBuilder buildSelectClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException {
        ValidateUtils.requireNonNull(queries);
        Set<String> clauses = queries.stream()
                .flatMap(query -> query.getSelectColumn().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (clauses.isEmpty())
            throw new SQLSyntaxErrorException("Wrong query structure.");
        return SqlBuilder.format(clauses, ", ").insert(0," SELECT ");
    }

    static StringBuilder buildFromClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException {
        ValidateUtils.requireNonNull(queries);
        Set<?> tables = queries.stream()
                .flatMap(query -> query.getFromTable().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Set<String> clauses = new LinkedHashSet<>();
        for (Object table : tables) {
            if (table instanceof String)
                clauses.add((String) table);
            else if (table instanceof SqlQuery)
                clauses.add(buildSubQuery((SqlQuery) table, null));
            else throw new SQLSyntaxErrorException();
        }
        if (clauses.isEmpty())
            throw new SQLSyntaxErrorException("Wrong query structure.");
        return SqlBuilder.format(clauses, ", ").insert(0, " FROM ");
    }

    static StringBuilder buildJoinClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException {
        ValidateUtils.requireNonNull(queries);
        Set<SqlJoin> joins = queries.stream()
                .flatMap(query -> query.getJoin().stream())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Set<String> clauses = new LinkedHashSet<>();
        for (SqlJoin join : joins) {
            String table;
            Object joinTable = join.getJoinTable();
            if (joinTable instanceof String)
                table = (String) joinTable;
            else if (joinTable instanceof SqlQuery)
                table = buildSubQuery((SqlQuery) joinTable, null);
            else throw new SQLSyntaxErrorException();
            String clause = join.getJoinType().getKeyword()
                    + " " + table + " ON " + join.getJoinOn();
            clauses.add(clause);
        }
        if (clauses.isEmpty())
            return new StringBuilder();
        return SqlBuilder.format(clauses, " ").insert(0, " ");
    }

    static StringBuilder buildWhereClause(Map<SqlQuery, ?> statements) throws SQLSyntaxErrorException {
        ValidateUtils.requireNonNull(statements);
        Set<String> clauses = new LinkedHashSet<>();
        //convert elements of Map(ConditionKey, value) to query clause after WHERE.
        for (Map.Entry<SqlQuery, ?> entry : statements.entrySet()) {
            final SqlQuery query = entry.getKey();
            final Object val = entry.getValue();
            if (val == null)
                continue;
            StringBuilder clause = new StringBuilder();

            //add column name (example: building.name)
            Object where = query.getWhereColumn();
            if (where instanceof String)
                clause.append(query.getWhereColumn()).append(" ");
            else if (where instanceof SqlQuery)
                clause.append("EXIST ").append(buildSubQuery((SqlQuery) where, null)).append(" ");
            else throw new SQLSyntaxErrorException("Missing Where columns.");

            //add operator and values (example: building.name like "%abc%")
            if (val instanceof Code) {
                clause.append("= '").append(val).append("'");
            } else if (val instanceof CharSequence) {
                clause.append("LIKE '%").append(val).append("%'");
            } else if (val instanceof Number) {
                clause.append("= ").append(val);
            } else if (val.getClass().isArray()) {
                String join = Arrays.stream(((Object[]) val))
                        .filter(o -> o instanceof CharSequence)
                        .map(o -> "'" + o + "'")
                        .collect(Collectors.joining(","));
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
        if (clauses.isEmpty())
            return new StringBuilder();
        return SqlBuilder.format(clauses, " AND ").insert(0, " WHERE 1=1 AND ");
    }
}
