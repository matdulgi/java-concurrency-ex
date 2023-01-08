package com.dulgi.ex.java.concurrency.thread;

public class VariablesOfRunnable {
    public static void main(String[] args) throws InterruptedException {
        /**
         *  - Runnable 내의 멤버 변수는 스레드간 공유된다.
         */
        Runnable runnable = new Runnable() {
            int s1 = 0;
            @Override
            public void run() {
                int s = s1++;
                System.out.println("runnable "  + s + " started");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("runnable "  + s + " ended");
            }
        };

        for (int i = 0; i< 10; i++){
            new Thread(runnable).start();
        }
    }


}
