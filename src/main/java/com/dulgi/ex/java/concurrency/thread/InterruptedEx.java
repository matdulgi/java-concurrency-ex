package com.dulgi.ex.java.concurrency.thread;

public class InterruptedEx {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ex 1 === ");
        Thread thread1 = new Thread(() ->{
            while(true){
                if(Thread.interrupted() && !Thread.interrupted()) {
                    System.out.println("ex 1 finished");
                    return;
                }
            }
        } );
        thread1.start();
        Thread.sleep(1);
        thread1.interrupt();
//        thread1.join();
        Thread.sleep(100);
        // conclusion : second interrupted calling returns false


        System.out.println("=== ex 2 === ");
        Thread thread2 = new Thread(() ->{
            while(true){
                if(Thread.currentThread().isInterrupted() && Thread.currentThread().isInterrupted()) {
                    System.out.println("ex 2 finished");
                    return;
                }
            }
        } );
        thread2.start();
        Thread.sleep(1);
        thread2.interrupt();
        Thread.sleep(100);
        // conclusion : Thread.currentThread().isInterrupted() does not change interrupt to false


        System.out.println("=== ex 3 === ");
        Thread thread3 = new Thread(() ->{
            while(true){
                boolean x = Thread.interrupted();
                boolean y = Thread.currentThread().isInterrupted();
                if(x && !y) {
                    System.out.println("interrupted : " + x + ", isInterrupted : " + y);
                    System.out.println("ex 3 finished");
                    return;
                }
            }
        } );
        thread3.start();
        Thread.sleep(1);
        thread3.interrupt();
        Thread.sleep(100);
//         conclusion : after interrupt(), isInterrupt() returns false



        System.out.println("=== ex 4 === ");
        Thread thread = new Thread(() ->{
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ex 4 finished");
        });
        thread.start();
        System.out.println("is alive? " + thread.isAlive());
        Thread.sleep(100);
        thread.interrupt();
        System.out.println("is interrupted ? " + thread.isInterrupted());
        System.out.println("is alive? " + thread.isAlive());
        // conclusion : dead thread return false for isInterrupted
    }
}
