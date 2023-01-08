package com.dulgi.ex.java.concurrency.utils;

public class TimeUtils {
    public static void printTimeTakes(long startTimeMillis){
        System.out.println("job done in " + (System.currentTimeMillis() - startTimeMillis) + " msec");
    }

    public static long timeTakes(long fromTimeMillis){
        return (System.currentTimeMillis() - fromTimeMillis);
    }
}
