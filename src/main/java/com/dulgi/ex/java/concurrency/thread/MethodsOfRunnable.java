package com.dulgi.ex.java.concurrency.thread;

import lombok.Getter;

public class MethodsOfRunnable {
    public static void main(String[] args) throws InterruptedException {
        /**
         *  - 익명객체는 외부에서 다시 접근할 수 없기 때문에, 외부에서 메소드에 접근하고 싶으면 Runnable을 상속해야 한다
         */
        @Getter
        class MyRunnable implements Runnable{
            private int value;
//            private volatile int value;
            MyRunnable(int value){
                this.value = value;
            }

            @Override
            public void run() {
                System.out.println("run");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                this.value++;

            }
        }

        MyRunnable runnable1 = new MyRunnable(1);
        Thread thread = new Thread(runnable1);
        thread.start();
        System.out.println(runnable1.getValue());

        // value of main thread not changed
//        thread.join();
//        System.out.println(runnable1.getValue()); // value joined




    }

}
