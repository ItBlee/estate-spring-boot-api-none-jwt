package com.itblee.repository.conditions;

public interface SqlConditions {
    StringBuilder generateSQL();
    StringBuilder generateSQL(StringBuilder builder);
}
