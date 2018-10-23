package de.splotycode.jwa.utils;

/**
 * General Utilities for Strings
 */
public final class StringUtil {

    /**
     * Checks if an string is empty
     * @param str the string that you want to check
     * @return if it is empty or not (true if empty, false when not)
     */
    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
