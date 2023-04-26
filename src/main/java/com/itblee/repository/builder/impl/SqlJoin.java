package com.itblee.repository.builder.impl;

import com.itblee.utils.StringUtils;

import java.io.Serializable;
import java.util.Objects;

public final class SqlJoin implements Serializable {

	private static final long serialVersionUID = -1075257006792061786L;

	public enum Type {
	    EMPTY(""),
        INNER_JOIN("INNER JOIN"),
        LEFT_JOIN("LEFT JOIN"),
        RIGHT_JOIN("RIGHT JOIN"),
        FULL_JOIN("FULL JOIN");

        private final String keyword;

        Type(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }
    }

    private final String joinTable;
    private final SqlQuery joinInnerTable;
    private final String joinOn;
    private final Type joinType;

    public static final class Builder {
        private String joinTable = null;
        private SqlQuery joinInnerTable = null;
        private String joinOn = null;
        private Type joinType = null;

        public Builder join(String joinTable) {
            this.joinTable = joinTable;
            this.joinInnerTable = null;
            return this;
        }

        public Builder join(SqlQuery joinInnerTable) {
            this.joinInnerTable = joinInnerTable;
            this.joinTable = null;
            return this;
        }

        public Builder on(String joinON) {
            this.joinOn = joinON;
            return this;
        }

        public Builder type(Type joinType) {
            this.joinType = joinType;
            return this;
        }

        public SqlJoin done() {
            Objects.requireNonNull(joinType);
            if ((joinTable == null && joinInnerTable == null)
                    || StringUtils.isBlank(joinOn))
                throw new IllegalArgumentException();
            return new SqlJoin(this);
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public SqlJoin(Builder builder) {
        this.joinTable = builder.joinTable;
        this.joinInnerTable = builder.joinInnerTable;
        this.joinOn = builder.joinOn;
        this.joinType = builder.joinType;
    }

    public String getJoinTable() {
        if (isInnerTable())
            throw new UnsupportedOperationException();
        return joinTable;
    }

    public SqlQuery getJoinInnerTable() {
        if (!isInnerTable())
            throw new UnsupportedOperationException();
        return joinInnerTable;
    }

    public String getJoinOn() {
        return joinOn;
    }

    public Type getJoinType() {
        return joinType;
    }

    public boolean isInnerTable() {
        return joinInnerTable != null && joinTable == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlJoin)) return false;
        SqlJoin that = (SqlJoin) o;
        if (!Objects.equals(isInnerTable(), that.isInnerTable()))
            return false;
        if (isInnerTable())
            return Objects.equals(getJoinInnerTable(), that.getJoinInnerTable())
                    && Objects.equals(getJoinOn(), that.getJoinOn());
        else return Objects.equals(getJoinTable(), that.getJoinTable())
                && Objects.equals(getJoinOn(), that.getJoinOn());
    }

    @Override
    public int hashCode() {
        if (isInnerTable())
            return Objects.hash(getJoinInnerTable(), getJoinOn());
        else return Objects.hash(getJoinTable(), getJoinOn());
    }
}
