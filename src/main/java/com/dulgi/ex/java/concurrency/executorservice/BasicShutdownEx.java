package com.dulgi.ex.java.concurrency.executorservice;

import java.util.concurrent.*;

public class BasicShutdownEx {
    ExecutorService executorService;
    long i = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        BasicShutdownEx ex = new BasicShutdownEx();
        ex.basicShutdownEx();
    }

    public void basicShutdownEx() throws ExecutionException, InterruptedException {
        executorService = Executors.newSingleThreadExecutor();

        Future<String> future = executorService.submit(callable1);
        info("just executed");
        System.out.println("result : " + future.get());
        info("after finished");

        executorService.shutdown();
        info("after shutdown");

        while(!executorService.isTerminated()){}
        info("after waiting");

        // this not works
        try {
            executorService.submit(() -> System.out.println("goodday"));
        } catch (RejectedExecutionException e) {
//            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // re-shutdown
        executorService.shutdown();
        info("after re-shutdown");
        // this affects nothing but executed

    }

    public Callable<String> callable1 = () ->{
        try {
            Thread.sleep(500);
            return "work finished";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    };


    public void info(String message){
        System.out.println(message);
        System.out.println("is terminated? " + executorService.isTerminated());
        System.out.println("is shutdown? " + executorService.isShutdown());
        System.out.println("");
    }
}
