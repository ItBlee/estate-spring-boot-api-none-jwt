package com.itblee.sqlbuilder;

import com.itblee.util.StringUtils;

import java.sql.SQLSyntaxErrorException;
import java.util.Set;
import java.util.stream.Collectors;

public interface SqlBuilder {
    String build() throws SQLSyntaxErrorException;

    interface Query extends SqlBuilder {
        String buildQuery() throws SQLSyntaxErrorException;
        StringBuilder buildSelectClause() throws SQLSyntaxErrorException;
        StringBuilder buildFromClause() throws SQLSyntaxErrorException;
        StringBuilder buildJoinClause() throws SQLSyntaxErrorException;
        StringBuilder buildWhereClause() throws SQLSyntaxErrorException;
    }

    interface Insert extends SqlBuilder {}
    interface Update extends SqlBuilder {}
    interface Delete extends SqlBuilder {}

    static StringBuilder format(Set<String> clauses, String delim) {
        String format = clauses.stream()
                .map(String::trim)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(delim));
        return new StringBuilder(format);
    }
}
