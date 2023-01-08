package com.dulgi.ex.java.concurrency.executorservice;


import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorServiceEx {

    public static AtomicInteger taskNum = new AtomicInteger(0);

    public static int randomIntRangeThreeToFive (){
        return ((int)(Math.random() * 10) % 3 + 3);
    }

    public static String dummyTask() {
        int thisTaskNum = taskNum.addAndGet(1);
        int time = randomIntRangeThreeToFive();
        System.out.println("task " + thisTaskNum+ " start");
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("task " + thisTaskNum + " done");
        return "task " + thisTaskNum + " result";
    }


    public void run() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<String>> tasks = IntStream.rangeClosed(1, 5).mapToObj(i -> new Callable<String>(){
            @Override
            public String call() throws Exception {
                return dummyTask();
            } }).collect(Collectors.toList());

        List<Future<String>> results = executorService.invokeAll(tasks); // this will invoke all callable in parallel

        results.stream().map(f -> {
            try {
                return f.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);

        System.out.println("job done in " +  (System.currentTimeMillis() - start)+ " msec");
        long start2 = System.currentTimeMillis();

        String result = executorService.invokeAny(tasks);
        System.out.println(result);
        System.out.println("job done in " +  (System.currentTimeMillis() - start2)+ " msec");


        executorService.shutdown(); // should showdown !

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorServiceEx ex = new ExecutorServiceEx();
        ex.run();
    }
}