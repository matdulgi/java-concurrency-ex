package com.dulgi.ex.java.concurrency.thread;


import com.dulgi.ex.java.concurrency.utils.InvalidCode;

public class InterruptEx {
    static long i = 0;

    public static void main(String[] args) throws InterruptedException {
        InterruptEx ex = new InterruptEx();

        // it does not finish
        Thread thread1 = ex.threadWithNoInterruptHandling();
        ex.verify(thread1);

//        ex.isInterruptedEx();

        Thread thread2 = ex.threadWithInterruptHandling();
        ex.verify(thread2);

        /* why static interrupted?
         * will `interrupted` method in a thread be affected for interrupt on other thread?
         * if interrupt to dummy thread, will the thread3 stop?
        */
        Thread dummyThread = ex.dummyThread();
        dummyThread.start();
        Thread thread3 = ex.threadWithInterruptHandling();
        new Thread(() -> {
            try {
                Thread.sleep(500);
                dummyThread.interrupt();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        ex.verify(thread3);
        // conclusion : static method Thread.interrupted checks interrupt in own thread


        /* interrupt just only change flag
        it needs to be used with the method it handles the interrupt
         */
        Thread threadThatHasTheMethodHandlesInterrupt = new Thread(() -> {
            while(true){
                i++;
                try {
                    ex.methodHandlesInterrupt();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ex.verify(threadThatHasTheMethodHandlesInterrupt);




    }

    public Thread threadWithNoInterruptHandling(){
        Thread thread = new Thread(() -> {
            while(true) {
                InterruptEx.i++;
            }
        } );
        return thread;
    }

    public Thread threadWithInterruptHandling(){
        Thread thread = new Thread(() -> {
            while(!Thread.interrupted()) {
                InterruptEx.i++;
            }
        } );
        return thread;
    }

    // isInterrupted method cannot be used in thread
    @InvalidCode
    public void threadPassingRunnable(){
        Thread thread = new Thread(() -> {
//            if (thread.isInterrupted())...
        });
    }

    public Thread dummyThread(){
        Thread dummyThread = new Thread(() ->
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
            }
        });
        return dummyThread;
    }

    public void methodHandlesInterrupt() throws InterruptedException {
        if (Thread.interrupted()){
            throw new InterruptedException();
        }
    }

    public void verify(Thread thread){
        System.out.println("verifying");

        try {
            i = 0;
            thread.start();

            Thread.sleep(1000);
            System.out.println("i : " + i);

            thread.interrupt();
            Thread.sleep(1000);
            System.out.println("i : " + i);
            System.out.println("alive? : " + thread.isAlive());
            System.out.println("is interrupted? : " + thread.isInterrupted());
            System.out.println();

            // force stop
            thread.stop();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
