package com.itblee.repository.sqlbuilder.model;

import com.itblee.repository.sqlbuilder.SqlStatement;

import java.io.Serializable;
import java.util.*;

public class SqlQuery implements Serializable, SqlStatement {
	
	private static final long serialVersionUID = -4006437901874345613L;
	
	private final Set<String> selectColumn;
    private final Set<?> fromTable;
    private final Set<SqlJoin> join;
    private final Object whereColumn;
    private final String alias;

    public static final class Builder {
        private Set<String> selectColumn = Collections.emptySet();
        private Set<?> fromTable = Collections.emptySet();
        private Set<SqlJoin> join = Collections.emptySet();
        private Object whereColumn = null;
        private String alias = null;

        public Builder select(String... selectColumn) {
            this.selectColumn = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(selectColumn))
            );
            return this;
        }

        public Builder from(String... fromTable) {
            this.fromTable = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(fromTable))
            );
            return this;
        }
        public Builder from(SqlQuery... fromTable) {
            this.fromTable = Collections.unmodifiableSet(
                    new LinkedHashSet<>(Arrays.asList(fromTable))
            );
            return this;
        }

        public Builder from(Object... fromTable) {
            Set<Object> set = new LinkedHashSet<>();
            for (Object o : fromTable) {
                if (o instanceof String || o instanceof SqlQuery)
                    set.add(o);
                else throw new IllegalArgumentException();
            }
            this.fromTable = Collections.unmodifiableSet(set);
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

        public Builder where(SqlQuery whereColumn) {
            this.whereColumn = whereColumn;
            return this;
        }

        public Builder as(String alias) {
            this.alias = alias;
            return this;
        }

        public SqlQuery build() {
            if (fromTable.isEmpty())
                throw new IllegalStateException();
            return new SqlQuery(this);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    private SqlQuery(final Builder builder) {
        this.selectColumn = builder.selectColumn;
        this.fromTable = builder.fromTable;
        this.join = builder.join;
        this.whereColumn = builder.whereColumn;
        this.alias = builder.alias;
    }

    public Set<String> getSelectColumn() {
        return selectColumn;
    }

    public Set<?> getFromTable() {
        return fromTable;
    }

    public Set<SqlJoin> getJoin() {
        return join;
    }

    public Object getWhereColumn() {
        return whereColumn;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public Set<String> getIdentifiers() {
        return getSelectColumn();
    }

    @Override
    public Set<?> getTables() {
        return getFromTable();
    }

    @Override
    public Object getCondition() {
        return getWhereColumn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlQuery)) return false;
        SqlQuery sqlQuery = (SqlQuery) o;
        return Objects.equals(getSelectColumn(), sqlQuery.getSelectColumn())
                && getFromTable().equals(sqlQuery.getFromTable())
                && Objects.equals(getJoin(), sqlQuery.getJoin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSelectColumn(), getFromTable(), getJoin());
    }
}
