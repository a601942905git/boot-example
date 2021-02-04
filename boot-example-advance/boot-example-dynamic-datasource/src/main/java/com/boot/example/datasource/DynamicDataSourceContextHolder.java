package com.boot.example.datasource;

/**
 * com.boot.example.datasource.DynamicDataSourceContextHolder
 *
 * @author lipeng
 * @date 2021/2/4 10:39 AM
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void set(String value) {
        threadLocal.set(value);
    }

    public static String get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
