package com.dulgi.ex.java.concurrency.executorservice;


import com.dulgi.ex.java.concurrency.Tasks;
import com.dulgi.ex.java.concurrency.utils.TimeUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledExecutorServiceEx {

    public ScheduledExecutorService executorService;

    public ScheduledExecutorServiceEx (ScheduledExecutorService scheduledExecutorService){
        this.executorService = scheduledExecutorService;
    }

    public void schedule(long delay) throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        System.out.println("task will start in " +delay+ " seconds");
        Future<String> future = executorService.schedule(Tasks.dummyCallable, delay, TimeUnit.SECONDS);
        System.out.println(future.get());

        TimeUtils.printTimeTakes(start);
    }

    public void scheduleAtFixedDate(long initialDelay, long period){
        System.out.println("the first task will start in " +initialDelay+" seconds, and " +period+ " every seconds ");
        executorService.scheduleAtFixedRate(Tasks.timeAwareRunnable, initialDelay, period, TimeUnit.SECONDS);
        System.out.println("task has started in other thread");
    }

    public void scheduleWithFixedDelay(long initialDelay, long delay){
        System.out.println("the first task will start in " +initialDelay+" seconds, and in " +delay+ " seconds after finish");
        executorService.scheduleWithFixedDelay(Tasks.timeAwareRunnable, initialDelay, delay, TimeUnit.SECONDS);
        System.out.println("task has started in other thread");
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorServiceEx ex = new ScheduledExecutorServiceEx(executorService);

        ex.schedule(3);

        executorService.shutdown();

    }


}