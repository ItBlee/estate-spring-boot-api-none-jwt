package com.itblee.util;

import java.util.Map;

public final class MapUtils {

    private MapUtils() {
        throw new AssertionError();
    }

    public static Object get(Map<?, ?> map, Object key) {
        if (map == null || key == null)
            return null;
        return map.getOrDefault(key, null);
    }

    public static <T> T get(Map<?, ?> map, Object key, Class<T> cast) {
        Object val = get(map, key);
        if (val == null)
            return null;
        return CastUtils.cast(val, cast).orElse(null);
    }

}
