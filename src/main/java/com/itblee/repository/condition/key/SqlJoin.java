package com.itblee.repository.condition.key;

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
    private String joinON;
    private Type joinType;

    public SqlJoin() {
    }

    public String getJoinTable() {
        if (this.joinTable == null)
            this.joinTable = "";
        return joinTable;
    }

    public void join(String joinTable) {
        if (this.joinTable == null)
            this.joinTable = joinTable;
        else throw new IllegalStateException("Already set !");
    }

    public String getJoinON() {
        if (this.joinON == null)
            this.joinON = "";
        return joinON;
    }

    public void on(String joinON) {
        if (this.joinON == null)
            this.joinON = joinON;
        else throw new IllegalStateException("Already set !");
    }

    public Type getJoinType() {
        if (this.joinType == null)
            this.joinType = Type.EMPTY;
        return joinType;
    }

    public void type(Type joinType) {
        if (this.joinType == null)
            this.joinType = joinType;
        else throw new IllegalStateException("Already set !");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SqlJoin)) return false;
        SqlJoin joinMap = (SqlJoin) o;
        return getJoinTable().equals(joinMap.getJoinTable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJoinTable());
    }
}
