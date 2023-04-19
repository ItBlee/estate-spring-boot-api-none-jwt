package com.itblee.utils;

import java.util.Map;

public final class MapUtils {

    private MapUtils() {}

    public static Object get(Map<?, ?> map, Object key) {
        if (map == null || key == null)
            return null;
        return map.getOrDefault(key, null);
    }

    public static <E> E get(Map<?, ?> map, Object key, Class<E> e) {
        Object val = get(map, key);
        if (val == null)
            return null;
        return CastUtils.cast(val, e).orElse(null);
    }

}
