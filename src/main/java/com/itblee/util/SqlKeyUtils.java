package com.itblee.util;

import com.itblee.sqlbuilder.SqlKey;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class SqlKeyUtils {

    private final static Map<Class<? extends SqlKey>, Map<String, SqlKey>> instances = new HashMap<>();

    private SqlKeyUtils() {
        throw new AssertionError();
    }

    public static Map<String, SqlKey> getInstance(Class<? extends SqlKey> kClass) {
        Map<String, SqlKey> keyMap = instances.getOrDefault(kClass, null);
        if (keyMap == null) {
            keyMap = Arrays.stream(kClass.getEnumConstants())
                    .filter(key -> !key.isScope())
                    .collect(Collectors.toMap(SqlKey::getParamName, Function.identity(),
                            (o1, o2) -> o1, () -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER)));
            instances.put(kClass, Collections.unmodifiableMap(keyMap));
        }
        return keyMap;
    }

    public static <T> Optional<T> mapByKey(Map<String, ?> row, Class<T> tClass, Class<? extends SqlKey> kClass) {
        final String SETTER_KEYWORD = "set";
        Set<Method> setters = Arrays.stream(tClass.getMethods())
                .filter(method -> method.getName().startsWith(SETTER_KEYWORD))
                .collect(Collectors.toSet());
        try {
            T instance = tClass.newInstance();
            for (Method setter : setters) {
                String setterName = StringUtils.formatAlphaOnly(setter.getName())
                        .replaceFirst(SETTER_KEYWORD, "");
                SqlKey key = SqlKeyUtils.getInstance(kClass).getOrDefault(setterName, null);
                if (key != null && key.getType() == setter.getParameterTypes()[0])
                    setter.invoke(instance, MapUtils.get(row, key.getParamName(), key.getType()));
            }
            return Optional.of(instance);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

}
