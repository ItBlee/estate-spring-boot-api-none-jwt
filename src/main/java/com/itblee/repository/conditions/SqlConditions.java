package com.itblee.repository.conditions;

public interface SqlConditions {
    StringBuilder generateWhereClauseSQL();
    StringBuilder generateWhereClauseSQL(StringBuilder builder);
}
