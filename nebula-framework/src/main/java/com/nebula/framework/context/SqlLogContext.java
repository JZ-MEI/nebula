package com.nebula.framework.context;

public class SqlLogContext {
    private static final ThreadLocal<Boolean> sqlLogHolder = new ThreadLocal<>();

    public static void startLog() {
        sqlLogHolder.set(true);
    }

    public static void endLog() {
        sqlLogHolder.remove();
    }

    public static boolean isLog() {
        return sqlLogHolder.get() != null && sqlLogHolder.get();
    }
}
