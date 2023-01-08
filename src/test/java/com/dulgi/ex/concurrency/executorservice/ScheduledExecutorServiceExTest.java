package com.dulgi.ex.concurrency.executorservice;

import com.dulgi.ex.java.concurrency.executorservice.ScheduledExecutorServiceEx;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExTest {

    private final ScheduledExecutorServiceEx singleThreadEx = new ScheduledExecutorServiceEx(
            Executors.newSingleThreadScheduledExecutor() );
    private final ScheduledExecutorServiceEx threadPoolEx = new ScheduledExecutorServiceEx(
            Executors.newScheduledThreadPool(10) );


    @Test
    public void testSchedule() throws ExecutionException, InterruptedException {
        singleThreadEx.schedule(3);
    }

    @Test
    public void testScheduleAtFixedDate() throws InterruptedException {
        singleThreadEx.scheduleAtFixedDate(3, 10);
        Thread.sleep(30000);
        // job should start at every 3 seconds (3, 13, 23...)
    }

    @Test
    public void testScheduleAtFixedRateForLessTimeThanPeriod() throws InterruptedException {
        singleThreadEx.scheduleAtFixedDate(3, 1);
        TimeUnit.SECONDS.sleep(30);

        // conclusion :
    }

    @Test
    public void testScheduleWithFixedDelay() throws InterruptedException {
        singleThreadEx.scheduleWithFixedDelay(3, 1);
        TimeUnit.SECONDS.sleep(30);
    }

    @Test
    public void stopTest() throws InterruptedException {
        singleThreadEx.scheduleWithFixedDelay(3, 1);
        TimeUnit.SECONDS.sleep(10);
        singleThreadEx.executorService.shutdown();
    }

    // when the ScheduleThreadPool used?
    @Test
    public void TestScheduleThreadPool() throws InterruptedException {
        singleThreadEx.scheduleWithFixedDelay(3, 5);
        singleThreadEx.scheduleWithFixedDelay(3, 5);
        singleThreadEx.scheduleWithFixedDelay(3, 5);
        singleThreadEx.scheduleWithFixedDelay(3, 5);
        TimeUnit.SECONDS.sleep(30);
        singleThreadEx.executorService.shutdown();
        threadPoolEx.scheduleWithFixedDelay(3, 5);
        threadPoolEx.scheduleWithFixedDelay(3, 5);
        threadPoolEx.scheduleWithFixedDelay(3, 5);
        threadPoolEx.scheduleWithFixedDelay(3, 5);
        TimeUnit.SECONDS.sleep(30);
        // conclusion: SingleThreadExecutor cannot run task for parallel, ThreadPoolExecutor can
    }
}
