package com.itblee.utils;

public final class StringUtils {

    private StringUtils() {}

    public static boolean isBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0)
            return true;
        for (int i = 0; i < cs.length(); i++) {
            if (!Character.isWhitespace(cs.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    public static String formatAlphaOnly(final String str) {
        return str.replaceAll("[^a-zA-Z]", "");
    }

    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null)
            return false;
        final int length = searchStr.length();
        if (length == 0)
            return true;
        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

}
