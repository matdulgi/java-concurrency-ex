package com.dulgi.ex.java.concurrency;

import com.dulgi.ex.java.concurrency.utils.TimeUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Tasks {

    public static AtomicInteger taskNum = new AtomicInteger(0);
    public static long start = System.currentTimeMillis();

    public static int randomIntRangeThreeToFive (){
        return ((int)(Math.random() * 10) % 3 + 3);
    }

    public static void dummyTask(int taskNum){
        int time = randomIntRangeThreeToFive();
        System.out.println("task " + taskNum + " start : " + time + " sec time task");
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("task " + taskNum + " done");
    }

    public static void timeAwareTask(int taskNum, long startTime){
        int time = randomIntRangeThreeToFive();
        System.out.println("task " + taskNum + " start : " + time + " sec time task " + " current time: " + TimeUtils.timeTakes(startTime));
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("task " + taskNum + " done  current time: " + TimeUtils.timeTakes(startTime));
    }
    public static Runnable dummyRunnable = () -> {
        int thisTaskNum = taskNum.addAndGet(1);
        dummyTask(thisTaskNum);
    };

    public static Callable<String> dummyCallable = () ->{
        int thisTaskNum = taskNum.addAndGet(1);
        dummyTask(thisTaskNum);
        return "task " + thisTaskNum + " result";
    };

    public static Runnable timeAwareRunnable = () -> {
        int thisTaskNum = taskNum.addAndGet(1);
        timeAwareTask(thisTaskNum, start);
    };
}
