package com.dulgi.ex.java.concurrency.futuretask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class CallableBasic {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        // sample callable
        Callable<Integer> dummyCallableTask = new Callable() {
            @Override
            public Object call() throws Exception {
                int i = atomicInteger.getAndAdd(1);
                System.out.println("executing futureTask " + i);
                Thread.sleep(5000);
                return "I am callable " + i;
            }
        };

        // register Callable object to FutureTask
        FutureTask<String> futureTask;

        futureTask = new FutureTask(dummyCallableTask);
        new Thread(futureTask).start();
        while(!futureTask.isDone()){
            System.out.println("i am doing my job");
            Thread.sleep(1000);
        }
        System.out.println(futureTask.get());

        // cancel test
        FutureTask<String> futureTask2 = new FutureTask(dummyCallableTask);
        new Thread(futureTask2).start();
        Thread.sleep(3000);
        System.out.println("i am doing my job");
        // something occurs!
        futureTask2.cancel(true);
//        System.out.println(futureTask2.get()); // this code makes error

    }

}
