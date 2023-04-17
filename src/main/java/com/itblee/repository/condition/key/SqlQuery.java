package com.itblee.repository.condition.key;

import java.util.*;

public class SqlQuery {

    private Class<?> type;
    private Set<String> selectColumn;
    private Set<SqlJoin> join;
    private String whereColumn;

    public SqlQuery() {
    }

    public Class<?> getType() {
        if (this.type == null)
            this.type = Object.class;
        return type;
    }

    public void type(Class<?> type) {
        if (this.type == null)
            this.type = type;
        else throw new IllegalStateException("Already set !");
    }

    public Set<String> getSelectColumn() {
        if (selectColumn == null)
            selectColumn = Collections.emptySet();
        return selectColumn;
    }

    public void select(String... selectColumn) {
        if (this.selectColumn == null)
            this.selectColumn = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(selectColumn))
            );
        else throw new IllegalStateException("Already set !");
    }

    public Set<SqlJoin> getJoin() {
        if (join == null)
            join = Collections.emptySet();
        return join;
    }

    public void joinWith(SqlJoin... join) {
        if (this.join == null)
            this.join = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(join))
            );
        else throw new IllegalStateException("Already set !");
    }

    public String getWhereColumn() {
        if (this.whereColumn == null)
            this.whereColumn = "";
        return whereColumn;
    }

    public void where(String whereColumn) {
        if (this.whereColumn == null)
            this.whereColumn = whereColumn;
        else throw new IllegalStateException("Already set !");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQuery)) return false;
        SqlQuery sqlKey = (SqlQuery) o;
        return Objects.equals(getWhereColumn(), sqlKey.getWhereColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWhereColumn());
    }
}
