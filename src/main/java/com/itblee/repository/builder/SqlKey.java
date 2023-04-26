package com.itblee.repository.builder;

public interface SqlKey {
    SqlStatement getStatement();
    String getParamName();
    Class<?> getType();
    boolean isScope();
    boolean isMarker();
    boolean isRange();
}
