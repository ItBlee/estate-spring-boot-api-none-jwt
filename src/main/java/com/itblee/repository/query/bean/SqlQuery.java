package com.itblee.repository.query.bean;

import java.util.*;

public class SqlQuery {


    private Set<String> selectColumn;
    private Set<String> fromTable;
    private Set<SqlQuery> fromQueryTable;
    private Set<SqlJoin> join;
    private String whereColumn;
    private String alias;

    public SqlQuery() {
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

    public Set<String> getFromTable() {
        if (isFromQueryTable())
            throw new UnsupportedOperationException();
        if (fromTable == null)
            fromTable = Collections.emptySet();
        return fromTable;
    }

    public Set<SqlQuery> getFromQueryTable() {
        if (!isFromQueryTable())
            throw new UnsupportedOperationException();
        if (fromQueryTable == null)
            fromQueryTable = Collections.emptySet();
        return fromQueryTable;
    }

    public SqlQuery from(String... fromTable) {
        if (this.fromTable == null && this.fromQueryTable == null)
            this.fromTable = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(fromTable))
            );
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public SqlQuery from(SqlQuery... fromQueryTable) {
        if (this.fromQueryTable == null && this.fromTable == null)
            this.fromQueryTable = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(fromQueryTable))
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

    public String getAlias() {
        if (this.alias == null)
            this.alias = "";
        return alias;
    }

    public SqlQuery as(String alias) {
        if (this.alias == null)
            this.alias = alias;
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public boolean isFromQueryTable() {
        return fromQueryTable != null && fromTable == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQuery)) return false;
        SqlQuery query = (SqlQuery) o;
        return getSelectColumn().equals(query.getSelectColumn())
                && Objects.equals(getFromTable(), query.getFromTable())
                && Objects.equals(isFromQueryTable(), query.isFromQueryTable())
                && Objects.equals(getJoin(), query.getJoin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSelectColumn(), getFromTable(), isFromQueryTable(), getJoin());
    }
}
