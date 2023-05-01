package com.itblee.util;

import com.itblee.sqlbuilder.model.Code;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public final class CastUtils {

    private static final String STRING_DELIMITER = ",";

    private CastUtils() {
        throw new AssertionError();
    }

    public static <T> Optional<T> cast(final Object o, Class<T> cls) {
        if (cls == null)
            throw new ClassCastException("Couldn't cast to null.");
        if (o == null)
            return Optional.empty();
        if (cls.isInstance(o))
            return Optional.of(cls.cast(o));

        String str = castToString(o);
        if (StringUtils.isBlank(str))
            return Optional.empty();

        Object obj;
        try {
            if (!cls.isArray()) {
                if (Code.class.isAssignableFrom(cls)) {
                    obj = Code.valueOf(str);
                } else if (CharSequence.class.isAssignableFrom(cls)) {
                    obj = str;
                } else if (Integer.class.isAssignableFrom(cls)) {
                    obj = Integer.valueOf(str);
                } else if (Long.class.isAssignableFrom(cls)) {
                    obj = Long.valueOf(str);
                } else if (Date.class.isAssignableFrom(cls)) {
                    obj = Date.valueOf(str);
                } else {
                    throw new ClassCastException("Cast to " + cls.getSimpleName() + " not supported yet.");
                }
            } else {
                String[] arr = str.split(STRING_DELIMITER);
                if (Code[].class.isAssignableFrom(cls)) {
                    obj = Code.valueOf(arr);
                } else if (CharSequence[].class.isAssignableFrom(cls)) {
                    obj = arr;
                } else if (Integer[].class.isAssignableFrom(cls)) {
                    obj = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
                } else {
                    throw new ClassCastException("Cast to " + cls.getSimpleName() + " not supported yet.");
                }
            }

            return Optional.of(cls.cast(obj));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid value type.");
        }
    }

    public static String castToString(Object o) {
        ValidateUtils.requireNonNull(o);
        String str;
        if (o instanceof CharSequence || o instanceof Number || o instanceof Date) {
            str = o.toString();
        } else if (o.getClass().isArray()) {
            Object[] arr = (Object[]) o;
            str = String.join(STRING_DELIMITER, Arrays.copyOf(arr, arr.length, String[].class));
        } else if (o instanceof Collection<?>) {
            Object[] arr = ((Collection<?>) o).toArray();
            str = String.join(STRING_DELIMITER, Arrays.copyOf(arr, arr.length, String[].class));
        } else
            throw new ClassCastException("Cast type " + o.getClass().getName() + " not supported yet.");
        return str;
    }

}
