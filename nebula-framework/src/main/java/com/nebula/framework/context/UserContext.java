package com.nebula.framework.context;

public class UserContext {
    private static final ThreadLocal<Integer> userHolder = new ThreadLocal<>();

    public static void setUserId(Integer userId) {
        userHolder.set(userId);
    }

    public static Integer getUserId() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}
