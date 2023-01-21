package com.dulgi.ex.java.concurrency.executorservice;

import java.util.concurrent.*;

public class ShutdownNowEx {
    ExecutorService executorService;
    long i = 0;
    Thread thread;
    boolean interrupted;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ShutdownNowEx ex = new ShutdownNowEx();
        ex.shutdownNowEx();
    }
    public void shutdownNowEx() throws InterruptedException, ExecutionException {
        init();
        System.out.println("=== case 1 : shutdown === ");
        executorService = Executors.newSingleThreadExecutor();
        Future<Long> future = executorService.submit(callable);
        System.out.println("task submitted");
        Thread.sleep(100);
        timer();
        executorService.shutdown();
        info("shutdown...");
        System.out.println("result : " + future.get());
//        System.out.println("shutdown doesn't send interrupt to executing task");
        info("after result");
        System.out.println("=== case 1 : finished ===\n\n\n");

        init();
        System.out.println("=== case 2 : shutdownNow === ");
        executorService = Executors.newSingleThreadExecutor();
        future = executorService.submit(callable);
        System.out.println("task submitted");
        Thread.sleep(100);
        timer();
        executorService.shutdownNow();
        info("shutdown now...");
        System.out.println("### is interrupted? " + thread.isInterrupted());
        System.out.println("### is alive? " + thread.isAlive());
        Thread.sleep(10);
        System.out.println("after waiting");
        System.out.println("### is interrupted? " + thread.isInterrupted());
        System.out.println("### is alive? " + thread.isAlive());
        System.out.println("result : " + future.get());
        info("after result");
        System.out.println("=== case 2 : finished ===");
    }

    public void init(){
        i = 0;
        interrupted = false;

    }

    public Callable<Long> callable = () ->{
        thread = Thread.currentThread();
        while (!Thread.currentThread().isInterrupted()){
            i++;
        }
        interrupted = true;
        return i;
    };

    public void timer () {
        Thread timer = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (!interrupted){
                System.out.println("thread does not finished! force stop");
                thread.interrupt();
            }
        });
        timer.start();
    }

    public void info(String message){
        System.out.println(message);
        System.out.println("is terminated? " + executorService.isTerminated());
        System.out.println("is shutdown? " + executorService.isShutdown());
        System.out.println("");
    }
}
