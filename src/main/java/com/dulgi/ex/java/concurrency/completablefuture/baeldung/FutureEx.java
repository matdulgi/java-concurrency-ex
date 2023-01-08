package com.dulgi.ex.java.concurrency.completablefuture.baeldung;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;

// https://www.baeldung.com/java-completablefuture
public class FutureEx {
    public Future<String> calculateAsyncAsSimpleFuture() {
        CompletableFuture<String> future = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() ->{
            dummyJob();;
            future.complete("hello");
            return null;
        });
        return future;
    }

    public Future<String> calculateAsyncWithEncapsulatedComputation(){
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello");
        return CompletableFuture.supplyAsync(() -> "hello");
    }


//    asynchronousComputation
    public Future<String> thenApplyFuture(){
        return CompletableFuture.supplyAsync(() -> "hello")
                .thenApply(s -> s + " world");
    }

    public Future<String> thenComposeFuture(){
        return CompletableFuture.supplyAsync(slowHelloSupplier)
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " world"));
    }


    public Future<String> combineFutures(){
        return CompletableFuture.supplyAsync(slowHelloSupplier)
                .thenAcceptBoth(CompletableFuture.supplyAsync(slowWorldSupplier),
                        (s1, s2) -> System.out.println((String)s1 + s2));
    }

    Supplier slowHelloSupplier = new Supplier<String>() {
        @Override
        public String get() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "hello";
        }
    };

    Supplier<String> slowWorldSupplier = new Supplier<String>() {
        @Override
        public String get() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return " world";
        }
    };

    private void dummyJob(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
