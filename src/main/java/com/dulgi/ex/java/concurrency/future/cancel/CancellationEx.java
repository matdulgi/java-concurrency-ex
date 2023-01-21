package com.dulgi.ex.java.concurrency.future.cancel;

import java.util.concurrent.*;
import java.util.stream.LongStream;


public class CancellationEx {
    static long i = 0;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CancellationEx ex = new CancellationEx();
        Future<Long> f = executorService.submit(ex.callable);
        ex.verify(f);

//        Future<Long> f2 = executorService.submit(ex.callable2);
//        ex.verify(f2);

        executorService.shutdown();
    }

    public Callable<Long> callable = () -> {
        return LongStream.iterate(1, l -> {
            l=i++;
            return l;
        }).max().getAsLong();
//        while(true){
//        while(!Thread.interrupted()){
//            i++;
//        }
//        return i;
    };

    public Callable<Long> callable2 = () -> {
        return LongStream.iterate(1, l -> {
            methodThatHandlesInterrupt();
            l=i++;
            return l;
        }).max().getAsLong();
    };

    public void methodThatHandlesInterrupt(){
        if(Thread.interrupted()){
            throw new RuntimeException();
        }
    }


    public void verify(Future<Long> future){
        try {
            i = 0;
            Thread.sleep(1000);
            System.out.println("i : " + i);
            future.cancel(true);
            Thread.sleep(1000);
            System.out.println("i : " + i);
            System.out.println("is cancelled? : " + future.isCancelled());
            System.out.println("is done? : " + future.isDone());
            System.out.println();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}
