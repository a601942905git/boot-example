package com.boot.example.config;

/**
 * com.boot.example.config.SubjectHolder
 *
 * @author lipeng
 * @date 2018/12/19 下午4:43
 */
public class SubjectHolder {

    private static final ThreadLocal<String> SUBJECT_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(String subject) {
        SUBJECT_THREAD_LOCAL.set(subject);
    }

    public static String get() {
        return SUBJECT_THREAD_LOCAL.get();
    }

    public static void remove() {
        SUBJECT_THREAD_LOCAL.remove();
    }
}
