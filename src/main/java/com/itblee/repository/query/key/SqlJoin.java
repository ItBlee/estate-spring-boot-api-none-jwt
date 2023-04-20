package com.itblee.repository.query.key;

import java.util.Objects;

public class SqlJoin {

    public enum Type {
        EMPTY(""),
        INNER_JOIN("INNER JOIN"),
        LEFT_JOIN("LEFT JOIN"),
        RIGHT_JOIN("RIGHT JOIN"),
        FULL_JOIN("FULL JOIN");

        private final String keyword;

        Type(String syntax) {
            this.keyword = syntax;
        }

        public String getKeyword() {
            return keyword;
        }
    }

    private String joinTable;
    private SqlQuery nestedJoin;
    private String joinON;
    private Type joinType;

    public SqlJoin() {
    }

    public String getJoinTable() {
        return joinTable;
    }

    public SqlQuery getNestedJoin() {
        return nestedJoin;
    }

    public SqlJoin join(String joinTable) {
        if (this.joinTable == null && this.nestedJoin == null)
            this.joinTable = joinTable;
        else throw new IllegalStateException("Already set !");
        return this;
    }
    public SqlJoin join(SqlQuery nestedJoin) {
        if (this.nestedJoin == null && this.joinTable == null)
            this.nestedJoin = nestedJoin;
        else throw new IllegalStateException("Already set !");
        return this;
    }


    public String getJoinON() {
        if (this.joinON == null)
            this.joinON = "";
        return joinON;
    }

    public SqlJoin on(String joinON) {
        if (this.joinON == null)
            this.joinON = joinON;
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public Type getJoinType() {
        if (this.joinType == null)
            this.joinType = Type.EMPTY;
        return joinType;
    }

    public SqlJoin type(Type joinType) {
        if (this.joinType == null)
            this.joinType = joinType;
        else throw new IllegalStateException("Already set !");
        return this;
    }

    public boolean isNestedJoin() {
        return nestedJoin != null && joinTable == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlJoin)) return false;
        SqlJoin sqlJoin = (SqlJoin) o;
        return Objects.equals(getJoinTable(), sqlJoin.getJoinTable())
                && Objects.equals(getJoinON(), sqlJoin.getJoinON())
                && getJoinType() == sqlJoin.getJoinType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJoinTable(), getJoinON(), getJoinType());
    }
}
