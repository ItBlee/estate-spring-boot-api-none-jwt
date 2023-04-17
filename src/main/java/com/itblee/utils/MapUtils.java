package com.itblee.utils;

import java.util.Map;

public final class MapUtils {

    private MapUtils() {}

    public static Object get(Map<?, ?> map, Object key) {
        return map.getOrDefault(key, null);
    }

}
