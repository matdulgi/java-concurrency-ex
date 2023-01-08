package com.dulgi.ex.concurrency.future.baeldung;

import com.dulgi.ex.java.concurrency.completablefuture.baeldung.FutureEx;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class FutureExTest {
    FutureEx futureEx = new FutureEx();
    long start = System.nanoTime();
    @After
    public void after(){
        System.out.println("done in " + (System.nanoTime() - start) / 1000000 + " msecs");
    }

    @Test
    public void calculateAsyncAsOldFutureTest() throws ExecutionException, InterruptedException {
        Assert.assertEquals("hello", futureEx.calculateAsyncAsSimpleFuture().get() );
    } // 1114

    @Test
    public void calculateAsyncWithEncapsulatedComputationTest() throws ExecutionException, InterruptedException {
        Assert.assertEquals("hello", futureEx.calculateAsyncWithEncapsulatedComputation().get() );
    }

    @Test
    public void thenApplyFutureTest() throws ExecutionException, InterruptedException {
        Assert.assertEquals("hello world", futureEx.thenApplyFuture().get() );
    }
    @Test
    public void thenComposeFutureTest() throws ExecutionException, InterruptedException {
        Assert.assertEquals("hello world", futureEx.thenComposeFuture().get() );
    }

    @Test
    public void combineFutures() throws ExecutionException, InterruptedException {
//        System.out.println(futureEx.combineFutures().get());
        futureEx.combineFutures();
    }

    @Test
    public void asynchronousComputationTest(){
        try {
            Assert.assertEquals(futureEx.thenApplyFuture().get(), "hello world");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
