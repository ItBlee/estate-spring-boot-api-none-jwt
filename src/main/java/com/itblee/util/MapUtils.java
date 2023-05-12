package com.itblee.util;

import java.util.Map;
import java.util.Optional;

public final class MapUtils {

    private MapUtils() {
        throw new AssertionError();
    }

    public static Object getOrNull(Map<?, ?> map, Object key) {
        return map.getOrDefault(key, null);
    }

    public static <T> Optional<T> getAndCast(Map<?, ?> map, Object key, Class<T> cast) {
        return CastUtils.cast(getOrNull(map, key), cast);
    }

}
