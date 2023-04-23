package com.itblee.repository.query;

import com.itblee.repository.query.bean.SqlQuery;

import java.sql.SQLSyntaxErrorException;
import java.util.Collection;
import java.util.Map;

public interface SqlBuilder {
    StringBuilder buildFinalQuery() throws SQLSyntaxErrorException;

    StringBuilder buildSelectQuery() throws SQLSyntaxErrorException;
    StringBuilder buildSelectQuery(Collection<SqlQuery> queries) throws SQLSyntaxErrorException;

    StringBuilder buildFromQuery() throws SQLSyntaxErrorException;
    StringBuilder buildFromQuery(Collection<SqlQuery> queries) throws SQLSyntaxErrorException;

    StringBuilder buildJoinQuery() throws SQLSyntaxErrorException;
    StringBuilder buildJoinQuery(Collection<SqlQuery> queries) throws SQLSyntaxErrorException;

    StringBuilder buildWhereQuery() throws SQLSyntaxErrorException;
    StringBuilder buildWhereQuery(Map<SqlQuery, Object> map) throws SQLSyntaxErrorException;
}
