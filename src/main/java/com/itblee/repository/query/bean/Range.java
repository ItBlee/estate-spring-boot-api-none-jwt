package com.itblee.repository.query.bean;

public class Range {
    public static final String RANGE_FROM = "from";
    public static final String RANGE_TO = "to";

    public Number from;
    public Number to;

    private Range(Number from, Number to) {
        this.from = from;
        this.to = to;
    }

    public static Range newRange(Number from, Number to) {
        if ((from == null && to == null))
            throw new IllegalStateException();
        if (from != null && to != null && from.doubleValue() > to.doubleValue())
            throw new IllegalArgumentException("FROM value is greater than TO value");
        return new Range(from, to);
    }
}