package com.dulgi.ex.java.concurrency.completablefuture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class CompletableFutureBasic {


    public CompletableFuture<String> futureEx1(){
        CompletableFuture<String> completableFuture;
//                = CompletableFuture.supplyAsync()
        return null;

    }

    private Map<String, String> longTimeJob1(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new HashMap<String, String>(){{
            put("k1","good");
            put("k2","bad");
            put("k3","my");
        }};
    }

    private Map<String,String> longTimeJob2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return new HashMap<String, String>(){{
            put("k1", "day");
            put("k2","news");
            put("k3","journy");
        }};
    }

}
