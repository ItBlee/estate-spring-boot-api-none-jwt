package com.itblee.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public final class CastUtils {

    private CastUtils() {}

    public static <T> Optional<T> cast(final Object o, Class<T> cls) {
        if (cls == null)
            throw new IllegalArgumentException("Couldn't cast to null");
        if (o == null)
            return Optional.empty();
        if (cls.isInstance(o))
            return Optional.of(cls.cast(o));

        final String STRING_DELIMITER = ",";

        String str;
        if (o instanceof CharSequence || o instanceof Number) {
            str = o.toString();
        } else if (o.getClass().isArray()) {
            Object[] arr = (Object[]) o;
            str = String.join(STRING_DELIMITER, Arrays.copyOf(arr, arr.length, String[].class));
        } else if (o instanceof Collection<?>) {
            Object[] arr = ((Collection<?>) o).toArray();
            str = String.join(STRING_DELIMITER, Arrays.copyOf(arr, arr.length, String[].class));
        } else {
            throw new IllegalArgumentException("Cast type not supported yet.");
        }
        if (StringUtils.isBlank(str))
            return Optional.empty();

        Object obj;
        try {
            if (cls.isAssignableFrom(String.class)) {
                obj = str;
            } else if (cls.isAssignableFrom(Integer.class)) {
                obj = Integer.valueOf(str);
            } else if (cls.isAssignableFrom(Long.class)) {
                obj = Long.valueOf(str);
            } else if (cls.isAssignableFrom(String[].class)) {
                obj = str.split(STRING_DELIMITER);
            } else {
                throw new IllegalArgumentException("Cast to " + cls.getSimpleName() + " not supported yet.");
            }
            return Optional.of(cls.cast(obj));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

}
