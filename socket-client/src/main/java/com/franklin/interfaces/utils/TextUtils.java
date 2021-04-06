package com.franklin.interfaces.utils;

public class TextUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
