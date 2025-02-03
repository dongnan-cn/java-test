package com.nan;

import java.util.concurrent.CountDownLatch;

public class CountdownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        int threadCount = 5;
        CountDownLatch latch = new CountDownLatch(threadCount); // 初始化计数器

        for (int i = 0; i < threadCount; i++) {
            new Thread(new Worker(latch), "Thread-" + (i + 1)).start();
        }

        System.out.println("Main thread is waiting for all threads to complete...");
        latch.await(); // 主线程等待所有子线程完成任务
        System.out.println("All threads have completed their work. Main thread continues.");
    }

    static class Worker implements Runnable {
        private final CountDownLatch latch;

        Worker(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is working...");
                Thread.sleep((long) (Math.random() * 2000)); // 模拟任务耗时
                System.out.println(Thread.currentThread().getName() + " has completed its work.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                latch.countDown(); // 任务完成，计数器减 1
            }
        }
    }
}
