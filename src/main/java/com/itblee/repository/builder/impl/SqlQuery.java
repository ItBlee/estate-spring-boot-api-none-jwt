package com.itblee.repository.builder.impl;

import com.itblee.repository.builder.SqlStatement;

import java.io.Serializable;
import java.util.*;

public final class SqlQuery implements Serializable, SqlStatement {
	
	private static final long serialVersionUID = -4006437901874345613L;
	
	private final Set<String> selectColumn;
    private final Set<String> fromTable;
    private final Set<SqlQuery> fromInnerTable;
    private final Set<SqlJoin> join;
    private final String whereColumn;
    private final String alias;

    public static final class Builder {
        private Set<String> selectColumn = Collections.emptySet();
        private Set<String> from = null;
        private Set<SqlQuery> fromInnerTable = null;
        private Set<SqlJoin> join = Collections.emptySet();
        private String whereColumn = null;
        private String alias = null;

        public Builder select(String... selectColumn) {
            this.selectColumn = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(selectColumn))
            );
            return this;
        }

        public Builder from(String... fromTable) {
            this.from = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(fromTable))
            );
            this.fromInnerTable = null;
            return this;
        }

        public Builder from(SqlQuery... fromInnerTable) {
            this.fromInnerTable = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(fromInnerTable))
            );
            this.from = null;
            return this;
        }

        public Builder joinWith(SqlJoin... join) {
            this.join = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(join))
            );
            return this;
        }

        public Builder where(String whereColumn) {
            this.whereColumn = whereColumn;
            return this;
        }

        public Builder as(String alias) {
            this.alias = alias;
            return this;
        }

        public SqlQuery build() {
            if ((from.isEmpty() && fromInnerTable.isEmpty()))
                throw new IllegalArgumentException();
            return new SqlQuery(this);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public SqlQuery(Builder builder) {
        this.selectColumn = builder.selectColumn;
        this.fromTable = builder.from;
        this.fromInnerTable = builder.fromInnerTable;
        this.join = builder.join;
        this.whereColumn = builder.whereColumn;
        this.alias = builder.alias;
    }

    public Set<String> getSelectColumn() {
        return selectColumn;
    }

    public Set<String> getFromTable() {
        if (isInnerTable())
            throw new UnsupportedOperationException();
        return fromTable;
    }

    public Set<SqlQuery> getFromInnerTable() {
        if (!isInnerTable())
            throw new UnsupportedOperationException();
        return fromInnerTable;
    }

    public Set<SqlJoin> getJoin() {
        return join;
    }

    public String getWhereColumn() {
        return whereColumn;
    }

    public String getAlias() {
        return alias;
    }

    public boolean isInnerTable() {
        return fromInnerTable != null && fromTable == null;
    }

    @Override
    public Set<String> getIdentifiers() {
        return selectColumn;
    }

    @Override
    public String getCondition() {
        return whereColumn;
    }

    @Override
    public SqlQuery toQuery() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQuery)) return false;
        SqlQuery that = (SqlQuery) o;
        if (!Objects.equals(isInnerTable(), that.isInnerTable()))
            return false;
        if (isInnerTable())
            return getSelectColumn().equals(that.getSelectColumn())
                    && Objects.equals(getFromInnerTable(), that.getFromInnerTable())
                    && Objects.equals(getJoin(), that.getJoin());
        else return getSelectColumn().equals(that.getSelectColumn())
                && Objects.equals(getFromTable(), that.getFromTable())
                && Objects.equals(getJoin(), that.getJoin());
    }

    @Override
    public int hashCode() {
        if (isInnerTable())
            return Objects.hash(getSelectColumn(), getFromInnerTable(), true, getJoin());
        else return Objects.hash(getSelectColumn(), getFromTable(), false, getJoin());
    }
}
