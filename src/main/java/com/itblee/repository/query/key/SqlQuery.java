package com.itblee.repository.query.key;

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

    public SqlQuery typeOf(Class<?> type) {
        if (this.type == null)
            this.type = type;
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public Set<String> getSelectColumn() {
        if (selectColumn == null)
            selectColumn = Collections.emptySet();
        return selectColumn;
    }

    public SqlQuery select(String... selectColumn) {
        if (this.selectColumn == null)
            this.selectColumn = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(selectColumn))
            );
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public Set<SqlJoin> getJoin() {
        if (join == null)
            join = Collections.emptySet();
        return join;
    }

    public SqlQuery joinWith(SqlJoin... join) {
        if (this.join == null)
            this.join = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(join))
            );
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public String getWhereColumn() {
        if (this.whereColumn == null)
            this.whereColumn = "";
        return whereColumn;
    }

    public SqlQuery where(String whereColumn) {
        if (this.whereColumn == null)
            this.whereColumn = whereColumn;
        else throw new IllegalStateException("Already set !");
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQuery)) return false;
        SqlQuery sqlQuery = (SqlQuery) o;
        return Objects.equals(getType(), sqlQuery.getType())
                && Objects.equals(getSelectColumn(), sqlQuery.getSelectColumn())
                && Objects.equals(getJoin(), sqlQuery.getJoin())
                && Objects.equals(getWhereColumn(), sqlQuery.getWhereColumn());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), getSelectColumn(), getJoin(), getWhereColumn());
    }
}
