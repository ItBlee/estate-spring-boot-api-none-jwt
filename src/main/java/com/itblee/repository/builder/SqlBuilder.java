package com.itblee.repository.builder;

import com.itblee.repository.builder.impl.SqlQuery;

import java.sql.SQLSyntaxErrorException;
import java.util.Map;
import java.util.Set;

public interface SqlBuilder {
    String buildFinalQuery() throws SQLSyntaxErrorException;

     interface Query extends SqlBuilder {
        StringBuilder buildSelectClause() throws SQLSyntaxErrorException;
        StringBuilder buildSelectClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException;

        StringBuilder buildFromClause() throws SQLSyntaxErrorException;
        StringBuilder buildFromClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException;

        StringBuilder buildJoinClause() throws SQLSyntaxErrorException;
        StringBuilder buildJoinClause(Set<SqlQuery> queries) throws SQLSyntaxErrorException;

        StringBuilder buildWhereClause() throws SQLSyntaxErrorException;
        StringBuilder buildWhereClause(Map<SqlQuery, ?> map) throws SQLSyntaxErrorException;
    }

}
