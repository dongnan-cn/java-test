package com.nan.cas;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterExample {
    static class Counter {
        volatile int count; // 必须是 volatile 的

        public Counter(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建 AtomicIntegerFieldUpdater 实例
        AtomicIntegerFieldUpdater<Counter> updater = AtomicIntegerFieldUpdater.newUpdater(Counter.class, "count");

        // 创建一个 Counter 实例
        Counter counter = new Counter(0);

        // 创建多个线程并发更新 count 字段
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    updater.incrementAndGet(counter); // 原子递增
                }
            });
            threads[i].start();
        }

        // 等待所有线程执行完毕
        for (Thread thread : threads) {
            thread.join();
        }

        // 输出最终的 count 值
        System.out.println("Final count value: " + counter.getCount());
    }
}
