//package com.nebula.framework.context;
//
//public class TaskContext {
//    private static final ThreadLocal<Boolean> taskHolder = new ThreadLocal<>();
//
//    public static void startTask() {
//        taskHolder.set(true);
//    }
//
//    public static void endTask() {
//        taskHolder.remove();
//    }
//
//    public static boolean isTask() {
//        return taskHolder.get() != null && taskHolder.get();
//    }
//}
