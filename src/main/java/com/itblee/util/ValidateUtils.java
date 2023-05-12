package com.itblee.util;

import com.itblee.repository.sqlbuilder.model.Range;

import java.sql.Date;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

public final class ValidateUtils {

    private ValidateUtils() {
        throw new AssertionError();
    }

    public static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NoSuchElementException();
        return obj;
    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null)
            throw new NoSuchElementException(message);
        return obj;
    }

    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        if (obj == null)
            throw new NoSuchElementException(messageSupplier.get());
        return obj;
    }

    public static boolean isValid(Object o) {
        if (o == null)
            return false;
        if (o.getClass().isArray()) {
            if (o instanceof CharSequence[] && !StringUtils.containsBlank((CharSequence[]) o))
                return true;
            return o instanceof Integer[] || o instanceof Long[];
        } else {
            if (o instanceof CharSequence && StringUtils.isNotBlank(o.toString()))
                return true;
            return o instanceof Integer || o instanceof Long
                    || o instanceof Date || o instanceof Range;
        }
    }

    public static boolean isNotValid(Object o) {
        return !isValid(o);
    }

    public static void requireValid(Object o) {
        if (ValidateUtils.isNotValid(o))
            throw new IllegalArgumentException("Invalid.");
    }

    public static void requireValidParams(Map<String,?> params) {
    }

}
