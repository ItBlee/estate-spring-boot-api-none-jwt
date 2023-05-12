package com.itblee.repository.sqlbuilder.model;

import com.itblee.repository.sqlbuilder.SqlStatement;

import java.io.Serializable;
import java.util.*;

public class SqlQuery implements Serializable, SqlStatement {
	
	private static final long serialVersionUID = -4006437901874345613L;
	
	private final Set<?> selectColumn;
    private final Set<?> fromTable;
    private final Set<SqlJoin> join;
    private final Object whereColumn;
    private final String alias;

    public static final class Builder {
        private Set<Object> selectColumn = new LinkedHashSet<>();
        private Set<Object> fromTable = new LinkedHashSet<>();
        private Set<SqlJoin> join = new LinkedHashSet<>();
        private Object whereColumn = null;
        private String alias = null;

        public Builder select(Object... selectColumn) {
            for (Object o : selectColumn) {
                if (o instanceof String || o instanceof SqlQuery)
                    this.selectColumn.add(o);
                else throw new IllegalArgumentException();
            }
            return this;
        }

        public Builder from(Object... fromTable) {
            for (Object o : fromTable) {
                if (o instanceof String || o instanceof SqlQuery)
                    this.fromTable.add(o);
                else throw new IllegalArgumentException();
            }
            return this;
        }

        public Builder joinWith(SqlJoin... join) {
            this.join.addAll(Arrays.asList(join));
            return this;
        }

        public Builder where(Object whereColumn) {
            if (whereColumn instanceof String || whereColumn instanceof SqlQuery)
                this.whereColumn = whereColumn;
            else throw new IllegalArgumentException();
            return this;
        }

        public Builder as(String alias) {
            this.alias = alias;
            return this;
        }

        public SqlQuery build() {
            if (fromTable.isEmpty())
                throw new IllegalStateException();
            this.selectColumn = Collections.unmodifiableSet(this.selectColumn);
            this.fromTable = Collections.unmodifiableSet(this.fromTable);
            this.join = Collections.unmodifiableSet(this.join);
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

    public Set<?> getSelectColumn() {
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
    public Set<?> getIdentifiers() {
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
