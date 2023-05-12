package com.itblee.util;

import java.util.Map;

public final class MapUtils {

    private MapUtils() {
        throw new AssertionError();
    }

    public static Object getOrNull(Map<?, ?> map, Object key) {
        if (map == null || key == null)
            return null;
        return map.getOrDefault(key, null);
    }

    public static <T> T getAndCast(Map<?, ?> map, Object key, Class<T> cast) {
        return CastUtils.cast(getOrNull(map, key), cast).orElse(null);
    }

}
